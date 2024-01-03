package tech.anonymoushacker1279.immersiveweapons.data.recipes;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.BarrelTapRecipe;

import java.util.Objects;

public class BarrelTapRecipeBuilder extends tech.anonymoushacker1279.immersiveweapons.data.recipes.IWRecipeBuilder {

	private final Ingredient material;
	private final int materialCount;
	private final Item result;
	private final BarrelTapRecipe.Factory<?> factory;

	public BarrelTapRecipeBuilder(BarrelTapRecipe.Factory<?> factory, Ingredient material, int materialCount, Item result) {
		this.material = material;
		this.materialCount = materialCount;
		this.result = result;
		this.factory = factory;
	}

	public static BarrelTapRecipeBuilder fermenting(Ingredient block, int materialCount, Item result) {
		return new BarrelTapRecipeBuilder(BarrelTapRecipe::new, block, materialCount, result);
	}

	@Override
	public Item getResult() {
		return result;
	}

	@Override
	protected Recipe<?> getRecipe() {
		return factory.create(Objects.requireNonNullElse(group, "barrel_tap"), material, materialCount, result.getDefaultInstance());
	}
}