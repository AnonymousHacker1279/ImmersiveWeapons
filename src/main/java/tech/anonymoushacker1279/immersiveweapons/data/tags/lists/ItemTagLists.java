package tech.anonymoushacker1279.immersiveweapons.data.tags.lists;

import net.minecraft.world.item.Item;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

import java.util.ArrayList;
import java.util.List;

public class ItemTagLists {

	public static final List<Item> MUSKET_BALLS = new ArrayList<>(10);
	public static final List<Item> ARROWS = new ArrayList<>(20);

	/**
	 * Initialize the lists.
	 */
	public static void init() {
		addMusketBalls();
		addArrows();
	}

	private static void addMusketBalls() {
		MUSKET_BALLS.add(ItemRegistry.WOODEN_MUSKET_BALL.get());
		MUSKET_BALLS.add(ItemRegistry.STONE_MUSKET_BALL.get());
		MUSKET_BALLS.add(ItemRegistry.COPPER_MUSKET_BALL.get());
		MUSKET_BALLS.add(ItemRegistry.IRON_MUSKET_BALL.get());
		MUSKET_BALLS.add(ItemRegistry.COBALT_MUSKET_BALL.get());
		MUSKET_BALLS.add(ItemRegistry.GOLDEN_MUSKET_BALL.get());
		MUSKET_BALLS.add(ItemRegistry.DIAMOND_MUSKET_BALL.get());
		MUSKET_BALLS.add(ItemRegistry.NETHERITE_MUSKET_BALL.get());
	}

	private static void addArrows() {
		ARROWS.add(ItemRegistry.WOODEN_ARROW.get());
		ARROWS.add(ItemRegistry.STONE_ARROW.get());
		ARROWS.add(ItemRegistry.COPPER_ARROW.get());
		ARROWS.add(ItemRegistry.IRON_ARROW.get());
		ARROWS.add(ItemRegistry.COBALT_ARROW.get());
		ARROWS.add(ItemRegistry.GOLDEN_ARROW.get());
		ARROWS.add(ItemRegistry.DIAMOND_ARROW.get());
		ARROWS.add(ItemRegistry.NETHERITE_ARROW.get());
		ARROWS.add(ItemRegistry.SMOKE_GRENADE_ARROW.get());
		ARROWS.add(ItemRegistry.SMOKE_GRENADE_ARROW_RED.get());
		ARROWS.add(ItemRegistry.SMOKE_GRENADE_ARROW_GREEN.get());
		ARROWS.add(ItemRegistry.SMOKE_GRENADE_ARROW_BLUE.get());
		ARROWS.add(ItemRegistry.SMOKE_GRENADE_ARROW_PURPLE.get());
		ARROWS.add(ItemRegistry.SMOKE_GRENADE_ARROW_YELLOW.get());
	}
}