package tech.anonymoushacker1279.immersiveweapons.network.handler;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import tech.anonymoushacker1279.immersiveweapons.item.gun.AbstractGunItem;
import tech.anonymoushacker1279.immersiveweapons.item.gun.GunAimTracker;
import tech.anonymoushacker1279.immersiveweapons.network.payload.GunAimPayload;

public class GunAimPayloadHandler {

	private static final GunAimPayloadHandler INSTANCE = new GunAimPayloadHandler();

	public static GunAimPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleData(final GunAimPayload data, final IPayloadContext context) {
		context.enqueueWork(() -> {
					if (context.player() instanceof ServerPlayer player) {
						GunAimTracker.setAiming(player, data.aiming());
					}
				})
				.exceptionally(e -> {
					context.disconnect(Component.translatable("immersiveweapons.networking.failure.generic", e.getMessage()));
					return null;
				});
	}
}
