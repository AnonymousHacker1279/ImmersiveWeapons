package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.BlockEntityRegistry;

public class ShelfBlockEntity extends AbstractInventoryBlockEntity {

	private boolean isLocked = false;

	public ShelfBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(BlockEntityRegistry.SHELF_BLOCK_ENTITY.get(), blockPos, blockState);
	}

	/// Create a block entity for the block.
	///
	/// @param blockPos   the `BlockPos` the block is at
	/// @param blockState the `BlockState` of the block
	/// @return BlockEntity
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new ShelfBlockEntity(blockPos, blockState);
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean locked) {
		isLocked = locked;
	}

	@Override
	protected void saveAdditional(ValueOutput valueOutput) {
		super.saveAdditional(valueOutput);
		valueOutput.putBoolean("isLocked", isLocked);
	}

	@Override
	public void loadAdditional(ValueInput valueInput) {
		super.loadAdditional(valueInput);
		isLocked = valueInput.getBooleanOr("isLocked", false);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
		CompoundTag tag = super.getUpdateTag(provider);
		tag.putBoolean("isLocked", isLocked);
		return tag;
	}
}