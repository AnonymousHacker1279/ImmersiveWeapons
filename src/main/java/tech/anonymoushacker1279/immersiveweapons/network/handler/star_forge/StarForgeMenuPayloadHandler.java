package tech.anonymoushacker1279.immersiveweapons.network.handler.star_forge;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import tech.anonymoushacker1279.immersiveweapons.menu.StarForgeMenu;
import tech.anonymoushacker1279.immersiveweapons.network.payload.star_forge.StarForgeMenuPayload;

public class StarForgeMenuPayloadHandler {

	private static final StarForgeMenuPayloadHandler INSTANCE = new StarForgeMenuPayloadHandler();

	public static StarForgeMenuPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleData(final StarForgeMenuPayload data, final PlayPayloadContext context) {
		context.workHandler().submitAsync(() -> {
					if (context.player().isPresent() && context.player().get() instanceof ServerPlayer serverPlayer) {
						StarForgeMenu.updateServer(serverPlayer, data.containerId(), data.menuSelectionIndex(), data.beginCrafting());
					}
				})
				.exceptionally(e -> {
					context.packetHandler().disconnect(Component.translatable("immersiveweapons.networking.failure.generic", e.getMessage()));
					return null;
				});
	}
}