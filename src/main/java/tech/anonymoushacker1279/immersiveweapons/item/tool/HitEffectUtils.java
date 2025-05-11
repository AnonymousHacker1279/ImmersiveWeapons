package tech.anonymoushacker1279.immersiveweapons.item.tool;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import tech.anonymoushacker1279.immersiveweapons.data.IWEnchantments;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;

public interface HitEffectUtils {

	default void addMoltenEffects(LivingEntity target, LivingEntity source) {
		HolderGetter<Enchantment> enchantmentGetter = source.registryAccess().lookup(Registries.ENCHANTMENT).orElseThrow();

		int fireAspectLevel = source.getMainHandItem().getEnchantmentLevel(enchantmentGetter.get(Enchantments.FIRE_ASPECT).orElseThrow());
		int flameLevel = source.getMainHandItem().getEnchantmentLevel(enchantmentGetter.get(Enchantments.FLAME).orElseThrow());
		int scorchShotLevel = source.getMainHandItem().getEnchantmentLevel(enchantmentGetter.get(IWEnchantments.SCORCH_SHOT).orElseThrow());

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
		livingEntity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 140, 1, false, false));
		livingEntity.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 140, 1, false, false));
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