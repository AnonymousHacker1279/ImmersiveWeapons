package tech.anonymoushacker1279.immersiveweapons.potion;

import net.minecraft.world.effect.*;
import net.minecraft.world.entity.LivingEntity;

public class AlcoholEffect extends MobEffect {

	private int duration;

	public AlcoholEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
		livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, duration, amplifier, false, false));
		livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, duration, amplifier, false, false));
		livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, duration, amplifier, false, false));

		return true;
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		this.duration = duration;
		return true;
	}
}