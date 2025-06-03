package tech.anonymoushacker1279.immersiveweapons.block.core;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import tech.anonymoushacker1279.immersiveweapons.blockentity.AbstractStatueBlockEntity;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.stream.Stream;

public abstract class SpawnerStatueBlock<T extends AbstractStatueBlockEntity<?>> extends BasicOrientableBlock implements EntityBlock, SimpleWaterloggedBlock {

	private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	private static final VoxelShape SHAPE_NS = Stream.of(
			Block.box(3, 0, 2, 13, 1, 14),
			Block.box(4, 1, 3, 12, 2, 13),
			Block.box(6, 2, 5, 10, 3, 12),
			Block.box(6, 3, 6, 10, 10, 10)
	).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
	private static final VoxelShape SHAPE_EW = GeneralUtilities.rotateShape(Direction.NORTH, Direction.EAST, SHAPE_NS);

	public SpawnerStatueBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return createBlockEntity(blockPos, blockState);
	}

	@Override
	public <B extends BlockEntity> BlockEntityTicker<B> getTicker(Level level, BlockState blockState,
	                                                              BlockEntityType<B> blockEntityType) {

		return level.isClientSide ? null : (world, pos, state, entity) -> ((AbstractStatueBlockEntity<?>) entity).tick(world, pos);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
		return switch (state.getValue(FACING)) {
			case NORTH, SOUTH -> SHAPE_NS;
			case EAST, WEST -> SHAPE_EW;
			default ->
					throw new IllegalStateException("Unexpected value, cannot face direction: " + state.getValue(FACING));
		};
	}

	@Override
	public void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING, WATERLOGGED);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	protected abstract T createBlockEntity(BlockPos blockPos, BlockState blockState);
}