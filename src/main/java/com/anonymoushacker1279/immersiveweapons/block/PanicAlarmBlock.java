package com.anonymoushacker1279.immersiveweapons.block;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.tileentity.PanicAlarmTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
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
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class PanicAlarmBlock extends HorizontalBlock implements IWaterLoggable {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected static final VoxelShape SHAPE_NORTH = Block.box(4.0D, 4.0D, 13.0D, 12.0D, 11.0D, 16.0D);
	protected static final VoxelShape SHAPE_SOUTH = Block.box(4.0D, 4.0D, 0.0D, 12.0D, 11.0D, 3.0D);
	protected static final VoxelShape SHAPE_EAST = Block.box(0.0D, 4.0D, 4.0D, 3.0D, 11.0D, 12.0D);
	protected static final VoxelShape SHAPE_WEST = Block.box(13.0D, 4.0D, 4.0D, 16.0D, 11.0D, 12.0D);

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
	public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext selectionContext) {
		Vector3d vector3d = state.getOffset(reader, pos);
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
	public void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED, FACING);
	}

	/**
	 * Determine if a block has a tile entity.
	 * @param state the <code>BlockState</code> of the block
	 * @return boolean
	 */
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	/**
	 * Create a tile entity for a block.
	 * @param state the <code>BlockState</code> of the block
	 * @param reader the <code>IBlockReader</code> for the block
	 * @return TileEntity
	 */
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader reader) {
		return new PanicAlarmTileEntity();
	}

	/**
	 * Set placement properties.
	 * Sets the facing direction of the block for placement.
	 * @param context the <code>BlockItemUseContext</code> during placement
	 * @return BlockState
	 */
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
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
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
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
	public void onPlace(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
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
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
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
	private void playSiren(World worldIn, BlockPos pos) {
		BlockState state = worldIn.getBlockState(pos);
		if (state.getBlock() != DeferredRegistryHandler.PANIC_ALARM.get()) {
			return;
		}
		TileEntity tileEntity = worldIn.getBlockEntity(pos);

		if (tileEntity instanceof PanicAlarmTileEntity) {
			PanicAlarmTileEntity panicAlarmTileEntity = (PanicAlarmTileEntity) tileEntity;
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
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult blockRayTraceResult) {
		TileEntity tileEntity = worldIn.getBlockEntity(pos);
		if (tileEntity instanceof PanicAlarmTileEntity) {
			if (!worldIn.isClientSide && handIn == Hand.MAIN_HAND) {
				((PanicAlarmTileEntity) tileEntity).changeAlarmSound(player);
				return ActionResultType.SUCCESS;
			} else {
				return ActionResultType.PASS;
			}
		}
		return ActionResultType.FAIL;
	}
}