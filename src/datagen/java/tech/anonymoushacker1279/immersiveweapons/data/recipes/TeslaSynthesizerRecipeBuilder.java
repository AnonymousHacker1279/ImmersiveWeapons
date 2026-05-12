package tech.anonymoushacker1279.immersiveweapons.data.recipes;

import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.TeslaSynthesizerRecipe;

import java.util.Objects;

public class TeslaSynthesizerRecipeBuilder extends IWRecipeBuilder {

	private final Ingredient material1;
	private final Ingredient material2;
	private final Ingredient material3;
	private final int cookTime;
	private final ItemStackTemplate result;

	public TeslaSynthesizerRecipeBuilder(Ingredient material1, Ingredient material2, Ingredient material3, int cookTime, ItemStackTemplate result) {
		this.material1 = material1;
		this.material2 = material2;
		this.material3 = material3;
		this.cookTime = cookTime;
		this.result = result;
	}

	public static TeslaSynthesizerRecipeBuilder synthesizing(Ingredient material1, Ingredient material2, Ingredient material3, int cookTime, ItemStackTemplate result) {
		return new TeslaSynthesizerRecipeBuilder(material1, material2, material3, cookTime, result);
	}

	@Override
	public ResourceKey<Recipe<?>> defaultId() {
		return RecipeBuilder.getDefaultRecipeId(this.result);
	}

	@Override
	protected Recipe<?> getRecipe() {
		return new TeslaSynthesizerRecipe(Objects.requireNonNullElse(group, "tesla_synthesizer"), material1, material2, material3, result, cookTime);
	}
}