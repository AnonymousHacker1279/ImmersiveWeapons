package tech.anonymoushacker1279.immersiveweapons.network.handler;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import tech.anonymoushacker1279.immersiveweapons.menu.AmmunitionTableMenu;
import tech.anonymoushacker1279.immersiveweapons.network.payload.AmmunitionTablePayload;

public class AmmunitionTablePayloadHandler {

	private static final AmmunitionTablePayloadHandler INSTANCE = new AmmunitionTablePayloadHandler();

	public static AmmunitionTablePayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleData(final AmmunitionTablePayload data, final IPayloadContext context) {
		context.enqueueWork(() -> {
					if (context.player() instanceof ServerPlayer serverPlayer) {
						AmmunitionTableMenu.setDensityModifierOnServer(serverPlayer, data.containerId(), data.densityModifier());
					}
				})
				.exceptionally(e -> {
					context.disconnect(Component.translatable("immersiveweapons.networking.failure.generic", e.getMessage()));
					return null;
				});
	}
}