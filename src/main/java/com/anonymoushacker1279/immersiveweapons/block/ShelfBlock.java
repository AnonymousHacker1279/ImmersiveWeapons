package com.anonymoushacker1279.immersiveweapons.block;

import com.anonymoushacker1279.immersiveweapons.tileentity.WallShelfTileEntity;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class ShelfBlock extends ContainerBlock {

	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	protected static final VoxelShape SHAPE_NORTH = Block.makeCuboidShape(0.0D, 0.0D, 16.0D, 16.0D, 16.0D, 10.0D);
	protected static final VoxelShape SHAPE_SOUTH = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 6.0D);
	protected static final VoxelShape SHAPE_EAST = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 6.0D, 16.0D, 16.0D);
	protected static final VoxelShape SHAPE_WEST = Block.makeCuboidShape(10.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

	public ShelfBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(FACING, rot.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.toRotation(state.get(FACING)));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		Vector3d vector3d = state.getOffset(worldIn, pos);
		switch (state.get(FACING)) {
			case NORTH:
			default:
				return SHAPE_NORTH.withOffset(vector3d.x, vector3d.y, vector3d.z);
			case SOUTH:
				return SHAPE_SOUTH.withOffset(vector3d.x, vector3d.y, vector3d.z);
			case EAST:
				return SHAPE_EAST.withOffset(vector3d.x, vector3d.y, vector3d.z);
			case WEST:
				return SHAPE_WEST.withOffset(vector3d.x, vector3d.y, vector3d.z);
		}
	}

	@Override
	public void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader world) {
		return new WallShelfTileEntity();
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if (tileentity instanceof WallShelfTileEntity) {
			WallShelfTileEntity wallShelfTileEntity = (WallShelfTileEntity) tileentity;
			ItemStack itemstack = player.getHeldItem(handIn);
			if (itemstack.isEmpty()) {
				// If not holding anything, remove the last added item
				wallShelfTileEntity.removeItem();
				return ActionResultType.SUCCESS;
			}
			if (!worldIn.isRemote && wallShelfTileEntity.addItem(player.abilities.isCreativeMode ? itemstack.copy() : itemstack)) {
				return ActionResultType.SUCCESS;
			}
			return ActionResultType.CONSUME;
		}
		return ActionResultType.PASS;
	}

	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.matchesBlock(newState.getBlock())) {
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof WallShelfTileEntity) {
				InventoryHelper.dropItems(worldIn, pos, ((WallShelfTileEntity) tileentity).getInventory());
			}

			super.onReplaced(state, worldIn, pos, newState, isMoving);
		}
	}
}