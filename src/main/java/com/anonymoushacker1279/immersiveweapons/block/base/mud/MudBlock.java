package com.anonymoushacker1279.immersiveweapons.block.base.mud;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class MudBlock extends Block {

	float chanceToDry = 0.10f;

	public MudBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void randomTick(@NotNull BlockState pState, ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull Random pRandom) {
		if (pLevel.isRainingAt(pPos)) {
			chanceToDry = 0.03f;
		}
		if (canDry(pLevel, pPos) && pRandom.nextFloat() <= chanceToDry) {
			pLevel.setBlockAndUpdate(pPos, DeferredRegistryHandler.DRIED_MUD.get().defaultBlockState());
		}
	}

	private boolean canDry(ServerLevel level, BlockPos pos) {
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