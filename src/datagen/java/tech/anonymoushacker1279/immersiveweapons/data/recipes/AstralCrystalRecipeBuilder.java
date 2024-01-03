package tech.anonymoushacker1279.immersiveweapons.data.recipes;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.AstralCrystalRecipe;

import java.util.Objects;

public class AstralCrystalRecipeBuilder extends tech.anonymoushacker1279.immersiveweapons.data.recipes.IWRecipeBuilder {

	private final Ingredient primaryMaterial;
	private final Ingredient secondaryMaterial;
	private final ItemStack result;
	private final AstralCrystalRecipe.Factory<?> factory;

	public AstralCrystalRecipeBuilder(AstralCrystalRecipe.Factory<?> factory, Ingredient primaryMaterial, Ingredient secondaryMaterial, ItemStack result) {
		this.primaryMaterial = primaryMaterial;
		this.secondaryMaterial = secondaryMaterial;
		this.result = result;
		this.factory = factory;
	}

	public static AstralCrystalRecipeBuilder sorcery(Ingredient primaryMaterial, Ingredient secondaryMaterial, ItemStack result) {
		return new AstralCrystalRecipeBuilder(AstralCrystalRecipe::new, primaryMaterial, secondaryMaterial, result);
	}

	@Override
	public Item getResult() {
		return result.getItem();
	}

	@Override
	protected Recipe<?> getRecipe() {
		return factory.create(Objects.requireNonNullElse(group, "astral_crystal"), primaryMaterial, secondaryMaterial, result);
	}
}