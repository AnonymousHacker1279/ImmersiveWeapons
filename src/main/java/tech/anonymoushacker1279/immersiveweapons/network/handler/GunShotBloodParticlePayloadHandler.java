package tech.anonymoushacker1279.immersiveweapons.network.handler;

import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;
import tech.anonymoushacker1279.immersiveweapons.network.payload.GunShotBloodParticlePayload;

public class GunShotBloodParticlePayloadHandler {

	private static final GunShotBloodParticlePayloadHandler INSTANCE = new GunShotBloodParticlePayloadHandler();

	public static GunShotBloodParticlePayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleData(final GunShotBloodParticlePayload data, final IPayloadContext context) {
		context.enqueueWork(() -> {
					for (int i = 0; i <= IWConfigs.CLIENT.gunShotBloodParticles.getAsInt(); i++) {
						Level level = context.player().level();
						level.addParticle(
								data.particleOptions(),
								data.x(), data.y(), data.z(),
								(0.03d * level.random.nextGaussian()),
								(0.015d * level.random.nextGaussian()),
								(0.03d * level.random.nextGaussian())
						);
					}
				})
				.exceptionally(e -> {
					context.disconnect(Component.translatable("immersiveweapons.networking.failure.generic", e.getMessage()));
					return null;
				});
	}
}