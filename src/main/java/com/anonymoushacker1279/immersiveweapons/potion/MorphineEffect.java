package com.anonymoushacker1279.immersiveweapons.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import org.jetbrains.annotations.NotNull;

public class MorphineEffect extends Effect {

	private int duration;

	/**
	 * Constructor for MorphineEffect.
	 * @param typeIn the <code>EffectType</code> instance
	 * @param liquidColorIn the liquid color
	 */
	public MorphineEffect(EffectType typeIn, int liquidColorIn) {
		super(typeIn, liquidColorIn);
	}

	/**
	 * Runs once each tick while the effect is active.
	 * @param livingEntity the <code>LivingEntity</code> with the effect
	 * @param amplifier the effect amplifier
	 */
	@Override
	public void applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
		if (duration > 900) {
			livingEntity.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, duration - 900, amplifier, false, false));
			livingEntity.addEffect(new EffectInstance(Effects.HEALTH_BOOST, duration - 900, amplifier, false, false));
			livingEntity.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, duration - 900, amplifier, false, false));
		}
		if (duration <= 900) {
			livingEntity.addEffect(new EffectInstance(Effects.WEAKNESS, duration, amplifier, false, false));
			livingEntity.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, duration, amplifier, false, false));
			livingEntity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, duration, amplifier, false, false));
		}
	}

	/**
	 * Check if the duration effect is ticking.
	 * @param duration the duration
	 * @param amplifier the effect amplifier
	 * @return boolean
	 */
	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		this.duration = duration;
		return true;
	}
}