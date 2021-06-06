package com.anonymoushacker1279.immersiveweapons.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import org.jetbrains.annotations.NotNull;

public class MorphineEffect extends Effect {

	private int duration;

	public MorphineEffect(EffectType typeIn, int liquidColorIn) {
		super(typeIn, liquidColorIn);
	}

	@Override
	public void performEffect(@NotNull LivingEntity entityLivingBaseIn, int amplifier) {
		if (duration > 900) {
			entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.RESISTANCE, duration - 900, amplifier, false, false));
			entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.HEALTH_BOOST, duration - 900, amplifier, false, false));
			entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.SPEED, duration - 900, amplifier, false, false));
		}
		if (duration <= 900) {
			entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.WEAKNESS, duration, amplifier, false, false));
			entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.MINING_FATIGUE, duration, amplifier, false, false));
			entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.SLOWNESS, duration, amplifier, false, false));
		}
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		this.duration = duration;
		return true;
	}
}