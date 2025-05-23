package tech.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.redstone.Orientation;
import tech.anonymoushacker1279.immersiveweapons.block.core.BasicOrientableBlock;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SpotlightBlock extends BasicOrientableBlock implements SimpleWaterloggedBlock {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty LIT = BlockStateProperties.LIT;
	private static final List<BlockPos> lightPositions = new ArrayList<>(8);
	private static final BlockState airState = Blocks.AIR.defaultBlockState();
	private static final BlockState lightState = Blocks.LIGHT.defaultBlockState();

	public SpotlightBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, false).setValue(FACING, Direction.NORTH)
				.setValue(LIT, false));
	}

	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED, FACING, LIT);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
		Direction direction = state.getValue(FACING);
		BlockPos positionOpposite = pos.relative(direction.getOpposite());
		BlockState stateOpposite = reader.getBlockState(positionOpposite);
		return stateOpposite.isFaceSturdy(reader, positionOpposite, direction);
	}

	@Override
	protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
		return direction.getOpposite() == state.getValue(FACING) && !state.canSurvive(level, pos)
				? Blocks.AIR.defaultBlockState() : state;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())
				.setValue(LIT, false)
				.setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, @Nullable Orientation orientation, boolean movedByPiston) {
		if (!level.isClientSide) {
			boolean isLit = state.getValue(LIT);
			if (!isLit && level.hasNeighborSignal(pos)) {
				stateToggled(pos, level, state, state.getValue(LIT));
				level.setBlock(pos, state.cycle(LIT), 3);
			} else if (isLit && !level.hasNeighborSignal(pos)) {
				stateToggled(pos, level, state, state.getValue(LIT));
				level.setBlock(pos, state.cycle(LIT), 3);
			}
		}
	}

	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
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
	protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean movedByPiston) {
		stateToggled(pos, level, state, true);
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