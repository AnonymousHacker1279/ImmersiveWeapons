package tech.anonymoushacker1279.immersiveweapons.network.handler;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import tech.anonymoushacker1279.immersiveweapons.network.payload.CobaltArmorPayload;

public class CobaltArmorPayloadHandler {

	private static final CobaltArmorPayloadHandler INSTANCE = new CobaltArmorPayloadHandler();

	public static CobaltArmorPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleData(final CobaltArmorPayload data, final IPayloadContext context) {
		context.enqueueWork(() -> {
					if (context.player() instanceof ServerPlayer serverPlayer) {
						serverPlayer.getPersistentData().putBoolean("CobaltArmorEffectEnabled", data.state());
					}
				})
				.exceptionally(e -> {
					context.disconnect(Component.translatable("immersiveweapons.networking.failure.generic", e.getMessage()));
					return null;
				});
	}
}