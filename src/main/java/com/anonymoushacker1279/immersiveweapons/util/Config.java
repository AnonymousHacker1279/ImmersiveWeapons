package com.anonymoushacker1279.immersiveweapons.util;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

public class Config {

	private static final ForgeConfigSpec.Builder COMMON_CONFIG_BUILDER = new ForgeConfigSpec.Builder();
	public static ForgeConfigSpec.ConfigValue<Boolean> TESLA_ARMOR_EFFECT_SOUND;
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
	static ForgeConfigSpec.ConfigValue<Integer> MAX_ABANDONED_FACTORY_DISTANCE;
	static ForgeConfigSpec.ConfigValue<Integer> MIN_ABANDONED_FACTORY_DISTANCE;
	static ForgeConfigSpec.ConfigValue<Integer> MAX_PITFALL_TRAP_DISTANCE;
	static ForgeConfigSpec.ConfigValue<Integer> MIN_PITFALL_TRAP_DISTANCE;
	static ForgeConfigSpec.ConfigValue<Integer> MAX_BEAR_TRAP_DISTANCE;
	static ForgeConfigSpec.ConfigValue<Integer> MIN_BEAR_TRAP_DISTANCE;
	static ForgeConfigSpec.ConfigValue<Integer> MAX_LANDMINE_TRAP_DISTANCE;
	static ForgeConfigSpec.ConfigValue<Integer> MIN_LANDMINE_TRAP_DISTANCE;
	static ForgeConfigSpec.ConfigValue<Integer> MAX_UNDERGROUND_BUNKER_DISTANCE;
	static ForgeConfigSpec.ConfigValue<Integer> MIN_UNDERGROUND_BUNKER_DISTANCE;
	static ForgeConfigSpec.ConfigValue<Integer> MAX_BATTLEFIELD_CAMP_DISTANCE;
	static ForgeConfigSpec.ConfigValue<Integer> MIN_BATTLEFIELD_CAMP_DISTANCE;
	static ForgeConfigSpec.ConfigValue<Integer> MAX_BATTLEFIELD_VILLAGE_DISTANCE;
	static ForgeConfigSpec.ConfigValue<Integer> MIN_BATTLEFIELD_VILLAGE_DISTANCE;
	static ForgeConfigSpec.ConfigValue<Integer> MAX_CLOUD_ISLAND_DISTANCE;
	static ForgeConfigSpec.ConfigValue<Integer> MIN_CLOUD_ISLAND_DISTANCE;
	static ForgeConfigSpec.ConfigValue<Integer> MAX_CAMPSITE_DISTANCE;
	static ForgeConfigSpec.ConfigValue<Integer> MIN_CAMPSITE_DISTANCE;
	static ForgeConfigSpec.ConfigValue<Integer> MAX_BATTLEFIELD_HOUSE_DISTANCE;
	static ForgeConfigSpec.ConfigValue<Integer> MIN_BATTLEFIELD_HOUSE_DISTANCE;
	static ForgeConfigSpec.ConfigValue<Integer> MAX_OUTHOUSE_DISTANCE;
	static ForgeConfigSpec.ConfigValue<Integer> MIN_OUTHOUSE_DISTANCE;
	static ForgeConfigSpec.ConfigValue<Integer> MAX_WATER_TOWER_DISTANCE;
	static ForgeConfigSpec.ConfigValue<Integer> MIN_WATER_TOWER_DISTANCE;
	static ForgeConfigSpec.ConfigValue<Integer> MAX_GRAVEYARD_DISTANCE;
	static ForgeConfigSpec.ConfigValue<Integer> MIN_GRAVEYARD_DISTANCE;
	private static ForgeConfigSpec COMMON_CONFIG;

	static {
		initConfig();
	}

