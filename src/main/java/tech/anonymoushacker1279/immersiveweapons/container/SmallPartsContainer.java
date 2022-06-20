package tech.anonymoushacker1279.immersiveweapons.container;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.block.crafting.small_parts.SmallPartsCraftables;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

import java.util.List;

public class SmallPartsContainer extends AbstractContainerMenu {

	private static final int INV_SLOT_START = 4;
	private static final int INV_SLOT_END_USE_ROW_SLOT_START = 31;
	private static final int USE_ROW_SLOT_END = 38;
	private final ContainerLevelAccess access;
	final DataSlot selectedPartsPatternIndex = DataSlot.standalone();
	Runnable slotUpdateListener = () -> {
	};
	final Slot materialSlot;
	private final Slot resultSlot;
	long lastSoundTime;
	private final Container inputContainer = new SimpleContainer(1) {
		@Override
		public void setChanged() {
			super.setChanged();
			slotsChanged(this);
			slotUpdateListener.run();
		}
	};
	private final Container outputContainer = new SimpleContainer(1) {
		@Override
		public void setChanged() {
			super.setChanged();
			slotUpdateListener.run();
		}
	};

	public SmallPartsContainer(int containerID, Inventory inventory) {
		this(containerID, inventory, ContainerLevelAccess.NULL);
	}

	public SmallPartsContainer(int containerID, Inventory inventory, ContainerLevelAccess levelAccess) {
		super(DeferredRegistryHandler.SMALL_PARTS_TABLE_CONTAINER.get(), containerID);
		access = levelAccess;
		materialSlot = addSlot(new Slot(inputContainer, 0, 23, 36) {
			/**
			 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
			 */
			@Override
			public boolean mayPlace(@NotNull ItemStack stack) {
				return true;
			}
		});
		resultSlot = addSlot(new Slot(outputContainer, 0, 143, 58) {
			/**
			 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
			 */
			@Override
			public boolean mayPlace(@NotNull ItemStack stack) {
				return false;
			}

			@Override
			public void onTake(@NotNull Player player, @NotNull ItemStack stack) {
				materialSlot.remove(1);
				if (!materialSlot.hasItem()) {
					selectedPartsPatternIndex.set(0);
				}

				levelAccess.execute((level, pos) -> {
					long gameTime = level.getGameTime();
					if (lastSoundTime != gameTime) {
						level.playSound(null, pos, DeferredRegistryHandler.SMALL_PARTS_TABLE_USED.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
						lastSoundTime = gameTime;
					}

				});
				super.onTake(player, stack);
			}
		});

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k) {
			addSlot(new Slot(inventory, k, 8 + k * 18, 142));
		}

		addDataSlot(selectedPartsPatternIndex);
	}

	public int getSelectedPartsPatternIndex() {
		return selectedPartsPatternIndex.get();
	}

	/**
	 * Determines whether supplied player can use this container
	 */
	@Override
	public boolean stillValid(@NotNull Player pPlayer) {
		return stillValid(access, pPlayer, DeferredRegistryHandler.SMALL_PARTS_TABLE.get());
	}

	@Override
	public boolean clickMenuButton(@NotNull Player pPlayer, int pId) {
		if (pId > 0 && pId <= SmallPartsCraftables.ALL_CRAFTABLES.size()) {
			selectedPartsPatternIndex.set(pId);
			setupResultSlot();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Callback for when the crafting matrix is changed.
	 */
	@Override
	public void slotsChanged(@NotNull Container pInventory) {
		resultSlot.set(ItemStack.EMPTY);
		selectedPartsPatternIndex.set(0);

		setupResultSlot();
		broadcastChanges();
	}

	public void registerUpdateListener(Runnable runnable) {
		slotUpdateListener = runnable;
	}

	/**
	 * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
	 * inventory and the other inventory(s).
	 */
	@Override
	public @NotNull ItemStack quickMoveStack(@NotNull Player pPlayer, int pIndex) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = slots.get(pIndex);
		if (slot.hasItem()) {
			ItemStack slotStack = slot.getItem();
			stack = slotStack.copy();
			if (pIndex == resultSlot.index) {
				if (!moveItemStackTo(slotStack, INV_SLOT_START, USE_ROW_SLOT_END, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickCraft(slotStack, stack);
			} else if (pIndex != materialSlot.index) {
				if (!moveItemStackTo(slotStack, materialSlot.index, materialSlot.index + 1, false)) {
					return ItemStack.EMPTY;
				} else if (pIndex >= INV_SLOT_START && pIndex < INV_SLOT_END_USE_ROW_SLOT_START) {
					if (!moveItemStackTo(slotStack, INV_SLOT_END_USE_ROW_SLOT_START, USE_ROW_SLOT_END, false)) {
						return ItemStack.EMPTY;
					}
				} else if (pIndex >= INV_SLOT_END_USE_ROW_SLOT_START && pIndex < USE_ROW_SLOT_END && !moveItemStackTo(slotStack, INV_SLOT_START, INV_SLOT_END_USE_ROW_SLOT_START, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!moveItemStackTo(slotStack, INV_SLOT_START, USE_ROW_SLOT_END, false)) {
				return ItemStack.EMPTY;
			}

			if (slotStack.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (slotStack.getCount() == stack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(pPlayer, slotStack);
		}

		return stack;
	}

	/**
	 * Called when the container is closed.
	 */
	@Override
	public void removed(@NotNull Player pPlayer) {
		super.removed(pPlayer);
		access.execute((level, pos) -> clearContainer(pPlayer, inputContainer));
	}

	private void setupResultSlot() {
		ItemStack material = materialSlot.getItem();
		List<Item> resultPatterns = SmallPartsCraftables.getAvailableCraftables(material);

		if (selectedPartsPatternIndex.get() > 0 && resultPatterns.size() >= selectedPartsPatternIndex.get() - 1) {
			if (!material.isEmpty()) {
				resultSlot.set(new ItemStack(resultPatterns.get(selectedPartsPatternIndex.get() - 1)));
			}
		}

	}

	public Slot getMaterialSlot() {
		return materialSlot;
	}
}