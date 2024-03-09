package tech.anonymoushacker1279.immersiveweapons.potion;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.EffectCure;
import net.neoforged.neoforge.common.EffectCures;
import tech.anonymoushacker1279.immersiveweapons.init.ParticleTypesRegistry;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

import java.util.Set;

public class BleedingEffect extends MobEffect {

	private int cooldownTicks = 0;

	public BleedingEffect(MobEffectCategory effectCategory, int color) {
		super(effectCategory, color);
	}

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
		if (livingEntity.level() instanceof ServerLevel serverLevel) {
			RandomSource random = livingEntity.getRandom();
			if (cooldownTicks <= 0) {
				cooldownTicks = 60 - (amplifier * 10);

				float amount = 1.0f + (amplifier * 0.25f);

				if (livingEntity.invulnerableTime > cooldownTicks) {
					livingEntity.invulnerableTime = cooldownTicks;
				}

				livingEntity.hurt(IWDamageSources.BLEEDING, amount);
			} else {
				cooldownTicks--;
			}

			serverLevel.sendParticles(
					ParticleTypesRegistry.BLOOD_PARTICLE.get(),
					livingEntity.position().x,
					livingEntity.position().y + (random.nextFloat() * livingEntity.getEyeHeight()),
					livingEntity.position().z,
					1,
					livingEntity.getBbWidth() * 0.5f,
					livingEntity.getBbHeight() * 0.5f,
					livingEntity.getBbWidth() * 0.5f,
					0.0d);
		}
	}

	@Override
	public boolean isBeneficial() {
		return false;
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}

	@Override
	public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {
		cures.add(EffectCures.PROTECTED_BY_TOTEM);
	}
}