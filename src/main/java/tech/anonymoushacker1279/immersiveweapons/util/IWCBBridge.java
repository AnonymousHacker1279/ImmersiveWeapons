package tech.anonymoushacker1279.immersiveweapons.util;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem.EffectType;
import tech.anonymoushacker1279.iwcompatbridge.plugin.curios.AccessoryBridge;

import java.util.List;
import java.util.Map;

/**
 * This class is used to access IWCB methods. It is in a separate class to avoid classloading issues when IWCB is not
 * present. Do <b>NOT</b> load this class without checking if IWCB is loaded first.
 */
public class IWCBBridge {

	public static double collectEffects(EffectType type, Player player) {
		return AccessoryBridge.collectEffects(type, player);
	}

	public static Map<AttributeModifier, Attribute> collectStandardAttributes(Player player) {
		return AccessoryBridge.collectStandardAttributes(player);
	}

	public static Map<Map<AttributeModifier, Attribute>, Double> collectDynamicAttributes(Player player) {
		return AccessoryBridge.collectDynamicAttributes(player);
	}

	public static List<MobEffectInstance> collectMobEffects(Player player) {
		return AccessoryBridge.collectMobEffects(player);
	}

	public static boolean isAccessoryActive(Player player, AccessoryItem item) {
		return AccessoryBridge.isAccessoryActive(player, item);
	}
}