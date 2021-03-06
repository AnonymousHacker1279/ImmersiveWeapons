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
	public void applyEffectTick(@NotNull LivingEntity entityLivingBaseIn, int amplifier) {
		if (duration > 900) {
			entityLivingBaseIn.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, duration - 900, amplifier, false, false));
			entityLivingBaseIn.addEffect(new EffectInstance(Effects.HEALTH_BOOST, duration - 900, amplifier, false, false));
			entityLivingBaseIn.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, duration - 900, amplifier, false, false));
		}
		if (duration <= 900) {
			entityLivingBaseIn.addEffect(new EffectInstance(Effects.WEAKNESS, duration, amplifier, false, false));
			entityLivingBaseIn.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, duration, amplifier, false, false));
			entityLivingBaseIn.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, duration, amplifier, false, false));
		}
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		this.duration = duration;
		return true;
	}
}