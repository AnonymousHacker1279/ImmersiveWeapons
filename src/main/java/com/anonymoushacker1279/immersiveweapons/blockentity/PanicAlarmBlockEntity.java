package com.anonymoushacker1279.immersiveweapons.blockentity;

import com.anonymoushacker1279.immersiveweapons.block.PanicAlarmBlock;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.PacketHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.minecraftforge.fmllegacy.network.NetworkEvent.Context;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class PanicAlarmBlockEntity extends BlockEntity implements EntityBlock {

	private boolean isPowered = false;
	private int cooldown = 0;
	private int currentlyPlayingSound = 1;

	/**
	 * Constructor for PanicAlarmBlockEntity.
	 */
	public PanicAlarmBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(DeferredRegistryHandler.PANIC_ALARM_BLOCK_ENTITY.get(), blockPos, blockState);
	}

	/**
	 * Create a block entity for the block.
	 * @param blockPos the <code>BlockPos</code> the block is at
	 * @param blockState the <code>BlockState</code> of the block
	 * @return BlockEntity
	 */
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new PanicAlarmBlockEntity(blockPos, blockState);
	}

	/**
	 * Runs once each tick. Handle scanning and spawning entities.
	 */
	// TODO: Evaluate usages
	public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, PanicAlarmBlockEntity panicAlarmBlockEntity) {
		if (level != null) {
			if (panicAlarmBlockEntity.cooldown > 0) {
				panicAlarmBlockEntity.cooldown--;
			}

			if (panicAlarmBlockEntity.isPowered && panicAlarmBlockEntity.cooldown == 0) {
				PanicAlarmBlockEntity blockEntity = (PanicAlarmBlockEntity) level.getBlockEntity(panicAlarmBlockEntity.worldPosition);

				if (blockEntity != null) {
					PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(blockEntity.worldPosition)), new PanicAlarmPacketHandler(panicAlarmBlockEntity.currentlyPlayingSound, panicAlarmBlockEntity.worldPosition));

					if (panicAlarmBlockEntity.currentlyPlayingSound == 1) {
						blockEntity.setCooldown(60);
					} else {
						blockEntity.setCooldown(240);
					}

					level.setBlock(panicAlarmBlockEntity.worldPosition, level.getBlockState(panicAlarmBlockEntity.worldPosition).setValue(PanicAlarmBlock.FACING, level.getBlockState(panicAlarmBlockEntity.worldPosition).getValue(PanicAlarmBlock.FACING)), 2);
					level.setBlockEntity(blockEntity);
				}
			}
		}
	}

	/**
	 * Get the power status.
	 * @return boolean
	 */
	public boolean isPowered() {
		return isPowered;
	}

	/**
	 * Set the power status.
	 * @param isPowered if the device should be powered
	 */
	public void setPowered(boolean isPowered) {
		this.isPowered = isPowered;
	}

	/**
	 * Set the cooldown.
	 * @param cooldown the cooldown time
	 */
	private void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}

	/**
	 * Handle changing of alarm sounds.
	 * @param activator the <code>PlayerEntity</code> interacting with the block entity
	 */
	public void changeAlarmSound(Player activator) {
		if (currentlyPlayingSound == 1) {
			currentlyPlayingSound++;
			activator.sendMessage(new TranslatableComponent("immersiveweapons.block.alarm.alarm2").withStyle(ChatFormatting.YELLOW), Util.NIL_UUID);
		} else if (currentlyPlayingSound == 2) {
			currentlyPlayingSound++;
			activator.sendMessage(new TranslatableComponent("immersiveweapons.block.alarm.alarm3").withStyle(ChatFormatting.YELLOW), Util.NIL_UUID);
		} else if (currentlyPlayingSound == 3) {
			// Reset back to zero - we're already at the last one
			currentlyPlayingSound = 1;
			activator.sendMessage(new TranslatableComponent("immersiveweapons.block.alarm.alarm1").withStyle(ChatFormatting.YELLOW), Util.NIL_UUID);
		} else {
			currentlyPlayingSound = 1;
			activator.sendMessage(new TranslatableComponent("immersiveweapons.block.alarm.alarm1").withStyle(ChatFormatting.YELLOW), Util.NIL_UUID);
		}
	}

	/**
	 * Save NBT data.
	 * @param nbt the <code>CompoundNBT</code> to save
	 */
	@Override
	public CompoundTag save(CompoundTag nbt) {
		super.save(nbt);
		nbt.putInt("cooldown", cooldown);
		nbt.putBoolean("isPowered", isPowered);
		nbt.putInt("currentlyPlayingSound", currentlyPlayingSound);
		return nbt;
	}

	/**
	 * Load NBT data.
	 * @param nbt the <code>CompoundNBT</code> to load
	 */
	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);

		cooldown = nbt.getInt("cooldown");
		isPowered = nbt.getBoolean("isPowered");
		currentlyPlayingSound = nbt.getInt("currentlyPlayingSound");
	}

	public record PanicAlarmPacketHandler(int currentlyPlayingSound, BlockPos blockPos) {

		/**
		 * Constructor for PanicAlarmPacketHandler.
		 */
		public PanicAlarmPacketHandler {
		}

		/**
		 * Encodes a packet
		 *
		 * @param msg          the <code>PanicAlarmPacketHandler</code> message being sent
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 */
		public static void encode(PanicAlarmPacketHandler msg, FriendlyByteBuf packetBuffer) {
			packetBuffer.writeInt(msg.currentlyPlayingSound);
			packetBuffer.writeBlockPos(msg.blockPos);
		}

		/**
		 * Decodes a packet
		 *
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 * @return PanicAlarmPacketHandler
		 */
		public static PanicAlarmPacketHandler decode(FriendlyByteBuf packetBuffer) {
			return new PanicAlarmPacketHandler(packetBuffer.readInt(), packetBuffer.readBlockPos());
		}

		/**
		 * Handles an incoming packet, by sending it to the client/server
		 *
		 * @param msg             the <code>PanicAlarmPacketHandler</code> message being sent
		 * @param contextSupplier the <code>Supplier</code> providing context
		 */
		public static void handle(PanicAlarmPacketHandler msg, Supplier<Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> handleOnClient(msg)));
			context.setPacketHandled(true);
		}

		/**
		 * Runs specifically on the client, when a packet is received
		 *
		 * @param msg the <code>PanicAlarmPacketHandler</code> message being sent
		 */
		@OnlyIn(Dist.CLIENT)
		private static void handleOnClient(PanicAlarmPacketHandler msg) {
			int currentlyPlayingSound = msg.currentlyPlayingSound;
			Minecraft minecraft = Minecraft.getInstance();
			if (minecraft.level != null) {
				PanicAlarmBlockEntity blockEntity = (PanicAlarmBlockEntity) minecraft.level.getBlockEntity(msg.blockPos);
				if (blockEntity != null) {
					if (currentlyPlayingSound == 1) {
						minecraft.level.playLocalSound(msg.blockPos, DeferredRegistryHandler.ALARM_1.get(), SoundSource.BLOCKS, 1.0f, 1.0f, false);
					} else if (currentlyPlayingSound == 2) {
						minecraft.level.playLocalSound(msg.blockPos, DeferredRegistryHandler.ALARM_2.get(), SoundSource.BLOCKS, 1.0f, 1.0f, false);
					} else if (currentlyPlayingSound == 3) {
						minecraft.level.playLocalSound(msg.blockPos, DeferredRegistryHandler.ALARM_3.get(), SoundSource.BLOCKS, 1.0f, 1.0f, false);
					}
				}
			}
		}
	}
}