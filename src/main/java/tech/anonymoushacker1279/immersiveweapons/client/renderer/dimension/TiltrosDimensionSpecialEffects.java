package tech.anonymoushacker1279.immersiveweapons.client.renderer.dimension;

import com.mojang.blaze3d.buffers.BufferType;
import com.mojang.blaze3d.buffers.BufferUsage;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.TriState;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import java.util.OptionalDouble;
import java.util.OptionalInt;

public class TiltrosDimensionSpecialEffects extends DimensionSpecialEffects {

	private static final ResourceLocation SKY_LOCATION = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
			"textures/environment/tiltros_sky.png");
	private final GpuBuffer skyBuffer;

	public TiltrosDimensionSpecialEffects() {
		super(Float.NaN, true, SkyType.END, true, false);
		skyBuffer = buildBuffer();
	}

	@Override
	public Vec3 getBrightnessDependentFogColor(Vec3 scale, float pBrightness) {
		return scale.scale(0.15F);
	}

	@Override
	public boolean isFoggyAt(int pX, int pY) {
		return false;
	}

	@Override
	public int getSunriseOrSunsetColor(float timeOfDay) {
		return 0xFFFFFF;
	}

	@Override
	public boolean renderSky(ClientLevel level, int ticks, float partialTick, Matrix4f modelViewMatrix, Camera camera, Matrix4f projectionMatrix, Runnable setupFog) {
		TextureManager textureManager = Minecraft.getInstance().getTextureManager();
		AbstractTexture texture = textureManager.getTexture(SKY_LOCATION);
		texture.setFilter(TriState.FALSE, false);
		RenderSystem.AutoStorageIndexBuffer indexBuffer = RenderSystem.getSequentialBuffer(VertexFormat.Mode.QUADS);
		GpuBuffer buffer = indexBuffer.getBuffer(36);
		GpuTexture colorTexture = Minecraft.getInstance().getMainRenderTarget().getColorTexture();
		GpuTexture depthTexture = Minecraft.getInstance().getMainRenderTarget().getDepthTexture();

		try (RenderPass renderpass = RenderSystem.getDevice()
				.createCommandEncoder()
				.createRenderPass(colorTexture, OptionalInt.empty(), depthTexture, OptionalDouble.empty())) {
			renderpass.setPipeline(RenderPipelines.END_SKY);
			renderpass.bindSampler("Sampler0", texture.getTexture());
			renderpass.setVertexBuffer(0, skyBuffer);
			renderpass.setIndexBuffer(buffer, indexBuffer.type());
			renderpass.drawIndexed(0, 36);
		}

		return true;
	}

	private static GpuBuffer buildBuffer() {
		GpuBuffer buffer;
		try (ByteBufferBuilder byteBufferBuilder = new ByteBufferBuilder(24 * DefaultVertexFormat.POSITION_TEX_COLOR.getVertexSize())) {
			BufferBuilder bufferBuilder = new BufferBuilder(byteBufferBuilder, VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);

			for (int i = 0; i < 6; i++) {
				Matrix4f matrix4f = new Matrix4f();
				switch (i) {
					case 1:
						matrix4f.rotationX((float) (Math.PI / 2));
						break;
					case 2:
						matrix4f.rotationX((float) (-Math.PI / 2));
						break;
					case 3:
						matrix4f.rotationX((float) Math.PI);
						break;
					case 4:
						matrix4f.rotationZ((float) (Math.PI / 2));
						break;
					case 5:
						matrix4f.rotationZ((float) (-Math.PI / 2));
				}

				bufferBuilder.addVertex(matrix4f, -100.0F, -100.0F, -100.0F).setUv(0.0F, 0.0F).setColor(-14145496);
				bufferBuilder.addVertex(matrix4f, -100.0F, -100.0F, 100.0F).setUv(0.0F, 16.0F).setColor(-14145496);
				bufferBuilder.addVertex(matrix4f, 100.0F, -100.0F, 100.0F).setUv(16.0F, 16.0F).setColor(-14145496);
				bufferBuilder.addVertex(matrix4f, 100.0F, -100.0F, -100.0F).setUv(16.0F, 0.0F).setColor(-14145496);
			}

			try (MeshData mesh = bufferBuilder.buildOrThrow()) {
				buffer = RenderSystem.getDevice().createBuffer(() ->
						"End sky vertex buffer", BufferType.VERTICES, BufferUsage.STATIC_WRITE, mesh.vertexBuffer());
			}
		}

		return buffer;
	}
}