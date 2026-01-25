package tech.anonymoushacker1279.immersiveweapons.client.gui.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.menu.CelestialAltarMenu;

public class CelestialAltarScreen extends AbstractContainerScreen<CelestialAltarMenu> {

	private static final Identifier GUI_TEXTURE = Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/gui/container/celestial_altar.png");

	public CelestialAltarScreen(CelestialAltarMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		super.render(guiGraphics, mouseX, mouseY, partialTick);
		renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight, 256, 256);

		// Render the fragment cost next to its respective slot
		if (menu.hasInputItem()) {
			guiGraphics.drawString(
					font,
					" : " + menu.getFragmentCost(),
					leftPos + 100,
					topPos + 15,
					0xFF000000,
					false
			);
		}
	}
}