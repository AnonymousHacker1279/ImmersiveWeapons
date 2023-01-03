package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.BlockEntityRegistry;

public class WallShelfBlockEntity extends AbstractInventoryBlockEntity {

	/**
	 * Constructor for WallShelfBlockEntity.
	 */
	public WallShelfBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(BlockEntityRegistry.WALL_SHELF_BLOCK_ENTITY.get(), blockPos, blockState);
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
		return new WallShelfBlockEntity(blockPos, blockState);
	}
}