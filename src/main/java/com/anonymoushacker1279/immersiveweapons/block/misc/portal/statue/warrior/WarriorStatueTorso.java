package com.anonymoushacker1279.immersiveweapons.block.misc.portal.statue.warrior;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import com.anonymoushacker1279.immersiveweapons.util.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.minecraftforge.fmllegacy.network.NetworkEvent.Context;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import net.minecraftforge.fmllegacy.network.PacketDistributor.TargetPoint;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class WarriorStatueTorso extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	private static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

	public WarriorStatueTorso(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWERED, false).setValue(WATERLOGGED, false));
	}

	/**
	 * Create the BlockState definition.
	 *
	 * @param builder the <code>StateContainer.Builder</code> of the block
	 */
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, POWERED, WATERLOGGED);
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
		return switch (state.getValue(FACING)) {
			case SOUTH -> SHAPE.move(0.0D, 0.0D, -0.1D);
			case EAST -> SHAPE.move(-0.1D, 0.0D, 0.0D);
			case WEST -> SHAPE.move(0.1D, 0.0D, 0.0D);
			default -> SHAPE.move(0.0D, 0.0D, 0.1D);
		};
	}

	/**
	 * Set placement properties.
	 * Sets the facing direction of the block for placement.
	 *
	 * @param context the <code>BlockItemUseContext</code> during placement
	 * @return BlockState
	 */
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState blockStateBelow = context.getLevel().getBlockState(context.getClickedPos().below());
		if (blockStateBelow.getBlock() instanceof WarriorStatueBase &&
				blockStateBelow.getValue(FACING) == context.getHorizontalDirection().getOpposite()) {
			context.getLevel().playLocalSound(context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.BLOCKS, 0.5f, GeneralUtilities.getRandomNumber(0.3f, 0.4f), false);
			return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
		} else {
			return Blocks.AIR.defaultBlockState();
		}
	}

	/**
	 * Set the shading brightness on the client.
	 *
	 * @param state  the <code>BlockState</code> of the block
	 * @param reader the <code>IBlockReader</code> of the block
	 * @param pos    the <code>BlockPos</code> the block is at
	 * @return float
	 */
	@SuppressWarnings("deprecation")
	@Override
	@OnlyIn(Dist.CLIENT)
	public float getShadeBrightness(@NotNull BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos pos) {
		return 1.0F;
	}

	@Override
	public @NotNull FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	/**
	 * Runs when neighboring blocks change state.
	 *
	 * @param state    the <code>BlockState</code> of the block
	 * @param worldIn  the <code>World</code> the block is in
	 * @param pos      the <code>BlockPos</code> the block is at
	 * @param blockIn  the <code>Block</code> that is changing
	 * @param fromPos  the <code>BlockPos</code> of the changing block
	 * @param isMoving determines if the block is moving
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void neighborChanged(@NotNull BlockState state, Level worldIn, @NotNull BlockPos pos, @NotNull Block blockIn, @NotNull BlockPos fromPos, boolean isMoving) {
		if (!worldIn.isClientSide) {
			if (worldIn.getBlockState(pos.below()).getBlock() != DeferredRegistryHandler.WARRIOR_STATUE_BASE.get()) {
				worldIn.destroyBlock(pos, true);
				worldIn.destroyBlock(pos.above(), true);
			}
		}
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
		if (!worldIn.isClientSide && handIn.equals(InteractionHand.MAIN_HAND)) {
			if (worldIn.getBlockState(pos.above()).getBlock() == DeferredRegistryHandler.WARRIOR_STATUE_HEAD.get()) {
				ItemStack itemStack = player.getMainHandItem();
				if (!state.getValue(POWERED) && itemStack.getItem() == DeferredRegistryHandler.AZUL_KEYSTONE.get()) {
					if (!player.isCreative()) {
						itemStack.shrink(1);
					}
					worldIn.setBlock(pos, state.setValue(POWERED, true), 3);
					worldIn.setBlock(pos.above(), DeferredRegistryHandler.WARRIOR_STATUE_HEAD.get().defaultBlockState().setValue(FACING, state.getValue(FACING)).setValue(POWERED, true), 3);

					worldIn.destroyBlock(pos.below(2).relative(state.getValue(FACING)), true);
					worldIn.setBlock(pos.below(2).relative(state.getValue(FACING)), Blocks.GRASS_BLOCK.defaultBlockState(), 3);
					worldIn.destroyBlock(pos.below().relative(state.getValue(FACING)), true);
					worldIn.setBlock(pos.below().relative(state.getValue(FACING)), DeferredRegistryHandler.AZUL_STAINED_ORCHID.get().defaultBlockState(), 3);

					PacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(() -> new TargetPoint(pos.getX(), pos.getY(), pos.getZ(), 12, worldIn.dimension())), new WarriorStatueTorsoPacketHandler(pos, 1));

					return InteractionResult.CONSUME;
				} else {
					PacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(() -> new TargetPoint(pos.getX(), pos.getY(), pos.getZ(), 12, worldIn.dimension())), new WarriorStatueTorsoPacketHandler(pos, 2));
				}
			}

			return InteractionResult.SUCCESS;
		}

		return InteractionResult.PASS;
	}

	/**
	 * Runs when the player destroys the block.
	 *
	 * @param worldIn the <code>World</code> the block is in
	 * @param pos     the <code>BlockPos</code> the block is at
	 * @param state   the <code>BlockState</code> of the block
	 * @param player  the <code>PlayerEntity</code> destroying the block
	 */
	@Override
	public void playerWillDestroy(@NotNull Level worldIn, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
		if (worldIn.getBlockState(pos.above()).getBlock() == DeferredRegistryHandler.WARRIOR_STATUE_HEAD.get()) {
			worldIn.destroyBlock(pos.above(), true, null);
		}

		super.playerWillDestroy(worldIn, pos, state, player);
	}

	public record WarriorStatueTorsoPacketHandler(BlockPos blockPos, int soundType) {

		/**
		 * Constructor for WarriorStatueTorsoPacketHandler.
		 */
		public WarriorStatueTorsoPacketHandler {
		}

		/**
		 * Encodes a packet
		 *
		 * @param msg          the <code>WarriorStatueTorsoPacketHandler</code> message being sent
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 */
		public static void encode(WarriorStatueTorsoPacketHandler msg, FriendlyByteBuf packetBuffer) {
			packetBuffer.writeBlockPos(msg.blockPos).writeInt(msg.soundType);
		}

		/**
		 * Decodes a packet
		 *
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 * @return WarriorStatueTorsoPacketHandler
		 */
		public static WarriorStatueTorsoPacketHandler decode(FriendlyByteBuf packetBuffer) {
			return new WarriorStatueTorsoPacketHandler(packetBuffer.readBlockPos(), packetBuffer.readInt());
		}

		/**
		 * Handles an incoming packet, by sending it to the client/server
		 *
		 * @param msg             the <code>WarriorStatueTorsoPacketHandler</code> message being sent
		 * @param contextSupplier the <code>Supplier</code> providing context
		 */
		public static void handle(WarriorStatueTorsoPacketHandler msg, Supplier<Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> handleOnClient(msg)));
			context.setPacketHandled(true);
		}

		/**
		 * Runs specifically on the client, when a packet is received
		 *
		 * @param msg the <code>WarriorStatueTorsoPacketHandler</code> message being sent
		 */
		@OnlyIn(Dist.CLIENT)
		private static void handleOnClient(WarriorStatueTorsoPacketHandler msg) {
			Minecraft minecraft = Minecraft.getInstance();
			if (minecraft.level != null) {
				if (msg.soundType == 1) {
					minecraft.level.playLocalSound(msg.blockPos, SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.BLOCKS, 0.6f, 1.0f, false);
					for (int i = 0; i < 25; i++) {
						minecraft.level.addParticle(ParticleTypes.DRIPPING_WATER, msg.blockPos.getX() + 0.5d + GeneralUtilities.getRandomNumber(-1.0d, 1.0d), msg.blockPos.getY() + GeneralUtilities.getRandomNumber(-2.0d, 1.5d), msg.blockPos.getZ() + 0.5d + GeneralUtilities.getRandomNumber(-1.0d, 1.0d), GeneralUtilities.getRandomNumber(-0.03d, 0.03d), GeneralUtilities.getRandomNumber(-0.1d, -0.08d), GeneralUtilities.getRandomNumber(-0.03d, 0.03d));
					}
				} else if (msg.soundType == 2) {
					minecraft.level.playLocalSound(msg.blockPos, SoundEvents.ENDERMITE_AMBIENT, SoundSource.BLOCKS, 0.6f, GeneralUtilities.getRandomNumber(0.4f, 0.8f), false);
				}
			}
		}
	}
}