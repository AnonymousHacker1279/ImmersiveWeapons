package tech.anonymoushacker1279.immersiveweapons.util;

import net.minecraft.world.entity.player.Player;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem.EffectType;

/**
 * This class is used to access IWCB methods. It is in a separate class to avoid classloading issues when IWCB is not
 * present. Do <b>NOT</b> load this class without checking if IWCB is loaded first.
 */
public class IWCBBridge {

	// TODO: uncomment when IWCB is updated

	public static double collectEffects(EffectType type, Player player) {
		// return AccessoryBridge.collectEffects(type, player);
		return 0;
	}

	public static boolean isAccessoryActive(Player player, AccessoryItem item) {
		// return AccessoryBridge.isAccessoryActive(player, item);
		return false;
	}
}