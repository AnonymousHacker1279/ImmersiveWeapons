package tech.anonymoushacker1279.immersiveweapons.data.recipes;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.StarForgeRecipe;

import java.util.Objects;

public class StarForgeRecipeBuilder extends IWRecipeBuilder {

	private final Ingredient ingot;
	private final int ingotCount;
	private final Ingredient secondaryMaterial;
	private final int secondaryMaterialCount;
	private final Item result;
	private final int smeltTime;
	private final StarForgeRecipe.Factory<?> factory;

	public StarForgeRecipeBuilder(StarForgeRecipe.Factory<?> factory, Ingredient ingot, int ingotCount, Ingredient secondaryMaterial, int secondaryMaterialCount, Item result, int smeltTime) {
		this.ingot = ingot;
		this.ingotCount = ingotCount;
		this.secondaryMaterial = secondaryMaterial;
		this.secondaryMaterialCount = secondaryMaterialCount;
		this.result = result;
		this.smeltTime = smeltTime;
		this.factory = factory;
	}

	public static StarForgeRecipeBuilder forge(Ingredient ingot, int ingotCount, Ingredient secondaryMaterial, int secondaryMaterialCount, Item result, int smeltTime) {
		return new StarForgeRecipeBuilder(StarForgeRecipe::new, ingot, ingotCount, secondaryMaterial, secondaryMaterialCount, result, smeltTime);
	}

	@Override
	public Item getResult() {
		return result;
	}

	@Override
	protected Recipe<?> getRecipe() {
		return factory.create(Objects.requireNonNullElse(group, "star_forge"), ingot, ingotCount, secondaryMaterial, secondaryMaterialCount, smeltTime, result.getDefaultInstance());
	}
}