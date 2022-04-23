package com.anonymoushacker1279.immersiveweapons.data.tags.lists;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class BlockTagLists {

	public static final List<Block> BULLETPROOF_GLASS = new ArrayList<>(15);
	public static final List<Block> STAINED_GLASS = new ArrayList<>(15);
	public static final List<Block> BURNED_OAK_LOGS = new ArrayList<>(5);
	public static final List<Block> TABLES = new ArrayList<>(9);

	static {
		addBulletproofGlass();
		addStainedGlass();
		addBurnedOakLogs();
		addTables();
	}

	private static void addBulletproofGlass() {
		BULLETPROOF_GLASS.add(DeferredRegistryHandler.BULLETPROOF_GLASS.get());
		BULLETPROOF_GLASS.add(DeferredRegistryHandler.WHITE_STAINED_BULLETPROOF_GLASS.get());
		BULLETPROOF_GLASS.add(DeferredRegistryHandler.LIGHT_GRAY_STAINED_BULLETPROOF_GLASS.get());
		BULLETPROOF_GLASS.add(DeferredRegistryHandler.GRAY_STAINED_BULLETPROOF_GLASS.get());
		BULLETPROOF_GLASS.add(DeferredRegistryHandler.BLACK_STAINED_BULLETPROOF_GLASS.get());
		BULLETPROOF_GLASS.add(DeferredRegistryHandler.ORANGE_STAINED_BULLETPROOF_GLASS.get());
		BULLETPROOF_GLASS.add(DeferredRegistryHandler.MAGENTA_STAINED_BULLETPROOF_GLASS.get());
		BULLETPROOF_GLASS.add(DeferredRegistryHandler.LIGHT_BLUE_STAINED_BULLETPROOF_GLASS.get());
		BULLETPROOF_GLASS.add(DeferredRegistryHandler.YELLOW_STAINED_BULLETPROOF_GLASS.get());
		BULLETPROOF_GLASS.add(DeferredRegistryHandler.LIME_STAINED_BULLETPROOF_GLASS.get());
		BULLETPROOF_GLASS.add(DeferredRegistryHandler.PINK_STAINED_BULLETPROOF_GLASS.get());
		BULLETPROOF_GLASS.add(DeferredRegistryHandler.CYAN_STAINED_BULLETPROOF_GLASS.get());
		BULLETPROOF_GLASS.add(DeferredRegistryHandler.PURPLE_STAINED_BULLETPROOF_GLASS.get());
		BULLETPROOF_GLASS.add(DeferredRegistryHandler.BLUE_STAINED_BULLETPROOF_GLASS.get());
		BULLETPROOF_GLASS.add(DeferredRegistryHandler.BROWN_STAINED_BULLETPROOF_GLASS.get());
		BULLETPROOF_GLASS.add(DeferredRegistryHandler.GREEN_STAINED_BULLETPROOF_GLASS.get());
		BULLETPROOF_GLASS.add(DeferredRegistryHandler.RED_STAINED_BULLETPROOF_GLASS.get());
	}

	private static void addStainedGlass() {
		STAINED_GLASS.add(DeferredRegistryHandler.WHITE_STAINED_BULLETPROOF_GLASS.get());
		STAINED_GLASS.add(DeferredRegistryHandler.LIGHT_GRAY_STAINED_BULLETPROOF_GLASS.get());
		STAINED_GLASS.add(DeferredRegistryHandler.GRAY_STAINED_BULLETPROOF_GLASS.get());
		STAINED_GLASS.add(DeferredRegistryHandler.BLACK_STAINED_BULLETPROOF_GLASS.get());
		STAINED_GLASS.add(DeferredRegistryHandler.ORANGE_STAINED_BULLETPROOF_GLASS.get());
		STAINED_GLASS.add(DeferredRegistryHandler.MAGENTA_STAINED_BULLETPROOF_GLASS.get());
		STAINED_GLASS.add(DeferredRegistryHandler.LIGHT_BLUE_STAINED_BULLETPROOF_GLASS.get());
		STAINED_GLASS.add(DeferredRegistryHandler.YELLOW_STAINED_BULLETPROOF_GLASS.get());
		STAINED_GLASS.add(DeferredRegistryHandler.LIME_STAINED_BULLETPROOF_GLASS.get());
		STAINED_GLASS.add(DeferredRegistryHandler.PINK_STAINED_BULLETPROOF_GLASS.get());
		STAINED_GLASS.add(DeferredRegistryHandler.CYAN_STAINED_BULLETPROOF_GLASS.get());
		STAINED_GLASS.add(DeferredRegistryHandler.PURPLE_STAINED_BULLETPROOF_GLASS.get());
		STAINED_GLASS.add(DeferredRegistryHandler.BLUE_STAINED_BULLETPROOF_GLASS.get());
		STAINED_GLASS.add(DeferredRegistryHandler.BROWN_STAINED_BULLETPROOF_GLASS.get());
		STAINED_GLASS.add(DeferredRegistryHandler.GREEN_STAINED_BULLETPROOF_GLASS.get());
		STAINED_GLASS.add(DeferredRegistryHandler.RED_STAINED_BULLETPROOF_GLASS.get());
	}

	private static void addBurnedOakLogs() {
		BURNED_OAK_LOGS.add(DeferredRegistryHandler.BURNED_OAK_LOG.get());
		BURNED_OAK_LOGS.add(DeferredRegistryHandler.BURNED_OAK_WOOD.get());
		BURNED_OAK_LOGS.add(DeferredRegistryHandler.STRIPPED_BURNED_OAK_LOG.get());
		BURNED_OAK_LOGS.add(DeferredRegistryHandler.STRIPPED_BURNED_OAK_WOOD.get());
	}

	private static void addTables() {
		TABLES.add(DeferredRegistryHandler.OAK_TABLE.get());
		TABLES.add(DeferredRegistryHandler.SPRUCE_TABLE.get());
		TABLES.add(DeferredRegistryHandler.BIRCH_TABLE.get());
		TABLES.add(DeferredRegistryHandler.JUNGLE_TABLE.get());
		TABLES.add(DeferredRegistryHandler.ACACIA_TABLE.get());
		TABLES.add(DeferredRegistryHandler.DARK_OAK_TABLE.get());
		TABLES.add(DeferredRegistryHandler.CRIMSON_TABLE.get());
		TABLES.add(DeferredRegistryHandler.WARPED_TABLE.get());
		TABLES.add(DeferredRegistryHandler.BURNED_OAK_TABLE.get());
	}
}