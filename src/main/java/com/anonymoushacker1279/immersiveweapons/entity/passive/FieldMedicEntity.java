package com.anonymoushacker1279.immersiveweapons.entity.passive;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class FieldMedicEntity extends AbstractFieldMedicEntity {

	/**
	 * Constructor for FieldMedicEntity.
	 * @param entityType the <code>EntityType</code> instance
	 * @param world the <code>World</code> the entity is in
	 */
	public FieldMedicEntity(EntityType<? extends FieldMedicEntity> entityType, Level world) {
		super(entityType, world);
	}

	/**
	 * Get the ambient sound.
	 * @return SoundEvent
	 */
	@Override
	protected SoundEvent getAmbientSound() {
		return DeferredRegistryHandler.FIELD_MEDIC_AMBIENT.get();
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
	protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
		return DeferredRegistryHandler.FIELD_MEDIC_HURT.get();
	}

	/**
	 * Get the death sound.
	 * @return SoundEvent
	 */
	@Override
	protected SoundEvent getDeathSound() {
		return DeferredRegistryHandler.FIELD_MEDIC_DEATH.get();
	}

	/**
	 * Get the step sound.
	 * @return SoundEvent
	 */
	@Override
	protected SoundEvent getStepSound() {
		return DeferredRegistryHandler.FIELD_MEDIC_STEP.get();
	}

	/**
	 * Set custom loot parameters.
	 * @param source the <code>DamageSource</code> instance
	 * @param looting the level of looting enchantment on an item
	 * @param recentlyHitIn if the entity was recently hit
	 */
	@Override
	protected void dropCustomDeathLoot(@NotNull DamageSource source, int looting, boolean recentlyHitIn) {
		super.dropCustomDeathLoot(source, looting, recentlyHitIn);
		int lootingModifier = looting * 2;
		if (lootingModifier >= 85) {
			spawnAtLocation(DeferredRegistryHandler.USED_SYRINGE.get());
		} else {
			int i = GeneralUtilities.getRandomNumber(1, 85 - lootingModifier);
			if (i == 1) {
				spawnAtLocation(DeferredRegistryHandler.USED_SYRINGE.get());
			}
		}
	}
}