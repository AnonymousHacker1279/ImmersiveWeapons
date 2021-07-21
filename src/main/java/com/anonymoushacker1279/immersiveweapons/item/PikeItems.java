package com.anonymoushacker1279.immersiveweapons.item;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.AddAttributesAfterSetup;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;

public class PikeItems {

	public static class WoodPikeItem extends PikeItem {

		public static Multimap<Attribute, AttributeModifier> woodPikeAttributes;

		public WoodPikeItem(Item.Properties builderIn, double damageIn, double attackSpeedIn) {
			super(builderIn, attackSpeedIn, attackSpeedIn);
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", damageIn, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
			woodPikeAttributes = builder.build();
		}

		@Override
		public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType equipmentSlot) {
			Multimap<Attribute, AttributeModifier> returnValue;
			if (AddAttributesAfterSetup.hasCompletedClientSetup) {
				returnValue = equipmentSlot == EquipmentSlotType.MAINHAND ? WoodPikeItem.woodPikeAttributes : super.getDefaultAttributeModifiers(equipmentSlot);
			} else {
				returnValue = WoodPikeItem.woodPikeAttributes;
			}
			return returnValue;
		}

		Ingredient getRepairMaterial() {
			return Ingredient.of(ItemTags.PLANKS);
		}

		@Override
		public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
			return getRepairMaterial().test(repair) || super.isValidRepairItem(toRepair, repair);
		}

	}

	public static class StonePikeItem extends PikeItem {

		public static Multimap<Attribute, AttributeModifier> stonePikeAttributes;

		public StonePikeItem(Item.Properties builderIn, double damageIn, double attackSpeedIn) {
			super(builderIn, attackSpeedIn, attackSpeedIn);
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", damageIn, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
			stonePikeAttributes = builder.build();
		}

		@Override
		public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType equipmentSlot) {
			Multimap<Attribute, AttributeModifier> returnValue;
			if (AddAttributesAfterSetup.hasCompletedClientSetup) {
				returnValue = equipmentSlot == EquipmentSlotType.MAINHAND ? StonePikeItem.stonePikeAttributes : super.getDefaultAttributeModifiers(equipmentSlot);
			} else {
				returnValue = StonePikeItem.stonePikeAttributes;
			}
			return returnValue;
		}

		Ingredient getRepairMaterial() {
			return Ingredient.of(ItemTags.STONE_TOOL_MATERIALS);
		}

		@Override
		public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
			return getRepairMaterial().test(repair) || super.isValidRepairItem(toRepair, repair);
		}
	}

	public static class GoldPikeItem extends PikeItem {

		public static Multimap<Attribute, AttributeModifier> goldPikeAttributes;

		public GoldPikeItem(Item.Properties builderIn, double damageIn, double attackSpeedIn) {
			super(builderIn, attackSpeedIn, attackSpeedIn);
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", damageIn, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
			goldPikeAttributes = builder.build();
		}

		@Override
		public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType equipmentSlot) {
			Multimap<Attribute, AttributeModifier> returnValue;
			if (AddAttributesAfterSetup.hasCompletedClientSetup) {
				returnValue = equipmentSlot == EquipmentSlotType.MAINHAND ? GoldPikeItem.goldPikeAttributes : super.getDefaultAttributeModifiers(equipmentSlot);
			} else {
				returnValue = GoldPikeItem.goldPikeAttributes;
			}
			return returnValue;
		}

		Ingredient getRepairMaterial() {
			return Ingredient.of(Items.GOLD_INGOT);
		}

		@Override
		public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
			return getRepairMaterial().test(repair) || super.isValidRepairItem(toRepair, repair);
		}
	}

	public static class CopperPikeItem extends PikeItem {

		public static Multimap<Attribute, AttributeModifier> copperPikeAttributes;

		public CopperPikeItem(Item.Properties builderIn, double damageIn, double attackSpeedIn) {
			super(builderIn, attackSpeedIn, attackSpeedIn);
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", damageIn, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
			copperPikeAttributes = builder.build();
		}

		@Override
		public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType equipmentSlot) {
			Multimap<Attribute, AttributeModifier> returnValue;
			if (AddAttributesAfterSetup.hasCompletedClientSetup) {
				returnValue = equipmentSlot == EquipmentSlotType.MAINHAND ? CopperPikeItem.copperPikeAttributes : super.getDefaultAttributeModifiers(equipmentSlot);
			} else {
				returnValue = CopperPikeItem.copperPikeAttributes;
			}
			return returnValue;
		}

		Ingredient getRepairMaterial() {
			return Ingredient.of(DeferredRegistryHandler.COPPER_INGOT.get());
		}

		@Override
		public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
			return getRepairMaterial().test(repair) || super.isValidRepairItem(toRepair, repair);
		}
	}

	public static class IronPikeItem extends PikeItem {

		public static Multimap<Attribute, AttributeModifier> ironPikeAttributes;

		public IronPikeItem(Item.Properties builderIn, double damageIn, double attackSpeedIn) {
			super(builderIn, attackSpeedIn, attackSpeedIn);
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", damageIn, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
			ironPikeAttributes = builder.build();
		}

		@Override
		public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType equipmentSlot) {
			Multimap<Attribute, AttributeModifier> returnValue;
			if (AddAttributesAfterSetup.hasCompletedClientSetup) {
				returnValue = equipmentSlot == EquipmentSlotType.MAINHAND ? IronPikeItem.ironPikeAttributes : super.getDefaultAttributeModifiers(equipmentSlot);
			} else {
				returnValue = IronPikeItem.ironPikeAttributes;
			}
			return returnValue;
		}

		Ingredient getRepairMaterial() {
			return Ingredient.of(Items.IRON_INGOT);
		}

		@Override
		public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
			return getRepairMaterial().test(repair) || super.isValidRepairItem(toRepair, repair);
		}
	}

	public static class DiamondPikeItem extends PikeItem {

		public static Multimap<Attribute, AttributeModifier> diamondPikeAttributes;

		public DiamondPikeItem(Item.Properties builderIn, double damageIn, double attackSpeedIn) {
			super(builderIn, attackSpeedIn, attackSpeedIn);
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", damageIn, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
			diamondPikeAttributes = builder.build();
		}

		@Override
		public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType equipmentSlot) {
			Multimap<Attribute, AttributeModifier> returnValue;
			if (AddAttributesAfterSetup.hasCompletedClientSetup) {
				returnValue = equipmentSlot == EquipmentSlotType.MAINHAND ? DiamondPikeItem.diamondPikeAttributes : super.getDefaultAttributeModifiers(equipmentSlot);
			} else {
				returnValue = DiamondPikeItem.diamondPikeAttributes;
			}
			return returnValue;
		}

		Ingredient getRepairMaterial() {
			return Ingredient.of(Items.DIAMOND);
		}

		@Override
		public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
			return getRepairMaterial().test(repair) || super.isValidRepairItem(toRepair, repair);
		}
	}

	public static class NetheritePikeItem extends PikeItem {

		public static Multimap<Attribute, AttributeModifier> netheritePikeAttributes;

		public NetheritePikeItem(Item.Properties builderIn, double damageIn, double attackSpeedIn) {
			super(builderIn, attackSpeedIn, attackSpeedIn);
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", damageIn, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
			netheritePikeAttributes = builder.build();
		}

		@Override
		public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType equipmentSlot) {
			Multimap<Attribute, AttributeModifier> returnValue;
			if (AddAttributesAfterSetup.hasCompletedClientSetup) {
				returnValue = equipmentSlot == EquipmentSlotType.MAINHAND ? NetheritePikeItem.netheritePikeAttributes : super.getDefaultAttributeModifiers(equipmentSlot);
			} else {
				returnValue = NetheritePikeItem.netheritePikeAttributes;
			}
			return returnValue;
		}

		Ingredient getRepairMaterial() {
			return Ingredient.of(Items.NETHERITE_INGOT);
		}

		@Override
		public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
			return getRepairMaterial().test(repair) || super.isValidRepairItem(toRepair, repair);
		}
	}
}