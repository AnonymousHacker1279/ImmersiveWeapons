package tech.anonymoushacker1279.immersiveweapons.client.gui.overlays;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.GameRenderer;
import tech.anonymoushacker1279.immersiveweapons.client.gui.IWOverlays;

public class ScopeOverlay {

	public static void renderOverlay(int screenWidth, int screenHeight, float scopeScale) {
		RenderSystem.depthMask(false);
		RenderSystem.setShaderColor(0.0f, 0.0f, 0.0f, 0.1f);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, IWOverlays.SCOPE_LOCATION);
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferbuilder = tesselator.getBuilder();
		float minScreenWidthAndHeight = (float) Math.min(screenWidth, screenHeight);
		float scaled = Math.min((float) screenWidth / minScreenWidthAndHeight, (float) screenHeight / minScreenWidthAndHeight) * scopeScale;
		float scaledMinScreeWidthAndHeight = minScreenWidthAndHeight * scaled;
		float x = ((float) screenWidth - scaledMinScreeWidthAndHeight) / 2.0F;
		float y = ((float) screenHeight - scaledMinScreeWidthAndHeight) / 2.0F;
		float x1 = x + scaledMinScreeWidthAndHeight;
		float y1 = y + scaledMinScreeWidthAndHeight;
		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		bufferbuilder.vertex(x, y1, -90.0D).uv(0.0F, 1.0F).endVertex();
		bufferbuilder.vertex(x1, y1, -90.0D).uv(1.0F, 1.0F).endVertex();
		bufferbuilder.vertex(x1, y, -90.0D).uv(1.0F, 0.0F).endVertex();
		bufferbuilder.vertex(x, y, -90.0D).uv(0.0F, 0.0F).endVertex();
		tesselator.end();
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
		bufferbuilder.vertex(0.0D, screenHeight, -90.0D).color(0, 0, 0, 255).endVertex();
		bufferbuilder.vertex(screenWidth, screenHeight, -90.0D).color(0, 0, 0, 255).endVertex();
		bufferbuilder.vertex(screenWidth, y1, -90.0D).color(0, 0, 0, 255).endVertex();
		bufferbuilder.vertex(0.0D, y1, -90.0D).color(0, 0, 0, 255).endVertex();

		bufferbuilder.vertex(0.0D, y, -90.0D).color(0, 0, 0, 255).endVertex();
		bufferbuilder.vertex(screenWidth, y, -90.0D).color(0, 0, 0, 255).endVertex();
		bufferbuilder.vertex(screenWidth, 0.0D, -90.0D).color(0, 0, 0, 255).endVertex();
		bufferbuilder.vertex(0.0D, 0.0D, -90.0D).color(0, 0, 0, 255).endVertex();
		bufferbuilder.vertex(0.0D, y1, -90.0D).color(0, 0, 0, 255).endVertex();
		bufferbuilder.vertex(x, y1, -90.0D).color(0, 0, 0, 255).endVertex();
		bufferbuilder.vertex(x, y, -90.0D).color(0, 0, 0, 255).endVertex();
		bufferbuilder.vertex(0.0D, y, -90.0D).color(0, 0, 0, 255).endVertex();

		bufferbuilder.vertex(x1, y1, -90.0D).color(0, 0, 0, 255).endVertex();
		bufferbuilder.vertex(screenWidth, y1, -90.0D).color(0, 0, 0, 255).endVertex();
		bufferbuilder.vertex(screenWidth, y, -90.0D).color(0, 0, 0, 255).endVertex();
		bufferbuilder.vertex(x1, y, -90.0D).color(0, 0, 0, 255).endVertex();
		tesselator.end();
		RenderSystem.depthMask(true);
		RenderSystem.enableDepthTest();
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	}
}