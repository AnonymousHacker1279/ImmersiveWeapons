package tech.anonymoushacker1279.immersiveweapons.mixin;

import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
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

	@Inject(method = "getDamageAfterAbsorb", at = @At("RETURN"), cancellable = true, require = 0)
	private static void getDamageAfterAbsorb(float damage, DamageSource source, float armor, float armorToughness, CallbackInfoReturnable<Float> ci) {
		float toughnessModifier = 2.0F + armorToughness / 4.0F;
		float armorProtection = (float) Mth.clamp(armor - damage / toughnessModifier, armor * 0.2F, CommonConfig.maxArmorProtection) / 25f;
		float armorBreachModifier = EnchantmentHelper.calculateArmorBreach(source.getEntity(), armorProtection);
		float damageModifier = 1.0F - armorBreachModifier;
		ci.setReturnValue(damage * damageModifier);
	}
}