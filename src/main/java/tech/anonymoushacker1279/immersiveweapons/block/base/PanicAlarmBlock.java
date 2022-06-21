package tech.anonymoushacker1279.immersiveweapons.block.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
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
import org.jetbrains.annotations.NotNull;
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

	/**
	 * Set the shape of the block.
	 *
	 * @param state            the <code>BlockState</code> of the block
	 * @param reader           the <code>BlockGetter</code> for the block
	 * @param pos              the <code>BlockPos</code> the block is at
	 * @param collisionContext the <code>CollisionContext</code> of the block
	 * @return VoxelShape
	 */
	@Override
	public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos pos,
	                                    @NotNull CollisionContext collisionContext) {

		return switch (state.getValue(FACING)) {
			case SOUTH -> SHAPE_SOUTH;
			case EAST -> SHAPE_EAST;
			case WEST -> SHAPE_WEST;
			default -> SHAPE_NORTH;
		};
	}

	/**
	 * Create the BlockState definition.
	 *
	 * @param builder the <code>StateDefinition.Builder</code> of the block
	 */
	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED, FACING);
	}

	/**
	 * Create a block entity for the block.
	 *
	 * @param blockPos   the <code>BlockPos</code> the block is at
	 * @param blockState the <code>BlockState</code> of the block
	 * @return BlockEntity
	 */
	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
		return new PanicAlarmBlockEntity(blockPos, blockState);
	}

	/**
	 * Get the ticker for the block.
	 *
	 * @param level           the <code>Level</code> the block is in
	 * @param blockState      the <code>BlockState</code> of the block
	 * @param blockEntityType the <code>BlockEntityType</code> to get the ticker of
	 * @param <T>             the type extending BlockEntity
	 * @return BlockEntityTicker
	 */
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState blockState,
	                                                              @NotNull BlockEntityType<T> blockEntityType) {

		return level.isClientSide ? null : (world, pos, state, entity) -> ((PanicAlarmBlockEntity) entity).tick(world, pos);
	}

	/**
	 * Set placement properties.
	 * Sets the facing direction of the block for placement.
	 *
	 * @param context the <code>BlockPlaceContext</code> during placement
	 * @return BlockState
	 */
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState()
				.setValue(FACING, context.getHorizontalDirection().getOpposite())
				.setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
	}

	/**
	 * Set FluidState properties.
	 * Allows the block to exhibit waterlogged behavior.
	 *
	 * @param state the <code>BlockState</code> of the block
	 * @return FluidState
	 */
	@Override
	public @NotNull FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	/**
	 * Runs when neighboring blocks change state.
	 *
	 * @param state    the <code>BlockState</code> of the block
	 * @param level    the <code>Level</code> the block is in
	 * @param pos      the <code>BlockPos</code> the block is at
	 * @param blockIn  the <code>Block</code> that is changing
	 * @param fromPos  the <code>BlockPos</code> of the changing block
	 * @param isMoving determines if the block is moving
	 */
	@Override
	public void neighborChanged(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Block blockIn,
	                            @NotNull BlockPos fromPos, boolean isMoving) {

		if (!level.isClientSide) {
			checkPowered(level, pos);
			level.scheduleTick(pos, state.getBlock(), 5);
		}
	}

	/**
	 * Runs when neighboring blocks change state.
	 *
	 * @param state    the <code>BlockState</code> of the block
	 * @param level    the <code>Level</code> the block is in
	 * @param pos      the <code>BlockPos</code> the block is at
	 * @param oldState the <code>BlockState</code> the block previously had
	 * @param isMoving determines if the block is moving
	 */
	@Override
	public void onPlace(BlockState state, @NotNull Level level, @NotNull BlockPos pos, BlockState oldState, boolean isMoving) {
		if (!oldState.is(state.getBlock())) {
			if (level.hasNeighborSignal(pos)) {
				if (!level.isClientSide) {
					level.scheduleTick(pos, state.getBlock(), 5);
				}
			}
		}
	}

	/**
	 * Runs once every tick
	 *
	 * @param state       the <code>BlockState</code> of the block
	 * @param serverLevel the <code>ServerLevel</code> of the block
	 * @param pos         the <code>BlockPos</code> the block is at
	 * @param random      a <code>RandomSource</code> instance
	 */
	@Override
	public void tick(@NotNull BlockState state, ServerLevel serverLevel, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (!serverLevel.isClientSide) {
			checkPowered(serverLevel, pos);
			serverLevel.scheduleTick(pos, state.getBlock(), 5);
		}
	}

	/**
	 * Plays a sound when powered.
	 *
	 * @param level the <code>Level</code> the block is at
	 * @param pos   the <code>BlockPos</code> the block is at
	 */
	private void checkPowered(Level level, BlockPos pos) {
		BlockEntity blockEntity = level.getBlockEntity(pos);

		if (blockEntity instanceof PanicAlarmBlockEntity panicAlarmTileEntity) {
			if (level.getBestNeighborSignal(pos) > 0) {
				if (!panicAlarmTileEntity.isPowered()) {
					panicAlarmTileEntity.setPowered(true);
				}
			} else {
				if (panicAlarmTileEntity.isPowered()) {
					panicAlarmTileEntity.setPowered(false);
				}
			}
		}
	}
}