package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.core.Direction;
import net.minecraft.world.item.*;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings({"unused"})
public class BlockItemRegistry {

	@SuppressWarnings("EmptyMethod")
	public static void bootstrap() {
	}

	// Block Items
	public static final RegistryObject<BlockItem> MOLTEN_ORE_ITEM = ItemRegistry.ITEMS.register("molten_ore", () -> new BlockItem(BlockRegistry.MOLTEN_ORE.get(), new Properties().fireResistant()));
	public static final RegistryObject<BlockItem> ELECTRIC_ORE_ITEM = ItemRegistry.ITEMS.register("electric_ore", () -> new BlockItem(BlockRegistry.ELECTRIC_ORE.get(), new Properties()));
	public static final RegistryObject<BlockItem> COBALT_ORE_ITEM = ItemRegistry.ITEMS.register("cobalt_ore", () -> new BlockItem(BlockRegistry.COBALT_ORE.get(), new Properties()));
	public static final RegistryObject<BlockItem> DEEPSLATE_COBALT_ORE_ITEM = ItemRegistry.ITEMS.register("deepslate_cobalt_ore", () -> new BlockItem(BlockRegistry.DEEPSLATE_COBALT_ORE.get(), new Properties()));
	public static final RegistryObject<BlockItem> VENTUS_ORE_ITEM = ItemRegistry.ITEMS.register("ventus_ore", () -> new BlockItem(BlockRegistry.VENTUS_ORE.get(), new Properties()));
	public static final RegistryObject<BlockItem> SULFUR_ORE_ITEM = ItemRegistry.ITEMS.register("sulfur_ore", () -> new BlockItem(BlockRegistry.SULFUR_ORE.get(), new Properties()));
	public static final RegistryObject<BlockItem> DEEPSLATE_SULFUR_ORE_ITEM = ItemRegistry.ITEMS.register("deepslate_sulfur_ore", () -> new BlockItem(BlockRegistry.DEEPSLATE_SULFUR_ORE.get(), new Properties()));
	public static final RegistryObject<BlockItem> NETHER_SULFUR_ORE_ITEM = ItemRegistry.ITEMS.register("nether_sulfur_ore", () -> new BlockItem(BlockRegistry.NETHER_SULFUR_ORE.get(), new Properties()));
	public static final RegistryObject<BlockItem> RAW_SULFUR_BLOCK_ITEM = ItemRegistry.ITEMS.register("raw_sulfur_block", () -> new BlockItem(BlockRegistry.RAW_SULFUR_BLOCK.get(), new Properties()));
	public static final RegistryObject<BlockItem> MOLTEN_BLOCK_ITEM = ItemRegistry.ITEMS.register("molten_block", () -> new BlockItem(BlockRegistry.MOLTEN_BLOCK.get(), new Properties().fireResistant()));
	public static final RegistryObject<BlockItem> TESLA_BLOCK_ITEM = ItemRegistry.ITEMS.register("tesla_block", () -> new BlockItem(BlockRegistry.TESLA_BLOCK.get(), new Properties()));
	public static final RegistryObject<BlockItem> ASTRAL_BLOCK_ITEM = ItemRegistry.ITEMS.register("astral_block", () -> new BlockItem(BlockRegistry.ASTRAL_BLOCK.get(), new Properties()));
	public static final RegistryObject<BlockItem> STARSTORM_BLOCK_ITEM = ItemRegistry.ITEMS.register("starstorm_block", () -> new BlockItem(BlockRegistry.STARSTORM_BLOCK.get(), new Properties()));
	public static final RegistryObject<BlockItem> COBALT_BLOCK_ITEM = ItemRegistry.ITEMS.register("cobalt_block", () -> new BlockItem(BlockRegistry.COBALT_BLOCK.get(), new Properties()));
	public static final RegistryObject<BlockItem> RAW_COBALT_BLOCK_ITEM = ItemRegistry.ITEMS.register("raw_cobalt_block", () -> new BlockItem(BlockRegistry.RAW_COBALT_BLOCK.get(), new Properties()));
	public static final RegistryObject<BlockItem> RUSTED_IRON_BLOCK_ITEM = ItemRegistry.ITEMS.register("rusted_iron_block", () -> new BlockItem(BlockRegistry.RUSTED_IRON_BLOCK.get(), new Properties()));
	public static final RegistryObject<BlockItem> SMALL_PARTS_TABLE_ITEM = ItemRegistry.ITEMS.register("small_parts_table", () -> new BlockItem(BlockRegistry.SMALL_PARTS_TABLE.get(), new Properties()));
	public static final RegistryObject<BlockItem> BARREL_TAP_ITEM = ItemRegistry.ITEMS.register("barrel_tap", () -> new BlockItem(BlockRegistry.BARREL_TAP.get(), new Properties()));
	public static final RegistryObject<BlockItem> BULLETPROOF_GLASS_ITEM = ItemRegistry.ITEMS.register("bulletproof_glass", () -> new BlockItem(BlockRegistry.BULLETPROOF_GLASS.get(), new Properties()));
	public static final RegistryObject<BlockItem> WHITE_STAINED_BULLETPROOF_GLASS_ITEM = ItemRegistry.ITEMS.register("white_stained_bulletproof_glass", () -> new BlockItem(BlockRegistry.WHITE_STAINED_BULLETPROOF_GLASS.get(), new Properties()));
	public static final RegistryObject<BlockItem> LIGHT_GRAY_STAINED_BULLETPROOF_GLASS_ITEM = ItemRegistry.ITEMS.register("light_gray_stained_bulletproof_glass", () -> new BlockItem(BlockRegistry.LIGHT_GRAY_STAINED_BULLETPROOF_GLASS.get(), new Properties()));
	public static final RegistryObject<BlockItem> GRAY_STAINED_BULLETPROOF_GLASS_ITEM = ItemRegistry.ITEMS.register("gray_stained_bulletproof_glass", () -> new BlockItem(BlockRegistry.GRAY_STAINED_BULLETPROOF_GLASS.get(), new Properties()));
	public static final RegistryObject<BlockItem> BLACK_STAINED_BULLETPROOF_GLASS_ITEM = ItemRegistry.ITEMS.register("black_stained_bulletproof_glass", () -> new BlockItem(BlockRegistry.BLACK_STAINED_BULLETPROOF_GLASS.get(), new Properties()));
	public static final RegistryObject<BlockItem> ORANGE_STAINED_BULLETPROOF_GLASS_ITEM = ItemRegistry.ITEMS.register("orange_stained_bulletproof_glass", () -> new BlockItem(BlockRegistry.ORANGE_STAINED_BULLETPROOF_GLASS.get(), new Properties()));
	public static final RegistryObject<BlockItem> MAGENTA_STAINED_BULLETPROOF_GLASS_ITEM = ItemRegistry.ITEMS.register("magenta_stained_bulletproof_glass", () -> new BlockItem(BlockRegistry.MAGENTA_STAINED_BULLETPROOF_GLASS.get(), new Properties()));
	public static final RegistryObject<BlockItem> LIGHT_BLUE_STAINED_BULLETPROOF_GLASS_ITEM = ItemRegistry.ITEMS.register("light_blue_stained_bulletproof_glass", () -> new BlockItem(BlockRegistry.LIGHT_BLUE_STAINED_BULLETPROOF_GLASS.get(), new Properties()));
	public static final RegistryObject<BlockItem> YELLOW_STAINED_BULLETPROOF_GLASS_ITEM = ItemRegistry.ITEMS.register("yellow_stained_bulletproof_glass", () -> new BlockItem(BlockRegistry.YELLOW_STAINED_BULLETPROOF_GLASS.get(), new Properties()));
	public static final RegistryObject<BlockItem> LIME_STAINED_BULLETPROOF_GLASS_ITEM = ItemRegistry.ITEMS.register("lime_stained_bulletproof_glass", () -> new BlockItem(BlockRegistry.LIME_STAINED_BULLETPROOF_GLASS.get(), new Properties()));
	public static final RegistryObject<BlockItem> PINK_STAINED_BULLETPROOF_GLASS_ITEM = ItemRegistry.ITEMS.register("pink_stained_bulletproof_glass", () -> new BlockItem(BlockRegistry.PINK_STAINED_BULLETPROOF_GLASS.get(), new Properties()));
	public static final RegistryObject<BlockItem> CYAN_STAINED_BULLETPROOF_GLASS_ITEM = ItemRegistry.ITEMS.register("cyan_stained_bulletproof_glass", () -> new BlockItem(BlockRegistry.CYAN_STAINED_BULLETPROOF_GLASS.get(), new Properties()));
	public static final RegistryObject<BlockItem> PURPLE_STAINED_BULLETPROOF_GLASS_ITEM = ItemRegistry.ITEMS.register("purple_stained_bulletproof_glass", () -> new BlockItem(BlockRegistry.PURPLE_STAINED_BULLETPROOF_GLASS.get(), new Properties()));
	public static final RegistryObject<BlockItem> BLUE_STAINED_BULLETPROOF_GLASS_ITEM = ItemRegistry.ITEMS.register("blue_stained_bulletproof_glass", () -> new BlockItem(BlockRegistry.BLUE_STAINED_BULLETPROOF_GLASS.get(), new Properties()));
	public static final RegistryObject<BlockItem> BROWN_STAINED_BULLETPROOF_GLASS_ITEM = ItemRegistry.ITEMS.register("brown_stained_bulletproof_glass", () -> new BlockItem(BlockRegistry.BROWN_STAINED_BULLETPROOF_GLASS.get(), new Properties()));
	public static final RegistryObject<BlockItem> GREEN_STAINED_BULLETPROOF_GLASS_ITEM = ItemRegistry.ITEMS.register("green_stained_bulletproof_glass", () -> new BlockItem(BlockRegistry.GREEN_STAINED_BULLETPROOF_GLASS.get(), new Properties()));
	public static final RegistryObject<BlockItem> RED_STAINED_BULLETPROOF_GLASS_ITEM = ItemRegistry.ITEMS.register("red_stained_bulletproof_glass", () -> new BlockItem(BlockRegistry.RED_STAINED_BULLETPROOF_GLASS.get(), new Properties()));
	public static final RegistryObject<BlockItem> PUNJI_STICKS_ITEM = ItemRegistry.ITEMS.register("punji_sticks", () -> new BlockItem(BlockRegistry.PUNJI_STICKS.get(), new Properties()));
	public static final RegistryObject<BlockItem> PITFALL_ITEM = ItemRegistry.ITEMS.register("pitfall", () -> new BlockItem(BlockRegistry.PITFALL.get(), new Properties()));
	public static final RegistryObject<BlockItem> BEAR_TRAP_ITEM = ItemRegistry.ITEMS.register("bear_trap", () -> new BlockItem(BlockRegistry.BEAR_TRAP.get(), new Properties()));
	public static final RegistryObject<BlockItem> BARBED_WIRE_ITEM = ItemRegistry.ITEMS.register("barbed_wire", () -> new BlockItem(BlockRegistry.BARBED_WIRE.get(), new Properties()));
	public static final RegistryObject<BlockItem> LANDMINE_ITEM = ItemRegistry.ITEMS.register("landmine", () -> new BlockItem(BlockRegistry.LANDMINE.get(), new Properties()));
	public static final RegistryObject<BlockItem> SPIKE_TRAP_ITEM = ItemRegistry.ITEMS.register("spike_trap", () -> new BlockItem(BlockRegistry.SPIKE_TRAP.get(), new Properties()));
	public static final RegistryObject<BlockItem> SANDBAG_ITEM = ItemRegistry.ITEMS.register("sandbag", () -> new BlockItem(BlockRegistry.SANDBAG.get(), new Properties()));
	public static final RegistryObject<BlockItem> IRON_PANEL_ITEM = ItemRegistry.ITEMS.register("iron_panel", () -> new BlockItem(BlockRegistry.IRON_PANEL.get(), new Properties()));
	public static final RegistryObject<BlockItem> IRON_PANEL_BARS_ITEM = ItemRegistry.ITEMS.register("iron_panel_bars", () -> new BlockItem(BlockRegistry.IRON_PANEL_BARS.get(), new Properties()));
	public static final RegistryObject<BlockItem> SPOTLIGHT_ITEM = ItemRegistry.ITEMS.register("spotlight", () -> new BlockItem(BlockRegistry.SPOTLIGHT.get(), new Properties()));
	public static final RegistryObject<BlockItem> MORTAR_ITEM = ItemRegistry.ITEMS.register("mortar", () -> new BlockItem(BlockRegistry.MORTAR.get(), new Properties()));
	public static final RegistryObject<BlockItem> WALL_SHELF_ITEM = ItemRegistry.ITEMS.register("wall_shelf", () -> new BlockItem(BlockRegistry.WALL_SHELF.get(), new Properties()));
	public static final RegistryObject<BlockItem> PANIC_ALARM_ITEM = ItemRegistry.ITEMS.register("panic_alarm", () -> new BlockItem(BlockRegistry.PANIC_ALARM.get(), new Properties()));
	public static final RegistryObject<BlockItem> OAK_TABLE_ITEM = ItemRegistry.ITEMS.register("oak_table", () -> new BlockItem(BlockRegistry.OAK_TABLE.get(), new Properties()));
	public static final RegistryObject<BlockItem> SPRUCE_TABLE_ITEM = ItemRegistry.ITEMS.register("spruce_table", () -> new BlockItem(BlockRegistry.SPRUCE_TABLE.get(), new Properties()));
	public static final RegistryObject<BlockItem> BIRCH_TABLE_ITEM = ItemRegistry.ITEMS.register("birch_table", () -> new BlockItem(BlockRegistry.BIRCH_TABLE.get(), new Properties()));
	public static final RegistryObject<BlockItem> JUNGLE_TABLE_ITEM = ItemRegistry.ITEMS.register("jungle_table", () -> new BlockItem(BlockRegistry.JUNGLE_TABLE.get(), new Properties()));
	public static final RegistryObject<BlockItem> ACACIA_TABLE_ITEM = ItemRegistry.ITEMS.register("acacia_table", () -> new BlockItem(BlockRegistry.ACACIA_TABLE.get(), new Properties()));
	public static final RegistryObject<BlockItem> DARK_OAK_TABLE_ITEM = ItemRegistry.ITEMS.register("dark_oak_table", () -> new BlockItem(BlockRegistry.DARK_OAK_TABLE.get(), new Properties()));
	public static final RegistryObject<BlockItem> CRIMSON_TABLE_ITEM = ItemRegistry.ITEMS.register("crimson_table", () -> new BlockItem(BlockRegistry.CRIMSON_TABLE.get(), new Properties()));
	public static final RegistryObject<BlockItem> WARPED_TABLE_ITEM = ItemRegistry.ITEMS.register("warped_table", () -> new BlockItem(BlockRegistry.WARPED_TABLE.get(), new Properties()));
	public static final RegistryObject<BlockItem> MANGROVE_TABLE_ITEM = ItemRegistry.ITEMS.register("mangrove_table", () -> new BlockItem(BlockRegistry.MANGROVE_TABLE.get(), new Properties()));
	public static final RegistryObject<BlockItem> CHERRY_TABLE_ITEM = ItemRegistry.ITEMS.register("cherry_table", () -> new BlockItem(BlockRegistry.CHERRY_TABLE.get(), new Properties()));
	public static final RegistryObject<BlockItem> BAMBOO_TABLE_ITEM = ItemRegistry.ITEMS.register("bamboo_table", () -> new BlockItem(BlockRegistry.BAMBOO_TABLE.get(), new Properties()));
	public static final RegistryObject<BlockItem> BURNED_OAK_TABLE_ITEM = ItemRegistry.ITEMS.register("burned_oak_table", () -> new BlockItem(BlockRegistry.BURNED_OAK_TABLE.get(), new Properties()));
	public static final RegistryObject<BlockItem> STARDUST_TABLE_ITEM = ItemRegistry.ITEMS.register("stardust_table", () -> new BlockItem(BlockRegistry.STARDUST_TABLE.get(), new Properties()));
	public static final RegistryObject<BlockItem> CAMP_CHAIR_ITEM = ItemRegistry.ITEMS.register("camp_chair", () -> new BlockItem(BlockRegistry.CAMP_CHAIR.get(), new Properties()));
	public static final RegistryObject<BlockItem> BARBED_WIRE_FENCE_ITEM = ItemRegistry.ITEMS.register("barbed_wire_fence", () -> new BlockItem(BlockRegistry.BARBED_WIRE_FENCE.get(), new Properties()));
	public static final RegistryObject<BlockItem> WOODEN_SPIKES_ITEM = ItemRegistry.ITEMS.register("wooden_spikes", () -> new BlockItem(BlockRegistry.WOODEN_SPIKES.get(), new Properties()));
	public static final RegistryObject<BlockItem> BIOHAZARD_BOX_ITEM = ItemRegistry.ITEMS.register("biohazard_box", () -> new BlockItem(BlockRegistry.BIOHAZARD_BOX.get(), new Properties()));
	public static final RegistryObject<BlockItem> MINUTEMAN_STATUE_ITEM = ItemRegistry.ITEMS.register("minuteman_statue", () -> new BlockItem(BlockRegistry.MINUTEMAN_STATUE.get(), new Properties()));
	public static final RegistryObject<BlockItem> MEDIC_STATUE_ITEM = ItemRegistry.ITEMS.register("medic_statue", () -> new BlockItem(BlockRegistry.MEDIC_STATUE.get(), new Properties()));
	public static final RegistryObject<BlockItem> TESLA_SYNTHESIZER_ITEM = ItemRegistry.ITEMS.register("tesla_synthesizer", () -> new BlockItem(BlockRegistry.TESLA_SYNTHESIZER.get(), new Properties()));
	public static final RegistryObject<BlockItem> CLOUD_ITEM = ItemRegistry.ITEMS.register("cloud", () -> new BlockItem(BlockRegistry.CLOUD.get(), new Properties()));
	public static final RegistryObject<BlockItem> CLOUD_MARBLE_ITEM = ItemRegistry.ITEMS.register("cloud_marble", () -> new BlockItem(BlockRegistry.CLOUD_MARBLE.get(), new Properties()));
	public static final RegistryObject<BlockItem> CLOUD_MARBLE_BRICKS_ITEM = ItemRegistry.ITEMS.register("cloud_marble_bricks", () -> new BlockItem(BlockRegistry.CLOUD_MARBLE_BRICKS.get(), new Properties()));
	public static final RegistryObject<BlockItem> CLOUD_MARBLE_PILLAR_ITEM = ItemRegistry.ITEMS.register("cloud_marble_pillar", () -> new BlockItem(BlockRegistry.CLOUD_MARBLE_PILLAR.get(), new Properties()));
	public static final RegistryObject<BlockItem> CLOUD_MARBLE_BRICK_STAIRS_ITEM = ItemRegistry.ITEMS.register("cloud_marble_brick_stairs", () -> new BlockItem(BlockRegistry.CLOUD_MARBLE_BRICK_STAIRS.get(), new Properties()));
	public static final RegistryObject<BlockItem> CLOUD_MARBLE_BRICK_SLAB_ITEM = ItemRegistry.ITEMS.register("cloud_marble_brick_slab", () -> new BlockItem(BlockRegistry.CLOUD_MARBLE_BRICK_SLAB.get(), new Properties()));
	public static final RegistryObject<BlockItem> CLOUD_MARBLE_BRICK_WALL_ITEM = ItemRegistry.ITEMS.register("cloud_marble_brick_wall", () -> new BlockItem(BlockRegistry.CLOUD_MARBLE_BRICK_WALL.get(), new Properties()));
	public static final RegistryObject<BlockItem> BURNED_OAK_WOOD_ITEM = ItemRegistry.ITEMS.register("burned_oak_wood", () -> new BlockItem(BlockRegistry.BURNED_OAK_WOOD.get(), new Properties()));
	public static final RegistryObject<BlockItem> BURNED_OAK_LOG_ITEM = ItemRegistry.ITEMS.register("burned_oak_log", () -> new BlockItem(BlockRegistry.BURNED_OAK_LOG.get(), new Properties()));
	public static final RegistryObject<BlockItem> STRIPPED_BURNED_OAK_WOOD_ITEM = ItemRegistry.ITEMS.register("stripped_burned_oak_wood", () -> new BlockItem(BlockRegistry.STRIPPED_BURNED_OAK_WOOD.get(), new Properties()));
	public static final RegistryObject<BlockItem> STRIPPED_BURNED_OAK_LOG_ITEM = ItemRegistry.ITEMS.register("stripped_burned_oak_log", () -> new BlockItem(BlockRegistry.STRIPPED_BURNED_OAK_LOG.get(), new Properties()));
	public static final RegistryObject<BlockItem> BURNED_OAK_PLANKS_ITEM = ItemRegistry.ITEMS.register("burned_oak_planks", () -> new BlockItem(BlockRegistry.BURNED_OAK_PLANKS.get(), new Properties()));
	public static final RegistryObject<BlockItem> BURNED_OAK_STAIRS_ITEM = ItemRegistry.ITEMS.register("burned_oak_stairs", () -> new BlockItem(BlockRegistry.BURNED_OAK_STAIRS.get(), new Properties()));
	public static final RegistryObject<BlockItem> BURNED_OAK_SLAB_ITEM = ItemRegistry.ITEMS.register("burned_oak_slab", () -> new BlockItem(BlockRegistry.BURNED_OAK_SLAB.get(), new Properties()));
	public static final RegistryObject<BlockItem> BURNED_OAK_FENCE_ITEM = ItemRegistry.ITEMS.register("burned_oak_fence", () -> new BlockItem(BlockRegistry.BURNED_OAK_FENCE.get(), new Properties()));
	public static final RegistryObject<BlockItem> BURNED_OAK_FENCE_GATE_ITEM = ItemRegistry.ITEMS.register("burned_oak_fence_gate", () -> new BlockItem(BlockRegistry.BURNED_OAK_FENCE_GATE.get(), new Properties()));
	public static final RegistryObject<BlockItem> BURNED_OAK_BRANCH_ITEM = ItemRegistry.ITEMS.register("burned_oak_branch", () -> new BlockItem(BlockRegistry.BURNED_OAK_BRANCH.get(), new Properties()));
	public static final RegistryObject<BlockItem> BURNED_OAK_DOOR_ITEM = ItemRegistry.ITEMS.register("burned_oak_door", () -> new BlockItem(BlockRegistry.BURNED_OAK_DOOR.get(), new Properties()));
	public static final RegistryObject<BlockItem> BURNED_OAK_TRAPDOOR_ITEM = ItemRegistry.ITEMS.register("burned_oak_trapdoor", () -> new BlockItem(BlockRegistry.BURNED_OAK_TRAPDOOR.get(), new Properties()));
	public static final RegistryObject<BlockItem> BURNED_OAK_PRESSURE_PLATE_ITEM = ItemRegistry.ITEMS.register("burned_oak_pressure_plate", () -> new BlockItem(BlockRegistry.BURNED_OAK_PRESSURE_PLATE.get(), new Properties()));
	public static final RegistryObject<SignItem> BURNED_OAK_SIGN_ITEM = ItemRegistry.ITEMS.register("burned_oak_sign", () -> new SignItem(new Properties().stacksTo(16), BlockRegistry.BURNED_OAK_SIGN.get(), BlockRegistry.BURNED_OAK_WALL_SIGN.get()));
	public static final RegistryObject<HangingSignItem> BURNED_OAK_HANGING_SIGN_ITEM = ItemRegistry.ITEMS.register("burned_oak_hanging_sign", () -> new HangingSignItem(BlockRegistry.BURNED_OAK_HANGING_SIGN.get(), BlockRegistry.BURNED_OAK_WALL_HANGING_SIGN.get(), new Properties().stacksTo(16)));
	public static final RegistryObject<BlockItem> BURNED_OAK_BUTTON_ITEM = ItemRegistry.ITEMS.register("burned_oak_button", () -> new BlockItem(BlockRegistry.BURNED_OAK_BUTTON.get(), new Properties()));
	public static final RegistryObject<BlockItem> FLAG_POLE_ITEM = ItemRegistry.ITEMS.register("flag_pole", () -> new BlockItem(BlockRegistry.FLAG_POLE.get(), new Properties()));
	public static final RegistryObject<BlockItem> AMERICAN_FLAG_ITEM = ItemRegistry.ITEMS.register("american_flag", () -> new BlockItem(BlockRegistry.AMERICAN_FLAG.get(), new Properties()));
	public static final RegistryObject<BlockItem> GADSDEN_FLAG_ITEM = ItemRegistry.ITEMS.register("gadsden_flag", () -> new BlockItem(BlockRegistry.GADSDEN_FLAG.get(), new Properties()));
	public static final RegistryObject<BlockItem> CANADIAN_FLAG_ITEM = ItemRegistry.ITEMS.register("canadian_flag", () -> new BlockItem(BlockRegistry.CANADIAN_FLAG.get(), new Properties()));
	public static final RegistryObject<BlockItem> MEXICAN_FLAG_ITEM = ItemRegistry.ITEMS.register("mexican_flag", () -> new BlockItem(BlockRegistry.MEXICAN_FLAG.get(), new Properties()));
	public static final RegistryObject<BlockItem> BRITISH_FLAG_ITEM = ItemRegistry.ITEMS.register("british_flag", () -> new BlockItem(BlockRegistry.BRITISH_FLAG.get(), new Properties()));
	public static final RegistryObject<BlockItem> TROLL_FLAG_ITEM = ItemRegistry.ITEMS.register("troll_flag", () -> new BlockItem(BlockRegistry.TROLL_FLAG.get(), new Properties()));
	public static final RegistryObject<BlockItem> IMMERSIVE_WEAPONS_FLAG_ITEM = ItemRegistry.ITEMS.register("immersive_weapons_flag", () -> new BlockItem(BlockRegistry.IMMERSIVE_WEAPONS_FLAG.get(), new Properties()));
	public static final RegistryObject<BlockItem> MUD_ITEM = ItemRegistry.ITEMS.register("mud", () -> new BlockItem(BlockRegistry.MUD.get(), new Properties()));
	public static final RegistryObject<BlockItem> DRIED_MUD_ITEM = ItemRegistry.ITEMS.register("dried_mud", () -> new BlockItem(BlockRegistry.DRIED_MUD.get(), new Properties()));
	public static final RegistryObject<BlockItem> HARDENED_MUD_ITEM = ItemRegistry.ITEMS.register("hardened_mud", () -> new BlockItem(BlockRegistry.HARDENED_MUD.get(), new Properties()));
	public static final RegistryObject<BlockItem> HARDENED_MUD_STAIRS_ITEM = ItemRegistry.ITEMS.register("hardened_mud_stairs", () -> new BlockItem(BlockRegistry.HARDENED_MUD_STAIRS.get(), new Properties()));
	public static final RegistryObject<BlockItem> HARDENED_MUD_SLAB_ITEM = ItemRegistry.ITEMS.register("hardened_mud_slab", () -> new BlockItem(BlockRegistry.HARDENED_MUD_SLAB.get(), new Properties()));
	public static final RegistryObject<BlockItem> HARDENED_MUD_WINDOW_ITEM = ItemRegistry.ITEMS.register("hardened_mud_window", () -> new BlockItem(BlockRegistry.HARDENED_MUD_WINDOW.get(), new Properties()));
	public static final RegistryObject<BlockItem> WARRIOR_STATUE_BASE_ITEM = ItemRegistry.ITEMS.register("warrior_statue_base", () -> new BlockItem(BlockRegistry.WARRIOR_STATUE_BASE.get(), new Properties()));
	public static final RegistryObject<BlockItem> WARRIOR_STATUE_TORSO_ITEM = ItemRegistry.ITEMS.register("warrior_statue_torso", () -> new BlockItem(BlockRegistry.WARRIOR_STATUE_TORSO.get(), new Properties()));
	public static final RegistryObject<BlockItem> WARRIOR_STATUE_HEAD_ITEM = ItemRegistry.ITEMS.register("warrior_statue_head", () -> new BlockItem(BlockRegistry.WARRIOR_STATUE_HEAD.get(), new Properties()));
	public static final RegistryObject<BlockItem> AZUL_STAINED_ORCHID_ITEM = ItemRegistry.ITEMS.register("azul_stained_orchid", () -> new BlockItem(BlockRegistry.AZUL_STAINED_ORCHID.get(), new Properties()));
	public static final RegistryObject<BlockItem> CELESTIAL_LANTERN_ITEM = ItemRegistry.ITEMS.register("celestial_lantern", () -> new BlockItem(BlockRegistry.CELESTIAL_LANTERN.get(), new Properties()));
	public static final RegistryObject<BlockItem> MINUTEMAN_HEAD_ITEM = ItemRegistry.ITEMS.register("minuteman_head", () -> new StandingAndWallBlockItem(BlockRegistry.MINUTEMAN_HEAD.get(), BlockRegistry.MINUTEMAN_WALL_HEAD.get(), new Properties(), Direction.DOWN));
	public static final RegistryObject<BlockItem> FIELD_MEDIC_HEAD_ITEM = ItemRegistry.ITEMS.register("field_medic_head", () -> new StandingAndWallBlockItem(BlockRegistry.FIELD_MEDIC_HEAD.get(), BlockRegistry.FIELD_MEDIC_WALL_HEAD.get(), new Properties(), Direction.DOWN));
	public static final RegistryObject<BlockItem> DYING_SOLDIER_HEAD_ITEM = ItemRegistry.ITEMS.register("dying_soldier_head", () -> new StandingAndWallBlockItem(BlockRegistry.DYING_SOLDIER_HEAD.get(), BlockRegistry.DYING_SOLDIER_WALL_HEAD.get(), new Properties(), Direction.DOWN));
	public static final RegistryObject<BlockItem> WANDERING_WARRIOR_HEAD_ITEM = ItemRegistry.ITEMS.register("wandering_warrior_head", () -> new StandingAndWallBlockItem(BlockRegistry.WANDERING_WARRIOR_HEAD.get(), BlockRegistry.WANDERING_WARRIOR_WALL_HEAD.get(), new Properties(), Direction.DOWN));
	public static final RegistryObject<BlockItem> HANS_HEAD_ITEM = ItemRegistry.ITEMS.register("hans_head", () -> new StandingAndWallBlockItem(BlockRegistry.HANS_HEAD.get(), BlockRegistry.HANS_WALL_HEAD.get(), new Properties(), Direction.DOWN));
	public static final RegistryObject<BlockItem> STORM_CREEPER_HEAD_ITEM = ItemRegistry.ITEMS.register("storm_creeper_head", () -> new StandingAndWallBlockItem(BlockRegistry.STORM_CREEPER_HEAD.get(), BlockRegistry.STORM_CREEPER_WALL_HEAD.get(), new Properties(), Direction.DOWN));
	public static final RegistryObject<BlockItem> SKELETON_MERCHANT_HEAD_ITEM = ItemRegistry.ITEMS.register("skeleton_merchant_head", () -> new StandingAndWallBlockItem(BlockRegistry.SKELETON_MERCHANT_HEAD.get(), BlockRegistry.SKELETON_MERCHANT_WALL_HEAD.get(), new Properties(), Direction.DOWN));
	public static final RegistryObject<BlockItem> MOONGLOW_ITEM = ItemRegistry.ITEMS.register("moonglow", () -> new BlockItem(BlockRegistry.MOONGLOW.get(), new Properties()));
	public static final RegistryObject<BlockItem> STARDUST_LOG_ITEM = ItemRegistry.ITEMS.register("stardust_log", () -> new BlockItem(BlockRegistry.STARDUST_LOG.get(), new Properties()));
	public static final RegistryObject<BlockItem> STRIPPED_STARDUST_LOG_ITEM = ItemRegistry.ITEMS.register("stripped_stardust_log", () -> new BlockItem(BlockRegistry.STRIPPED_STARDUST_LOG.get(), new Properties()));
	public static final RegistryObject<BlockItem> STARDUST_WOOD_ITEM = ItemRegistry.ITEMS.register("stardust_wood", () -> new BlockItem(BlockRegistry.STARDUST_WOOD.get(), new Properties()));
	public static final RegistryObject<BlockItem> STRIPPED_STARDUST_WOOD_ITEM = ItemRegistry.ITEMS.register("stripped_stardust_wood", () -> new BlockItem(BlockRegistry.STRIPPED_STARDUST_WOOD.get(), new Properties()));
	public static final RegistryObject<BlockItem> STARDUST_LEAVES_ITEM = ItemRegistry.ITEMS.register("stardust_leaves", () -> new BlockItem(BlockRegistry.STARDUST_LEAVES.get(), new Properties()));
	public static final RegistryObject<BlockItem> STARDUST_PLANKS_ITEM = ItemRegistry.ITEMS.register("stardust_planks", () -> new BlockItem(BlockRegistry.STARDUST_PLANKS.get(), new Properties()));
	public static final RegistryObject<BlockItem> STARDUST_SLAB_ITEM = ItemRegistry.ITEMS.register("stardust_slab", () -> new BlockItem(BlockRegistry.STARDUST_SLAB.get(), new Properties()));
	public static final RegistryObject<BlockItem> STARDUST_STAIRS_ITEM = ItemRegistry.ITEMS.register("stardust_stairs", () -> new BlockItem(BlockRegistry.STARDUST_STAIRS.get(), new Properties()));
	public static final RegistryObject<BlockItem> STARDUST_FENCE_ITEM = ItemRegistry.ITEMS.register("stardust_fence", () -> new BlockItem(BlockRegistry.STARDUST_FENCE.get(), new Properties()));
	public static final RegistryObject<BlockItem> STARDUST_FENCE_GATE_ITEM = ItemRegistry.ITEMS.register("stardust_fence_gate", () -> new BlockItem(BlockRegistry.STARDUST_FENCE_GATE.get(), new Properties()));
	public static final RegistryObject<BlockItem> STARDUST_DOOR_ITEM = ItemRegistry.ITEMS.register("stardust_door", () -> new BlockItem(BlockRegistry.STARDUST_DOOR.get(), new Properties()));
	public static final RegistryObject<BlockItem> STARDUST_TRAPDOOR_ITEM = ItemRegistry.ITEMS.register("stardust_trapdoor", () -> new BlockItem(BlockRegistry.STARDUST_TRAPDOOR.get(), new Properties()));
	public static final RegistryObject<BlockItem> STARDUST_BUTTON_ITEM = ItemRegistry.ITEMS.register("stardust_button", () -> new BlockItem(BlockRegistry.STARDUST_BUTTON.get(), new Properties()));
	public static final RegistryObject<BlockItem> STARDUST_PRESSURE_PLATE_ITEM = ItemRegistry.ITEMS.register("stardust_pressure_plate", () -> new BlockItem(BlockRegistry.STARDUST_PRESSURE_PLATE.get(), new Properties()));
	public static final RegistryObject<SignItem> STARDUST_SIGN_ITEM = ItemRegistry.ITEMS.register("stardust_sign", () -> new SignItem(new Properties().stacksTo(16), BlockRegistry.STARDUST_SIGN.get(), BlockRegistry.STARDUST_WALL_SIGN.get()));
	public static final RegistryObject<HangingSignItem> STARDUST_HANGING_SIGN_ITEM = ItemRegistry.ITEMS.register("stardust_hanging_sign", () -> new HangingSignItem(BlockRegistry.STARDUST_HANGING_SIGN.get(), BlockRegistry.STARDUST_WALL_HANGING_SIGN.get(), new Properties().stacksTo(16)));
	public static final RegistryObject<BlockItem> STARDUST_SAPLING_ITEM = ItemRegistry.ITEMS.register("stardust_sapling", () -> new BlockItem(BlockRegistry.STARDUST_SAPLING.get(), new Properties()));
	public static final RegistryObject<BlockItem> BLOOD_SAND_ITEM = ItemRegistry.ITEMS.register("blood_sand", () -> new BlockItem(BlockRegistry.BLOOD_SAND.get(), new Properties()));
	public static final RegistryObject<BlockItem> BLOOD_SANDSTONE_ITEM = ItemRegistry.ITEMS.register("blood_sandstone", () -> new BlockItem(BlockRegistry.BLOOD_SANDSTONE.get(), new Properties()));
	public static final RegistryObject<BlockItem> BLOOD_SANDSTONE_SLAB_ITEM = ItemRegistry.ITEMS.register("blood_sandstone_slab", () -> new BlockItem(BlockRegistry.BLOOD_SANDSTONE_SLAB.get(), new Properties()));
	public static final RegistryObject<BlockItem> BLOOD_SANDSTONE_STAIRS_ITEM = ItemRegistry.ITEMS.register("blood_sandstone_stairs", () -> new BlockItem(BlockRegistry.BLOOD_SANDSTONE_STAIRS.get(), new Properties()));
	public static final RegistryObject<BlockItem> BLOOD_SANDSTONE_WALL_ITEM = ItemRegistry.ITEMS.register("blood_sandstone_wall", () -> new BlockItem(BlockRegistry.BLOOD_SANDSTONE_WALL.get(), new Properties()));
	public static final RegistryObject<BlockItem> CHISELED_BLOOD_SANDSTONE_ITEM = ItemRegistry.ITEMS.register("chiseled_blood_sandstone", () -> new BlockItem(BlockRegistry.CHISELED_BLOOD_SANDSTONE.get(), new Properties()));
	public static final RegistryObject<BlockItem> CUT_BLOOD_SANDSTONE_ITEM = ItemRegistry.ITEMS.register("cut_blood_sandstone", () -> new BlockItem(BlockRegistry.CUT_BLOOD_SANDSTONE.get(), new Properties()));
	public static final RegistryObject<BlockItem> CUT_BLOOD_SANDSTONE_SLAB_ITEM = ItemRegistry.ITEMS.register("cut_blood_sandstone_slab", () -> new BlockItem(BlockRegistry.CUT_BLOOD_SANDSTONE_SLAB.get(), new Properties()));
	public static final RegistryObject<BlockItem> SMOOTH_BLOOD_SANDSTONE_ITEM = ItemRegistry.ITEMS.register("smooth_blood_sandstone", () -> new BlockItem(BlockRegistry.SMOOTH_BLOOD_SANDSTONE.get(), new Properties()));
	public static final RegistryObject<BlockItem> SMOOTH_BLOOD_SANDSTONE_SLAB_ITEM = ItemRegistry.ITEMS.register("smooth_blood_sandstone_slab", () -> new BlockItem(BlockRegistry.SMOOTH_BLOOD_SANDSTONE_SLAB.get(), new Properties()));
	public static final RegistryObject<BlockItem> SMOOTH_BLOOD_SANDSTONE_STAIRS_ITEM = ItemRegistry.ITEMS.register("smooth_blood_sandstone_stairs", () -> new BlockItem(BlockRegistry.SMOOTH_BLOOD_SANDSTONE_STAIRS.get(), new Properties()));
	public static final RegistryObject<BlockItem> DEATHWEED_ITEM = ItemRegistry.ITEMS.register("deathweed", () -> new BlockItem(BlockRegistry.DEATHWEED.get(), new Properties()));
	public static final RegistryObject<BlockItem> ASTRAL_ORE_ITEM = ItemRegistry.ITEMS.register("astral_ore", () -> new BlockItem(BlockRegistry.ASTRAL_ORE.get(), new Properties()));
	public static final RegistryObject<BlockItem> ASTRAL_CRYSTAL_ITEM = ItemRegistry.ITEMS.register("astral_crystal", () -> new BlockItem(BlockRegistry.ASTRAL_CRYSTAL.get(), new Properties()));
	public static final RegistryObject<BlockItem> STARSTORM_CRYSTAL_ITEM = ItemRegistry.ITEMS.register("starstorm_crystal", () -> new BlockItem(BlockRegistry.STARSTORM_CRYSTAL.get(), new Properties()));
	public static final RegistryObject<BlockItem> BIODOME_LIFE_SUPPORT_UNIT_ITEM = ItemRegistry.ITEMS.register("biodome_life_support_unit", () -> new BlockItem(BlockRegistry.BIODOME_LIFE_SUPPORT_UNIT.get(), new Properties()));
}