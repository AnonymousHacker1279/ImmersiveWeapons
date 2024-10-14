package tech.anonymoushacker1279.immersiveweapons.network.handler;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import tech.anonymoushacker1279.immersiveweapons.network.payload.ArrowGravityPayload;
import tech.anonymoushacker1279.immersiveweapons.util.ArrowAttributeAccessor;

public class ArrowGravityPayloadHandler {

	private static final ArrowGravityPayloadHandler INSTANCE = new ArrowGravityPayloadHandler();

	public static ArrowGravityPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleData(final ArrowGravityPayload data, final IPayloadContext context) {
		context.enqueueWork(() -> {
					if (context.player().level() instanceof ClientLevel level) {
						Entity entity = level.getEntity(data.entityID());

						if (entity instanceof AbstractArrow arrow) {
							((ArrowAttributeAccessor) arrow).immersiveWeapons$setGravity(data.gravity());
						}
					}
				})
				.exceptionally(e -> {
					context.disconnect(Component.translatable("immersiveweapons.networking.failure.generic", e.getMessage()));
					return null;
				});
	}
}