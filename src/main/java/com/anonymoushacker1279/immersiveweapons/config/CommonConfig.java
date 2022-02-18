package com.anonymoushacker1279.immersiveweapons.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class CommonConfig {

	public static final ForgeConfigSpec COMMON_SPEC;
	public static final CommonConfig COMMON;

	public static ForgeConfigSpec.ConfigValue<Integer> MAX_SMOKE_BOMB_PARTICLES;
	public static ForgeConfigSpec.ConfigValue<Boolean> BULLETS_BREAK_GLASS;
	public static ForgeConfigSpec.ConfigValue<Boolean> DYING_SOLDIER_SPAWN;
	public static ForgeConfigSpec.ConfigValue<Boolean> WANDERING_WARRIOR_SPAWN;
	public static ForgeConfigSpec.ConfigValue<Boolean> HANS_SPAWN;
	public static ForgeConfigSpec.ConfigValue<Boolean> LAVA_REVENANT_SPAWN;
	public static ForgeConfigSpec.ConfigValue<Boolean> ROCK_SPIDER_SPAWN;
	public static ForgeConfigSpec.ConfigValue<Boolean> CELESTIAL_TOWER_SPAWN;
	public static ForgeConfigSpec.ConfigValue<Boolean> TILTROS_ENABLED;
	public static ForgeConfigSpec.ConfigValue<Integer> CELESTIAL_TOWER_XZ_SPAWN_CHECK_RADIUS;
	public static ForgeConfigSpec.ConfigValue<Integer> CELESTIAL_TOWER_Y_SPAWN_CHECK_RADIUS;
	public static ForgeConfigSpec.ConfigValue<String> CELESTIAL_TOWER_MINIONS_WAVE_SIZE_MODIFIER;
	public static ForgeConfigSpec.ConfigValue<List<Integer>> MOLTEN_ORE_CONFIG;
	public static ForgeConfigSpec.ConfigValue<List<Integer>> NETHER_SULFUR_ORE_CONFIG;
	public static ForgeConfigSpec.ConfigValue<List<Integer>> SULFUR_ORE_CONFIG;
	public static ForgeConfigSpec.ConfigValue<List<Integer>> DEEPSLATE_SULFUR_ORE_CONFIG;
	public static ForgeConfigSpec.ConfigValue<List<Integer>> COBALT_ORE_CONFIG;
	public static ForgeConfigSpec.ConfigValue<List<Integer>> DEEPSLATE_COBALT_ORE_CONFIG;
	public static ForgeConfigSpec.ConfigValue<Integer> MAX_ABANDONED_FACTORY_DISTANCE;
	public static ForgeConfigSpec.ConfigValue<Integer> MIN_ABANDONED_FACTORY_DISTANCE;
	public static ForgeConfigSpec.ConfigValue<Integer> MAX_PITFALL_TRAP_DISTANCE;
	public static ForgeConfigSpec.ConfigValue<Integer> MIN_PITFALL_TRAP_DISTANCE;
	public static ForgeConfigSpec.ConfigValue<Integer> MAX_BEAR_TRAP_DISTANCE;
	public static ForgeConfigSpec.ConfigValue<Integer> MIN_BEAR_TRAP_DISTANCE;
	public static ForgeConfigSpec.ConfigValue<Integer> MAX_LANDMINE_TRAP_DISTANCE;
	public static ForgeConfigSpec.ConfigValue<Integer> MIN_LANDMINE_TRAP_DISTANCE;
	public static ForgeConfigSpec.ConfigValue<Integer> MAX_UNDERGROUND_BUNKER_DISTANCE;
	public static ForgeConfigSpec.ConfigValue<Integer> MIN_UNDERGROUND_BUNKER_DISTANCE;
	public static ForgeConfigSpec.ConfigValue<Integer> MAX_BATTLEFIELD_CAMP_DISTANCE;
	public static ForgeConfigSpec.ConfigValue<Integer> MIN_BATTLEFIELD_CAMP_DISTANCE;
	public static ForgeConfigSpec.ConfigValue<Integer> MAX_BATTLEFIELD_VILLAGE_DISTANCE;
	public static ForgeConfigSpec.ConfigValue<Integer> MIN_BATTLEFIELD_VILLAGE_DISTANCE;
	public static ForgeConfigSpec.ConfigValue<Integer> MAX_CLOUD_ISLAND_DISTANCE;
	public static ForgeConfigSpec.ConfigValue<Integer> MIN_CLOUD_ISLAND_DISTANCE;
	public static ForgeConfigSpec.ConfigValue<Integer> MAX_CAMPSITE_DISTANCE;
	public static ForgeConfigSpec.ConfigValue<Integer> MIN_CAMPSITE_DISTANCE;
	public static ForgeConfigSpec.ConfigValue<Integer> MAX_BATTLEFIELD_HOUSE_DISTANCE;
	public static ForgeConfigSpec.ConfigValue<Integer> MIN_BATTLEFIELD_HOUSE_DISTANCE;
	public static ForgeConfigSpec.ConfigValue<Integer> MAX_OUTHOUSE_DISTANCE;
	public static ForgeConfigSpec.ConfigValue<Integer> MIN_OUTHOUSE_DISTANCE;
	public static ForgeConfigSpec.ConfigValue<Integer> MAX_WATER_TOWER_DISTANCE;
	public static ForgeConfigSpec.ConfigValue<Integer> MIN_WATER_TOWER_DISTANCE;
	public static ForgeConfigSpec.ConfigValue<Integer> MAX_GRAVEYARD_DISTANCE;
	public static ForgeConfigSpec.ConfigValue<Integer> MIN_GRAVEYARD_DISTANCE;

	CommonConfig(ForgeConfigSpec.Builder builder) {
		builder.push("Server Configuration");

		builder.push("Miscellaneous");
		MAX_SMOKE_BOMB_PARTICLES = builder
				.comment("Set the maximum number of particles produced by the smoke bomb - Default 96\n" +
						"Setting this higher can make clients laggy, setting to 0 effectively disables it")
				.translation("config.immersiveweapons.max_smoke_bomb_particles")
				.define("max_smoke_bomb_particles", 96);
		BULLETS_BREAK_GLASS = builder
				.comment("Enable bullets breaking glass - Default true")
				.translation("config.immersiveweapons.bullets_break_glass")
				.define("bullets_break_glass", true);
		TILTROS_ENABLED = builder
				.comment("Enable the Tiltros dimension portal - Default true")
				.translation("config.immersiveweapons.tiltros_enabled")
				.define("tiltros_enabled", true);
		builder.pop();

		builder.push("Entity Spawns");
		DYING_SOLDIER_SPAWN = builder
				.comment("Enable the natural spawning of Dying Soldiers - Default true")
				.translation("config.immersiveweapons.dying_soldier_spawn")
				.define("dying_soldiers_spawn", true);
		WANDERING_WARRIOR_SPAWN = builder
				.comment("Enable the natural spawning of Wandering Warriors - Default true")
				.translation("config.immersiveweapons.wandering_warrior_spawn")
				.define("wandering_warriors_spawn", true);
		HANS_SPAWN = builder
				.comment("Enable the natural spawning of Hans the Almighty - Default true")
				.translation("config.immersiveweapons.hans_spawn")
				.define("hans_spawn", true);
		LAVA_REVENANT_SPAWN = builder
				.comment("Enable the natural spawning of Lava Revenants - Default true")
				.translation("config.immersiveweapons.lava_revenant_spawn")
				.define("lava_revenant_spawn", true);
		ROCK_SPIDER_SPAWN = builder
				.comment("Enable the natural spawning of Rock Spiders - Default true")
				.translation("config.immersiveweapons.rock_spider_spawn")
				.define("rock_spider_spawn", true);
		CELESTIAL_TOWER_SPAWN = builder
				.comment("Enable the natural spawning of Celestial Towers - Default true")
				.translation("config.immersiveweapons.celestial_tower_spawn")
				.define("celestial_tower_spawn", true);
		builder.pop();

		builder.push("Celestial Tower");
		CELESTIAL_TOWER_XZ_SPAWN_CHECK_RADIUS = builder
				.comment("Set the X and Z spawn checking radius for the Celestial Tower.\n" +
						"Setting this higher will negatively impact server ticks in Tiltros, but make Celestial Lanterns more effective - Default 56")
				.translation("config.immersiveweapons.celestial_tower_xz_spawn_check_radius")
				.define("celestial_tower_xz_spawn_check_radius", 56);
		CELESTIAL_TOWER_Y_SPAWN_CHECK_RADIUS = builder
				.comment("Set the Y spawn checking radius for the Celestial Tower.\n" +
						"Setting this higher will negatively impact server ticks in Tiltros, but make Celestial Lanterns more effective - Default 20")
				.translation("config.immersiveweapons.celestial_tower_y_spawn_check_radius")
				.define("celestial_tower_y_spawn_check_radius", 20);
		CELESTIAL_TOWER_MINIONS_WAVE_SIZE_MODIFIER = builder
				.comment("""
						Multiplier to change the wave size from Celestial Tower summons.
						Set less than 1 to reduce, greater than 1 to increase.
						Increasing the wave size will negatively affect the server ticks in Tiltros. - Default 1.0""")
				.translation("config.immersiveweapons.celestial_tower_minions_wave_size_modifier")
				.define("celestial_tower_minions_wave_size_modifier", "1.0");
		builder.pop();

		builder.push("Structure Generation");

		builder.push("Abandoned Factory");
		MAX_ABANDONED_FACTORY_DISTANCE = builder
				.comment("Maximum distance in chunks between Abandoned Factories - Default 120")
				.translation("config.immersiveweapons.max_abandoned_factory_distance")
				.define("max_abandoned_factory_distance", 120);
		MIN_ABANDONED_FACTORY_DISTANCE = builder
				.comment("Minimum distance in chunks between Abandoned Factories - Default 90")
				.translation("config.immersiveweapons.min_abandoned_factory_distance")
				.define("min_abandoned_factory_distance", 90);
		builder.pop();

		builder.push("Pitfall Trap");
		MAX_PITFALL_TRAP_DISTANCE = builder
				.comment("Maximum distance in chunks between Pitfall Traps - Default 8")
				.translation("config.immersiveweapons.max_pitfall_trap_distance")
				.define("max_pitfall_trap_distance", 8);
		MIN_PITFALL_TRAP_DISTANCE = builder
				.comment("Minimum distance in chunks between Pitfall Traps - Default 2")
				.translation("config.immersiveweapons.min_pitfall_trap_distance")
				.define("min_pitfall_trap_distance", 2);
		builder.pop();

		builder.push("Bear Trap");
		MAX_BEAR_TRAP_DISTANCE = builder
				.comment("Maximum distance in chunks between Bear Traps - Default 10")
				.translation("config.immersiveweapons.max_bear_trap_distance")
				.define("max_bear_trap_distance", 10);
		MIN_BEAR_TRAP_DISTANCE = builder
				.comment("Minimum distance in chunks between Bear Traps - Default 4")
				.translation("config.immersiveweapons.min_bear_trap_distance")
				.define("min_bear_trap_distance", 4);
		builder.pop();

		builder.push("Landmine Trap");
		MAX_LANDMINE_TRAP_DISTANCE = builder
				.comment("Minimum distance in chunks between Landmine Traps - Default 12")
				.translation("config.immersiveweapons.max_landmine_trap_distance")
				.define("max_landmine_trap_distance", 12);
		MIN_LANDMINE_TRAP_DISTANCE = builder
				.comment("Minimum distance in chunks between Landmine Traps - Default 6")
				.translation("config.immersiveweapons.min_landmine_trap_distance")
				.define("min_landmine_trap_distance", 6);
		builder.pop();

		builder.push("Underground Bunker");
		MAX_UNDERGROUND_BUNKER_DISTANCE = builder
				.comment("Maximum distance in chunks between Underground Bunkers - Default 110")
				.translation("config.immersiveweapons.max_underground_bunker_distance")
				.define("max_underground_bunker_distance", 110);
		MIN_UNDERGROUND_BUNKER_DISTANCE = builder
				.comment("Minimum distance in chunks between Underground Bunkers - Default 80")
				.translation("config.immersiveweapons.min_underground_bunker_distance")
				.define("min_underground_bunker_distance", 80);
		builder.pop();

		builder.push("Battlefield Camp");
		MAX_BATTLEFIELD_CAMP_DISTANCE = builder
				.comment("Maximum distance in chunks between Battlefield Camps - Default 16")
				.translation("config.immersiveweapons.max_battlefield_camp_distance")
				.define("max_battlefield_camp_distance", 16);
		MIN_BATTLEFIELD_CAMP_DISTANCE = builder
				.comment("Minimum distance in chunks between Battlefield Camps - Default 4")
				.translation("config.immersiveweapons.min_battlefield_camp_distance")
				.define("min_battlefield_camp_distance", 4);
		builder.pop();

		builder.push("Battlefield Village");
		MAX_BATTLEFIELD_VILLAGE_DISTANCE = builder
				.comment("Maximum distance in chunks between Battlefield Villages - Default 30")
				.translation("config.immersiveweapons.max_battlefield_village_distance")
				.define("max_battlefield_village_distance", 30);
		MIN_BATTLEFIELD_VILLAGE_DISTANCE = builder
				.comment("Minimum distance in chunks between Battlefield Villages - Default 10")
				.translation("config.immersiveweapons.min_battlefield_village_distance")
				.define("min_battlefield_village_distance", 10);
		builder.pop();

		builder.push("Cloud Island");
		MAX_CLOUD_ISLAND_DISTANCE = builder
				.comment("Maximum distance in chunks between Cloud Islands - Default 90")
				.translation("config.immersiveweapons.max_cloud_island_distance")
				.define("max_cloud_island_distance", 90);
		MIN_CLOUD_ISLAND_DISTANCE = builder
				.comment("Minimum distance in chunks between Cloud Islands - Default 70")
				.translation("config.immersiveweapons.min_cloud_island_distance")
				.define("min_cloud_island_distance", 70);
		builder.pop();

		builder.push("Campsite");
		MAX_CAMPSITE_DISTANCE = builder
				.comment("Maximum distance in chunks between Campsites - Default 50")
				.translation("config.immersiveweapons.max_campsite_distance")
				.define("max_campsite_distance", 50);
		MIN_CAMPSITE_DISTANCE = builder
				.comment("Minimum distance in chunks between Campsites - Default 30")
				.translation("config.immersiveweapons.min_campsite_distance")
				.define("min_campsite_distance", 30);
		builder.pop();

		builder.push("Battlefield House");
		MAX_BATTLEFIELD_HOUSE_DISTANCE = builder
				.comment("Maximum distance in chunks between Battlefield Houses - Default 12")
				.translation("config.immersiveweapons.max_battlefield_house_distance")
				.define("max_battlefield_house_distance", 12);
		MIN_BATTLEFIELD_HOUSE_DISTANCE = builder
				.comment("Minimum distance in chunks between Battlefield Houses - Default 4")
				.translation("config.immersiveweapons.min_battlefield_house_distance")
				.define("min_battlefield_house_distance", 4);
		builder.pop();

		builder.push("Outhouse");
		MAX_OUTHOUSE_DISTANCE = builder
				.comment("Maximum distance in chunks between Outhouses - Default 40")
				.translation("config.immersiveweapons.max_outhouse_distance")
				.define("max_outhouse_distance", 40);
		MIN_OUTHOUSE_DISTANCE = builder
				.comment("Minimum distance in chunks between Outhouses - Default 20")
				.translation("config.immersiveweapons.min_outhouse_distance")
				.define("min_outhouse_distance", 20);
		builder.pop();

		builder.push("Water Tower");
		MAX_WATER_TOWER_DISTANCE = builder
				.comment("Maximum distance in chunks between Water Towers - Default 30")
				.translation("config.immersiveweapons.max_water_tower_distance")
				.define("max_water_tower_distance", 30);
		MIN_WATER_TOWER_DISTANCE = builder
				.comment("Minimum distance in chunks between Water Towers - Default 12")
				.translation("config.immersiveweapons.min_water_tower_distance")
				.define("min_water_tower_distance", 12);
		builder.pop();

		builder.push("Graveyard");
		MAX_GRAVEYARD_DISTANCE = builder
				.comment("Maximum distance in chunks between Graveyards - Default 50")
				.translation("config.immersiveweapons.max_graveyard_distance")
				.define("max_graveyard_distance", 50);
		MIN_GRAVEYARD_DISTANCE = builder
				.comment("Minimum distance in chunks between Graveyards - Default 30")
				.translation("config.immersiveweapons.min_graveyard_distance")
				.define("min_graveyard_distance", 30);
		builder.pop();

		builder.pop();

		builder.push("Ore Generation");
		List<Integer> molten_ore_config_list = new ArrayList<>(3);
		molten_ore_config_list.addAll(Arrays.asList(4, 8, 64));
		MOLTEN_ORE_CONFIG = builder
				.comment("Configuration for Molten Ore. Specified as a list. [ore_per_vein, veins_per_chunk, max_y]. Default: [4, 8, 64]")
				.translation("config.immersiveweapons.molten_ore_config")
				.define("molten_ore_config", molten_ore_config_list);
		List<Integer> nether_sulfur_ore_config_list = new ArrayList<>(2);
		nether_sulfur_ore_config_list.addAll(Arrays.asList(12, 16));
		NETHER_SULFUR_ORE_CONFIG = builder
				.comment("Configuration for Nether Sulfur Ore. Specified as a list. [ore_per_vein, veins_per_chunk]. Default: [12, 16]")
				.translation("config.immersiveweapons.nether_sulfur_ore_config")
				.define("nether_sulfur_ore_config", nether_sulfur_ore_config_list);
		List<Integer> sulfur_ore_config_list = new ArrayList<>(2);
		sulfur_ore_config_list.addAll(Arrays.asList(8, 14));
		SULFUR_ORE_CONFIG = builder
				.comment("Configuration for Sulfur Ore. Specified as a list. [ore_per_vein, veins_per_chunk]. Default: [8, 14]")
				.translation("config.immersiveweapons.sulfur_ore_config")
				.define("sulfur_ore_config", sulfur_ore_config_list);
		List<Integer> cobalt_ore_config_list = new ArrayList<>(3);
		cobalt_ore_config_list.addAll(Arrays.asList(6, 12, 24));
		COBALT_ORE_CONFIG = builder
				.comment("Configuration for Cobalt Ore. Specified as a list. [ore_per_vein, veins_per_chunk, blocks_below_top]. Default: [6, 12, 24]")
				.translation("config.immersiveweapons.cobalt_ore_config")
				.define("cobalt_ore_config", cobalt_ore_config_list);
		List<Integer> deepslate_sulfur_ore_config_list = new ArrayList<>(2);
		deepslate_sulfur_ore_config_list.addAll(Arrays.asList(8, 14));
		DEEPSLATE_SULFUR_ORE_CONFIG = builder
				.comment("Configuration for Deepslate Sulfur Ore. Specified as a list. [ore_per_vein, veins_per_chunk]. Default: [8, 14]")
				.translation("config.immersiveweapons.deepslate_sulfur_ore_config")
				.define("deepslate_sulfur_ore_config", deepslate_sulfur_ore_config_list);
		List<Integer> deepslate_cobalt_ore_config_list = new ArrayList<>(2);
		deepslate_cobalt_ore_config_list.addAll(Arrays.asList(8, 16));
		DEEPSLATE_COBALT_ORE_CONFIG = builder
				.comment("Configuration for Deepslate Cobalt Ore. Specified as a list. [ore_per_vein, veins_per_chunk]. Default: [8, 16]")
				.translation("config.immersiveweapons.deepslate_cobalt_ore_config")
				.define("deepslate_cobalt_ore_config", deepslate_cobalt_ore_config_list);
		builder.pop();

		builder.pop();
	}

	static {
		Pair<CommonConfig, ForgeConfigSpec> commonConfigForgeConfigSpecPair = new Builder().configure(CommonConfig::new);

		COMMON_SPEC = commonConfigForgeConfigSpecPair.getRight();
		COMMON = commonConfigForgeConfigSpecPair.getLeft();
	}
}