package com.anonymoushacker1279.immersiveweapons.block;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.tileentity.BearTrapTileEntity;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class BearTrapBlock extends ContainerBlock implements IWaterLoggable {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty TRIGGERED = BooleanProperty.create("triggered");
	public static final BooleanProperty VINES = BooleanProperty.create("vines");
	protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
	private final boolean isSomethingTrapped = false;

	public BearTrapBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(TRIGGERED, Boolean.valueOf(false)).with(WATERLOGGED, Boolean.valueOf(false)).with(VINES, Boolean.valueOf(false)));

	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (!worldIn.isRemote) {
			BearTrapTileEntity bearTrap = (BearTrapTileEntity) worldIn.getTileEntity(pos);
			ItemStack currentlyHeldItem = player.getHeldItemMainhand();
			if (state.get(TRIGGERED) && !bearTrap.hasTrappedEntity() && !bearTrap.hasTrappedPlayerEntity()) {
				worldIn.setBlockState(pos, state.with(TRIGGERED, false).with(VINES, false), 3);
				return ActionResultType.SUCCESS;
			}
			if (!state.get(VINES) && currentlyHeldItem.getItem() == Items.VINE) {
				worldIn.setBlockState(pos, state.with(VINES, true), 3);
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
	public TileEntity createNewTileEntity(IBlockReader world) {
		return new BearTrapTileEntity();
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		final BearTrapTileEntity bearTrap = (BearTrapTileEntity) world.getTileEntity(pos);

		if (state.get(TRIGGERED)) {
			assert bearTrap != null;
			if (bearTrap.getTrappedEntity() == entity || bearTrap.getTrappedPlayerEntity() == entity) {
				entity.setMotionMultiplier(state, new Vector3d(0.0F, 0.0D, 0.0F));
				if ((entity.lastTickPosX != entity.getPosX() || entity.lastTickPosZ != entity.getPosZ())) {
					double d0 = Math.abs(entity.getPosX() - entity.lastTickPosX);
					double d1 = Math.abs(entity.getPosZ() - entity.lastTickPosZ);
					if (d0 >= (double) 0.003F || d1 >= (double) 0.003F) {
						entity.attackEntityFrom(BearTrapTileEntity.damageSource, 1.0F);
					}
				}
			}
			return;
		}


		if ((entity instanceof PlayerEntity)) {
			final PlayerEntity livingEntity = (PlayerEntity) entity;
			if (!livingEntity.abilities.isCreativeMode) {
				if (state.get(VINES)) {
					world.setBlockState(pos, state.with(TRIGGERED, true).with(VINES, true), 3);
				} else {
					world.setBlockState(pos, state.with(TRIGGERED, true).with(VINES, false), 3);
				}
				livingEntity.attackEntityFrom(BearTrapTileEntity.damageSource, 2.0F);
				world.playSound((PlayerEntity) entity, pos, DeferredRegistryHandler.BEAR_TRAP_CLOSE.get(), SoundCategory.BLOCKS, 1f, 1f);
				bearTrap.setTrappedPlayerEntity(livingEntity);
			}
		} else if ((entity instanceof MobEntity)) {
			if (state.get(VINES)) {
				world.setBlockState(pos, state.with(TRIGGERED, true).with(VINES, true), 3);
			} else {
				world.setBlockState(pos, state.with(TRIGGERED, true).with(VINES, false), 3);
			}
			final MobEntity livingEntity = (MobEntity) entity;
			world.setBlockState(pos, state.with(TRIGGERED, true), 3);
			livingEntity.attackEntityFrom(BearTrapTileEntity.damageSource, 2.0F);
			world.playSound(pos.getX(), pos.getY(), pos.getZ(), DeferredRegistryHandler.BEAR_TRAP_CLOSE.get(), SoundCategory.BLOCKS, 1f, 1f, false);
			bearTrap.setTrappedEntity(livingEntity);
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
	public boolean hasComparatorInputOverride(BlockState state) {
		return true;
	}


	@Override
	public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
		return blockState.get(TRIGGERED) ? 15 : 0;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(TRIGGERED, VINES, WATERLOGGED);
	}

	@Override
	public boolean canProvidePower(BlockState state) {
		return state.get(TRIGGERED);
	}

	@Override
	public int getWeakPower(BlockState blockState, IBlockReader blockReader, BlockPos pos, Direction side) {
		if (!blockState.canProvidePower()) {
			return 0;
		} else {
			return 15;
		}
	}

	@Override
	public int getStrongPower(BlockState blockState, IBlockReader blockReader, BlockPos pos, Direction side) {
		return side == Direction.UP ? blockState.getWeakPower(blockReader, pos, side) : 0;
	}
}