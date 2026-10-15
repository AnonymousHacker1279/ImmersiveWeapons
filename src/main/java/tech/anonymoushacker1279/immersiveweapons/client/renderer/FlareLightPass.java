package tech.anonymoushacker1279.immersiveweapons.client.renderer;

import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.buffers.Std140SizeCalculator;
import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import com.mojang.blaze3d.framegraph.FramePass;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.resource.ResourceHandle;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.renderpearl.api.GpuFormat;
import com.mojang.renderpearl.api.buffers.GpuBuffer;
import com.mojang.renderpearl.api.commands.CommandEncoder;
import com.mojang.renderpearl.api.commands.RenderPass;
import com.mojang.renderpearl.api.pipeline.BindGroupLayout;
import com.mojang.renderpearl.api.pipeline.BlendFactor;
import com.mojang.renderpearl.api.pipeline.BlendFunction;
import com.mojang.renderpearl.api.pipeline.ColorTargetState;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import com.mojang.renderpearl.api.pipeline.UniformType;
import com.mojang.renderpearl.api.textures.FilterMode;
import com.mojang.renderpearl.api.textures.GpuTexture;
import com.mojang.renderpearl.api.textures.GpuTextureView;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelTargetBundle;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.FlareEntity;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;

/// A screen-space lighting pass for flares, added to the frame graph right after the main level pass.
///
/// It reads the main depth buffer, rebuilds the world position of each pixel, and blends a smooth falloff around every
/// nearby flare on top of the frame. The light follows the flare every frame instead of snapping between blocks, but
/// it is purely visual and does not affect the light level of blocks.
///
/// What blocks the light comes from a [FlareLightGrid] built around each flare, so walls, corners, and tunnels behave
/// the same no matter where the camera is.
public final class FlareLightPass {

	/// Must match `MAX_LIGHTS` in `shaders/post/flare_light.fsh`.
	private static final int MAX_LIGHTS = 8;
	private static final double MAX_DISTANCE_SQR = 128.0D * 128.0D;
	private static final int MAX_BUILDS_PER_FRAME = 2;
	private static final float[] COLOR = {1.0F, 0.48F, 0.28F};
	private static final float STRENGTH = 0.85F;

	/// Adds light scaled by what is already on screen (`dst * (1 + light)`), so the light is tinted by each surface's own
	/// color instead of being painted over it. Plain addition washes everything out toward the light's color.
	private static final BlendFunction ALBEDO_WEIGHTED = new BlendFunction(
			BlendFactor.DST_COLOR, BlendFactor.ONE, BlendFactor.ONE, BlendFactor.ONE
	);

