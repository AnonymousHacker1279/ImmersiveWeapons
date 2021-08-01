package com.anonymoushacker1279.immersiveweapons.client.gui.screen;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.container.TeslaSynthesizerContainer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class TeslaSynthesizerScreen extends AbstractContainerScreen<TeslaSynthesizerContainer> {

	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/gui/container/tesla_synthesizer.png");

	/**
	 * Constructor for TeslaSynthesizerScreen.
	 * @param container a <code>SmallPartsContainer</code> instance
	 * @param playerInventory a <code>PlayerInventory</code> instance
	 * @param title the <code>ITextComponent</code> title for the screen
	 */
	public TeslaSynthesizerScreen(TeslaSynthesizerContainer container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		titleLabelX = 75;
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
	protected void renderBg(@NotNull PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, GUI_TEXTURE);
		int i = leftPos;
		int j = topPos;
		blit(matrixStack, i, j, 0, 0, imageWidth, imageHeight);
		if (menu.isBurning()) {
			int k = menu.getBurnLeftScaled();
			blit(matrixStack, i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
		}

		int l = menu.getCookProgressionScaled();
		blit(matrixStack, i + 79, j + 34, 176, 14, l + 1, 16);
	}

	/**
	 * Render labels on the screen.
	 * @param matrixStack the <code>MatrixStack</code> instance for the screen
	 * @param x the X position to render at
	 * @param y the Y position to render at
	 */
	@Override
	protected void renderLabels(@NotNull PoseStack matrixStack, int x, int y) {
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
	public void render(@NotNull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		renderTooltip(matrixStack, mouseX, mouseY);
	}
}