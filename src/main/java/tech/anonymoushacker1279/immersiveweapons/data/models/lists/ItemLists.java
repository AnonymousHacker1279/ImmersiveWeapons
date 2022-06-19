package tech.anonymoushacker1279.immersiveweapons.data.models.lists;

import net.minecraft.world.item.Item;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

import java.util.ArrayList;
import java.util.List;

public class ItemLists {

	public static final List<Item> modelGeneratorIgnoredItems = new ArrayList<>(75);
	public static final List<Item> headItems = new ArrayList<>(15);

	static {
		// Items
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.BLUNDERBUSS.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.FLINTLOCK_PISTOL.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.MUSKET.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.MUSKET_SCOPE.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.FLARE_GUN.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.WOODEN_PIKE.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.STONE_PIKE.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.COPPER_PIKE.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.IRON_PIKE.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.COBALT_PIKE.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.GOLDEN_PIKE.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.DIAMOND_PIKE.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.NETHERITE_PIKE.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.FLARE.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.MORTAR_SHELL.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.MOLOTOV_COCKTAIL.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.SMOKE_GRENADE.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.SMOKE_GRENADE_BLUE.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.SMOKE_GRENADE_RED.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.SMOKE_GRENADE_GREEN.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.SMOKE_GRENADE_PURPLE.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.SMOKE_GRENADE_YELLOW.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.EXPLOSIVE_CHOCOLATE_BAR.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.MRE.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.WOODEN_GAUNTLET.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.STONE_GAUNTLET.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.GOLDEN_GAUNTLET.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.COPPER_GAUNTLET.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.IRON_GAUNTLET.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.COBALT_GAUNTLET.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.DIAMOND_GAUNTLET.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.NETHERITE_GAUNTLET.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.WOODEN_MUSKET_BALL.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.STONE_MUSKET_BALL.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.GOLDEN_MUSKET_BALL.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.COPPER_MUSKET_BALL.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.IRON_MUSKET_BALL.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.COBALT_MUSKET_BALL.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.DIAMOND_MUSKET_BALL.get());
		modelGeneratorIgnoredItems.add(DeferredRegistryHandler.NETHERITE_MUSKET_BALL.get());

		headItems.add(DeferredRegistryHandler.MINUTEMAN_HEAD_ITEM.get());
		headItems.add(DeferredRegistryHandler.FIELD_MEDIC_HEAD_ITEM.get());
		headItems.add(DeferredRegistryHandler.DYING_SOLDIER_HEAD_ITEM.get());
		headItems.add(DeferredRegistryHandler.WANDERING_WARRIOR_HEAD_ITEM.get());
		headItems.add(DeferredRegistryHandler.HANS_HEAD_ITEM.get());
	}
}