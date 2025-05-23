package tech.anonymoushacker1279.immersiveweapons.potion;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.armor.ArmorUtils;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

public class HellfireEffect extends MobEffect {

	private int cooldownTicks = 0;

	public HellfireEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity entity, int amplifier) {
		if (entity.isInWater() || entity.hasEffect(MobEffects.FIRE_RESISTANCE) || ArmorUtils.isWearingMoltenArmor(entity)) {
			entity.removeEffect(EffectRegistry.HELLFIRE_EFFECT);

			return false;
		}

		RandomSource random = entity.getRandom();
		if (cooldownTicks <= 0) {
			cooldownTicks = (80 - (amplifier * 10)) - (random.nextInt(10) * amplifier);

			float amount = (1.0f + (amplifier * 0.2f)) + (random.nextFloat() * (amplifier * 0.1f));

			if (entity.invulnerableTime > cooldownTicks) {
				entity.invulnerableTime = cooldownTicks;
			}

			entity.hurt(IWDamageSources.hellfire(serverLevel.registryAccess()), amount);
			entity.igniteForSeconds(Mth.ceil((float) cooldownTicks / 20));
		} else {
			cooldownTicks--;
		}

		serverLevel.sendParticles(
				ParticleTypes.FLAME,
				entity.position().x,
				entity.position().y + (random.nextFloat() * entity.getEyeHeight()),
				entity.position().z,
				3,
				entity.getBbWidth() * 0.5f,
				entity.getBbHeight() * 0.5f,
				entity.getBbWidth() * 0.5f,
				0.1d);

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