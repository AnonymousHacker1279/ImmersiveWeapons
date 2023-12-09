package tech.anonymoushacker1279.immersiveweapons.data.recipes;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Block;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.PistonCrushingRecipe;

import java.util.Objects;

public class PistonCrushingRecipeBuilder extends IWRecipeBuilder {

	private final Block block;
	private final Item result;
	private final int minCount;
	private final int maxCount;
	private final PistonCrushingRecipe.Factory<?> factory;

	public PistonCrushingRecipeBuilder(PistonCrushingRecipe.Factory<?> factory, Block block, Item result, int minCount, int maxCount) {
		this.block = block;
		this.result = result;
		this.minCount = minCount;
		this.maxCount = maxCount;
		this.factory = factory;
	}

	public static PistonCrushingRecipeBuilder crushing(Block block, Item result, int minCount, int maxCount) {
		return new PistonCrushingRecipeBuilder(PistonCrushingRecipe::new, block, result, minCount, maxCount);
	}

	@Override
	public Item getResult() {
		return result;
	}

	@Override
	protected Recipe<?> getRecipe() {
		return factory.create(Objects.requireNonNullElse(group, "piston_crushing"), BuiltInRegistries.BLOCK.getKey(block), result.getDefaultInstance(), minCount, maxCount);
	}
}