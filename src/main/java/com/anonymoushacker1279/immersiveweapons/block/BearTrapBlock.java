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
import net.minecraft.world.World;

public class BearTrapBlock extends ContainerBlock implements IWaterLoggable {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty TRIGGERED = BooleanProperty.create("triggered");
	public static final BooleanProperty VINES = BooleanProperty.create("vines");
	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);

	/**
	 * Constructor for BearTrapBlock.
	 * @param properties the <code>Properties</code> of the block
	 */
	public BearTrapBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(TRIGGERED, Boolean.FALSE).setValue(WATERLOGGED, Boolean.FALSE).setValue(VINES, Boolean.FALSE));
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
			BearTrapTileEntity bearTrap = (BearTrapTileEntity) worldIn.getBlockEntity(pos);
			ItemStack currentlyHeldItem = player.getMainHandItem();
			if (bearTrap != null && state.getValue(TRIGGERED) && !bearTrap.hasTrappedEntity() && !bearTrap.hasTrappedPlayerEntity()) {
				worldIn.setBlock(pos, state.setValue(TRIGGERED, false).setValue(VINES, false), 3);
				return ActionResultType.SUCCESS;
			}
			if (!state.getValue(VINES) && currentlyHeldItem.getItem() == Items.VINE) {
				worldIn.setBlock(pos, state.setValue(VINES, true), 3);
				if (!player.isCreative()) {
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
	 * Create a tile entity for the block.
	 * @param reader the <code>IBlockReader</code> of the block
	 * @return TileEntity
	 */
	@Override
	public TileEntity newBlockEntity(IBlockReader reader) {
		return new BearTrapTileEntity();
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
		final BearTrapTileEntity bearTrap = (BearTrapTileEntity) world.getBlockEntity(pos);

		if (state.getValue(TRIGGERED)) {
			if (bearTrap != null && (bearTrap.getTrappedEntity() == entity || bearTrap.getTrappedPlayerEntity() == entity)) {
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
			if (!livingEntity.isCreative()) {
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
			if (bearTrap != null) {
				bearTrap.setTrappedEntity(livingEntity);
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
	 * Set the block's analog output signal.
	 * @param state the <code>BlockState</code> of the block
	 * @return boolean
	 */
	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	/**
	 * Set the block's analog output signal strength.
	 * @param state the <code>BlockState</code> of the block
	 * @param worldIn the <code>World</code> the block is in
	 * @param pos the <code>BlockPos</code> the block is at
	 * @return int
	 */
	@Override
	public int getAnalogOutputSignal(BlockState state, World worldIn, BlockPos pos) {
		return state.getValue(TRIGGERED) ? 15 : 0;
	}

	/**
	 * Create the BlockState definition.
	 * @param builder the <code>StateContainer.Builder</code> of the block
	 */
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(TRIGGERED, VINES, WATERLOGGED);
	}

	/**
	 * Set the block's signal source.
	 * @param state the <code>BlockState</code> of the block
	 * @return boolean
	 */
	@Override
	public boolean isSignalSource(BlockState state) {
		return state.getValue(TRIGGERED);
	}

	/**
	 * Get the signal of the block.
	 * @param blockState the <code>BlockState</code> of the block
	 * @param reader the <code>IBlockReader</code> for the block
	 * @param pos the <code>BlockPos</code> the block is at
	 * @param side the <code>Direction</code> the block is facing
	 * @return int
	 */
	@Override
	public int getSignal(BlockState blockState, IBlockReader reader, BlockPos pos, Direction side) {
		if (!blockState.isSignalSource()) {
			return 0;
		} else {
			return 15;
		}
	}
}