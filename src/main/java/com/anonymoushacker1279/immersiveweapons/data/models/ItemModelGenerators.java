package com.anonymoushacker1279.immersiveweapons.data.models;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.google.gson.JsonElement;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.fmllegacy.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ItemModelGenerators {

	private final BiConsumer<ResourceLocation, Supplier<JsonElement>> output;

	public ItemModelGenerators(BiConsumer<ResourceLocation, Supplier<JsonElement>> biConsumer) {
		output = biConsumer;
	}

	private void generateFlatItem(Item item, ModelTemplate modelTemplate) {
		modelTemplate.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(item), output);
	}

	public void run() {
		List<Item> items = new ArrayList<>(1);
		List<Item> ignoredItems = new ArrayList<>(1);

		ignoredItems.add(DeferredRegistryHandler.BLUNDERBUSS.get());
		ignoredItems.add(DeferredRegistryHandler.FLINTLOCK_PISTOL.get());
		ignoredItems.add(DeferredRegistryHandler.FLARE_GUN.get());
		ignoredItems.add(DeferredRegistryHandler.WOOD_PIKE.get());
		ignoredItems.add(DeferredRegistryHandler.STONE_PIKE.get());
		ignoredItems.add(DeferredRegistryHandler.COPPER_PIKE.get());
		ignoredItems.add(DeferredRegistryHandler.IRON_PIKE.get());
		ignoredItems.add(DeferredRegistryHandler.GOLD_PIKE.get());
		ignoredItems.add(DeferredRegistryHandler.DIAMOND_PIKE.get());
		ignoredItems.add(DeferredRegistryHandler.NETHERITE_PIKE.get());
		ignoredItems.add(DeferredRegistryHandler.FLARE.get());
		ignoredItems.add(DeferredRegistryHandler.MORTAR_SHELL.get());
		ignoredItems.add(DeferredRegistryHandler.MOLOTOV_COCKTAIL.get());
		ignoredItems.add(DeferredRegistryHandler.SMOKE_BOMB.get());
		ignoredItems.add(DeferredRegistryHandler.SMOKE_BOMB_BLUE.get());
		ignoredItems.add(DeferredRegistryHandler.SMOKE_BOMB_RED.get());
		ignoredItems.add(DeferredRegistryHandler.SMOKE_BOMB_GREEN.get());
		ignoredItems.add(DeferredRegistryHandler.SMOKE_BOMB_PURPLE.get());
		ignoredItems.add(DeferredRegistryHandler.SMOKE_BOMB_YELLOW.get());
		ignoredItems.add(DeferredRegistryHandler.EXPLOSIVE_CHOCOLATE_BAR.get());
		ignoredItems.add(DeferredRegistryHandler.DYING_SOLDIER_SPAWN_EGG.get());
		ignoredItems.add(DeferredRegistryHandler.MINUTEMAN_SPAWN_EGG.get());
		ignoredItems.add(DeferredRegistryHandler.FIELD_MEDIC_SPAWN_EGG.get());
		ignoredItems.add(DeferredRegistryHandler.WANDERING_WARRIOR_SPAWN_EGG.get());
		ignoredItems.add(DeferredRegistryHandler.HANS_SPAWN_EGG.get());
		ignoredItems.add(DeferredRegistryHandler.MRE.get());

		DeferredRegistryHandler.ITEMS.getEntries().stream().map(RegistryObject::get).filter(Predicate.not(ignoredItems::contains)).forEach(items::add);

		boolean isAtBlockItems = false;
		for (Item item : items) {
			if (item == DeferredRegistryHandler.MOLTEN_ORE_ITEM.get()) {
				isAtBlockItems = true;
			}
			if (!isAtBlockItems) {
				generateFlatItem(item, ModelTemplates.FLAT_ITEM);
			} else {
				break;
			}
		}
	}
}