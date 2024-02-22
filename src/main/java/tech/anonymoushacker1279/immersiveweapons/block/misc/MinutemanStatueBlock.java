package tech.anonymoushacker1279.immersiveweapons.block.misc;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import tech.anonymoushacker1279.immersiveweapons.blockentity.MinutemanStatueBlockEntity;

public class MinutemanStatueBlock extends SpawnerStatueBlock<MinutemanStatueBlockEntity> {

	public MinutemanStatueBlock(Properties properties) {
		super(properties);
	}

	@Override
	public MinutemanStatueBlockEntity createBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new MinutemanStatueBlockEntity(blockPos, blockState);
	}
}