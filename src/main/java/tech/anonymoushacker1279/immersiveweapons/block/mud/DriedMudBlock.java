package tech.anonymoushacker1279.immersiveweapons.block.mud;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

public class DriedMudBlock extends IWMudBlock {


	public DriedMudBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
		if (pLevel.isRainingAt(pPos)) {
			changeStateChance = 0.13f;
		}
		if (!canDry(pLevel, pPos) && pRandom.nextFloat() <= changeStateChance) {
			pLevel.setBlockAndUpdate(pPos, DeferredRegistryHandler.MUD.get().defaultBlockState());
		}
	}
}