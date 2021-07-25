package com.anonymoushacker1279.immersiveweapons.item;

import com.anonymoushacker1279.immersiveweapons.util.AddAttributesAfterSetup;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class PikeItems {

	public static class WoodPikeItem extends AbstractPikeItem {

		public static Multimap<Attribute, AttributeModifier> woodPikeAttributes;

		/**
		 * Constructor for WoodPikeItem.
		 * @param properties the <code>Properties</code> for the item
		 * @param damageIn the damage
		 * @param attackSpeedIn the attack speed
		 */
		public WoodPikeItem(Properties properties, double damageIn, double attackSpeedIn) {
			super(properties, attackSpeedIn, attackSpeedIn);
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", damageIn, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
			woodPikeAttributes = builder.build();
		}

		@Override
		public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack) {
			Multimap<Attribute, AttributeModifier> returnValue;
			if (AddAttributesAfterSetup.hasCompletedClientSetup) {
				returnValue = equipmentSlot == EquipmentSlot.MAINHAND ? WoodPikeItem.woodPikeAttributes : super.getAttributeModifiers(equipmentSlot, stack);
			} else {
				returnValue = WoodPikeItem.woodPikeAttributes;
			}
			return returnValue;
		}
	}

	public static class StonePikeItem extends AbstractPikeItem {

		public static Multimap<Attribute, AttributeModifier> stonePikeAttributes;

		/**
		 * Constructor for StonePikeItem.
		 * @param properties the <code>Properties</code> for the item
		 * @param damageIn the damage
		 * @param attackSpeedIn the attack speed
		 */
		public StonePikeItem(Properties properties, double damageIn, double attackSpeedIn) {
			super(properties, attackSpeedIn, attackSpeedIn);
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", damageIn, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
			stonePikeAttributes = builder.build();
		}

		@Override
		public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack) {
			Multimap<Attribute, AttributeModifier> returnValue;
			if (AddAttributesAfterSetup.hasCompletedClientSetup) {
				returnValue = equipmentSlot == EquipmentSlot.MAINHAND ? StonePikeItem.stonePikeAttributes : super.getAttributeModifiers(equipmentSlot, stack);
			} else {
				returnValue = StonePikeItem.stonePikeAttributes;
			}
			return returnValue;
		}

		@Override
		Ingredient getRepairMaterial() {
			return Ingredient.of(ItemTags.STONE_TOOL_MATERIALS);
		}
	}

	public static class GoldPikeItem extends AbstractPikeItem {

		public static Multimap<Attribute, AttributeModifier> goldPikeAttributes;

		/**
		 * Constructor for GoldPikeItem.
		 * @param properties the <code>Properties</code> for the item
		 * @param damageIn the damage
		 * @param attackSpeedIn the attack speed
		 */
		public GoldPikeItem(Properties properties, double damageIn, double attackSpeedIn) {
			super(properties, attackSpeedIn, attackSpeedIn);
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", damageIn, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
			goldPikeAttributes = builder.build();
		}

		@Override
		public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack) {
			Multimap<Attribute, AttributeModifier> returnValue;
			if (AddAttributesAfterSetup.hasCompletedClientSetup) {
				returnValue = equipmentSlot == EquipmentSlot.MAINHAND ? GoldPikeItem.goldPikeAttributes : super.getAttributeModifiers(equipmentSlot, stack);
			} else {
				returnValue = GoldPikeItem.goldPikeAttributes;
			}
			return returnValue;
		}

		@Override
		Ingredient getRepairMaterial() {
			return Ingredient.of(Items.GOLD_INGOT);
		}
	}

	public static class CopperPikeItem extends AbstractPikeItem {

		public static Multimap<Attribute, AttributeModifier> copperPikeAttributes;

		/**
		 * Constructor for CopperPikeItem.
		 * @param properties the <code>Properties</code> for the item
		 * @param damageIn the damage
		 * @param attackSpeedIn the attack speed
		 */
		public CopperPikeItem(Properties properties, double damageIn, double attackSpeedIn) {
			super(properties, attackSpeedIn, attackSpeedIn);
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", damageIn, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
			copperPikeAttributes = builder.build();
		}

		@Override
		public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack) {
			Multimap<Attribute, AttributeModifier> returnValue;
			if (AddAttributesAfterSetup.hasCompletedClientSetup) {
				returnValue = equipmentSlot == EquipmentSlot.MAINHAND ? CopperPikeItem.copperPikeAttributes : super.getAttributeModifiers(equipmentSlot, stack);
			} else {
				returnValue = CopperPikeItem.copperPikeAttributes;
			}
			return returnValue;
		}

		@Override
		Ingredient getRepairMaterial() {
			return Ingredient.of(Items.COPPER_INGOT);
		}
	}

	public static class IronPikeItem extends AbstractPikeItem {

		public static Multimap<Attribute, AttributeModifier> ironPikeAttributes;

		/**
		 * Constructor for IronPikeItem.
		 * @param properties the <code>Properties</code> for the item
		 * @param damageIn the damage
		 * @param attackSpeedIn the attack speed
		 */
		public IronPikeItem(Properties properties, double damageIn, double attackSpeedIn) {
			super(properties, attackSpeedIn, attackSpeedIn);
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", damageIn, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
			ironPikeAttributes = builder.build();
		}

		@Override
		public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack) {
			Multimap<Attribute, AttributeModifier> returnValue;
			if (AddAttributesAfterSetup.hasCompletedClientSetup) {
				returnValue = equipmentSlot == EquipmentSlot.MAINHAND ? IronPikeItem.ironPikeAttributes : super.getAttributeModifiers(equipmentSlot, stack);
			} else {
				returnValue = IronPikeItem.ironPikeAttributes;
			}
			return returnValue;
		}

		@Override
		Ingredient getRepairMaterial() {
			return Ingredient.of(Items.IRON_INGOT);
		}
	}

	public static class DiamondPikeItem extends AbstractPikeItem {

		public static Multimap<Attribute, AttributeModifier> diamondPikeAttributes;

		/**
		 * Constructor for DiamondPikeItem.
		 * @param properties the <code>Properties</code> for the item
		 * @param damageIn the damage
		 * @param attackSpeedIn the attack speed
		 */
		public DiamondPikeItem(Properties properties, double damageIn, double attackSpeedIn) {
			super(properties, attackSpeedIn, attackSpeedIn);
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", damageIn, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
			diamondPikeAttributes = builder.build();
		}

		@Override
		public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack) {
			Multimap<Attribute, AttributeModifier> returnValue;
			if (AddAttributesAfterSetup.hasCompletedClientSetup) {
				returnValue = equipmentSlot == EquipmentSlot.MAINHAND ? DiamondPikeItem.diamondPikeAttributes : super.getAttributeModifiers(equipmentSlot, stack);
			} else {
				returnValue = DiamondPikeItem.diamondPikeAttributes;
			}
			return returnValue;
		}

		@Override
		Ingredient getRepairMaterial() {
			return Ingredient.of(Items.DIAMOND);
		}
	}

	public static class NetheritePikeItem extends AbstractPikeItem {

		public static Multimap<Attribute, AttributeModifier> netheritePikeAttributes;

		/**
		 * Constructor for NetheritePikeItem.
		 * @param properties the <code>Properties</code> for the item
		 * @param damageIn the damage
		 * @param attackSpeedIn the attack speed
		 */
		public NetheritePikeItem(Properties properties, double damageIn, double attackSpeedIn) {
			super(properties, attackSpeedIn, attackSpeedIn);
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", damageIn, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
			netheritePikeAttributes = builder.build();
		}

		@Override
		public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack) {
			Multimap<Attribute, AttributeModifier> returnValue;
			if (AddAttributesAfterSetup.hasCompletedClientSetup) {
				returnValue = equipmentSlot == EquipmentSlot.MAINHAND ? NetheritePikeItem.netheritePikeAttributes : super.getAttributeModifiers(equipmentSlot, stack);
			} else {
				returnValue = NetheritePikeItem.netheritePikeAttributes;
			}
			return returnValue;
		}

		@Override
		Ingredient getRepairMaterial() {
			return Ingredient.of(Items.NETHERITE_INGOT);
		}
	}
}