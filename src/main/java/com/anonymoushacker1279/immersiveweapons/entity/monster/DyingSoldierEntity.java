package com.anonymoushacker1279.immersiveweapons.entity.monster;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.entity.EntityType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class DyingSoldierEntity extends AbstractDyingSoldierEntity {

	public DyingSoldierEntity(EntityType<? extends DyingSoldierEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return DeferredRegistryHandler.DYING_SOLDIER_AMBIENT.get();
	}

	@Override
	public int getAmbientSoundInterval() {
		return GeneralUtilities.getRandomNumber(240, 1600);
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DeferredRegistryHandler.DYING_SOLDIER_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DeferredRegistryHandler.DYING_SOLDIER_DEATH.get();
	}

	@Override
	protected SoundEvent getStepSound() {
		return DeferredRegistryHandler.DYING_SOLDIER_STEP.get();
	}

	@Override
	protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
		super.dropCustomDeathLoot(source, looting, recentlyHitIn);
		int lootingModifier = looting * 2;
		if (lootingModifier >= 75) {
			this.spawnAtLocation(DeferredRegistryHandler.FLINTLOCK_PISTOL.get());
		} else {
			int i = GeneralUtilities.getRandomNumber(1, 75 - lootingModifier);
			if (i == 1) {
				this.spawnAtLocation(DeferredRegistryHandler.FLINTLOCK_PISTOL.get());
			}
		}
	}
}