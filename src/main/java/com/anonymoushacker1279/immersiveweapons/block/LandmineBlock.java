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
	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 3.5D, 14.0D);
	private static final DamageSource damageSource = new DamageSource("immersiveweapons.landmine");
	//private static final ExplosionContext explosionContext = new ExplosionContext();

	public LandmineBlock(AbstractBlock.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(ARMED, Boolean.valueOf(false)).setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(VINES, Boolean.valueOf(false)).setValue(SAND, Boolean.valueOf(false)));

	}

	private static void explode(World worldIn, BlockPos pos, @Nullable LivingEntity entityIn) {
		if (!worldIn.isClientSide) {
			worldIn.explode(entityIn, damageSource, null, pos.getX(), pos.getY(), pos.getZ(), 2.0F, false, Explosion.Mode.BREAK);
			worldIn.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
		}
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
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

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext selectionContext) {
		return SHAPE;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext selectionContext) {
		return VoxelShapes.empty();
	}

	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.MODEL;
	}

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

	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.getValue(WATERLOGGED)) {
			worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
		}

		return facing == Direction.DOWN && !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		final FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());

		return this.defaultBlockState().setValue(WATERLOGGED, ifluidstate.getType() == Fluids.WATER);
	}

	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return Block.canSupportCenter(worldIn, pos.below(), Direction.UP);
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(ARMED, SAND, VINES, WATERLOGGED);
	}

	@Override
	public void wasExploded(World worldIn, BlockPos pos, Explosion explosionIn) {
		if (!worldIn.isClientSide) {
			explode(worldIn, pos, null);
		}
	}

	@Override
	public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		if (!worldIn.isClientSide() && !player.isCreative() && state.getValue(ARMED)) {
			explode(worldIn, pos, player);
		}
		super.playerWillDestroy(worldIn, pos, state, player);
	}
}