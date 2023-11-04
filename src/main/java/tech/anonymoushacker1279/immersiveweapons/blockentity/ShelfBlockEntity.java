package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.BlockEntityRegistry;

public class ShelfBlockEntity extends AbstractInventoryBlockEntity {

	private boolean isLocked = false;

	public ShelfBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(BlockEntityRegistry.SHELF_BLOCK_ENTITY.get(), blockPos, blockState);
	}

	/**
	 * Create a blockLocation entity for the blockLocation.
	 *
	 * @param blockPos   the <code>BlockPos</code> the blockLocation is at
	 * @param blockState the <code>BlockState</code> of the blockLocation
	 * @return BlockEntity
	 */
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
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		tag.putBoolean("isLocked", isLocked);
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		isLocked = tag.getBoolean("isLocked");
	}

	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag tag = super.getUpdateTag();
		tag.putBoolean("isLocked", isLocked);
		return tag;
	}
}