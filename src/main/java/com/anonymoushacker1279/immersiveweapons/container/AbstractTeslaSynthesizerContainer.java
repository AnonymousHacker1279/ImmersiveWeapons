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
	private final IIntArray teslaSynthesizerData;
	protected final World world;

	protected AbstractTeslaSynthesizerContainer(ContainerType<?> containerType, int id, PlayerInventory playerInventory) {
		this(containerType, id, playerInventory, new Inventory(5), new IntArray(4));
	}

	protected AbstractTeslaSynthesizerContainer(ContainerType<?> containerType, int id, PlayerInventory playerInventory, IInventory iInventory, IIntArray iIntArray) {
		super(containerType, id);
		assertInventorySize(iInventory, 5);
		assertIntArraySize(iIntArray, 4);
		this.teslaSynthesizerInventory = iInventory;
		this.teslaSynthesizerData = iIntArray;
		this.world = playerInventory.player.world;
		this.addSlot(new Slot(iInventory, 0, 6, 17) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() == Items.STONE;
			}
		});
		this.addSlot(new Slot(iInventory, 1, 31, 17) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() == Items.LAPIS_LAZULI;
			}
		});
		this.addSlot(new Slot(iInventory, 2, 56, 17) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() == DeferredRegistryHandler.CONDUCTIVE_ALLOY.get();
			}
		});
		this.addSlot(new TeslaSynthesizerFuelSlot(this, iInventory, 3, 56, 53));
		this.addSlot(new TeslaSynthesizerResultSlot(playerInventory.player, iInventory, 4, 116, 35));

		// Player inventory slots
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k) {
			this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
		}

		this.trackIntArray(iIntArray);
	}

	@OnlyIn(Dist.CLIENT)
	public int getSize() {
		return 5;
	}

	/**
	 * Determines whether supplied player can use this container
	 */
	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return this.teslaSynthesizerInventory.isUsableByPlayer(playerIn);
	}

	/**
	 * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
	 * inventory and the other inventory(s).
	 */
	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemStack1 = slot.getStack();
			itemstack = itemStack1.copy();
			if (index == 2) {
				if (!this.mergeItemStack(itemStack1, 3, 39, true)) {
					return ItemStack.EMPTY;
				}
				slot.onSlotChange(itemStack1, itemstack);
			} else if (index != 1 && index != 0) {
				if (this.isFuel(itemStack1)) {
					if (!this.mergeItemStack(itemStack1, 3, 4, false)) {
						return ItemStack.EMPTY;
					}
				} else if (!this.mergeItemStack(itemStack1, 0, 3, false)) {
					return ItemStack.EMPTY;
				} else if (index < 30) {
					if (!this.mergeItemStack(itemStack1, 30, 39, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index < 39 && !this.mergeItemStack(itemStack1, 3, 30, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemStack1, 3, 39, false)) {
				return ItemStack.EMPTY;
			}

			if (itemStack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (itemStack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemStack1);
		}

		return itemstack;
	}

	public boolean isFuel(ItemStack stack) {
		return AbstractTeslaSynthesizerTileEntity.isFuel(stack);
	}

	@OnlyIn(Dist.CLIENT)
	public int getCookProgressionScaled() {
		int i = this.teslaSynthesizerData.get(2);
		int j = this.teslaSynthesizerData.get(3);
		return j != 0 && i != 0 ? i * 24 / j : 0;
	}

	@OnlyIn(Dist.CLIENT)
	public int getBurnLeftScaled() {
		int i = this.teslaSynthesizerData.get(1);
		if (i == 0) {
			i = 200;
		}

		return this.teslaSynthesizerData.get(0) * 13 / i;
	}

	@OnlyIn(Dist.CLIENT)
	public boolean isBurning() {
		return this.teslaSynthesizerData.get(0) > 0;
	}

}