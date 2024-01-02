package tech.anonymoushacker1279.immersiveweapons.network.handler;

import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import tech.anonymoushacker1279.immersiveweapons.event.SyncHandler;
import tech.anonymoushacker1279.immersiveweapons.network.payload.DebugDataPayload;

public class DebugDataPayloadHandler {

	private static final DebugDataPayloadHandler INSTANCE = new DebugDataPayloadHandler();

	public static DebugDataPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleData(final DebugDataPayload data, final PlayPayloadContext context) {
		context.workHandler().submitAsync(() -> {
					SyncHandler.debugDataHandler(data.lastDamageDealt(), data.lastDamageTaken(), data.ticksSinceRest(), data.playerUUID());
				})
				.exceptionally(e -> {
					context.packetHandler().disconnect(Component.translatable("immersiveweapons.networking.failure.generic", e.getMessage()));
					return null;
				});
	}
}