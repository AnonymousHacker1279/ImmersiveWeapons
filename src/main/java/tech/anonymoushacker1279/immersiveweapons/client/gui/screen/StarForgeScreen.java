package tech.anonymoushacker1279.immersiveweapons.client.gui.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.blockentity.StarForgeBlockEntity;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.StarForgeRecipe;
import tech.anonymoushacker1279.immersiveweapons.menu.StarForgeMenu;

import java.util.List;

public class StarForgeScreen extends AbstractContainerScreen<StarForgeMenu> {

	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(ImmersiveWeapons.MOD_ID + ":textures/gui/container/star_forge.png");
	private static final Component SI_TOOLTIP_INACTIVE = Component.translatable("container.immersiveweapons.star_forge.si_inactive");
	private static final Component SI_TOOLTIP_ACTIVE = Component.translatable("container.immersiveweapons.star_forge.si_active");

	public StarForgeScreen(StarForgeMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
		super(pMenu, pPlayerInventory, pTitle);
		titleLabelY = 5;
		titleLabelX = 31;
	}

	@Override
	public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
		super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
		renderTooltip(guiGraphics, pMouseX, pMouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pX, int pY) {
		guiGraphics.blit(GUI_TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);

		// Render the solar indicator (12x12 px) at (37, 28)
		// The inactive indicator is at (177, 17) and the active is at (177, 29)
		if (menu.hasSolarEnergy()) {
			guiGraphics.blit(GUI_TEXTURE, leftPos + 36, topPos + 27, 176, 12, 12, 12);
		} else {
			guiGraphics.blit(GUI_TEXTURE, leftPos + 36, topPos + 27, 176, 0, 12, 12);
		}

		// Render the temperature bar (12x38 px) at (11, 10)
		// Start from the bottom up, increasing on the y-axis
		// The bar is at (177, 25)
		int temperature = menu.getTemperature();
		// Determine the number of pixels to render (the temp range is 0-1000)
		int pixels = (int) Math.ceil(temperature / 1000.0 * 38);
		// Calculate the y-coordinate for the source texture and the destination rectangle
		int sourceY = 24 + 38 - pixels;
		int destY = topPos + 9 + 38 - pixels;
		// Render the bar
		guiGraphics.blit(GUI_TEXTURE, leftPos + 10, destY, 176, sourceY, 12, pixels);

		// Render the selection area and the entries
		int leftPosOffset = leftPos + 60;
		int topPosOffset = topPos + 17;
		int startIndexOffset = menu.getMenuSelectionIndex() + 2; // Two entries visible at a time

		List<StarForgeRecipe> availableRecipes = StarForgeMenu.AVAILABLE_RECIPES;

		for (int i = menu.getMenuSelectionIndex(); i < startIndexOffset; ++i) {
			if (i != -1 && i < availableRecipes.size()) {
				int indexOffset = i - menu.getMenuSelectionIndex();
				int y = topPosOffset + indexOffset * 24; // Each entry is 24px tall
				int vOffset = 166; // Default background for an entry

				if (i == menu.getMenuSelectionIndex()) {
					vOffset += 24; // Selected entry
				} else if (pX >= leftPosOffset && pY >= y && pX < leftPosOffset + 55 && pY < y + 24) {
					vOffset += 48; // Hovered entry
				}

				guiGraphics.blit(GUI_TEXTURE, leftPosOffset, y, 0, vOffset, 56, 24);
			}
		}

		// Render the item icons for the ingot/material and the result
		for (int i = menu.getMenuSelectionIndex(); i < startIndexOffset; ++i) {
			int indexOffset = i - menu.getMenuSelectionIndex();
			int y = topPosOffset + indexOffset * 24; // Each entry is 24px tall

			// Get the recipe
			if (!availableRecipes.isEmpty() && i < availableRecipes.size()) {
				StarForgeRecipe recipe = availableRecipes.get(i);

				ItemStack ingot = recipe.getIngot();
				ItemStack secondaryMaterial = recipe.getSecondaryMaterial();
				ItemStack result = recipe.result();

				// Render the items
				guiGraphics.renderItem(ingot, leftPosOffset + 3, y + 3);
				guiGraphics.renderItemDecorations(font, ingot, leftPosOffset + 3, y + 3);

				guiGraphics.renderItem(secondaryMaterial, leftPosOffset + 21, y + 3);
				guiGraphics.renderItemDecorations(font, secondaryMaterial, leftPosOffset + 21, y + 3);

				guiGraphics.renderItem(result, leftPosOffset + 39, y + 3);
			}
		}

		// Render the scrollbar
		int handleHeight = 15;
		int scrollbarY = topPos + 13;
		int scrollbarHeight = 56;
		int handleY = scrollbarY + Mth.clamp((menu.getMenuSelectionIndex() * (scrollbarHeight - handleHeight) / (availableRecipes.size() - 1)), 0, scrollbarHeight - handleHeight);
		int scrollbarX = leftPos + 119;
		int scrollbarTextureY = pX >= scrollbarX && pY >= scrollbarY && pX < scrollbarX + 10 && pY < scrollbarY + scrollbarHeight ? 244 : 232;

		guiGraphics.blit(GUI_TEXTURE, scrollbarX, handleY, scrollbarTextureY, 0, 11, handleHeight);
	}

