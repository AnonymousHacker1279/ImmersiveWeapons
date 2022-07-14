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
import org.jetbrains.annotations.NotNull;

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

	/**
	 * Set the shape of the block.
	 *
	 * @param state            the <code>BlockState</code> of the block
	 * @param reader           the <code>BlockGetter</code> for the block
	 * @param pos              the <code>BlockPos</code> the block is at
	 * @param collisionContext the <code>CollisionContext</code> of the block
	 * @return VoxelShape
	 */
	@SuppressWarnings("deprecation")
	@Override
	public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos pos,
	                                    @NotNull CollisionContext collisionContext) {

		return SHAPE;
	}

	/**
	 * Get the collision shape of the block.
	 *
	 * @param state            the <code>BlockState</code> of the block
	 * @param reader           the <code>BlockGetter</code> for the block
	 * @param pos              the <code>BlockPos</code> the block is at
	 * @param collisionContext the <code>CollisionContext</code> of the block
	 * @return VoxelShape
	 */
	@SuppressWarnings("deprecation")
	@Override
	public @NotNull VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter reader,
	                                             @NotNull BlockPos pos, @NotNull CollisionContext collisionContext) {

		return Shapes.empty();
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
	@SuppressWarnings("deprecation")
	@Override
	public @NotNull BlockState updateShape(BlockState stateIn, @NotNull Direction facing, @NotNull BlockState facingState,
	                                       @NotNull LevelAccessor levelAccessor, @NotNull BlockPos currentPos,
	                                       @NotNull BlockPos facingPos) {

		return !stateIn.canSurvive(levelAccessor, currentPos) ? Blocks.AIR.defaultBlockState()
				: super.updateShape(stateIn, facing, facingState, levelAccessor, currentPos, facingPos);
	}

	/**
	 * Determines if the block can exist in a given state.
	 *
	 * @param state  the <code>BlockState</code> of the block
	 * @param reader the <code>LevelReader</code> for the block
	 * @param pos    the <code>BlocKPos</code> the block is at
	 * @return boolean
	 */
	@SuppressWarnings("deprecation")
	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader reader, @NotNull BlockPos pos) {
		for (Direction facing : new Direction[]{Direction.EAST, Direction.WEST, Direction.SOUTH, Direction.NORTH}) {

			BlockPos relativePosition = pos.relative(facing);
			BlockState blockstate = reader.getBlockState(relativePosition);
			if (Block.canSupportCenter(reader, relativePosition, facing.getOpposite()) || blockstate.getBlock() == this) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Runs when an entity is inside the block's collision area.
	 *
	 * @param state  the <code>BlockState</code> of the block
	 * @param level  the <code>Level</code> the block is in
	 * @param pos    the <code>BlockPos</code> the block is at
	 * @param entity the <code>Entity</code> passing through the block
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
		if (entity instanceof LivingEntity && !level.isClientSide) {
			level.destroyBlock(pos, true);
		}
	}
}