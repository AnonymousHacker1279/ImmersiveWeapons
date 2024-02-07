package tech.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import tech.anonymoushacker1279.immersiveweapons.blockentity.PanicAlarmBlockEntity;

public class PanicAlarmBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock, EntityBlock {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	private static final VoxelShape SHAPE_NORTH = Block.box(4.0D, 4.0D, 13.0D, 12.0D, 11.0D, 16.0D);
	private static final VoxelShape SHAPE_SOUTH = Block.box(4.0D, 4.0D, 0.0D, 12.0D, 11.0D, 3.0D);
	private static final VoxelShape SHAPE_EAST = Block.box(0.0D, 4.0D, 4.0D, 3.0D, 11.0D, 12.0D);
	private static final VoxelShape SHAPE_WEST = Block.box(13.0D, 4.0D, 4.0D, 16.0D, 11.0D, 12.0D);

	/**
	 * Constructor for PanicAlarmBlock.
	 *
	 * @param properties the <code>Properties</code> of the block
	 */
	public PanicAlarmBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, false).setValue(FACING, Direction.NORTH));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos,
	                           CollisionContext collisionContext) {

		return switch (state.getValue(FACING)) {
			case SOUTH -> SHAPE_SOUTH;
			case EAST -> SHAPE_EAST;
			case WEST -> SHAPE_WEST;
			default -> SHAPE_NORTH;
		};
	}

	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED, FACING);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new PanicAlarmBlockEntity(blockPos, blockState);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState,
	                                                              BlockEntityType<T> blockEntityType) {

		return level.isClientSide ? null : (world, pos, state, entity) -> ((PanicAlarmBlockEntity) entity).tick(world, pos);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState()
				.setValue(FACING, context.getHorizontalDirection().getOpposite())
				.setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block blockIn,
	                            BlockPos fromPos, boolean isMoving) {

		if (!level.isClientSide) {
			checkPowered(level, pos);
		}
	}

	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
		if (!oldState.is(state.getBlock())) {
			if (level.hasNeighborSignal(pos)) {
				if (!level.isClientSide) {
					checkPowered(level, pos);
				}
			}
		}
	}

	@Override
	public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
		// Ensure it is being placed on the side of a block
		BlockState blockState = pLevel.getBlockState(pPos.relative(pState.getValue(FACING), -1));
		return blockState.isFaceSturdy(pLevel, pPos, pState.getValue(FACING));
	}

	@Override
	public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
		return !pState.canSurvive(pLevel, pPos) ? Blocks.AIR.defaultBlockState() : pState;
	}

	/**
	 * Plays a sound when powered.
	 *
	 * @param level the <code>Level</code> the block is at
	 * @param pos   the <code>BlockPos</code> the block is at
	 */
	private void checkPowered(Level level, BlockPos pos) {
		BlockEntity blockEntity = level.getBlockEntity(pos);

		if (blockEntity instanceof PanicAlarmBlockEntity panicAlarmBlockEntity) {
			if (level.getBestNeighborSignal(pos) > 0) {
				if (!panicAlarmBlockEntity.isPowered()) {
					panicAlarmBlockEntity.setPowered(true);
				}
			} else {
				if (panicAlarmBlockEntity.isPowered()) {
					panicAlarmBlockEntity.setPowered(false);
				}
			}
		}
	}
}