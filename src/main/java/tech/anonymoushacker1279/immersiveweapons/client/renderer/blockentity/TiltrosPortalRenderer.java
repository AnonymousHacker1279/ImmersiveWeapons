package tech.anonymoushacker1279.immersiveweapons.client.renderer.blockentity;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.blockentity.TiltrosPortalBlockEntity;

public class TiltrosPortalRenderer<T extends TiltrosPortalBlockEntity> implements BlockEntityRenderer<T> {

	private static final ResourceLocation SKY_LOCATION = new ResourceLocation(ImmersiveWeapons.MOD_ID,
			"textures/environment/tiltros_sky.png");

	public TiltrosPortalRenderer() {
	}

	public void render(T blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
		RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
		RenderSystem.setShaderTexture(0, SKY_LOCATION);
		RenderSystem.enableDepthTest(); // Prevents texture from being rendered through blocks

		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferBuilder = tesselator.getBuilder();

		poseStack.pushPose();
		Matrix4f poseMatrix = poseStack.last().pose();

		// Center the texture
		poseStack.translate(0.5F, 0.0F, 0.5F);

		if (blockEntity.getLevel() == null) {
			return;
		}

		// Move the texture based on the time
		long time = blockEntity.getLevel().getGameTime();
		float i = (time / 100.0F);
		float xOffset = Mth.cos(i * 2);
		float zOffset = Mth.sin(i * 4);

		BlockPos pos = blockEntity.getBlockPos();

		// A scale factor of two makes nearby portal blocks line up, making a continuous image
		float scaleFactor = 2.0F;

		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
		bufferBuilder.vertex(poseMatrix, 0.5F, 0.5F, 0.5F)
				.uv((pos.getX() + xOffset) / scaleFactor, (pos.getZ() + zOffset) / scaleFactor)
				.color(255, 255, 255, 255)
				.endVertex();
		bufferBuilder.vertex(poseMatrix, 0.5F, 0.5F, -0.5F)
				.uv((pos.getX() + xOffset) / scaleFactor, (pos.getZ() + 1.0F + zOffset) / scaleFactor)
				.color(255, 255, 255, 255)
				.endVertex();
		bufferBuilder.vertex(poseMatrix, -0.5F, 0.5F, -0.5F)
				.uv((pos.getX() + 1.0F + xOffset) / scaleFactor, (pos.getZ() + 1.0F + zOffset) / scaleFactor)
				.color(255, 255, 255, 255)
				.endVertex();
		bufferBuilder.vertex(poseMatrix, -0.5F, 0.5F, 0.5F)
				.uv((pos.getX() + 1.0F + xOffset) / scaleFactor, (pos.getZ() + zOffset) / scaleFactor)
				.color(255, 255, 255, 255)
				.endVertex();
		tesselator.end();

		poseStack.popPose();
	}
}