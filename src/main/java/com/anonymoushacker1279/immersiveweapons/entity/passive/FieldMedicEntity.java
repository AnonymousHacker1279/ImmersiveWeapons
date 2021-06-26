package com.anonymoushacker1279.immersiveweapons.entity.passive;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.entity.EntityType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class FieldMedicEntity extends AbstractFieldMedicEntity {

	public FieldMedicEntity(EntityType<? extends FieldMedicEntity> entity, World world) {
		super(entity, world);
	}

	//TODO: Need custom sounds
	@Override
	protected SoundEvent getAmbientSound() {
		return DeferredRegistryHandler.FIELD_MEDIC_AMBIENT.get();
	}

	@Override
	public int getAmbientSoundInterval() {
		return GeneralUtilities.getRandomNumber(240, 1600);
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DeferredRegistryHandler.FIELD_MEDIC_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DeferredRegistryHandler.FIELD_MEDIC_DEATH.get();
	}

	@Override
	protected SoundEvent getStepSound() {
		return DeferredRegistryHandler.FIELD_MEDIC_STEP.get();
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
			this.spawnAtLocation(DeferredRegistryHandler.USED_SYRINGE.get());
		} else {
			int i = GeneralUtilities.getRandomNumber(1, 85 - lootingModifier);
			if (i == 1) {
				this.spawnAtLocation(DeferredRegistryHandler.USED_SYRINGE.get());
			}
		}
	}
}