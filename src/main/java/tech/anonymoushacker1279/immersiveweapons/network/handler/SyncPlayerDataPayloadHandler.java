package tech.anonymoushacker1279.immersiveweapons.network.handler;

import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import tech.anonymoushacker1279.immersiveweapons.event.SyncHandler;
import tech.anonymoushacker1279.immersiveweapons.network.payload.SyncPlayerDataPayload;

public class SyncPlayerDataPayloadHandler {

	private static final SyncPlayerDataPayloadHandler INSTANCE = new SyncPlayerDataPayloadHandler();

	public static SyncPlayerDataPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleData(final SyncPlayerDataPayload data, final PlayPayloadContext context) {
		context.workHandler().submitAsync(() -> {
					SyncHandler.syncPersistentData(data.tag(), data.playerUUID());
				})
				.exceptionally(e -> {
					context.packetHandler().disconnect(Component.translatable("immersiveweapons.networking.failure.generic", e.getMessage()));
					return null;
				});
	}
}