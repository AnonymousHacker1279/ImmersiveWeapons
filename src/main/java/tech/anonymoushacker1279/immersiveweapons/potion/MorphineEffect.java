package tech.anonymoushacker1279.immersiveweapons.potion;

import net.minecraft.world.effect.*;
import net.minecraft.world.entity.LivingEntity;

public class MorphineEffect extends MobEffect {

	private int duration;

	public MorphineEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
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

		return true;
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		this.duration = duration;
		return true;
	}
}