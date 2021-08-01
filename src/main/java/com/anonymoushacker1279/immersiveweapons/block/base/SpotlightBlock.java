package com.anonymoushacker1279.immersiveweapons.block.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class SpotlightBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {

	private static final BooleanProperty LIT = BlockStateProperties.LIT;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	/**
	 * Constructor for SpotlightBlock.
	 * @param properties the <code>Properties</code> of the block
	 */
	public SpotlightBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, false).setValue(FACING, Direction.NORTH).setValue(LIT, false));
	}

	/**
	 * Create the BlockState definition.
	 * @param builder the <code>StateContainer.Builder</code> of the block
	 */
	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED, FACING, LIT);
	}

	/**
	 * Determines if the block can exist in a given state.
	 * @param state the <code>BlockState</code> of the block
	 * @param reader the <code>IWorldReader</code> for the block
	 * @param pos the <code>BlocKPos</code> the block is at
	 * @return boolean
	 */
	@Override
	public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
		Direction direction = state.getValue(FACING);
		BlockPos blockpos = pos.relative(direction.getOpposite());
		BlockState blockstate = reader.getBlockState(blockpos);
		return blockstate.isFaceSturdy(reader, blockpos, direction);
	}

	/**
	 * Updates the block when required.
	 * @param stateIn the <code>BlockState</code> of the block
	 * @param facing the <code>Direction</code> the block is facing
	 * @param facingState the <code>BlockState</code> of the facing block
	 * @param worldIn the <code>IWorld</code> the block is in
	 * @param currentPos the <code>BlockPos</code> the block is at
	 * @param facingPos the <code>BlocKPos</code> the facing block is at
	 * @return BlockState
	 */
	@Override
	public @NotNull BlockState updateShape(BlockState stateIn, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor worldIn, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
		if (stateIn.getValue(WATERLOGGED)) {
			worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
		}
		return facing.getOpposite() == stateIn.getValue(FACING) && !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : stateIn;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(LIT, false).setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
	}

	/**
	 * Set FluidState properties.
	 * Allows the block to exhibit waterlogged behavior.
	 * @param state the <code>BlockState</code> of the block
	 * @return FluidState
	 */
	@Override
	public @NotNull FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	/**
	 * Runs when neighboring blocks change state.
	 * @param state the <code>BlockState</code> of the block
	 * @param worldIn the <code>World</code> the block is in
	 * @param pos the <code>BlockPos</code> the block is at
	 * @param blockIn the <code>Block</code> that is changing
	 * @param fromPos the <code>BlockPos</code> of the changing block
	 * @param isMoving determines if the block is moving
	 */
	@Override
	public void neighborChanged(@NotNull BlockState state, Level worldIn, @NotNull BlockPos pos, @NotNull Block blockIn, @NotNull BlockPos fromPos, boolean isMoving) {
		if (!worldIn.isClientSide) {
			boolean flag = state.getValue(LIT);
			if (flag != worldIn.hasNeighborSignal(pos)) {
				if (flag) {
					worldIn.getBlockTicks().scheduleTick(pos, this, 1);
				} else {
					worldIn.setBlock(pos, state.cycle(LIT), 3);
				}
			}
		}
	}

	/**
	 * Runs when neighboring blocks change state.
	 * @param state the <code>BlockState</code> of the block
	 * @param worldIn the <code>World</code> the block is in
	 * @param pos the <code>BlockPos</code> the block is at
	 * @param oldState the <code>BlockState</code> the block previously had
	 * @param isMoving determines if the block is moving
	 */
	@Override
	public void onPlace(BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos, BlockState oldState, boolean isMoving) {
		if (!oldState.is(state.getBlock())) {
			if (worldIn.hasNeighborSignal(pos)) {
				worldIn.setBlock(pos, state.setValue(LIT, true), 3);
			}
		}
	}

	/**
	 * Runs once every tick
	 * @param state the <code>BlockState</code> of the block
	 * @param worldIn the <code>ServerWorld</code> of the block
	 * @param pos the <code>BlockPos</code> the block is at
	 * @param rand a <code>Random</code> instance
	 */
	@Override
	public void tick(BlockState state, @NotNull ServerLevel worldIn, @NotNull BlockPos pos, @NotNull Random rand) {
		if (state.getValue(LIT) && !worldIn.hasNeighborSignal(pos)) {
			worldIn.setBlock(pos, state.setValue(LIT, false), 3);
		}
	}

	/**
	 * Get the light value of the block.
	 * @param state the <code>BlockState</code> of the block
	 * @param reader the <code>IBlockReader</code> of the block
	 * @param pos the <code>BlockPos</code> the block is at
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