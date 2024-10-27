package tech.anonymoushacker1279.immersiveweapons.client.gui.screen;

import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.menu.CelestialAltarMenu;

public class CelestialAltarScreen extends AbstractContainerScreen<CelestialAltarMenu> {

	private static final ResourceLocation GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/gui/container/celestial_altar.png");

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
		guiGraphics.blit(GUI_TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);

		// Render the fragment cost next to its respective slot
		if (menu.hasInputItem()) {
			font.drawInBatch(" : " + menu.getFragmentCost(),
					leftPos + 100,
					topPos + 15,
					0x000000,
					false,
					guiGraphics.pose().last().pose(),
					guiGraphics.bufferSource(),
					DisplayMode.NORMAL,
					0,
					15728880);
		}
	}
}