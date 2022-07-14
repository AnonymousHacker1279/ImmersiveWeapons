package tech.anonymoushacker1279.immersiveweapons.item.tool;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public interface HitEffectUtils {

	default void addTeslaEffects(LivingEntity livingEntity) {
		livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 140, 1, false, false));
		livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 140, 1, false, false));
		livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 140, 1, false, false));
	}
}