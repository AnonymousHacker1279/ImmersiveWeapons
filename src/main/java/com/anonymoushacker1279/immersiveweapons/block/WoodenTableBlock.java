package com.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;

public class WoodenTableBlock extends Block implements IWaterLoggable {

	public static final VoxelShape SHAPE = VoxelShapes.or(Block.box(0.0D, 14.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(7.0D, 0.0D, 7.0D, 9.0D, 14.0D, 9.0D));
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	/**
	 * Constructor for WoodenTableBlock.
	 * @param properties the <code>Properties</code> of the block
	 */
	public WoodenTableBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, false));
	}

	/**
	 * Create the BlockState definition.
	 * @param builder the <code>StateContainer.Builder</code> of the block
	 */
	@Override
	public void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}

	/**
	 * Set FluidState properties.
	 * Allows the block to exhibit waterlogged behavior.
	 * @param state the <code>BlockState</code> of the block
	 * @return FluidState
	 */
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	/**
	 * Set the shape of the block.
	 * @param state the <code>BlockState</code> of the block
	 * @param reader the <code>IBlockReader</code> for the block
	 * @param pos the <code>BlockPos</code> the block is at
	 * @param selectionContext the <code>ISelectionContext</code> of the block
	 * @return VoxelShape
	 */
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext selectionContext) {
		Vector3d vector3d = state.getOffset(reader, pos);
		return SHAPE.move(vector3d.x, vector3d.y, vector3d.z);
	}
}