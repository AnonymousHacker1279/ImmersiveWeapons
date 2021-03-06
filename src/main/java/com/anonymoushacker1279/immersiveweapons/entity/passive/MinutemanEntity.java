package com.anonymoushacker1279.immersiveweapons.entity.passive;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.entity.EntityType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class MinutemanEntity extends AbstractMinutemanEntity {

	public MinutemanEntity(EntityType<? extends MinutemanEntity> entityType, World world) {
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
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
		super.dropCustomDeathLoot(source, looting, recentlyHitIn);
		int lootingModifier = looting * 2;
		if (lootingModifier >= 85) {
			spawnAtLocation(DeferredRegistryHandler.BLUNDERBUSS.get());
		} else {
			int i = GeneralUtilities.getRandomNumber(1, 85 - lootingModifier);
			if (i == 1) {
				spawnAtLocation(DeferredRegistryHandler.BLUNDERBUSS.get());
			}
		}
	}
}