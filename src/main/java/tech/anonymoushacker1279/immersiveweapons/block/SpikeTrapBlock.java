package tech.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.network.PacketDistributor;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;
import tech.anonymoushacker1279.immersiveweapons.network.payload.LocalSoundPayload;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

public class SpikeTrapBlock extends Block implements SimpleWaterloggedBlock {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty POWERED = BooleanProperty.create("powered");
	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

	/**
	 * Constructor for SpikeTrapBlock.
	 *
	 * @param properties the <code>Properties</code> of the block
	 */
	public SpikeTrapBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, Boolean.FALSE).setValue(POWERED, Boolean.FALSE));
	}

	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos,
	                           CollisionContext collisionContext) {
		return SHAPE;
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState neighborState,
	                              LevelAccessor levelAccessor, BlockPos currentPos,
	                              BlockPos neighborPos) {

		return facing == Direction.DOWN && !state.canSurvive(levelAccessor, currentPos)
				? Blocks.AIR.defaultBlockState()
				: super.updateShape(state, facing, neighborState, levelAccessor, currentPos, neighborPos);
	}

	/**
	 * Set placement properties.
	 * Sets the facing direction of the block for placement.
	 *
	 * @param context the <code>BlockPlaceContext</code> during placement
	 * @return BlockState
	 */
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(WATERLOGGED, context.getLevel()
				.getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
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
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	/**
	 * Create the BlockState definition.
	 *
	 * @param builder the <code>StateContainer.Builder</code> of the block
	 */
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED, POWERED);
	}

	/**
	 * Determines if the block can exist in a given state.
	 *
	 * @param state  the <code>BlockState</code> of the block
	 * @param reader the <code>LevelReader</code> for the block
	 * @param pos    the <code>BlocKPos</code> the block is at
	 * @return boolean
	 */
	@SuppressWarnings("deprecation")
	@Override
	public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
		return Block.canSupportCenter(reader, pos.below(), Direction.UP);
	}

	/**
	 * Runs when an entity is inside the block's collision area.
	 * Allows the block to deal damage on contact.
	 *
	 * @param state  the <code>BlockState</code> of the block
	 * @param level  the <code>Level</code> the block is in
	 * @param pos    the <code>BlockPos</code> the block is at
	 * @param entity the <code>Entity</code> passing through the block
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (entity instanceof Player || entity instanceof Mob) {
			if (state.getValue(POWERED)) {
				entity.hurt(IWDamageSources.SPIKE_TRAP, 2f);
			}
		}
	}

	/**
	 * Runs when neighboring blocks change state.
	 *
	 * @param state    the <code>BlockState</code> of the block
	 * @param level    the <code>Level</code> the block is in
	 * @param pos      the <code>BlockPos</code> the block is at
	 * @param oldState the <code>BlockState</code> the block previously had
	 * @param isMoving determines if the block is moving
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
		if (!oldState.is(state.getBlock())) {
			if (level.hasNeighborSignal(pos)) {
				level.setBlock(pos, state.setValue(POWERED, true), 3);
			}
		}
	}

	/**
	 * Runs when neighboring blocks change state.
	 *
	 * @param state    the <code>BlockState</code> of the block
	 * @param level    the <code>Level</code> the block is in
	 * @param pos      the <code>BlockPos</code> the block is at
	 * @param blockIn  the <code>Block</code> that is changing
	 * @param fromPos  the <code>BlockPos</code> of the changing block
	 * @param isMoving determines if the block is moving
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block blockIn,
	                            BlockPos fromPos, boolean isMoving) {
		boolean hasNeighborSignal = level.hasNeighborSignal(pos);
		if (hasNeighborSignal != state.getValue(POWERED)) {
			state = state.setValue(POWERED, hasNeighborSignal);
			if (state.getValue(POWERED)) {
				PacketDistributor.TRACKING_CHUNK.with(level.getChunkAt(pos))
						.send(new LocalSoundPayload(pos, SoundEventRegistry.SPIKE_TRAP_EXTEND.get().getLocation(),
								SoundSource.BLOCKS, 1.0f, 1.0f, true));

				level.gameEvent(GameEvent.BLOCK_ACTIVATE, pos, GameEvent.Context.of(state));
			} else {
				PacketDistributor.TRACKING_CHUNK.with(level.getChunkAt(pos))
						.send(new LocalSoundPayload(pos, SoundEventRegistry.SPIKE_TRAP_RETRACT.get().getLocation(),
								SoundSource.BLOCKS, 1.0f, 1.0f, true));

				level.gameEvent(GameEvent.BLOCK_DEACTIVATE, pos, GameEvent.Context.of(state));
			}

			level.setBlock(pos, state.setValue(POWERED, hasNeighborSignal), 2);
		}
	}
}