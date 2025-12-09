package tech.anonymoushacker1279.immersiveweapons.client.renderer.dimension;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.state.LevelRenderState;
import net.minecraft.client.renderer.state.SkyRenderState;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import java.util.OptionalDouble;
import java.util.OptionalInt;

public class TiltrosDimensionSpecialEffects extends DimensionSpecialEffects {

	private static final ResourceLocation SKY_LOCATION = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
			"textures/environment/tiltros_sky.png");
	private final GpuBuffer skyBuffer;

	public TiltrosDimensionSpecialEffects() {
		super(SkyType.END, false, false);
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
	public boolean renderSky(LevelRenderState levelRenderState, SkyRenderState skyRenderState, Matrix4f modelViewMatrix, Runnable setupFog) {
		TextureManager textureManager = Minecraft.getInstance().getTextureManager();
		AbstractTexture texture = textureManager.getTexture(SKY_LOCATION);
		texture.setUseMipmaps(false);
		RenderSystem.AutoStorageIndexBuffer sequentialBuffer = RenderSystem.getSequentialBuffer(VertexFormat.Mode.QUADS);
		GpuBuffer gpuBuffer = sequentialBuffer.getBuffer(36);
		GpuTextureView colorTextureView = Minecraft.getInstance().getMainRenderTarget().getColorTextureView();
		GpuTextureView depthTextureView = Minecraft.getInstance().getMainRenderTarget().getDepthTextureView();
		GpuBufferSlice bufferSlice = RenderSystem.getDynamicUniforms()
				.writeTransform(RenderSystem.getModelViewMatrix(), new Vector4f(1.0F, 1.0F, 1.0F, 1.0F), new Vector3f(), new Matrix4f(), 0.0F);

		try (RenderPass pass = RenderSystem.getDevice()
				.createCommandEncoder()
				.createRenderPass(() -> "Tiltros sky", colorTextureView, OptionalInt.empty(), depthTextureView, OptionalDouble.empty())) {
			pass.setPipeline(RenderPipelines.END_SKY);
			RenderSystem.bindDefaultUniforms(pass);
			pass.setUniform("DynamicTransforms", bufferSlice);
			pass.bindSampler("Sampler0", texture.getTextureView());
			pass.setVertexBuffer(0, skyBuffer);
			pass.setIndexBuffer(gpuBuffer, sequentialBuffer.type());
			pass.drawIndexed(0, 0, 36, 1);
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
				buffer = RenderSystem.getDevice().createBuffer(() -> "Tiltros sky vertex buffer", 40, mesh.vertexBuffer());
			}
		}

		return buffer;
	}
}