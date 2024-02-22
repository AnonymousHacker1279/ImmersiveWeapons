package tech.anonymoushacker1279.immersiveweapons.data.recipes;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.TeslaSynthesizerRecipe;

import java.util.Objects;

public class TeslaSynthesizerRecipeBuilder extends IWRecipeBuilder {

	private final Ingredient block;
	private final Ingredient material1;
	private final Ingredient material2;
	private final int cookTime;
	private final Item result;
	private final TeslaSynthesizerRecipe.Factory<?> factory;

	public TeslaSynthesizerRecipeBuilder(TeslaSynthesizerRecipe.Factory<?> factory, Ingredient block, Ingredient material1, Ingredient material2, int cookTime, Item result) {
		this.block = block;
		this.material1 = material1;
		this.material2 = material2;
		this.cookTime = cookTime;
		this.result = result;
		this.factory = factory;
	}

	public static TeslaSynthesizerRecipeBuilder synthesizing(Ingredient block, Ingredient material1, Ingredient material2, int cookTime, Item pResult) {
		return new TeslaSynthesizerRecipeBuilder(TeslaSynthesizerRecipe::new, block, material1, material2, cookTime, pResult);
	}

	@Override
	public Item getResult() {
		return result;
	}

	@Override
	protected Recipe<?> getRecipe() {
		return factory.create(Objects.requireNonNullElse(group, "tesla_synthesizer"), block, material1, material2, result.getDefaultInstance(), cookTime);
	}
}