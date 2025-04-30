package tech.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

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
	protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
		return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState()
				: super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
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