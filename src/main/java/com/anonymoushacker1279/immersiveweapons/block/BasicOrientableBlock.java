package com.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;

public class BasicOrientableBlock extends HorizontalBlock {

	public static final DirectionProperty FACING = HorizontalBlock.FACING;

	/**
	 * Constructor for BasicOrientableBlock.
	 * This class creates a block with a DirectionProperty.
	 * @param properties the <code>Properties</code> of the block
	 * @see HorizontalBlock
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
	public void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	/**
	 * Set placement properties.
	 * Sets the facing direction of the block for placement.
	 * @param context the <code>BlockItemUseContext</code> during placement
	 * @return BlockState
	 */
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

}