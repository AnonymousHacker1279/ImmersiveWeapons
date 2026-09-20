package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public abstract class AbstractInventoryBlockEntity extends BlockEntity implements EntityBlock, Container {

	private final NonNullList<ItemStack> inventory = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
	/// Constructor for AbstractInventoryBlockEntity.
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
		int filledSlots = 0;
		for (ItemStack stack : inventory) {
			if (!stack.isEmpty()) {
				filledSlots++;
			}
		}

		return filledSlots;
	}

	@Override
	public void setItem(int slot, ItemStack stack) {
		inventory.set(slot, stack);
		inventoryChanged();
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}

	/// Add an item to the inventory.
	///
	/// @param itemStack the `ItemStack` to add
	/// @return boolean
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

	@Override
	public ItemStack removeItem(int slot, int amount) {
		ItemStack stack = ContainerHelper.removeItem(inventory, slot, amount);
		inventoryChanged();
		return stack;
	}

	@Override
	public ItemStack removeItemNoUpdate(int slot) {
		return ContainerHelper.takeItem(inventory, slot);
	}

	/// Remove an item from the inventory.
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

	/// Set the change state.
	public void inventoryChanged() {
		setChanged();
		if (level != null) {
			level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
		}
	}

	/// Get the inventory.
	///
	/// @return NonNullList extending ItemStack
	public NonNullList<ItemStack> getInventory() {
		return inventory;
	}

	@Override
	protected void loadAdditional(ValueInput valueInput) {
		super.loadAdditional(valueInput);
		inventory.clear();
		ContainerHelper.loadAllItems(valueInput, inventory);
	}

	@Override
	protected void saveAdditional(ValueOutput valueOutput) {
		super.saveAdditional(valueOutput);
		ContainerHelper.saveAllItems(valueOutput, inventory);
	}

	/// Get the entity update packet.
	///
	/// @return ClientboundBlockEntityDataPacket
	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
		CompoundTag tag;
		try (ProblemReporter.ScopedCollector collector = new ProblemReporter.ScopedCollector(this.problemPath(), ImmersiveWeapons.LOGGER)) {
			TagValueOutput output = TagValueOutput.createWithContext(collector, provider);
			ContainerHelper.saveAllItems(output, inventory, true);
			tag = output.buildResult();
		}

		return tag;
	}

	/// Clear the inventory.
	@Override
	public void clearContent() {
		inventory.clear();
	}

}