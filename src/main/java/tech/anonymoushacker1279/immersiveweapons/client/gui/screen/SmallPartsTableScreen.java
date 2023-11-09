package tech.anonymoushacker1279.immersiveweapons.client.gui.screen;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.menu.SmallPartsMenu;

import java.util.ArrayList;
import java.util.List;

public class SmallPartsTableScreen extends AbstractContainerScreen<SmallPartsMenu> {

	private static final ResourceLocation BG_LOCATION = new ResourceLocation(ImmersiveWeapons.MOD_ID + ":textures/gui/container/small_parts_table.png");
	private static int TOTAL_PATTERN_ROWS = 0;
	private static final int SCROLLER_WIDTH = 12;
	private static final int SCROLLER_HEIGHT = 15;
	private static final int PATTERN_IMAGE_SIZE = 16;
	private static final int SCROLLER_FULL_HEIGHT = 56;
	private static final int PATTERNS_X = 64;
	private static final int PATTERNS_Y = 17;
	@Nullable
	private List<Item> resultPatterns = new ArrayList<>(15);
	private ItemStack materialStack = ItemStack.EMPTY;
	private boolean displayPatterns;
	private float scrollOffs;
	private boolean scrolling;
	private int startIndex = 1;

	public SmallPartsTableScreen(SmallPartsMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
		super(pMenu, pPlayerInventory, pTitle);
		pMenu.registerUpdateListener(this::containerChanged);
		titleLabelY = 5;
	}

	@Override
	public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
		super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
		renderTooltip(guiGraphics, pMouseX, pMouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTick, int pX, int pY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, BG_LOCATION);
		int leftPos = this.leftPos;
		int topPos = this.topPos;
		guiGraphics.blit(BG_LOCATION, leftPos, topPos, 0, 0, imageWidth, imageHeight);
		Slot materialSlot = menu.getMaterialSlot();

		if (!materialSlot.hasItem()) {
			guiGraphics.blit(BG_LOCATION, leftPos + materialSlot.x, topPos + materialSlot.y, imageWidth, 0, 16, 16);
		}

		int scrollOffset = (int) (41.0F * scrollOffs);
		guiGraphics.blit(BG_LOCATION, leftPos + 119, topPos + PATTERNS_Y - 4 + scrollOffset,
				232 + (displayPatterns ? 0 : SCROLLER_WIDTH),
				0, SCROLLER_WIDTH, SCROLLER_HEIGHT);

		if (resultPatterns != null) {
			if (menu.getSelectedPartsPatternIndex() > 0) {
				ItemStack material = new ItemStack(resultPatterns.get(menu.getSelectedPartsPatternIndex() - 1));
				guiGraphics.renderItem(material, leftPos + 143, topPos + 19);
			}
		}

		if (displayPatterns && !resultPatterns.isEmpty()) {
			int leftPosOffset = leftPos + PATTERNS_X;
			int topPosOffset = topPos + PATTERNS_Y;
			int startIndexOffset = startIndex + 9;

			for (int i = startIndex; i < startIndexOffset && i <= resultPatterns.size(); ++i) {
				int indexOffset = i - startIndex;
				int x = leftPosOffset + indexOffset % 3 * PATTERN_IMAGE_SIZE;
				int y = topPosOffset + indexOffset / 3 * PATTERN_IMAGE_SIZE;
				RenderSystem.setShaderTexture(0, BG_LOCATION);
				int vOffset = imageHeight;
				if (i == menu.getSelectedPartsPatternIndex()) {
					vOffset += PATTERN_IMAGE_SIZE;
				} else if (pX >= x && pY >= y && pX < x + PATTERN_IMAGE_SIZE && pY < y + PATTERN_IMAGE_SIZE) {
					vOffset += 32;
				}

				guiGraphics.blit(BG_LOCATION, x, y, 0, vOffset, PATTERN_IMAGE_SIZE, PATTERN_IMAGE_SIZE);
				if (startIndex + indexOffset - 1 <= resultPatterns.size() - 1) {
					guiGraphics.renderItem(new ItemStack(resultPatterns.get(startIndex + indexOffset - 1)), x, y);
				}
			}
		}

