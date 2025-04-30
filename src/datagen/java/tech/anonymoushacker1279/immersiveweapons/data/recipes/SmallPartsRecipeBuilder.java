package tech.anonymoushacker1279.immersiveweapons.data.recipes;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.SmallPartsRecipe;

import java.util.List;
import java.util.Objects;

public class SmallPartsRecipeBuilder extends IWRecipeBuilder {

	private final Ingredient material;
	private final List<ItemStack> craftables;
	private final SmallPartsRecipe.Factory<?> factory;

	public SmallPartsRecipeBuilder(SmallPartsRecipe.Factory<?> factory, Ingredient material, List<ItemStack> craftables) {
		this.material = material;
		this.craftables = craftables;
		this.factory = factory;
	}

	public static SmallPartsRecipeBuilder tinker(Ingredient material, List<Item> craftables) {
		return new SmallPartsRecipeBuilder(SmallPartsRecipe::new, material, craftables.stream().map(ItemStack::new).toList());
	}

	@Override
	public Item getResult() {
		return craftables.getFirst().getItem();
	}

	@Override
	protected Recipe<?> getRecipe() {
		return factory.create(Objects.requireNonNullElse(group, "small_parts"), material, craftables);
	}
}