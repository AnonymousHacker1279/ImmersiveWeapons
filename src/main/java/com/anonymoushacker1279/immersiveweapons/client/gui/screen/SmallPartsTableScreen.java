package com.anonymoushacker1279.immersiveweapons.client.gui.screen;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.container.SmallPartsContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class SmallPartsTableScreen extends ContainerScreen<SmallPartsContainer> {
	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/gui/container/small_parts_table.png");

	/**
	 * Constructor for SmallPartsTableScreen.
	 * @param container a <code>SmallPartsContainer</code> instance
	 * @param playerInventory a <code>PlayerInventory</code> instance
	 * @param title the <code>ITextComponent</code> title for the screen
	 */
	public SmallPartsTableScreen(SmallPartsContainer container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title);
		titleLabelX = 60;
		titleLabelY = 18;
	}

	/**
	 * Render the background of the screen.
	 * @param matrixStack the <code>MatrixStack</code> instance for the screen
	 * @param partialTicks the current partial tick
	 * @param mouseX the mouse's X position
	 * @param mouseY the mouse's Y position
	 */
	@Override
	protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
		if (minecraft != null) {
			minecraft.getTextureManager().bind(GUI_TEXTURE);
		}
		int x = (width - imageWidth) / 2;
		int y = (height - imageHeight) / 2;
		blit(matrixStack, x, y, 0, 0, imageWidth, imageHeight);
	}

	/**
	 * Render labels on the screen.
	 * @param matrixStack the <code>MatrixStack</code> instance for the screen
	 * @param x the X position to render at
	 * @param y the Y position to render at
	 */
	@Override
	protected void renderLabels(MatrixStack matrixStack, int x, int y) {
		RenderSystem.disableBlend();
		super.renderLabels(matrixStack, x, y);
	}

	/**
	 * Render the screen.
	 * @param matrixStack the <code>MatrixStack</code> instance for the screen
	 * @param mouseX the mouse's X position
	 * @param mouseY the mouse's Y position
	 * @param partialTicks the current partial tick
	 */
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		renderTooltip(matrixStack, mouseX, mouseY);
	}
}