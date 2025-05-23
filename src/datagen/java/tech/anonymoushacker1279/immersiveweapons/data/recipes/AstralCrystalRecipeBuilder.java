package tech.anonymoushacker1279.immersiveweapons.data.recipes;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.AstralCrystalRecipe;

import java.util.Objects;

public class AstralCrystalRecipeBuilder extends IWRecipeBuilder {

	private final Ingredient material;
	private final Ingredient catalyst;
	private final ItemStack result;
	private final AstralCrystalRecipe.Factory<?> factory;

	public AstralCrystalRecipeBuilder(AstralCrystalRecipe.Factory<?> factory, Ingredient material, Ingredient catalyst, ItemStack result) {
		this.material = material;
		this.catalyst = catalyst;
		this.result = result;
		this.factory = factory;
	}

	public static AstralCrystalRecipeBuilder sorcery(Ingredient material, Ingredient catalyst, ItemStack result) {
		return new AstralCrystalRecipeBuilder(AstralCrystalRecipe::new, material, catalyst, result);
	}

	@Override
	public Item getResult() {
		return result.getItem();
	}

	@Override
	protected Recipe<?> getRecipe() {
		return factory.create(Objects.requireNonNullElse(group, "astral_crystal"), material, catalyst, result);
	}
}