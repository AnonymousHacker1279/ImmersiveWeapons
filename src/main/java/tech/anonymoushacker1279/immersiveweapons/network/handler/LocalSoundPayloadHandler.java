package tech.anonymoushacker1279.immersiveweapons.network.handler;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.network.payload.LocalSoundPayload;

public class LocalSoundPayloadHandler {

	private static final LocalSoundPayloadHandler INSTANCE = new LocalSoundPayloadHandler();

	public static LocalSoundPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleData(final LocalSoundPayload data, final PlayPayloadContext context) {
		context.workHandler().submitAsync(() -> {
					if (context.level().isPresent()) {
						SoundEvent soundEvent = BuiltInRegistries.SOUND_EVENT.get(data.soundLocation());

						if (soundEvent != null) {
							context.level().get().playLocalSound(data.pos(), soundEvent, data.source(), data.volume(), data.pitch(), data.distanceDelay());
						} else {
							ImmersiveWeapons.LOGGER.error("Tried playing a sound that doesn't exist: {}", data.soundLocation());
						}
					}
				})
				.exceptionally(e -> {
					context.packetHandler().disconnect(Component.translatable("immersiveweapons.networking.failure.generic", e.getMessage()));
					return null;
				});
	}
}