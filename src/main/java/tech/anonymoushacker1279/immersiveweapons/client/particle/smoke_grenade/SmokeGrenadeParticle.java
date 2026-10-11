package tech.anonymoushacker1279.immersiveweapons.client.particle.smoke_grenade;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.renderpearl.api.pipeline.BlendFunction;
import com.mojang.renderpearl.api.pipeline.ColorTargetState;
import com.mojang.renderpearl.api.pipeline.CompareOp;
import com.mojang.renderpearl.api.pipeline.DepthStencilState;
import com.mojang.renderpearl.api.pipeline.PrimitiveTopology;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.BindGroupLayouts;
import net.minecraft.client.renderer.oit.OitPipelineSet;
import net.minecraft.client.renderer.state.level.QuadParticleRenderState;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.particle.ParticleSeedPacking;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.SmokeCloudVolume;

/// Smoke grenade smoke. With fancy particles on, it is drawn with a custom shader (`shaders/core/smoke_grenade`) that
/// builds soft, billowing puffs procedurally. Otherwise, it uses the opaque sprite.
public class SmokeGrenadeParticle extends SingleQuadParticle {

	private static final Identifier SHADER = Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "core/smoke_grenade");

	/// The same setup as the vanilla particle pipelines, but with our shaders.
	private static final RenderPipeline.Builder PIPELINE_BUILDER = RenderPipeline.builder()
			.withVertexShader(SHADER)
			.withFragmentShader(SHADER)
			.withBindGroupLayout(BindGroupLayouts.SAMPLER0_SAMPLER2)
			.withBindGroupLayout(BindGroupLayouts.DYNAMIC_TRANSFORMS)
			.withBindGroupLayout(BindGroupLayouts.FOG)
			.withVertexBinding(0, DefaultVertexFormat.PARTICLE)
			.withPrimitiveTopology(PrimitiveTopology.QUADS)
			.withDepthStencilState(new DepthStencilState(CompareOp.GREATER_THAN_OR_EQUAL, false));

	public static final OitPipelineSet OIT_PIPELINES = OitPipelineSet.builder("smoke_grenade", PIPELINE_BUILDER).build();

	/// Used when order-independent transparency is not active.
	public static final RenderPipeline PIPELINE = RenderPipeline.builder()
			.withLocation(Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "pipeline/smoke_grenade"))
			.withVertexShader(SHADER)
			.withFragmentShader(SHADER)
			.withBindGroupLayout(BindGroupLayouts.GLOBALS)
			.withBindGroupLayout(BindGroupLayouts.PROJECTION)
			.withBindGroupLayout(BindGroupLayouts.DYNAMIC_TRANSFORMS)
			.withBindGroupLayout(BindGroupLayouts.FOG)
			.withBindGroupLayout(BindGroupLayouts.SAMPLER0_SAMPLER2)
			.withVertexBinding(0, DefaultVertexFormat.PARTICLE)
			.withPrimitiveTopology(PrimitiveTopology.QUADS)
			// Tested against the world but not written, so overlapping puffs don't cut hard edges into each other
			// as the camera rotates the billboards
			.withDepthStencilState(new DepthStencilState(CompareOp.GREATER_THAN_OR_EQUAL, false))
			.withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
			.build();

	private static final Layer SHADER_LAYER = new Layer(true, TextureAtlas.LOCATION_PARTICLES, PIPELINE, OIT_PIPELINES);

	/// Approximate edge length, in blocks, of the tiles a puff is split into so it can be clipped to the cloud.
	private static final float TILE_SIZE = 1.0F;
	private static final int MAX_TILES = 8;

	/// Portion of the lifetime over which the puff fades out at the end.
	private static final float FADE_OUT_FRACTION = 0.25F;

	@Nullable
	protected static SpriteSet sprites;

	private final boolean fancyParticles;
	private final int delay;
	@Nullable
	private final SmokeCloudField cloud;
	private final float baseAlpha;

	protected SmokeGrenadeParticle(ClientLevel level, double x, double y, double z,
	                               double xSpeed, double ySpeed,
	                               double zSpeed, SpriteSet spriteSet, int color, int delay, int cloudId) {

		super(level, x, y, z, spriteSet.first());
		this.delay = delay;
		cloud = cloudId == 0 ? null : SmokeCloudField.get(cloudId);
		friction = 0.96F;
		fancyParticles = IWConfigs.CLIENT.fancySmokeGrenadeParticles.getAsBoolean();
		gravity = fancyParticles ? 0.02F : 0.05F;
		speedUpWhenYMotionIsBlocked = true;
		sprites = spriteSet;
		xd *= 1.0F;
		yd *= 1.0F;
		zd *= 1.0F;
		xd += fancyParticles ? xSpeed * 0.4f : xSpeed;
		yd += fancyParticles ? ySpeed * 0.4f : ySpeed;
		zd += fancyParticles ? zSpeed * 0.4f : zSpeed;
		float vibrancyModifier = level.getRandom().nextFloat() * 0.4F + 0.6F;
		rCol = randomizeColor((float) ((color >> 16) & 0xFF) / 255.0F, vibrancyModifier);
		gCol = randomizeColor((float) ((color >> 8) & 0xFF) / 255.0F, vibrancyModifier);
		bCol = randomizeColor((float) (color & 0xFF) / 255.0F, vibrancyModifier);
		baseAlpha = fancyParticles ? 0.97F : 1.0F;
		alpha = baseAlpha;
		quadSize *= 0.75F * 35.0F;
		if (cloud != null) {
			// All puffs end close together, so the server can end the target-clearing effect when they have mostly faded
			// This includes the growth delay, so every puff ends at about the same time however far it had to spread
			lifetime = (int) (cloud.duration() * (1.0F + (SmokeCloudVolume.PUFF_LIFETIME_SPREAD - 1.0F) * random.nextFloat()));
			lifetime = Math.max(lifetime, delay + 1);
		} else {
			lifetime = (int) ((double) 90 / ((double) level.getRandom().nextFloat() * 0.8D + 0.2D));
			lifetime = (int) ((float) lifetime * (35.0F * 0.1F));
		}
		if (cloud == null) {
			lifetime = Math.max(lifetime, 1) + delay;
		}
		setSpriteFromAge(spriteSet);
		hasPhysics = true;

		if (fancyParticles) {
			// The shader has no other per-puff data, so it gets a 9-bit seed in the low bits of the color channels
			int seed = random.nextInt(ParticleSeedPacking.SEED_RANGE);
			rCol = ParticleSeedPacking.pack(rCol, seed, 0);
			gCol = ParticleSeedPacking.pack(gCol, seed, 1);
			bCol = ParticleSeedPacking.pack(bCol, seed, 2);
		}
	}

	protected float randomizeColor(float color, float multiplier) {
		return (random.nextFloat() * 0.05F + 0.95F) * color * multiplier;
	}

	@Override
	protected Layer getLayer() {
		return fancyParticles ? SHADER_LAYER : Layer.OPAQUE;
	}

	@Override
	public float getQuadSize(float partialTick) {
		float growthAge = (float) age - delay + partialTick;
		float size = quadSize * Mth.clamp(growthAge / (float) (lifetime - delay) * 32.0F, 0.0F, 1.0F);

		if (fancyParticles) {
			size *= 0.9F;
		}

		return size;
	}

	/// The fade is computed per frame from the partial tick so it doesn't step.
	///
	/// With the shader, the puff is split into a grid of tiles that each carry how visible their corners are, taken from
	/// the [SmokeCloudField]. That clips the smoke to the space it fills, which a single camera-facing quad can't do.
	/// The tile and visibilities are packed into the UVs, which the shader doesn't need for sampling; see
	/// `shaders/core/smoke_grenade.vsh`.
	@Override
	public void extract(QuadParticleRenderState state, Camera camera, float partialTick) {
		if (!fancyParticles) {
			super.extract(state, camera, partialTick);
			return;
		}

		float remaining = ((float) lifetime - age - partialTick) / ((float) lifetime * FADE_OUT_FRACTION);
		alpha = baseAlpha * Mth.clamp(remaining, 0.0F, 1.0F);

		float half = getQuadSize(partialTick);
		if (half <= 0.001F || alpha <= 0.0F) {
			return;
		}

		Quaternionf rotation = new Quaternionf();
		getFacingCameraMode().setRotation(rotation, camera, partialTick);
		Vector3f right = new Vector3f(1.0F, 0.0F, 0.0F).rotate(rotation);
		Vector3f up = new Vector3f(0.0F, 1.0F, 0.0F).rotate(rotation);

		double worldX = Mth.lerp(partialTick, xo, x);
		double worldY = Mth.lerp(partialTick, yo, y);
		double worldZ = Mth.lerp(partialTick, zo, z);
		Vec3 cameraPos = camera.position();
		float relX = (float) (worldX - cameraPos.x());
		float relY = (float) (worldY - cameraPos.y());
		float relZ = (float) (worldZ - cameraPos.z());

		int tiles = Mth.clamp(Math.round(half * 2.0F / TILE_SIZE), 1, MAX_TILES);

		// Visibility at each tile corner. Row b runs from the top of the puff to the bottom, to match the shader.
		float[][] visibility = new float[tiles + 1][tiles + 1];
		for (int b = 0; b <= tiles; b++) {
			float py = (1.0F - 2.0F * b / tiles) * half;
			for (int a = 0; a <= tiles; a++) {
				float px = (2.0F * a / tiles - 1.0F) * half;
				visibility[b][a] = cloud == null ? 1.0F : cloud.visibility(
						worldX + right.x * px + up.x * py,
						worldY + right.y * px + up.y * py,
						worldZ + right.z * px + up.z * py);
			}
		}

		int color = ARGB.colorFromFloat(alpha, rCol, gCol, bCol);
		int light = getLightCoords(partialTick);
		float tileScale = half / tiles;
		float tileSpan = 1.0F / tiles;

		for (int j = 0; j < tiles; j++) {
			for (int i = 0; i < tiles; i++) {
				float topLeft = visibility[j][i];
				float topRight = visibility[j][i + 1];
				float bottomLeft = visibility[j + 1][i];
				float bottomRight = visibility[j + 1][i + 1];

				if (Math.max(Math.max(topLeft, topRight), Math.max(bottomLeft, bottomRight)) <= 0.01F) {
					continue;
				}

				// Skip tiles entirely outside the inscribed circle, which the shader never draws
				float nearestX = Math.max(Math.abs((i + 0.5F) * 2.0F * tileSpan - 1.0F) - tileSpan, 0.0F);
				float nearestY = Math.max(Math.abs((j + 0.5F) * 2.0F * tileSpan - 1.0F) - tileSpan, 0.0F);
				if (nearestX * nearestX + nearestY * nearestY >= 1.0F) {
					continue;
				}

				// Each quad corner owns one of the four UV values, so each carries its own visibility. The value is tagged
				// with that corner (0 for U1, 1 for V0, 2 for U0, 3 for V1) so the shader can tell which corner a vertex is.
				float code = (tiles * 64 + j * 8 + i) * 4;
				float u1 = code + 0 + encodeVisibility(bottomRight);
				float v0 = code + 1 + encodeVisibility(topRight);
				float u0 = code + 2 + encodeVisibility(topLeft);
				float v1 = code + 3 + encodeVisibility(bottomLeft);

				float px = ((i + 0.5F) * 2.0F * tileSpan - 1.0F) * half;
				float py = (1.0F - (j + 0.5F) * 2.0F * tileSpan) * half;
				state.add(getLayer(),
						relX + right.x * px + up.x * py,
						relY + right.y * px + up.y * py,
						relZ + right.z * px + up.z * py,
						rotation.x, rotation.y, rotation.z, rotation.w,
						tileScale, u0, u1, v0, v1, color, light);
			}
		}
	}

	/// Keeps the fractional part clear of 1.0, so it can't be mistaken for the next tag.
	private static float encodeVisibility(float visibility) {
		return Mth.clamp(visibility, 0.0F, 1.0F) * 0.99F;
	}

	@Override
	public void tick() {
		super.tick();
		if (sprites != null) {
			setSpriteFromAge(sprites);
		}
	}

	public static class Provider implements ParticleProvider<SmokeGrenadeParticleOptions> {

		public Provider(SpriteSet pSprites) {
			sprites = pSprites;
		}

		@Nullable
		@Override
		public Particle createParticle(SmokeGrenadeParticleOptions type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
			return sprites != null ? new SmokeGrenadeParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, sprites, type.color(), type.delay(), type.cloudId()) : null;
		}
	}
}
