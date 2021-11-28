package com.anonymoushacker1279.immersiveweapons.container;

import com.anonymoushacker1279.immersiveweapons.blockentity.AbstractTeslaSynthesizerBlockEntity;
import com.anonymoushacker1279.immersiveweapons.container.slot.TeslaSynthesizerFuelSlot;
import com.anonymoushacker1279.immersiveweapons.container.slot.TeslaSynthesizerResultSlot;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractTeslaSynthesizerContainer extends AbstractContainerMenu {

	private final Container teslaSynthesizerInventory;
	private final ContainerData teslaSynthesizerData;

	/**
	 * Constructor for AbstractTeslaSynthesizerContainer.
	 *
	 * @param containerType   the <code>ContainerType</code> of the container
	 * @param id              the ID of the container
	 * @param playerInventory the <code>PlayerInventory</code> instance
	 */
	AbstractTeslaSynthesizerContainer(MenuType<?> containerType, int id, Inventory playerInventory) {
		this(containerType, id, playerInventory, new SimpleContainer(5), new SimpleContainerData(4));
	}

	/**
	 * Constructor for AbstractTeslaSynthesizerContainer.
	 *
	 * @param containerType   the <code>ContainerType</code> of the container
	 * @param id              the ID of the container
	 * @param playerInventory the <code>PlayerInventory</code> instance
	 * @param iInventory      the <code>IInventory</code> instance
	 * @param iIntArray       the <code>IIntArray</code> instance
	 */
	AbstractTeslaSynthesizerContainer(MenuType<?> containerType, int id, Inventory playerInventory, Container iInventory, ContainerData iIntArray) {
		super(containerType, id);
		checkContainerSize(iInventory, 5);
		checkContainerDataCount(iIntArray, 4);
		teslaSynthesizerInventory = iInventory;
		teslaSynthesizerData = iIntArray;
		// First ingredient slot
		addSlot(new Slot(iInventory, 0, 6, 17));
		// Second ingredient slot
		addSlot(new Slot(iInventory, 1, 31, 17));
		// Third ingredient slot
		addSlot(new Slot(iInventory, 2, 56, 17));
		// Fuel slot
		addSlot(new TeslaSynthesizerFuelSlot(this, iInventory, 3, 56, 53));
		// Result slot
		addSlot(new TeslaSynthesizerResultSlot(iInventory, 4, 116, 35));

		// Player inventory slots
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k) {
			addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
		}

		addDataSlots(iIntArray);
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
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = slots.get(index);
		if (slot.hasItem()) {
			ItemStack itemStack1 = slot.getItem();
			itemstack = itemStack1.copy();
			if (index == 4) {
				if (!moveItemStackTo(itemStack1, 3, 39, true)) {
					return ItemStack.EMPTY;
				}
				slot.onQuickCraft(itemStack1, itemstack);
			} else if (index != 2 && index != 1 && index != 0) {
				if (isFuel(itemStack1)) {
					if (!moveItemStackTo(itemStack1, 3, 4, false)) {
						return ItemStack.EMPTY;
					}
				} else if (!moveItemStackTo(itemStack1, 0, 3, false)) {
					return ItemStack.EMPTY;
				} else if (index < 30) {
					if (!moveItemStackTo(itemStack1, 30, 39, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index < 39 && !moveItemStackTo(itemStack1, 3, 30, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!moveItemStackTo(itemStack1, 3, 39, false)) {
				return ItemStack.EMPTY;
			}

			if (itemStack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (itemStack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemStack1);
		}

		return itemstack;
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
	@OnlyIn(Dist.CLIENT)
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
	@OnlyIn(Dist.CLIENT)
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
	@OnlyIn(Dist.CLIENT)
	public boolean isBurning() {
		return teslaSynthesizerData.get(0) > 0;
	}

}