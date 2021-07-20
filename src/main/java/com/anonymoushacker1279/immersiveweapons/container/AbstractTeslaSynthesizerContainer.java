package com.anonymoushacker1279.immersiveweapons.container;

import com.anonymoushacker1279.immersiveweapons.container.slot.TeslaSynthesizerFuelSlot;
import com.anonymoushacker1279.immersiveweapons.container.slot.TeslaSynthesizerResultSlot;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.tileentity.AbstractTeslaSynthesizerTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class AbstractTeslaSynthesizerContainer extends Container {

	protected final IInventory teslaSynthesizerInventory;
	protected final World world;
	private final IIntArray teslaSynthesizerData;

	/**
	 * Constructor for AbstractTeslaSynthesizerContainer.
	 * @param containerType the <code>ContainerType</code> of the container
	 * @param id the ID of the container
	 * @param playerInventory the <code>PlayerInventory</code> instance
	 */
	protected AbstractTeslaSynthesizerContainer(ContainerType<?> containerType, int id, PlayerInventory playerInventory) {
		this(containerType, id, playerInventory, new Inventory(5), new IntArray(5));
	}

	/**
	 * Constructor for AbstractTeslaSynthesizerContainer.
	 * @param containerType the <code>ContainerType</code> of the container
	 * @param id the ID of the container
	 * @param playerInventory the <code>PlayerInventory</code> instance
	 * @param iInventory the <code>IInventory</code> instance
	 * @param iIntArray the <code>IIntArray</code> instance
	 */
	protected AbstractTeslaSynthesizerContainer(ContainerType<?> containerType, int id, PlayerInventory playerInventory, IInventory iInventory, IIntArray iIntArray) {
		super(containerType, id);
		checkContainerSize(iInventory, 5);
		checkContainerDataCount(iIntArray, 4);
		teslaSynthesizerInventory = iInventory;
		teslaSynthesizerData = iIntArray;
		world = playerInventory.player.level;
		// First ingredient slot
		addSlot(new Slot(iInventory, 0, 6, 17) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() == Items.STONE;
			}
		});
		// Second ingredient slot
		addSlot(new Slot(iInventory, 1, 31, 17) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() == Items.LAPIS_LAZULI;
			}
		});
		// Third ingredient slot
		addSlot(new Slot(iInventory, 2, 56, 17) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.getItem() == DeferredRegistryHandler.CONDUCTIVE_ALLOY.get();
			}
		});
		// Fuel slot
		addSlot(new TeslaSynthesizerFuelSlot(this, iInventory, 3, 56, 53));
		// Result slot
		addSlot(new TeslaSynthesizerResultSlot(playerInventory.player, iInventory, 4, 116, 35));

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

	@OnlyIn(Dist.CLIENT)
	public int getSize() {
		return 5;
	}

	/**
	 * Determines whether the player can use this container.
	 * @param playerIn the <code>PlayerEntity</code> being checked
	 * @return boolean
	 */
	@Override
	public boolean stillValid(PlayerEntity playerIn) {
		return teslaSynthesizerInventory.stillValid(playerIn);
	}

	/**
	 * Handle shift-clicking stacks from slots.
	 * @param playerIn the <code>PlayerEntity</code> instance
	 * @param index the slot index
	 * @return ItemStack
	 */
	@Override
	public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = slots.get(index);
		if (slot != null && slot.hasItem()) {
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
	 * @param stack the <code>ItemStack</code> being checked
	 * @return
	 */
	public boolean isFuel(ItemStack stack) {
		return AbstractTeslaSynthesizerTileEntity.isFuel(stack);
	}

	/**
	 * Get the current progression.
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
	 * @return boolean
	 */
	@OnlyIn(Dist.CLIENT)
	public boolean isBurning() {
		return teslaSynthesizerData.get(0) > 0;
	}

}