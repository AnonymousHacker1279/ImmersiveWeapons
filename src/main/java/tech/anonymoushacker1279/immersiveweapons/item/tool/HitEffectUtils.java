package tech.anonymoushacker1279.immersiveweapons.item.tool;

import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantments;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.EnchantmentRegistry;

public interface HitEffectUtils {

	default void addMoltenEffects(LivingEntity target, LivingEntity source) {
		int fireAspectLevel = source.getMainHandItem().getEnchantmentLevel(Enchantments.FIRE_ASPECT);
		int flameLevel = source.getMainHandItem().getEnchantmentLevel(Enchantments.FLAME);
		int scorchShotLevel = source.getMainHandItem().getEnchantmentLevel(EnchantmentRegistry.SCORCH_SHOT.get());

		if (fireAspectLevel > 0) {
			target.addEffect(new MobEffectInstance(EffectRegistry.HELLFIRE_EFFECT, 200, Mth.clamp(fireAspectLevel + 1, 1, 5), false, false));
		} else if (flameLevel > 0) {
			target.addEffect(new MobEffectInstance(EffectRegistry.HELLFIRE_EFFECT, 200, Mth.clamp(flameLevel + 1, 1, 5), false, false));
		} else if (scorchShotLevel > 0) {
			target.addEffect(new MobEffectInstance(EffectRegistry.HELLFIRE_EFFECT, 200, Mth.clamp(scorchShotLevel + 1, 1, 5), false, false));
		} else {
			target.addEffect(new MobEffectInstance(EffectRegistry.HELLFIRE_EFFECT, 200, 1, false, false));
		}
	}

	default void addTeslaEffects(LivingEntity livingEntity) {
		livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 140, 1, false, false));
		livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 140, 1, false, false));
		livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 140, 1, false, false));
	}

	default void addVentusEffects(LivingEntity livingEntity) {
		livingEntity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 60, 2, false, false));
	}

	enum HitEffect {
		NONE,
		MOLTEN,
		TESLA,
		VENTUS
	}
}