package com.anonymoushacker1279.immersiveweapons.container.slot;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class TeslaSynthesizerResultSlot extends Slot {

	private final PlayerEntity player;
	private int removeCount;

	/**
	 * Constructor for TeslaSynthesizerResultSlot.
	 * @param player the <code>PlayerEntity</code> instance
	 * @param inventoryIn the <code>IInventory</code> of the tile entity
	 * @param slotIndex the slot index
	 * @param xPosition the X position of the slot
	 * @param yPosition the Y position of the slot
	 */
	public TeslaSynthesizerResultSlot(PlayerEntity player, IInventory inventoryIn, int slotIndex, int xPosition, int yPosition) {
		super(inventoryIn, slotIndex, xPosition, yPosition);
		this.player = player;
	}

	/**
	 * Check if the stack is allowed to be placed in this slot.
	 * @param stack the <code>ItemStack</code> to be checked
	 * @return boolean
	 */
	@Override
	public boolean mayPlace(ItemStack stack) {
		return false;
	}

	/**
	 * Decrease the size of the stack in slot by the specified amount. Returns the new stack.
	 * @param amount the amount to reduce the stack by
	 * @return ItemStack
	 */
	@Override
	public ItemStack remove(int amount) {
		if (hasItem()) {
			removeCount += Math.min(amount, getItem().getCount());
		}

		return super.remove(amount);
	}

	/**
	 * Runs when the stack is taken from the tile entity.
	 * @param player the <code>PlayerEntity</code> instance
	 * @param stack the <code>ItemStack</code> being taken
	 * @return ItemStack
	 */
	@Override
	public ItemStack onTake(PlayerEntity player, ItemStack stack) {
		checkTakeAchievements(stack);
		super.onTake(player, stack);
		return stack;
	}
}