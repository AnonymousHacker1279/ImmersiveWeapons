package tech.anonymoushacker1279.immersiveweapons.network.handler;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import tech.anonymoushacker1279.immersiveweapons.network.payload.PlayerSoundPayload;

public class PlayerSoundPayloadHandler {

	private static final PlayerSoundPayloadHandler INSTANCE = new PlayerSoundPayloadHandler();

	public static PlayerSoundPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleData(final PlayerSoundPayload data, final IPayloadContext context) {
		context.enqueueWork(() -> {
					SoundEvent soundEvent = BuiltInRegistries.SOUND_EVENT.getValueOrThrow(data.soundKey());
					context.player().playSound(soundEvent, data.volume(), data.pitch());
				})
				.exceptionally(e -> {
					context.disconnect(Component.translatable("immersiveweapons.networking.failure.generic", e.getMessage()));
					return null;
				});
	}
}