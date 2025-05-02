package tech.anonymoushacker1279.immersiveweapons.menu;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.blockentity.TeslaSynthesizerBlockEntity;
import tech.anonymoushacker1279.immersiveweapons.init.MenuTypeRegistry;

public class TeslaSynthesizerMenu extends AbstractContainerMenu {

	private final Container container;
	private final ContainerData containerData;

	// Slot indices for easy reference
	private static final int FIRST_INGREDIENT_SLOT = 0;
	private static final int SECOND_INGREDIENT_SLOT = 1;
	private static final int THIRD_INGREDIENT_SLOT = 2;
	private static final int FUEL_SLOT = 3;
	private static final int RESULT_SLOT = 4;
	private static final int PLAYER_INVENTORY_START = 5;
	private static final int PLAYER_INVENTORY_END = 41;

	/**
	 * Constructor for TeslaSynthesizerMenu.
	 *
	 * @param id        the ID of the container
	 * @param inventory the <code>Inventory</code> instance
	 */
	public TeslaSynthesizerMenu(int id, Inventory inventory) {
		this(id, inventory, new SimpleContainer(5), new SimpleContainerData(4));
	}

	/**
	 * Constructor for TeslaSynthesizerMenu.
	 *
	 * @param id            the ID of the container
	 * @param inventory     the player inventory
	 * @param container     the container
	 * @param containerData the container data
	 */
	public TeslaSynthesizerMenu(int id, Inventory inventory, Container container, ContainerData containerData) {
		super(MenuTypeRegistry.TESLA_SYNTHESIZER_MENU.get(), id);
		checkContainerSize(container, 5);
		checkContainerDataCount(containerData, 4);
		this.container = container;
		this.containerData = containerData;
		// First ingredient slot
		addSlot(new Slot(container, 0, 6, 17));
		// Second ingredient slot
		addSlot(new Slot(container, 1, 31, 17));
		// Third ingredient slot
		addSlot(new Slot(container, 2, 56, 17));
		// Fuel slot
		addSlot(new Slot(container, 3, 56, 53));
		// Result slot
		addSlot(new Slot(container, 4, 116, 35));

		// Player inventory slots
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k) {
			addSlot(new Slot(inventory, k, 8 + k * 18, 142));
		}

		addDataSlots(containerData);
	}

	@Override
	public boolean stillValid(Player player) {
		return container.stillValid(player);
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack newStack = ItemStack.EMPTY;
		Slot slot = slots.get(index);

		if (slot.hasItem()) {
			ItemStack oldStack = slot.getItem();
			newStack = oldStack.copy();

			// If the slot is the result slot
			if (index == RESULT_SLOT) {
				if (!moveItemStackTo(oldStack, PLAYER_INVENTORY_START, PLAYER_INVENTORY_END, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickCraft(oldStack, newStack);
			}
			// If the slot is not in the synthesizer container (in player inventory)
			else if (index >= PLAYER_INVENTORY_START) {
				// If the item is a fuel item
				if (isFuel(oldStack)) {
					if (!moveItemStackTo(oldStack, FUEL_SLOT, FUEL_SLOT + 1, false)) {
						return ItemStack.EMPTY;
					}
				}
				// Try to move to ingredient slots (0-3)
				if (!moveItemStackTo(oldStack, FIRST_INGREDIENT_SLOT, THIRD_INGREDIENT_SLOT + 1, false)) {
					return ItemStack.EMPTY;
				}
				// If not fuel or special ingredient, move between inventory and hotbar
				else if (index < PLAYER_INVENTORY_END - 9) {
					// Move from inventory to hotbar
					if (!moveItemStackTo(oldStack, PLAYER_INVENTORY_END - 9, PLAYER_INVENTORY_END, false)) {
						return ItemStack.EMPTY;
					}
				} else {
					// Move from hotbar to inventory
					if (!moveItemStackTo(oldStack, PLAYER_INVENTORY_START, PLAYER_INVENTORY_END - 9, false)) {
						return ItemStack.EMPTY;
					}
				}
			}
			// If the slot is in the synthesizer container (not result)
			else {
				if (!moveItemStackTo(oldStack, PLAYER_INVENTORY_START, PLAYER_INVENTORY_END, false)) {
					return ItemStack.EMPTY;
				}
			}

			if (oldStack.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (oldStack.getCount() == newStack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(player, oldStack);
		}

		return newStack;
	}

	/**
	 * Check if the given ItemStack is a fuel item.
	 *
	 * @param stack the <code>ItemStack</code> being checked
	 * @return boolean
	 */
	public boolean isFuel(ItemStack stack) {
		return TeslaSynthesizerBlockEntity.isFuel(stack);
	}

	/**
	 * Get the current progression.
	 *
	 * @return int
	 */
	public int getCookProgressionScaled() {
		int i = containerData.get(2);
		int j = containerData.get(3);
		return j != 0 && i != 0 ? i * 24 / j : 0;
	}

	/**
	 * Get the scaled burn time left.
	 *
	 * @return int
	 */
	public int getBurnLeftScaled() {
		int i = containerData.get(1);
		if (i == 0) {
			i = 200;
		}

		return containerData.get(0) * 13 / i;
	}

	/**
	 * Check if the fuel is currently burning.
	 *
	 * @return boolean
	 */
	public boolean isBurning() {
		return containerData.get(0) > 0;
	}
}