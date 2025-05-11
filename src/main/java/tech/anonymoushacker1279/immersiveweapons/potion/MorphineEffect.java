package tech.anonymoushacker1279.immersiveweapons.potion;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class MorphineEffect extends MobEffect {

	private int duration;

	public MorphineEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity entity, int amplifier) {
		if (duration > 900) {
			entity.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, duration - 900, amplifier, false, false));
			entity.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, duration - 900, amplifier, false, false));
			entity.addEffect(new MobEffectInstance(MobEffects.SPEED, duration - 900, amplifier, false, false));
		}

		if (duration <= 900) {
			entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, duration, amplifier, false, false));
			entity.addEffect(new MobEffectInstance(MobEffects.MINING_FATIGUE, duration, amplifier, false, false));
			entity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, duration, amplifier, false, false));
		}

		return true;
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		this.duration = duration;
		return true;
	}
}