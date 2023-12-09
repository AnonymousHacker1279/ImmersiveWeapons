package tech.anonymoushacker1279.immersiveweapons.block.misc.warrior_statue;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.DistExecutor;
import net.neoforged.neoforge.network.NetworkEvent.Context;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.PacketDistributor.TargetPoint;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class WarriorStatueTorso extends WarriorStatueBase {

	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	public WarriorStatueTorso(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWERED, false)
				.setValue(WATERLOGGED, false));
	}

	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, POWERED, WATERLOGGED);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState blockStateBelow = context.getLevel().getBlockState(context.getClickedPos().below());
		if (blockStateBelow.getBlock() == BlockRegistry.WARRIOR_STATUE_BASE.get() &&
				blockStateBelow.getValue(FACING) == context.getHorizontalDirection().getOpposite()) {

			context.getLevel().playLocalSound(context.getClickedPos().getX(),
					context.getClickedPos().getY(),
					context.getClickedPos().getZ(),
					SoundEvents.END_PORTAL_FRAME_FILL,
					SoundSource.BLOCKS,
					0.5f,
					GeneralUtilities.getRandomNumber(0.3f, 0.4f),
					false);

			return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
		} else {
			return Blocks.AIR.defaultBlockState();
		}
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		if (level.getBlockState(pos.below()).getBlock() != BlockRegistry.WARRIOR_STATUE_BASE.get()) {
			return false;
		}

		return super.canSurvive(state, level, pos);
	}

	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
		if (!level.isClientSide) {
			if (level.getBlockState(pos.below()).getBlock() != BlockRegistry.WARRIOR_STATUE_BASE.get()) {
				level.destroyBlock(pos, true);
			}
		}
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos,
	                             Player player, InteractionHand hand,
	                             BlockHitResult hitResult) {

		if (level instanceof ServerLevel serverLevel && hand.equals(InteractionHand.MAIN_HAND)) {
			if (level.getBlockState(pos.above()).getBlock() == BlockRegistry.WARRIOR_STATUE_HEAD.get()) {
				ItemStack itemStack = player.getMainHandItem();
				if (!state.getValue(POWERED) && itemStack.getItem() == ItemRegistry.AZUL_KEYSTONE.get()) {
					if (!player.isCreative()) {
						itemStack.shrink(1);
					}

					serverLevel.setBlock(pos, state.setValue(POWERED, true), 3);
					serverLevel.setBlock(pos.above(), BlockRegistry.WARRIOR_STATUE_HEAD.get().defaultBlockState()
							.setValue(FACING, state.getValue(FACING)).setValue(POWERED, true), 3);

					serverLevel.destroyBlock(pos.below(2).relative(state.getValue(FACING)), true);
					serverLevel.setBlock(pos.below(2).relative(state.getValue(FACING)),
							Blocks.GRASS_BLOCK.defaultBlockState(), 3);
					serverLevel.destroyBlock(pos.below().relative(state.getValue(FACING)), true);
					serverLevel.setBlock(pos.below().relative(state.getValue(FACING)),
							BlockRegistry.AZUL_STAINED_ORCHID.get().defaultBlockState(), 3);

					PacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(() -> new TargetPoint(pos.getX(),
									pos.getY(), pos.getZ(), 12, serverLevel.dimension())),
							new WarriorStatueTorsoPacketHandler(pos, 1));

					for (int i = 0; i < 25; i++) {
						serverLevel.sendParticles(ParticleTypes.DRIPPING_WATER,
								pos.getX() + 0.5d + GeneralUtilities.getRandomNumber(-1.0d, 1.0d),
								pos.getY() + GeneralUtilities.getRandomNumber(-2.0d, 1.5d),
								pos.getZ() + 0.5d + GeneralUtilities.getRandomNumber(-1.0d, 1.0d),
								1, GeneralUtilities.getRandomNumber(-0.03d, 0.03d),
								GeneralUtilities.getRandomNumber(-0.1d, -0.08d),
								GeneralUtilities.getRandomNumber(-0.03d, 0.03d), 1.0f);

					}

					CriterionTriggerRegistry.WARRIOR_STATUE_ACTIVATED_TRIGGER.get().trigger((ServerPlayer) player);

					return InteractionResult.CONSUME;
				} else {
					PacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(() -> new TargetPoint(pos.getX(),
									pos.getY(), pos.getZ(), 12, level.dimension())),
							new WarriorStatueTorsoPacketHandler(pos, 2));
				}
			}

			return InteractionResult.PASS;
		}

		return InteractionResult.PASS;
	}

	public record WarriorStatueTorsoPacketHandler(BlockPos blockPos, int soundType) {

		public static void encode(WarriorStatueTorsoPacketHandler msg, FriendlyByteBuf packetBuffer) {
			packetBuffer.writeBlockPos(msg.blockPos).writeInt(msg.soundType);
		}

		public static WarriorStatueTorsoPacketHandler decode(FriendlyByteBuf packetBuffer) {
			return new WarriorStatueTorsoPacketHandler(packetBuffer.readBlockPos(), packetBuffer.readInt());
		}

		public static void handle(WarriorStatueTorsoPacketHandler msg, Context context) {
			context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> handleOnClient(msg)));
			context.setPacketHandled(true);
		}

		private static void handleOnClient(WarriorStatueTorsoPacketHandler msg) {
			Minecraft minecraft = Minecraft.getInstance();
			if (minecraft.level != null) {
				if (msg.soundType == 1) {
					minecraft.level.playLocalSound(msg.blockPos, SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.BLOCKS,
							0.6f, 1.0f, false);
				} else if (msg.soundType == 2) {
					minecraft.level.playLocalSound(msg.blockPos, SoundEvents.ENDERMITE_AMBIENT, SoundSource.BLOCKS,
							0.6f, GeneralUtilities.getRandomNumber(0.4f, 0.8f), false);
				}
			}
		}
	}
}