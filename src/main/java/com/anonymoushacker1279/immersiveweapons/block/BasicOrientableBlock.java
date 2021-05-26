package com.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;

public class BasicOrientableBlock extends HorizontalBlock {

	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

	public BasicOrientableBlock(Properties properties) {
		super(properties);
		this.setDefaultState(
				this.stateContainer.getBaseState()
						.with(FACING, Direction.NORTH)
		);
	}

	@Override
	public void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	}

}