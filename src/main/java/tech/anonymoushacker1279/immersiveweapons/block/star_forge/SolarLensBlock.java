package tech.anonymoushacker1279.immersiveweapons.block.star_forge;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AbstractGlassBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;

public class SolarLensBlock extends AbstractGlassBlock {

	protected static final VoxelShape SHAPE = Block.box(0.0D, 6.0D, 0.0D, 16.0D, 9.0D, 16.0D);

	public SolarLensBlock(Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
		return SHAPE;
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {

		int validSides = 0;
		for (Direction facing : new Direction[]{Direction.EAST, Direction.WEST, Direction.SOUTH, Direction.NORTH}) {

			BlockPos relativePosition = pos.relative(facing);
			BlockState relativeState = reader.getBlockState(relativePosition);
			if (relativeState.getBlock() == BlockRegistry.STAR_FORGE_BRICKS.get()) {
				validSides++;
			}
		}

		return validSides == 4;
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
	                              LevelAccessor levelAccessor, BlockPos currentPos,
	                              BlockPos neighborPos) {

		return !state.canSurvive(levelAccessor, currentPos) ? Blocks.AIR.defaultBlockState()
				: super.updateShape(state, direction, neighborState, levelAccessor, currentPos, neighborPos);
	}
}