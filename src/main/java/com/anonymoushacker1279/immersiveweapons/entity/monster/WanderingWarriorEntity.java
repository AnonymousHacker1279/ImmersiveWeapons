package com.anonymoushacker1279.immersiveweapons.entity.monster;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class WanderingWarriorEntity extends AbstractWanderingWarriorEntity {

	/**
	 * Constructor for WanderingWarriorEntity.
	 * @param entityType the <code>EntityType</code> instance
	 * @param world the <code>World</code> the entity is in
	 */
	public WanderingWarriorEntity(EntityType<? extends WanderingWarriorEntity> entityType, Level world) {
		super(entityType, world);
	}

	/**
	 * Get the ambient sound.
	 * @return SoundEvent
	 */
	@Override
	protected SoundEvent getAmbientSound() {
		return DeferredRegistryHandler.WANDERING_WARRIOR_AMBIENT.get();
	}

	/**
	 * Get the ambient sound interval.
	 * @return int
	 */
	@Override
	public int getAmbientSoundInterval() {
		return GeneralUtilities.getRandomNumber(240, 1600);
	}

	/**
	 * Get the hurt sound.
	 * @param damageSourceIn the <code>DamageSource</code> instance
	 * @return SoundEvent
	 */
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DeferredRegistryHandler.WANDERING_WARRIOR_HURT.get();
	}

	/**
	 * Get the death sound.
	 * @return SoundEvent
	 */
	@Override
	protected SoundEvent getDeathSound() {
		return DeferredRegistryHandler.WANDERING_WARRIOR_DEATH.get();
	}

	/**
	 * Get the step sound.
	 * @return SoundEvent
	 */
	@Override
	protected SoundEvent getStepSound() {
		return DeferredRegistryHandler.WANDERING_WARRIOR_STEP.get();
	}
}