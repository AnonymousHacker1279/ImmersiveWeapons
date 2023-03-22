package tech.anonymoushacker1279.immersiveweapons.block.decoration;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import tech.anonymoushacker1279.immersiveweapons.block.core.BasicOrientableBlock;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.IWBlockTagGroups;

public class BranchBlock extends BasicOrientableBlock {

	private static final VoxelShape SHAPE_NORTH = Block.box(4.0D, 0.0D, 0.0D, 12.0D, 5.0D, 3.0D);
	private static final VoxelShape SHAPE_SOUTH = Block.box(4.0D, 0.0D, 13.0D, 12.0D, 5.0D, 16.0D);
	private static final VoxelShape SHAPE_EAST = Block.box(13.0D, 0.0D, 4.0D, 16.0D, 5.0D, 12.0D);
	private static final VoxelShape SHAPE_WEST = Block.box(0.0D, 0.0D, 4.0D, 3.0D, 5.0D, 12.0D);

	/**
	 * Constructor for BranchBlock.
	 *
	 * @param properties The <code>Properties</code> of the block
	 */
	public BranchBlock(Properties properties) {
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
	public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos,
	                           CollisionContext collisionContext) {

		return switch (state.getValue(FACING)) {
			case SOUTH -> SHAPE_SOUTH;
			case EAST -> SHAPE_EAST;
			case WEST -> SHAPE_WEST;
			default -> SHAPE_NORTH;
		};
	}

	/**
	 * Determine if the block can survive an update.
	 *
	 * @param state  the <code>BlockState</code> of the block
	 * @param reader the <code>LevelReader</code> for the block
	 * @param pos    the <code>BlockPos</code> the block is at
	 * @return boolean
	 */
	@SuppressWarnings("deprecation")
	@Override
	public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
		BlockState blockstate = reader.getBlockState(pos.relative(state.getValue(FACING)));
		return blockstate.is(IWBlockTagGroups.BURNED_OAK_LOGS);
	}

	/**
	 * Update the block's shape.
	 *
	 * @param state         the new <code>BlockState</code> of the block
	 * @param direction     the <code>Direction</code> the block is facing
	 * @param neighborState the neighbor <code>BlockState</code> of the block
	 * @param level         the <code>LevelAccessor</code> for the block
	 * @param pos           the new <code>BlockPos</code> the block is at
	 * @param neighborPos   the old <code>BlockPos</code> the block was at
	 * @return BlockState
	 */
	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
	                              LevelAccessor level, BlockPos pos, BlockPos neighborPos) {

		return direction == state.getValue(FACING) && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState()
				: super.updateShape(state, direction, neighborState, level, pos, neighborPos);
	}
}