package tech.anonymoushacker1279.immersiveweapons.client.gui.overlays;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import tech.anonymoushacker1279.immersiveweapons.client.gui.IWOverlays;

public class ScopeOverlay {

	public static void renderOverlay(GuiGraphics gui, int guiWidth, int guiHeight, float scopeScale) {
		float minWidthHeight = (float) Math.min(guiWidth, guiHeight);
		float scale = Math.min((float) guiWidth / minWidthHeight, (float) guiHeight / minWidthHeight) * scopeScale;
		int uWidth = Mth.floor(minWidthHeight * scale);
		int vHeight = Mth.floor(minWidthHeight * scale);
		int x = (guiWidth - uWidth) / 2;
		int y = (guiHeight - vHeight) / 2;
		int x1 = x + uWidth;
		int y1 = y + vHeight;
		int color = ARGB.colorFromFloat(1f, 1.0f, 1.0f, 1.0f);
		gui.blit(RenderPipelines.GUI_TEXTURED, IWOverlays.SCOPE_LOCATION, x, y, 0.0F, 0.0F, uWidth, vHeight, uWidth, vHeight, color);
		gui.fill(RenderPipelines.GUI_TEXTURED, 0, y1, guiWidth, guiHeight, -90);
		gui.fill(RenderPipelines.GUI_TEXTURED, 0, 0, guiWidth, y, -90);
		gui.fill(RenderPipelines.GUI_TEXTURED, 0, y, x, y1, -90);
		gui.fill(RenderPipelines.GUI_TEXTURED, x1, y, guiWidth, y1, -90);
	}
}