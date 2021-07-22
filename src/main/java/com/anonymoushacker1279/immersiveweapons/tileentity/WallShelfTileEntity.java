package com.anonymoushacker1279.immersiveweapons.tileentity;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.IClearable;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;

import java.util.Objects;

public class WallShelfTileEntity extends TileEntity implements IClearable {

	private final NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);

	/**
	 * Constructor for PanicAlarmTileEntity.
	 */
	public WallShelfTileEntity() {
		super(DeferredRegistryHandler.WALL_SHELF_TILE_ENTITY.get());
	}

	/**
	 * Add an item to the inventory.
	 * @param itemStackIn the <code>ItemStack</code> to add
	 * @return boolean
	 */
	public boolean addItem(ItemStack itemStackIn) {
		for (int i = 0; i < inventory.size(); i++) {
			ItemStack itemstack = inventory.get(i);
			if (itemstack.isEmpty()) {
				inventory.set(i, itemStackIn.split(1));
				inventoryChanged();
				return true;
			}
		}
		return false;
	}

	/**
	 * Remove an item from the inventory.
	 */
	public void removeItem() {
		for (int i = inventory.size() - 1; i > -1; i--) {
			if (!inventory.get(i).isEmpty()) {
				if (level != null) {
					InventoryHelper.dropItemStack(level, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), inventory.get(i));
				}
				inventory.set(i, ItemStack.EMPTY);
				inventoryChanged();
				return;
			}
		}
	}

	/**
	 * Set the change state.
	 */
	private void inventoryChanged() {
		setChanged();
		Objects.requireNonNull(getLevel()).sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
	}

	/**
	 * Get the inventory.
	 * @return NonNullList extending ItemStack
	 */
	public NonNullList<ItemStack> getInventory() {
		return inventory;
	}

	/**
	 * Load NBT data.
	 * @param state the <code>BlockState</code> of the block
	 * @param nbt the <code>CompoundNBT</code> to load
	 */
	@Override
	public void load(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		inventory.clear();
		ItemStackHelper.loadAllItems(nbt, inventory);
	}

	/**
	 * Save NBT data.
	 * @param nbt the <code>CompoundNBT</code> to save
	 */
	@Override
	public CompoundNBT save(CompoundNBT nbt) {
		writeItems(nbt);
		return nbt;
	}

	/**
	 * Write items to NBT.
	 * @param nbt the <code>NBT</code> to write to
	 * @return CompoundNBT
	 */
	private CompoundNBT writeItems(CompoundNBT nbt) {
		super.save(nbt);
		ItemStackHelper.saveAllItems(nbt, inventory, true);
		return nbt;
	}

	/**
	 * Get the entity update packet.
	 * @return SUpdateTileEntityPacket
	 */
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(worldPosition, 13, getUpdateTag());
	}

	/**
	 * Get the update tag.
	 * @return CompoundNBT
	 */
	@Override
	public CompoundNBT getUpdateTag() {
		return writeItems(new CompoundNBT());
	}

	/**
	 * Handle data packets.
	 * @param net the <code>NetworkManager</code> instance
	 * @param pkt the <code>SUpdateTileEntityPacket</code> instance
	 */
	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		super.onDataPacket(net, pkt);
		handleUpdateTag(getBlockState(), pkt.getTag());
	}

	/**
	 * Clear the inventory.
	 */
	@Override
	public void clearContent() {
		inventory.clear();
	}
}