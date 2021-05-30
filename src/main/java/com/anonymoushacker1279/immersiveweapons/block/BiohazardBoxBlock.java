package com.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;

public class BiohazardBoxBlock extends HorizontalBlock {

	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	protected static final VoxelShape SHAPE_NORTH = Block.makeCuboidShape(2.0D, 3.0D, 16.0D, 14.0D, 13.0D, 11.0D);
	protected static final VoxelShape SHAPE_SOUTH = Block.makeCuboidShape(2.0D, 3.0D, 0.0D, 14.0D, 13.0D, 5.0D);
	protected static final VoxelShape SHAPE_EAST = Block.makeCuboidShape(0.0D, 3.0D, 2.0D, 5.0D, 13.0D, 14.0D);
	protected static final VoxelShape SHAPE_WEST = Block.makeCuboidShape(11.0D, 3.0D, 2.0D, 16.0D, 13.0D, 14.0D);

	public BiohazardBoxBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
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
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	}
}