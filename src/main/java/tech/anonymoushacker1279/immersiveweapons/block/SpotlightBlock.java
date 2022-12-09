package tech.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SpotlightBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty LIT = BlockStateProperties.LIT;
	private static final List<BlockPos> lightPositions = new ArrayList<>(8);
	private static final BlockState airState = Blocks.AIR.defaultBlockState();
	private static final BlockState lightState = Blocks.LIGHT.defaultBlockState();

	/**
	 * Constructor for SpotlightBlock.
	 *
	 * @param properties the <code>Properties</code> of the block
	 */
	public SpotlightBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, false).setValue(FACING, Direction.NORTH)
				.setValue(LIT, false));
	}

	/**
	 * Create the BlockState definition.
	 *
	 * @param builder the <code>StateContainer.Builder</code> of the block
	 */
	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED, FACING, LIT);
	}

	/**
	 * Determines if the block can exist in a given state.
	 *
	 * @param state  the <code>BlockState</code> of the block
	 * @param reader the <code>LevelReader</code> for the block
	 * @param pos    the <code>BlocKPos</code> the block is at
	 * @return boolean
	 */
	@Override
	public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
		Direction direction = state.getValue(FACING);
		BlockPos positionOpposite = pos.relative(direction.getOpposite());
		BlockState stateOpposite = reader.getBlockState(positionOpposite);
		return stateOpposite.isFaceSturdy(reader, positionOpposite, direction);
	}

	/**
	 * Updates the block when required.
	 *
	 * @param stateIn       the <code>BlockState</code> of the block
	 * @param facing        the <code>Direction</code> the block is facing
	 * @param facingState   the <code>BlockState</code> of the facing block
	 * @param levelAccessor the <code>LevelAccessor</code> the block is in
	 * @param currentPos    the <code>BlockPos</code> the block is at
	 * @param facingPos     the <code>BlocKPos</code> the facing block is at
	 * @return BlockState
	 */
	@Override
	public @NotNull BlockState updateShape(BlockState stateIn, @NotNull Direction facing, @NotNull BlockState facingState,
	                                       @NotNull LevelAccessor levelAccessor, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {

		if (stateIn.getValue(WATERLOGGED)) {
			levelAccessor.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
		}
		return facing.getOpposite() == stateIn.getValue(FACING) && !stateIn.canSurvive(levelAccessor, currentPos)
				? Blocks.AIR.defaultBlockState() : stateIn;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())
				.setValue(LIT, false)
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
			boolean isLit = state.getValue(LIT);
			if (isLit != level.hasNeighborSignal(pos)) {
				if (isLit) {
					level.scheduleTick(pos, this, 1);
				} else {
					stateToggled(pos, level, state, state.getValue(LIT));
					level.setBlock(pos, state.cycle(LIT), 3);
				}
			}
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
				stateToggled(pos, level, state, false);
				level.setBlock(pos, state.setValue(LIT, true), 3);
			}
		}
	}

	private void stateToggled(BlockPos pos, Level level, BlockState state, boolean lit) {
		// Start building a list of light positions in front of the block
		if (!level.isClientSide) {
			Direction facing = state.getValue(FACING);
			BlockPos orientedPos = pos.relative(facing);
			lightPositions.add(orientedPos.below());
			lightPositions.add(orientedPos.below().relative(facing));
			lightPositions.add(orientedPos.below(2).relative(facing, 2));
			lightPositions.add(orientedPos.below(2).relative(facing, 3));
			lightPositions.add(orientedPos.below(3).relative(facing, 4));
			lightPositions.add(orientedPos.below(3).relative(facing, 5));
			lightPositions.add(orientedPos.below(4).relative(facing, 6));
			lightPositions.add(orientedPos.below(4).relative(facing, 7));

			int iteration = 0;
			for (BlockPos position : lightPositions) {
				if (!lit && level.getBlockState(position) == airState) {
					level.setBlockAndUpdate(position, lightState.setValue(LightBlock.LEVEL, 15 - iteration));
				}
				if (lit && level.getBlockState(position).getBlock() == Blocks.LIGHT) {
					level.setBlockAndUpdate(position, airState);
				}
				iteration++;
			}
			lightPositions.clear();
		}
	}

	@Override
	public void destroy(@NotNull LevelAccessor pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState) {
		super.destroy(pLevel, pPos, pState);
		stateToggled(pPos, (Level) pLevel, pState, true);
	}

	/**
	 * Get the light value of the block.
	 *
	 * @param state  the <code>BlockState</code> of the block
	 * @param reader the <code>BlockGetter</code> of the block
	 * @param pos    the <code>BlockPos</code> the block is at
	 * @return int
	 */
	@Override
	public int getLightEmission(BlockState state, BlockGetter reader, BlockPos pos) {
		if (state.getValue(LIT)) {
			return 15;
		} else {
			return 0;
		}
	}
}