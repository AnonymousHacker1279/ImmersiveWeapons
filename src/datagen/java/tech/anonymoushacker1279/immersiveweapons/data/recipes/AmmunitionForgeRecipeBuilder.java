package tech.anonymoushacker1279.immersiveweapons.data.recipes;

import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.AmmunitionForgeRecipe;

import java.util.Objects;

public class AmmunitionForgeRecipeBuilder extends IWRecipeBuilder {

	private final Ingredient material;
	private final Ingredient mold;
	private final ItemStackTemplate result;
	private final int cookingTime;

	public AmmunitionForgeRecipeBuilder(Ingredient material, Ingredient mold, ItemStackTemplate result, int cookingTime) {
		this.material = material;
		this.mold = mold;
		this.result = result;
		this.cookingTime = cookingTime;
	}

	public static AmmunitionForgeRecipeBuilder forging(Ingredient material, Ingredient mold, ItemStackTemplate result, int cookingTime) {
		return new AmmunitionForgeRecipeBuilder(material, mold, result, cookingTime);
	}

	@Override
	public ResourceKey<Recipe<?>> defaultId() {
		return RecipeBuilder.getDefaultRecipeId(result);
	}

	@Override
	protected Recipe<?> getRecipe() {
		return new AmmunitionForgeRecipe(Objects.requireNonNullElse(group, "ammunition_forge"), material, mold, result, cookingTime);
	}
}
