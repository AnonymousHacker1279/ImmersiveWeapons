package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

public class AstralCrystalBlockEntity extends AbstractInventoryBlockEntity {

	public AstralCrystalBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(DeferredRegistryHandler.ASTRAL_CRYSTAL_BLOCK_ENTITY.get(), blockPos, blockState);
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
		return new AstralCrystalBlockEntity(blockPos, blockState);
	}
}