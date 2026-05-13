package tech.anonymoushacker1279.immersiveweapons.client.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
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

	private static final Identifier BG_LOCATION = Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/gui/container/small_parts_table.png");
	private static final int SCROLLER_WIDTH = 12;
	private static final int SCROLLER_HEIGHT = 15;
	private static final int PATTERN_IMAGE_SIZE = 16;
	private static final int SCROLLER_FULL_HEIGHT = 56;
	private static final int PATTERNS_X = 64;
	private static final int PATTERNS_Y = 17;
	private static int TOTAL_PATTERN_ROWS = 0;
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
	public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
		super.extractBackground(graphics, mouseX, mouseY, a);

		int leftPos = this.leftPos;
		int topPos = this.topPos;
		graphics.blit(RenderPipelines.GUI_TEXTURED, BG_LOCATION, leftPos, topPos, 0, 0, imageWidth, imageHeight, 256, 256);
		Slot materialSlot = menu.getMaterialSlot();

		if (!materialSlot.hasItem()) {
			graphics.blit(RenderPipelines.GUI_TEXTURED, BG_LOCATION, leftPos + materialSlot.x, topPos + materialSlot.y, imageWidth, 0, 16, 16, 256, 256);
		}

		int scrollOffset = (int) (41.0F * scrollOffs);
		graphics.blit(RenderPipelines.GUI_TEXTURED, BG_LOCATION, leftPos + 119, topPos + PATTERNS_Y - 4 + scrollOffset,
				232 + (displayPatterns ? 0 : SCROLLER_WIDTH),
				0, SCROLLER_WIDTH, SCROLLER_HEIGHT, 256, 256);

		if (resultPatterns != null) {
			if (menu.getSelectedPartsPatternIndex() > 0) {
				ItemStack material = new ItemStack(resultPatterns.get(menu.getSelectedPartsPatternIndex() - 1));
				graphics.item(material, leftPos + 143, topPos + 19);
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
				int vOffset = imageHeight;
				if (i == menu.getSelectedPartsPatternIndex()) {
					vOffset += PATTERN_IMAGE_SIZE;
				} else if (mouseX >= x && mouseY >= y && mouseX < x + PATTERN_IMAGE_SIZE && mouseY < y + PATTERN_IMAGE_SIZE) {
					vOffset += 32;
				}

				graphics.blit(RenderPipelines.GUI_TEXTURED, BG_LOCATION, x, y, 0, vOffset, PATTERN_IMAGE_SIZE, PATTERN_IMAGE_SIZE, 256, 256);
				if (startIndex + indexOffset - 1 <= resultPatterns.size() - 1) {
					graphics.item(new ItemStack(resultPatterns.get(startIndex + indexOffset - 1)), x, y);
				}
			}
		}
	}

	@Override
	public boolean mouseClicked(MouseButtonEvent event, boolean flag) {
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
				double x = event.x() - (double) (leftPosOffset + indexOffset % 3 * PATTERN_IMAGE_SIZE);
				double y = event.y() - (double) (rightPosOffset + indexOffset / 3 * PATTERN_IMAGE_SIZE);
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
			if (event.x() >= (double) leftPosOffset
					&& event.x() < (double) (leftPosOffset + SCROLLER_WIDTH)
					&& event.y() >= (double) rightPosOffset
					&& event.y() < (double) (rightPosOffset + SCROLLER_FULL_HEIGHT)) {
				scrolling = true;
			}
		}

		return super.mouseClicked(event, flag);
	}

	@Override
	public boolean mouseDragged(MouseButtonEvent event, double dragX, double dragY) {
		if (scrolling && displayPatterns) {
			int topPosOffset = topPos + PATTERNS_Y;
			int maxHeight = topPosOffset + SCROLLER_FULL_HEIGHT;
			scrollOffs = ((float) event.y() - topPosOffset - 7.5F) / ((maxHeight - topPosOffset) - 15.0F);
			scrollOffs = Mth.clamp(scrollOffs, 0.0F, 1.0F);
			int i = (int) ((scrollOffs * TOTAL_PATTERN_ROWS) + 0.5D);
			if (i < 0) {
				i = 0;
			}

			startIndex = 1 + i * 3;
			return true;
		} else {
			return super.mouseDragged(event, dragX, dragY);
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