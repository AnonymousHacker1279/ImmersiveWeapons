package tech.anonymoushacker1279.immersiveweapons.network.handler;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import tech.anonymoushacker1279.immersiveweapons.network.payload.TeslaArmorPayload;

public class TeslaArmorPayloadHandler {

	private static final TeslaArmorPayloadHandler INSTANCE = new TeslaArmorPayloadHandler();

	public static TeslaArmorPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleData(final TeslaArmorPayload data, final PlayPayloadContext context) {
		context.workHandler().submitAsync(() -> {
					if (context.player().isPresent() && context.player().get() instanceof ServerPlayer serverPlayer) {
						serverPlayer.getPersistentData().putString("TeslaArmorEffectState", data.state().getSerializedName());
					}
				})
				.exceptionally(e -> {
					context.packetHandler().disconnect(Component.translatable("immersiveweapons.networking.failure.generic", e.getMessage()));
					return null;
				});
	}
}