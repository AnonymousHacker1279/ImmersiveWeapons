package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

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
	public void inventoryChanged() {
		setChanged();
		if (level != null) {
			level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
		}
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
	public void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
		super.loadAdditional(nbt, provider);
		inventory.clear();
		ContainerHelper.loadAllItems(nbt, inventory, provider);
	}

	/**
	 * Save NBT data.
	 *
	 * @param tag the <code>CompoundNBT</code> to save
	 */
	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
		writeItems(tag, provider);
	}

	/**
	 * Write items to NBT.
	 *
	 * @param tag the <code>CompoundTag</code> to write to
	 * @return CompoundTag
	 */
	private CompoundTag writeItems(CompoundTag tag, HolderLookup.Provider provider) {
		super.saveAdditional(tag, provider);
		ContainerHelper.saveAllItems(tag, inventory, provider);
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
	public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
		return writeItems(new CompoundTag(), provider);
	}

	/**
	 * Clear the inventory.
	 */
	@Override
	public void clearContent() {
		inventory.clear();
	}
}