package tech.anonymoushacker1279.immersiveweapons.potion;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import tech.anonymoushacker1279.immersiveweapons.init.ParticleTypesRegistry;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

public class BleedingEffect extends MobEffect {

	private int cooldownTicks = 0;

	/**
	 * Constructor for BleedingEffect.
	 *
	 * @param effectCategory the <code>MobEffectCategory</code> instance
	 * @param color          the effect color
	 */
	public BleedingEffect(MobEffectCategory effectCategory, int color) {
		super(effectCategory, color);
	}

	/**
	 * Runs once each tick while the effect is active.
	 *
	 * @param livingEntity the <code>LivingEntity</code> with the effect
	 * @param amplifier    the effect amplifier
	 */
	@Override
	public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
		if (!livingEntity.level().isClientSide) {
			if (cooldownTicks <= 0) {
				cooldownTicks = 60 - (amplifier * 10);

				float amount = 1.0f + (amplifier * 0.25f);

				if (livingEntity.invulnerableTime > cooldownTicks) {
					livingEntity.invulnerableTime = cooldownTicks;
				}

				livingEntity.hurt(IWDamageSources.BLEEDING, amount);
			} else {
				cooldownTicks--;
			}

			((ServerLevel) livingEntity.level()).sendParticles(
					ParticleTypesRegistry.BLOOD_PARTICLE.get(),
					livingEntity.position().x, livingEntity.position().y + GeneralUtilities.getRandomNumber(0.3d, livingEntity.getEyeHeight()),
					livingEntity.position().z, 1, GeneralUtilities.getRandomNumber(-0.03d, 0.03d),
					GeneralUtilities.getRandomNumber(-0.1d, -0.08d), GeneralUtilities.getRandomNumber(-0.03d, 0.03d), 0.0d
			);
		}
	}

	@Override
	public boolean isBeneficial() {
		return false;
	}

	/**
	 * Check if the duration effect is ticking.
	 *
	 * @param duration  the duration
	 * @param amplifier the effect amplifier
	 * @return boolean
	 */
	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}