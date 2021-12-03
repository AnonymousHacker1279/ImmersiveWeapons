package com.anonymoushacker1279.immersiveweapons.data.models;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.google.gson.JsonElement;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ItemModelGenerators {

	private final BiConsumer<ResourceLocation, Supplier<JsonElement>> output;

	/**
	 * Constructor for ItemModelGenerators.
	 *
	 * @param biConsumer the <code>BiConsumer</code> extending ResourceLocation and Supplier, extending JsonElement
	 */
	public ItemModelGenerators(BiConsumer<ResourceLocation, Supplier<JsonElement>> biConsumer) {
		output = biConsumer;
	}

	/**
	 * Generate a flat item.
	 *
	 * @param item          the <code>Item</code> to generate a model for
	 * @param modelTemplate the <code>ModelTemplate</code> to use
	 */
	private void generateFlatItem(Item item, ModelTemplate modelTemplate) {
		modelTemplate.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(item), output);
	}

	/**
	 * Run the model generator.
	 */
	public void run() {
		ItemModelLists.init();
		List<Item> items = new ArrayList<>(1);
		List<Item> ignoredItems = ItemModelLists.ignoredItems;

		DeferredRegistryHandler.ITEMS.getEntries().stream().map(RegistryObject::get).filter(Predicate.not(ignoredItems::contains)).forEach(items::add);

		boolean isAtBlockItems = false;
		boolean isPastToolItems = false;
		for (Item item : items) {
			if (item == DeferredRegistryHandler.MOLTEN_ORE_ITEM.get()) {
				isAtBlockItems = true;
			} else if (item == DeferredRegistryHandler.STONE_SHARD.get()) {
				isPastToolItems = true;
			}
			if (!isAtBlockItems) {
				if (!isPastToolItems) {
					generateFlatItem(item, ModelTemplates.FLAT_HANDHELD_ITEM);
				} else {
					generateFlatItem(item, ModelTemplates.FLAT_ITEM);
				}
			}
		}
	}
}