	public static final RenderPipeline PIPELINE = RenderPipeline.builder(RenderPipelines.POST_PROCESSING_SNIPPET)
			.withLocation(Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "pipeline/flare_light"))
			.withVertexShader(Identifier.withDefaultNamespace("core/screenquad"))
			.withFragmentShader(Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "post/flare_light"))
			.withBindGroupLayout(BindGroupLayout.builder()
					.withUniform("DepthSampler", UniformType.COMBINED_IMAGE_SAMPLER)
					.withUniform("LightGrid", UniformType.COMBINED_IMAGE_SAMPLER)
					.withUniform("FlareLights", UniformType.UNIFORM_BUFFER)
					.build())
			.withColorTargetState(new ColorTargetState(ALBEDO_WEIGHTED))
			.build();

	/// The same light, added as a small flat amount. Surfaces in darkness have almost no color to scale, so this is
	/// what lets the flare light up caves, tinted by the light's own color.
	public static final RenderPipeline FLAT_PIPELINE = RenderPipeline.builder(RenderPipelines.POST_PROCESSING_SNIPPET)
			.withLocation(Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "pipeline/flare_light_flat"))
			.withVertexShader(Identifier.withDefaultNamespace("core/screenquad"))
			.withFragmentShader(Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "post/flare_light"))
			.withShaderDefine("FLAT")
			.withBindGroupLayout(BindGroupLayout.builder()
					.withUniform("DepthSampler", UniformType.COMBINED_IMAGE_SAMPLER)
					.withUniform("LightGrid", UniformType.COMBINED_IMAGE_SAMPLER)
					.withUniform("FlareLights", UniformType.UNIFORM_BUFFER)
					.build())
			.withColorTargetState(new ColorTargetState(BlendFunction.ADDITIVE))
			.build();

	/// One flare's grid, kept between frames so it is only rebuilt when the flare moves.
	private static final class Slot {
		private final FlareLightGrid grid = new FlareLightGrid();
		private int entityId = -1;
	}

	private static final Slot[] SLOTS = new Slot[MAX_LIGHTS];

	static {
		for (int i = 0; i < SLOTS.length; i++) {
			SLOTS[i] = new Slot();
		}
	}

	/// All grids live in one image, a row of `FlareLightGrid.HEIGHT` rows per slot.
	private static GpuTexture gridTexture;
	private static GpuTextureView gridTextureView;

	private FlareLightPass() {
	}

	/// A light's exact position relative to the camera, with its brightness (already includes fade and flicker) and
	/// where its grid is.
	private record Light(float x, float y, float z, float brightness, float gridX, float gridY, float gridZ, int slot) {
	}

	/// Adds the pass to the frame. Must be called after the main pass was added, so it runs once the frame is drawn.
	public static void addToFrame(FrameGraphBuilder frame, LevelTargetBundle targets, CameraRenderState camera) {
		if (!IWConfigs.CLIENT.flareDynamicLighting.getAsBoolean()) {
			return;
		}

		List<Light> lights = collectLights(camera.pos);
		if (lights.isEmpty()) {
			return;
		}

		Matrix4f invProjection = camera.projectionMatrix.invert(new Matrix4f());
		Matrix4f invView = camera.viewRotationMatrix.invert(new Matrix4f());

		FramePass pass = frame.addPass("flare_light");
		ResourceHandle<RenderTarget> main = pass.readsAndWrites(targets.main);
		targets.main = main;

		pass.executes(() -> draw(main.get(), invProjection, invView, lights));
	}

	private record Candidate(FlareEntity flare, Vec3 offset) {
	}

	private static List<Light> collectLights(Vec3 cameraPos) {
		Minecraft minecraft = Minecraft.getInstance();
		ClientLevel level = minecraft.level;
		if (level == null) {
			return List.of();
		}

		float partialTick = minecraft.getDeltaTracker().getGameTimeDeltaPartialTick(false);
		long gameTime = level.getGameTime();
		float time = gameTime + partialTick;

		List<Candidate> candidates = new ArrayList<>();
		for (Entity entity : level.entitiesForRendering()) {
			if (!(entity instanceof FlareEntity flare) || flare.isRemoved()) {
				continue;
			}

			Vec3 offset = flare.getPosition(partialTick).subtract(cameraPos);
			if (offset.lengthSqr() <= MAX_DISTANCE_SQR) {
				candidates.add(new Candidate(flare, offset));
			}
		}

		// Only the closest flares get a grid
		candidates.sort(Comparator.comparingDouble(candidate -> candidate.offset.lengthSqr()));
		if (candidates.size() > MAX_LIGHTS) {
			candidates = new ArrayList<>(candidates.subList(0, MAX_LIGHTS));
		}

		// Free the slots of flares that went away or fell out of range
		Set<Integer> chosen = new HashSet<>();
		for (Candidate candidate : candidates) {
			chosen.add(candidate.flare.getId());
		}
		for (Slot slot : SLOTS) {
			if (!chosen.contains(slot.entityId)) {
				slot.entityId = -1;
			}
		}

		List<Light> lights = new ArrayList<>();
		int builds = 0;
		for (Candidate candidate : candidates) {
			FlareEntity flare = candidate.flare;

			int slotIndex = slotFor(flare.getId());
			if (slotIndex < 0) {
				continue;
			}

			FlareLightGrid grid = SLOTS[slotIndex].grid;
			BlockPos cell = flare.blockPosition();
			if (grid.needsBuild(cell, gameTime)) {
				// A flare with no grid yet has to wait its turn, rather than being drawn without occlusion
				if (builds >= MAX_BUILDS_PER_FRAME && !grid.isBuilt()) {
					continue;
				}

				if (builds < MAX_BUILDS_PER_FRAME) {
					grid.build(level, cell, gameTime);
					builds++;
				}
			}

			// Two out-of-phase waves keep the flicker from looking mechanical, each flare offset by its id
			float flicker = 0.9F + 0.06F * (float) Math.sin(time * 1.9F + flare.getId() * 1.7F)
					+ 0.04F * (float) Math.sin(time * 5.3F + flare.getId() * 0.6F);
			float brightness = flare.getLightIntensity() * flicker * STRENGTH;

			Vec3 offset = candidate.offset;
			lights.add(new Light(
					(float) offset.x, (float) offset.y, (float) offset.z, brightness,
					(float) (grid.originX() - cameraPos.x),
					(float) (grid.originY() - cameraPos.y),
					(float) (grid.originZ() - cameraPos.z),
					slotIndex
			));
		}

		return lights;
	}

	/// Finds the slot already holding this flare, or claims a free one.
	private static int slotFor(int entityId) {
		for (int i = 0; i < SLOTS.length; i++) {
			if (SLOTS[i].entityId == entityId) {
				return i;
			}
		}

		for (int i = 0; i < SLOTS.length; i++) {
			if (SLOTS[i].entityId == -1) {
				SLOTS[i].entityId = entityId;
				SLOTS[i].grid.invalidate();
				return i;
			}
		}

		return -1;
	}

	private static void ensureTexture() {
		if (gridTexture != null && !gridTexture.isClosed()) {
			return;
		}

		gridTexture = RenderSystem.getDevice().createTexture(
				"Flare light grid",
				GpuTexture.USAGE_COPY_DST | GpuTexture.USAGE_TEXTURE_BINDING,
				GpuFormat.R8_UNORM,
				FlareLightGrid.WIDTH,
				FlareLightGrid.HEIGHT * MAX_LIGHTS,
				1,
				1
		);
		gridTextureView = RenderSystem.getDevice().createTextureView(gridTexture);

		// Whatever was uploaded before is gone
		for (Slot slot : SLOTS) {
			slot.grid.dirty = true;
		}
	}

	private static void uploadGrids(CommandEncoder encoder, List<Light> lights) {
		ByteBuffer buffer = MemoryUtil.memAlloc(FlareLightGrid.WIDTH * FlareLightGrid.HEIGHT);
		try {
			for (Light light : lights) {
				FlareLightGrid grid = SLOTS[light.slot].grid;
				if (!grid.dirty) {
					continue;
				}

				buffer.clear();
				buffer.put(grid.image);
				buffer.flip();

				encoder.writeToTexture(
						gridTexture, buffer, 0, 0,
						0, light.slot * FlareLightGrid.HEIGHT,
						FlareLightGrid.WIDTH, FlareLightGrid.HEIGHT
				);
				grid.dirty = false;
			}
		} finally {
			MemoryUtil.memFree(buffer);
		}
	}

	private static void draw(RenderTarget target, Matrix4f invProjection, Matrix4f invView, List<Light> lights) {
		ensureTexture();
		CommandEncoder encoder = RenderSystem.getDevice().createCommandEncoder();
		uploadGrids(encoder, lights);

		Std140SizeCalculator calculator = new Std140SizeCalculator().putMat4f().putMat4f();
		for (int i = 0; i < MAX_LIGHTS * 3; i++) {
			calculator.putVec4();
		}
		calculator.putInt();

		try (MemoryStack stack = MemoryStack.stackPush()) {
			Std140Builder builder = Std140Builder.onStack(stack, calculator.get());
			builder.putMat4f(invProjection).putMat4f(invView);

			for (int i = 0; i < MAX_LIGHTS; i++) {
				if (i < lights.size()) {
					Light light = lights.get(i);
					builder.putVec4(light.x, light.y, light.z, 0.0F);
				} else {
					builder.putVec4(0.0F, 0.0F, 0.0F, 0.0F);
				}
			}

			for (int i = 0; i < MAX_LIGHTS; i++) {
				if (i < lights.size()) {
					Light light = lights.get(i);
					builder.putVec4(light.gridX, light.gridY, light.gridZ, light.slot * FlareLightGrid.HEIGHT);
				} else {
					builder.putVec4(0.0F, 0.0F, 0.0F, 0.0F);
				}
			}

			for (int i = 0; i < MAX_LIGHTS; i++) {
				float brightness = i < lights.size() ? lights.get(i).brightness : 0.0F;
				builder.putVec4(COLOR[0] * brightness, COLOR[1] * brightness, COLOR[2] * brightness, 0.0F);
			}

			builder.putInt(lights.size());

			try (GpuBuffer buffer = RenderSystem.getDevice().createBuffer(() -> "Flare lights", GpuBuffer.USAGE_UNIFORM, builder.get());
			     RenderPass renderPass = encoder.createRenderPass(
					     () -> "Flare lights",
					     target.getColorTextureView(),
					     Optional.empty(),
					     null,
					     OptionalDouble.empty()
			     )) {
				renderPass.setPipeline(RenderSystem.getCompiledPipeline(PIPELINE));
				RenderSystem.bindDefaultUniforms(renderPass);
				renderPass.setUniform("FlareLights", buffer);

				var nearest = RenderSystem.getSamplerCache().getClampToEdge(FilterMode.NEAREST);
				renderPass.setUniform("DepthSampler", target.getDepthTextureView(), nearest);
				renderPass.setUniform("LightGrid", gridTextureView, nearest);
				renderPass.draw(3, 1, 0, 0);

				// The flat light for surfaces too dark to be brightened by the first draw, such as in caves
				renderPass.setPipeline(RenderSystem.getCompiledPipeline(FLAT_PIPELINE));
				renderPass.setUniform("FlareLights", buffer);
				renderPass.setUniform("DepthSampler", target.getDepthTextureView(), nearest);
				renderPass.setUniform("LightGrid", gridTextureView, nearest);
				renderPass.draw(3, 1, 0, 0);
			}
		}
	}
}
