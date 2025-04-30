package tech.anonymoushacker1279.immersiveweapons.network.handler.star_forge;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.StarForgeRecipe;
import tech.anonymoushacker1279.immersiveweapons.menu.StarForgeMenu;
import tech.anonymoushacker1279.immersiveweapons.network.payload.star_forge.StarForgeUpdateRecipesPayload;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StarForgeUpdateRecipesPayloadHandler {

	private static final StarForgeUpdateRecipesPayloadHandler INSTANCE = new StarForgeUpdateRecipesPayloadHandler();

	public static StarForgeUpdateRecipesPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleData(final StarForgeUpdateRecipesPayload data, final IPayloadContext context) {
		context.enqueueWork(() -> {
					Player player = context.player();
					int containerId = data.containerId();
					List<ResourceKey<Recipe<?>>> recipeIds = data.recipeIds();

					if (player.containerMenu instanceof StarForgeMenu menu && menu.containerId == containerId && player.level() instanceof ServerLevel serverLevel) {
						RecipeManager recipeManager = serverLevel.recipeAccess();
						menu.availableRecipes = recipeIds.stream()
								.map(recipeManager::byKey)
								.filter(Optional::isPresent)
								.map(Optional::get)
								.map(holder -> {
									if (holder.value() instanceof StarForgeRecipe recipe) {
										return recipe;
									}
									return null;
								})
								.collect(Collectors.toList());
					}
				})
				.exceptionally(e -> {
					context.disconnect(Component.translatable("immersiveweapons.networking.failure.generic", e.getMessage()));
					return null;
				});
	}
}