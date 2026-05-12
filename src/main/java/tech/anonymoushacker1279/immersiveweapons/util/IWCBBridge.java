package tech.anonymoushacker1279.immersiveweapons.util;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.AccessoryEffectType;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.scaling.AttributeOperation;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.scaling.DynamicAttributeOperationInstance;

import java.util.List;

/**
 * This class is used to access IWCB methods. It is in a separate class to avoid classloading issues when IWCB is not
 * present. Do <b>NOT</b> load this class without checking if IWCB is loaded first.
 */
public class IWCBBridge {

	// TODO: reimplement after IWCB updates
	public static double collectEffects(AccessoryEffectType type, Player player) {
		return 0.0d;
		// return AccessoryBridge.collectEffects(type, player);
	}

	public static List<AttributeOperation> collectStandardAttributes(Player player) {
		return List.of();
		// return AccessoryBridge.collectStandardAttributes(player);
	}

	public static List<DynamicAttributeOperationInstance> collectDynamicAttributes(Player player) {
		return List.of();
		// return AccessoryBridge.collectDynamicAttributes(player);
	}

	public static List<MobEffectInstance> collectMobEffects(Player player) {
		return List.of();
		// return AccessoryBridge.collectMobEffects(player);
	}

	public static boolean isAccessoryActive(Player player, ItemStack stack) {
		return false;
		// return AccessoryBridge.isAccessoryActive(player, stack);
	}
}