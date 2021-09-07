package com.anonymoushacker1279.immersiveweapons.util;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public enum CustomItemMaterials implements Tier {
	MOLTEN(3, 1900, 8.2F, 3.0F, 17, () -> {
		return Ingredient.of(DeferredRegistryHandler.MOLTEN_INGOT.get());
	}),
	COPPER(2, 180, 5.9F, 2.0F, 12, () -> {
		return Ingredient.of(Items.COPPER_INGOT);
	}),
	TESLA(4, 2100, 18.0F, 3.0F, 20, () -> {
		return Ingredient.of(DeferredRegistryHandler.TESLA_INGOT.get());
	}),
	COBALT(2, 300, 6.2F, 3.0F, 15, () -> {
		return Ingredient.of(DeferredRegistryHandler.COBALT_INGOT.get());
	}),
	VENTUS(3, 1900, 8.6F, 3.0F, 16, () -> {
		return Ingredient.of(DeferredRegistryHandler.VENTUS_SHARD.get());
	});

	private final int harvestLevel;
	private final int maxUses;
	private final float efficiency;
	private final float attackDamage;
	private final int enchantability;
	private final Supplier<Ingredient> repairMaterial;

	/**
	 * Constructor for CustomItemMaterials.
	 * @param harvestLevelIn the harvest level
	 * @param maxUsesIn the max uses
	 * @param efficiencyIn the efficiency
	 * @param attackDamageIn the attack damage
	 * @param enchantabilityIn the enchantability
	 * @param repairMaterialIn the <code>Supplier</code> extending Ingredient for repairs
	 */
	CustomItemMaterials(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
		harvestLevel = harvestLevelIn;
		maxUses = maxUsesIn;
		efficiency = efficiencyIn;
		attackDamage = attackDamageIn;
		enchantability = enchantabilityIn;
		repairMaterial = repairMaterialIn;
	}

	/**
	 * Get max uses.
	 * @return int
	 */
	@Override
	public int getUses() {
		return maxUses;
	}

	/**
	 * Get the efficiency.
	 * @return float
	 */
	@Override
	public float getSpeed() {
		return efficiency;
	}

	/**
	 * Get the attack damage.
	 * @return float
	 */
	@Override
	public float getAttackDamageBonus() {
		return attackDamage;
	}

	/**
	 * Get the harvest level.
	 * @return int
	 */
	@Override
	public int getLevel() {
		return harvestLevel;
	}

	/**
	 * Get the enchantability.
	 * @return int
	 */
	@Override
	public int getEnchantmentValue() {
		return enchantability;
	}

	/**
	 * Get the repair ingredient.
	 * @return Ingredient
	 */
	@Override
	public @NotNull Ingredient getRepairIngredient() {
		return repairMaterial.get();
	}
}