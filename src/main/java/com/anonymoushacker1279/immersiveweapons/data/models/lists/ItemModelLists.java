package com.anonymoushacker1279.immersiveweapons.data.models.lists;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemModelLists {

	public static final List<Item> ignoredItems = new ArrayList<>(75);

	static {
		// Items
		ignoredItems.add(DeferredRegistryHandler.BLUNDERBUSS.get());
		ignoredItems.add(DeferredRegistryHandler.FLINTLOCK_PISTOL.get());
		ignoredItems.add(DeferredRegistryHandler.MUSKET.get());
		ignoredItems.add(DeferredRegistryHandler.MUSKET_SCOPE.get());
		ignoredItems.add(DeferredRegistryHandler.FLARE_GUN.get());
		ignoredItems.add(DeferredRegistryHandler.WOODEN_PIKE.get());
		ignoredItems.add(DeferredRegistryHandler.STONE_PIKE.get());
		ignoredItems.add(DeferredRegistryHandler.COPPER_PIKE.get());
		ignoredItems.add(DeferredRegistryHandler.IRON_PIKE.get());
		ignoredItems.add(DeferredRegistryHandler.COBALT_PIKE.get());
		ignoredItems.add(DeferredRegistryHandler.GOLDEN_PIKE.get());
		ignoredItems.add(DeferredRegistryHandler.DIAMOND_PIKE.get());
		ignoredItems.add(DeferredRegistryHandler.NETHERITE_PIKE.get());
		ignoredItems.add(DeferredRegistryHandler.FLARE.get());
		ignoredItems.add(DeferredRegistryHandler.MORTAR_SHELL.get());
		ignoredItems.add(DeferredRegistryHandler.MOLOTOV_COCKTAIL.get());
		ignoredItems.add(DeferredRegistryHandler.SMOKE_GRENADE.get());
		ignoredItems.add(DeferredRegistryHandler.SMOKE_GRENADE_BLUE.get());
		ignoredItems.add(DeferredRegistryHandler.SMOKE_GRENADE_RED.get());
		ignoredItems.add(DeferredRegistryHandler.SMOKE_GRENADE_GREEN.get());
		ignoredItems.add(DeferredRegistryHandler.SMOKE_GRENADE_PURPLE.get());
		ignoredItems.add(DeferredRegistryHandler.SMOKE_GRENADE_YELLOW.get());
		ignoredItems.add(DeferredRegistryHandler.EXPLOSIVE_CHOCOLATE_BAR.get());
		ignoredItems.add(DeferredRegistryHandler.MRE.get());
		ignoredItems.add(DeferredRegistryHandler.WOODEN_GAUNTLET.get());
		ignoredItems.add(DeferredRegistryHandler.STONE_GAUNTLET.get());
		ignoredItems.add(DeferredRegistryHandler.GOLDEN_GAUNTLET.get());
		ignoredItems.add(DeferredRegistryHandler.COPPER_GAUNTLET.get());
		ignoredItems.add(DeferredRegistryHandler.IRON_GAUNTLET.get());
		ignoredItems.add(DeferredRegistryHandler.COBALT_GAUNTLET.get());
		ignoredItems.add(DeferredRegistryHandler.DIAMOND_GAUNTLET.get());
		ignoredItems.add(DeferredRegistryHandler.NETHERITE_GAUNTLET.get());
		ignoredItems.add(DeferredRegistryHandler.BURNED_OAK_BOAT.get());
		ignoredItems.add(DeferredRegistryHandler.WOODEN_MUSKET_BALL.get());
		ignoredItems.add(DeferredRegistryHandler.STONE_MUSKET_BALL.get());
		ignoredItems.add(DeferredRegistryHandler.GOLDEN_MUSKET_BALL.get());
		ignoredItems.add(DeferredRegistryHandler.COPPER_MUSKET_BALL.get());
		ignoredItems.add(DeferredRegistryHandler.IRON_MUSKET_BALL.get());
		ignoredItems.add(DeferredRegistryHandler.COBALT_MUSKET_BALL.get());
		ignoredItems.add(DeferredRegistryHandler.DIAMOND_MUSKET_BALL.get());
		ignoredItems.add(DeferredRegistryHandler.NETHERITE_MUSKET_BALL.get());
		// Block Items
		ignoredItems.add(DeferredRegistryHandler.CLOUD_MARBLE_BRICK_STAIRS_ITEM.get());
		ignoredItems.add(DeferredRegistryHandler.CLOUD_MARBLE_BRICK_SLAB_ITEM.get());
		ignoredItems.add(DeferredRegistryHandler.BURNED_OAK_STAIRS_ITEM.get());
		ignoredItems.add(DeferredRegistryHandler.BURNED_OAK_SLAB_ITEM.get());
		ignoredItems.add(DeferredRegistryHandler.BURNED_OAK_FENCE_ITEM.get());
		ignoredItems.add(DeferredRegistryHandler.BURNED_OAK_DOOR_ITEM.get());
		ignoredItems.add(DeferredRegistryHandler.BURNED_OAK_TRAPDOOR_ITEM.get());
		ignoredItems.add(DeferredRegistryHandler.BURNED_OAK_SIGN_ITEM.get());
		ignoredItems.add(DeferredRegistryHandler.BURNED_OAK_BUTTON_ITEM.get());
		ignoredItems.add(DeferredRegistryHandler.HARDENED_MUD_STAIRS_ITEM.get());
		ignoredItems.add(DeferredRegistryHandler.HARDENED_MUD_SLAB_ITEM.get());
		ignoredItems.add(DeferredRegistryHandler.AZUL_STAINED_ORCHID_ITEM.get());
		ignoredItems.add(DeferredRegistryHandler.BEAR_TRAP_ITEM.get());
		ignoredItems.add(DeferredRegistryHandler.LANDMINE_ITEM.get());
		ignoredItems.add(DeferredRegistryHandler.SPIKE_TRAP_ITEM.get());
		ignoredItems.add(DeferredRegistryHandler.SANDBAG_ITEM.get());
		ignoredItems.add(DeferredRegistryHandler.SPOTLIGHT_ITEM.get());
		ignoredItems.add(DeferredRegistryHandler.MORTAR_ITEM.get());
		ignoredItems.add(DeferredRegistryHandler.MINUTEMAN_HEAD_ITEM.get());
		ignoredItems.add(DeferredRegistryHandler.FIELD_MEDIC_HEAD_ITEM.get());
		ignoredItems.add(DeferredRegistryHandler.DYING_SOLDIER_HEAD_ITEM.get());
		ignoredItems.add(DeferredRegistryHandler.WANDERING_WARRIOR_HEAD_ITEM.get());
		ignoredItems.add(DeferredRegistryHandler.HANS_HEAD_ITEM.get());
	}
}