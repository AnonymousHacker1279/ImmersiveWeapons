package tech.anonymoushacker1279.immersiveweapons.block.base.mud;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

public class DriedMudBlock extends Block {

	float chanceToMoisten = 0.10f;

	public DriedMudBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void randomTick(@NotNull BlockState pState, ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
		if (pLevel.isRainingAt(pPos)) {
			chanceToMoisten = 0.13f;
		}
		if (canMoisten(pLevel, pPos) && pRandom.nextFloat() <= chanceToMoisten) {
			pLevel.setBlockAndUpdate(pPos, DeferredRegistryHandler.MUD.get().defaultBlockState());
		}
	}

	private boolean canMoisten(ServerLevel level, BlockPos pos) {
		BlockState stateNorth = level.getBlockState(pos.north());
		BlockState stateSouth = level.getBlockState(pos.south());
		BlockState stateEast = level.getBlockState(pos.east());
		BlockState stateWest = level.getBlockState(pos.west());
		BlockState stateUp = level.getBlockState(pos.above());
		BlockState stateDown = level.getBlockState(pos.below());

		return stateNorth == Blocks.WATER.defaultBlockState() || stateSouth == Blocks.WATER.defaultBlockState() || stateEast == Blocks.WATER.defaultBlockState()
				|| stateWest == Blocks.WATER.defaultBlockState() || stateUp == Blocks.WATER.defaultBlockState() || stateDown == Blocks.WATER.defaultBlockState();
	}
}