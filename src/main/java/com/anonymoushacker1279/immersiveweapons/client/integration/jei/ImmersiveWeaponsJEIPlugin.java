package com.anonymoushacker1279.immersiveweapons.client.integration.jei;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.client.gui.screen.SmallPartsTableScreen;
import com.anonymoushacker1279.immersiveweapons.client.gui.screen.TeslaSynthesizerScreen;
import com.anonymoushacker1279.immersiveweapons.client.integration.jei.category.SmallPartsRecipeCategory;
import com.anonymoushacker1279.immersiveweapons.client.integration.jei.category.TeslaSynthesizerRecipeCategory;
import com.anonymoushacker1279.immersiveweapons.container.SmallPartsContainer;
import com.anonymoushacker1279.immersiveweapons.container.TeslaSynthesizerContainer;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.item.crafting.*;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.fml.util.thread.EffectiveSide;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
@JeiPlugin
public class ImmersiveWeaponsJEIPlugin implements IModPlugin {

	public static final RecipeType<SmallPartsRecipe> SMALL_PARTS =
			RecipeType.create(ImmersiveWeapons.MOD_ID, "small_parts", SmallPartsRecipe.class);
	public static final RecipeType<TeslaSynthesizerRecipe> TESLA_SYNTHESIZER =
			RecipeType.create(ImmersiveWeapons.MOD_ID, "tesla_synthesizer", TeslaSynthesizerRecipe.class);

	/**
	 * Get the plugin UID.
	 *
	 * @return ResourceLocation
	 */
	@Override
	public @NotNull ResourceLocation getPluginUid() {
		return new ResourceLocation(ImmersiveWeapons.MOD_ID, "jei_plugin");
	}

	/**
	 * Register recipe catalysts.
	 *
	 * @param registration an <code>IRecipeCatalystRegistration</code> instance
	 */
	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(DeferredRegistryHandler.SMALL_PARTS_TABLE.get()), SMALL_PARTS);
		registration.addRecipeCatalyst(new ItemStack(DeferredRegistryHandler.TESLA_SYNTHESIZER.get()), TESLA_SYNTHESIZER);
	}

	/**
	 * Register recipes.
	 *
	 * @param registration an <code>IRecipeRegistration</code> instance
	 */
	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		List<SmallPartsRecipe> smallPartsRecipes = Objects.requireNonNull(getRecipeManager())
				.getAllRecipesFor(CustomRecipeTypes.SMALL_PARTS);
		List<TeslaSynthesizerRecipe> teslaSynthesizerRecipes = Objects.requireNonNull(getRecipeManager())
				.getAllRecipesFor(CustomRecipeTypes.TESLA_SYNTHESIZER);

		registration.addRecipes(SMALL_PARTS, smallPartsRecipes);
		registration.addRecipes(TESLA_SYNTHESIZER, teslaSynthesizerRecipes);
	}

	/**
	 * Register recipe categories.
	 *
	 * @param registration an <code>IRecipeCategoryRegistration</code> instance
	 */
	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		registration.addRecipeCategories(new SmallPartsRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
		registration.addRecipeCategories(new TeslaSynthesizerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
	}

	/**
	 * Register recipe transfer handlers.
	 *
	 * @param registration an <code>IRecipeTransferRegistration</code> instance
	 */
	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
		registration.addRecipeTransferHandler(SmallPartsContainer.class, SMALL_PARTS,
				0, 2, 0, 35);
		registration.addRecipeTransferHandler(TeslaSynthesizerContainer.class, TESLA_SYNTHESIZER,
				0, 3, 0, 35);
	}

	/**
	 * Register GUI handlers.
	 *
	 * @param registration an <code>IGuiHandlerRegistration</code> instance
	 */
	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		registration.addRecipeClickArea(SmallPartsTableScreen.class, 103, 48, 25, 15, SMALL_PARTS);
		registration.addRecipeClickArea(TeslaSynthesizerScreen.class, 107, 16, 25, 15, TESLA_SYNTHESIZER);
	}

	private static RecipeManager getRecipeManager() {
		try {
			if (EffectiveSide.get().isClient()) {
				if (Minecraft.getInstance().player != null) {
					return Minecraft.getInstance().player.connection.getRecipeManager();
				}
				return null;
			} else {
				return ServerLifecycleHooks.getCurrentServer().getRecipeManager();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}