	/**
	 * Initialize the configuration file.
	 */
	private static void initConfig() {
		COMMON_CONFIG_BUILDER.push("General");

		COMMON_CONFIG_BUILDER.push("Miscellaneous");
		TESLA_ARMOR_EFFECT_SOUND = COMMON_CONFIG_BUILDER
				.comment("Enable the Tesla Armor effect sound - Default true")
				.translation("config.immersiveweapons.tesla_armor_effect_sound")
				.define("tesla_armor_effect_sound", true);
		MAX_SMOKE_BOMB_PARTICLES = COMMON_CONFIG_BUILDER
				.comment("Enable bullets breaking glass - Default true")
				.translation("config.immersiveweapons.max_smoke_bomb_particles")
				.define("max_smoke_bomb_particles", 96);
		BULLETS_BREAK_GLASS = COMMON_CONFIG_BUILDER
				.comment("Enable bullets breaking glass - Default true")
				.translation("config.immersiveweapons.bullets_break_glass")
				.define("bullets_break_glass", true);
		TILTROS_ENABLED = COMMON_CONFIG_BUILDER
				.comment("Enable the Tiltros dimension portal - Default true")
				.translation("config.immersiveweapons.tiltros_enabled")
				.define("tiltros_enabled", true);
		COMMON_CONFIG_BUILDER.pop();

		COMMON_CONFIG_BUILDER.push("Entity Spawns");
		DYING_SOLDIER_SPAWN = COMMON_CONFIG_BUILDER
				.comment("Enable the natural spawning of Dying Soldiers - Default true")
				.translation("config.immersiveweapons.dying_soldier_spawn")
				.define("dying_soldiers_spawn", true);
		WANDERING_WARRIOR_SPAWN = COMMON_CONFIG_BUILDER
				.comment("Enable the natural spawning of Wandering Warriors - Default true")
				.translation("config.immersiveweapons.wandering_warrior_spawn")
				.define("wandering_warriors_spawn", true);
		HANS_SPAWN = COMMON_CONFIG_BUILDER
				.comment("Enable the natural spawning of Hans the Almighty - Default true")
				.translation("config.immersiveweapons.hans_spawn")
				.define("hans_spawn", true);
		LAVA_REVENANT_SPAWN = COMMON_CONFIG_BUILDER
				.comment("Enable the natural spawning of Lava Revenants - Default true")
				.translation("config.immersiveweapons.lava_revenant_spawn")
				.define("lava_revenant_spawn", true);
		ROCK_SPIDER_SPAWN = COMMON_CONFIG_BUILDER
				.comment("Enable the natural spawning of Rock Spiders - Default true")
				.translation("config.immersiveweapons.rock_spider_spawn")
				.define("rock_spider_spawn", true);
		CELESTIAL_TOWER_SPAWN = COMMON_CONFIG_BUILDER
				.comment("Enable the natural spawning of Celestial Towers - Default true")
				.translation("config.immersiveweapons.celestial_tower_spawn")
				.define("celestial_tower_spawn", true);
		COMMON_CONFIG_BUILDER.pop();

		COMMON_CONFIG_BUILDER.push("Celestial Tower");
		CELESTIAL_TOWER_XZ_SPAWN_CHECK_RADIUS = COMMON_CONFIG_BUILDER
				.comment("Set the X and Z spawn checking radius for the Celestial Tower.\n" +
						"Setting this higher will negatively impact server ticks in Tiltros, but make Celestial Lanterns more effective - Default 56")
				.translation("config.immersiveweapons.celestial_tower_xz_spawn_check_radius")
				.define("celestial_tower_xz_spawn_check_radius", 56);
		CELESTIAL_TOWER_Y_SPAWN_CHECK_RADIUS = COMMON_CONFIG_BUILDER
				.comment("Set the Y spawn checking radius for the Celestial Tower.\n" +
						"Setting this higher will negatively impact server ticks in Tiltros, but make Celestial Lanterns more effective - Default 20")
				.translation("config.immersiveweapons.celestial_tower_y_spawn_check_radius")
				.define("celestial_tower_y_spawn_check_radius", 20);
		CELESTIAL_TOWER_MINIONS_WAVE_SIZE_MODIFIER = COMMON_CONFIG_BUILDER
				.comment("""
						Multiplier to change the wave size from Celestial Tower summons.
						Set less than 1 to reduce, greater than 1 to increase.
						Increasing the wave size will negatively affect the server ticks in Tiltros. - Default 1.0""")
				.translation("config.immersiveweapons.celestial_tower_minions_wave_size_modifier")
				.define("celestial_tower_minions_wave_size_modifier", "1.0");
		COMMON_CONFIG_BUILDER.pop();

		COMMON_CONFIG_BUILDER.push("Structure Generation");

		COMMON_CONFIG_BUILDER.push("Abandoned Factory");
		MAX_ABANDONED_FACTORY_DISTANCE = COMMON_CONFIG_BUILDER
				.comment("Maximum distance in chunks between Abandoned Factories - Default 120")
				.translation("config.immersiveweapons.max_abandoned_factory_distance")
				.define("max_abandoned_factory_distance", 120);
		MIN_ABANDONED_FACTORY_DISTANCE = COMMON_CONFIG_BUILDER
				.comment("Minimum distance in chunks between Abandoned Factories - Default 90")
				.translation("config.immersiveweapons.min_abandoned_factory_distance")
				.define("min_abandoned_factory_distance", 90);
		COMMON_CONFIG_BUILDER.pop();

		COMMON_CONFIG_BUILDER.push("Pitfall Trap");
		MAX_PITFALL_TRAP_DISTANCE = COMMON_CONFIG_BUILDER
				.comment("Maximum distance in chunks between Pitfall Traps - Default 8")
				.translation("config.immersiveweapons.max_pitfall_trap_distance")
				.define("max_pitfall_trap_distance", 8);
		MIN_PITFALL_TRAP_DISTANCE = COMMON_CONFIG_BUILDER
				.comment("Minimum distance in chunks between Pitfall Traps - Default 2")
				.translation("config.immersiveweapons.min_pitfall_trap_distance")
				.define("min_pitfall_trap_distance", 2);
		COMMON_CONFIG_BUILDER.pop();

		COMMON_CONFIG_BUILDER.push("Bear Trap");
		MAX_BEAR_TRAP_DISTANCE = COMMON_CONFIG_BUILDER
				.comment("Maximum distance in chunks between Bear Traps - Default 10")
				.translation("config.immersiveweapons.max_bear_trap_distance")
				.define("max_bear_trap_distance", 10);
		MIN_BEAR_TRAP_DISTANCE = COMMON_CONFIG_BUILDER
				.comment("Minimum distance in chunks between Bear Traps - Default 4")
				.translation("config.immersiveweapons.min_bear_trap_distance")
				.define("min_bear_trap_distance", 4);
		COMMON_CONFIG_BUILDER.pop();

		COMMON_CONFIG_BUILDER.push("Landmine Trap");
		MAX_LANDMINE_TRAP_DISTANCE = COMMON_CONFIG_BUILDER
				.comment("Minimum distance in chunks between Landmine Traps - Default 12")
				.translation("config.immersiveweapons.max_landmine_trap_distance")
				.define("max_landmine_trap_distance", 12);
		MIN_LANDMINE_TRAP_DISTANCE = COMMON_CONFIG_BUILDER
				.comment("Minimum distance in chunks between Landmine Traps - Default 6")
				.translation("config.immersiveweapons.min_landmine_trap_distance")
				.define("min_landmine_trap_distance", 6);
		COMMON_CONFIG_BUILDER.pop();

		COMMON_CONFIG_BUILDER.push("Underground Bunker");
		MAX_UNDERGROUND_BUNKER_DISTANCE = COMMON_CONFIG_BUILDER
				.comment("Maximum distance in chunks between Underground Bunkers - Default 110")
				.translation("config.immersiveweapons.max_underground_bunker_distance")
				.define("max_underground_bunker_distance", 110);
		MIN_UNDERGROUND_BUNKER_DISTANCE = COMMON_CONFIG_BUILDER
				.comment("Minimum distance in chunks between Underground Bunkers - Default 80")
				.translation("config.immersiveweapons.min_underground_bunker_distance")
				.define("min_underground_bunker_distance", 80);
		COMMON_CONFIG_BUILDER.pop();

		COMMON_CONFIG_BUILDER.push("Battlefield Camp");
		MAX_BATTLEFIELD_CAMP_DISTANCE = COMMON_CONFIG_BUILDER
				.comment("Maximum distance in chunks between Battlefield Camps - Default 16")
				.translation("config.immersiveweapons.max_battlefield_camp_distance")
				.define("max_battlefield_camp_distance", 16);
		MIN_BATTLEFIELD_CAMP_DISTANCE = COMMON_CONFIG_BUILDER
				.comment("Minimum distance in chunks between Battlefield Camps - Default 4")
				.translation("config.immersiveweapons.min_battlefield_camp_distance")
				.define("min_battlefield_camp_distance", 4);
		COMMON_CONFIG_BUILDER.pop();

		COMMON_CONFIG_BUILDER.push("Battlefield Village");
		MAX_BATTLEFIELD_VILLAGE_DISTANCE = COMMON_CONFIG_BUILDER
				.comment("Maximum distance in chunks between Battlefield Villages - Default 30")
				.translation("config.immersiveweapons.max_battlefield_village_distance")
				.define("max_battlefield_village_distance", 30);
		MIN_BATTLEFIELD_VILLAGE_DISTANCE = COMMON_CONFIG_BUILDER
				.comment("Minimum distance in chunks between Battlefield Villages - Default 10")
				.translation("config.immersiveweapons.min_battlefield_village_distance")
				.define("min_battlefield_village_distance", 10);
		COMMON_CONFIG_BUILDER.pop();

		COMMON_CONFIG_BUILDER.push("Cloud Island");
		MAX_CLOUD_ISLAND_DISTANCE = COMMON_CONFIG_BUILDER
				.comment("Maximum distance in chunks between Cloud Islands - Default 90")
				.translation("config.immersiveweapons.max_cloud_island_distance")
				.define("max_cloud_island_distance", 90);
		MIN_CLOUD_ISLAND_DISTANCE = COMMON_CONFIG_BUILDER
				.comment("Minimum distance in chunks between Cloud Islands - Default 70")
				.translation("config.immersiveweapons.min_cloud_island_distance")
				.define("min_cloud_island_distance", 70);
		COMMON_CONFIG_BUILDER.pop();

		COMMON_CONFIG_BUILDER.push("Campsite");
		MAX_CAMPSITE_DISTANCE = COMMON_CONFIG_BUILDER
				.comment("Maximum distance in chunks between Campsites - Default 50")
				.translation("config.immersiveweapons.max_campsite_distance")
				.define("max_campsite_distance", 50);
		MIN_CAMPSITE_DISTANCE = COMMON_CONFIG_BUILDER
				.comment("Minimum distance in chunks between Campsites - Default 30")
				.translation("config.immersiveweapons.min_campsite_distance")
				.define("min_campsite_distance", 30);
		COMMON_CONFIG_BUILDER.pop();

		COMMON_CONFIG_BUILDER.push("Battlefield House");
		MAX_BATTLEFIELD_HOUSE_DISTANCE = COMMON_CONFIG_BUILDER
				.comment("Maximum distance in chunks between Battlefield Houses - Default 12")
				.translation("config.immersiveweapons.max_battlefield_house_distance")
				.define("max_battlefield_house_distance", 12);
		MIN_BATTLEFIELD_HOUSE_DISTANCE = COMMON_CONFIG_BUILDER
				.comment("Minimum distance in chunks between Battlefield Houses - Default 4")
				.translation("config.immersiveweapons.min_battlefield_house_distance")
				.define("min_battlefield_house_distance", 4);
		COMMON_CONFIG_BUILDER.pop();

		COMMON_CONFIG_BUILDER.push("Outhouse");
		MAX_OUTHOUSE_DISTANCE = COMMON_CONFIG_BUILDER
				.comment("Maximum distance in chunks between Outhouses - Default 40")
				.translation("config.immersiveweapons.max_outhouse_distance")
				.define("max_outhouse_distance", 40);
		MIN_OUTHOUSE_DISTANCE = COMMON_CONFIG_BUILDER
				.comment("Minimum distance in chunks between Outhouses - Default 20")
				.translation("config.immersiveweapons.min_outhouse_distance")
				.define("min_outhouse_distance", 20);
		COMMON_CONFIG_BUILDER.pop();

		COMMON_CONFIG_BUILDER.push("Water Tower");
		MAX_WATER_TOWER_DISTANCE = COMMON_CONFIG_BUILDER
				.comment("Maximum distance in chunks between Water Towers - Default 30")
				.translation("config.immersiveweapons.max_water_tower_distance")
				.define("max_water_tower_distance", 30);
		MIN_WATER_TOWER_DISTANCE = COMMON_CONFIG_BUILDER
				.comment("Minimum distance in chunks between Water Towers - Default 12")
				.translation("config.immersiveweapons.min_water_tower_distance")
				.define("min_water_tower_distance", 12);
		COMMON_CONFIG_BUILDER.pop();

		COMMON_CONFIG_BUILDER.push("Graveyard");
		MAX_GRAVEYARD_DISTANCE = COMMON_CONFIG_BUILDER
				.comment("Maximum distance in chunks between Graveyards - Default 50")
				.translation("config.immersiveweapons.max_graveyard_distance")
				.define("max_graveyard_distance", 50);
		MIN_GRAVEYARD_DISTANCE = COMMON_CONFIG_BUILDER
				.comment("Minimum distance in chunks between Graveyards - Default 30")
				.translation("config.immersiveweapons.min_graveyard_distance")
				.define("min_graveyard_distance", 30);
		COMMON_CONFIG_BUILDER.pop();

		COMMON_CONFIG_BUILDER.pop();

		COMMON_CONFIG_BUILDER.pop();
		COMMON_CONFIG = COMMON_CONFIG_BUILDER.build();
	}

	/**
	 * Setup a configuration file.
	 *
	 * @param path the <code>Path</code> of the file
	 */
	public static void setup(Path path) {
		CommentedFileConfig configData = CommentedFileConfig.builder(path)
				.sync()
				.autosave()
				.writingMode(WritingMode.REPLACE)
				.build();

		configData.load();
		COMMON_CONFIG.setConfig(configData);
	}

}