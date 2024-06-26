package tech.anonymoushacker1279.immersiveweapons.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.CombatRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tech.anonymoushacker1279.immersiveweapons.config.CommonConfig;

/**
 * Replace the vanilla armor cap of 20 points with a configurable value.
 */
@Mixin(CombatRules.class)
public abstract class CombatRulesMixin {

	@Inject(method = "getDamageAfterAbsorb(FFF)F", at = @At("RETURN"), cancellable = true, require = 0)
	private static void getDamageAfterAbsorb(float damage, float totalArmor, float toughnessAttribute, CallbackInfoReturnable<Float> ci, @Local(ordinal = 3) float toughnessModifier) {
		float clampedArmorProtection = (float) Mth.clamp(totalArmor - damage / toughnessModifier, totalArmor * 0.2F, CommonConfig.maxArmorProtection);
		float newDamage = damage * (1.0F - clampedArmorProtection / 25.0F);
		ci.setReturnValue(newDamage);
	}
}