package com.anonymoushacker1279.immersiveweapons.data.tags.lists;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.world.item.Item;

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
		MUSKET_BALLS.add(DeferredRegistryHandler.WOOD_MUSKET_BALL.get());
		MUSKET_BALLS.add(DeferredRegistryHandler.STONE_MUSKET_BALL.get());
		MUSKET_BALLS.add(DeferredRegistryHandler.COPPER_MUSKET_BALL.get());
		MUSKET_BALLS.add(DeferredRegistryHandler.IRON_MUSKET_BALL.get());
		MUSKET_BALLS.add(DeferredRegistryHandler.COBALT_MUSKET_BALL.get());
		MUSKET_BALLS.add(DeferredRegistryHandler.GOLD_MUSKET_BALL.get());
		MUSKET_BALLS.add(DeferredRegistryHandler.DIAMOND_MUSKET_BALL.get());
		MUSKET_BALLS.add(DeferredRegistryHandler.NETHERITE_MUSKET_BALL.get());
	}

	private static void addArrows() {
		ARROWS.add(DeferredRegistryHandler.WOOD_ARROW.get());
		ARROWS.add(DeferredRegistryHandler.STONE_ARROW.get());
		ARROWS.add(DeferredRegistryHandler.COPPER_ARROW.get());
		ARROWS.add(DeferredRegistryHandler.IRON_ARROW.get());
		ARROWS.add(DeferredRegistryHandler.COBALT_ARROW.get());
		ARROWS.add(DeferredRegistryHandler.GOLD_ARROW.get());
		ARROWS.add(DeferredRegistryHandler.DIAMOND_ARROW.get());
		ARROWS.add(DeferredRegistryHandler.NETHERITE_ARROW.get());
		ARROWS.add(DeferredRegistryHandler.SMOKE_BOMB_ARROW.get());
		ARROWS.add(DeferredRegistryHandler.SMOKE_BOMB_ARROW_RED.get());
		ARROWS.add(DeferredRegistryHandler.SMOKE_BOMB_ARROW_GREEN.get());
		ARROWS.add(DeferredRegistryHandler.SMOKE_BOMB_ARROW_BLUE.get());
		ARROWS.add(DeferredRegistryHandler.SMOKE_BOMB_ARROW_PURPLE.get());
		ARROWS.add(DeferredRegistryHandler.SMOKE_BOMB_ARROW_YELLOW.get());
	}
}