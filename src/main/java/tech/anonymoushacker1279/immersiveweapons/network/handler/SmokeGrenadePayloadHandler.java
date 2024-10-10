package tech.anonymoushacker1279.immersiveweapons.network.handler;

import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.SmokeGrenadeEntity;
import tech.anonymoushacker1279.immersiveweapons.network.payload.SmokeGrenadePayload;

public class SmokeGrenadePayloadHandler {

	private static final SmokeGrenadePayloadHandler INSTANCE = new SmokeGrenadePayloadHandler();

	public static SmokeGrenadePayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleData(final SmokeGrenadePayload data, final IPayloadContext context) {
		context.enqueueWork(() -> SmokeGrenadeEntity.runOnClientImpact(data.x(), data.y(), data.z(), data.color(), context.player().level(), data.forcedParticleCount()))
				.exceptionally(e -> {
					context.disconnect(Component.translatable("immersiveweapons.networking.failure.generic", e.getMessage()));
					return null;
				});
	}
}