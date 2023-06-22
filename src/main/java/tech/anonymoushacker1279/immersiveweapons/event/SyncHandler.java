package tech.anonymoushacker1279.immersiveweapons.event;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import tech.anonymoushacker1279.immersiveweapons.client.gui.overlays.DebugTracingData;

import java.util.Objects;
import java.util.UUID;

public class SyncHandler {

	/**
	 * Sync persistent data from server to client. This is in a separate class to avoid loading client classes
	 * on the dedicated server.
	 *
	 * @param tag        the <code>CompoundTag</code> to sync, should be from {@link Player#getPersistentData()}
	 * @param playerUUID the <code>UUID</code> of the player to sync to
	 */
	public static void syncPersistentData(CompoundTag tag, UUID playerUUID) {
		Player player = Minecraft.getInstance().player;
		if (player != null && player.getUUID().equals(playerUUID)) {
			// Add the server tags to the player
			for (String key : tag.getAllKeys()) {
				if (tag.get(key) != null) {
					player.getPersistentData().put(key, Objects.requireNonNull(tag.get(key)));
				}
			}
		}
	}

	/**
	 * Sync damage values for debug tracing data from server to client. This is in a separate class to avoid loading
	 * client classes on the dedicated server.
	 *
	 * @param lastDamageDealt the damage dealt
	 * @param lastDamageTaken the damage taken
	 * @param playerUUID      the <code>UUID</code> of the player to sync to
	 */
	public static void debugDataHandler(float lastDamageDealt, float lastDamageTaken, UUID playerUUID) {
		Player player = Minecraft.getInstance().player;
		if (player != null && player.getUUID().equals(playerUUID)) {
			if (lastDamageDealt >= 0) {
				DebugTracingData.lastDamageDealt = lastDamageDealt;
			}
			if (lastDamageTaken >= 0) {
				DebugTracingData.lastDamageTaken = lastDamageTaken;
			}
		}
	}
}