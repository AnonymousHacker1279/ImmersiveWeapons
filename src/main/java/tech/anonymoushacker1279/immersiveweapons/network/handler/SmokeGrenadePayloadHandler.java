package tech.anonymoushacker1279.immersiveweapons.network.handler;

import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.SmokeGrenadeEntity;
import tech.anonymoushacker1279.immersiveweapons.network.payload.SmokeGrenadePayload;

public class SmokeGrenadePayloadHandler {

	private static final SmokeGrenadePayloadHandler INSTANCE = new SmokeGrenadePayloadHandler();

	public static SmokeGrenadePayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleData(final SmokeGrenadePayload data, final PlayPayloadContext context) {
		context.workHandler().submitAsync(() -> {
					if (context.level().isPresent()) {
						SmokeGrenadeEntity.runOnClientImpact(data.x(), data.y(), data.z(), data.color(), context.level().get(), data.forcedParticleCount());
					}
				})
				.exceptionally(e -> {
					context.packetHandler().disconnect(Component.translatable("immersiveweapons.networking.failure.generic", e.getMessage()));
					return null;
				});
	}
}