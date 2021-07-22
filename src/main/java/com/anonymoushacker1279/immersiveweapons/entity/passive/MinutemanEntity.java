package com.anonymoushacker1279.immersiveweapons.entity.passive;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.entity.EntityType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class MinutemanEntity extends AbstractMinutemanEntity {

	/**
	 * Constructor for MinutemanEntity.
	 * @param entityType the <code>EntityType</code> instance
	 * @param world the <code>World</code> the entity is in
	 */
	public MinutemanEntity(EntityType<? extends MinutemanEntity> entityType, World world) {
		super(entityType, world);
	}

	/**
	 * Get the ambient sound.
	 * @return SoundEvent
	 */
	@Override
	protected SoundEvent getAmbientSound() {
		return DeferredRegistryHandler.DYING_SOLDIER_AMBIENT.get();
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
		return DeferredRegistryHandler.DYING_SOLDIER_HURT.get();
	}

	/**
	 * Get the death sound.
	 * @return SoundEvent
	 */
	@Override
	protected SoundEvent getDeathSound() {
		return DeferredRegistryHandler.DYING_SOLDIER_DEATH.get();
	}

	/**
	 * Get the step sound.
	 * @return SoundEvent
	 */
	@Override
	protected SoundEvent getStepSound() {
		return DeferredRegistryHandler.DYING_SOLDIER_STEP.get();
	}

	/**
	 * Set custom loot parameters.
	 * @param source the <code>DamageSource</code> instance
	 * @param looting the level of looting enchantment on an item
	 * @param recentlyHitIn if the entity was recently hit
	 */
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