package com.anonymoushacker1279.immersiveweapons.item.gauntlet;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.init.PostSetupHandler;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class DiamondGauntletItem extends GauntletItem {
	public static Multimap<Attribute, AttributeModifier> gauntletAttributes;
	private final float bleedChance;

	public DiamondGauntletItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties, float bleedChance) {
		super(tier, attackDamageModifier, attackSpeedModifier, properties, bleedChance);
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (float) attackDamageModifier + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeedModifier, AttributeModifier.Operation.ADDITION));
		gauntletAttributes = builder.build();
		this.bleedChance = bleedChance;
	}

	@Override
	public @NotNull Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack) {
		Multimap<Attribute, AttributeModifier> returnValue;
		if (PostSetupHandler.hasCompletedClientSetup) {
			returnValue = equipmentSlot == EquipmentSlot.MAINHAND ? DiamondGauntletItem.gauntletAttributes : super.getAttributeModifiers(equipmentSlot, stack);
		} else {
			returnValue = DiamondGauntletItem.gauntletAttributes;
		}
		return returnValue;
	}

	@Override
	Ingredient getRepairMaterial() {
		return Ingredient.of(Items.DIAMOND);
	}

	@Override
	public void bleedBehavior(LivingEntity target) {
		if (GeneralUtilities.getRandomNumber(0.0f, 1.0f) <= bleedChance) {
			target.addEffect(new MobEffectInstance(DeferredRegistryHandler.BLEEDING_EFFECT.get(), 200, 1, false, false));
		}
	}
}