package tech.anonymoushacker1279.immersiveweapons.data.models.lists;

import net.minecraft.world.level.block.Block;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

import java.util.ArrayList;
import java.util.List;

public class BlockLists {

	public static final List<Block> simpleBlocks = new ArrayList<>(25);
	public static final List<Block> stoneBasedOres = new ArrayList<>(25);
	public static final List<Block> deepslateBasedOres = new ArrayList<>(25);
	public static final List<Block> netherrackBasedOres = new ArrayList<>(25);
	public static final List<Block> tableBlocks = new ArrayList<>(25);
	public static final List<Block> flagBlocks = new ArrayList<>(25);
	public static final List<Block> headBlocks = new ArrayList<>(25);
	public static final List<Block> stainedGlassBlocks = new ArrayList<>(25);

	static {
		simpleBlocks.add(DeferredRegistryHandler.CLOUD_MARBLE.get());
		simpleBlocks.add(DeferredRegistryHandler.CLOUD_MARBLE_BRICKS.get());
		simpleBlocks.add(DeferredRegistryHandler.RAW_SULFUR_BLOCK.get());
		simpleBlocks.add(DeferredRegistryHandler.VENTUS_ORE.get());
		simpleBlocks.add(DeferredRegistryHandler.COBALT_BLOCK.get());
		simpleBlocks.add(DeferredRegistryHandler.RAW_COBALT_BLOCK.get());
		simpleBlocks.add(DeferredRegistryHandler.MOLTEN_BLOCK.get());
		simpleBlocks.add(DeferredRegistryHandler.BURNED_OAK_PLANKS.get());
		simpleBlocks.add(DeferredRegistryHandler.MUD.get());
		simpleBlocks.add(DeferredRegistryHandler.DRIED_MUD.get());
		simpleBlocks.add(DeferredRegistryHandler.HARDENED_MUD.get());
		simpleBlocks.add(DeferredRegistryHandler.STARDUST_PLANKS.get());

		stoneBasedOres.add(DeferredRegistryHandler.SULFUR_ORE.get());
		stoneBasedOres.add(DeferredRegistryHandler.ELECTRIC_ORE.get());
		stoneBasedOres.add(DeferredRegistryHandler.COBALT_ORE.get());

		deepslateBasedOres.add(DeferredRegistryHandler.DEEPSLATE_SULFUR_ORE.get());
		deepslateBasedOres.add(DeferredRegistryHandler.DEEPSLATE_COBALT_ORE.get());

		netherrackBasedOres.add(DeferredRegistryHandler.MOLTEN_ORE.get());
		netherrackBasedOres.add(DeferredRegistryHandler.NETHER_SULFUR_ORE.get());

		tableBlocks.add(DeferredRegistryHandler.OAK_TABLE.get());
		tableBlocks.add(DeferredRegistryHandler.SPRUCE_TABLE.get());
		tableBlocks.add(DeferredRegistryHandler.BIRCH_TABLE.get());
		tableBlocks.add(DeferredRegistryHandler.JUNGLE_TABLE.get());
		tableBlocks.add(DeferredRegistryHandler.ACACIA_TABLE.get());
		tableBlocks.add(DeferredRegistryHandler.DARK_OAK_TABLE.get());
		tableBlocks.add(DeferredRegistryHandler.CRIMSON_TABLE.get());
		tableBlocks.add(DeferredRegistryHandler.WARPED_TABLE.get());
		tableBlocks.add(DeferredRegistryHandler.BURNED_OAK_TABLE.get());

		flagBlocks.add(DeferredRegistryHandler.AMERICAN_FLAG.get());
		flagBlocks.add(DeferredRegistryHandler.GADSDEN_FLAG.get());
		flagBlocks.add(DeferredRegistryHandler.CANADIAN_FLAG.get());
		flagBlocks.add(DeferredRegistryHandler.MEXICAN_FLAG.get());
		flagBlocks.add(DeferredRegistryHandler.BRITISH_FLAG.get());
		flagBlocks.add(DeferredRegistryHandler.TROLL_FLAG.get());
		flagBlocks.add(DeferredRegistryHandler.IMMERSIVE_WEAPONS_FLAG.get());

		headBlocks.add(DeferredRegistryHandler.MINUTEMAN_HEAD.get());
		headBlocks.add(DeferredRegistryHandler.FIELD_MEDIC_HEAD.get());
		headBlocks.add(DeferredRegistryHandler.DYING_SOLDIER_HEAD.get());
		headBlocks.add(DeferredRegistryHandler.WANDERING_WARRIOR_HEAD.get());
		headBlocks.add(DeferredRegistryHandler.HANS_HEAD.get());
		headBlocks.add(DeferredRegistryHandler.MINUTEMAN_WALL_HEAD.get());
		headBlocks.add(DeferredRegistryHandler.FIELD_MEDIC_WALL_HEAD.get());
		headBlocks.add(DeferredRegistryHandler.DYING_SOLDIER_WALL_HEAD.get());
		headBlocks.add(DeferredRegistryHandler.WANDERING_WARRIOR_WALL_HEAD.get());
		headBlocks.add(DeferredRegistryHandler.HANS_WALL_HEAD.get());

		stainedGlassBlocks.add(DeferredRegistryHandler.WHITE_STAINED_BULLETPROOF_GLASS.get());
		stainedGlassBlocks.add(DeferredRegistryHandler.LIGHT_GRAY_STAINED_BULLETPROOF_GLASS.get());
		stainedGlassBlocks.add(DeferredRegistryHandler.GRAY_STAINED_BULLETPROOF_GLASS.get());
		stainedGlassBlocks.add(DeferredRegistryHandler.BLACK_STAINED_BULLETPROOF_GLASS.get());
		stainedGlassBlocks.add(DeferredRegistryHandler.ORANGE_STAINED_BULLETPROOF_GLASS.get());
		stainedGlassBlocks.add(DeferredRegistryHandler.MAGENTA_STAINED_BULLETPROOF_GLASS.get());
		stainedGlassBlocks.add(DeferredRegistryHandler.LIGHT_BLUE_STAINED_BULLETPROOF_GLASS.get());
		stainedGlassBlocks.add(DeferredRegistryHandler.YELLOW_STAINED_BULLETPROOF_GLASS.get());
		stainedGlassBlocks.add(DeferredRegistryHandler.LIME_STAINED_BULLETPROOF_GLASS.get());
		stainedGlassBlocks.add(DeferredRegistryHandler.PINK_STAINED_BULLETPROOF_GLASS.get());
		stainedGlassBlocks.add(DeferredRegistryHandler.CYAN_STAINED_BULLETPROOF_GLASS.get());
		stainedGlassBlocks.add(DeferredRegistryHandler.PURPLE_STAINED_BULLETPROOF_GLASS.get());
		stainedGlassBlocks.add(DeferredRegistryHandler.BLUE_STAINED_BULLETPROOF_GLASS.get());
		stainedGlassBlocks.add(DeferredRegistryHandler.BROWN_STAINED_BULLETPROOF_GLASS.get());
		stainedGlassBlocks.add(DeferredRegistryHandler.GREEN_STAINED_BULLETPROOF_GLASS.get());
		stainedGlassBlocks.add(DeferredRegistryHandler.RED_STAINED_BULLETPROOF_GLASS.get());
	}
}