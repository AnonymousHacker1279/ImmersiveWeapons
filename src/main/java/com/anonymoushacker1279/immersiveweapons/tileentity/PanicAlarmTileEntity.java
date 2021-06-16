package com.anonymoushacker1279.immersiveweapons.tileentity;

import com.anonymoushacker1279.immersiveweapons.block.PanicAlarmBlock;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.Option;
import com.anonymoushacker1279.immersiveweapons.util.PacketHandler;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

public class PanicAlarmTileEntity extends TileEntity implements ITickableTileEntity {

	private final Option.IntOption delay = new Option.IntOption("delay", 2, 1, 30);
	private boolean isPowered = false;
	private int cooldown = 0;
	private int currentlyPlayingSound = 1;

	public PanicAlarmTileEntity() {
		super(DeferredRegistryHandler.PANIC_ALARM_TILE_ENTITY.get());
	}

	@Override
	public void tick() {
		if (level != null) {
			if (cooldown > 0) {
				cooldown--;
			}

			if (isPowered && cooldown == 0) {
				PanicAlarmTileEntity tileEntity = (PanicAlarmTileEntity) level.getBlockEntity(worldPosition);

				if (tileEntity != null) {
					PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(tileEntity.worldPosition)), new PanicAlarmPacketHandler(currentlyPlayingSound, worldPosition));

					if (currentlyPlayingSound == 1) {
						tileEntity.setCooldown(delay.get() * 27);
					} else if (currentlyPlayingSound == 2) {
						tileEntity.setCooldown(delay.get() * 120);
					} else if (currentlyPlayingSound == 3) {
						tileEntity.setCooldown(delay.get() * 120);
					}

					level.setBlock(worldPosition, level.getBlockState(worldPosition).setValue(PanicAlarmBlock.HORIZONTAL_FACING, level.getBlockState(worldPosition).getValue(PanicAlarmBlock.HORIZONTAL_FACING)), 2);
					level.setBlockEntity(worldPosition, tileEntity);
				}
			}
		}
	}

	public boolean isPowered() {
		return isPowered;
	}

	public void setPowered(boolean isPowered) {
		this.isPowered = isPowered;
	}

	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}

	public void changeAlarmSound(PlayerEntity activator) {
		if (currentlyPlayingSound == 1) {
			currentlyPlayingSound++;
			activator.sendMessage(new TranslationTextComponent("immersiveweapons.block.alarm.alarm2").withStyle(TextFormatting.YELLOW), Util.NIL_UUID);
		} else if (currentlyPlayingSound == 2) {
			currentlyPlayingSound++;
			activator.sendMessage(new TranslationTextComponent("immersiveweapons.block.alarm.alarm3").withStyle(TextFormatting.YELLOW), Util.NIL_UUID);
		} else if (currentlyPlayingSound == 3) {
			// Reset back to zero - we're already at the last one
			currentlyPlayingSound = 1;
			activator.sendMessage(new TranslationTextComponent("immersiveweapons.block.alarm.alarm1").withStyle(TextFormatting.YELLOW), Util.NIL_UUID);
		} else {
			currentlyPlayingSound = 1;
			activator.sendMessage(new TranslationTextComponent("immersiveweapons.block.alarm.alarm1").withStyle(TextFormatting.YELLOW), Util.NIL_UUID);
		}
	}

	@Override
	public CompoundNBT save(CompoundNBT tag) {
		super.save(tag);
		tag.putInt("cooldown", cooldown);
		tag.putBoolean("isPowered", isPowered);
		tag.putInt("currentlyPlayingSound", currentlyPlayingSound);
		return tag;
	}

	@Override
	public void load(BlockState state, CompoundNBT tag) {
		super.load(state, tag);

		cooldown = tag.getInt("cooldown");
		isPowered = tag.getBoolean("isPowered");
		currentlyPlayingSound = tag.getInt("currentlyPlayingSound");
	}

	public static class PanicAlarmPacketHandler {

		private final int currentlyPlayingSound;
		private final BlockPos blockPos;

		public PanicAlarmPacketHandler(final int currentlyPlayingSound, final BlockPos blockPos) {
			this.currentlyPlayingSound = currentlyPlayingSound;
			this.blockPos = blockPos;
		}

		public static void encode(final PanicAlarmPacketHandler msg, final PacketBuffer packetBuffer) {
			packetBuffer.writeInt(msg.currentlyPlayingSound);
			packetBuffer.writeBlockPos(msg.blockPos);
		}

		public static PanicAlarmPacketHandler decode(final PacketBuffer packetBuffer) {
			return new PanicAlarmPacketHandler(packetBuffer.readInt(), packetBuffer.readBlockPos());
		}

		public static void handle(final PanicAlarmPacketHandler msg, final Supplier<Context> contextSupplier) {
			final NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> handleOnClient(msg)));
			context.setPacketHandled(true);
		}

		@OnlyIn(Dist.CLIENT)
		private static void handleOnClient(final PanicAlarmPacketHandler msg) {
			final int currentlyPlayingSound = msg.currentlyPlayingSound;
			Minecraft minecraft = Minecraft.getInstance();
			if (minecraft.level != null) {
				PanicAlarmTileEntity tileEntity = (PanicAlarmTileEntity) minecraft.level.getBlockEntity(msg.blockPos);
				if (tileEntity != null) {
					if (currentlyPlayingSound == 1) {
						minecraft.level.playLocalSound(msg.blockPos, DeferredRegistryHandler.ALARM_1.get(), SoundCategory.BLOCKS, 1.0f, 1.0f, false);
					} else if (currentlyPlayingSound == 2) {
						minecraft.level.playLocalSound(msg.blockPos, DeferredRegistryHandler.ALARM_2.get(), SoundCategory.BLOCKS, 1.0f, 1.0f, false);
					} else if (currentlyPlayingSound == 3) {
						minecraft.level.playLocalSound(msg.blockPos, DeferredRegistryHandler.ALARM_3.get(), SoundCategory.BLOCKS, 1.0f, 1.0f, false);
					}
				}
			}
		}
	}

}