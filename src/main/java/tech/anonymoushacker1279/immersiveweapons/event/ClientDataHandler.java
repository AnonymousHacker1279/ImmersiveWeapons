package tech.anonymoushacker1279.immersiveweapons.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;
import java.util.UUID;

public class ClientDataHandler {

	/**
	 * Sync persistent data from server to client. This is in a separate class to avoid loading client classes on the
	 * dedicated server.
	 *
	 * @param tag        the <code>CompoundTag</code> to sync, should be from {@link Player#getPersistentData()}
	 * @param playerUUID the <code>UUID</code> of the player to sync to
	 */
	public static void syncPersistentData(CompoundTag tag, UUID playerUUID) {
		Player player = Minecraft.getInstance().player;
		if (player != null && player.getUUID().equals(playerUUID)) {
			// Add the server tags to the player
			for (String key : tag.keySet()) {
				if (tag.get(key) != null) {
					player.getPersistentData().put(key, Objects.requireNonNull(tag.get(key)));
				}
			}
		}
	}

	public static int getTicksSinceRest() {
		LocalPlayer player = Minecraft.getInstance().player;

		if (player == null) {
			return 0;
		}

		if (player.tickCount % 20 == 0) {
			updateStats();
		}

		return player.getStats().getValue(Stats.CUSTOM, Stats.TIME_SINCE_REST);
	}

	private static void updateStats() {
		ClientPacketListener connection = Minecraft.getInstance().getConnection();
		if (connection != null) {
			connection.send(new ServerboundClientCommandPacket(ServerboundClientCommandPacket.Action.REQUEST_STATS));
		}
	}
}