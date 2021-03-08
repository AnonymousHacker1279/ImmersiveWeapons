package com.anonymoushacker1279.immersiveweapons.block;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.tileentity.PanicAlarmTileEntity;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class PanicAlarmBlock extends HorizontalBlock implements IWaterLoggable {

	public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected static final VoxelShape SHAPE_NORTH = Block.makeCuboidShape(4.0D, 4.0D, 13.0D, 12.0D, 11.0D, 16.0D);
	protected static final VoxelShape SHAPE_SOUTH = Block.makeCuboidShape(4.0D, 4.0D, 0.0D, 12.0D, 11.0D, 3.0D);
	protected static final VoxelShape SHAPE_EAST = Block.makeCuboidShape(0.0D, 4.0D, 4.0D, 3.0D, 11.0D, 12.0D);
	protected static final VoxelShape SHAPE_WEST = Block.makeCuboidShape(13.0D, 4.0D, 4.0D, 16.0D, 11.0D, 12.0D);

	public PanicAlarmBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(WATERLOGGED, false).with(HORIZONTAL_FACING, Direction.NORTH));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		Vector3d vector3d = state.getOffset(worldIn, pos);
		switch (state.get(HORIZONTAL_FACING)) {
			case NORTH:
			default:
				return SHAPE_NORTH.withOffset(vector3d.x, vector3d.y, vector3d.z);
			case SOUTH:
				return SHAPE_SOUTH.withOffset(vector3d.x, vector3d.y, vector3d.z);
			case EAST:
				return SHAPE_EAST.withOffset(vector3d.x, vector3d.y, vector3d.z);
			case WEST:
				return SHAPE_WEST.withOffset(vector3d.x, vector3d.y, vector3d.z);
		}
	}

	@Override
	public void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
		builder.add(HORIZONTAL_FACING);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader reader) {
		return new PanicAlarmTileEntity();
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		Direction direction = state.get(HORIZONTAL_FACING);
		BlockPos blockpos = pos.offset(direction.getOpposite());
		BlockState blockstate = worldIn.getBlockState(blockpos);
		return blockstate.isSolidSide(worldIn, blockpos, direction);
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.get(WATERLOGGED)) {
			worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}
		return facing.getOpposite() == stateIn.get(HORIZONTAL_FACING) && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : stateIn;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		FluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
		return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite()).with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		if (!worldIn.isRemote) {
			playSiren(worldIn, pos);
			worldIn.getPendingBlockTicks().scheduleTick(pos, state.getBlock(), 5);
		}
	}

	@Override
	public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		if (!oldState.isIn(state.getBlock())) {
			if (worldIn.isBlockPowered(pos)) {
				if (!worldIn.isRemote) {
					worldIn.getPendingBlockTicks().scheduleTick(pos, state.getBlock(), 5);
				}
			}
		}
	}

	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		if (!worldIn.isRemote) {
			playSiren(worldIn, pos);
			worldIn.getPendingBlockTicks().scheduleTick(pos, state.getBlock(), 5);
		}
	}

	private void playSiren(World worldIn, BlockPos pos) {
		BlockState state = worldIn.getBlockState(pos);
		if (state.getBlock() != DeferredRegistryHandler.PANIC_ALARM.get()) {
			return;
		}
		TileEntity tileEntity = worldIn.getTileEntity(pos);

		if (tileEntity instanceof PanicAlarmTileEntity) {
			PanicAlarmTileEntity panicAlarmTileEntity = (PanicAlarmTileEntity) tileEntity;
			if (worldIn.getRedstonePowerFromNeighbors(pos) > 0) {
				boolean isPowered = panicAlarmTileEntity.isPowered();

				if (!isPowered) {
					panicAlarmTileEntity.setPowered(true);
				}
			} else {
				boolean isPowered = panicAlarmTileEntity.isPowered();

				if (isPowered) {
					panicAlarmTileEntity.setPowered(false);
				}
			}
		}
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof PanicAlarmTileEntity) {
			if (!worldIn.isRemote && handIn == Hand.MAIN_HAND) {
				((PanicAlarmTileEntity) tileEntity).changeAlarmSound(player, worldIn);
				return ActionResultType.SUCCESS;
			} else {
				return ActionResultType.PASS;
			}
		}
		return ActionResultType.FAIL;
	}
}