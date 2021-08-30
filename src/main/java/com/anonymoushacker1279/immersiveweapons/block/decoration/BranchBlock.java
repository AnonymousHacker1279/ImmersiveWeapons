package com.anonymoushacker1279.immersiveweapons.block.decoration;

import com.anonymoushacker1279.immersiveweapons.block.core.BasicOrientableBlock;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class BranchBlock extends BasicOrientableBlock {

	private static final VoxelShape SHAPE_NORTH = Block.box(4.0D, 0.0D, 0.0D, 12.0D, 5.0D, 3.0D);
	private static final VoxelShape SHAPE_SOUTH = Block.box(4.0D, 0.0D, 13.0D, 12.0D, 5.0D, 16.0D);
	private static final VoxelShape SHAPE_EAST = Block.box(13.0D, 0.0D, 4.0D, 16.0D, 5.0D, 12.0D);
	private static final VoxelShape SHAPE_WEST = Block.box(0.0D, 0.0D, 4.0D, 3.0D, 5.0D, 12.0D);

	/**
	 * Constructor for BranchBlock.
	 * @param properties The <code>Properties</code> of the block
	 */
	public BranchBlock(Properties properties) {
		super(properties);
	}

	/**
	 * Set the shape of the block.
	 * @param state the <code>BlockState</code> of the block
	 * @param reader the <code>IBlockReader</code> for the block
	 * @param pos the <code>BlockPos</code> the block is at
	 * @param selectionContext the <code>ISelectionContext</code> of the block
	 * @return VoxelShape
	 */
	@Override
	public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos pos, @NotNull CollisionContext selectionContext) {
		Vec3 vector3d = state.getOffset(reader, pos);
		return switch (state.getValue(FACING)) {
			default -> SHAPE_NORTH.move(vector3d.x, vector3d.y, vector3d.z);
			case SOUTH -> SHAPE_SOUTH.move(vector3d.x, vector3d.y, vector3d.z);
			case EAST -> SHAPE_EAST.move(vector3d.x, vector3d.y, vector3d.z);
			case WEST -> SHAPE_WEST.move(vector3d.x, vector3d.y, vector3d.z);
		};
	}

	/**
	 * Determine if the block can survive an update.
	 * @param state the <code>BlockState</code> of the block
	 * @param reader the <code>LevelReader</code> for the block
	 * @param pos the <code>BlockPos</code> the block is at
	 * @return boolean
	 */
	@Override
	public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
		BlockState blockstate = reader.getBlockState(pos.relative(state.getValue(FACING)));
		return blockstate.is(DeferredRegistryHandler.BURNED_OAK_LOG.get());
	}

	/**
	 * Update the block's shape.
	 * @param state the new <code>BlockState</code> of the block
	 * @param direction the <code>Direction</code> the block is facing
	 * @param state1 the old <code>BlockState</code> of the block
	 * @param accessor the <code>LevelAccessor</code> for the block
	 * @param pos the new <code>BlockPos</code> the block is at
	 * @param pos1 the old <code>BlockPos</code> the block was at
	 * @return BlockState
	 */
	@Override
	public @NotNull BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState state1, @NotNull LevelAccessor accessor, @NotNull BlockPos pos, @NotNull BlockPos pos1) {
		return direction == state.getValue(FACING) && !state.canSurvive(accessor, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, state1, accessor, pos, pos1);
	}
}