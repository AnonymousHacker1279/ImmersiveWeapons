package tech.anonymoushacker1279.immersiveweapons.util;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.AccessoryEffectType;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.scaling.AttributeOperation;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.scaling.DynamicAttributeOperationInstance;
import tech.anonymoushacker1279.iwcompatbridge.plugin.curios.AccessoryBridge;

import java.util.List;

/**
 * This class is used to access IWCB methods. It is in a separate class to avoid classloading issues when IWCB is not
 * present. Do <b>NOT</b> load this class without checking if IWCB is loaded first.
 */
public class IWCBBridge {

	public static double collectEffects(AccessoryEffectType type, Player player) {
		return AccessoryBridge.collectEffects(type, player);
	}

	public static List<AttributeOperation> collectStandardAttributes(Player player) {
		return AccessoryBridge.collectStandardAttributes(player);
	}

	public static List<DynamicAttributeOperationInstance> collectDynamicAttributes(Player player) {
		return AccessoryBridge.collectDynamicAttributes(player);
	}

	public static List<MobEffectInstance> collectMobEffects(Player player) {
		return AccessoryBridge.collectMobEffects(player);
	}

	public static boolean isAccessoryActive(Player player, ItemStack stack) {
		return AccessoryBridge.isAccessoryActive(player, stack);
	}
}