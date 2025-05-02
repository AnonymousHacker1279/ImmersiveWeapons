package tech.anonymoushacker1279.immersiveweapons.network.handler.star_forge;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import tech.anonymoushacker1279.immersiveweapons.menu.StarForgeMenu;
import tech.anonymoushacker1279.immersiveweapons.network.payload.star_forge.StarForgeUpdateRecipesPayload;

public class StarForgeUpdateRecipesPayloadHandler {

	private static final StarForgeUpdateRecipesPayloadHandler INSTANCE = new StarForgeUpdateRecipesPayloadHandler();

	public static StarForgeUpdateRecipesPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleData(final StarForgeUpdateRecipesPayload data, final IPayloadContext context) {
		context.enqueueWork(() -> {
					Player player = context.player();
					int containerId = data.containerId();

					if (player.containerMenu instanceof StarForgeMenu menu && menu.containerId == containerId) {
						// TODO: fix recipe sync
						// menu.availableRecipes.addAll(data.recipes());
					}
				})
				.exceptionally(e -> {
					context.disconnect(Component.translatable("immersiveweapons.networking.failure.generic", e.getMessage()));
					return null;
				});
	}
}