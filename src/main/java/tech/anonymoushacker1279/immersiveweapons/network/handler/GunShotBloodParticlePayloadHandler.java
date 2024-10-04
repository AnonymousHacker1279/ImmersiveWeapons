package tech.anonymoushacker1279.immersiveweapons.network.handler;

import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;
import tech.anonymoushacker1279.immersiveweapons.network.payload.GunShotBloodParticlePayload;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class GunShotBloodParticlePayloadHandler {

	private static final GunShotBloodParticlePayloadHandler INSTANCE = new GunShotBloodParticlePayloadHandler();

	public static GunShotBloodParticlePayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleData(final GunShotBloodParticlePayload data, final IPayloadContext context) {
		context.enqueueWork(() -> {
					for (int i = 0; i <= IWConfigs.CLIENT.gunShotBloodParticles.getAsInt(); i++) {
						context.player().level().addParticle(
								data.particleOptions(),
								data.x(), data.y(), data.z(),
								GeneralUtilities.getRandomNumber(-0.03d, 0.03d),
								GeneralUtilities.getRandomNumber(-0.03d, 0.03d),
								GeneralUtilities.getRandomNumber(-0.03d, 0.03d)
						);
					}
				})
				.exceptionally(e -> {
					context.disconnect(Component.translatable("immersiveweapons.networking.failure.generic", e.getMessage()));
					return null;
				});
	}
}