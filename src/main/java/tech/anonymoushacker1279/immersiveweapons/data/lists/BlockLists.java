package tech.anonymoushacker1279.immersiveweapons.data.lists;

import net.minecraft.world.level.block.Block;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;

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
	public static final List<Block> wallHeadBlocks = new ArrayList<>(25);
	public static final List<Block> stainedGlassBlocks = new ArrayList<>(25);

	static {
		simpleBlocks.add(BlockRegistry.CLOUD_MARBLE.get());
		simpleBlocks.add(BlockRegistry.CLOUD_MARBLE_BRICKS.get());
		simpleBlocks.add(BlockRegistry.RAW_SULFUR_BLOCK.get());
		simpleBlocks.add(BlockRegistry.VENTUS_ORE.get());
		simpleBlocks.add(BlockRegistry.COBALT_BLOCK.get());
		simpleBlocks.add(BlockRegistry.RAW_COBALT_BLOCK.get());
		simpleBlocks.add(BlockRegistry.MOLTEN_BLOCK.get());
		simpleBlocks.add(BlockRegistry.BURNED_OAK_PLANKS.get());
		simpleBlocks.add(BlockRegistry.MUD.get());
		simpleBlocks.add(BlockRegistry.DRIED_MUD.get());
		simpleBlocks.add(BlockRegistry.HARDENED_MUD.get());
		simpleBlocks.add(BlockRegistry.STARDUST_PLANKS.get());
		simpleBlocks.add(BlockRegistry.BLOOD_SAND.get());
		simpleBlocks.add(BlockRegistry.ASTRAL_BLOCK.get());
		simpleBlocks.add(BlockRegistry.STARSTORM_BLOCK.get());

		stoneBasedOres.add(BlockRegistry.SULFUR_ORE.get());
		stoneBasedOres.add(BlockRegistry.ELECTRIC_ORE.get());
		stoneBasedOres.add(BlockRegistry.COBALT_ORE.get());

		deepslateBasedOres.add(BlockRegistry.DEEPSLATE_SULFUR_ORE.get());
		deepslateBasedOres.add(BlockRegistry.DEEPSLATE_COBALT_ORE.get());

		netherrackBasedOres.add(BlockRegistry.MOLTEN_ORE.get());
		netherrackBasedOres.add(BlockRegistry.NETHER_SULFUR_ORE.get());

		tableBlocks.add(BlockRegistry.OAK_TABLE.get());
		tableBlocks.add(BlockRegistry.SPRUCE_TABLE.get());
		tableBlocks.add(BlockRegistry.BIRCH_TABLE.get());
		tableBlocks.add(BlockRegistry.JUNGLE_TABLE.get());
		tableBlocks.add(BlockRegistry.ACACIA_TABLE.get());
		tableBlocks.add(BlockRegistry.DARK_OAK_TABLE.get());
		tableBlocks.add(BlockRegistry.CRIMSON_TABLE.get());
		tableBlocks.add(BlockRegistry.WARPED_TABLE.get());
		tableBlocks.add(BlockRegistry.BURNED_OAK_TABLE.get());
		tableBlocks.add(BlockRegistry.STARDUST_TABLE.get());

		flagBlocks.add(BlockRegistry.AMERICAN_FLAG.get());
		flagBlocks.add(BlockRegistry.GADSDEN_FLAG.get());
		flagBlocks.add(BlockRegistry.CANADIAN_FLAG.get());
		flagBlocks.add(BlockRegistry.MEXICAN_FLAG.get());
		flagBlocks.add(BlockRegistry.BRITISH_FLAG.get());
		flagBlocks.add(BlockRegistry.TROLL_FLAG.get());
		flagBlocks.add(BlockRegistry.IMMERSIVE_WEAPONS_FLAG.get());

		headBlocks.add(BlockRegistry.MINUTEMAN_HEAD.get());
		headBlocks.add(BlockRegistry.FIELD_MEDIC_HEAD.get());
		headBlocks.add(BlockRegistry.DYING_SOLDIER_HEAD.get());
		headBlocks.add(BlockRegistry.WANDERING_WARRIOR_HEAD.get());
		headBlocks.add(BlockRegistry.HANS_HEAD.get());
		headBlocks.add(BlockRegistry.STORM_CREEPER_HEAD.get());
		headBlocks.add(BlockRegistry.SKELETON_MERCHANT_HEAD.get());
		wallHeadBlocks.add(BlockRegistry.MINUTEMAN_WALL_HEAD.get());
		wallHeadBlocks.add(BlockRegistry.FIELD_MEDIC_WALL_HEAD.get());
		wallHeadBlocks.add(BlockRegistry.DYING_SOLDIER_WALL_HEAD.get());
		wallHeadBlocks.add(BlockRegistry.WANDERING_WARRIOR_WALL_HEAD.get());
		wallHeadBlocks.add(BlockRegistry.HANS_WALL_HEAD.get());
		wallHeadBlocks.add(BlockRegistry.STORM_CREEPER_WALL_HEAD.get());
		wallHeadBlocks.add(BlockRegistry.SKELETON_MERCHANT_WALL_HEAD.get());

		stainedGlassBlocks.add(BlockRegistry.WHITE_STAINED_BULLETPROOF_GLASS.get());
		stainedGlassBlocks.add(BlockRegistry.LIGHT_GRAY_STAINED_BULLETPROOF_GLASS.get());
		stainedGlassBlocks.add(BlockRegistry.GRAY_STAINED_BULLETPROOF_GLASS.get());
		stainedGlassBlocks.add(BlockRegistry.BLACK_STAINED_BULLETPROOF_GLASS.get());
		stainedGlassBlocks.add(BlockRegistry.ORANGE_STAINED_BULLETPROOF_GLASS.get());
		stainedGlassBlocks.add(BlockRegistry.MAGENTA_STAINED_BULLETPROOF_GLASS.get());
		stainedGlassBlocks.add(BlockRegistry.LIGHT_BLUE_STAINED_BULLETPROOF_GLASS.get());
		stainedGlassBlocks.add(BlockRegistry.YELLOW_STAINED_BULLETPROOF_GLASS.get());
		stainedGlassBlocks.add(BlockRegistry.LIME_STAINED_BULLETPROOF_GLASS.get());
		stainedGlassBlocks.add(BlockRegistry.PINK_STAINED_BULLETPROOF_GLASS.get());
		stainedGlassBlocks.add(BlockRegistry.CYAN_STAINED_BULLETPROOF_GLASS.get());
		stainedGlassBlocks.add(BlockRegistry.PURPLE_STAINED_BULLETPROOF_GLASS.get());
		stainedGlassBlocks.add(BlockRegistry.BLUE_STAINED_BULLETPROOF_GLASS.get());
		stainedGlassBlocks.add(BlockRegistry.BROWN_STAINED_BULLETPROOF_GLASS.get());
		stainedGlassBlocks.add(BlockRegistry.GREEN_STAINED_BULLETPROOF_GLASS.get());
		stainedGlassBlocks.add(BlockRegistry.RED_STAINED_BULLETPROOF_GLASS.get());
	}
}