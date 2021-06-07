package com.anonymoushacker1279.immersiveweapons.container.slot;

import com.anonymoushacker1279.immersiveweapons.container.AbstractTeslaSynthesizerContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class TeslaSynthesizerFuelSlot extends Slot {

	private final AbstractTeslaSynthesizerContainer abstractTeslaSynthesizerContainer;

	public TeslaSynthesizerFuelSlot(AbstractTeslaSynthesizerContainer teslaSynthesizerFuelSlot, IInventory teslaSynthesizerInventory, int slotIndex, int xPos, int yPos) {
		super(teslaSynthesizerInventory, slotIndex, xPos, yPos);
		this.abstractTeslaSynthesizerContainer = teslaSynthesizerFuelSlot;
	}

	/**
	 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
	 */
	@Override
	public boolean mayPlace(ItemStack stack) {
		return this.abstractTeslaSynthesizerContainer.isFuel(stack);
	}

	@Override
	public int getMaxStackSize(ItemStack stack) {
		return super.getMaxStackSize(stack);
	}
}