package tech.anonymoushacker1279.immersiveweapons.block.crafting;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.SimpleContainerData;
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
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.blockentity.TeslaSynthesizerBlockEntity;
import tech.anonymoushacker1279.immersiveweapons.menu.TeslaSynthesizerMenu;

public class TeslaSynthesizerBlock extends Block implements SimpleWaterloggedBlock, EntityBlock {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D);
	private static final Component CONTAINER_NAME = Component.translatable("container.immersiveweapons.tesla_synthesizer");

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
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new TeslaSynthesizerBlockEntity(blockPos, blockState);
	}

	/**
	 * Set the shape of the block.
	 *
	 * @param state            the <code>BlockState</code> of the block
	 * @param getter           the <code>BlockGetter</code> for the block
	 * @param pos              the <code>BlockPos</code> the block is at
	 * @param collisionContext the <code>CollisionContext</code> of the block
	 * @return VoxelShape
	 */
	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos,
	                           CollisionContext collisionContext) {

		return SHAPE;
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
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState,
	                                                              BlockEntityType<T> blockEntityType) {

		return level.isClientSide ? null : (world, pos, state, entity) -> ((TeslaSynthesizerBlockEntity) entity).tick(world);
	}

	/**
	 * Create the BlockState definition.
	 *
	 * @param builder the <code>StateDefinition.Builder</code> of the block
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
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	/**
	 * Runs when the block is activated.
	 * Allows the block to respond to user interaction.
	 *
	 * @param state     the <code>BlockState</code> of the block
	 * @param level     the <code>Level</code> the block is in
	 * @param pos       the <code>BlockPos</code> the block is at
	 * @param player    the <code>Player</code> interacting with the block
	 * @param hand      the <code>InteractionHand</code> the PlayerEntity used
	 * @param hitResult the <code>BlockHitResult</code> of the interaction
	 * @return InteractionResult
	 */
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

		if (level.getBlockEntity(pos) instanceof TeslaSynthesizerBlockEntity blockEntity && player instanceof ServerPlayer serverPlayer) {
			serverPlayer.openMenu(new SimpleMenuProvider((id, inventory, player1) -> new TeslaSynthesizerMenu(id, inventory, blockEntity, new SimpleContainerData(1)), CONTAINER_NAME));

			return InteractionResult.CONSUME;
		}

		return InteractionResult.SUCCESS;
	}

	/**
	 * Runs when the block is removed.
	 *
	 * @param state    the <code>BlockState</code> of the block
	 * @param level    the <code>Level</code> the block is in
	 * @param pos      the <code>BlockPos</code> the block is at
	 * @param newState the <code>BlockState</code> the block now has
	 * @param isMoving determines if the block is moving
	 */
	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.is(newState.getBlock())) {
			if (level.getBlockEntity(pos) instanceof TeslaSynthesizerBlockEntity teslaSynthesizerBlockEntity) {
				Containers.dropContents(level, pos, teslaSynthesizerBlockEntity);
				level.updateNeighbourForOutputSignal(pos, this);
			}

			super.onRemove(state, level, pos, newState, isMoving);
		}
	}

	/**
	 * Runs occasionally to create animations.
	 *
	 * @param state  the <code>BlockState</code> of the block
	 * @param level  the <code>Level</code> the block is in
	 * @param pos    the <code>BlockPos</code> the block is at
	 * @param random a <code>RandomSource</code> instance
	 */
	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		level.addParticle(ParticleTypes.SOUL_FIRE_FLAME,
				pos.getX() + 0.5D,
				pos.getY() + 0.4D,
				pos.getZ() + 0.5D,
				0.0D, 0.0D, 0.0D);
	}
}