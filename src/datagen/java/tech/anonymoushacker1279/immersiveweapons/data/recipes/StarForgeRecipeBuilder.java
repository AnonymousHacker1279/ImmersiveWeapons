package tech.anonymoushacker1279.immersiveweapons.data.recipes;

import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.StarForgeRecipe;

import java.util.Objects;
import java.util.Optional;

public class StarForgeRecipeBuilder extends IWRecipeBuilder {

	private final Ingredient ingot;
	private final int ingotCount;
	@Nullable
	private final Ingredient secondaryMaterial;
	private final int secondaryMaterialCount;
	private final ItemStackTemplate result;
	private final int smeltTime;

	public StarForgeRecipeBuilder(Ingredient ingot, int ingotCount, @Nullable Ingredient secondaryMaterial, int secondaryMaterialCount, ItemStackTemplate result, int smeltTime) {
		this.ingot = ingot;
		this.ingotCount = ingotCount;
		this.secondaryMaterial = secondaryMaterial;
		this.secondaryMaterialCount = secondaryMaterialCount;
		this.result = result;
		this.smeltTime = smeltTime;
	}

	public static StarForgeRecipeBuilder forge(Ingredient ingot, int ingotCount, Ingredient secondaryMaterial, int secondaryMaterialCount, ItemStackTemplate result, int smeltTime) {
		return new StarForgeRecipeBuilder(ingot, ingotCount, secondaryMaterial, secondaryMaterialCount, result, smeltTime);
	}

	public static StarForgeRecipeBuilder forge(Ingredient ingot, int ingotCount, ItemStackTemplate result, int smeltTime) {
		return new StarForgeRecipeBuilder(ingot, ingotCount, null, 0, result, smeltTime);
	}

	@Override
	public ResourceKey<Recipe<?>> defaultId() {
		return RecipeBuilder.getDefaultRecipeId(this.result);
	}

	@Override
	protected Recipe<?> getRecipe() {
		return new StarForgeRecipe(Objects.requireNonNullElse(group, "star_forge"), ingot, ingotCount, Optional.ofNullable(secondaryMaterial), secondaryMaterialCount, smeltTime, result);
	}
}