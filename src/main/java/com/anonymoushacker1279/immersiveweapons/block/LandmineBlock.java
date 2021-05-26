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
import net.minecraft.world.*;

import javax.annotation.Nullable;

public class LandmineBlock extends Block implements IWaterLoggable {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty ARMED = BooleanProperty.create("armed");
	public static final BooleanProperty SAND = BooleanProperty.create("sand");
	public static final BooleanProperty VINES = BooleanProperty.create("vines");
	protected static final VoxelShape SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 3.5D, 14.0D);
	private static final DamageSource damageSource = new DamageSource("immersiveweapons.landmine");
	//private static final ExplosionContext explosionContext = new ExplosionContext();

	public LandmineBlock(AbstractBlock.Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(ARMED, Boolean.valueOf(false)).with(WATERLOGGED, Boolean.valueOf(false)).with(VINES, Boolean.valueOf(false)).with(SAND, Boolean.valueOf(false)));

	}

	private static void explode(World worldIn, BlockPos pos, @Nullable LivingEntity entityIn) {
		if (!worldIn.isRemote) {
			worldIn.createExplosion(entityIn, damageSource, null, pos.getX(), pos.getY(), pos.getZ(), 2.0F, false, Explosion.Mode.BREAK);
			worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
		}
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (!worldIn.isRemote) {
			ItemStack currentlyHeldItem = player.getHeldItemMainhand();
			if (state.get(ARMED) && currentlyHeldItem.getItem() == DeferredRegistryHandler.PLIERS.get()) {
				worldIn.setBlockState(pos, state.with(ARMED, false), 3);
				return ActionResultType.PASS;
			}
			if (!state.get(ARMED) && currentlyHeldItem.getItem() != DeferredRegistryHandler.PLIERS.get()) {
				worldIn.setBlockState(pos, state.with(ARMED, true), 3);
			}
			if (!state.get(VINES) && !state.get(SAND) && currentlyHeldItem.getItem() == Items.VINE) {
				worldIn.setBlockState(pos, state.with(VINES, true), 3);
				if (!player.abilities.isCreativeMode) {
					currentlyHeldItem.shrink(1);
				}
			}
			if (!state.get(SAND) && !state.get(VINES) && currentlyHeldItem.getItem() == Items.SAND) {
				worldIn.setBlockState(pos, state.with(SAND, true), 3);
				if (!player.abilities.isCreativeMode) {
					currentlyHeldItem.shrink(1);
				}
			}
		}
		return ActionResultType.PASS;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext selectionContext) {
		return SHAPE;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext selectionContext) {
		return VoxelShapes.empty();
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (state.get(ARMED) && !state.get(WATERLOGGED)) {
			if (entity instanceof MobEntity) {
				explode(world, pos, (LivingEntity) entity);
			}
			if (entity instanceof PlayerEntity && !((PlayerEntity) entity).isCreative()) {
				explode(world, pos, (LivingEntity) entity);
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.get(WATERLOGGED)) {
			worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}

		return facing == Direction.DOWN && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		final FluidState ifluidstate = context.getWorld().getFluidState(context.getPos());

		return this.getDefaultState().with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
	}

	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return Block.hasEnoughSolidSide(worldIn, pos.down(), Direction.UP);
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(ARMED, SAND, VINES, WATERLOGGED);
	}

	@Override
	public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
		if (!worldIn.isRemote) {
			explode(worldIn, pos, null);
		}
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		if (!worldIn.isRemote() && !player.isCreative() && state.get(ARMED)) {
			explode(worldIn, pos, player);
		}
		super.onBlockHarvested(worldIn, pos, state, player);
	}
}