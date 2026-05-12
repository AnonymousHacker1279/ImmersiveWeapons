package tech.anonymoushacker1279.immersiveweapons.data.recipes;

import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.AstralCrystalRecipe;

import java.util.Objects;

public class AstralCrystalRecipeBuilder extends IWRecipeBuilder {

	private final Ingredient material;
	private final Ingredient catalyst;
	private final ItemStackTemplate result;

	public AstralCrystalRecipeBuilder(Ingredient material, Ingredient catalyst, ItemStackTemplate result) {
		this.material = material;
		this.catalyst = catalyst;
		this.result = result;
	}

	public static AstralCrystalRecipeBuilder sorcery(Ingredient material, Ingredient catalyst, ItemStackTemplate result) {
		return new AstralCrystalRecipeBuilder(material, catalyst, result);
	}

	@Override
	public ResourceKey<Recipe<?>> defaultId() {
		return RecipeBuilder.getDefaultRecipeId(this.result);
	}

	@Override
	protected Recipe<?> getRecipe() {
		return new AstralCrystalRecipe(Objects.requireNonNullElse(group, "astral_crystal"), material, catalyst, result);
	}
}