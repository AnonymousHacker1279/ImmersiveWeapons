package tech.anonymoushacker1279.immersiveweapons.block.crafting;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
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
import tech.anonymoushacker1279.immersiveweapons.init.RecipeTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.BarrelTapRecipe;

import java.util.List;

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
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	/**
	 * Set the shape of the block.
	 *
	 * @param state            the <code>BlockState</code> of the block
	 * @param getter           the <code>BlockGetter</code> for the block
	 * @param pos              the <code>BlockPos</code> the block is at
	 * @param collisionContext the <code>CollisionContext</code> of the block
	 * @return VoxelShape
	 */
	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext collisionContext) {
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
	 * @param builder the <code>StateDefinition.Builder</code> of the block
	 */
	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, WATERLOGGED);
	}

	/**
	 * Set placement properties.
	 * Sets the facing direction of the block for placement.
	 *
	 * @param context the <code>BlockPlaceContext</code> during placement
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
	 * @param state     the <code>BlockState</code> of the block
	 * @param level     the <code>Level</code> the block is in
	 * @param pos       the <code>BlockPos</code> the block is at
	 * @param player    the <code>Player</code> interacting with the block
	 * @param hand      the <code>InteractionHand</code> the PlayerEntity used
	 * @param hitResult the <code>BlockHitResult</code> of the interaction
	 * @return ActionResultType
	 */
	@SuppressWarnings("deprecation")
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos,
	                             Player player, InteractionHand hand,
	                             BlockHitResult hitResult) {

		BlockState blockStateNorth = level.getBlockState(pos.north());
		BlockState blockStateSouth = level.getBlockState(pos.south());
		BlockState blockStateEast = level.getBlockState(pos.east());
		BlockState blockStateWest = level.getBlockState(pos.west());

		if (blockStateNorth.is(Blocks.BARREL)) {
			directionToUse = "north";
		} else if (blockStateSouth.is(Blocks.BARREL)) {
			directionToUse = "south";
		} else if (blockStateEast.is(Blocks.BARREL)) {
			directionToUse = "east";
		} else if (blockStateWest.is(Blocks.BARREL)) {
			directionToUse = "west";
		}

		if (level.isClientSide) {
			return InteractionResult.SUCCESS;
		} else {
			if (blockStateNorth.is(Blocks.BARREL) || blockStateSouth.is(Blocks.BARREL) || blockStateEast.is(Blocks.BARREL) || blockStateWest.is(Blocks.BARREL)) {

				BlockEntity blockEntity = switch (directionToUse) {
					case "south" -> level.getBlockEntity(pos.south());
					case "east" -> level.getBlockEntity(pos.east());
					case "west" -> level.getBlockEntity(pos.west());
					default -> level.getBlockEntity(pos.north());
				};

				if (blockEntity != null) {
					if (player.getMainHandItem().getItem() == Items.GLASS_BOTTLE) {
						Container container = ((Container) blockEntity);
						List<BarrelTapRecipe> recipes = level.getRecipeManager()
								.getAllRecipesFor(RecipeTypeRegistry.BARREL_TAP_RECIPE_TYPE.get());

						for (BarrelTapRecipe recipe : recipes) {
							for (int i = 0; i < container.getContainerSize(); ++i) {
								if (recipe.material().test(container.getItem(i))) {
									if (container.getItem(i).getCount() >= recipe.getMaterialCount()) {
										player.getInventory().add(recipe.getResultItem());
										player.getMainHandItem().shrink(1);
										container.removeItem(i, recipe.getMaterialCount());
										return InteractionResult.CONSUME;
									}
								}
							}
						}
					}
				}
				return InteractionResult.PASS;
			}
			return InteractionResult.FAIL;
		}
	}
}