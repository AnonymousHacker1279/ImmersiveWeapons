package tech.anonymoushacker1279.immersiveweapons.network.handler;

import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import tech.anonymoushacker1279.immersiveweapons.event.SyncHandler;
import tech.anonymoushacker1279.immersiveweapons.network.payload.SyncPlayerDataPayload;

public class SyncPlayerDataPayloadHandler {

	private static final SyncPlayerDataPayloadHandler INSTANCE = new SyncPlayerDataPayloadHandler();

	public static SyncPlayerDataPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleData(final SyncPlayerDataPayload data, final IPayloadContext context) {
		context.enqueueWork(() -> SyncHandler.syncPersistentData(data.tag(), data.playerUUID()))
				.exceptionally(e -> {
					context.disconnect(Component.translatable("immersiveweapons.networking.failure.generic", e.getMessage()));
					return null;
				});
	}
}