		Lighting.setupFor3DItems();
	}

	@Override
	public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
		scrolling = false;
		if (displayPatterns) {
			int leftPosOffset = leftPos + PATTERNS_X;
			int rightPosOffset = topPos + PATTERNS_Y;
			int startIndexOffset = 0;
			if (resultPatterns != null) {
				startIndexOffset = startIndex + resultPatterns.size() <= 9 ? resultPatterns.size() + 1 : 9;
			}

			for (int i = startIndex; i < startIndexOffset; ++i) {
				int indexOffset = i - startIndex;
				double x = pMouseX - (double) (leftPosOffset + indexOffset % 3 * PATTERN_IMAGE_SIZE);
				double y = pMouseY - (double) (rightPosOffset + indexOffset / 3 * PATTERN_IMAGE_SIZE);
				if (minecraft != null && minecraft.player != null
						&& x >= 0.0D && y >= 0.0D && x < 16.0D && y < 16.0D
						&& menu.clickMenuButton(minecraft.player, i)) {

					Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_LOOM_SELECT_PATTERN, 1.0F));
					if (minecraft.gameMode != null) {
						minecraft.gameMode.handleInventoryButtonClick((menu).containerId, i);
					}
					return true;
				}
			}

			leftPosOffset = leftPos + 119;
			rightPosOffset = topPos + 9;
			if (pMouseX >= (double) leftPosOffset
					&& pMouseX < (double) (leftPosOffset + SCROLLER_WIDTH)
					&& pMouseY >= (double) rightPosOffset
					&& pMouseY < (double) (rightPosOffset + SCROLLER_FULL_HEIGHT)) {
				scrolling = true;
			}
		}

		return super.mouseClicked(pMouseX, pMouseY, pButton);
	}

	@Override
	public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
		if (scrolling && displayPatterns) {
			int topPosOffset = topPos + PATTERNS_Y;
			int maxHeight = topPosOffset + SCROLLER_FULL_HEIGHT;
			scrollOffs = ((float) pMouseY - topPosOffset - 7.5F) / ((maxHeight - topPosOffset) - 15.0F);
			scrollOffs = Mth.clamp(scrollOffs, 0.0F, 1.0F);
			int i = (int) ((scrollOffs * TOTAL_PATTERN_ROWS) + 0.5D);
			if (i < 0) {
				i = 0;
			}

			startIndex = 1 + i * 3;
			return true;
		} else {
			return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
		}
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY) {
		if (displayPatterns) {
			float scroll = (float) deltaY / (float) TOTAL_PATTERN_ROWS;
			scrollOffs = Mth.clamp(scrollOffs - scroll, 0.0F, 1.0F);
			startIndex = 1 + (int) (scrollOffs * (float) TOTAL_PATTERN_ROWS + 0.5F) * 3;
		}

		return true;
	}

	@Override
	protected boolean hasClickedOutside(double pMouseX, double pMouseY, int pGuiLeft, int pGuiTop, int pMouseButton) {
		return pMouseX < (double) pGuiLeft
				|| pMouseY < (double) pGuiTop
				|| pMouseX >= (double) (pGuiLeft + imageWidth)
				|| pMouseY >= (double) (pGuiTop + imageHeight);
	}

	private void containerChanged() {
		ItemStack materialStack = menu.getMaterialSlot().getItem();
		if (materialStack.isEmpty()) {
			resultPatterns = null;
		} else {
			resultPatterns = menu.getAvailableCraftables(materialStack);
			int BASE_PATTERN_INDEX = 1;
			TOTAL_PATTERN_ROWS = (resultPatterns.size() - BASE_PATTERN_INDEX + 3 - BASE_PATTERN_INDEX) / 3;
		}

		ItemStack materialSlot = menu.getMaterialSlot().getItem();
		if (!ItemStack.matches(materialStack, this.materialStack)) {
			displayPatterns = !materialSlot.isEmpty();
		}

		this.materialStack = materialSlot.copy();
	}
}