package com.anonymoushacker1279.immersiveweapons.block;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
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

public class BarrelTapBlock extends HorizontalBlock {

	protected static final VoxelShape SHAPE_NORTH = Block.makeCuboidShape(7.0D, 4.0D, 0.0D, 9.0D, 7.0D, 3.0D);
	protected static final VoxelShape SHAPE_SOUTH = Block.makeCuboidShape(7.0D, 4.0D, 13.0D, 9.0D, 7.0D, 16.0D);
	protected static final VoxelShape SHAPE_EAST = Block.makeCuboidShape(0.0D, 4.0D, 7.0D, 3.0D, 7.0D, 9.0D);
	protected static final VoxelShape SHAPE_WEST = Block.makeCuboidShape(13.0D, 4.0D, 7.0D, 16.0D, 7.0D, 9.0D);
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

	private BlockState blockstateNorth;
	private BlockState blockstateSouth;
	private BlockState blockstateEast;
	private BlockState blockstateWest;

	public BarrelTapBlock(Properties properties) {
		super(properties);
		this.setDefaultState(
				this.stateContainer.getBaseState().with(FACING, Direction.NORTH)
		);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		Vector3d vector3d = state.getOffset(worldIn, pos);
		switch (state.get(FACING)) {
			case NORTH:
			default:
				return SHAPE_SOUTH.withOffset(vector3d.x, vector3d.y, vector3d.z);
			case SOUTH:
				return SHAPE_NORTH.withOffset(vector3d.x, vector3d.y, vector3d.z);
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

	private String directionToUse = "north"; // Default: check North for a barrel

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		blockstateNorth = worldIn.getBlockState(pos.north());
		blockstateSouth = worldIn.getBlockState(pos.south());
		blockstateEast = worldIn.getBlockState(pos.east());
		blockstateWest = worldIn.getBlockState(pos.west());

		if (blockstateNorth.matchesBlock(Blocks.BARREL)) {
			directionToUse = "north";
		} else if (blockstateSouth.matchesBlock(Blocks.BARREL)) {
			directionToUse = "south";
		} else if (blockstateEast.matchesBlock(Blocks.BARREL)) {
			directionToUse = "east";
		} else if (blockstateWest.matchesBlock(Blocks.BARREL)) {
			directionToUse = "west";
		}

		if (worldIn.isRemote) {
			return ActionResultType.SUCCESS;
		} else {
			if (blockstateNorth.matchesBlock(Blocks.BARREL) || blockstateSouth.matchesBlock(Blocks.BARREL) || blockstateEast.matchesBlock(Blocks.BARREL) || blockstateWest.matchesBlock(Blocks.BARREL)) {

				TileEntity tileEntity = worldIn.getTileEntity(pos.north());

				switch (directionToUse) {
					case "south":
						tileEntity = worldIn.getTileEntity(pos.south());
						break;
					case "east":
						tileEntity = worldIn.getTileEntity(pos.east());
						break;
					case "west":
						tileEntity = worldIn.getTileEntity(pos.west());
						break;
					default:
						tileEntity = worldIn.getTileEntity(pos.north());
						break;
				}

				ItemStack itemStack;

				for (int i = 0; i < ((IInventory) tileEntity).getSizeInventory(); ++i) {
					itemStack = ((IInventory) tileEntity).getStackInSlot(i);

					// Define the various recipes
					// They will be checked in order, so items higher in the list
					// will be made before lower items

					// Bottle of Alcohol
					if (itemStack.getItem() == Items.WHEAT && itemStack.getCount() >= 16) {
						if (player.getHeldItemMainhand().getItem() == Items.GLASS_BOTTLE) {
							player.addItemStackToInventory(new ItemStack(DeferredRegistryHandler.BOTTLE_OF_ALCOHOL.get()));
							itemStack.shrink(16);
							if (!player.abilities.isCreativeMode) {
								player.getHeldItemMainhand().shrink(1);
							}
							i = ((IInventory) tileEntity).getSizeInventory();
						}
					}
					// Bottle of Wine
					else if (itemStack.getItem() == Items.SWEET_BERRIES && itemStack.getCount() >= 16) {
						if (player.getHeldItemMainhand().getItem() == Items.GLASS_BOTTLE) {
							player.addItemStackToInventory(new ItemStack(DeferredRegistryHandler.BOTTLE_OF_WINE.get()));
							itemStack.shrink(16);
							if (!player.abilities.isCreativeMode) {
								player.getHeldItemMainhand().shrink(1);
							}
							i = ((IInventory) tileEntity).getSizeInventory();
						}
					}
				}
				return ActionResultType.SUCCESS;
			}
			return ActionResultType.FAIL;
		}
	}
}