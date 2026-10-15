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
import tech.anonymoushacker1279.immersiveweapons.init.AttachmentTypeRegistry;

/// Tracks which players are aiming a firearm. The server owns the state, which is synced to everyone tracking the player
/// through an attachment. Aiming reduces shot spread and movement speed.
@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID)
public class GunAimTracker {

	private static final Identifier SPEED_MODIFIER_ID = Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "gun_aiming");
	private static final double SPEED_PENALTY = -0.35d;

	public static boolean isAiming(Player player) {
		return player.getData(AttachmentTypeRegistry.GUN_AIMING);
	}

	public static void setAiming(ServerPlayer player, boolean aiming) {
		if (aiming && (AbstractGunItem.getGunHand(player) == null || isReloading(player) || player.isSprinting())) {
			aiming = false;
		}

		if (isAiming(player) != aiming) {
			player.setData(AttachmentTypeRegistry.GUN_AIMING, aiming);
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

	/// Stop aiming if the player is no longer holding a firearm, starts reloading, or sprints. Sprinting also cancels
	/// a reload in progress.
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		if (!(event.getEntity() instanceof ServerPlayer player)) {
			return;
		}

		if (player.isSprinting() && isReloading(player)) {
			player.stopUsingItem();
		}

		if (isAiming(player) && (AbstractGunItem.getGunHand(player) == null || isReloading(player) || player.isSprinting())) {
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
