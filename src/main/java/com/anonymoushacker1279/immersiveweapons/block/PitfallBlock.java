package com.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
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
	 * @param properties the <code>Properties</code> of the block
	 */
	public PitfallBlock(Properties properties) {
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
	public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext selectionContext) {
		return SHAPE;
	}

	/**
	 * Get the collision shape of the block.
	 * @param state the <code>BlockState</code> of the block
	 * @param reader the <code>IBlockReader</code> for the block
	 * @param pos the <code>BlockPos</code> the block is at
	 * @param selectionContext the <code>ISelectionContext</code> of the block
	 * @return VoxelShape
	 */
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext selectionContext) {
		return Shapes.empty();
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
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
		return !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
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
		for (Direction facing : new Direction[]{Direction.EAST, Direction.WEST, Direction.SOUTH, Direction.NORTH}) {
			BlockPos posOff = pos.relative(facing);
			BlockState blockstate = reader.getBlockState(posOff);
			if (Block.canSupportCenter(reader, posOff, facing.getOpposite()) || blockstate.getBlock() == this)
				return true;
		}
		return false;
	}

	/**
	 * Runs when an entity is inside of the block's collision area.
	 * Allows the block to deal damage on contact.
	 * @param state the <code>BlockState</code> of the block
	 * @param world the <code>World</code> the block is in
	 * @param pos the <code>BlockPos</code> the block is at
	 * @param entity the <code>Entity</code> passing through the block
	 */
	@Override
	public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
		if (entity instanceof LivingEntity && !world.isClientSide) {
			world.destroyBlock(pos, true);
		}
	}
}