package com.anonymoushacker1279.immersiveweapons.block;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import java.util.List;

public class SpikeTrapBlock extends Block implements IWaterLoggable {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
	private DamageSource damageSource = new DamageSource("immersiveweapons.spike_trap");
	public static final BooleanProperty POWERED = BooleanProperty.create("powered");

	public SpikeTrapBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(WATERLOGGED, Boolean.FALSE).with(POWERED, Boolean.FALSE));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		Vector3d vector3d = state.getOffset(worldIn, pos);
		return SHAPE.withOffset(vector3d.x, vector3d.y, vector3d.z);
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
		FluidState ifluidstate = context.getWorld().getFluidState(context.getPos());

		return this.getDefaultState().with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
	}

	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED, POWERED);
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return Block.hasEnoughSolidSide(worldIn, pos.down(), Direction.UP);
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (entity instanceof PlayerEntity || entity instanceof MobEntity) {
			if (state.get(POWERED)) {
				entity.attackEntityFrom(damageSource, 2f);
			}
		}
	}

	@Override
	public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		if (!oldState.matchesBlock(state.getBlock())) {
			if (worldIn.isBlockPowered(pos)) {
				worldIn.setBlockState(pos, state.with(POWERED, true), 3);
			}
		}
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		boolean flag = worldIn.isBlockPowered(pos);
		if (flag != state.get(POWERED)) {
			state = state.with(POWERED, flag);
			// A bit hacky here. This runs from the client so we can't use server logic to broadcast to all players.
			AxisAlignedBB bound = new AxisAlignedBB(pos.getX() - 7, pos.getY() - 7, pos.getZ() - 7, pos.getX() + 7, pos.getY() + 7, pos.getZ() + 7);
			List<PlayerEntity> entitiesInBound = worldIn.getEntitiesWithinAABB(PlayerEntity.class, bound);
			for (int i = 0; i < worldIn.getPlayers().size(); i++) {
				if (entitiesInBound.contains(worldIn.getPlayers().get(i))) {
					if (state.get(POWERED)) {
						worldIn.getPlayers().get(i).playSound(DeferredRegistryHandler.SPIKE_TRAP_EXTEND.get(), SoundCategory.BLOCKS, 1.0f, 1.0f);
					} else {
						worldIn.getPlayers().get(i).playSound(DeferredRegistryHandler.SPIKE_TRAP_RETRACT.get(), SoundCategory.BLOCKS, 1.0f, 1.0f);
					}
				}
			}
			worldIn.setBlockState(pos, state.with(POWERED, flag), 2);
		}
	}
}