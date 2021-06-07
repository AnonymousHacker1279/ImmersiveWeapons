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

public class PikeItem {

	public static class WoodPikeItem extends Pike {

		public static Multimap<Attribute, AttributeModifier> woodPikeAttributes;
		private Multimap<Attribute, AttributeModifier> returnValue;

		public WoodPikeItem(Item.Properties builderIn, double damageIn, double attackSpeedIn) {
			super(builderIn, attackSpeedIn, attackSpeedIn);
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", damageIn, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
			woodPikeAttributes = builder.build();
		}

		@SuppressWarnings("deprecation")
		@Override
		public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType equipmentSlot) {
			if (AddAttributesAfterSetup.hasCompletedClientSetup) {
				returnValue = equipmentSlot == EquipmentSlotType.MAINHAND ? WoodPikeItem.woodPikeAttributes : super.getDefaultAttributeModifiers(equipmentSlot);
			} else {
				returnValue = WoodPikeItem.woodPikeAttributes;
			}
			return returnValue;
		}

		public Ingredient getRepairMaterial() {
			return Ingredient.of(ItemTags.PLANKS);
		}

		@Override
		public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
			return this.getRepairMaterial().test(repair) || super.isValidRepairItem(toRepair, repair);
		}

	}

	public static class StonePikeItem extends Pike {

		public static Multimap<Attribute, AttributeModifier> stonePikeAttributes;
		private Multimap<Attribute, AttributeModifier> returnValue;

		public StonePikeItem(Item.Properties builderIn, double damageIn, double attackSpeedIn) {
			super(builderIn, attackSpeedIn, attackSpeedIn);
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", damageIn, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
			stonePikeAttributes = builder.build();
		}

		@SuppressWarnings("deprecation")
		@Override
		public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType equipmentSlot) {
			if (AddAttributesAfterSetup.hasCompletedClientSetup) {
				returnValue = equipmentSlot == EquipmentSlotType.MAINHAND ? StonePikeItem.stonePikeAttributes : super.getDefaultAttributeModifiers(equipmentSlot);
			} else {
				returnValue = StonePikeItem.stonePikeAttributes;
			}
			return returnValue;
		}

		public Ingredient getRepairMaterial() {
			return Ingredient.of(ItemTags.STONE_TOOL_MATERIALS);
		}

		@Override
		public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
			return this.getRepairMaterial().test(repair) || super.isValidRepairItem(toRepair, repair);
		}
	}

	public static class GoldPikeItem extends Pike {

		public static Multimap<Attribute, AttributeModifier> goldPikeAttributes;
		private Multimap<Attribute, AttributeModifier> returnValue;

		public GoldPikeItem(Item.Properties builderIn, double damageIn, double attackSpeedIn) {
			super(builderIn, attackSpeedIn, attackSpeedIn);
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", damageIn, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
			goldPikeAttributes = builder.build();
		}

		@SuppressWarnings("deprecation")
		@Override
		public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType equipmentSlot) {
			if (AddAttributesAfterSetup.hasCompletedClientSetup) {
				returnValue = equipmentSlot == EquipmentSlotType.MAINHAND ? GoldPikeItem.goldPikeAttributes : super.getDefaultAttributeModifiers(equipmentSlot);
			} else {
				returnValue = GoldPikeItem.goldPikeAttributes;
			}
			return returnValue;
		}

		public Ingredient getRepairMaterial() {
			return Ingredient.of(Items.GOLD_INGOT);
		}

		@Override
		public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
			return this.getRepairMaterial().test(repair) || super.isValidRepairItem(toRepair, repair);
		}
	}

	public static class CopperPikeItem extends Pike {

		public static Multimap<Attribute, AttributeModifier> copperPikeAttributes;
		private Multimap<Attribute, AttributeModifier> returnValue;

		public CopperPikeItem(Item.Properties builderIn, double damageIn, double attackSpeedIn) {
			super(builderIn, attackSpeedIn, attackSpeedIn);
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", damageIn, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
			copperPikeAttributes = builder.build();
		}

		@SuppressWarnings("deprecation")
		@Override
		public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType equipmentSlot) {
			if (AddAttributesAfterSetup.hasCompletedClientSetup) {
				returnValue = equipmentSlot == EquipmentSlotType.MAINHAND ? CopperPikeItem.copperPikeAttributes : super.getDefaultAttributeModifiers(equipmentSlot);
			} else {
				returnValue = CopperPikeItem.copperPikeAttributes;
			}
			return returnValue;
		}

		public Ingredient getRepairMaterial() {
			return Ingredient.of(DeferredRegistryHandler.COPPER_INGOT.get());
		}

		@Override
		public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
			return this.getRepairMaterial().test(repair) || super.isValidRepairItem(toRepair, repair);
		}
	}

	public static class IronPikeItem extends Pike {

		public static Multimap<Attribute, AttributeModifier> ironPikeAttributes;
		private Multimap<Attribute, AttributeModifier> returnValue;

		public IronPikeItem(Item.Properties builderIn, double damageIn, double attackSpeedIn) {
			super(builderIn, attackSpeedIn, attackSpeedIn);
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", damageIn, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
			ironPikeAttributes = builder.build();
		}

		@SuppressWarnings("deprecation")
		@Override
		public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType equipmentSlot) {
			if (AddAttributesAfterSetup.hasCompletedClientSetup) {
				returnValue = equipmentSlot == EquipmentSlotType.MAINHAND ? IronPikeItem.ironPikeAttributes : super.getDefaultAttributeModifiers(equipmentSlot);
			} else {
				returnValue = IronPikeItem.ironPikeAttributes;
			}
			return returnValue;
		}

		public Ingredient getRepairMaterial() {
			return Ingredient.of(Items.IRON_INGOT);
		}

		@Override
		public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
			return this.getRepairMaterial().test(repair) || super.isValidRepairItem(toRepair, repair);
		}
	}

	public static class DiamondPikeItem extends Pike {

		public static Multimap<Attribute, AttributeModifier> diamondPikeAttributes;
		private Multimap<Attribute, AttributeModifier> returnValue;

		public DiamondPikeItem(Item.Properties builderIn, double damageIn, double attackSpeedIn) {
			super(builderIn, attackSpeedIn, attackSpeedIn);
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", damageIn, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
			diamondPikeAttributes = builder.build();
		}

		@SuppressWarnings("deprecation")
		@Override
		public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType equipmentSlot) {
			if (AddAttributesAfterSetup.hasCompletedClientSetup) {
				returnValue = equipmentSlot == EquipmentSlotType.MAINHAND ? DiamondPikeItem.diamondPikeAttributes : super.getDefaultAttributeModifiers(equipmentSlot);
			} else {
				returnValue = DiamondPikeItem.diamondPikeAttributes;
			}
			return returnValue;
		}

		public Ingredient getRepairMaterial() {
			return Ingredient.of(Items.DIAMOND);
		}

		@Override
		public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
			return this.getRepairMaterial().test(repair) || super.isValidRepairItem(toRepair, repair);
		}
	}

	public static class NetheritePikeItem extends Pike {

		public static Multimap<Attribute, AttributeModifier> netheritePikeAttributes;
		private Multimap<Attribute, AttributeModifier> returnValue;

		public NetheritePikeItem(Item.Properties builderIn, double damageIn, double attackSpeedIn) {
			super(builderIn, attackSpeedIn, attackSpeedIn);
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", damageIn, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
			netheritePikeAttributes = builder.build();
		}

		@SuppressWarnings("deprecation")
		@Override
		public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType equipmentSlot) {
			if (AddAttributesAfterSetup.hasCompletedClientSetup) {
				returnValue = equipmentSlot == EquipmentSlotType.MAINHAND ? NetheritePikeItem.netheritePikeAttributes : super.getDefaultAttributeModifiers(equipmentSlot);
			} else {
				returnValue = NetheritePikeItem.netheritePikeAttributes;
			}
			return returnValue;
		}

		public Ingredient getRepairMaterial() {
			return Ingredient.of(Items.NETHERITE_INGOT);
		}

		@Override
		public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
			return this.getRepairMaterial().test(repair) || super.isValidRepairItem(toRepair, repair);
		}
	}
}