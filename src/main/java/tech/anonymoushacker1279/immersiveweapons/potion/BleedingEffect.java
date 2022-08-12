package tech.anonymoushacker1279.immersiveweapons.potion;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class BleedingEffect extends MobEffect {

	public static final DamageSource damageSource = new DamageSource("immersiveweapons.bleeding").bypassArmor();
	private int cooldownTicks = 0;

	/**
	 * Constructor for BleedingEffect.
	 *
	 * @param effectCategory the <code>MobEffectCategory</code> instance
	 * @param liquidColorIn  the liquid color
	 */
	public BleedingEffect(MobEffectCategory effectCategory, int liquidColorIn) {
		super(effectCategory, liquidColorIn);
	}

	/**
	 * Runs once each tick while the effect is active.
	 *
	 * @param livingEntity the <code>LivingEntity</code> with the effect
	 * @param amplifier    the effect amplifier
	 */
	@Override
	public void applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
		if (!livingEntity.level.isClientSide) {
			if (cooldownTicks <= 0) {
				cooldownTicks = 59 - (amplifier >= 1 ? amplifier * 10 : 0);
				livingEntity.hurt(damageSource, 1.0f);
			} else {
				cooldownTicks--;
			}
		} else {
			livingEntity.level.addParticle(DeferredRegistryHandler.BLOOD_PARTICLE.get(),
					livingEntity.position().x, livingEntity.position().y + GeneralUtilities.getRandomNumber(0.3d, livingEntity.getEyeHeight()),
					livingEntity.position().z, GeneralUtilities.getRandomNumber(-0.03d, 0.03d),
					GeneralUtilities.getRandomNumber(-0.1d, -0.08d), GeneralUtilities.getRandomNumber(-0.03d, 0.03d));
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