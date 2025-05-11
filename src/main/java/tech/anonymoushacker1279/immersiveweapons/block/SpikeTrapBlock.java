package tech.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.network.PacketDistributor;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;
import tech.anonymoushacker1279.immersiveweapons.network.payload.LocalSoundPayload;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

import javax.annotation.Nullable;

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

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos,
	                           CollisionContext collisionContext) {
		return SHAPE;
	}

	@Override
	protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
		return direction == Direction.DOWN && !state.canSurvive(level, pos)
				? Blocks.AIR.defaultBlockState()
				: super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(WATERLOGGED, context.getLevel()
				.getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED, POWERED);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
		return Block.canSupportCenter(reader, pos.below(), Direction.UP);
	}

	@Override
	protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier) {
		if (entity instanceof Player || entity instanceof Mob) {
			if (state.getValue(POWERED)) {
				entity.hurt(IWDamageSources.spikeTrap(level.registryAccess()), 2f);
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
	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
		if (!oldState.is(state.getBlock())) {
			if (level.hasNeighborSignal(pos)) {
				level.setBlock(pos, state.setValue(POWERED, true), 3);
			}
		}
	}

	@Override
	protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, @Nullable Orientation orientation, boolean movedByPiston) {
		boolean hasNeighborSignal = level.hasNeighborSignal(pos);
		if (hasNeighborSignal != state.getValue(POWERED) && level instanceof ServerLevel serverLevel) {
			state = state.setValue(POWERED, hasNeighborSignal);
			if (state.getValue(POWERED)) {
				PacketDistributor.sendToPlayersTrackingChunk(serverLevel, serverLevel.getChunk(pos).getPos(),
						new LocalSoundPayload(pos, SoundEventRegistry.SPIKE_TRAP_EXTEND.getKey(),
								SoundSource.BLOCKS, 1.0f, 1.0f, true));

				level.gameEvent(GameEvent.BLOCK_ACTIVATE, pos, GameEvent.Context.of(state));
			} else {
				PacketDistributor.sendToPlayersTrackingChunk(serverLevel, serverLevel.getChunk(pos).getPos(),
						new LocalSoundPayload(pos, SoundEventRegistry.SPIKE_TRAP_RETRACT.getKey(),
								SoundSource.BLOCKS, 1.0f, 1.0f, true));

				level.gameEvent(GameEvent.BLOCK_DEACTIVATE, pos, GameEvent.Context.of(state));
			}

			level.setBlock(pos, state.setValue(POWERED, hasNeighborSignal), 2);
		}
	}
}