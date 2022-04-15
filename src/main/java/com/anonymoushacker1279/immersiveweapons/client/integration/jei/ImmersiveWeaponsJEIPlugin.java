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
import com.google.common.collect.ImmutableSet;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;

@SuppressWarnings("unused")
@JeiPlugin
public class ImmersiveWeaponsJEIPlugin implements IModPlugin {

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
		registration.addRecipeCatalyst(new ItemStack(DeferredRegistryHandler.SMALL_PARTS_TABLE.get()), SmallPartsRecipeCategory.UID);
		registration.addRecipeCatalyst(new ItemStack(DeferredRegistryHandler.TESLA_SYNTHESIZER.get()), TeslaSynthesizerRecipeCategory.UID);
	}

	/**
	 * Register recipes.
	 *
	 * @param registration an <code>IRecipeRegistration</code> instance
	 */
	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		ClientLevel world = Objects.requireNonNull(Minecraft.getInstance().level);

		Set<SmallPartsRecipe> smallPartsRecipes = ImmutableSet.copyOf(world.getRecipeManager().getAllRecipesFor(CustomRecipeTypes.SMALL_PARTS));
		Set<TeslaSynthesizerRecipe> teslaSynthesizerRecipes = ImmutableSet.copyOf(world.getRecipeManager().getAllRecipesFor(CustomRecipeTypes.TESLA_SYNTHESIZER));
		registration.addRecipes(smallPartsRecipes, SmallPartsRecipeCategory.UID);
		registration.addRecipes(teslaSynthesizerRecipes, TeslaSynthesizerRecipeCategory.UID);
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
		registration.addRecipeTransferHandler(SmallPartsContainer.class, SmallPartsRecipeCategory.UID, 0, 2, 0, 35);
		registration.addRecipeTransferHandler(TeslaSynthesizerContainer.class, TeslaSynthesizerRecipeCategory.UID, 0, 3, 0, 35);
	}

	/**
	 * Register GUI handlers.
	 *
	 * @param registration an <code>IGuiHandlerRegistration</code> instance
	 */
	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		registration.addRecipeClickArea(SmallPartsTableScreen.class, 103, 48, 25, 15, SmallPartsRecipeCategory.UID);
		registration.addRecipeClickArea(TeslaSynthesizerScreen.class, 107, 16, 25, 15, TeslaSynthesizerRecipeCategory.UID);
	}
}