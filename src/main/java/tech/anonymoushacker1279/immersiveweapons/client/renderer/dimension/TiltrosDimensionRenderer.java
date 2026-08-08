package tech.anonymoushacker1279.immersiveweapons.client.renderer.dimension;

import com.mojang.blaze3d.PrimitiveTopology;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.Identifier;
import org.joml.Matrix4f;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import java.util.Optional;
import java.util.OptionalDouble;

public class TiltrosDimensionRenderer {

	private static final Identifier SKY_LOCATION = Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/environment/tiltros_sky.png");
	private final GpuBuffer skyBuffer;
	private final RenderTarget renderTarget;
	private final AbstractTexture tiltrosSkyTexture;

	public TiltrosDimensionRenderer() {
		skyBuffer = buildSkyBuffer();

		TextureManager textureManager = Minecraft.getInstance().getTextureManager();
		renderTarget = Minecraft.getInstance().gameRenderer.mainRenderTarget();
		tiltrosSkyTexture = textureManager.getTexture(SKY_LOCATION);
	}

	private static GpuBuffer buildSkyBuffer() {
		try (ByteBufferBuilder byteBufferBuilder = ByteBufferBuilder.exactlySized(24 * DefaultVertexFormat.POSITION_TEX_COLOR.getVertexSize())) {
			BufferBuilder bufferBuilder = new BufferBuilder(byteBufferBuilder, PrimitiveTopology.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);

			for (int i = 0; i < 6; i++) {
				Matrix4f pose = new Matrix4f();
				switch (i) {
					case 1:
						pose.rotationX((float) (Math.PI / 2));
						break;
					case 2:
						pose.rotationX((float) (-Math.PI / 2));
						break;
					case 3:
						pose.rotationX((float) Math.PI);
						break;
					case 4:
						pose.rotationZ((float) (Math.PI / 2));
						break;
					case 5:
						pose.rotationZ((float) (-Math.PI / 2));
				}

				bufferBuilder.addVertex(pose, -100.0F, -100.0F, -100.0F).setUv(0.0F, 0.0F).setColor(-14145496);
				bufferBuilder.addVertex(pose, -100.0F, -100.0F, 100.0F).setUv(0.0F, 16.0F).setColor(-14145496);
				bufferBuilder.addVertex(pose, 100.0F, -100.0F, 100.0F).setUv(16.0F, 16.0F).setColor(-14145496);
				bufferBuilder.addVertex(pose, 100.0F, -100.0F, -100.0F).setUv(16.0F, 0.0F).setColor(-14145496);
			}

			try (MeshData meshData = bufferBuilder.buildOrThrow()) {
				return RenderSystem.getDevice().createBuffer(() -> "Tiltros sky vertex buffer", GpuBuffer.USAGE_COPY_DST | GpuBuffer.USAGE_VERTEX, meshData.vertexBuffer());
			}
		}
	}

	public void renderSky() {
		RenderSystem.AutoStorageIndexBuffer autoIndices = RenderSystem.getSequentialBuffer(PrimitiveTopology.QUADS);
		GpuBuffer indexBuffer = autoIndices.getBuffer(36);
		GpuTextureView colorTexture = renderTarget.getColorTextureView();
		GpuTextureView depthTexture = renderTarget.getDepthTextureView();
		GpuBufferSlice dynamicTransforms = RenderSystem.getDynamicUniforms().writeTransform(RenderSystem.getModelViewMatrixCopy());

		try (RenderPass renderPass = RenderSystem.getDevice()
				.createCommandEncoder()
				.createRenderPass(() -> "Tiltros sky", colorTexture, Optional.empty(), depthTexture, OptionalDouble.empty())) {
			renderPass.setPipeline(RenderPipelines.END_SKY);
			RenderSystem.bindDefaultUniforms(renderPass);
			renderPass.setUniform("DynamicTransforms", dynamicTransforms);
			renderPass.bindTexture("Sampler0", tiltrosSkyTexture.getTextureView(), tiltrosSkyTexture.getSampler());
			renderPass.setVertexBuffer(0, skyBuffer.slice());
			renderPass.setIndexBuffer(indexBuffer, autoIndices.type());
			renderPass.drawIndexed(36, 1, 0, 0, 0);
		}
	}
}