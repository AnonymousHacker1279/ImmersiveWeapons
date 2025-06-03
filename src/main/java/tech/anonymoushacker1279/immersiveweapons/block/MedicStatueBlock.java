package tech.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import tech.anonymoushacker1279.immersiveweapons.block.core.SpawnerStatueBlock;
import tech.anonymoushacker1279.immersiveweapons.blockentity.MedicStatueBlockEntity;

public class MedicStatueBlock extends SpawnerStatueBlock<MedicStatueBlockEntity> {

	public MedicStatueBlock(Properties properties) {
		super(properties);
	}

	@Override
	public MedicStatueBlockEntity createBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new MedicStatueBlockEntity(blockPos, blockState);
	}
}