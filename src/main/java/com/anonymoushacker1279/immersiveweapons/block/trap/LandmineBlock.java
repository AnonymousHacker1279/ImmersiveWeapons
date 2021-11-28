package com.anonymoushacker1279.immersiveweapons.block.trap;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class LandmineBlock extends Block implements SimpleWaterloggedBlock {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 3.5D, 14.0D);
	private static final BooleanProperty ARMED = BooleanProperty.create("armed");
	private static final BooleanProperty SAND = BooleanProperty.create("sand");
	private static final BooleanProperty VINES = BooleanProperty.create("vines");
	private static final DamageSource damageSource = new DamageSource("immersiveweapons.landmine");

	/**
	 * Constructor for LandmineBlock.
	 *
	 * @param properties the <code>Properties</code> of the block
	 */
	public LandmineBlock(BlockBehaviour.Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(ARMED, Boolean.FALSE).setValue(WATERLOGGED, Boolean.FALSE).setValue(VINES, Boolean.FALSE).setValue(SAND, Boolean.FALSE));

	}

	/**
	 * Create an explosion.
	 *
	 * @param worldIn  the <code>World</code> to explode in
	 * @param pos      the <code>BlockPos</code> to explode at
	 * @param entityIn the <code>LivingEntity</code> causing the explosion
	 */
	private static void explode(Level worldIn, BlockPos pos, @Nullable LivingEntity entityIn) {
		if (!worldIn.isClientSide) {
			worldIn.explode(entityIn, damageSource, null, pos.getX(), pos.getY(), pos.getZ(), 2.0F, false, Explosion.BlockInteraction.BREAK);
			worldIn.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F, false);
		}
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
		if (!worldIn.isClientSide) {
			ItemStack currentlyHeldItem = player.getMainHandItem();
			if (state.getValue(ARMED) && currentlyHeldItem.getItem() == DeferredRegistryHandler.PLIERS.get()) {
				worldIn.setBlock(pos, state.setValue(ARMED, false), 3);
				return InteractionResult.PASS;
			}
			if (!state.getValue(ARMED) && currentlyHeldItem.getItem() != DeferredRegistryHandler.PLIERS.get()) {
				worldIn.setBlock(pos, state.setValue(ARMED, true), 3);
			}
			if (!state.getValue(VINES) && !state.getValue(SAND) && currentlyHeldItem.getItem() == Items.VINE) {
				worldIn.setBlock(pos, state.setValue(VINES, true), 3);
				if (!player.isCreative()) {
					currentlyHeldItem.shrink(1);
				}
			}
			if (!state.getValue(SAND) && !state.getValue(VINES) && currentlyHeldItem.getItem() == Items.SAND) {
				worldIn.setBlock(pos, state.setValue(SAND, true), 3);
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
	@SuppressWarnings("deprecation")
	@Override
	public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
		return RenderShape.MODEL;
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
	public void entityInside(BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
		if (state.getValue(ARMED) && !state.getValue(WATERLOGGED)) {
			if (entity instanceof Mob) {
				explode(level, pos, (LivingEntity) entity);
			}
			if (entity instanceof Player && !((Player) entity).isCreative()) {
				explode(level, pos, (LivingEntity) entity);
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
	 * Create the BlockState definition.
	 *
	 * @param builder the <code>StateContainer.Builder</code> of the block
	 */
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(ARMED, SAND, VINES, WATERLOGGED);
	}

	/**
	 * Runs when the block is exploded by another block.
	 *
	 * @param worldIn     the <code>World</code> the block is in
	 * @param pos         the <code>BlockPos</code> the block is at
	 * @param explosionIn the <code>Explosion</code> destroying the block
	 */
	@Override
	public void wasExploded(Level worldIn, @NotNull BlockPos pos, @NotNull Explosion explosionIn) {
		if (!worldIn.isClientSide) {
			explode(worldIn, pos, null);
		}
	}

	/**
	 * Runs when the player destroys the block.
	 *
	 * @param worldIn the <code>World</code> the block is in
	 * @param pos     the <code>BlockPos</code> the block is at
	 * @param state   the <code>BlockState</code> of the block
	 * @param player  the <code>PlayerEntity</code> destroying the block
	 */
	@Override
	public void playerWillDestroy(Level worldIn, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
		if (!worldIn.isClientSide() && !player.isCreative() && state.getValue(ARMED)) {
			explode(worldIn, pos, player);
		}
		super.playerWillDestroy(worldIn, pos, state, player);
	}
}