package tech.anonymoushacker1279.immersiveweapons.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.menu.TeslaSynthesizerMenu;

public class TeslaSynthesizerScreen extends AbstractContainerScreen<TeslaSynthesizerMenu> {

	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/gui/container/tesla_synthesizer.png");


	public TeslaSynthesizerScreen(TeslaSynthesizerMenu container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		titleLabelX = 75;
		titleLabelY = 18;
	}


	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, GUI_TEXTURE);
		int i = leftPos;
		int j = topPos;
		guiGraphics.blit(GUI_TEXTURE, i, j, 0, 0, imageWidth, imageHeight);
		if (menu.isBurning()) {
			int k = menu.getBurnLeftScaled();
			guiGraphics.blit(GUI_TEXTURE, i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
		}

		int l = menu.getCookProgressionScaled();
		guiGraphics.blit(GUI_TEXTURE, i + 79, j + 34, 176, 14, l + 1, 16);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int x, int y) {
		RenderSystem.disableBlend();
		super.renderLabels(guiGraphics, x, y);
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		renderTooltip(guiGraphics, mouseX, mouseY);
	}
}