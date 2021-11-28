package com.anonymoushacker1279.immersiveweapons.block.base;

import com.anonymoushacker1279.immersiveweapons.entity.projectile.MortarShellEntity;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.minecraftforge.fmllegacy.network.NetworkEvent.Context;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class MortarBlock extends HorizontalDirectionalBlock {

	public static final IntegerProperty ROTATION = IntegerProperty.create("rotation", 0, 2);
	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 10.0D, 14.0D);
	private static final BooleanProperty LOADED = BooleanProperty.create("loaded");

	/**
	 * Constructor for MortarBlock.
	 *
	 * @param properties the <code>Properties</code> of the block
	 */
	public MortarBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(ROTATION, 0).setValue(LOADED, false));
	}

	/**
	 * Create the BlockState definition.
	 *
	 * @param builder the <code>StateContainer.Builder</code> of the block
	 */
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(ROTATION, FACING, LOADED);
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
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
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
			ItemStack itemStack = player.getMainHandItem();
			if (state.getValue(LOADED) && itemStack.getItem() == Items.FLINT_AND_STEEL) {
				if (!player.isCreative()) {
					itemStack.setDamageValue(itemStack.getDamageValue() - 1);
				}
				fire(worldIn, pos, state);
				return InteractionResult.CONSUME;
			} else if (!state.getValue(LOADED) && itemStack.getItem() == DeferredRegistryHandler.MORTAR_SHELL.get()) {
				worldIn.setBlock(pos, state.setValue(LOADED, true), 3);
				if (!player.isCreative()) {
					itemStack.shrink(1);
				}
				return InteractionResult.CONSUME;
			} else if (itemStack.getItem() == Items.AIR && player.isCrouching()) {
				if (state.getValue(LOADED)) {
					if (!player.isCreative()) {
						player.getInventory().add(new ItemStack(DeferredRegistryHandler.MORTAR_SHELL.get()));
					}
					worldIn.setBlock(pos, state.setValue(LOADED, false), 3);
				}
				return InteractionResult.SUCCESS;
			} else if (itemStack.getItem() == Items.AIR) {
				if (state.getValue(ROTATION) < 2) {
					worldIn.setBlock(pos, state.setValue(ROTATION, state.getValue(ROTATION) + 1), 3);
				} else {
					worldIn.setBlock(pos, state.setValue(ROTATION, 0), 3);
				}
			}

			return InteractionResult.SUCCESS;
		}

		return InteractionResult.PASS;
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
			if (state.getValue(LOADED) && worldIn.hasNeighborSignal(pos)) {
				fire(worldIn, pos, state);
			}
		}
	}

	/**
	 * Fires a mortar shell and sends packets to tracking players.
	 *
	 * @param worldIn the <code>World</code> the block is in
	 * @param pos     the <code>BlockPos</code> the block is at
	 * @param state   the <code>BlockState</code> of the block
	 */
	private void fire(Level worldIn, BlockPos pos, BlockState state) {
		PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> worldIn.getChunkAt(pos)), new MortarBlockPacketHandler(pos));
		worldIn.setBlock(pos, state.setValue(LOADED, false), 3);
		MortarShellEntity.create(worldIn, pos, 1f, state);
	}

	public record MortarBlockPacketHandler(BlockPos blockPos) {

		/**
		 * Constructor for MortarBlockPacketHandler.
		 *
		 * @param blockPos the <code>BlockPos</code> of the block where the packet was sent
		 */
		public MortarBlockPacketHandler {
		}

		/**
		 * Encodes a packet
		 *
		 * @param msg          the <code>MortarBlockPacketHandler</code> message being sent
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 */
		public static void encode(MortarBlockPacketHandler msg, FriendlyByteBuf packetBuffer) {
			packetBuffer.writeBlockPos(msg.blockPos);
		}

		/**
		 * Decodes a packet
		 *
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 * @return MortarBlockPacketHandler
		 */
		public static MortarBlockPacketHandler decode(FriendlyByteBuf packetBuffer) {
			return new MortarBlockPacketHandler(packetBuffer.readBlockPos());
		}

		/**
		 * Handles an incoming packet, by sending it to the client/server
		 *
		 * @param msg             the <code>MortarBlockPacketHandler</code> message being sent
		 * @param contextSupplier the <code>Supplier</code> providing context
		 */
		public static void handle(MortarBlockPacketHandler msg, Supplier<Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> handleOnClient(msg)));
			context.setPacketHandled(true);
		}

		/**
		 * Runs specifically on the client, when a packet is received
		 *
		 * @param msg the <code>MortarBlockPacketHandler</code> message being sent
		 */
		@OnlyIn(Dist.CLIENT)
		private static void handleOnClient(MortarBlockPacketHandler msg) {
			Minecraft minecraft = Minecraft.getInstance();
			if (minecraft.level != null) {
				minecraft.level.playLocalSound(msg.blockPos, SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 0.7f, 1f, false);
			}
		}
	}
}