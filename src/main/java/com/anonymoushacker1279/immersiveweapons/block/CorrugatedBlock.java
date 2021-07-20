package com.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;

public class CorrugatedBlock {

	public static class CorrugatedBlockNormal extends HorizontalBlock implements IWaterLoggable {

		public static final DirectionProperty FACING = HorizontalBlock.FACING;
		public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
		protected static final VoxelShape SHAPE_NORTH = Block.box(0.0D, 0.0D, 16.0D, 16.0D, 16.0D, 14.0D);
		protected static final VoxelShape SHAPE_SOUTH = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 2.0D);
		protected static final VoxelShape SHAPE_EAST = Block.box(0.0D, 0.0D, 0.0D, 2.0D, 16.0D, 16.0D);
		protected static final VoxelShape SHAPE_WEST = Block.box(14.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

		/**
		 * Constructor for CorrugatedBlockNormal.
		 * @param properties the <code>Properties</code> of the block
		 */
		public CorrugatedBlockNormal(Properties properties) {
			super(properties);
			registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, false).setValue(FACING, Direction.NORTH));
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
			switch (state.getValue(FACING)) {
				case NORTH:
				default:
					return SHAPE_NORTH.move(vector3d.x, vector3d.y, vector3d.z);
				case SOUTH:
					return SHAPE_SOUTH.move(vector3d.x, vector3d.y, vector3d.z);
				case EAST:
					return SHAPE_EAST.move(vector3d.x, vector3d.y, vector3d.z);
				case WEST:
					return SHAPE_WEST.move(vector3d.x, vector3d.y, vector3d.z);
			}
		}

		/**
		 * Create the BlockState definition.
		 * @param builder the <code>StateContainer.Builder</code> of the block
		 */
		@Override
		public void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
			builder.add(FACING, WATERLOGGED);
		}

		/**
		 * Set placement properties.
		 * Sets the facing direction of the block for placement.
		 * @param context the <code>BlockItemUseContext</code> during placement
		 * @return BlockState
		 */
		@Override
		public BlockState getStateForPlacement(BlockItemUseContext context) {
			return defaultBlockState().setValue(WATERLOGGED, false).setValue(FACING, context.getHorizontalDirection().getOpposite());
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
	}

	public static class CorrugatedBlockFlat extends HorizontalBlock implements IWaterLoggable {

		public static final DirectionProperty FACING = HorizontalBlock.FACING;
		public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
		protected static final VoxelShape SHAPE_FLAT = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);

		/**
		 * Constructor for CorrugatedBlockFlat.
		 * @param properties the <code>Properties</code> of the block
		 */
		public CorrugatedBlockFlat(Properties properties) {
			super(properties);
			registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, false).setValue(FACING, Direction.NORTH));
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
			return SHAPE_FLAT.move(vector3d.x, vector3d.y, vector3d.z);
		}

		/**
		 * Create the BlockState definition.
		 * @param builder the <code>StateContainer.Builder</code> of the block
		 */
		@Override
		public void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
			builder.add(FACING, WATERLOGGED);
		}

		/**
		 * Set placement properties.
		 * Sets the facing direction of the block for placement.
		 * @param context the <code>BlockItemUseContext</code> during placement
		 * @return BlockState
		 */
		@Override
		public BlockState getStateForPlacement(BlockItemUseContext context) {
			return defaultBlockState().setValue(WATERLOGGED, false).setValue(FACING, context.getHorizontalDirection().getOpposite());
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
	}
}