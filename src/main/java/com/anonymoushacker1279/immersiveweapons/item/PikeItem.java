package com.anonymoushacker1279.immersiveweapons.item;

import com.anonymoushacker1279.immersiveweapons.util.AddAttributesAfterSetup;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;

import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;

public class PikeItem {
	
	public static class WoodPikeItem extends Pike {
		
		public static Multimap<Attribute, AttributeModifier> woodPikeAttributes;

		public WoodPikeItem(Item.Properties builderIn, double damageIn, double attackSpeedIn) {
			super(builderIn, attackSpeedIn, attackSpeedIn);
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", damageIn, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
			woodPikeAttributes = builder.build();
		 }
		
		private Multimap<Attribute, AttributeModifier> returnValue;
		
		@SuppressWarnings("deprecation")
		@Override
		public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
			if (AddAttributesAfterSetup.hasCompletedClientSetup) {
				returnValue =  equipmentSlot == EquipmentSlotType.MAINHAND ? WoodPikeItem.woodPikeAttributes : super.getAttributeModifiers(equipmentSlot);
			} else {
				returnValue = WoodPikeItem.woodPikeAttributes;
			}
			return returnValue;
		}
		
	}
	
	public static class StonePikeItem extends Pike {
		
		public static Multimap<Attribute, AttributeModifier> stonePikeAttributes;

		public StonePikeItem(Item.Properties builderIn, double damageIn, double attackSpeedIn) {
			super(builderIn, attackSpeedIn, attackSpeedIn);
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", damageIn, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
			stonePikeAttributes = builder.build();
		 }
		
		private Multimap<Attribute, AttributeModifier> returnValue;
		
		@SuppressWarnings("deprecation")
		@Override
		public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
			if (AddAttributesAfterSetup.hasCompletedClientSetup) {
				returnValue =  equipmentSlot == EquipmentSlotType.MAINHAND ? StonePikeItem.stonePikeAttributes : super.getAttributeModifiers(equipmentSlot);
			} else {
				returnValue = StonePikeItem.stonePikeAttributes;
			}
			return returnValue;
		}
		
	}
	
	public static class GoldPikeItem extends Pike {
		
		public static Multimap<Attribute, AttributeModifier> goldPikeAttributes;

		public GoldPikeItem(Item.Properties builderIn, double damageIn, double attackSpeedIn) {
			super(builderIn, attackSpeedIn, attackSpeedIn);
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", damageIn, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
			goldPikeAttributes = builder.build();
		 }
		
		private Multimap<Attribute, AttributeModifier> returnValue;
		
		@SuppressWarnings("deprecation")
		@Override
		public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
			if (AddAttributesAfterSetup.hasCompletedClientSetup) {
				returnValue =  equipmentSlot == EquipmentSlotType.MAINHAND ? GoldPikeItem.goldPikeAttributes : super.getAttributeModifiers(equipmentSlot);
			} else {
				returnValue = GoldPikeItem.goldPikeAttributes;
			}
			return returnValue;
		}
		
	}

	public static class CopperPikeItem extends Pike {
		
		public static Multimap<Attribute, AttributeModifier> copperPikeAttributes;

		public CopperPikeItem(Item.Properties builderIn, double damageIn, double attackSpeedIn) {
			super(builderIn, attackSpeedIn, attackSpeedIn);
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", damageIn, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
			copperPikeAttributes = builder.build();
		 }
		
		private Multimap<Attribute, AttributeModifier> returnValue;
		
		@SuppressWarnings("deprecation")
		@Override
		public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
			if (AddAttributesAfterSetup.hasCompletedClientSetup) {
				returnValue =  equipmentSlot == EquipmentSlotType.MAINHAND ? CopperPikeItem.copperPikeAttributes : super.getAttributeModifiers(equipmentSlot);
			} else {
				returnValue = CopperPikeItem.copperPikeAttributes;
			}
			return returnValue;
		}
		
	}
	
	public static class IronPikeItem extends Pike {
		
		public static Multimap<Attribute, AttributeModifier> ironPikeAttributes;

		public IronPikeItem(Item.Properties builderIn, double damageIn, double attackSpeedIn) {
			super(builderIn, attackSpeedIn, attackSpeedIn);
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", damageIn, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
			ironPikeAttributes = builder.build();
		 }
		
		private Multimap<Attribute, AttributeModifier> returnValue;
		
		@SuppressWarnings("deprecation")
		@Override
		public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
			if (AddAttributesAfterSetup.hasCompletedClientSetup) {
				returnValue =  equipmentSlot == EquipmentSlotType.MAINHAND ? IronPikeItem.ironPikeAttributes : super.getAttributeModifiers(equipmentSlot);
			} else {
				returnValue = IronPikeItem.ironPikeAttributes;
			}
			return returnValue;
		}
		
	}
	
	public static class DiamondPikeItem extends Pike {
		
		public static Multimap<Attribute, AttributeModifier> diamondPikeAttributes;

		public DiamondPikeItem(Item.Properties builderIn, double damageIn, double attackSpeedIn) {
			super(builderIn, attackSpeedIn, attackSpeedIn);
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", damageIn, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
			diamondPikeAttributes = builder.build();
		 }
		
		private Multimap<Attribute, AttributeModifier> returnValue;
		
		@SuppressWarnings("deprecation")
		@Override
		public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
			if (AddAttributesAfterSetup.hasCompletedClientSetup) {
				returnValue =  equipmentSlot == EquipmentSlotType.MAINHAND ? DiamondPikeItem.diamondPikeAttributes : super.getAttributeModifiers(equipmentSlot);
			} else {
				returnValue = DiamondPikeItem.diamondPikeAttributes;
			}
			return returnValue;
		}
		
	}
	
	public static class NetheritePikeItem extends Pike {
		
		public static Multimap<Attribute, AttributeModifier> netheritePikeAttributes;

		public NetheritePikeItem(Item.Properties builderIn, double damageIn, double attackSpeedIn) {
			super(builderIn, attackSpeedIn, attackSpeedIn);
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", damageIn, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
			netheritePikeAttributes = builder.build();
		 }
		
		private Multimap<Attribute, AttributeModifier> returnValue;
		
		@SuppressWarnings("deprecation")
		@Override
		public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
			if (AddAttributesAfterSetup.hasCompletedClientSetup) {
				returnValue =  equipmentSlot == EquipmentSlotType.MAINHAND ? NetheritePikeItem.netheritePikeAttributes : super.getAttributeModifiers(equipmentSlot);
			} else {
				returnValue = NetheritePikeItem.netheritePikeAttributes;
			}
			return returnValue;
		}
		
	}
}
