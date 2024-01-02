package tech.anonymoushacker1279.immersiveweapons.network.handler;

import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import tech.anonymoushacker1279.immersiveweapons.item.gun.data.GunData;
import tech.anonymoushacker1279.immersiveweapons.network.payload.GunScopePayload;

public class GunScopePayloadHandler {

	private static final GunScopePayloadHandler INSTANCE = new GunScopePayloadHandler();

	public static GunScopePayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleData(final GunScopePayload data, final PlayPayloadContext context) {
		context.workHandler().submitAsync(() -> {
					GunData.playerFOV = data.playerFOV();
					GunData.changingPlayerFOV = data.changingPlayerFOV();
				})
				.exceptionally(e -> {
					context.packetHandler().disconnect(Component.translatable("immersiveweapons.networking.failure.generic", e.getMessage()));
					return null;
				});
	}
}