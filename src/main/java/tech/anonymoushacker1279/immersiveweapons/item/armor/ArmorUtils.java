package tech.anonymoushacker1279.immersiveweapons.item.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

public class ArmorUtils {

	public static boolean isWearingCobaltArmor(LivingEntity livingEntity) {
		return livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistry.COBALT_HELMET.get() &&
				livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistry.COBALT_CHESTPLATE.get() &&
				livingEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemRegistry.COBALT_LEGGINGS.get() &&
				livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemRegistry.COBALT_BOOTS.get();
	}

	public static boolean isWearingMoltenArmor(LivingEntity livingEntity) {
		return livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistry.MOLTEN_HELMET.get() &&
				livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistry.MOLTEN_CHESTPLATE.get() &&
				livingEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemRegistry.MOLTEN_LEGGINGS.get() &&
				livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemRegistry.MOLTEN_BOOTS.get();
	}

	public static boolean isWearingTeslaArmor(LivingEntity livingEntity) {
		return livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistry.TESLA_HELMET.get() &&
				livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistry.TESLA_CHESTPLATE.get() &&
				livingEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemRegistry.TESLA_LEGGINGS.get() &&
				livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemRegistry.TESLA_BOOTS.get();
	}

	public static boolean isWearingVentusArmor(LivingEntity livingEntity) {
		return livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistry.VENTUS_HELMET.get() &&
				livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistry.VENTUS_CHESTPLATE.get() &&
				livingEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemRegistry.VENTUS_LEGGINGS.get() &&
				livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemRegistry.VENTUS_BOOTS.get();
	}

	public static boolean isWearingAstralArmor(LivingEntity livingEntity) {
		return livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistry.ASTRAL_HELMET.get() &&
				livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistry.ASTRAL_CHESTPLATE.get() &&
				livingEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemRegistry.ASTRAL_LEGGINGS.get() &&
				livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemRegistry.ASTRAL_BOOTS.get();
	}

	public static boolean isWearingStarstormArmor(LivingEntity livingEntity) {
		return livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistry.STARSTORM_HELMET.get() &&
				livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistry.STARSTORM_CHESTPLATE.get() &&
				livingEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemRegistry.STARSTORM_LEGGINGS.get() &&
				livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemRegistry.STARSTORM_BOOTS.get();
	}

	public static boolean isWearingVoidArmor(LivingEntity livingEntity) {
		return livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistry.VOID_HELMET.get() &&
				livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistry.VOID_CHESTPLATE.get() &&
				livingEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemRegistry.VOID_LEGGINGS.get() &&
				livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemRegistry.VOID_BOOTS.get();
	}
}