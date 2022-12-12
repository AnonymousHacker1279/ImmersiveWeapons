package tech.anonymoushacker1279.immersiveweapons.block.mud;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;

public class IWMudBlock extends Block {

	protected float changeStateChance = 0.10f;

	public IWMudBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
		if (pLevel.isRainingAt(pPos)) {
			changeStateChance = 0.03f;
		}
		if (canDry(pLevel, pPos) && pRandom.nextFloat() <= changeStateChance) {
			pLevel.setBlockAndUpdate(pPos, BlockRegistry.DRIED_MUD.get().defaultBlockState());
		}
	}

	protected boolean canDry(ServerLevel level, BlockPos pos) {
		BlockState stateNorth = level.getBlockState(pos.north());
		BlockState stateSouth = level.getBlockState(pos.south());
		BlockState stateEast = level.getBlockState(pos.east());
		BlockState stateWest = level.getBlockState(pos.west());
		BlockState stateUp = level.getBlockState(pos.above());
		BlockState stateDown = level.getBlockState(pos.below());

		return stateNorth != Blocks.WATER.defaultBlockState() && stateSouth != Blocks.WATER.defaultBlockState() && stateEast != Blocks.WATER.defaultBlockState()
				&& stateWest != Blocks.WATER.defaultBlockState() && stateUp != Blocks.WATER.defaultBlockState() && stateDown != Blocks.WATER.defaultBlockState();
	}
}