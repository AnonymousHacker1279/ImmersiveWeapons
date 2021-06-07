package com.anonymoushacker1279.immersiveweapons.client.integration.jei;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.client.gui.screen.SmallPartsTableScreen;
import com.anonymoushacker1279.immersiveweapons.client.gui.screen.TeslaSynthesizerScreen;
import com.anonymoushacker1279.immersiveweapons.client.integration.jei.recipe_category.SmallPartsRecipeCategory;
import com.anonymoushacker1279.immersiveweapons.client.integration.jei.recipe_category.TeslaSynthesizerRecipeCategory;
import com.anonymoushacker1279.immersiveweapons.container.SmallPartsContainer;
import com.anonymoushacker1279.immersiveweapons.container.TeslaSynthesizerContainer;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.item.crafting.ICustomRecipeType;
import com.anonymoushacker1279.immersiveweapons.item.crafting.SmallPartsRecipe;
import com.anonymoushacker1279.immersiveweapons.item.crafting.TeslaSynthesizerRecipe;
import com.google.common.collect.ImmutableSet;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;
import java.util.Set;

@JeiPlugin
public class ImmersiveWeaponsJEIPlugin implements IModPlugin {

	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(ImmersiveWeapons.MOD_ID, "jei_plugin");
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(DeferredRegistryHandler.SMALL_PARTS_TABLE.get()), SmallPartsRecipeCategory.UID);
		registration.addRecipeCatalyst(new ItemStack(DeferredRegistryHandler.TESLA_SYNTHESIZER.get()), TeslaSynthesizerRecipeCategory.UID);
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		Minecraft minecraft = Minecraft.getInstance();
		ClientWorld world = Objects.requireNonNull(minecraft.level);

		Set<SmallPartsRecipe> smallPartsRecipes = ImmutableSet.copyOf(world.getRecipeManager().getAllRecipesFor(ICustomRecipeType.SMALL_PARTS));
		Set<TeslaSynthesizerRecipe> teslaSynthesizerRecipes = ImmutableSet.copyOf(world.getRecipeManager().getAllRecipesFor(ICustomRecipeType.TESLA_SYNTHESIZER));
		registration.addRecipes(smallPartsRecipes, SmallPartsRecipeCategory.UID);
		registration.addRecipes(teslaSynthesizerRecipes, TeslaSynthesizerRecipeCategory.UID);
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		registration.addRecipeCategories(new SmallPartsRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
		registration.addRecipeCategories(new TeslaSynthesizerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
		registration.addRecipeTransferHandler(SmallPartsContainer.class, SmallPartsRecipeCategory.UID, 0, 2, 0, 35);
		registration.addRecipeTransferHandler(TeslaSynthesizerContainer.class, TeslaSynthesizerRecipeCategory.UID, 0, 3, 0, 35);
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		registration.addRecipeClickArea(SmallPartsTableScreen.class, 103, 48, 25, 15, SmallPartsRecipeCategory.UID);
		registration.addRecipeClickArea(TeslaSynthesizerScreen.class, 107, 16, 25, 15, TeslaSynthesizerRecipeCategory.UID);
	}
}