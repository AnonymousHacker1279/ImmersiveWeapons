package tech.anonymoushacker1279.immersiveweapons.client.gui.screen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.menu.AmmunitionForgeMenu;

public class AmmunitionForgeScreen extends AbstractContainerScreen<AmmunitionForgeMenu> {

	private static final Identifier GUI_TEXTURE = Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/gui/container/ammunition_forge.png");
	// The flame and arrow are reused from the vanilla furnace
	private static final Identifier LIT_PROGRESS_SPRITE = Identifier.withDefaultNamespace("container/furnace/lit_progress");
	private static final Identifier BURN_PROGRESS_SPRITE = Identifier.withDefaultNamespace("container/furnace/burn_progress");

	public AmmunitionForgeScreen(AmmunitionForgeMenu menu, Inventory playerInventory, Component title) {
		super(menu, playerInventory, title);
	}

	@Override
	protected void init() {
		super.init();
		titleLabelX = (imageWidth - font.width(title)) / 2;
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
		super.extractBackground(graphics, mouseX, mouseY, a);

		graphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight, 256, 256);

		if (menu.isLit()) {
			int litProgressHeight = Mth.ceil(menu.getLitProgress() * 13.0F) + 1;
			graphics.blitSprite(RenderPipelines.GUI_TEXTURED, LIT_PROGRESS_SPRITE, 14, 14, 0, 14 - litProgressHeight,
					leftPos + 56, topPos + 36 + 14 - litProgressHeight, 14, litProgressHeight);
		}

		int burnProgressWidth = Mth.ceil(menu.getCookingProgress() * 24.0F);
		graphics.blitSprite(RenderPipelines.GUI_TEXTURED, BURN_PROGRESS_SPRITE, 24, 16, 0, 0,
				leftPos + 79, topPos + 34, burnProgressWidth, 16);
	}
}
