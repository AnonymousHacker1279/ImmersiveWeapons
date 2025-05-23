package tech.anonymoushacker1279.immersiveweapons.network.handler.star_forge;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.StarForgeRecipe;
import tech.anonymoushacker1279.immersiveweapons.menu.StarForgeMenu;
import tech.anonymoushacker1279.immersiveweapons.network.payload.star_forge.StarForgeUpdateRecipesPayload;

import java.util.ArrayList;
import java.util.List;

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
						if (data.recipes().getFirst().getType().equals(RecipeTypeRegistry.STAR_FORGE_RECIPE_TYPE.get())) {
							List<StarForgeRecipe> recipes = new ArrayList<>(data.recipes().size());
							for (Recipe<?> recipe : data.recipes()) {
								recipes.add((StarForgeRecipe) recipe);
							}
							menu.availableRecipes.addAll(recipes);
						}
					}
				})
				.exceptionally(e -> {
					context.disconnect(Component.translatable("immersiveweapons.networking.failure.generic", e.getMessage()));
					return null;
				});
	}
}