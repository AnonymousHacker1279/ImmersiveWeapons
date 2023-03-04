package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.block.decoration.ShelfBlock;
import tech.anonymoushacker1279.immersiveweapons.init.BlockEntityRegistry;

public class ShelfBlockEntity extends AbstractInventoryBlockEntity {

	private boolean isLocked = false;
	private int renderUpdateDelay = 0;

	public ShelfBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(BlockEntityRegistry.SHELF_BLOCK_ENTITY.get(), blockPos, blockState);
	}

	/**
	 * Create a block entity for the block.
	 *
	 * @param blockPos   the <code>BlockPos</code> the block is at
	 * @param blockState the <code>BlockState</code> of the block
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

	public void tick() {
		/* During BreakEvent, if this entity is locked, the server will not allow the block to be broken however
		 the client will still "break" it. This causes the contents to be reset client-side. This simply
		 re-syncs the client-side inventory with the server-side inventory.*/
		if (ShelfBlock.needsRenderUpdate) {
			renderUpdateDelay = 1;
			ShelfBlock.needsRenderUpdate = false;
		}
		if (renderUpdateDelay == 0) {
			inventoryChanged();
		} else if (renderUpdateDelay > 0) {
			renderUpdateDelay--;
		}
	}
}