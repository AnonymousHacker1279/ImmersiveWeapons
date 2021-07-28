package com.anonymoushacker1279.immersiveweapons.blockentity;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Clearable;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Objects;

public class WallShelfBlockEntity extends BlockEntity implements EntityBlock, Clearable {

	private final NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);

	/**
	 * Constructor for WallShelfBlockEntity.
	 */
	public WallShelfBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(DeferredRegistryHandler.WALL_SHELF_BLOCK_ENTITY.get(), blockPos, blockState);
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
					Containers.dropItemStack(level, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), inventory.get(i));
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
	 * @param nbt the <code>CompoundNBT</code> to load
	 */
	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		inventory.clear();
		ContainerHelper.loadAllItems(nbt, inventory);
	}

	/**
	 * Save NBT data.
	 * @param nbt the <code>CompoundNBT</code> to save
	 */
	@Override
	public CompoundTag save(CompoundTag nbt) {
		writeItems(nbt);
		return nbt;
	}

	/**
	 * Write items to NBT.
	 * @param nbt the <code>NBT</code> to write to
	 * @return CompoundNBT
	 */
	private CompoundTag writeItems(CompoundTag nbt) {
		super.save(nbt);
		ContainerHelper.saveAllItems(nbt, inventory, true);
		return nbt;
	}

	/**
	 * Get the entity update packet.
	 * @return ClientboundBlockEntityDataPacket
	 */
	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return new ClientboundBlockEntityDataPacket(worldPosition, 13, getUpdateTag());
	}

	/**
	 * Get the update tag.
	 * @return CompoundNBT
	 */
	@Override
	public CompoundTag getUpdateTag() {
		return writeItems(new CompoundTag());
	}

	/**
	 * Handle data packets.
	 * @param net the <code>NetworkManager</code> instance
	 * @param pkt the <code>ClientboundBlockEntityDataPacket</code> instance
	 */
	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
		super.onDataPacket(net, pkt);
		handleUpdateTag(pkt.getTag());
	}

	/**
	 * Clear the inventory.
	 */
	@Override
	public void clearContent() {
		inventory.clear();
	}

	/**
	 * Create a block entity for the block.
	 * @param blockPos the <code>BlockPos</code> the block is at
	 * @param blockState the <code>BlockState</code> of the block
	 * @return BlockEntity
	 */
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new WallShelfBlockEntity(blockPos, blockState);
	}
}