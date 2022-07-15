package tech.anonymoushacker1279.immersiveweapons.block.crafting.small_parts;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeManager;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.SmallPartsRecipe;

import java.util.ArrayList;
import java.util.List;

public class SmallPartsCraftables {

	public static final List<Pair<Item, Item>> ALL_CRAFTABLES = new ArrayList<>(15);

	public static void init(RecipeManager manager) {
		ImmersiveWeapons.LOGGER.info("Initializing small parts system");

		List<SmallPartsRecipe> recipes = manager.getAllRecipesFor(DeferredRegistryHandler.SMALL_PARTS_RECIPE_TYPE.get());

		for (SmallPartsRecipe recipe : recipes) {
			for (Item craftable : recipe.craftables()) {
				if (!(craftable == Items.AIR)) {
					for (ItemStack material : recipe.material().getItems()) {
						Pair<Item, Item> pair = new Pair<>(material.getItem(), craftable);
						if (!ALL_CRAFTABLES.contains(pair)) {
							ALL_CRAFTABLES.add(pair);
						}
					}
				}
			}
		}
	}

	public static List<Item> getAvailableCraftables(ItemStack stack) {
		List<Pair<Item, Item>> filteredCraftables = SmallPartsCraftables.ALL_CRAFTABLES.stream()
				.filter(itemItemPair -> stack.is(itemItemPair.getFirst()))
				.toList();

		List<Item> craftables = new ArrayList<>(15);
		for (Pair<Item, Item> itemItemPair : filteredCraftables) {
			craftables.add(itemItemPair.getSecond());
		}

		return craftables;
	}
}