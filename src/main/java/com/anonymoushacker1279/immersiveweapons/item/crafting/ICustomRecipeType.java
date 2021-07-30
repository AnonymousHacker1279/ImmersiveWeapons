package com.anonymoushacker1279.immersiveweapons.item.crafting;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public interface ICustomRecipeType {
	RecipeType<SmallPartsRecipe> SMALL_PARTS = register(ImmersiveWeapons.MOD_ID + ":small_parts");
	RecipeType<TeslaSynthesizerRecipe> TESLA_SYNTHESIZER = register(ImmersiveWeapons.MOD_ID + ":tesla_synthesizer");

	/**
	 * Register recipe types.
	 * @param key the registry key
	 * @param <T> <code>IRecipeType</code> extending IRecipe
	 * @return T extending IRecipe
	 */
	static <T extends Recipe<?>> RecipeType<T> register(String key) {
		return Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(key), new RecipeType<T>() {
			@Override
			public String toString() {
				return key;
			}
		});
	}

}