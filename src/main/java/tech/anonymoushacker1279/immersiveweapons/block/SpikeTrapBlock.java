package tech.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
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
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.DistExecutor;
import net.neoforged.neoforge.network.NetworkEvent.Context;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.PacketDistributor.TargetPoint;
import tech.anonymoushacker1279.immersiveweapons.init.PacketHandler;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

public class SpikeTrapBlock extends Block implements SimpleWaterloggedBlock {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty POWERED = BooleanProperty.create("powered");
	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

	/**
	 * Constructor for SpikeTrapBlock.
	 *
	 * @param properties the <code>Properties</code> of the blockLocation
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
	 * Sets the facing direction of the blockLocation for placement.
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
	 * Allows the blockLocation to exhibit waterlogged behavior.
	 *
	 * @param state the <code>BlockState</code> of the blockLocation
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
	 * @param builder the <code>StateContainer.Builder</code> of the blockLocation
	 */
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED, POWERED);
	}

	/**
	 * Determines if the blockLocation can exist in a given state.
	 *
	 * @param state  the <code>BlockState</code> of the blockLocation
	 * @param reader the <code>LevelReader</code> for the blockLocation
	 * @param pos    the <code>BlocKPos</code> the blockLocation is at
	 * @return boolean
	 */
	@SuppressWarnings("deprecation")
	@Override
	public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
		return Block.canSupportCenter(reader, pos.below(), Direction.UP);
	}

	/**
	 * Runs when an entity is inside the blockLocation's collision area.
	 * Allows the blockLocation to deal damage on contact.
	 *
	 * @param state  the <code>BlockState</code> of the blockLocation
	 * @param level  the <code>Level</code> the blockLocation is in
	 * @param pos    the <code>BlockPos</code> the blockLocation is at
	 * @param entity the <code>Entity</code> passing through the blockLocation
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
	 * @param state    the <code>BlockState</code> of the blockLocation
	 * @param level    the <code>Level</code> the blockLocation is in
	 * @param pos      the <code>BlockPos</code> the blockLocation is at
	 * @param oldState the <code>BlockState</code> the blockLocation previously had
	 * @param isMoving determines if the blockLocation is moving
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
	 * @param state    the <code>BlockState</code> of the blockLocation
	 * @param level    the <code>Level</code> the blockLocation is in
	 * @param pos      the <code>BlockPos</code> the blockLocation is at
	 * @param blockIn  the <code>Block</code> that is changing
	 * @param fromPos  the <code>BlockPos</code> of the changing blockLocation
	 * @param isMoving determines if the blockLocation is moving
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block blockIn,
	                            BlockPos fromPos, boolean isMoving) {
		boolean flag = level.hasNeighborSignal(pos);
		if (flag != state.getValue(POWERED)) {
			state = state.setValue(POWERED, flag);
			if (state.getValue(POWERED)) {
				PacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(() ->
								new TargetPoint(pos.getX(), pos.getY(), pos.getZ(), 12, level.dimension())),
						new SpikeTrapBlockPacketHandler(pos, true));
			} else {
				PacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(() ->
								new TargetPoint(pos.getX(), pos.getY(), pos.getZ(), 12, level.dimension())),
						new SpikeTrapBlockPacketHandler(pos, false));
			}
			level.setBlock(pos, state.setValue(POWERED, flag), 2);
		}
	}

	public record SpikeTrapBlockPacketHandler(BlockPos blockPos, boolean extend) {

		public static void encode(SpikeTrapBlockPacketHandler msg, FriendlyByteBuf packetBuffer) {
			packetBuffer.writeBlockPos(msg.blockPos).writeBoolean(msg.extend);
		}

		public static SpikeTrapBlockPacketHandler decode(FriendlyByteBuf packetBuffer) {
			return new SpikeTrapBlockPacketHandler(packetBuffer.readBlockPos(), packetBuffer.readBoolean());
		}

		public static void handle(SpikeTrapBlockPacketHandler msg, Context context) {
			context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> handleOnClient(msg)));
			context.setPacketHandled(true);
		}

		private static void handleOnClient(SpikeTrapBlockPacketHandler msg) {
			Minecraft minecraft = Minecraft.getInstance();
			if (minecraft.level != null) {
				if (msg.extend) {
					minecraft.level.playLocalSound(msg.blockPos, SoundEventRegistry.SPIKE_TRAP_EXTEND.get(),
							SoundSource.BLOCKS, 1.0f, 1.0f, true);
				} else {
					minecraft.level.playLocalSound(msg.blockPos, SoundEventRegistry.SPIKE_TRAP_RETRACT.get(),
							SoundSource.BLOCKS, 1.0f, 1.0f, true);
				}
			}
		}
	}
}