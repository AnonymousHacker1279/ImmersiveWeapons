package tech.anonymoushacker1279.immersiveweapons.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.menu.TeslaSynthesizerMenu;

public class TeslaSynthesizerScreen extends AbstractContainerScreen<TeslaSynthesizerMenu> {

	private static final ResourceLocation GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/gui/container/tesla_synthesizer.png");


	public TeslaSynthesizerScreen(TeslaSynthesizerMenu container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		titleLabelX = 75;
		titleLabelY = 18;
	}


	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		int i = leftPos;
		int j = topPos;
		guiGraphics.blit(RenderType::guiTextured, GUI_TEXTURE, i, j, 0, 0, imageWidth, imageHeight, 256, 256);
		if (menu.isBurning()) {
			int k = menu.getBurnLeftScaled();
			guiGraphics.blit(RenderType::guiTextured, GUI_TEXTURE, i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1, 256, 256);
		}

		int l = menu.getCookProgressionScaled();
		guiGraphics.blit(RenderType::guiTextured, GUI_TEXTURE, i + 79, j + 34, 176, 14, l + 1, 16, 256, 256);
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		renderTooltip(guiGraphics, mouseX, mouseY);
	}
}