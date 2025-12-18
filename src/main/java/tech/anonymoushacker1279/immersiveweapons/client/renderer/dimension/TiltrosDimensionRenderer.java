package tech.anonymoushacker1279.immersiveweapons.client.renderer.dimension;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.Identifier;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import java.util.OptionalDouble;
import java.util.OptionalInt;

public class TiltrosDimensionRenderer {

	private static final Identifier SKY_LOCATION = Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
			"textures/environment/tiltros_sky.png");
	private final GpuBuffer skyBuffer;
	private final AbstractTexture tiltrosSkyTexture;

	public TiltrosDimensionRenderer() {
		skyBuffer = buildSkyBuffer();

		TextureManager textureManager = Minecraft.getInstance().getTextureManager();
		tiltrosSkyTexture = textureManager.getTexture(SKY_LOCATION);
	}

	private static GpuBuffer buildSkyBuffer() {
		GpuBuffer buffer;
		try (ByteBufferBuilder byteBufferBuilder = ByteBufferBuilder.exactlySized(24 * DefaultVertexFormat.POSITION_TEX_COLOR.getVertexSize())) {
			BufferBuilder bufferbuilder = new BufferBuilder(byteBufferBuilder, VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);

			for (int i = 0; i < 6; i++) {
				Matrix4f matrix = new Matrix4f();
				switch (i) {
					case 1:
						matrix.rotationX((float) (Math.PI / 2));
						break;
					case 2:
						matrix.rotationX((float) (-Math.PI / 2));
						break;
					case 3:
						matrix.rotationX((float) Math.PI);
						break;
					case 4:
						matrix.rotationZ((float) (Math.PI / 2));
						break;
					case 5:
						matrix.rotationZ((float) (-Math.PI / 2));
				}

				bufferbuilder.addVertex(matrix, -100.0F, -100.0F, -100.0F).setUv(0.0F, 0.0F).setColor(-14145496);
				bufferbuilder.addVertex(matrix, -100.0F, -100.0F, 100.0F).setUv(0.0F, 16.0F).setColor(-14145496);
				bufferbuilder.addVertex(matrix, 100.0F, -100.0F, 100.0F).setUv(16.0F, 16.0F).setColor(-14145496);
				bufferbuilder.addVertex(matrix, 100.0F, -100.0F, -100.0F).setUv(16.0F, 0.0F).setColor(-14145496);
			}

			try (MeshData meshdata = bufferbuilder.buildOrThrow()) {
				buffer = RenderSystem.getDevice().createBuffer(() -> "Tiltros sky vertex buffer", 40, meshdata.vertexBuffer());
			}
		}

		return buffer;
	}

	public void renderSky() {
		RenderSystem.AutoStorageIndexBuffer indexBuffer = RenderSystem.getSequentialBuffer(VertexFormat.Mode.QUADS);
		GpuBuffer buffer = indexBuffer.getBuffer(36);
		GpuTextureView colorTextureView = Minecraft.getInstance().getMainRenderTarget().getColorTextureView();
		GpuTextureView depthTextureView = Minecraft.getInstance().getMainRenderTarget().getDepthTextureView();
		GpuBufferSlice bufferSlice = RenderSystem.getDynamicUniforms()
				.writeTransform(RenderSystem.getModelViewMatrix(), new Vector4f(1.0F, 1.0F, 1.0F, 1.0F), new Vector3f(), new Matrix4f());

		try (RenderPass pass = RenderSystem.getDevice()
				.createCommandEncoder()
				.createRenderPass(() -> "Tiltros sky", colorTextureView, OptionalInt.empty(), depthTextureView, OptionalDouble.empty())) {
			pass.setPipeline(RenderPipelines.END_SKY);
			RenderSystem.bindDefaultUniforms(pass);
			pass.setUniform("DynamicTransforms", bufferSlice);
			pass.bindTexture("Sampler0", tiltrosSkyTexture.getTextureView(), tiltrosSkyTexture.getSampler());
			pass.setVertexBuffer(0, skyBuffer);
			pass.setIndexBuffer(buffer, indexBuffer.type());
			pass.drawIndexed(0, 0, 36, 1);
		}
	}
}