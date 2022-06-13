package tech.anonymoushacker1279.immersiveweapons.block.trap;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.*;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.blockentity.BearTrapBlockEntity;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

public class BearTrapBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
	private static final BooleanProperty TRIGGERED = BooleanProperty.create("triggered");
	private static final BooleanProperty VINES = BooleanProperty.create("vines");

	/**
	 * Constructor for BearTrapBlock.
	 *
	 * @param properties the <code>Properties</code> of the block
	 */
	public BearTrapBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(TRIGGERED, Boolean.FALSE).setValue(WATERLOGGED, Boolean.FALSE).setValue(VINES, Boolean.FALSE));
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
	public @NotNull InteractionResult use(@NotNull BlockState state, Level worldIn, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand handIn, @NotNull BlockHitResult blockRayTraceResult) {
		if (!worldIn.isClientSide && handIn.equals(InteractionHand.MAIN_HAND)) {
			BearTrapBlockEntity bearTrap = (BearTrapBlockEntity) worldIn.getBlockEntity(pos);
			ItemStack currentlyHeldItem = player.getMainHandItem();
			if (bearTrap != null) {
				if (state.getValue(TRIGGERED) && !bearTrap.hasTrappedEntity() && bearTrap.hasTrappedPlayerEntity()) {
					worldIn.setBlock(pos, state.setValue(TRIGGERED, false).setValue(VINES, false), 3);
					return InteractionResult.SUCCESS;
				}
			}
			if (!state.getValue(VINES) && currentlyHeldItem.getItem() == Items.VINE) {
				worldIn.setBlock(pos, state.setValue(VINES, true), 3);
				if (!player.isCreative()) {
					currentlyHeldItem.shrink(1);
				}
			}
		}

		return InteractionResult.PASS;
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
	public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos pos, @NotNull CollisionContext selectionContext) {
		return SHAPE;
	}

	/**
	 * Get the collision shape of the block.
	 *
	 * @param state            the <code>BlockState</code> of the block
	 * @param reader           the <code>IBlockReader</code> for the block
	 * @param pos              the <code>BlockPos</code> the block is at
	 * @param selectionContext the <code>ISelectionContext</code> of the block
	 * @return VoxelShape
	 */
	@SuppressWarnings("deprecation")
	@Override
	public @NotNull VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos pos, @NotNull CollisionContext selectionContext) {
		return Shapes.empty();
	}

	/**
	 * Set the RenderShape of the block's model.
	 *
	 * @param state the <code>BlockState</code> of the block
	 * @return BlockRenderType
	 */
	@Override
	public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
		return RenderShape.MODEL;
	}

	/**
	 * Create a block entity for the block.
	 *
	 * @param blockPos   the <code>BlockPos</code> the block is at
	 * @param blockState the <code>BlockState</code> of the block
	 * @return BlockEntity
	 */
	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
		return new BearTrapBlockEntity(blockPos, blockState);
	}

	/**
	 * Get the ticker for the block.
	 *
	 * @param level           the <code>Level</code> the block is in
	 * @param blockState      the <code>BlockState</code> of the block
	 * @param blockEntityType the <code>BlockEntityType</code> to get the ticker of
	 * @param <T>             the type extending BlockEntity
	 * @return BlockEntityTicker
	 */
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState blockState, @NotNull BlockEntityType<T> blockEntityType) {
		return createTickerHelper(blockEntityType, DeferredRegistryHandler.BEAR_TRAP_BLOCK_ENTITY.get(), (level1, blockPos, blockState1, bearTrapBlockEntity) -> BearTrapBlockEntity.tick(blockPos, bearTrapBlockEntity));
	}

	/**
	 * Runs when an entity is inside the block's collision area.
	 * Allows the block to deal damage on contact.
	 *
	 * @param state  the <code>BlockState</code> of the block
	 * @param level  the <code>Level</code> the block is in
	 * @param pos    the <code>BlockPos</code> the block is at
	 * @param entity the <code>Entity</code> passing through the block
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void entityInside(BlockState state, Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
		BearTrapBlockEntity bearTrap = (BearTrapBlockEntity) level.getBlockEntity(pos);

		if (state.getValue(TRIGGERED)) {
			if (bearTrap != null && (bearTrap.getTrappedMobEntity() == entity || bearTrap.getTrappedPlayerEntity() == entity)) {
				entity.makeStuckInBlock(state, new Vec3(0.0F, 0.0D, 0.0F));
				if ((entity.xOld != entity.getX() || entity.zOld != entity.getZ())) {
					double d0 = Math.abs(entity.getX() - entity.xOld);
					double d1 = Math.abs(entity.getZ() - entity.zOld);
					if (d0 >= (double) 0.003F || d1 >= (double) 0.003F) {
						entity.hurt(BearTrapBlockEntity.damageSource, 1.0F);
					}
				}
			}
			return;
		}


		if (entity instanceof Player livingEntity) {
			if (!livingEntity.isCreative()) {
				if (state.getValue(VINES)) {
					level.setBlock(pos, state.setValue(TRIGGERED, true).setValue(VINES, true), 3);
				} else {
					level.setBlock(pos, state.setValue(TRIGGERED, true).setValue(VINES, false), 3);
				}
				livingEntity.hurt(BearTrapBlockEntity.damageSource, 2.0F);
				level.playSound((Player) entity, pos, DeferredRegistryHandler.BEAR_TRAP_CLOSE.get(), SoundSource.BLOCKS, 1f, 1f);
				if (bearTrap != null) {
					bearTrap.setTrappedPlayerEntity(livingEntity);
				}
			}
		} else if (entity instanceof Mob livingEntity) {
			if (state.getValue(VINES)) {
				level.setBlock(pos, state.setValue(TRIGGERED, true).setValue(VINES, true), 3);
			} else {
				level.setBlock(pos, state.setValue(TRIGGERED, true).setValue(VINES, false), 3);
			}
			level.setBlock(pos, state.setValue(TRIGGERED, true), 3);
			livingEntity.hurt(BearTrapBlockEntity.damageSource, 2.0F);
			level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), DeferredRegistryHandler.BEAR_TRAP_CLOSE.get(), SoundSource.BLOCKS, 1f, 1f, false);
			if (bearTrap != null) {
				bearTrap.setTrappedMobEntity(livingEntity);
			}
		}
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
		return defaultBlockState().setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
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
	 * Set the block's analog output signal.
	 *
	 * @param state the <code>BlockState</code> of the block
	 * @return boolean
	 */
	@SuppressWarnings("deprecation")
	@Override
	public boolean hasAnalogOutputSignal(@NotNull BlockState state) {
		return true;
	}

	/**
	 * Set the block's analog output signal strength.
	 *
	 * @param state   the <code>BlockState</code> of the block
	 * @param worldIn the <code>World</code> the block is in
	 * @param pos     the <code>BlockPos</code> the block is at
	 * @return int
	 */
	@SuppressWarnings("deprecation")
	@Override
	public int getAnalogOutputSignal(BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos) {
		return state.getValue(TRIGGERED) ? 15 : 0;
	}

	/**
	 * Create the BlockState definition.
	 *
	 * @param builder the <code>StateContainer.Builder</code> of the block
	 */
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(TRIGGERED, VINES, WATERLOGGED);
	}

	/**
	 * Set the block's signal source.
	 *
	 * @param state the <code>BlockState</code> of the block
	 * @return boolean
	 */
	@SuppressWarnings("deprecation")
	@Override
	public boolean isSignalSource(BlockState state) {
		return state.getValue(TRIGGERED);
	}

	/**
	 * Get the signal of the block.
	 *
	 * @param blockState the <code>BlockState</code> of the block
	 * @param reader     the <code>IBlockReader</code> for the block
	 * @param pos        the <code>BlockPos</code> the block is at
	 * @param side       the <code>Direction</code> the block is facing
	 * @return int
	 */
	@SuppressWarnings("deprecation")
	@Override
	public int getSignal(BlockState blockState, @NotNull BlockGetter reader, @NotNull BlockPos pos, @NotNull Direction side) {
		if (!blockState.isSignalSource()) {
			return 0;
		} else {
			return 15;
		}
	}
}