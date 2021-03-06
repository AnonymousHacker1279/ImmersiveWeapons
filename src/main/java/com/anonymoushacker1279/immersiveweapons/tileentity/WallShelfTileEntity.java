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

import javax.annotation.Nullable;
import java.util.Objects;

public class WallShelfTileEntity extends TileEntity implements IClearable {

	private final NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);

	public WallShelfTileEntity() {
		super(DeferredRegistryHandler.WALL_SHELF_TILE_ENTITY.get());
	}

	public boolean addItem(ItemStack itemStackIn) {
		for (int i = 0; i < this.inventory.size(); i++) {
			ItemStack itemstack = this.inventory.get(i);
			if (itemstack.isEmpty()) {
				this.inventory.set(i, itemStackIn.split(1));
				this.inventoryChanged();
				return true;
			}
		}
		return false;
	}

	public void removeItem() {
		for (int i = this.inventory.size() - 1; i > -1; i--) {
			if (!this.inventory.get(i).isEmpty()) {
				if (this.level != null) {
					InventoryHelper.dropItemStack(this.level, this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ(), this.inventory.get(i));
				}
				this.inventory.set(i, ItemStack.EMPTY);
				this.inventoryChanged();
				return;
			}
		}
	}

	private void inventoryChanged() {
		this.setChanged();
		Objects.requireNonNull(this.getLevel()).sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
	}

	public NonNullList<ItemStack> getInventory() {
		return this.inventory;
	}

	@Override
	public void load(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		this.inventory.clear();
		ItemStackHelper.loadAllItems(nbt, this.inventory);
	}

	@Override
	public CompoundNBT save(CompoundNBT compound) {
		this.writeItems(compound);
		return compound;
	}

	private CompoundNBT writeItems(CompoundNBT compound) {
		super.save(compound);
		ItemStackHelper.saveAllItems(compound, this.inventory, true);
		return compound;
	}

	@Override
	@Nullable
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(this.worldPosition, 13, this.getUpdateTag());
	}

	@Override
	public CompoundNBT getUpdateTag() {
		return this.writeItems(new CompoundNBT());
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		super.onDataPacket(net, pkt);
		handleUpdateTag(this.getBlockState(), pkt.getTag());
	}

	@Override
	public void clearContent() {
		this.inventory.clear();
	}
}