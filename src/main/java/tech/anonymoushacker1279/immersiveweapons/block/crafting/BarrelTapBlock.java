package tech.anonymoushacker1279.immersiveweapons.block.crafting;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

public class BarrelTapBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	private static final VoxelShape SHAPE_NORTH = Block.box(7.0D, 4.0D, 0.0D, 9.0D, 7.0D, 3.0D);
	private static final VoxelShape SHAPE_SOUTH = Block.box(7.0D, 4.0D, 13.0D, 9.0D, 7.0D, 16.0D);
	private static final VoxelShape SHAPE_EAST = Block.box(0.0D, 4.0D, 7.0D, 3.0D, 7.0D, 9.0D);
	private static final VoxelShape SHAPE_WEST = Block.box(13.0D, 4.0D, 7.0D, 16.0D, 7.0D, 9.0D);
	private String directionToUse = "north"; // Default: check North for a barrel

	/**
	 * Constructor for BarrelTapBlock.
	 *
	 * @param properties the <code>Properties</code> of the block
	 */
	public BarrelTapBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
	}

	/**
	 * Set FluidState properties.
	 * Allows the block to exhibit waterlogged behavior.
	 *
	 * @param state the <code>BlockState</code> of the block
	 * @return FluidState
	 */
	@SuppressWarnings("deprecation")
	@Override
	public @NotNull FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	/**
	 * Set the shape of the block.
	 *
	 * @param state            the <code>BlockState</code> of the block
	 * @param reader           the <code>IBlockReader</code> for the block
	 * @param pos              the <code>BlockPos</code> the block is at
	 * @param selectionContext the <code>ISelectionContext</code> of the block
	 * @return VoxelShape
	 */
	@SuppressWarnings("deprecation")
	@Override
	public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos pos, @NotNull CollisionContext selectionContext) {
		return switch (state.getValue(FACING)) {
			case SOUTH -> SHAPE_NORTH;
			case EAST -> SHAPE_EAST;
			case WEST -> SHAPE_WEST;
			default -> SHAPE_SOUTH;
		};
	}

	/**
	 * Create the BlockState definition.
	 *
	 * @param builder the <code>StateContainer.Builder</code> of the block
	 */
	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, WATERLOGGED);
	}

	/**
	 * Set placement properties.
	 * Sets the facing direction of the block for placement.
	 *
	 * @param context the <code>BlockItemUseContext</code> during placement
	 * @return BlockState
	 */
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	/**
	 * Runs when the block is activated.
	 * Allows the block to respond to user interaction.
	 *
	 * @param state               the <code>BlockState</code> of the block
	 * @param worldIn             the <code>World</code> the block is in
	 * @param pos                 the <code>BlockPos</code> the block is at
	 * @param player              the <code>PlayerEntity</code> interacting with the block
	 * @param handIn              the <code>Hand</code> the PlayerEntity used
	 * @param blockRayTraceResult the <code>BlockRayTraceResult</code> of the interaction
	 * @return ActionResultType
	 */
	@SuppressWarnings("deprecation")
	@Override
	public @NotNull InteractionResult use(@NotNull BlockState state, Level worldIn, BlockPos pos, @NotNull Player player, @NotNull InteractionHand handIn, @NotNull BlockHitResult blockRayTraceResult) {
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
			return InteractionResult.SUCCESS;
		} else {
			if (blockStateNorth.is(Blocks.BARREL) || blockStateSouth.is(Blocks.BARREL) || blockStateEast.is(Blocks.BARREL) || blockStateWest.is(Blocks.BARREL)) {

				BlockEntity tileEntity = switch (directionToUse) {
					case "south" -> worldIn.getBlockEntity(pos.south());
					case "east" -> worldIn.getBlockEntity(pos.east());
					case "west" -> worldIn.getBlockEntity(pos.west());
					default -> worldIn.getBlockEntity(pos.north());
				};

				ItemStack itemStack;

				if (tileEntity != null) {
					for (int i = 0; i < ((Container) tileEntity).getContainerSize(); ++i) {
						itemStack = ((Container) tileEntity).getItem(i);

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
								i = ((Container) tileEntity).getContainerSize();
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
								i = ((Container) tileEntity).getContainerSize();
							}
						}
					}
				}
				return InteractionResult.SUCCESS;
			}
			return InteractionResult.FAIL;
		}
	}
}