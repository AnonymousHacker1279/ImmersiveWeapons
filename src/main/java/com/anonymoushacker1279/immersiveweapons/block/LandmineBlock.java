package com.anonymoushacker1279.immersiveweapons.block;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class LandmineBlock extends Block implements IWaterLoggable {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty ARMED = BooleanProperty.create("armed");
	public static final BooleanProperty SAND = BooleanProperty.create("sand");
	public static final BooleanProperty VINES = BooleanProperty.create("vines");
	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 3.5D, 14.0D);
	private static final DamageSource damageSource = new DamageSource("immersiveweapons.landmine");

	/**
	 * Constructor for LandmineBlock.
	 * @param properties the <code>Properties</code> of the block
	 */
	public LandmineBlock(AbstractBlock.Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(ARMED, Boolean.FALSE).setValue(WATERLOGGED, Boolean.FALSE).setValue(VINES, Boolean.FALSE).setValue(SAND, Boolean.FALSE));

	}

	/**
	 * Create an explosion.
	 * @param worldIn the <code>World</code> to explode in
	 * @param pos the <code>BlockPos</code> to explode at
	 * @param entityIn the <code>LivingEntity</code> causing the explosion
	 */
	private static void explode(World worldIn, BlockPos pos, @Nullable LivingEntity entityIn) {
		if (!worldIn.isClientSide) {
			worldIn.explode(entityIn, damageSource, null, pos.getX(), pos.getY(), pos.getZ(), 2.0F, false, Explosion.Mode.BREAK);
			worldIn.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
		}
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
		if (!worldIn.isClientSide) {
			ItemStack currentlyHeldItem = player.getMainHandItem();
			if (state.getValue(ARMED) && currentlyHeldItem.getItem() == DeferredRegistryHandler.PLIERS.get()) {
				worldIn.setBlock(pos, state.setValue(ARMED, false), 3);
				return ActionResultType.PASS;
			}
			if (!state.getValue(ARMED) && currentlyHeldItem.getItem() != DeferredRegistryHandler.PLIERS.get()) {
				worldIn.setBlock(pos, state.setValue(ARMED, true), 3);
			}
			if (!state.getValue(VINES) && !state.getValue(SAND) && currentlyHeldItem.getItem() == Items.VINE) {
				worldIn.setBlock(pos, state.setValue(VINES, true), 3);
				if (!player.abilities.instabuild) {
					currentlyHeldItem.shrink(1);
				}
			}
			if (!state.getValue(SAND) && !state.getValue(VINES) && currentlyHeldItem.getItem() == Items.SAND) {
				worldIn.setBlock(pos, state.setValue(SAND, true), 3);
				if (!player.abilities.instabuild) {
					currentlyHeldItem.shrink(1);
				}
			}
		}
		return ActionResultType.PASS;
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
		return SHAPE;
	}

	/**
	 * Get the collision shape of the block.
	 * @param state the <code>BlockState</code> of the block
	 * @param reader the <code>IBlockReader</code> for the block
	 * @param pos the <code>BlockPos</code> the block is at
	 * @param selectionContext the <code>ISelectionContext</code> of the block
	 * @return VoxelShape
	 */
	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext selectionContext) {
		return VoxelShapes.empty();
	}

	/**
	 * Set the RenderShape of the block's model.
	 * @param state the <code>BlockState</code> of the block
	 * @return BlockRenderType
	 */
	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.MODEL;
	}

	/**
	 * Runs when an entity is inside of the block's collision area.
	 * Allows the block to deal damage on contact.
	 * @param state the <code>BlockState</code> of the block
	 * @param world the <code>World</code> the block is in
	 * @param pos the <code>BlockPos</code> the block is at
	 * @param entity the <code>Entity</code> passing through the block
	 */
	@Override
	public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {
		if (state.getValue(ARMED) && !state.getValue(WATERLOGGED)) {
			if (entity instanceof MobEntity) {
				explode(world, pos, (LivingEntity) entity);
			}
			if (entity instanceof PlayerEntity && !((PlayerEntity) entity).isCreative()) {
				explode(world, pos, (LivingEntity) entity);
			}
		}
	}

	/**
	 * Set placement properties.
	 * Sets the facing direction of the block for placement.
	 * @param context the <code>BlockItemUseContext</code> during placement
	 * @return BlockState
	 */
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return defaultBlockState().setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
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
	 * Create the BlockState definition.
	 * @param builder the <code>StateContainer.Builder</code> of the block
	 */
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(ARMED, SAND, VINES, WATERLOGGED);
	}

	/**
	 * Runs when the block is exploded by another block.
	 * @param worldIn the <code>World</code> the block is in
	 * @param pos the <code>BlockPos</code> the block is at
	 * @param explosionIn the <code>Explosion</code> destroying the block
	 */
	@Override
	public void wasExploded(World worldIn, BlockPos pos, Explosion explosionIn) {
		if (!worldIn.isClientSide) {
			explode(worldIn, pos, null);
		}
	}

	/**
	 * Runs when the player destroys the block.
	 * @param worldIn the <code>World</code> the block is in
	 * @param pos the <code>BlockPos</code> the block is at
	 * @param state the <code>BlockState</code> of the block
	 * @param player the <code>PlayerEntity</code> destroying the block
	 */
	@Override
	public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		if (!worldIn.isClientSide() && !player.isCreative() && state.getValue(ARMED)) {
			explode(worldIn, pos, player);
		}
		super.playerWillDestroy(worldIn, pos, state, player);
	}
}