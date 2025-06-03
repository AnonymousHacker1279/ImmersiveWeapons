package tech.anonymoushacker1279.immersiveweapons.potion;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import tech.anonymoushacker1279.immersiveweapons.init.ParticleTypesRegistry;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

public class BleedingEffect extends MobEffect {

	private int cooldownTicks = 0;

	public BleedingEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity entity, int amplifier) {
		RandomSource random = entity.getRandom();
		if (cooldownTicks <= 0) {
			cooldownTicks = 60 - (amplifier * 10);

			float amount = 1.0f + (amplifier * 0.25f);

			if (entity.invulnerableTime > cooldownTicks) {
				entity.invulnerableTime = cooldownTicks;
			}

			entity.hurtServer(serverLevel, IWDamageSources.bleeding(serverLevel.registryAccess()), amount);
		} else {
			cooldownTicks--;
		}

		serverLevel.sendParticles(
				ParticleTypesRegistry.BLOOD_PARTICLE.get(),
				entity.position().x,
				entity.position().y + (random.nextFloat() * entity.getEyeHeight()),
				entity.position().z,
				1,
				entity.getBbWidth() * 0.5f,
				entity.getBbHeight() * 0.5f,
				entity.getBbWidth() * 0.5f,
				0.0d);

		return true;
	}

	@Override
	public boolean isBeneficial() {
		return false;
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}
}