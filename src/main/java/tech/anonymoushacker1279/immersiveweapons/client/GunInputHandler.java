package tech.anonymoushacker1279.immersiveweapons.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.CalculatePlayerTurnEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.init.DataComponentTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.gun.AbstractGunItem;
import tech.anonymoushacker1279.immersiveweapons.network.payload.GunAimPayload;
import tech.anonymoushacker1279.immersiveweapons.network.payload.GunTriggerPayload;

/// Client input for firearms: the fire key fires or reloads, and the aim key aims. The vanilla attack and use keys
/// are suppressed while a firearm is in hand.
@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, value = Dist.CLIENT)
public class GunInputHandler {

	private static boolean previousFireDown;
	private static boolean aimSent;
	private static boolean overridingUseKey;

	@SubscribeEvent
	public static void onClientTick(ClientTickEvent.Pre event) {
		Minecraft minecraft = Minecraft.getInstance();
		LocalPlayer player = minecraft.player;

		InteractionHand hand = player != null ? AbstractGunItem.getGunHand(player) : null;
		boolean active = hand != null && minecraft.gui.screen() == null && minecraft.mouseHandler.isMouseGrabbed();

		boolean fireDown = active && IWKeyBinds.FIRE.isDown();
		boolean aimKeyDown = active && IWKeyBinds.AIM.isDown();

		if (active) {
			ItemStack gun = player.getItemInHand(hand);

			// Fire if loaded, otherwise start reloading. The client predicts (recoil, pose); the server decides.
			if (fireDown && !previousFireDown && gun.getItem() instanceof AbstractGunItem gunItem) {
				gunItem.triggerPressed(player.level(), player, hand);
				ClientPacketDistributor.sendToServer(new GunTriggerPayload());
			}

			// Stop vanilla from attacking or breaking blocks
			minecraft.options.keyAttack.setDown(false);

			// A reload is held through the fire key, but vanilla stops using an item when the use key is released.
			// Present the use key as held for as long as the reload is. This must be checked after the press above,
			// which may have just started the reload.
			minecraft.options.keyUse.setDown(fireDown && isReloading(player));
			overridingUseKey = true;
		} else if (overridingUseKey) {
			// Hand the use key back to the real mouse state
			minecraft.options.keyUse.setDown(minecraft.mouseHandler.isRightPressed());
			overridingUseKey = false;
		}

		previousFireDown = fireDown;

		// Can't aim while reloading
		boolean aimDown = aimKeyDown && !isReloading(player);
		if (aimDown != aimSent) {
			aimSent = aimDown;
			GunAimClient.setAiming(aimDown);
			ClientPacketDistributor.sendToServer(new GunAimPayload(aimDown));
		}

		GunAimClient.tick();
	}

	private static boolean isReloading(LocalPlayer player) {
		return player.isUsingItem() && player.getUseItem().getItem() instanceof AbstractGunItem;
	}

	/// Cancel vanilla attack and use interactions while a firearm is in the interacting hand.
	@SubscribeEvent
	public static void onInteraction(InputEvent.InteractionKeyMappingTriggered event) {
		Player player = Minecraft.getInstance().player;

		if (player != null && (event.isAttack() || event.isUseItem())
				&& player.getItemInHand(event.getHand()).getItem() instanceof AbstractGunItem) {
			event.setCanceled(true);
			event.setSwingHand(false);
		}
	}

	/// Slower mouse turning while aiming, more so when scoped.
	@SubscribeEvent
	public static void onCalculateTurn(CalculatePlayerTurnEvent event) {
		Minecraft minecraft = Minecraft.getInstance();
		LocalPlayer player = minecraft.player;
		InteractionHand hand = player != null ? AbstractGunItem.getGunHand(player) : null;

		float progress = GunAimClient.getProgress(1.0f);
		if (hand != null && progress > 0.0f) {
			boolean scoped = player.getItemInHand(hand).has(DataComponentTypeRegistry.SCOPE.get());
			float slowdown = scoped ? 0.3f : 0.7f;

			event.setMouseSensitivity(event.getMouseSensitivity() * (1.0f - progress * (1.0f - slowdown)));
		}
	}
}