	@Override
	public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
		// Check if the mouse click is within the selection area
		if (pMouseX >= leftPos + 61 && pMouseX < leftPos + 116 && pMouseY >= topPos + 17 && pMouseY < topPos + 65) {
			// Calculate which entry is clicked
			int clickedEntry = menu.getMenuSelectionIndex() + (int) ((pMouseY - (topPos + 17)) / 24);
			menu.setMenuSelectionIndex(clickedEntry);
			return true;
		}

		// Check if the mouse click is within the scrollbar area
		if (pMouseX >= leftPos + 119 && pMouseX < leftPos + 119 + 11 && pMouseY >= topPos + 13 && pMouseY < topPos + 13 + 56) {
			return true;
		}

		return super.mouseClicked(pMouseX, pMouseY, pButton);
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY) {
		// Adjust the scroll offset based on the scroll amount
		int scrollAmount = (int) deltaY;
		menu.setMenuSelectionIndex(menu.getMenuSelectionIndex() - scrollAmount);
		return true;
	}

	@Override
	public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
		// Check if the scrollbar is being dragged
		if (pMouseX >= leftPos + 119 && pMouseX < leftPos + 119 + 11 && pMouseY >= topPos + 13 && pMouseY < topPos + 13 + 56) {
			// Calculate the new menu selection index
			int newMenuSelectionIndex = (int) ((pMouseY - (topPos + 13)) / (56 - 15) * ((StarForgeBlockEntity) menu.container).getAvailableRecipes(
					menu.ingotInputSlot.getItem(),
					menu.secondaryInputSlot.getItem()).size());
			menu.setMenuSelectionIndex(newMenuSelectionIndex);
			return true;
		}

		return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
	}

	@Override
	protected void renderTooltip(GuiGraphics guiGraphics, int x, int y) {
		super.renderTooltip(guiGraphics, x, y);

		// Render the solar indicator tooltip
		if (isOverSolarIndicator(x, y)) {
			guiGraphics.renderTooltip(
					font,
					menu.hasSolarEnergy() ? SI_TOOLTIP_ACTIVE : SI_TOOLTIP_INACTIVE,
					x,
					y
			);
		}

		// Render the temperature bar tooltip
		if (isOverTemperatureBar(x, y)) {
			// Format the temperature with one decimal place
			String temperature = String.format("%.1f", menu.getTemperature() / 10.0f);
			guiGraphics.renderTooltip(
					font,
					Component.translatable("container.immersiveweapons.star_forge.temperature", temperature + "%"),
					x,
					y
			);
		}
	}

	/**
	 * Check if the mouse is over the solar indicator region.
	 *
	 * @param x the x position of the mouse
	 * @param y the y position of the mouse
	 * @return boolean
	 */
	private boolean isOverSolarIndicator(int x, int y) {
		return x >= leftPos + 37 && x <= leftPos + 47 && y >= topPos + 28 && y <= topPos + 38;
	}

	/**
	 * Check if the mouse is over the temperature bar region.
	 *
	 * @param x the x position of the mouse
	 * @param y the y position of the mouse
	 * @return boolean
	 */
	private boolean isOverTemperatureBar(int x, int y) {
		return x >= leftPos + 11 && x <= leftPos + 23 && y >= topPos + 10 && y <= topPos + 48;
	}
}