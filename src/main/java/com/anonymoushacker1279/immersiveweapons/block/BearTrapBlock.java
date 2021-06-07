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

import net.minecraft.block.AbstractBlock.Properties;

public class BearTrapBlock extends ContainerBlock implements IWaterLoggable {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty TRIGGERED = BooleanProperty.create("triggered");
	public static final BooleanProperty VINES = BooleanProperty.create("vines");
	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
	private final boolean isSomethingTrapped = false;

	public BearTrapBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(TRIGGERED, Boolean.valueOf(false)).setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(VINES, Boolean.valueOf(false)));

	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (!worldIn.isClientSide) {
			BearTrapTileEntity bearTrap = (BearTrapTileEntity) worldIn.getBlockEntity(pos);
			ItemStack currentlyHeldItem = player.getMainHandItem();
			if (state.getValue(TRIGGERED) && !bearTrap.hasTrappedEntity() && !bearTrap.hasTrappedPlayerEntity()) {
				worldIn.setBlock(pos, state.setValue(TRIGGERED, false).setValue(VINES, false), 3);
				return ActionResultType.SUCCESS;
			}
			if (!state.getValue(VINES) && currentlyHeldItem.getItem() == Items.VINE) {
				worldIn.setBlock(pos, state.setValue(VINES, true), 3);
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
	public TileEntity newBlockEntity(IBlockReader world) {
		return new BearTrapTileEntity();
	}

	@Override
	public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {
		final BearTrapTileEntity bearTrap = (BearTrapTileEntity) world.getBlockEntity(pos);

		if (state.getValue(TRIGGERED)) {
			assert bearTrap != null;
			if (bearTrap.getTrappedEntity() == entity || bearTrap.getTrappedPlayerEntity() == entity) {
				entity.makeStuckInBlock(state, new Vector3d(0.0F, 0.0D, 0.0F));
				if ((entity.xOld != entity.getX() || entity.zOld != entity.getZ())) {
					double d0 = Math.abs(entity.getX() - entity.xOld);
					double d1 = Math.abs(entity.getZ() - entity.zOld);
					if (d0 >= (double) 0.003F || d1 >= (double) 0.003F) {
						entity.hurt(BearTrapTileEntity.damageSource, 1.0F);
					}
				}
			}
			return;
		}


		if ((entity instanceof PlayerEntity)) {
			final PlayerEntity livingEntity = (PlayerEntity) entity;
			if (!livingEntity.abilities.instabuild) {
				if (state.getValue(VINES)) {
					world.setBlock(pos, state.setValue(TRIGGERED, true).setValue(VINES, true), 3);
				} else {
					world.setBlock(pos, state.setValue(TRIGGERED, true).setValue(VINES, false), 3);
				}
				livingEntity.hurt(BearTrapTileEntity.damageSource, 2.0F);
				world.playSound((PlayerEntity) entity, pos, DeferredRegistryHandler.BEAR_TRAP_CLOSE.get(), SoundCategory.BLOCKS, 1f, 1f);
				bearTrap.setTrappedPlayerEntity(livingEntity);
			}
		} else if ((entity instanceof MobEntity)) {
			if (state.getValue(VINES)) {
				world.setBlock(pos, state.setValue(TRIGGERED, true).setValue(VINES, true), 3);
			} else {
				world.setBlock(pos, state.setValue(TRIGGERED, true).setValue(VINES, false), 3);
			}
			final MobEntity livingEntity = (MobEntity) entity;
			world.setBlock(pos, state.setValue(TRIGGERED, true), 3);
			livingEntity.hurt(BearTrapTileEntity.damageSource, 2.0F);
			world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), DeferredRegistryHandler.BEAR_TRAP_CLOSE.get(), SoundCategory.BLOCKS, 1f, 1f, false);
			bearTrap.setTrappedEntity(livingEntity);
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
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}


	@Override
	public int getAnalogOutputSignal(BlockState blockState, World worldIn, BlockPos pos) {
		return blockState.getValue(TRIGGERED) ? 15 : 0;
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(TRIGGERED, VINES, WATERLOGGED);
	}

	@Override
	public boolean isSignalSource(BlockState state) {
		return state.getValue(TRIGGERED);
	}

	@Override
	public int getSignal(BlockState blockState, IBlockReader blockReader, BlockPos pos, Direction side) {
		if (!blockState.isSignalSource()) {
			return 0;
		} else {
			return 15;
		}
	}

	@Override
	public int getDirectSignal(BlockState blockState, IBlockReader blockReader, BlockPos pos, Direction side) {
		return side == Direction.UP ? blockState.getSignal(blockReader, pos, side) : 0;
	}
}