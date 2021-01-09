package com.anonymoushacker1279.immersiveweapons.util;

import java.util.function.Supplier;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

public enum CustomItemMaterials implements IItemTier{
	   MOLTEN(3, 1900, 8.2F, 3.0F, 14, () -> {
		   		return Ingredient.fromItems(DeferredRegistryHandler.MOLTEN_INGOT.get()); 
	   }),
	   COPPER(2, 180, 5.9F, 2.0F, 11, () -> {
		   		return Ingredient.fromItems(DeferredRegistryHandler.COPPER_INGOT.get());
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
		public int getMaxUses() {
		      return this.maxUses;
		   }

		   @Override
		public float getEfficiency() {
		      return this.efficiency;
		   }

		   @Override
		public float getAttackDamage() {
		      return this.attackDamage;
		   }

		   @Override
		public int getHarvestLevel() {
		      return this.harvestLevel;
		   }

		   @Override
		public int getEnchantability() {
		      return this.enchantability;
		   }

		   @Override
		public Ingredient getRepairMaterial() {
		      return this.repairMaterial.getValue();
		   } 
}
