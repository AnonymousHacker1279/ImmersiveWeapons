package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.BlockEntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.world.level.saveddata.CelestialLanternData;

public class CelestialLanternBlockEntity extends BlockEntity implements EntityBlock {

	/**
	 * Constructor for CelestialLanternBlockEntity.
	 */
	public CelestialLanternBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(BlockEntityRegistry.CELESTIAL_LANTERN_BLOCK_ENTITY.get(), blockPos, blockState);
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
		return new CelestialLanternBlockEntity(blockPos, blockState);
	}

	@Override
	public void onLoad() {
		super.onLoad();
		if (level instanceof ServerLevel serverLevel) {
			CelestialLanternData.getData(serverLevel.getServer()).addLantern(getBlockPos());
		}
	}
}