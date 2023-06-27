package tech.anonymoushacker1279.immersiveweapons.entity.neutral;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;

public class MinutemanEntity extends AbstractMinutemanEntity {

	/**
	 * Constructor for MinutemanEntity.
	 *
	 * @param entityType the <code>EntityType</code> instance
	 * @param world      the <code>World</code> the entity is in
	 */
	public MinutemanEntity(EntityType<? extends MinutemanEntity> entityType, Level world) {
		super(entityType, world);
	}

	/**
	 * Get the ambient sound.
	 *
	 * @return SoundEvent
	 */
	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEventRegistry.DYING_SOLDIER_AMBIENT.get();
	}

	/**
	 * Get the ambient sound interval.
	 *
	 * @return int
	 */
	@Override
	public int getAmbientSoundInterval() {
		return getRandom().nextIntBetweenInclusive(240, 1600);
	}

	/**
	 * Get the hurt sound.
	 *
	 * @param damageSourceIn the <code>DamageSource</code> instance
	 * @return SoundEvent
	 */
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEventRegistry.DYING_SOLDIER_HURT.get();
	}

	/**
	 * Get the death sound.
	 *
	 * @return SoundEvent
	 */
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEventRegistry.DYING_SOLDIER_DEATH.get();
	}

	/**
	 * Get the step sound.
	 *
	 * @return SoundEvent
	 */
	@Override
	protected SoundEvent getStepSound() {
		return SoundEventRegistry.DYING_SOLDIER_STEP.get();
	}
}