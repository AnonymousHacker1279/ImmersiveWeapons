package com.anonymoushacker1279.immersiveweapons.util;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public enum CustomArmorMaterials implements IArmorMaterial {

	MOLTEN("molten", 39, new int[]{5, 6, 9, 4}, 15, SoundEvents.ARMOR_EQUIP_NETHERITE, 3.5F, () -> {
		return Ingredient.of(DeferredRegistryHandler.MOLTEN_PLATE.get());
	}, 0.12F),
	COPPER("copper", 15, new int[]{1, 4, 5, 1}, 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, () -> {
		return Ingredient.of(DeferredRegistryHandler.COPPER_INGOT.get());
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
	public int getDurabilityForSlot(EquipmentSlotType slotIn) {
		return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
	}

	@Override
	public int getDefenseForSlot(EquipmentSlotType slotIn) {
		return this.damageReductionAmountArray[slotIn.getIndex()];
	}

	@Override
	public int getEnchantmentValue() {
		return this.enchantability;
	}

	@Override
	public SoundEvent getEquipSound() {
		return this.soundEvent;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return this.repairMaterial.get();
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