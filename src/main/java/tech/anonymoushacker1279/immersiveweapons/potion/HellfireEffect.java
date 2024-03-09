package tech.anonymoushacker1279.immersiveweapons.potion;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.EffectCure;
import net.neoforged.neoforge.common.EffectCures;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.armor.ArmorUtils;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

import java.util.Set;

public class HellfireEffect extends MobEffect {

	private int cooldownTicks = 0;

	public HellfireEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
		if (livingEntity.isInWater() || livingEntity.hasEffect(MobEffects.FIRE_RESISTANCE) || ArmorUtils.isWearingMoltenArmor(livingEntity)) {
			livingEntity.removeEffect(EffectRegistry.HELLFIRE_EFFECT.get());

			return;
		}

		if (livingEntity.level() instanceof ServerLevel serverLevel) {
			RandomSource random = livingEntity.getRandom();
			if (cooldownTicks <= 0) {
				cooldownTicks = (80 - (amplifier * 10)) - (random.nextInt(10) * amplifier);

				float amount = (1.0f + (amplifier * 0.2f)) + (random.nextFloat() * (amplifier * 0.1f));

				if (livingEntity.invulnerableTime > cooldownTicks) {
					livingEntity.invulnerableTime = cooldownTicks;
				}

				livingEntity.hurt(IWDamageSources.HELLFIRE, amount);
				livingEntity.setSecondsOnFire(Mth.ceil((float) cooldownTicks / 20));
			} else {
				cooldownTicks--;
			}

			serverLevel.sendParticles(
					ParticleTypes.FLAME,
					livingEntity.position().x,
					livingEntity.position().y + (random.nextFloat() * livingEntity.getEyeHeight()),
					livingEntity.position().z,
					3,
					livingEntity.getBbWidth() * 0.5f,
					livingEntity.getBbHeight() * 0.5f,
					livingEntity.getBbWidth() * 0.5f,
					0.1d);
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