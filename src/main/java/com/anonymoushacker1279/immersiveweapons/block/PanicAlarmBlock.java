package com.anonymoushacker1279.immersiveweapons.block;

import com.anonymoushacker1279.immersiveweapons.blockentity.PanicAlarmBlockEntity;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class PanicAlarmBlock extends BaseEntityBlock implements SimpleWaterloggedBlock, EntityBlock {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
	private static final VoxelShape SHAPE_NORTH = Block.box(4.0D, 4.0D, 13.0D, 12.0D, 11.0D, 16.0D);
	private static final VoxelShape SHAPE_SOUTH = Block.box(4.0D, 4.0D, 0.0D, 12.0D, 11.0D, 3.0D);
	private static final VoxelShape SHAPE_EAST = Block.box(0.0D, 4.0D, 4.0D, 3.0D, 11.0D, 12.0D);
	private static final VoxelShape SHAPE_WEST = Block.box(13.0D, 4.0D, 4.0D, 16.0D, 11.0D, 12.0D);

	/**
	 * Constructor for PanicAlarmBlock.
	 * @param properties the <code>Properties</code> of the block
	 */
	public PanicAlarmBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, false).setValue(FACING, Direction.NORTH));
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
	public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext selectionContext) {
		Vec3 vector3d = state.getOffset(reader, pos);
		switch (state.getValue(FACING)) {
			case NORTH:
			default:
				return SHAPE_NORTH.move(vector3d.x, vector3d.y, vector3d.z);
			case SOUTH:
				return SHAPE_SOUTH.move(vector3d.x, vector3d.y, vector3d.z);
			case EAST:
				return SHAPE_EAST.move(vector3d.x, vector3d.y, vector3d.z);
			case WEST:
				return SHAPE_WEST.move(vector3d.x, vector3d.y, vector3d.z);
		}
	}

	/**
	 * Create the BlockState definition.
	 * @param builder the <code>StateContainer.Builder</code> of the block
	 */
	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED, FACING);
	}

	/**
	 * Set the RenderShape of the block's model.
	 * @param state the <code>BlockState</code> of the block
	 * @return BlockRenderType
	 */
	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	/**
	 * Create a tile entity for the block.
	 * @param blockPos the <code>BlockPos</code> the block is at
	 * @param blockState the <code>BlockState</code> of the block
	 * @return TileEntity
	 */
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new PanicAlarmBlockEntity(blockPos, blockState);
	}

	// TODO: Javdocs
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
		return level.isClientSide ? null : createTickerHelper(blockEntityType, DeferredRegistryHandler.PANIC_ALARM_BLOCK_ENTITY.get(), PanicAlarmBlockEntity::serverTick);
	}

	/**
	 * Set placement properties.
	 * Sets the facing direction of the block for placement.
	 * @param context the <code>BlockItemUseContext</code> during placement
	 * @return BlockState
	 */
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
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
	 * Runs when neighboring blocks change state.
	 * @param state the <code>BlockState</code> of the block
	 * @param worldIn the <code>World</code> the block is in
	 * @param pos the <code>BlockPos</code> the block is at
	 * @param blockIn the <code>Block</code> that is changing
	 * @param fromPos the <code>BlockPos</code> of the changing block
	 * @param isMoving determines if the block is moving
	 */
	@Override
	public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		if (!worldIn.isClientSide) {
			playSiren(worldIn, pos);
			worldIn.getBlockTicks().scheduleTick(pos, state.getBlock(), 5);
		}
	}

	/**
	 * Runs when neighboring blocks change state.
	 * @param state the <code>BlockState</code> of the block
	 * @param worldIn the <code>World</code> the block is in
	 * @param pos the <code>BlockPos</code> the block is at
	 * @param oldState the <code>BlockState</code> the block previously had
	 * @param isMoving determines if the block is moving
	 */
	@Override
	public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		if (!oldState.is(state.getBlock())) {
			if (worldIn.hasNeighborSignal(pos)) {
				if (!worldIn.isClientSide) {
					worldIn.getBlockTicks().scheduleTick(pos, state.getBlock(), 5);
				}
			}
		}
	}

	/**
	 * Runs once every tick
	 * @param state the <code>BlockState</code> of the block
	 * @param worldIn the <code>ServerWorld</code> of the block
	 * @param pos the <code>BlockPos</code> the block is at
	 * @param rand a <code>Random</code> instance
	 */
	@Override
	public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random rand) {
		if (!worldIn.isClientSide) {
			playSiren(worldIn, pos);
			worldIn.getBlockTicks().scheduleTick(pos, state.getBlock(), 5);
		}
	}

	/**
	 * Plays a sound when powered.
	 * @param worldIn the <code>World</code> the block is at
	 * @param pos the <code>BlockPos</code> the block is at
	 */
	private void playSiren(Level worldIn, BlockPos pos) {
		BlockState state = worldIn.getBlockState(pos);
		if (state.getBlock() != DeferredRegistryHandler.PANIC_ALARM.get()) {
			return;
		}
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);

		if (tileEntity instanceof PanicAlarmBlockEntity) {
			PanicAlarmBlockEntity panicAlarmTileEntity = (PanicAlarmBlockEntity) tileEntity;
			if (worldIn.getBestNeighborSignal(pos) > 0) {
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
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult blockRayTraceResult) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		if (tileEntity instanceof PanicAlarmBlockEntity) {
			if (!worldIn.isClientSide && handIn == InteractionHand.MAIN_HAND) {
				((PanicAlarmBlockEntity) tileEntity).changeAlarmSound(player);
				return InteractionResult.SUCCESS;
			} else {
				return InteractionResult.PASS;
			}
		}
		return InteractionResult.FAIL;
	}
}