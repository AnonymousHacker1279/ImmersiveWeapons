package tech.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.*;

public class PitfallBlock extends Block {

	protected static final VoxelShape SHAPE = Block.box(0.0D, 15.0D, 0.0D, 16.0D, 16D, 16.0D);

	/**
	 * Constructor for PitfallBlock.
	 *
	 * @param properties the <code>Properties</code> of the block
	 */
	public PitfallBlock(Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext collisionContext) {
		return SHAPE;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext collisionContext) {
		return Shapes.empty();
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
	                              LevelAccessor levelAccessor, BlockPos currentPos,
	                              BlockPos neighborPos) {

		return !state.canSurvive(levelAccessor, currentPos) ? Blocks.AIR.defaultBlockState()
				: super.updateShape(state, direction, neighborState, levelAccessor, currentPos, neighborPos);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
		for (Direction facing : new Direction[]{Direction.EAST, Direction.WEST, Direction.SOUTH, Direction.NORTH}) {

			BlockPos relativePosition = pos.relative(facing);
			BlockState blockstate = reader.getBlockState(relativePosition);
			if (Block.canSupportCenter(reader, relativePosition, facing.getOpposite()) || blockstate.getBlock() == this) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (entity instanceof LivingEntity && !level.isClientSide) {
			level.destroyBlock(pos, true);
		}
	}
}