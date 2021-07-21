package com.anonymoushacker1279.immersiveweapons.block;

import com.anonymoushacker1279.immersiveweapons.tileentity.WallShelfTileEntity;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class ShelfBlock extends ContainerBlock implements IWaterLoggable {

	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected static final VoxelShape SHAPE_NORTH = Block.box(0.0D, 0.0D, 16.0D, 16.0D, 16.0D, 10.0D);
	protected static final VoxelShape SHAPE_SOUTH = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 6.0D);
	protected static final VoxelShape SHAPE_EAST = Block.box(0.0D, 0.0D, 0.0D, 6.0D, 16.0D, 16.0D);
	protected static final VoxelShape SHAPE_WEST = Block.box(10.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

	/**
	 * Constructor for ShelfBlock.
	 * @param properties the <code>Properties</code> of the block
	 */
	public ShelfBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
	}

	/**
	 * Set the RenderShape of the block's model.
	 * @param state the <code>BlockState</code> of the block
	 * @return BlockRenderType
	 */
	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.MODEL;
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
		switch (state.getValue(FACING)) {
			case NORTH:
			default:
				return SHAPE_NORTH.move(vector3d.x, vector3d.y, vector3d.z);
			case SOUTH:
				return SHAPE_SOUTH.move(vector3d.x, vector3d.y, vector3d.z);
			case EAST:
				return SHAPE_EAST.move(vector3d.x, vector3d.y, vector3d.z);
			case WEST:
				return SHAPE_WEST.move(vector3d.x, vector3d.y, vector3d.z);
		}
	}

	/**
	 * Create the BlockState definition.
	 * @param builder the <code>StateContainer.Builder</code> of the block
	 */
	@Override
	public void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING, WATERLOGGED);
	}

	/**
	 * Set placement properties.
	 * Sets the facing direction of the block for placement.
	 * @param context the <code>BlockItemUseContext</code> during placement
	 * @return BlockState
	 */
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
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
	 * Create a tile entity for the block.
	 * @param reader the <code>IBlockReader</code> of the block
	 * @return TileEntity
	 */
	@Override
	public TileEntity newBlockEntity(IBlockReader reader) {
		return new WallShelfTileEntity();
	}

	/**
	 * Runs when the block is activated.
	 * Allows the block to respond to user interaction.
	 * @param state the <code>BlockState</code> of the block
	 * @param worldIn the <code>World</code> the block is in
	 * @param pos the <code>BlockPos</code> the block is at
	 * @param player the <code>PlayerEntity</code> interacting with the block
	 * @param handIn the <code>Hand</code> the PlayerEntity used
	 * @param blockRayTraceResult the <code>BlockRayTraceResult</code> of the interaction
	 * @return ActionResultType
	 */
	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult blockRayTraceResult) {
		TileEntity tileentity = worldIn.getBlockEntity(pos);
		if (tileentity instanceof WallShelfTileEntity) {
			WallShelfTileEntity wallShelfTileEntity = (WallShelfTileEntity) tileentity;
			ItemStack itemstack = player.getItemInHand(handIn);
			if (itemstack.isEmpty()) {
				// If not holding anything, remove the last added item
				wallShelfTileEntity.removeItem();
				return ActionResultType.SUCCESS;
			}
			if (!worldIn.isClientSide && wallShelfTileEntity.addItem(player.isCreative() ? itemstack.copy() : itemstack)) {
				return ActionResultType.SUCCESS;
			}
			return ActionResultType.CONSUME;
		}
		return ActionResultType.PASS;
	}

	/**
	 * Runs when the block is removed.
	 * @param state the <code>BlockState</code> of the block
	 * @param worldIn the <code>World</code> the block is in
	 * @param pos the <code>BlockPos</code> the block is at
	 * @param newState the <code>BlockState</code> the block now has
	 * @param isMoving determines if the block is moving
	 */
	@Override
	public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.is(newState.getBlock())) {
			TileEntity tileentity = worldIn.getBlockEntity(pos);
			if (tileentity instanceof WallShelfTileEntity) {
				InventoryHelper.dropContents(worldIn, pos, ((WallShelfTileEntity) tileentity).getInventory());
			}
			super.onRemove(state, worldIn, pos, newState, isMoving);
		}
	}
}