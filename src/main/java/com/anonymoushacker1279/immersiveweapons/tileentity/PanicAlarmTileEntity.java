package com.anonymoushacker1279.immersiveweapons.tileentity;

import com.anonymoushacker1279.immersiveweapons.block.PanicAlarmBlock;
import com.anonymoushacker1279.immersiveweapons.client.sound.AlarmTickableSounds;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.Option;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class PanicAlarmTileEntity extends TileEntity implements ITickableTileEntity {

	private boolean isPowered = false;
	private Option.IntOption range = new Option.IntOption(this::getPos, "range", 20, 0, 30, 1, true);
	private Option.IntOption delay = new Option.IntOption(this::getPos, "delay", 2, 1, 30, 1, true);
	private int cooldown = 0;
	private int currentlyPlayingSound = 1;

	public PanicAlarmTileEntity() {
		super(DeferredRegistryHandler.PANIC_ALARM_TILE_ENTITY.get());
	}

	@Override
	public void tick() {
		if (!world.isRemote) {
			if (cooldown > 0) {
				cooldown--;
			}

			if (isPowered && cooldown == 0) {
				PanicAlarmTileEntity tileEntity = (PanicAlarmTileEntity) world.getTileEntity(pos);

				boolean flag = Minecraft.getInstance().getSoundHandler().isPlaying(new AlarmTickableSounds.AlarmTickableSound1((tileEntity)));
				boolean flag2 = Minecraft.getInstance().getSoundHandler().isPlaying(new AlarmTickableSounds.AlarmTickableSound2((tileEntity)));
				boolean flag3 = Minecraft.getInstance().getSoundHandler().isPlaying(new AlarmTickableSounds.AlarmTickableSound3((tileEntity)));

				for (ServerPlayerEntity player : ((ServerWorld) world).getPlayers(p -> p.getPosition().distanceSq(pos) <= Math.pow(range.get(), 2))) {
					if (currentlyPlayingSound == 1 && !flag) {
						Minecraft.getInstance().getSoundHandler().play(new AlarmTickableSounds.AlarmTickableSound1(tileEntity));
					} else if (currentlyPlayingSound == 2 && !flag2) {
						Minecraft.getInstance().getSoundHandler().play(new AlarmTickableSounds.AlarmTickableSound2(tileEntity));
					} else if (currentlyPlayingSound == 3 && !flag3) {
						Minecraft.getInstance().getSoundHandler().play(new AlarmTickableSounds.AlarmTickableSound3(tileEntity));
					}
				}

				if (currentlyPlayingSound == 1) {
					tileEntity.setCooldown(delay.get() * 27);
				} else if (currentlyPlayingSound == 2) {
					tileEntity.setCooldown(delay.get() * 120);
				} else if (currentlyPlayingSound == 3) {
					tileEntity.setCooldown(delay.get() * 120);
				}

				world.setBlockState(pos, world.getBlockState(pos).with(PanicAlarmBlock.HORIZONTAL_FACING, world.getBlockState(pos).get(PanicAlarmBlock.HORIZONTAL_FACING)), 2);
				world.setTileEntity(pos, tileEntity);
			} else if (!isPowered) {
				cooldown = 0;
				PanicAlarmTileEntity tileEntity = (PanicAlarmTileEntity) world.getTileEntity(pos);
				assert tileEntity != null;
				if (Minecraft.getInstance().getSoundHandler().isPlaying(new AlarmTickableSounds.AlarmTickableSound1((tileEntity)))) {
					Minecraft.getInstance().getSoundHandler().stop(new AlarmTickableSounds.AlarmTickableSound1((tileEntity)));
				}
				if (Minecraft.getInstance().getSoundHandler().isPlaying(new AlarmTickableSounds.AlarmTickableSound2((tileEntity)))) {
					Minecraft.getInstance().getSoundHandler().stop(new AlarmTickableSounds.AlarmTickableSound2((tileEntity)));
				}
				if (Minecraft.getInstance().getSoundHandler().isPlaying(new AlarmTickableSounds.AlarmTickableSound3((tileEntity)))) {
					Minecraft.getInstance().getSoundHandler().stop(new AlarmTickableSounds.AlarmTickableSound3((tileEntity)));
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

	public boolean changeAlarmSound(PlayerEntity activator, World worldIn) {
		if (currentlyPlayingSound == 1) {
			currentlyPlayingSound++;
			activator.sendMessage(new TranslationTextComponent("immersiveweapons.block.alarm.alarm2").mergeStyle(TextFormatting.YELLOW), Util.DUMMY_UUID);
			return true;
		} else if (currentlyPlayingSound == 2) {
			currentlyPlayingSound++;
			activator.sendMessage(new TranslationTextComponent("immersiveweapons.block.alarm.alarm3").mergeStyle(TextFormatting.YELLOW), Util.DUMMY_UUID);
			return true;
		} else if (currentlyPlayingSound == 3) {
			// Reset back to zero - we're already at the last one
			currentlyPlayingSound = 1;
			activator.sendMessage(new TranslationTextComponent("immersiveweapons.block.alarm.alarm1").mergeStyle(TextFormatting.YELLOW), Util.DUMMY_UUID);
			return true;
		} else {
			currentlyPlayingSound = 1;
			activator.sendMessage(new TranslationTextComponent("immersiveweapons.block.alarm.alarm1").mergeStyle(TextFormatting.YELLOW), Util.DUMMY_UUID);
			return true;
		}
	}

	@Override
	public CompoundNBT write(CompoundNBT tag) {
		super.write(tag);
		tag.putInt("cooldown", cooldown);
		tag.putBoolean("isPowered", isPowered);
		tag.putInt("currentlyPlayingSound", currentlyPlayingSound);
		return tag;
	}

	@Override
	public void read(BlockState state, CompoundNBT tag) {
		super.read(state, tag);

		cooldown = tag.getInt("cooldown");
		isPowered = tag.getBoolean("isPowered");
		currentlyPlayingSound = tag.getInt("currentlyPlayingSound");
	}

}