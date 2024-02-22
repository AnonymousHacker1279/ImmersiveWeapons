package tech.anonymoushacker1279.immersiveweapons.network.handler;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.network.payload.PlayerSoundPayload;

public class PlayerSoundPayloadHandler {

	private static final PlayerSoundPayloadHandler INSTANCE = new PlayerSoundPayloadHandler();

	public static PlayerSoundPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleData(final PlayerSoundPayload data, final PlayPayloadContext context) {
		context.workHandler().submitAsync(() -> {
					if (context.player().isPresent()) {
						SoundEvent soundEvent = BuiltInRegistries.SOUND_EVENT.get(data.soundLocation());

						if (soundEvent != null) {
							context.player().get().playSound(soundEvent, data.volume(), data.pitch());
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