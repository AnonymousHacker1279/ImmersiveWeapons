package tech.anonymoushacker1279.immersiveweapons.client.gui.overlays;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.event.game_effects.AccessoryManager;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.item.gun.AbstractGunItem;

/**
 * This class contains data for debug tracing.
 */
public class DebugTracingData {

	public static boolean isDebugTracingEnabled = false;

	public static float lastDamageDealt = 0;
	public static float lastDamageTaken = 0;

	public static float gunBaseVelocity = 0;
	public static double liveBulletDamage = 0;
	public static boolean isBulletCritical = false;

	public static double GENERAL_DAMAGE_BONUS = 0;
	public static double MELEE_DAMAGE_BONUS = 0;
	public static double PROJECTILE_DAMAGE_BONUS = 0;

	public static double ARMOR_VALUE = 0;
	public static double ARMOR_TOUGHNESS_VALUE = 0;
	public static double GENERAL_DAMAGE_RESISTANCE = 0;
	public static double KNOCKBACK_RESISTANCE = 0;

	public static float CELESTIAL_PROTECTION_NO_DAMAGE_CHANCE = 0;

	public static int TICKS_SINCE_REST = 0;

	public static void handleTracing(Player player) {
		if (player.tickCount % 20 == 0) {
			ItemStack heldItem = player.getMainHandItem();
			if (heldItem.getItem() instanceof AbstractGunItem gunItem) {
				// Round to nearest 0.1
				gunBaseVelocity = Math.round(gunItem.getBaseFireVelocity() * 10.0f) / 10.0f;
			} else {
				gunBaseVelocity = 0;
			}

			GENERAL_DAMAGE_BONUS = 0;
			MELEE_DAMAGE_BONUS = 0;
			PROJECTILE_DAMAGE_BONUS = 0;

			if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistry.STARSTORM_HELMET.get() &&
					player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistry.STARSTORM_CHESTPLATE.get() &&
					player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemRegistry.STARSTORM_LEGGINGS.get() &&
					player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemRegistry.STARSTORM_BOOTS.get()) {

				GENERAL_DAMAGE_BONUS += 0.2d;
			}
			if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistry.MOLTEN_HELMET.get() &&
					player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistry.MOLTEN_CHESTPLATE.get() &&
					player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemRegistry.MOLTEN_LEGGINGS.get() &&
					player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemRegistry.MOLTEN_BOOTS.get()) {

				double bonus = 0;
				if (player.level().dimension() == Level.NETHER) {
					bonus += 0.2d;
				}

				if (player.isInLava()) {
					bonus += 0.1d;
				}

				GENERAL_DAMAGE_BONUS += bonus;
			}

			GENERAL_DAMAGE_BONUS += AccessoryManager.collectEffects(AccessoryEffectTypeRegistry.GENERAL_DAMAGE.get(), player);
			MELEE_DAMAGE_BONUS += AccessoryManager.collectEffects(AccessoryEffectTypeRegistry.MELEE_DAMAGE.get(), player);
			PROJECTILE_DAMAGE_BONUS += AccessoryManager.collectEffects(AccessoryEffectTypeRegistry.PROJECTILE_DAMAGE.get(), player);

			ARMOR_VALUE = player.getArmorValue();
			ARMOR_TOUGHNESS_VALUE = player.getAttributeValue(Attributes.ARMOR_TOUGHNESS);
			GENERAL_DAMAGE_RESISTANCE = AccessoryManager.collectEffects(AccessoryEffectTypeRegistry.DAMAGE_RESISTANCE.get(), player);
			KNOCKBACK_RESISTANCE = player.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);

			if (player.hasEffect(EffectRegistry.CELESTIAL_PROTECTION_EFFECT)) {
				GENERAL_DAMAGE_RESISTANCE += 0.05d;

				if (!player.getPersistentData().isEmpty()) {
					CELESTIAL_PROTECTION_NO_DAMAGE_CHANCE = player.getPersistentData().getFloat("celestialProtectionChanceForNoDamage");
				}
			}
		}
	}
}