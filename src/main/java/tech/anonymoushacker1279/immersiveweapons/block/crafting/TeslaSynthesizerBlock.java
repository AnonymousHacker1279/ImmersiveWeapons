package tech.anonymoushacker1279.immersiveweapons.block.crafting;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.blockentity.AbstractTeslaSynthesizerBlockEntity;
import tech.anonymoushacker1279.immersiveweapons.blockentity.TeslaSynthesizerBlockEntity;
import tech.anonymoushacker1279.immersiveweapons.container.TeslaSynthesizerContainer;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

import javax.annotation.Nullable;
import java.util.Random;

public class TeslaSynthesizerBlock extends Block implements EntityBlock, SimpleWaterloggedBlock {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D);
	private static final Component CONTAINER_NAME = new TranslatableComponent("container.immersiveweapons.tesla_synthesizer");

	/**
	 * Constructor for TeslaSynthesizerBlock.
	 *
	 * @param properties the <code>Properties</code> of the block
	 */
	public TeslaSynthesizerBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, false));
	}

	/**
	 * Create a block entity for the block.
	 *
	 * @param blockPos   the <code>BlockPos</code> the block is at
	 * @param blockState the <code>BlockState</code> of the block
	 * @return BlockEntity
	 */
	@Nullable
	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
		return new TeslaSynthesizerBlockEntity(blockPos, blockState);
	}

	/**
	 * Get the ticker for the block.
	 *
	 * @param level           the <code>Level</code> the block is in
	 * @param blockState      the <code>BlockState</code> of the block
	 * @param blockEntityType the <code>BlockEntityType</code> to get the ticker of
	 * @param <T>             the type extending BlockEntity
	 * @return BlockEntityTicker
	 */
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState blockState, @NotNull BlockEntityType<T> blockEntityType) {
		return level.isClientSide ? null : BaseEntityBlock.createTickerHelper(blockEntityType, DeferredRegistryHandler.TESLA_SYNTHESIZER_BLOCK_ENTITY.get(), (level1, blockPos, blockState1, abstractTeslaSynthesizerTileEntity) -> TeslaSynthesizerBlockEntity.serverTick(level1, abstractTeslaSynthesizerTileEntity));
	}

	/**
	 * Set the shape of the block.
	 *
	 * @param state            the <code>BlockState</code> of the block
	 * @param reader           the <code>IBlockReader</code> for the block
	 * @param pos              the <code>BlockPos</code> the block is at
	 * @param selectionContext the <code>ISelectionContext</code> of the block
	 * @return VoxelShape
	 */
	@SuppressWarnings("deprecation")
	@Override
	public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos pos, @NotNull CollisionContext selectionContext) {
		return SHAPE;
	}

	/**
	 * Create the BlockState definition.
	 *
	 * @param builder the <code>StateContainer.Builder</code> of the block
	 */
	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}

	/**
	 * Set FluidState properties.
	 * Allows the block to exhibit waterlogged behavior.
	 *
	 * @param state the <code>BlockState</code> of the block
	 * @return FluidState
	 */
	@SuppressWarnings("deprecation")
	@Override
	public @NotNull FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	/**
	 * Get the INamedContainerProvider for the block.
	 *
	 * @param state   the <code>BlockState</code> of the block
	 * @param worldIn the <code>World</code> the block is in
	 * @param pos     the <code>BlockPos</code> the block is at
	 * @return INamedContainerProvider
	 */
	@SuppressWarnings("deprecation")
	@Override
	public MenuProvider getMenuProvider(@NotNull BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos) {
		return new SimpleMenuProvider((id, inventory, player) -> new TeslaSynthesizerContainer(id, inventory), CONTAINER_NAME);
	}

	/**
	 * Runs when the block is activated.
	 * Allows the block to respond to user interaction.
	 *
	 * @param state               the <code>BlockState</code> of the block
	 * @param worldIn             the <code>World</code> the block is in
	 * @param pos                 the <code>BlockPos</code> the block is at
	 * @param player              the <code>PlayerEntity</code> interacting with the block
	 * @param handIn              the <code>Hand</code> the PlayerEntity used
	 * @param blockRayTraceResult the <code>BlockRayTraceResult</code> of the interaction
	 * @return ActionResultType
	 */
	@SuppressWarnings("deprecation")
	@Override
	public @NotNull InteractionResult use(@NotNull BlockState state, Level worldIn, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand handIn, @NotNull BlockHitResult blockRayTraceResult) {
		if (worldIn.isClientSide) {
			return InteractionResult.SUCCESS;
		} else {
			BlockEntity tileEntity = worldIn.getBlockEntity(pos);
			if (tileEntity instanceof TeslaSynthesizerBlockEntity) {
				NetworkHooks.openGui((ServerPlayer) player, (MenuProvider) tileEntity, pos);
			}
			return InteractionResult.CONSUME;
		}
	}

	/**
	 * Runs when the block is removed.
	 *
	 * @param state    the <code>BlockState</code> of the block
	 * @param worldIn  the <code>World</code> the block is in
	 * @param pos      the <code>BlockPos</code> the block is at
	 * @param newState the <code>BlockState</code> the block now has
	 * @param isMoving determines if the block is moving
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.is(newState.getBlock())) {
			BlockEntity tileEntity = worldIn.getBlockEntity(pos);
			if (tileEntity instanceof AbstractTeslaSynthesizerBlockEntity) {
				Containers.dropContents(worldIn, pos, (AbstractTeslaSynthesizerBlockEntity) tileEntity);
				worldIn.updateNeighbourForOutputSignal(pos, this);
			}

			super.onRemove(state, worldIn, pos, newState, isMoving);
		}
	}

	/**
	 * Runs occasionally to create animations.
	 *
	 * @param stateIn the <code>BlockState</code> of the block
	 * @param worldIn the <code>World</code> the block is in
	 * @param pos     the <code>BlockPos</code> the block is at
	 * @param rand    a <code>Random</code> instance
	 */
	@Override
	public void animateTick(@NotNull BlockState stateIn, Level worldIn, BlockPos pos, @NotNull Random rand) {
		worldIn.addParticle(ParticleTypes.SOUL_FIRE_FLAME, pos.getX() + 0.5D, pos.getY() + 0.4D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
	}
}