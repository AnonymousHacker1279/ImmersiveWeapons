package tech.anonymoushacker1279.immersiveweapons.client.integration.pmmo;

import harmonised.pmmo.api.APIUtils;
import net.minecraft.server.level.ServerPlayer;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class PMMOPlugin {

	public static void awardSmallPartsTableCraftXP(ServerPlayer player) {
		APIUtils.awardXpTrigger(player.getUUID(), ImmersiveWeapons.MOD_ID + ".small_parts_table.craft", null, true, false);
	}
}