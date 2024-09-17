package tech.anonymoushacker1279.immersiveweapons.data.enchantments;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.item.enchantment.effects.AddValue;
import tech.anonymoushacker1279.immersiveweapons.data.IWEnchantments;
import tech.anonymoushacker1279.immersiveweapons.data.groups.immersiveweapons.IWItemTagGroups;

public class EnchantmentsGenerator {

	public static void bootstrap(BootstrapContext<Enchantment> context) {
		HolderGetter<Enchantment> enchantmentGetter = context.lookup(Registries.ENCHANTMENT);
		HolderGetter<Item> itemGetter = context.lookup(Registries.ITEM);

		register(context,
				IWEnchantments.FIREPOWER,
				Enchantment.enchantment(Enchantment.definition(
						itemGetter.getOrThrow(IWItemTagGroups.FIREARMS),
						10,
						5,
						Enchantment.dynamicCost(1, 10),
						Enchantment.dynamicCost(16, 10),
						1,
						EquipmentSlotGroup.HAND
				)));

		register(context,
				IWEnchantments.IMPACT,
				Enchantment.enchantment(Enchantment.definition(
						itemGetter.getOrThrow(IWItemTagGroups.FIREARMS),
						2,
						2,
						Enchantment.dynamicCost(12, 20),
						Enchantment.dynamicCost(37, 20),
						4,
						EquipmentSlotGroup.HAND
				)));

		register(context,
				IWEnchantments.ENDLESS_MUSKET_POUCH,
				Enchantment.enchantment(Enchantment.definition(
						itemGetter.getOrThrow(IWItemTagGroups.FIREARMS),
						1,
						1,
						Enchantment.constantCost(20),
						Enchantment.constantCost(50),
						8,
						EquipmentSlotGroup.HAND
				)));

		register(context,
				IWEnchantments.SCORCH_SHOT,
				Enchantment.enchantment(Enchantment.definition(
						itemGetter.getOrThrow(IWItemTagGroups.FIREARMS),
						2,
						1,
						Enchantment.constantCost(20),
						Enchantment.constantCost(50),
						8,
						EquipmentSlotGroup.HAND
				)));

		register(context,
				IWEnchantments.RAPID_FIRE,
				Enchantment.enchantment(Enchantment.definition(
						itemGetter.getOrThrow(IWItemTagGroups.FIREARMS),
						5,
						3,
						Enchantment.dynamicCost(10, 15),
						Enchantment.dynamicCost(16, 15),
						5,
						EquipmentSlotGroup.HAND
				)));

		register(context,
				IWEnchantments.VELOCITY,
				Enchantment.enchantment(Enchantment.definition(
						itemGetter.getOrThrow(IWItemTagGroups.RANGED_WEAPONS),
						5,
						3,
						Enchantment.dynamicCost(15, 20),
						Enchantment.dynamicCost(25, 20),
						5,
						EquipmentSlotGroup.HAND
				)));

		register(context,
				IWEnchantments.EXTENDED_REACH,
				Enchantment.enchantment(Enchantment.definition(
						itemGetter.getOrThrow(IWItemTagGroups.PIKES),
						2,
						1,
						Enchantment.constantCost(15),
						Enchantment.constantCost(30),
						3,
						EquipmentSlotGroup.HAND
				)));

		register(context,
				IWEnchantments.SHARPENED_HEAD,
				Enchantment.enchantment(Enchantment.definition(
								itemGetter.getOrThrow(IWItemTagGroups.PIKES),
								10,
								5,
								Enchantment.dynamicCost(1, 11),
								Enchantment.dynamicCost(21, 11),
								1,
								EquipmentSlotGroup.HAND
						))
						.exclusiveWith(enchantmentGetter.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE))
						.withEffect(EnchantmentEffectComponents.DAMAGE, new AddValue(LevelBasedValue.perLevel(1.0F, 0.5F))));

		register(context,
				IWEnchantments.CRIMSON_CLAW,
				Enchantment.enchantment(Enchantment.definition(
						itemGetter.getOrThrow(IWItemTagGroups.GAUNTLETS),
						2,
						3,
						Enchantment.dynamicCost(10, 15),
						Enchantment.dynamicCost(16, 15),
						3,
						EquipmentSlotGroup.HAND
				)));

		register(context,
				IWEnchantments.EXCESSIVE_FORCE,
				Enchantment.enchantment(Enchantment.definition(
						itemGetter.getOrThrow(IWItemTagGroups.GAUNTLETS),
						2,
						2,
						Enchantment.dynamicCost(20, 20),
						Enchantment.dynamicCost(21, 20),
						4,
						EquipmentSlotGroup.HAND
				)));

		register(context,
				IWEnchantments.REGENERATIVE_ASSAULT,
				Enchantment.enchantment(Enchantment.definition(
						itemGetter.getOrThrow(IWItemTagGroups.WEAPONS_AND_TOOLS),
						1,
						3,
						Enchantment.dynamicCost(20, 20),
						Enchantment.dynamicCost(25, 20),
						6,
						EquipmentSlotGroup.HAND
				)));

		register(context,
				IWEnchantments.HEAVY_COMET,
				Enchantment.enchantment(Enchantment.definition(
						itemGetter.getOrThrow(IWItemTagGroups.METEOR_STAFFS),
						2,
						2,
						Enchantment.dynamicCost(20, 20),
						Enchantment.dynamicCost(21, 20),
						4,
						EquipmentSlotGroup.HAND
				)));

		register(context,
				IWEnchantments.BURNING_HEAT,
				Enchantment.enchantment(Enchantment.definition(
						itemGetter.getOrThrow(IWItemTagGroups.METEOR_STAFFS),
						2,
						1,
						Enchantment.constantCost(25),
						Enchantment.constantCost(50),
						3,
						EquipmentSlotGroup.HAND
				)));

		register(context,
				IWEnchantments.CELESTIAL_FURY,
				Enchantment.enchantment(Enchantment.definition(
						itemGetter.getOrThrow(IWItemTagGroups.METEOR_STAFFS),
						1,
						1,
						Enchantment.constantCost(35),
						Enchantment.constantCost(50),
						5,
						EquipmentSlotGroup.HAND
				)));

		register(context,
				IWEnchantments.NIGHTMARISH_STARE,
				Enchantment.enchantment(Enchantment.definition(
						itemGetter.getOrThrow(IWItemTagGroups.CURSED_SIGHT_STAFFS),
						5,
						7,
						Enchantment.dynamicCost(10, 10),
						Enchantment.dynamicCost(15, 15),
						3,
						EquipmentSlotGroup.HAND
				)));

		register(context,
				IWEnchantments.MALEVOLENT_GAZE,
				Enchantment.enchantment(Enchantment.definition(
						itemGetter.getOrThrow(IWItemTagGroups.CURSED_SIGHT_STAFFS),
						2,
						3,
						Enchantment.dynamicCost(20, 20),
						Enchantment.dynamicCost(21, 25),
						5,
						EquipmentSlotGroup.HAND
				)));
	}

	private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
		context.register(key, builder.build(key.location()));
	}
}