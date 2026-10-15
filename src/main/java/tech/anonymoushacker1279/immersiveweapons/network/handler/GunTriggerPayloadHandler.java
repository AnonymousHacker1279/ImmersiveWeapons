package tech.anonymoushacker1279.immersiveweapons.network.handler;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import tech.anonymoushacker1279.immersiveweapons.item.gun.AbstractGunItem;
import tech.anonymoushacker1279.immersiveweapons.item.gun.GunAimTracker;
import tech.anonymoushacker1279.immersiveweapons.network.payload.GunTriggerPayload;

public class GunTriggerPayloadHandler {

	private static final GunTriggerPayloadHandler INSTANCE = new GunTriggerPayloadHandler();

	public static GunTriggerPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleData(final GunTriggerPayload data, final IPayloadContext context) {
		context.enqueueWork(() -> {
					if (context.player() instanceof ServerPlayer player) {
						InteractionHand hand = AbstractGunItem.getGunHand(player);

						if (hand != null && player.getItemInHand(hand).getItem() instanceof AbstractGunItem gun) {
							gun.triggerPressed(player.level(), player, hand);
						}
					}
				})
				.exceptionally(e -> {
					context.disconnect(Component.translatable("immersiveweapons.networking.failure.generic", e.getMessage()));
					return null;
				});
	}
}
