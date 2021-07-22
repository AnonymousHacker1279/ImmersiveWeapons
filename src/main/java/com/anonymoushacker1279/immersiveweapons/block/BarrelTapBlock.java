package com.anonymoushacker1279.immersiveweapons.block;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
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

public class BarrelTapBlock extends HorizontalBlock implements IWaterLoggable {

	protected static final VoxelShape SHAPE_NORTH = Block.box(7.0D, 4.0D, 0.0D, 9.0D, 7.0D, 3.0D);
	protected static final VoxelShape SHAPE_SOUTH = Block.box(7.0D, 4.0D, 13.0D, 9.0D, 7.0D, 16.0D);
	protected static final VoxelShape SHAPE_EAST = Block.box(0.0D, 4.0D, 7.0D, 3.0D, 7.0D, 9.0D);
	protected static final VoxelShape SHAPE_WEST = Block.box(13.0D, 4.0D, 7.0D, 16.0D, 7.0D, 9.0D);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	private String directionToUse = "north"; // Default: check North for a barrel

	/**
	 * Constructor for BarrelTapBlock.
	 * @param properties the <code>Properties</code> of the block
	 */
	public BarrelTapBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
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
				return SHAPE_SOUTH.move(vector3d.x, vector3d.y, vector3d.z);
			case SOUTH:
				return SHAPE_NORTH.move(vector3d.x, vector3d.y, vector3d.z);
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
		BlockState blockStateNorth = worldIn.getBlockState(pos.north());
		BlockState blockStateSouth = worldIn.getBlockState(pos.south());
		BlockState blockStateEast = worldIn.getBlockState(pos.east());
		BlockState blockStateWest = worldIn.getBlockState(pos.west());

		if (blockStateNorth.is(Blocks.BARREL)) {
			directionToUse = "north";
		} else if (blockStateSouth.is(Blocks.BARREL)) {
			directionToUse = "south";
		} else if (blockStateEast.is(Blocks.BARREL)) {
			directionToUse = "east";
		} else if (blockStateWest.is(Blocks.BARREL)) {
			directionToUse = "west";
		}

		if (worldIn.isClientSide) {
			return ActionResultType.SUCCESS;
		} else {
			if (blockStateNorth.is(Blocks.BARREL) || blockStateSouth.is(Blocks.BARREL) || blockStateEast.is(Blocks.BARREL) || blockStateWest.is(Blocks.BARREL)) {

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
								if (!player.isCreative()) {
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
								if (!player.isCreative()) {
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