package tech.anonymoushacker1279.immersiveweapons.block.mud;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.*;

public class HardenedMudWindowBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	private static final VoxelShape SHAPE_NS = Shapes.or(Block.box(0.0D, 0.0D, 7.5D, 2.0D, 16.0D, 8.5D),
			Block.box(14.0D, 0.0D, 7.5D, 16.0D, 16.0D, 8.5D),
			Block.box(2.0D, 0.0D, 7.5D, 14.0D, 2.0D, 8.5D),
			Block.box(2.0D, 14.0D, 7.5D, 14.0D, 16.0D, 8.5D));

	private static final VoxelShape SHAPE_EW = Shapes.or(Block.box(7.5D, 0.0D, 0.0D, 8.5D, 16.0D, 2.0D),
			Block.box(7.5D, 0.0D, 14.0D, 8.5D, 16.0D, 16D),
			Block.box(7.5D, 0.0D, 2.0D, 8.5D, 2.0D, 14.0D),
			Block.box(7.5D, 14.0D, 2.0D, 8.5D, 16.0D, 14.0D));

	public HardenedMudWindowBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
	}

	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext selectionContext) {
		if (state.getValue(FACING) == Direction.NORTH || state.getValue(FACING) == Direction.SOUTH) {
			return SHAPE_NS;
		} else {
			return SHAPE_EW;
		}
	}

	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, WATERLOGGED);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection());
	}

	@SuppressWarnings("deprecation")
	@Override
	public float getShadeBrightness(BlockState state, BlockGetter reader, BlockPos pos) {
		return 1.0F;
	}

	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}
}