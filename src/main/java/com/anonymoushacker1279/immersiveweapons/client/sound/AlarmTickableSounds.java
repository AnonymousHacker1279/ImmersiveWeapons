package com.anonymoushacker1279.immersiveweapons.client.sound;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.tileentity.PanicAlarmTileEntity;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.util.SoundCategory;

import net.minecraft.client.audio.ISound.AttenuationType;

public class AlarmTickableSounds {

	public static class AlarmTickableSound1 extends TickableSound {

		private final PanicAlarmTileEntity panicAlarmTileEntity;
		private float distance = 20.0F;

		public AlarmTickableSound1(PanicAlarmTileEntity panicAlarmTileEntity) {
			super(DeferredRegistryHandler.ALARM_1.get(), SoundCategory.BLOCKS);
			this.panicAlarmTileEntity = panicAlarmTileEntity;
			this.attenuation = AttenuationType.LINEAR;
			this.looping = false;
			this.delay = 0;
			this.volume = 1.0F;
			this.x = (float) panicAlarmTileEntity.getBlockPos().getX();
			this.y = (float) panicAlarmTileEntity.getBlockPos().getY();
			this.z = (float) panicAlarmTileEntity.getBlockPos().getZ();
		}

		@Override
		public void tick() {
			if (!panicAlarmTileEntity.isPowered()) {
				this.stop();
			} else {
				this.x = (float) panicAlarmTileEntity.getBlockPos().getX();
				this.y = (float) panicAlarmTileEntity.getBlockPos().getY();
				this.z = (float) panicAlarmTileEntity.getBlockPos().getZ();
				this.distance = 20f;
				this.volume = 1.0f;
			}
		}
	}

	public static class AlarmTickableSound2 extends TickableSound {

		private final PanicAlarmTileEntity panicAlarmTileEntity;
		private float distance = 20.0F;

		public AlarmTickableSound2(PanicAlarmTileEntity panicAlarmTileEntity) {
			super(DeferredRegistryHandler.ALARM_2.get(), SoundCategory.BLOCKS);
			this.panicAlarmTileEntity = panicAlarmTileEntity;
			this.attenuation = AttenuationType.LINEAR;
			this.looping = false;
			this.delay = 0;
			this.volume = 1.0F;
			this.x = (float) panicAlarmTileEntity.getBlockPos().getX();
			this.y = (float) panicAlarmTileEntity.getBlockPos().getY();
			this.z = (float) panicAlarmTileEntity.getBlockPos().getZ();
		}

		@Override
		public void tick() {
			if (!panicAlarmTileEntity.isPowered()) {
				this.stop();
			} else {
				this.x = (float) panicAlarmTileEntity.getBlockPos().getX();
				this.y = (float) panicAlarmTileEntity.getBlockPos().getY();
				this.z = (float) panicAlarmTileEntity.getBlockPos().getZ();
				this.distance = 20f;
				this.volume = 1.0f;
			}
		}
	}

	public static class AlarmTickableSound3 extends TickableSound {

		private final PanicAlarmTileEntity panicAlarmTileEntity;
		private float distance = 20.0F;

		public AlarmTickableSound3(PanicAlarmTileEntity panicAlarmTileEntity) {
			super(DeferredRegistryHandler.ALARM_3.get(), SoundCategory.BLOCKS);
			this.panicAlarmTileEntity = panicAlarmTileEntity;
			this.attenuation = AttenuationType.LINEAR;
			this.looping = false;
			this.delay = 0;
			this.volume = 1.0F;
			this.x = (float) panicAlarmTileEntity.getBlockPos().getX();
			this.y = (float) panicAlarmTileEntity.getBlockPos().getY();
			this.z = (float) panicAlarmTileEntity.getBlockPos().getZ();
		}

		@Override
		public void tick() {
			if (!panicAlarmTileEntity.isPowered()) {
				this.stop();
			} else {
				this.x = (float) panicAlarmTileEntity.getBlockPos().getX();
				this.y = (float) panicAlarmTileEntity.getBlockPos().getY();
				this.z = (float) panicAlarmTileEntity.getBlockPos().getZ();
				this.distance = 20f;
				this.volume = 1.0f;
			}
		}
	}
}