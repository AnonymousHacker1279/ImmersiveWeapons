package com.anonymoushacker1279.immersiveweapons.item.crafting;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public interface ICustomRecipeType {
	IRecipeType<SmallPartsRecipe> SMALL_PARTS = register(ImmersiveWeapons.MOD_ID + ":small_parts");
	IRecipeType<TeslaSynthesizerRecipe> TESLA_SYNTHESIZER = register(ImmersiveWeapons.MOD_ID + ":tesla_synthesizer");

	/**
	 * Register recipe types.
	 * @param key the registry key
	 * @param <T> <code>IRecipeType</code> extending IRecipe
	 * @return T extending IRecipe
	 */
	static <T extends IRecipe<?>> IRecipeType<T> register(String key) {
		return Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(key), new IRecipeType<T>() {
			@Override
			public String toString() {
				return key;
			}
		});
	}

}