package com.anonymoushacker1279.immersiveweapons.util;

import java.util.function.Supplier;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public enum CustomArmorMaterials implements IArmorMaterial {

	 MOLTEN("molten", 45, new int[]{5, 6, 9, 4}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.5F, () -> {
		 return Ingredient.fromItems(DeferredRegistryHandler.MOLTEN_PLATE.get()); 
	 }, 0.12F),
	 COPPER("copper", 25, new int[]{1, 4, 5, 1}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F, () -> {
		 return Ingredient.fromItems(DeferredRegistryHandler.COPPER_INGOT.get());
	 }, 0.0F),
	 TESLA("tesla", 65, new int[]{7, 8, 11, 6}, 11, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 3.0F, () -> {
		 return Ingredient.fromItems(DeferredRegistryHandler.TESLA_INGOT.get());
	 }, 0.0F);
	 
	   static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
	   private final String name;
	   private final int maxDamageFactor;
	   private final int[] damageReductionAmountArray;
	   private final int enchantability;
	   private final SoundEvent soundEvent;
	   private final float toughness;
	   private final LazyValue<Ingredient> repairMaterial;
	   private final float knockbackResistance;

	  CustomArmorMaterials(String nameIn, int maxDamageFactorIn, int[] damageReductionAmountsIn, int enchantabilityIn, SoundEvent equipSoundIn, float toughnessIn, Supplier<Ingredient> repairMaterialSupplier, float knockbackResistance) {
	      this.name = nameIn;
	      this.maxDamageFactor = maxDamageFactorIn;
	      this.damageReductionAmountArray = damageReductionAmountsIn;
	      this.enchantability = enchantabilityIn;
	      this.soundEvent = equipSoundIn;
	      this.toughness = toughnessIn;
	      this.repairMaterial = new LazyValue<>(repairMaterialSupplier);
	      this.knockbackResistance = knockbackResistance;
	   }

	   @Override
	public int getDurability(EquipmentSlotType slotIn) {
	      return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
	   }

	   @Override
	public int getDamageReductionAmount(EquipmentSlotType slotIn) {
	      return this.damageReductionAmountArray[slotIn.getIndex()];
	   }

	   @Override
	public int getEnchantability() {
	      return this.enchantability;
	   }

	   @Override
	public SoundEvent getSoundEvent() {
	      return this.soundEvent;
	   }

	   @Override
	public Ingredient getRepairMaterial() {
	      return this.repairMaterial.getValue();
	   }

	   @Override
	@OnlyIn(Dist.CLIENT)
	   public String getName() {
	      return this.name;
	   }

	   @Override
	public float getToughness() {
	      return this.toughness;
	   }

	@Override
	public float getKnockbackResistance() {
		return this.knockbackResistance;
	}
}
