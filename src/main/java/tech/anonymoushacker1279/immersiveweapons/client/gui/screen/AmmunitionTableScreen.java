package tech.anonymoushacker1279.immersiveweapons.client.gui.screen;

import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.menu.AmmunitionTableMenu;

public class AmmunitionTableScreen extends AbstractContainerScreen<AmmunitionTableMenu> {

	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(ImmersiveWeapons.MOD_ID + ":textures/gui/container/ammunition_table.png");

	public AmmunitionTableScreen(AmmunitionTableMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
		super(pMenu, pPlayerInventory, pTitle);
		titleLabelY = 5;
	}

	@Override
	public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
		super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
		renderTooltip(guiGraphics, pMouseX, pMouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pX, int pY) {
		renderBackground(guiGraphics);
		guiGraphics.blit(GUI_TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);

		/*Render the density scroller
		The scroller itself is at (253, 0) in the texture and is 3x8 pixels
		It should be placed starting at (72, 19) and can be moved to (102, 19)*/
		int scrollOffset = (int) Mth.clamp((menu.getDensityModifier() * 30), 0, 30);
		guiGraphics.blit(GUI_TEXTURE, leftPos + 72 + scrollOffset, topPos + 19, 253, 0, 3, 8);

		// Render the percent modifier next to the scrollbar
		String densityModifier = "+" + String.format("%.0f", menu.getDensityModifier() * 100) + "%";
		font.drawInBatch(densityModifier,
				leftPos + 110,
				topPos + 19,
				0x000000,
				false,
				guiGraphics.pose().last().pose(),
				guiGraphics.bufferSource(),
				DisplayMode.NORMAL,
				0,
				15728880);
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
		if (isOverDensityScrollbar(mouseX, mouseY)) {
			menu.setDensityModifier(menu.getDensityModifier() + (float) delta / 10.0F);
		}

		return super.mouseScrolled(mouseX, mouseY, delta);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
		if (isOverDensityScrollbar(mouseX, mouseY)) {
			menu.setDensityModifier(menu.getDensityModifier() + (float) dragX / 30.0F);
		}

		return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
	}

	/**
	 * Check if the mouse is over the density scrollbar.
	 *
	 * @param mouseX the mouse's X position
	 * @param mouseY the mouse's Y position
	 * @return boolean
	 */
	private boolean isOverDensityScrollbar(double mouseX, double mouseY) {
		return mouseX >= leftPos + 72 && mouseX <= leftPos + 102 && mouseY >= topPos + 19 && mouseY <= topPos + 27;
	}
}