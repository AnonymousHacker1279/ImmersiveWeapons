package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractInventoryBlockEntity extends BlockEntity implements EntityBlock, Container {

	private final NonNullList<ItemStack> inventory = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
	private int filledSlots = 0;

	/**
	 * Constructor for AbstractInventoryBlockEntity.
	 */
	public AbstractInventoryBlockEntity(BlockEntityType<?> type, BlockPos blockPos, BlockState blockState) {
		super(type, blockPos, blockState);
	}

	@Override
	public int getContainerSize() {
		return 4;
	}

	@Override
	public boolean isEmpty() {
		return inventory.stream().allMatch(ItemStack::isEmpty);
	}

	@Override
	public ItemStack getItem(int slot) {
		return inventory.get(slot);
	}

	public int getFilledSlots() {
		return filledSlots;
	}

	@Override
	public void setItem(int slot, ItemStack stack) {
		inventory.set(slot, stack);
		if (stack.isEmpty()) {
			filledSlots--;
		} else {
			filledSlots++;
		}
		inventoryChanged();
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
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
				filledSlots++;
				inventoryChanged();
				return true;
			}
		}
		return false;
	}

	@Override
	public ItemStack removeItem(int slot, int amount) {
		ItemStack stack = ContainerHelper.removeItem(inventory, slot, amount);
		if (inventory.get(slot).isEmpty()) {
			filledSlots--;
		}
		inventoryChanged();
		return stack;
	}

	@Override
	public ItemStack removeItemNoUpdate(int slot) {
		return ContainerHelper.takeItem(inventory, slot);
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
				filledSlots--;
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

		for (ItemStack itemStack : inventory) {
			if (!itemStack.isEmpty()) {
				filledSlots++;
			}
		}
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

	@Override
	public void preRemoveSideEffects(BlockPos pos, BlockState state) {
		super.preRemoveSideEffects(pos, state);
	}
}