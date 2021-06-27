package com.anonymoushacker1279.immersiveweapons.entity.monster;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.entity.EntityType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class WanderingWarriorEntity extends AbstractWanderingWarriorEntity {

	public WanderingWarriorEntity(EntityType<? extends WanderingWarriorEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return DeferredRegistryHandler.WANDERING_WARRIOR_AMBIENT.get();
	}

	@Override
	public int getAmbientSoundInterval() {
		return GeneralUtilities.getRandomNumber(240, 1600);
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DeferredRegistryHandler.WANDERING_WARRIOR_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DeferredRegistryHandler.WANDERING_WARRIOR_DEATH.get();
	}

	@Override
	protected SoundEvent getStepSound() {
		return DeferredRegistryHandler.WANDERING_WARRIOR_STEP.get();
	}
}