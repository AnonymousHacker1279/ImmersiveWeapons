package tech.anonymoushacker1279.immersiveweapons.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import tech.anonymoushacker1279.immersiveweapons.config.CommonConfig;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity;
import tech.anonymoushacker1279.immersiveweapons.init.AttributeRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;
import tech.anonymoushacker1279.immersiveweapons.potion.BrokenArmorEffect;

import javax.annotation.Nullable;

/**
 * Replace the vanilla armor cap of 20 points with a configurable value. Additionally, it allows a custom armor breach attribute to be used.
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	@Shadow
	public abstract int getArmorValue();

	@Shadow
	public abstract double getAttributeValue(Holder<Attribute> pAttribute);

	@Shadow
	@Nullable
	public abstract MobEffectInstance getEffect(Holder<MobEffect> pEffect);

	@ModifyVariable(method = "getDamageAfterArmorAbsorb", at = @At(value = "STORE"), require = 0, argsOnly = true)
	private float getDamageAfterAbsorb(float previousValue, @Local(argsOnly = true) DamageSource source, @Local(argsOnly = true) float damageAmount) {
		float armor = getArmorValue();
		float armorToughness = (float) getAttributeValue(Attributes.ARMOR_TOUGHNESS);

		float toughnessModifier = 2.0F + armorToughness / 4.0F;
		float armorProtection = (float) Mth.clamp(armor - damageAmount / toughnessModifier, armor * 0.2F, CommonConfig.maxArmorProtection) / 25f;
		final float[] armorBreachModifier = {EnchantmentHelper.calculateArmorBreach(source.getEntity(), armorProtection)};

		if (source.getEntity() instanceof LivingEntity attackingEntity) {
			ItemStack heldItem = attackingEntity.getMainHandItem();

			// Prevents being able to fire a projectile and switching to a weapon with armor breach to increase damage
			if (source.getDirectEntity() instanceof CustomArrowEntity customArrowEntity) {
				heldItem = customArrowEntity.firedWithStack;
			}

			if (heldItem != null) {
				heldItem.getAttributeModifiers(EquipmentSlot.MAINHAND).forEach((attribute, attributeModifier) -> {
					if (attribute == AttributeRegistry.ARMOR_BREACH) {
						armorBreachModifier[0] -= (float) attributeModifier.amount();
					}
				});
			}

			// Handle Broken Armor effects
			MobEffectInstance brokenArmorEffect = getEffect(EffectRegistry.BROKEN_ARMOR_EFFECT);
			if (brokenArmorEffect != null) {
				int level = brokenArmorEffect.getAmplifier();
				armorBreachModifier[0] -= ((BrokenArmorEffect) brokenArmorEffect.getEffect().value()).calculateArmorBreach(level);
			}
		}

		// Ensure the modifier does not go below zero
		armorBreachModifier[0] = Math.max(0.0F, armorBreachModifier[0]);

		float damageModifier = 1.0F - armorBreachModifier[0];
		return damageAmount * damageModifier;
	}
}