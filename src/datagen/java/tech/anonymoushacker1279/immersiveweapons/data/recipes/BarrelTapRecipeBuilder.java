package tech.anonymoushacker1279.immersiveweapons.data.recipes;

import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.BarrelTapRecipe;

public class BarrelTapRecipeBuilder extends IWRecipeBuilder {

	private final Ingredient material;
	private final int materialCount;
	private final ItemStackTemplate result;

	public BarrelTapRecipeBuilder(Ingredient material, int materialCount, ItemStackTemplate result) {
		this.material = material;
		this.materialCount = materialCount;
		this.result = result;
	}

	public static BarrelTapRecipeBuilder fermenting(Ingredient block, int materialCount, ItemStackTemplate result) {
		return new BarrelTapRecipeBuilder(block, materialCount, result);
	}

	@Override
	public ResourceKey<Recipe<?>> defaultId() {
		return RecipeBuilder.getDefaultRecipeId(this.result);
	}

	@Override
	protected Recipe<?> getRecipe() {
		return new BarrelTapRecipe(material, materialCount, result);
	}
}