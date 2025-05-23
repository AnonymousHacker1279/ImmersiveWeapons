package tech.anonymoushacker1279.immersiveweapons.data.recipes;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.PistonCrushingRecipe;

import java.util.Objects;

public class PistonCrushingRecipeBuilder extends IWRecipeBuilder {

	private final Ingredient block;
	private final Item result;
	private final int minCount;
	private final int maxCount;
	private final PistonCrushingRecipe.Factory<?> factory;

	public PistonCrushingRecipeBuilder(PistonCrushingRecipe.Factory<?> factory, Ingredient block, Item result, int minCount, int maxCount) {
		this.block = block;
		this.result = result;
		this.minCount = minCount;
		this.maxCount = maxCount;
		this.factory = factory;
	}

	public static PistonCrushingRecipeBuilder crushing(Ingredient block, Item result, int minCount, int maxCount) {
		return new PistonCrushingRecipeBuilder(PistonCrushingRecipe::new, block, result, minCount, maxCount);
	}

	@Override
	public Item getResult() {
		return result;
	}

	@Override
	protected Recipe<?> getRecipe() {
		return factory.create(Objects.requireNonNullElse(group, "piston_crushing"), block, result.getDefaultInstance(), minCount, maxCount);
	}
}