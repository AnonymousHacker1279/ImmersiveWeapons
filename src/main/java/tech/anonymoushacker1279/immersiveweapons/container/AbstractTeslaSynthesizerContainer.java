package tech.anonymoushacker1279.immersiveweapons.container;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.blockentity.AbstractTeslaSynthesizerBlockEntity;
import tech.anonymoushacker1279.immersiveweapons.container.slot.TeslaSynthesizerFuelSlot;
import tech.anonymoushacker1279.immersiveweapons.container.slot.TeslaSynthesizerResultSlot;

public abstract class AbstractTeslaSynthesizerContainer extends AbstractContainerMenu {

	private final Container teslaSynthesizerInventory;
	private final ContainerData teslaSynthesizerData;

	/**
	 * Constructor for AbstractTeslaSynthesizerContainer.
	 *
	 * @param menuType  the <code>MenuType</code> of the container
	 * @param id        the ID of the container
	 * @param inventory the <code>Inventory</code> instance
	 */
	AbstractTeslaSynthesizerContainer(MenuType<?> menuType, int id, Inventory inventory) {
		this(menuType, id, inventory, new SimpleContainer(5), new SimpleContainerData(4));
	}

	/**
	 * Constructor for AbstractTeslaSynthesizerContainer.
	 *
	 * @param menuType      the <code>MenuType</code> of the container
	 * @param id            the ID of the container
	 * @param inventory     the player inventory
	 * @param container     the container
	 * @param containerData the container data
	 */
	AbstractTeslaSynthesizerContainer(MenuType<?> menuType, int id, Inventory inventory, Container container, ContainerData containerData) {
		super(menuType, id);
		checkContainerSize(container, 5);
		checkContainerDataCount(containerData, 4);
		teslaSynthesizerInventory = container;
		teslaSynthesizerData = containerData;
		// First ingredient slot
		addSlot(new Slot(container, 0, 6, 17));
		// Second ingredient slot
		addSlot(new Slot(container, 1, 31, 17));
		// Third ingredient slot
		addSlot(new Slot(container, 2, 56, 17));
		// Fuel slot
		addSlot(new TeslaSynthesizerFuelSlot(this, container, 3, 56, 53));
		// Result slot
		addSlot(new TeslaSynthesizerResultSlot(container, 4, 116, 35));

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

	/**
	 * Determines whether the player can use this container.
	 *
	 * @param playerIn the <code>PlayerEntity</code> being checked
	 * @return boolean
	 */
	@Override
	public boolean stillValid(@NotNull Player playerIn) {
		return teslaSynthesizerInventory.stillValid(playerIn);
	}

	/**
	 * Handle shift-clicking stacks from slots.
	 *
	 * @param playerIn the <code>PlayerEntity</code> instance
	 * @param index    the slot index
	 * @return ItemStack
	 */
	@Override
	public @NotNull ItemStack quickMoveStack(@NotNull Player playerIn, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = slots.get(index);
		if (slot.hasItem()) {
			ItemStack slotItem = slot.getItem();
			itemStack = slotItem.copy();

			if (index == 4) { // Result slot
				// Try moving the result into any of the player inventory slots
				if (!moveItemStackTo(slotItem, 3, 39, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickCraft(slotItem, itemStack);
			} else if (index != 2 && index != 1 && index != 0) { // Anything but the ingredient slots

				// Check if the item is a fuel
				if (isFuel(slotItem)) {
					// Try moving the item into the fuel slot
					if (!moveItemStackTo(slotItem, 3, 3, false)) {
						return ItemStack.EMPTY;
					}
				} else if (!moveItemStackTo(slotItem, 0, 2, false)) {
					return ItemStack.EMPTY;
				} else if (index < 30) {

					if (!moveItemStackTo(slotItem, 30, 39, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index < 39 && !moveItemStackTo(slotItem, 3, 30, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!moveItemStackTo(slotItem, 3, 39, false)) {
				return ItemStack.EMPTY;
			}

			if (slotItem.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (slotItem.getCount() == itemStack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, slotItem);
		}

		return itemStack;
	}

	/**
	 * Check if the given ItemStack is a fuel item.
	 *
	 * @param stack the <code>ItemStack</code> being checked
	 * @return boolean
	 */
	public boolean isFuel(ItemStack stack) {
		return AbstractTeslaSynthesizerBlockEntity.isFuel(stack);
	}

	/**
	 * Get the current progression.
	 *
	 * @return int
	 */
	public int getCookProgressionScaled() {
		int i = teslaSynthesizerData.get(2);
		int j = teslaSynthesizerData.get(3);
		return j != 0 && i != 0 ? i * 24 / j : 0;
	}

	/**
	 * Get the scaled burn time left.
	 *
	 * @return int
	 */
	public int getBurnLeftScaled() {
		int i = teslaSynthesizerData.get(1);
		if (i == 0) {
			i = 200;
		}

		return teslaSynthesizerData.get(0) * 13 / i;
	}

	/**
	 * Check if the fuel is currently burning.
	 *
	 * @return boolean
	 */
	public boolean isBurning() {
		return teslaSynthesizerData.get(0) > 0;
	}
}