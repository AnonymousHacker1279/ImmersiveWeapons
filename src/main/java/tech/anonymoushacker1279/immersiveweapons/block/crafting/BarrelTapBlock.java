package tech.anonymoushacker1279.immersiveweapons.block.crafting;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import tech.anonymoushacker1279.immersiveweapons.block.core.BasicOrientableBlock;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.BarrelTapRecipe;

import java.util.Optional;

public class BarrelTapBlock extends BasicOrientableBlock implements SimpleWaterloggedBlock {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	private static final VoxelShape SHAPE_NORTH = Block.box(7.0D, 4.0D, 0.0D, 9.0D, 7.0D, 3.0D);
	private static final VoxelShape SHAPE_SOUTH = Block.box(7.0D, 4.0D, 13.0D, 9.0D, 7.0D, 16.0D);
	private static final VoxelShape SHAPE_EAST = Block.box(0.0D, 4.0D, 7.0D, 3.0D, 7.0D, 9.0D);
	private static final VoxelShape SHAPE_WEST = Block.box(13.0D, 4.0D, 7.0D, 16.0D, 7.0D, 9.0D);

	/**
	 * Constructor for BarrelTapBlock.
	 *
	 * @param properties the <code>Properties</code> of the block
	 */
	public BarrelTapBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
		// Ensure it is placed with the opposite facing direction contacting a Barrel block
		Direction facingDirection = state.getValue(FACING);
		BlockPos blockPos = pos.relative(facingDirection.getOpposite());
		BlockState blockState = reader.getBlockState(blockPos);
		return blockState.getBlock() instanceof BarrelBlock && blockState.getValue(BarrelBlock.FACING) == facingDirection;
	}

	/**
	 * Set FluidState properties. Allows the block to exhibit waterlogged behavior.
	 *
	 * @param state the <code>BlockState</code> of the block
	 * @return FluidState
	 */
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
	 * Set placement properties. Sets the facing direction of the block for placement.
	 *
	 * @param context the <code>BlockPlaceContext</code> during placement
	 * @return BlockState
	 */
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		Direction facingDirection = state.getValue(FACING);
		ItemStack itemInHand = player.getItemInHand(hand);

		if (level instanceof ServerLevel serverLevel && itemInHand.is(Items.GLASS_BOTTLE)) {
			if (serverLevel.getBlockEntity(pos.relative(facingDirection.getOpposite())) instanceof Container container) {
				for (int i = 0; i < container.getContainerSize(); i++) {
					Optional<RecipeHolder<BarrelTapRecipe>> optional = serverLevel.recipeAccess().getRecipeFor(
							RecipeTypeRegistry.BARREL_TAP_RECIPE_TYPE.get(),
							new SingleRecipeInput(container.getItem(i)),
							serverLevel
					);

					if (optional.isPresent()) {
						BarrelTapRecipe recipe = optional.get().value();
						player.getInventory().add(recipe.result());
						itemInHand.shrink(1);
						container.removeItem(i, recipe.getMaterialCount());

						level.playSound(
								null,
								pos.getX(),
								pos.getY(),
								pos.getZ(),
								SoundEvents.BOTTLE_FILL,
								SoundSource.BLOCKS,
								1.0F,
								1.0F
						);

						return InteractionResult.CONSUME;
					}
				}
			}
		}

		return InteractionResult.PASS;
	}
}