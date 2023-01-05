package tech.anonymoushacker1279.immersiveweapons.data.models.lists;

import net.minecraft.world.item.Item;
import tech.anonymoushacker1279.immersiveweapons.init.BlockItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

import java.util.ArrayList;
import java.util.List;

public class ItemLists {

	public static final List<Item> modelGeneratorIgnoredItems = new ArrayList<>(75);
	public static final List<Item> headItems = new ArrayList<>(15);
	public static final List<Item> smokeGrenadeItems = new ArrayList<>(15);
	public static final List<Item> smokeGrenadeArrowItems = new ArrayList<>(15);

	static {
		// Items
		modelGeneratorIgnoredItems.add(ItemRegistry.BLUNDERBUSS.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.FLINTLOCK_PISTOL.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.MUSKET.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.MUSKET_SCOPE.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.FLARE_GUN.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.WOODEN_PIKE.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.STONE_PIKE.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.COPPER_PIKE.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.IRON_PIKE.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.COBALT_PIKE.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.GOLDEN_PIKE.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.DIAMOND_PIKE.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.NETHERITE_PIKE.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.FLARE.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.MORTAR_SHELL.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.MOLOTOV_COCKTAIL.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.SMOKE_GRENADE.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.SMOKE_GRENADE_BLUE.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.SMOKE_GRENADE_RED.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.SMOKE_GRENADE_GREEN.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.SMOKE_GRENADE_PURPLE.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.SMOKE_GRENADE_YELLOW.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.EXPLOSIVE_CHOCOLATE_BAR.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.MRE.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.WOODEN_GAUNTLET.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.STONE_GAUNTLET.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.GOLDEN_GAUNTLET.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.COPPER_GAUNTLET.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.IRON_GAUNTLET.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.COBALT_GAUNTLET.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.DIAMOND_GAUNTLET.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.NETHERITE_GAUNTLET.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.WOODEN_MUSKET_BALL.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.STONE_MUSKET_BALL.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.GOLDEN_MUSKET_BALL.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.COPPER_MUSKET_BALL.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.IRON_MUSKET_BALL.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.COBALT_MUSKET_BALL.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.DIAMOND_MUSKET_BALL.get());
		modelGeneratorIgnoredItems.add(ItemRegistry.NETHERITE_MUSKET_BALL.get());

		headItems.add(BlockItemRegistry.MINUTEMAN_HEAD_ITEM.get());
		headItems.add(BlockItemRegistry.FIELD_MEDIC_HEAD_ITEM.get());
		headItems.add(BlockItemRegistry.DYING_SOLDIER_HEAD_ITEM.get());
		headItems.add(BlockItemRegistry.WANDERING_WARRIOR_HEAD_ITEM.get());
		headItems.add(BlockItemRegistry.HANS_HEAD_ITEM.get());

		smokeGrenadeItems.add(ItemRegistry.SMOKE_GRENADE.get());
		smokeGrenadeItems.add(ItemRegistry.SMOKE_GRENADE_BLUE.get());
		smokeGrenadeItems.add(ItemRegistry.SMOKE_GRENADE_RED.get());
		smokeGrenadeItems.add(ItemRegistry.SMOKE_GRENADE_GREEN.get());
		smokeGrenadeItems.add(ItemRegistry.SMOKE_GRENADE_PURPLE.get());
		smokeGrenadeItems.add(ItemRegistry.SMOKE_GRENADE_YELLOW.get());

		smokeGrenadeArrowItems.add(ItemRegistry.SMOKE_GRENADE_ARROW.get());
		smokeGrenadeArrowItems.add(ItemRegistry.SMOKE_GRENADE_ARROW_BLUE.get());
		smokeGrenadeArrowItems.add(ItemRegistry.SMOKE_GRENADE_ARROW_RED.get());
		smokeGrenadeArrowItems.add(ItemRegistry.SMOKE_GRENADE_ARROW_GREEN.get());
		smokeGrenadeArrowItems.add(ItemRegistry.SMOKE_GRENADE_ARROW_PURPLE.get());
		smokeGrenadeArrowItems.add(ItemRegistry.SMOKE_GRENADE_ARROW_YELLOW.get());
	}
}