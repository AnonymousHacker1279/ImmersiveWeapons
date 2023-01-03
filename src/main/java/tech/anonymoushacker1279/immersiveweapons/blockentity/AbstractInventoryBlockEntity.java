package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

public abstract class AbstractInventoryBlockEntity extends BlockEntity implements EntityBlock, Clearable {

	private final NonNullList<ItemStack> inventory = NonNullList.withSize(getInventorySize(), ItemStack.EMPTY);

	/**
	 * Constructor for AbstractInventoryBlockEntity.
	 */
	public AbstractInventoryBlockEntity(BlockEntityType<?> type, BlockPos blockPos, BlockState blockState) {
		super(type, blockPos, blockState);
	}

	public int getInventorySize() {
		return 4;
	}

	/**
	 * Add an item to the inventory.
	 *
	 * @param itemStack the <code>ItemStack</code> to add
	 * @return boolean
	 */
	public boolean addItem(ItemStack itemStack) {
		for (int i = 0; i < inventory.size(); i++) {
			ItemStack itemstack = inventory.get(i);
			if (itemstack.isEmpty()) {
				inventory.set(i, itemStack.split(1));
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
	 *
	 * @return NonNullList extending ItemStack
	 */
	public NonNullList<ItemStack> getInventory() {
		return inventory;
	}

	/**
	 * Load NBT data.
	 *
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
	 *
	 * @param tag the <code>CompoundNBT</code> to save
	 */
	@Override
	protected void saveAdditional(CompoundTag tag) {
		writeItems(tag);
	}

	/**
	 * Write items to NBT.
	 *
	 * @param tag the <code>CompoundTag</code> to write to
	 * @return CompoundTag
	 */
	private CompoundTag writeItems(CompoundTag tag) {
		super.saveAdditional(tag);
		ContainerHelper.saveAllItems(tag, inventory, true);
		return tag;
	}

	/**
	 * Get the entity update packet.
	 *
	 * @return ClientboundBlockEntityDataPacket
	 */
	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	/**
	 * Get the update tag.
	 *
	 * @return CompoundTag
	 */
	@Override
	public CompoundTag getUpdateTag() {
		return writeItems(new CompoundTag());
	}

	/**
	 * Handle data packets.
	 *
	 * @param connection the <code>Connection</code> instance
	 * @param pkt        the <code>ClientboundBlockEntityDataPacket</code> instance
	 */
	@Override
	public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket pkt) {
		super.onDataPacket(connection, pkt);
		handleUpdateTag(pkt.getTag());
	}

	/**
	 * Clear the inventory.
	 */
	@Override
	public void clearContent() {
		inventory.clear();
	}
}