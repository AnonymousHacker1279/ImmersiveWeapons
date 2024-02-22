package tech.anonymoushacker1279.immersiveweapons.data.recipes;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.AmmunitionTableRecipe;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.AmmunitionTableRecipe.MaterialGroup;

import java.util.*;

public class AmmunitionTableRecipeBuilder extends IWRecipeBuilder {

	private final List<MaterialGroup> materials = new ArrayList<>(5);
	private final Item result;
	private final AmmunitionTableRecipe.Factory<?> factory;

	public AmmunitionTableRecipeBuilder(AmmunitionTableRecipe.Factory<?> factory, List<MaterialGroup> materials, Item result) {
		this.materials.addAll(materials);
		this.result = result;
		this.factory = factory;
	}

	public static AmmunitionTableRecipeBuilder crafting(List<MaterialGroup> materials, Item result) {
		return new AmmunitionTableRecipeBuilder(AmmunitionTableRecipe::new, materials, result);
	}

	@Override
	public Item getResult() {
		return result;
	}

	@Override
	protected Recipe<?> getRecipe() {
		return factory.create(Objects.requireNonNullElse(group, "ammunition_table"), materials, result.getDefaultInstance());
	}
}