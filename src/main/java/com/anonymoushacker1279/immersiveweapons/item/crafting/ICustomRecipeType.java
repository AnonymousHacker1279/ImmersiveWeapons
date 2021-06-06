package com.anonymoushacker1279.immersiveweapons.item.crafting;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public interface ICustomRecipeType<T extends IRecipe<?>> {
	IRecipeType<SmallPartsRecipe> SMALL_PARTS = register(ImmersiveWeapons.MOD_ID + ":small_parts");
	IRecipeType<TeslaSynthesizerRecipe> TESLA_SYNTHESIZER = register(ImmersiveWeapons.MOD_ID + ":tesla_synthesizer");


	static <T extends IRecipe<?>> IRecipeType<T> register(final String key) {
		return Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(key), new IRecipeType<T>() {
			@Override
			public String toString() {
				return key;
			}
		});
	}

}