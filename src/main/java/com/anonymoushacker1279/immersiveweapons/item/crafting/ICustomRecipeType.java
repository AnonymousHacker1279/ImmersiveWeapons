package com.anonymoushacker1279.immersiveweapons.item.crafting;

import java.util.Optional;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public interface ICustomRecipeType<T extends IRecipe<?>> {
	IRecipeType<SmallPartsRecipe> SMALL_PARTS = register(ImmersiveWeapons.MOD_ID + ":small_parts");

	static <T extends IRecipe<?>> IRecipeType<T> register(final String key) {
		return Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(key), new IRecipeType<T>() {
			@Override
			public String toString() {
				return key;
			}
		});
	}

	default <C extends IInventory> Optional<T> matches(IRecipe<C> recipe, World worldIn, C inv) {
		return recipe.matches(inv, worldIn) ? Optional.of((T)recipe) : Optional.empty();
	}
}