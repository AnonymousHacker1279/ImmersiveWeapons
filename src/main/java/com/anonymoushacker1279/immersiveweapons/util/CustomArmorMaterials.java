package com.anonymoushacker1279.immersiveweapons.util;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public enum CustomArmorMaterials implements ArmorMaterial {

	MOLTEN("molten", 39, new int[]{5, 6, 9, 4}, 15, SoundEvents.ARMOR_EQUIP_NETHERITE, 3.5F, () -> {
		return Ingredient.of(DeferredRegistryHandler.MOLTEN_PLATE.get());
	}, 0.12F),
	COPPER("copper", 15, new int[]{1, 4, 5, 1}, 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, () -> {
		return Ingredient.of(Items.COPPER_INGOT);
	}, 0.0F),
	TESLA("tesla", 42, new int[]{7, 8, 11, 6}, 20, SoundEvents.ARMOR_EQUIP_GOLD, 3.0F, () -> {
		return Ingredient.of(DeferredRegistryHandler.TESLA_INGOT.get());
	}, 0.05F),
	COBALT("cobalt", 16, new int[]{3, 5, 6, 3}, 10, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, () -> {
		return Ingredient.of(DeferredRegistryHandler.COBALT_INGOT.get());
	}, 0.0F),
	VENTUS("ventus", 39, new int[]{5, 6, 9, 5}, 14, SoundEvents.ARMOR_EQUIP_NETHERITE, 2.5F, () -> {
		return Ingredient.of(DeferredRegistryHandler.VENTUS_SHARD.get());
	}, 0.02F);

	static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
	private final String name;
	private final int maxDamageFactor;
	private final int[] damageReductionAmountArray;
	private final int enchantability;
	private final SoundEvent soundEvent;
	private final float toughness;
	private final Supplier<Ingredient> repairMaterial;
	private final float knockbackResistance;

	/**
	 * Constructor for CustomArmorMaterials.
	 *
	 * @param nameIn                   the material name
	 * @param maxDamageFactorIn        the max damage factor
	 * @param damageReductionAmountsIn the reduction amount
	 * @param enchantabilityIn         the enchantability level
	 * @param equipSoundIn             the equipping <code>SoundEvent</code>
	 * @param toughnessIn              the toughness level
	 * @param repairMaterialSupplier   the <code>Supplier</code> extending Ingredient for repairs
	 * @param knockbackResistanceIn    the knockback resistance
	 */
	CustomArmorMaterials(String nameIn, int maxDamageFactorIn, int[] damageReductionAmountsIn, int enchantabilityIn, SoundEvent equipSoundIn, float toughnessIn, Supplier<Ingredient> repairMaterialSupplier, float knockbackResistanceIn) {
		name = nameIn;
		maxDamageFactor = maxDamageFactorIn;
		damageReductionAmountArray = damageReductionAmountsIn;
		enchantability = enchantabilityIn;
		soundEvent = equipSoundIn;
		toughness = toughnessIn;
		repairMaterial = repairMaterialSupplier;
		knockbackResistance = knockbackResistanceIn;
	}

	/**
	 * Get durability for a slot.
	 *
	 * @param slotIn the <code>EquipmentSlotType</code>
	 * @return int
	 */
	@Override
	public int getDurabilityForSlot(EquipmentSlot slotIn) {
		return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * maxDamageFactor;
	}

	/**
	 * Get the defense for a slot.
	 *
	 * @param slotIn the <code>EquipmentSlotType</code>
	 * @return int
	 */
	@Override
	public int getDefenseForSlot(EquipmentSlot slotIn) {
		return damageReductionAmountArray[slotIn.getIndex()];
	}

	/**
	 * Get the enchantment value.
	 *
	 * @return int
	 */
	@Override
	public int getEnchantmentValue() {
		return enchantability;
	}

	/**
	 * Get the equipping sound.
	 *
	 * @return SoundEvent
	 */
	@Override
	public @NotNull SoundEvent getEquipSound() {
		return soundEvent;
	}

	/**
	 * Get the repair ingredient.
	 *
	 * @return Ingredient
	 */
	@Override
	public @NotNull Ingredient getRepairIngredient() {
		return repairMaterial.get();
	}

	/**
	 * Get the name.
	 *
	 * @return String
	 */
	@Override
	@OnlyIn(Dist.CLIENT)
	public @NotNull String getName() {
		return name;
	}

	/**
	 * Get the toughness.
	 *
	 * @return float
	 */
	@Override
	public float getToughness() {
		return toughness;
	}

	/**
	 * Get the knockback resistance.
	 *
	 * @return float
	 */
	@Override
	public float getKnockbackResistance() {
		return knockbackResistance;
	}
}