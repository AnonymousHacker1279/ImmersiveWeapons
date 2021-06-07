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
	private final Option.IntOption range = new Option.IntOption(this::getBlockPos, "range", 20, 0, 30, 1, true);
	private final Option.IntOption delay = new Option.IntOption(this::getBlockPos, "delay", 2, 1, 30, 1, true);
	private int cooldown = 0;
	private int currentlyPlayingSound = 1;

	public PanicAlarmTileEntity() {
		super(DeferredRegistryHandler.PANIC_ALARM_TILE_ENTITY.get());
	}

	@Override
	public void tick() {
		if (!level.isClientSide) {
			if (cooldown > 0) {
				cooldown--;
			}

			if (isPowered && cooldown == 0) {
				PanicAlarmTileEntity tileEntity = (PanicAlarmTileEntity) level.getBlockEntity(worldPosition);

				boolean flag = Minecraft.getInstance().getSoundManager().isActive(new AlarmTickableSounds.AlarmTickableSound1((tileEntity)));
				boolean flag2 = Minecraft.getInstance().getSoundManager().isActive(new AlarmTickableSounds.AlarmTickableSound2((tileEntity)));
				boolean flag3 = Minecraft.getInstance().getSoundManager().isActive(new AlarmTickableSounds.AlarmTickableSound3((tileEntity)));

				for (ServerPlayerEntity player : ((ServerWorld) level).getPlayers(p -> p.blockPosition().distSqr(worldPosition) <= Math.pow(range.get(), 2))) {
					if (currentlyPlayingSound == 1 && !flag) {
						Minecraft.getInstance().getSoundManager().play(new AlarmTickableSounds.AlarmTickableSound1(tileEntity));
					} else if (currentlyPlayingSound == 2 && !flag2) {
						Minecraft.getInstance().getSoundManager().play(new AlarmTickableSounds.AlarmTickableSound2(tileEntity));
					} else if (currentlyPlayingSound == 3 && !flag3) {
						Minecraft.getInstance().getSoundManager().play(new AlarmTickableSounds.AlarmTickableSound3(tileEntity));
					}
				}

				if (currentlyPlayingSound == 1) {
					tileEntity.setCooldown(delay.get() * 27);
				} else if (currentlyPlayingSound == 2) {
					tileEntity.setCooldown(delay.get() * 120);
				} else if (currentlyPlayingSound == 3) {
					tileEntity.setCooldown(delay.get() * 120);
				}

				level.setBlock(worldPosition, level.getBlockState(worldPosition).setValue(PanicAlarmBlock.HORIZONTAL_FACING, level.getBlockState(worldPosition).getValue(PanicAlarmBlock.HORIZONTAL_FACING)), 2);
				level.setBlockEntity(worldPosition, tileEntity);
			} else if (!isPowered) {
				cooldown = 0;
				PanicAlarmTileEntity tileEntity = (PanicAlarmTileEntity) level.getBlockEntity(worldPosition);
				assert tileEntity != null;
				if (Minecraft.getInstance().getSoundManager().isActive(new AlarmTickableSounds.AlarmTickableSound1((tileEntity)))) {
					Minecraft.getInstance().getSoundManager().stop(new AlarmTickableSounds.AlarmTickableSound1((tileEntity)));
				}
				if (Minecraft.getInstance().getSoundManager().isActive(new AlarmTickableSounds.AlarmTickableSound2((tileEntity)))) {
					Minecraft.getInstance().getSoundManager().stop(new AlarmTickableSounds.AlarmTickableSound2((tileEntity)));
				}
				if (Minecraft.getInstance().getSoundManager().isActive(new AlarmTickableSounds.AlarmTickableSound3((tileEntity)))) {
					Minecraft.getInstance().getSoundManager().stop(new AlarmTickableSounds.AlarmTickableSound3((tileEntity)));
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
			activator.sendMessage(new TranslationTextComponent("immersiveweapons.block.alarm.alarm2").withStyle(TextFormatting.YELLOW), Util.NIL_UUID);
			return true;
		} else if (currentlyPlayingSound == 2) {
			currentlyPlayingSound++;
			activator.sendMessage(new TranslationTextComponent("immersiveweapons.block.alarm.alarm3").withStyle(TextFormatting.YELLOW), Util.NIL_UUID);
			return true;
		} else if (currentlyPlayingSound == 3) {
			// Reset back to zero - we're already at the last one
			currentlyPlayingSound = 1;
			activator.sendMessage(new TranslationTextComponent("immersiveweapons.block.alarm.alarm1").withStyle(TextFormatting.YELLOW), Util.NIL_UUID);
			return true;
		} else {
			currentlyPlayingSound = 1;
			activator.sendMessage(new TranslationTextComponent("immersiveweapons.block.alarm.alarm1").withStyle(TextFormatting.YELLOW), Util.NIL_UUID);
			return true;
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

}