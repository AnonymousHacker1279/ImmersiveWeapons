package tech.anonymoushacker1279.immersiveweapons.potion;

import net.minecraft.world.effect.*;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class MorphineEffect extends MobEffect {

	private int duration;

	/**
	 * Constructor for MorphineEffect.
	 *
	 * @param effectCategory the <code>MobEffectCategory</code> instance
	 * @param liquidColorIn  the liquid color
	 */
	public MorphineEffect(MobEffectCategory effectCategory, int liquidColorIn) {
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
		if (duration > 900) {
			livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, duration - 900, amplifier, false, false));
			livingEntity.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, duration - 900, amplifier, false, false));
			livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, duration - 900, amplifier, false, false));
		}
		if (duration <= 900) {
			livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, duration, amplifier, false, false));
			livingEntity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, duration, amplifier, false, false));
			livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, duration, amplifier, false, false));
		}
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
		this.duration = duration;
		return true;
	}
}