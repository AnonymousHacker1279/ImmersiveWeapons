package com.anonymoushacker1279.immersiveweapons.client.renderer.dimension;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.FogRenderer.FogMode;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.ISkyRenderHandler;

public class TiltrosSkyRenderHandler implements ISkyRenderHandler {

	private static final ResourceLocation SKY_LOCATION = new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/environment/tiltros_sky.png");

	@Override
	public void render(int ticks, float partialTicks, PoseStack poseStack, ClientLevel world, Minecraft minecraft) {
		FogRenderer.setupFog(minecraft.gameRenderer.getMainCamera(), FogMode.FOG_SKY, 32.0f, false, partialTicks);

		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.depthMask(false);
		RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
		RenderSystem.setShaderTexture(0, SKY_LOCATION);
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferbuilder = tesselator.getBuilder();

		for (int i = 0; i < 6; ++i) {
			poseStack.pushPose();
			if (i == 1) {
				poseStack.mulPose(Vector3f.XP.rotationDegrees(90.0F)); // North
			}

			if (i == 2) {
				poseStack.mulPose(Vector3f.XP.rotationDegrees(-90.0F)); // South
			}

			if (i == 3) {
				poseStack.mulPose(Vector3f.XP.rotationDegrees(180.0F)); // Up
			}

			if (i == 4) {
				poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F)); // East
			}

			if (i == 5) {
				poseStack.mulPose(Vector3f.ZP.rotationDegrees(-90.0F)); // West
			}

			Matrix4f matrix4f = poseStack.last().pose();
			bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
			bufferbuilder.vertex(matrix4f, -100.0F, -100.0F, -100.0F).uv(0.0F, 0.0F).color(255, 255, 255, 75).endVertex();
			bufferbuilder.vertex(matrix4f, -100.0F, -100.0F, 100.0F).uv(0.0F, 1.0F).color(255, 255, 255, 75).endVertex();
			bufferbuilder.vertex(matrix4f, 100.0F, -100.0F, 100.0F).uv(1.0F, 1.0F).color(255, 255, 255, 75).endVertex();
			bufferbuilder.vertex(matrix4f, 100.0F, -100.0F, -100.0F).uv(1.0F, 0.0F).color(255, 255, 255, 75).endVertex();
			tesselator.end();
			poseStack.popPose();
		}

		RenderSystem.depthMask(true);
		RenderSystem.enableTexture();
		RenderSystem.disableBlend();
	}
}