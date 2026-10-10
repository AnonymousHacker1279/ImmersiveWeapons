package tech.anonymoushacker1279.immersiveweapons.network.handler;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import tech.anonymoushacker1279.immersiveweapons.client.GunPlayerAnimations;
import tech.anonymoushacker1279.immersiveweapons.network.payload.GunAnimationPayload;

public class GunAnimationPayloadHandler {

	private static final GunAnimationPayloadHandler INSTANCE = new GunAnimationPayloadHandler();

	public static GunAnimationPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleData(final GunAnimationPayload data, final IPayloadContext context) {
		context.enqueueWork(() -> {
					if (context.player().level() instanceof ClientLevel level) {
						Entity entity = level.getEntity(data.entityID());

						if (entity instanceof Avatar avatar) {
							if (data.stop()) {
								GunPlayerAnimations.stop(avatar);
							} else {
								GunPlayerAnimations.play(avatar, data.animation(), data.speed(), data.mirrored());
							}
						}
					}
				})
				.exceptionally(e -> {
					context.disconnect(Component.translatable("immersiveweapons.networking.failure.generic", e.getMessage()));
					return null;
				});
	}
}
