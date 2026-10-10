package tech.anonymoushacker1279.immersiveweapons.item.gun;

import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/// Tracks which players are aiming a firearm (server-side). Aiming reduces shot spread and movement speed.
@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID)
public class GunAimTracker {

	private static final Set<UUID> AIMING = ConcurrentHashMap.newKeySet();
	private static final Identifier SPEED_MODIFIER_ID = Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "gun_aiming");
	private static final double SPEED_PENALTY = -0.35d;

	public static boolean isAiming(Player player) {
		return AIMING.contains(player.getUUID());
	}

	public static void setAiming(ServerPlayer player, boolean aiming) {
		if (aiming && (AbstractGunItem.getGunHand(player) == null || isReloading(player))) {
			aiming = false;
		}

		if (aiming) {
			AIMING.add(player.getUUID());
		} else {
			AIMING.remove(player.getUUID());
		}

		AttributeInstance speed = player.getAttribute(Attributes.MOVEMENT_SPEED);
		if (speed != null) {
			speed.removeModifier(SPEED_MODIFIER_ID);

			if (aiming) {
				speed.addTransientModifier(new AttributeModifier(SPEED_MODIFIER_ID, SPEED_PENALTY, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
			}
		}
	}

	private static boolean isReloading(Player player) {
		return player.isUsingItem() && player.getUseItem().getItem() instanceof AbstractGunItem;
	}

	/// Stop aiming if the player is no longer holding a firearm, or starts reloading.
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		if (event.getEntity() instanceof ServerPlayer player && isAiming(player)
				&& (AbstractGunItem.getGunHand(player) == null || isReloading(player))) {
			setAiming(player, false);
		}
	}

	@SubscribeEvent
	public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			setAiming(player, false);
		}
	}
}
