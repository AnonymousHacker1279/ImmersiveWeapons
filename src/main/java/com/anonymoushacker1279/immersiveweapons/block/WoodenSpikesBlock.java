package com.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class WoodenSpikesBlock extends HorizontalBlock implements IWaterLoggable {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15D, 14D, 15D);
	private final DamageSource damageSource = new DamageSource("immersiveweapons.spike_trap");

	/**
	 * Constructor for WoodenSpikesBlock.
	 * @param properties the <code>Properties</code> of the block
	 */
	public WoodenSpikesBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, Boolean.FALSE));
	}

	/**
	 * Set placement properties.
	 * Sets the facing direction of the block for placement.
	 * @param context the <code>BlockItemUseContext</code> during placement
	 * @return BlockState
	 */
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
	}

	/**
	 * Set FluidState properties.
	 * Allows the block to exhibit waterlogged behavior.
	 * @param state the <code>BlockState</code> of the block
	 * @return FluidState
	 */
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	/**
	 * Create the BlockState definition.
	 * @param builder the <code>StateContainer.Builder</code> of the block
	 */
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED, FACING);
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
	public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext selectionContext) {
		Vector3d vector3d = state.getOffset(reader, pos);
		return SHAPE.move(vector3d.x, vector3d.y, vector3d.z);
	}

	/**
	 * Determines if skylight should pass through the block.
	 * @param state the <code>BlockState</code> of the block
	 * @param reader the <code>IBlockReader</code> for the block
	 * @param pos the <code>BlockPos</code> the block is at
	 * @return boolean
	 */
	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
		return true;
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
	public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {
		if (entity instanceof LivingEntity) {
			entity.makeStuckInBlock(state, new Vector3d(0.85F, 0.80D, 0.85F));
			if (!world.isClientSide && (entity.xOld != entity.getX() || entity.zOld != entity.getZ())) {
				double d0 = Math.abs(entity.getX() - entity.xOld);
				double d1 = Math.abs(entity.getZ() - entity.zOld);
				if (d0 >= (double) 0.003F || d1 >= (double) 0.003F) {
					entity.hurt(damageSource, 1.5F);
				}
			}
		}
	}
}