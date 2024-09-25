package tech.anonymoushacker1279.immersiveweapons.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;
import tech.anonymoushacker1279.immersiveweapons.init.AttributeRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;
import tech.anonymoushacker1279.immersiveweapons.potion.BrokenArmorEffect;

/**
 * Replace the vanilla armor cap of 20 points with a configurable value. Additionally, it allows a custom armor breach attribute to be used.
 */
@Mixin(CombatRules.class)
public abstract class CombatRulesMixin {

	@Inject(method = "getDamageAfterAbsorb", at = @At("RETURN"), require = 0, cancellable = true)
	private static void getDamageAfterAbsorb(LivingEntity entity, float damage, DamageSource damageSource, float armorValue, float armorToughness, CallbackInfoReturnable<Float> ci) {
		float toughnessModifier = 2.0F + armorToughness / 4.0F;
		float clampedArmorProtection = (float) Mth.clamp(armorValue - damage / toughnessModifier, armorValue * 0.2F, IWConfigs.SERVER.maxArmorProtection.getAsDouble());
		final float[] damageModifier = {clampedArmorProtection / 25.0F};

		ItemStack weapon = damageSource.getWeaponItem();
		if (weapon != null && entity.level() instanceof ServerLevel serverlevel) {
			damageModifier[0] = Mth.clamp(EnchantmentHelper.modifyArmorEffectiveness(serverlevel, weapon, entity, damageSource, damageModifier[0]), 0.0F, 1.0F);

			// Handle armor breach attribute
			weapon.getAttributeModifiers().forEach(EquipmentSlot.MAINHAND, (attribute, attributeModifier) -> {
				if (attribute == AttributeRegistry.ARMOR_BREACH) {
					damageModifier[0] -= (float) attributeModifier.amount();
				}
			});

			// Handle Broken Armor effects
			MobEffectInstance brokenArmorEffect = entity.getEffect(EffectRegistry.BROKEN_ARMOR_EFFECT);
			if (brokenArmorEffect != null) {
				int level = brokenArmorEffect.getAmplifier();
				damageModifier[0] -= ((BrokenArmorEffect) brokenArmorEffect.getEffect().value()).calculateArmorBreach(level);
			}
		}

		// Ensure the modifier does not go below zero
		damageModifier[0] = Math.max(0.0F, damageModifier[0]);
		ci.setReturnValue(damage * (1.0F - damageModifier[0]));
	}

	/*
	float armor = getArmorValue();
		float armorToughness = (float) getAttributeValue(Attributes.ARMOR_TOUGHNESS);

		float toughnessModifier = 2.0F + armorToughness / 4.0F;
		float armorProtection = (float) Mth.clamp(armor - damageAmount / toughnessModifier, armor * 0.2F, CommonConfig.maxArmorProtection) / 25f;
		// final float[] armorBreachModifier = {EnchantmentHelper.calculateArmorBreach(source.getEntity(), armorProtection)};

		if (source.getEntity() instanceof LivingEntity attackingEntity) {
			ItemStack heldItem = attackingEntity.getMainHandItem();

			// Prevents being able to fire a projectile and switching to a weapon with armor breach to increase damage
			if (source.getDirectEntity() instanceof CustomArrowEntity customArrowEntity) {
				heldItem = customArrowEntity.firedWithStack;
			}

			if (heldItem != null) {
				heldItem.getAttributeModifiers().forEach(EquipmentSlot.MAINHAND, (attribute, attributeModifier) -> {
					if (attribute == AttributeRegistry.ARMOR_BREACH) {
						// armorBreachModifier[0] -= (float) attributeModifier.amount();
					}
				});
			}

			// Handle Broken Armor effects
			MobEffectInstance brokenArmorEffect = getEffect(EffectRegistry.BROKEN_ARMOR_EFFECT);
			if (brokenArmorEffect != null) {
				int level = brokenArmorEffect.getAmplifier();
				// armorBreachModifier[0] -= ((BrokenArmorEffect) brokenArmorEffect.getEffect().value()).calculateArmorBreach(level);
			}
		}

		// Ensure the modifier does not go below zero
		// armorBreachModifier[0] = Math.max(0.0F, armorBreachModifier[0]);

		// float damageModifier = 1.0F - armorBreachModifier[0];
		return damageAmount * armorProtection;
	 */
}