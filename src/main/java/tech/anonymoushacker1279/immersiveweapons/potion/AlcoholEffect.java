package tech.anonymoushacker1279.immersiveweapons.potion;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class AlcoholEffect extends MobEffect {

	private int duration;

	public AlcoholEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
		entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, duration, amplifier, false, false));
		entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, duration, amplifier, false, false));
		entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, duration, amplifier, false, false));

		return super.applyEffectTick(level, entity, amplifier);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		this.duration = duration;
		return true;
	}
}