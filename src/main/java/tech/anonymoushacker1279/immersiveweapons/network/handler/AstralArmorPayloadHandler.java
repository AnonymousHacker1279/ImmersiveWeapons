package tech.anonymoushacker1279.immersiveweapons.network.handler;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import tech.anonymoushacker1279.immersiveweapons.network.payload.AstralArmorPayload;

public class AstralArmorPayloadHandler {

	private static final AstralArmorPayloadHandler INSTANCE = new AstralArmorPayloadHandler();

	public static AstralArmorPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleData(final AstralArmorPayload data, final IPayloadContext context) {
		context.enqueueWork(() -> {
					if (context.player() instanceof ServerPlayer serverPlayer) {
						serverPlayer.getPersistentData().putBoolean("AstralArmorEffectEnabled", data.state());
					}
				})
				.exceptionally(e -> {
					context.disconnect(Component.translatable("immersiveweapons.networking.failure.generic", e.getMessage()));
					return null;
				});
	}
}