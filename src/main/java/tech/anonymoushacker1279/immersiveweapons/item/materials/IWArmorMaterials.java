package tech.anonymoushacker1279.immersiveweapons.item.materials;

import net.minecraft.Util;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.neoforge.common.Tags;
import tech.anonymoushacker1279.immersiveweapons.data.IWEquipmentAssets;
import tech.anonymoushacker1279.immersiveweapons.data.groups.common.CommonItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.groups.immersiveweapons.IWItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;

import java.util.EnumMap;

public class IWArmorMaterials {

	public static final ArmorMaterial MOLTEN = new ArmorMaterial(
			39,
			Util.make(new EnumMap<>(ArmorType.class), map -> {
				map.put(ArmorType.BOOTS, 5);
				map.put(ArmorType.LEGGINGS, 6);
				map.put(ArmorType.CHESTPLATE, 9);
				map.put(ArmorType.HELMET, 4);
			}),
			15,
			SoundEventRegistry.MOLTEN_ARMOR_EQUIP,
			3.25F,
			0.12F,
			IWItemTagGroups.MOLTEN_INGOTS,
			IWEquipmentAssets.MOLTEN
	);

	public static final ArmorMaterial COPPER = new ArmorMaterial(
			13,
			Util.make(new EnumMap<>(ArmorType.class), map -> {
				map.put(ArmorType.BOOTS, 1);
				map.put(ArmorType.LEGGINGS, 4);
				map.put(ArmorType.CHESTPLATE, 5);
				map.put(ArmorType.HELMET, 1);
			}),
			9,
			SoundEventRegistry.COPPER_ARMOR_EQUIP,
			0.0F,
			0.0F,
			Tags.Items.INGOTS_COPPER,
			IWEquipmentAssets.COPPER
	);

	public static final ArmorMaterial TESLA = new ArmorMaterial(
			42,
			Util.make(new EnumMap<>(ArmorType.class), map -> {
				map.put(ArmorType.BOOTS, 7);
				map.put(ArmorType.LEGGINGS, 8);
				map.put(ArmorType.CHESTPLATE, 11);
				map.put(ArmorType.HELMET, 6);
			}),
			20,
			SoundEventRegistry.TESLA_ARMOR_EQUIP,
			3.5F,
			0.05F,
			IWItemTagGroups.TESLA_INGOTS,
			IWEquipmentAssets.TESLA
	);

	public static final ArmorMaterial COBALT = new ArmorMaterial(
			19,
			Util.make(new EnumMap<>(ArmorType.class), map -> {
				map.put(ArmorType.BOOTS, 3);
				map.put(ArmorType.LEGGINGS, 5);
				map.put(ArmorType.CHESTPLATE, 6);
				map.put(ArmorType.HELMET, 3);
			}),
			10,
			SoundEventRegistry.COBALT_ARMOR_EQUIP,
			0.0F,
			0.0F,
			CommonItemTagGroups.COBALT_INGOTS,
			IWEquipmentAssets.COBALT
	);

	public static final ArmorMaterial VENTUS = new ArmorMaterial(
			39,
			Util.make(new EnumMap<>(ArmorType.class), map -> {
				map.put(ArmorType.BOOTS, 5);
				map.put(ArmorType.LEGGINGS, 6);
				map.put(ArmorType.CHESTPLATE, 9);
				map.put(ArmorType.HELMET, 5);
			}),
			14,
			SoundEventRegistry.VENTUS_ARMOR_EQUIP,
			2.75F,
			0.02F,
			IWItemTagGroups.VENTUS_SHARDS,
			IWEquipmentAssets.VENTUS
	);

	public static final ArmorMaterial ASTRAL = new ArmorMaterial(
			25,
			Util.make(new EnumMap<>(ArmorType.class), map -> {
				map.put(ArmorType.BOOTS, 4);
				map.put(ArmorType.LEGGINGS, 5);
				map.put(ArmorType.CHESTPLATE, 6);
				map.put(ArmorType.HELMET, 4);
			}),
			22,
			SoundEventRegistry.ASTRAL_ARMOR_EQUIP,
			1.8F,
			0.0F,
			IWItemTagGroups.ASTRAL_INGOTS,
			IWEquipmentAssets.ASTRAL
	);

	public static final ArmorMaterial STARSTORM = new ArmorMaterial(
			32,
			Util.make(new EnumMap<>(ArmorType.class), map -> {
				map.put(ArmorType.BOOTS, 5);
				map.put(ArmorType.LEGGINGS, 5);
				map.put(ArmorType.CHESTPLATE, 6);
				map.put(ArmorType.HELMET, 4);
			}),
			15,
			SoundEventRegistry.STARSTORM_ARMOR_EQUIP,
			2.2F,
			0.0F,
			IWItemTagGroups.STARSTORM_INGOTS,
			IWEquipmentAssets.STARSTORM
	);

	public static final ArmorMaterial PADDED_LEATHER = new ArmorMaterial(
			6,
			Util.make(new EnumMap<>(ArmorType.class), map -> {
				map.put(ArmorType.BOOTS, 2);
				map.put(ArmorType.LEGGINGS, 2);
				map.put(ArmorType.CHESTPLATE, 3);
				map.put(ArmorType.HELMET, 2);
			}),
			15,
			SoundEvents.ARMOR_EQUIP_LEATHER,
			0.0F,
			0.0F,
			Tags.Items.LEATHERS,
			IWEquipmentAssets.PADDED_LEATHER
	);

	public static final ArmorMaterial VOID = new ArmorMaterial(
			35,
			Util.make(new EnumMap<>(ArmorType.class), map -> {
				map.put(ArmorType.BOOTS, 6);
				map.put(ArmorType.LEGGINGS, 7);
				map.put(ArmorType.CHESTPLATE, 10);
				map.put(ArmorType.HELMET, 6);
			}),
			24,
			SoundEventRegistry.VOID_ARMOR_EQUIP,
			2.3F,
			0.01F,
			IWItemTagGroups.VOID_INGOTS,
			IWEquipmentAssets.VOID
	);
}