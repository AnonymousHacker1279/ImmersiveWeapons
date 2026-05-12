package tech.anonymoushacker1279.immersiveweapons.data.recipes;

import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Recipe;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.AmmunitionTableRecipe;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.AmmunitionTableRecipe.MaterialGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AmmunitionTableRecipeBuilder extends IWRecipeBuilder {

	private final List<MaterialGroup> materials = new ArrayList<>(5);
	private final ItemStackTemplate result;

	public AmmunitionTableRecipeBuilder(List<MaterialGroup> materials, ItemStackTemplate result) {
		this.materials.addAll(materials);
		this.result = result;
	}

	public static AmmunitionTableRecipeBuilder crafting(List<MaterialGroup> materials, ItemStackTemplate result) {
		return new AmmunitionTableRecipeBuilder(materials, result);
	}

	@Override
	public ResourceKey<Recipe<?>> defaultId() {
		return RecipeBuilder.getDefaultRecipeId(this.result);
	}

	@Override
	protected Recipe<?> getRecipe() {
		return new AmmunitionTableRecipe(Objects.requireNonNullElse(group, "ammunition_table"), materials, result);
	}
}