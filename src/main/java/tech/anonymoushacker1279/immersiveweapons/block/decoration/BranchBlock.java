package tech.anonymoushacker1279.immersiveweapons.block.decoration;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.block.core.BasicOrientableBlock;

public class BranchBlock extends BasicOrientableBlock {

	private static final VoxelShape SHAPE_NORTH = Block.box(4.0D, 0.0D, 0.0D, 12.0D, 5.0D, 3.0D);
	private static final VoxelShape SHAPE_SOUTH = Block.box(4.0D, 0.0D, 13.0D, 12.0D, 5.0D, 16.0D);
	private static final VoxelShape SHAPE_EAST = Block.box(13.0D, 0.0D, 4.0D, 16.0D, 5.0D, 12.0D);
	private static final VoxelShape SHAPE_WEST = Block.box(0.0D, 0.0D, 4.0D, 3.0D, 5.0D, 12.0D);

	public static final TagKey<Block> BURNED_OAK_LOGS = BlockTags.create(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "burned_oak_logs"));

	/**
	 * Constructor for BranchBlock.
	 *
	 * @param properties The <code>Properties</code> of the block
	 */
	public BranchBlock(Properties properties) {
		super(properties);
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
	public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
		BlockState blockstate = reader.getBlockState(pos.relative(state.getValue(FACING)));
		return blockstate.is(BURNED_OAK_LOGS);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
	                              LevelAccessor level, BlockPos pos, BlockPos neighborPos) {

		return direction == state.getValue(FACING) && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState()
				: super.updateShape(state, direction, neighborState, level, pos, neighborPos);
	}
}