package com.anonymoushacker1279.immersiveweapons.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class AlcoholEffect extends MobEffect {

	private int duration;

	/**
	 * Constructor for AlcoholEffect.
	 *
	 * @param typeIn        the <code>EffectType</code> instance
	 * @param liquidColorIn the liquid color
	 */
	public AlcoholEffect(MobEffectCategory typeIn, int liquidColorIn) {
		super(typeIn, liquidColorIn);
	}

	/**
	 * Runs once each tick while the effect is active.
	 *
	 * @param livingEntity the <code>LivingEntity</code> with the effect
	 * @param amplifier    the effect amplifier
	 */
	@Override
	public void applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
		livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, duration, amplifier, false, false));
		livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, duration, amplifier, false, false));
		livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, duration, amplifier, false, false));
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