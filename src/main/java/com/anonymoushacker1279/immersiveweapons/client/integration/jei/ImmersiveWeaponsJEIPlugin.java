package com.anonymoushacker1279.immersiveweapons.client.integration.jei;

import java.util.Objects;
import java.util.Set;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.client.gui.screen.SmallPartsTableScreen;
import com.anonymoushacker1279.immersiveweapons.client.integration.jei.small_parts.SmallPartsRecipeCategory;
import com.anonymoushacker1279.immersiveweapons.container.SmallPartsContainer;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.item.crafting.ICustomRecipeType;
import com.anonymoushacker1279.immersiveweapons.item.crafting.SmallPartsRecipe;
import com.google.common.collect.ImmutableSet;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class ImmersiveWeaponsJEIPlugin implements IModPlugin {

	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(ImmersiveWeapons.MOD_ID, "jei_plugin");
	}
	
	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(DeferredRegistryHandler.SMALL_PARTS_TABLE.get()), SmallPartsRecipeCategory.UID);
	}
	
	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		Minecraft minecraft = Minecraft.getInstance();
		ClientWorld world = Objects.requireNonNull(minecraft.world);
		
		Set<SmallPartsRecipe> smallPartsRecipes = ImmutableSet.copyOf(world.getRecipeManager().getRecipesForType(ICustomRecipeType.SMALL_PARTS));
		registration.addRecipes(smallPartsRecipes, SmallPartsRecipeCategory.UID);
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		registration.addRecipeCategories(new SmallPartsRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
	}
	
	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
		registration.addRecipeTransferHandler(SmallPartsContainer.class, SmallPartsRecipeCategory.UID, 0, 2, 0, 35);
	}
	
	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		registration.addRecipeClickArea(SmallPartsTableScreen.class, 103, 48, 25, 15, SmallPartsRecipeCategory.UID);
	}
}
