package tech.anonymoushacker1279.immersiveweapons.block.core;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;

public class WaterloggingHelper {

	/// Schedule a water tick if the block is waterlogged, so the fluid keeps flowing after a neighbor update.
	/// Should be called from `updateShape` in waterloggable blocks.
	///
	/// @param state             the `BlockState` of the block
	/// @param level             the `LevelReader` the block is in
	/// @param scheduledTickAccess the `ScheduledTickAccess` from `updateShape`
	/// @param pos               the `BlockPos` of the block
	public static void scheduleFluidTick(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos pos) {
		if (state.getValue(BlockStateProperties.WATERLOGGED)) {
			scheduledTickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}
	}
}
