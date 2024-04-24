package tech.anonymoushacker1279.immersiveweapons.client.gui.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
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
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		super.render(guiGraphics, mouseX, mouseY, partialTick);
		renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
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

		List<StarForgeRecipe> availableRecipes = menu.availableRecipes;

		// Render the scrollbar
		int handleHeight = 15;
		int scrollbarY = topPos + 13;
		int scrollbarHeight = 56;
		int handleY = scrollbarY + Mth.clamp((menu.getMenuSelectionIndex() * (scrollbarHeight - handleHeight) / Mth.clamp((availableRecipes.size() - 1), 1, 9999)), 0, scrollbarHeight - handleHeight);
		int scrollbarX = leftPos + 119;
		int scrollbarTextureY = mouseX >= scrollbarX && mouseY >= scrollbarY && mouseX < scrollbarX + 10 && mouseY < scrollbarY + scrollbarHeight ? 244 : 232;

		// Check if there is a recipe currently being crafted, if so, render the scrollbar as grayed out
		if (menu.getSmeltTime() > 0) {
			scrollbarTextureY = 244;
		}

		guiGraphics.blit(GUI_TEXTURE, scrollbarX, handleY, scrollbarTextureY, 0, 11, handleHeight);

		// Render the selection area and the entries
		int leftPosOffset = leftPos + 60;
		int topPosOffset = topPos + 17;
		int startIndexOffset = menu.getMenuSelectionIndex() + 2; // Two entries visible at a time

		for (int i = menu.getMenuSelectionIndex(); i < startIndexOffset; ++i) {
			if (i != -1 && i < availableRecipes.size()) {
				int indexOffset = i - menu.getMenuSelectionIndex();
				int y = topPosOffset + indexOffset * 24; // Each entry is 24px tall
				int vOffset = 166; // Default background for an entry

				if (i == menu.getMenuSelectionIndex()) {
					vOffset += 24; // Selected entry
				} else if (mouseX >= leftPosOffset && mouseY >= y && mouseX < leftPosOffset + 55 && mouseY < y + 24) {
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

		// Check if the currently selected recipe is valid, if so, render the crafting icon
		if (isRecipeValid(availableRecipes)) {
			// If there is a recipe being crafted, the crafting icon should be grayed out
			if (menu.getSmeltTime() > 0) {
				guiGraphics.blit(GUI_TEXTURE, leftPos + 144, topPos + 8, 188, 15, 15, 15);
			} else {
				guiGraphics.blit(GUI_TEXTURE, leftPos + 144, topPos + 8, 188, 0, 15, 15);
			}
		}
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		// Check if the mouse click is within the selection area
		if (mouseX >= leftPos + 61 && mouseX < leftPos + 116 && mouseY >= topPos + 17 && mouseY < topPos + 65) {
			// Check if the forge is currently active
			if (menu.getSmeltTime() == 0) {
				// Calculate which entry is clicked
				int clickedEntry = menu.getMenuSelectionIndex() + (int) ((mouseY - (topPos + 17)) / 24);
				menu.setMenuSelectionIndex(clickedEntry, false);
				return true;
			}
		}

		// Check if the mouse click is within the scrollbar area
		if (mouseX >= leftPos + 119 && mouseX < leftPos + 119 + 11 && mouseY >= topPos + 13 && mouseY < topPos + 13 + 56) {
			return true;
		}

		// Check if the mouse click is within the crafting icon area
		if (isOverCraftingIcon((int) mouseX, (int) mouseY)) {
			// Check if the currently selected recipe is valid
			if (isRecipeValid(menu.availableRecipes)) {
				menu.setMenuSelectionIndex(menu.getMenuSelectionIndex(), true);
			}
			return true;
		}

		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY) {
		// Adjust the scroll offset based on the scroll amount
		if (menu.getSmeltTime() == 0) {
			menu.setMenuSelectionIndex(menu.getMenuSelectionIndex() - (int) deltaY, false);
		}

		return true;
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
		// Check if the scrollbar is being dragged
		if (isOverScrollbar((int) mouseX, (int) mouseY) && menu.getSmeltTime() == 0 && Mth.abs((float) dragY) >= 1.5f) {
			// Calculate the new menu selection index
			int newMenuSelectionIndex = (int) ((mouseY - (topPos + 13)) / (56 - 15) * menu.availableRecipes.size());
			menu.setMenuSelectionIndex(newMenuSelectionIndex, false);
			return true;
		}

		return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
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

		// Render the crafting progress tooltip
		if (isOverCraftingIcon(x, y) && menu.getSmeltTime() > 0) {
			// Format the time remaining as seconds
			String timeRemaining = String.format("%.1f", menu.getSmeltTime() / 20.0f);
			guiGraphics.renderTooltip(
					font,
					Component.translatable("container.immersiveweapons.star_forge.craft_progress", timeRemaining + "s"),
					x,
					y
			);
		}
	}

	/**
	 * Check if the mouse is over the scrollbar region.
	 *
	 * @param x the x position of the mouse
	 * @param y the y position of the mouse
	 * @return boolean
	 */
	private boolean isOverScrollbar(int x, int y) {
		return x >= leftPos + 119 && x < leftPos + 119 + 11 && y >= topPos + 13 && y < topPos + 13 + 56;
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

	/**
	 * Check if the mouse is over the crafting icon region.
	 *
	 * @param x the x position of the mouse
	 * @param y the y position of the mouse
	 * @return boolean
	 */
	private boolean isOverCraftingIcon(int x, int y) {
		return x >= leftPos + 144 && x <= leftPos + 159 && y >= topPos + 8 && y <= topPos + 23;
	}

	private boolean isRecipeValid(List<StarForgeRecipe> availableRecipes) {
		if (!availableRecipes.isEmpty() && menu.getMenuSelectionIndex() < availableRecipes.size() && menu.getTemperature() == 1000) {
			StarForgeRecipe recipe = availableRecipes.get(menu.getMenuSelectionIndex());

			return recipe != null && recipe.matches(menu.container);
		}

		return false;
	}
}