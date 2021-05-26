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
import net.minecraft.world.IWorld;

public class CorrugatedBlock {

	public static class CorrugatedBlockNormal extends HorizontalBlock implements IWaterLoggable {

		public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
		public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
		protected static final VoxelShape SHAPE_NORTH = Block.makeCuboidShape(0.0D, 0.0D, 16.0D, 16.0D, 16.0D, 14.0D);
		protected static final VoxelShape SHAPE_SOUTH = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 2.0D);
		protected static final VoxelShape SHAPE_EAST = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 2.0D, 16.0D, 16.0D);
		protected static final VoxelShape SHAPE_WEST = Block.makeCuboidShape(14.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

		public CorrugatedBlockNormal(Properties properties) {
			super(properties);
			this.setDefaultState(this.stateContainer.getBaseState().with(WATERLOGGED, false).with(FACING, Direction.NORTH));
		}

		@Override
		public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
			Vector3d vector3d = state.getOffset(worldIn, pos);
			switch (state.get(FACING)) {
				case NORTH:
				default:
					return SHAPE_NORTH.withOffset(vector3d.x, vector3d.y, vector3d.z);
				case SOUTH:
					return SHAPE_SOUTH.withOffset(vector3d.x, vector3d.y, vector3d.z);
				case EAST:
					return SHAPE_EAST.withOffset(vector3d.x, vector3d.y, vector3d.z);
				case WEST:
					return SHAPE_WEST.withOffset(vector3d.x, vector3d.y, vector3d.z);
			}
		}

		@Override
		public void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
			builder.add(FACING);
			builder.add(WATERLOGGED);
		}

		@Override
		public BlockState getStateForPlacement(BlockItemUseContext context) {
			return this.getDefaultState().with(WATERLOGGED, false).with(FACING, context.getPlacementHorizontalFacing().getOpposite());
		}

		@Override
		public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
			if (stateIn.get(WATERLOGGED)) {
				worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
			}
			return stateIn;
		}

		@Override
		public FluidState getFluidState(BlockState state) {
			return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
		}
	}

	public static class CorrugatedBlockFlat extends HorizontalBlock implements IWaterLoggable {

		public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
		public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
		protected static final VoxelShape SHAPE_FLAT = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);

		public CorrugatedBlockFlat(Properties properties) {
			super(properties);
			this.setDefaultState(this.stateContainer.getBaseState().with(WATERLOGGED, false).with(FACING, Direction.NORTH));
		}

		@Override
		public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
			Vector3d vector3d = state.getOffset(worldIn, pos);
			return SHAPE_FLAT.withOffset(vector3d.x, vector3d.y, vector3d.z);
		}

		@Override
		public void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
			builder.add(FACING);
			builder.add(WATERLOGGED);
		}

		@Override
		public BlockState getStateForPlacement(BlockItemUseContext context) {
			return this.getDefaultState().with(WATERLOGGED, false).with(FACING, context.getPlacementHorizontalFacing().getOpposite());
		}

		@Override
		public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
			if (stateIn.get(WATERLOGGED)) {
				worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
			}
			return stateIn;
		}

		@Override
		public FluidState getFluidState(BlockState state) {
			return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
		}
	}
}