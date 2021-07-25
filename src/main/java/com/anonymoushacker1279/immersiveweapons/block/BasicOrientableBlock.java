package com.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class BasicOrientableBlock extends HorizontalDirectionalBlock {

	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

	/**
	 * Constructor for BasicOrientableBlock.
	 * This class creates a block with a DirectionProperty.
	 * @param properties the <code>Properties</code> of the block
	 */
	public BasicOrientableBlock(Properties properties) {
		super(properties);
		registerDefaultState(
				stateDefinition.any()
						.setValue(FACING, Direction.NORTH)
		);
	}

	/**
	 * Create the BlockState definition.
	 * @param builder the <code>StateContainer.Builder</code> of the block
	 */
	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	/**
	 * Set placement properties.
	 * Sets the facing direction of the block for placement.
	 * @param context the <code>BlockItemUseContext</code> during placement
	 * @return BlockState
	 */
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

}