package tech.anonymoushacker1279.immersiveweapons.data.recipes;

import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.PistonCrushingRecipe;

public class PistonCrushingRecipeBuilder extends IWRecipeBuilder {

	private final Ingredient block;
	private final ItemStackTemplate result;
	private final int minCount;
	private final int maxCount;

	public PistonCrushingRecipeBuilder(Ingredient block, ItemStackTemplate result, int minCount, int maxCount) {
		this.block = block;
		this.result = result;
		this.minCount = minCount;
		this.maxCount = maxCount;
	}

	public static PistonCrushingRecipeBuilder crushing(Ingredient block, ItemStackTemplate result, int minCount, int maxCount) {
		return new PistonCrushingRecipeBuilder(block, result, minCount, maxCount);
	}

	@Override
	public ResourceKey<Recipe<?>> defaultId() {
		return RecipeBuilder.getDefaultRecipeId(this.result);
	}

	@Override
	protected Recipe<?> getRecipe() {
		return new PistonCrushingRecipe(block, result, minCount, maxCount);
	}
}