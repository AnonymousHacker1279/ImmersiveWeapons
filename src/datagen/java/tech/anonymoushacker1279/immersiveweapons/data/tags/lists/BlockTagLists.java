package tech.anonymoushacker1279.immersiveweapons.data.tags.lists;

import net.minecraft.world.level.block.Block;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;

import java.util.ArrayList;
import java.util.List;

public class BlockTagLists {

	public static final List<Block> BULLETPROOF_GLASS = new ArrayList<>(15);
	public static final List<Block> STAINED_GLASS = new ArrayList<>(15);
	public static final List<Block> BURNED_OAK_LOGS = new ArrayList<>(5);
	public static final List<Block> STARDUST_LOGS = new ArrayList<>(5);

	static {
		addBulletproofGlass();
		addStainedGlass();
		addBurnedOakLogs();
		addStardustLogs();
	}

	private static void addBulletproofGlass() {
		BULLETPROOF_GLASS.add(BlockRegistry.BULLETPROOF_GLASS.get());
		BULLETPROOF_GLASS.add(BlockRegistry.WHITE_STAINED_BULLETPROOF_GLASS.get());
		BULLETPROOF_GLASS.add(BlockRegistry.LIGHT_GRAY_STAINED_BULLETPROOF_GLASS.get());
		BULLETPROOF_GLASS.add(BlockRegistry.GRAY_STAINED_BULLETPROOF_GLASS.get());
		BULLETPROOF_GLASS.add(BlockRegistry.BLACK_STAINED_BULLETPROOF_GLASS.get());
		BULLETPROOF_GLASS.add(BlockRegistry.ORANGE_STAINED_BULLETPROOF_GLASS.get());
		BULLETPROOF_GLASS.add(BlockRegistry.MAGENTA_STAINED_BULLETPROOF_GLASS.get());
		BULLETPROOF_GLASS.add(BlockRegistry.LIGHT_BLUE_STAINED_BULLETPROOF_GLASS.get());
		BULLETPROOF_GLASS.add(BlockRegistry.YELLOW_STAINED_BULLETPROOF_GLASS.get());
		BULLETPROOF_GLASS.add(BlockRegistry.LIME_STAINED_BULLETPROOF_GLASS.get());
		BULLETPROOF_GLASS.add(BlockRegistry.PINK_STAINED_BULLETPROOF_GLASS.get());
		BULLETPROOF_GLASS.add(BlockRegistry.CYAN_STAINED_BULLETPROOF_GLASS.get());
		BULLETPROOF_GLASS.add(BlockRegistry.PURPLE_STAINED_BULLETPROOF_GLASS.get());
		BULLETPROOF_GLASS.add(BlockRegistry.BLUE_STAINED_BULLETPROOF_GLASS.get());
		BULLETPROOF_GLASS.add(BlockRegistry.BROWN_STAINED_BULLETPROOF_GLASS.get());
		BULLETPROOF_GLASS.add(BlockRegistry.GREEN_STAINED_BULLETPROOF_GLASS.get());
		BULLETPROOF_GLASS.add(BlockRegistry.RED_STAINED_BULLETPROOF_GLASS.get());
	}

	private static void addStainedGlass() {
		STAINED_GLASS.add(BlockRegistry.WHITE_STAINED_BULLETPROOF_GLASS.get());
		STAINED_GLASS.add(BlockRegistry.LIGHT_GRAY_STAINED_BULLETPROOF_GLASS.get());
		STAINED_GLASS.add(BlockRegistry.GRAY_STAINED_BULLETPROOF_GLASS.get());
		STAINED_GLASS.add(BlockRegistry.BLACK_STAINED_BULLETPROOF_GLASS.get());
		STAINED_GLASS.add(BlockRegistry.ORANGE_STAINED_BULLETPROOF_GLASS.get());
		STAINED_GLASS.add(BlockRegistry.MAGENTA_STAINED_BULLETPROOF_GLASS.get());
		STAINED_GLASS.add(BlockRegistry.LIGHT_BLUE_STAINED_BULLETPROOF_GLASS.get());
		STAINED_GLASS.add(BlockRegistry.YELLOW_STAINED_BULLETPROOF_GLASS.get());
		STAINED_GLASS.add(BlockRegistry.LIME_STAINED_BULLETPROOF_GLASS.get());
		STAINED_GLASS.add(BlockRegistry.PINK_STAINED_BULLETPROOF_GLASS.get());
		STAINED_GLASS.add(BlockRegistry.CYAN_STAINED_BULLETPROOF_GLASS.get());
		STAINED_GLASS.add(BlockRegistry.PURPLE_STAINED_BULLETPROOF_GLASS.get());
		STAINED_GLASS.add(BlockRegistry.BLUE_STAINED_BULLETPROOF_GLASS.get());
		STAINED_GLASS.add(BlockRegistry.BROWN_STAINED_BULLETPROOF_GLASS.get());
		STAINED_GLASS.add(BlockRegistry.GREEN_STAINED_BULLETPROOF_GLASS.get());
		STAINED_GLASS.add(BlockRegistry.RED_STAINED_BULLETPROOF_GLASS.get());
	}

	private static void addBurnedOakLogs() {
		BURNED_OAK_LOGS.add(BlockRegistry.BURNED_OAK_LOG.get());
		BURNED_OAK_LOGS.add(BlockRegistry.BURNED_OAK_WOOD.get());
		BURNED_OAK_LOGS.add(BlockRegistry.STRIPPED_BURNED_OAK_LOG.get());
		BURNED_OAK_LOGS.add(BlockRegistry.STRIPPED_BURNED_OAK_WOOD.get());
	}

	private static void addStardustLogs() {
		STARDUST_LOGS.add(BlockRegistry.STARDUST_LOG.get());
		STARDUST_LOGS.add(BlockRegistry.STARDUST_WOOD.get());
		STARDUST_LOGS.add(BlockRegistry.STRIPPED_STARDUST_LOG.get());
		STARDUST_LOGS.add(BlockRegistry.STRIPPED_STARDUST_WOOD.get());
	}
}