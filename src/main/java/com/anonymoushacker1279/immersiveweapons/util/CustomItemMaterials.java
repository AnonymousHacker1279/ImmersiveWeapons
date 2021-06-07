package com.anonymoushacker1279.immersiveweapons.util;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

import java.util.function.Supplier;

public enum CustomItemMaterials implements IItemTier {
	MOLTEN(3, 1900, 8.2F, 3.0F, 17, () -> {
		return Ingredient.of(DeferredRegistryHandler.MOLTEN_INGOT.get());
	}),
	COPPER(2, 180, 5.9F, 2.0F, 12, () -> {
		return Ingredient.of(DeferredRegistryHandler.COPPER_INGOT.get());
	}),
	TESLA(4, 2100, 18.0F, 3.0F, 20, () -> {
		return Ingredient.of(DeferredRegistryHandler.TESLA_INGOT.get());
	}),
	COBALT(2, 300, 6.2F, 3.0F, 15, () -> {
		return Ingredient.of(DeferredRegistryHandler.COBALT_INGOT.get());
	});

	private final int harvestLevel;
	private final int maxUses;
	private final float efficiency;
	private final float attackDamage;
	private final int enchantability;
	private final LazyValue<Ingredient> repairMaterial;

	CustomItemMaterials(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
		this.harvestLevel = harvestLevelIn;
		this.maxUses = maxUsesIn;
		this.efficiency = efficiencyIn;
		this.attackDamage = attackDamageIn;
		this.enchantability = enchantabilityIn;
		this.repairMaterial = new LazyValue<>(repairMaterialIn);
	}

	@Override
	public int getUses() {
		return this.maxUses;
	}

	@Override
	public float getSpeed() {
		return this.efficiency;
	}

	@Override
	public float getAttackDamageBonus() {
		return this.attackDamage;
	}

	@Override
	public int getLevel() {
		return this.harvestLevel;
	}

	@Override
	public int getEnchantmentValue() {
		return this.enchantability;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return this.repairMaterial.get();
	}
}