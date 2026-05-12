package tech.anonymoushacker1279.immersiveweapons.client.gui.overlays;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.util.Mth;
import tech.anonymoushacker1279.immersiveweapons.client.gui.IWOverlays;

public class ScopeOverlay {

	public static void renderOverlay(GuiGraphicsExtractor gui, int guiWidth, int guiHeight, float scopeScale) {
		float min = Math.min(guiWidth, guiHeight);
		float scale = Math.min(guiWidth / min, guiHeight / min) * scopeScale;
		int uWidth = Mth.floor(min * scale);
		int uHeight = Mth.floor(min * scale);
		int x = (guiWidth - uWidth) / 2;
		int y = (guiHeight - uHeight) / 2;
		int x1 = x + uWidth;
		int y2 = y + uHeight;
		gui.blit(RenderPipelines.GUI_TEXTURED, IWOverlays.SCOPE_LOCATION, x, y, 0.0F, 0.0F, uWidth, uHeight, uWidth, uHeight);
		gui.fill(RenderPipelines.GUI, 0, y2, guiWidth, guiHeight, -16777216);
		gui.fill(RenderPipelines.GUI, 0, 0, guiWidth, y, -16777216);
		gui.fill(RenderPipelines.GUI, 0, y, x, y2, -16777216);
		gui.fill(RenderPipelines.GUI, x1, y, guiWidth, y2, -16777216);
	}
}