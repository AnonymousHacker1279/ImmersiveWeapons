package tech.anonymoushacker1279.immersiveweapons.network.handler;

import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import tech.anonymoushacker1279.immersiveweapons.client.gui.overlays.DebugTracingData;
import tech.anonymoushacker1279.immersiveweapons.network.payload.BulletEntityDebugPayload;

public class BulletEntityDebugPayloadHandler {

	private static final BulletEntityDebugPayloadHandler INSTANCE = new BulletEntityDebugPayloadHandler();

	public static BulletEntityDebugPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleData(final BulletEntityDebugPayload data, final IPayloadContext context) {
		context.enqueueWork(() -> {
					DebugTracingData.liveBulletDamage = data.liveBulletDamage();
					DebugTracingData.isBulletCritical = data.isBulletCritical();
				})
				.exceptionally(e -> {
					context.disconnect(Component.translatable("immersiveweapons.networking.failure.generic", e.getMessage()));
					return null;
				});
	}
}