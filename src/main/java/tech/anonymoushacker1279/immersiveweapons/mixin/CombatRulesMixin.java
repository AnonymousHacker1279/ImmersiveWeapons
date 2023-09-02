package tech.anonymoushacker1279.immersiveweapons.mixin;

import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.CombatRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import static org.spongepowered.asm.mixin.injection.callback.LocalCapture.CAPTURE_FAILSOFT;

/**
 * Replace the vanilla armor cap of 20 points with a configurable value.
 */
@Mixin(CombatRules.class)
public abstract class CombatRulesMixin {

	@Inject(method = "getDamageAfterAbsorb(FFF)F", at = @At("RETURN"), cancellable = true, locals = CAPTURE_FAILSOFT)
	private static void getDamageAfterAbsorb(float damage, float totalArmor, float toughnessAttribute, CallbackInfoReturnable<Float> ci, float f) {
		float clampedArmorProtection = (float) Mth.clamp(totalArmor - damage / f, totalArmor * 0.2F, ImmersiveWeapons.COMMON_CONFIG.maxArmorProtection().get());
		float newDamage = damage * (1.0F - clampedArmorProtection / 25.0F);
		ci.setReturnValue(newDamage);
	}
}