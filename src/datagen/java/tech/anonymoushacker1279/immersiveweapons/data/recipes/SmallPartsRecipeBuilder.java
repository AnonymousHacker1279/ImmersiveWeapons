package tech.anonymoushacker1279.immersiveweapons.data.recipes;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.SmallPartsRecipe;

import java.util.List;
import java.util.Objects;

public class SmallPartsRecipeBuilder extends IWRecipeBuilder {

	private final Ingredient material;
	private final List<ItemStackTemplate> craftables;
	private Identifier tagName;

	public SmallPartsRecipeBuilder(Ingredient material, List<ItemStackTemplate> craftables, Identifier tagName) {
		this.material = material;
		this.craftables = craftables;
		this.tagName = tagName;
	}

	public static SmallPartsRecipeBuilder tinker(Ingredient material, List<Item> craftables, Identifier tagName) {
		return new SmallPartsRecipeBuilder(material, craftables.stream().map(ItemStackTemplate::new).toList(), tagName);
	}

	@Override
	public ResourceKey<Recipe<?>> defaultId() {
		return ResourceKey.create(Registries.RECIPE, tagName);
	}

	@Override
	protected Recipe<?> getRecipe() {
		return new SmallPartsRecipe(Objects.requireNonNullElse(group, "small_parts"), material, craftables);
	}
}