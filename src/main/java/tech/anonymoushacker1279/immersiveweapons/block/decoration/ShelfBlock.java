package tech.anonymoushacker1279.immersiveweapons.block.decoration;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.level.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.blockentity.ShelfBlockEntity;

@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = Bus.FORGE)
public class ShelfBlock extends HorizontalDirectionalBlock implements EntityBlock, SimpleWaterloggedBlock {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	private static final VoxelShape SHAPE_NORTH = Block.box(0.0D, 0.0D, 10.0D, 16.0D, 16.0D, 16.0D);
	private static final VoxelShape SHAPE_SOUTH = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 6.0D);
	private static final VoxelShape SHAPE_EAST = Block.box(0.0D, 0.0D, 0.0D, 6.0D, 16.0D, 16.0D);
	private static final VoxelShape SHAPE_WEST = Block.box(10.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

	public static boolean needsRenderUpdate = false;

	public ShelfBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any()
				.setValue(FACING, Direction.NORTH)
				.setValue(WATERLOGGED, false));
	}

	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext selectionContext) {
		return switch (state.getValue(FACING)) {
			case SOUTH -> SHAPE_SOUTH;
			case EAST -> SHAPE_EAST;
			case WEST -> SHAPE_WEST;
			default -> SHAPE_NORTH;
		};
	}

	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, WATERLOGGED);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState()
				.setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new ShelfBlockEntity(blockPos, blockState);
	}

	/**
	 * Runs when the block is activated.
	 * Allows the block to respond to user interaction.
	 *
	 * @param state     the <code>BlockState</code> of the block
	 * @param level     the <code>Level</code> the block is in
	 * @param pos       the <code>BlockPos</code> the block is at
	 * @param player    the <code>Player</code> interacting with the block
	 * @param hand      the <code>InteractionHand</code> the Player used
	 * @param hitResult the <code>BlockHitResult</code> of the interaction
	 * @return InteractionResult
	 */
	@SuppressWarnings("deprecation")
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if (hand != InteractionHand.MAIN_HAND) {
			return InteractionResult.PASS;
		}

		if (level.getBlockEntity(pos) instanceof ShelfBlockEntity shelfBlockEntity) {
			ItemStack itemInHand = player.getItemInHand(hand);

			if (!level.isClientSide) {
				// If the item held is a debug stick, lock the shelf
				if (itemInHand.getItem() == Items.DEBUG_STICK) {
					shelfBlockEntity.setLocked(!shelfBlockEntity.isLocked());

					player.displayClientMessage(Component.translatable("immersiveweapons.block.wall_shelf."
									+ (shelfBlockEntity.isLocked() ? "locked" : "unlocked"))
							.withStyle(ChatFormatting.YELLOW), true);

					return InteractionResult.SUCCESS;
				}

				if (!shelfBlockEntity.isLocked()) {
					if (itemInHand.isEmpty()) {
						// If not holding anything, remove the last added item
						shelfBlockEntity.removeItem();
						return InteractionResult.SUCCESS;
					} else {
						if (shelfBlockEntity.addItem(player.isCreative() ? itemInHand.copy() : itemInHand)) {
							return InteractionResult.CONSUME;
						}
					}
				} else {
					player.displayClientMessage(Component.translatable("immersiveweapons.block.wall_shelf.locked")
							.withStyle(ChatFormatting.RED), true);

					return InteractionResult.FAIL;
				}
			}
		}
		return InteractionResult.PASS;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.is(newState.getBlock())) {
			if (level.getBlockEntity(pos) instanceof ShelfBlockEntity shelfBlockEntity) {
				Containers.dropContents(level, pos, shelfBlockEntity.getInventory());
			}
			super.onRemove(state, level, pos, newState, isMoving);
		}
	}

	@SubscribeEvent
	public static void blockBreakEvent(BreakEvent event) {
		LevelAccessor level = event.getLevel();

		if (level.getBlockEntity(event.getPos()) instanceof ShelfBlockEntity shelfBlockEntity) {
			if (shelfBlockEntity.isLocked()) {
				event.setCanceled(true);
				needsRenderUpdate = true;
			}
		}
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState,
	                                                              BlockEntityType<T> blockEntityType) {

		return (world, pos, state, entity) -> ((ShelfBlockEntity) entity).tick();
	}
}