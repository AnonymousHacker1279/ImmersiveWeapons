package com.anonymoushacker1279.immersiveweapons.data.models;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemModelLists {

	protected static final List<Item> ignoredItems = new ArrayList<>(1);

	/**
	 * Initialize the lists.
	 */
	protected static void init() {
		ignoredItems.add(DeferredRegistryHandler.BLUNDERBUSS.get());
		ignoredItems.add(DeferredRegistryHandler.FLINTLOCK_PISTOL.get());
		ignoredItems.add(DeferredRegistryHandler.FLARE_GUN.get());
		ignoredItems.add(DeferredRegistryHandler.WOOD_PIKE.get());
		ignoredItems.add(DeferredRegistryHandler.STONE_PIKE.get());
		ignoredItems.add(DeferredRegistryHandler.COPPER_PIKE.get());
		ignoredItems.add(DeferredRegistryHandler.IRON_PIKE.get());
		ignoredItems.add(DeferredRegistryHandler.COBALT_PIKE.get());
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
		/*ignoredItems.add(DeferredRegistryHandler.DYING_SOLDIER_SPAWN_EGG.get());
		ignoredItems.add(DeferredRegistryHandler.MINUTEMAN_SPAWN_EGG.get());
		ignoredItems.add(DeferredRegistryHandler.FIELD_MEDIC_SPAWN_EGG.get());
		ignoredItems.add(DeferredRegistryHandler.WANDERING_WARRIOR_SPAWN_EGG.get());
		ignoredItems.add(DeferredRegistryHandler.HANS_SPAWN_EGG.get());
		ignoredItems.add(DeferredRegistryHandler.LAVA_REVENANT_SPAWN_EGG.get());
		ignoredItems.add(DeferredRegistryHandler.ROCK_SPIDER_SPAWN_EGG.get());
		ignoredItems.add(DeferredRegistryHandler.CELESTIAL_TOWER_SPAWN_EGG.get());*/
		ignoredItems.add(DeferredRegistryHandler.MRE.get());
		ignoredItems.add(DeferredRegistryHandler.WOOD_GAUNTLET.get());
		ignoredItems.add(DeferredRegistryHandler.STONE_GAUNTLET.get());
		ignoredItems.add(DeferredRegistryHandler.GOLD_GAUNTLET.get());
		ignoredItems.add(DeferredRegistryHandler.COPPER_GAUNTLET.get());
		ignoredItems.add(DeferredRegistryHandler.IRON_GAUNTLET.get());
		ignoredItems.add(DeferredRegistryHandler.COBALT_GAUNTLET.get());
		ignoredItems.add(DeferredRegistryHandler.DIAMOND_GAUNTLET.get());
		ignoredItems.add(DeferredRegistryHandler.NETHERITE_GAUNTLET.get());
	}
}