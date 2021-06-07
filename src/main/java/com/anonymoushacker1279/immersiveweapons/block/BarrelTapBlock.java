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

	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	protected static final VoxelShape SHAPE_NORTH = Block.box(7.0D, 4.0D, 0.0D, 9.0D, 7.0D, 3.0D);
	protected static final VoxelShape SHAPE_SOUTH = Block.box(7.0D, 4.0D, 13.0D, 9.0D, 7.0D, 16.0D);
	protected static final VoxelShape SHAPE_EAST = Block.box(0.0D, 4.0D, 7.0D, 3.0D, 7.0D, 9.0D);
	protected static final VoxelShape SHAPE_WEST = Block.box(13.0D, 4.0D, 7.0D, 16.0D, 7.0D, 9.0D);
	private String directionToUse = "north"; // Default: check North for a barrel

	public BarrelTapBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(
				this.stateDefinition.any().setValue(FACING, Direction.NORTH)
		);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		Vector3d vector3d = state.getOffset(worldIn, pos);
		switch (state.getValue(FACING)) {
			case NORTH:
			default:
				return SHAPE_SOUTH.move(vector3d.x, vector3d.y, vector3d.z);
			case SOUTH:
				return SHAPE_NORTH.move(vector3d.x, vector3d.y, vector3d.z);
			case EAST:
				return SHAPE_EAST.move(vector3d.x, vector3d.y, vector3d.z);
			case WEST:
				return SHAPE_WEST.move(vector3d.x, vector3d.y, vector3d.z);
		}
	}

	@Override
	public void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		BlockState blockstateNorth = worldIn.getBlockState(pos.north());
		BlockState blockstateSouth = worldIn.getBlockState(pos.south());
		BlockState blockstateEast = worldIn.getBlockState(pos.east());
		BlockState blockstateWest = worldIn.getBlockState(pos.west());

		if (blockstateNorth.is(Blocks.BARREL)) {
			directionToUse = "north";
		} else if (blockstateSouth.is(Blocks.BARREL)) {
			directionToUse = "south";
		} else if (blockstateEast.is(Blocks.BARREL)) {
			directionToUse = "east";
		} else if (blockstateWest.is(Blocks.BARREL)) {
			directionToUse = "west";
		}

		if (worldIn.isClientSide) {
			return ActionResultType.SUCCESS;
		} else {
			if (blockstateNorth.is(Blocks.BARREL) || blockstateSouth.is(Blocks.BARREL) || blockstateEast.is(Blocks.BARREL) || blockstateWest.is(Blocks.BARREL)) {

				TileEntity tileEntity;

				switch (directionToUse) {
					case "south":
						tileEntity = worldIn.getBlockEntity(pos.south());
						break;
					case "east":
						tileEntity = worldIn.getBlockEntity(pos.east());
						break;
					case "west":
						tileEntity = worldIn.getBlockEntity(pos.west());
						break;
					default:
						tileEntity = worldIn.getBlockEntity(pos.north());
						break;
				}

				ItemStack itemStack;

				if (tileEntity != null) {
					for (int i = 0; i < ((IInventory) tileEntity).getContainerSize(); ++i) {
						itemStack = ((IInventory) tileEntity).getItem(i);

						// Define the various recipes
						// They will be checked in order, so items higher in the list
						// will be made before lower items

						// Bottle of Alcohol
						if (itemStack.getItem() == Items.WHEAT && itemStack.getCount() >= 16) {
							if (player.getMainHandItem().getItem() == Items.GLASS_BOTTLE) {
								player.addItem(new ItemStack(DeferredRegistryHandler.BOTTLE_OF_ALCOHOL.get()));
								itemStack.shrink(16);
								if (!player.abilities.instabuild) {
									player.getMainHandItem().shrink(1);
								}
								i = ((IInventory) tileEntity).getContainerSize();
							}
						}
						// Bottle of Wine
						else if (itemStack.getItem() == Items.SWEET_BERRIES && itemStack.getCount() >= 16) {
							if (player.getMainHandItem().getItem() == Items.GLASS_BOTTLE) {
								player.addItem(new ItemStack(DeferredRegistryHandler.BOTTLE_OF_WINE.get()));
								itemStack.shrink(16);
								if (!player.abilities.instabuild) {
									player.getMainHandItem().shrink(1);
								}
								i = ((IInventory) tileEntity).getContainerSize();
							}
						}
					}
				}
				return ActionResultType.SUCCESS;
			}
			return ActionResultType.FAIL;
		}
	}
}