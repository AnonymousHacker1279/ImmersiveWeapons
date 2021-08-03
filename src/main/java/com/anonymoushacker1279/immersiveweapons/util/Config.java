package com.anonymoushacker1279.immersiveweapons.util;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

public class Config {

	private static final ForgeConfigSpec.Builder COMMON_CONFIG_BUILDER = new ForgeConfigSpec.Builder();
	static ForgeConfigSpec.ConfigValue<Integer> MAX_ABANDONED_FACTORY_DISTANCE;
	static ForgeConfigSpec.ConfigValue<Integer> MIN_ABANDONED_FACTORY_DISTANCE;
	static ForgeConfigSpec.ConfigValue<Integer> MAX_PITFALL_TRAP_DISTANCE;
	static ForgeConfigSpec.ConfigValue<Integer> MIN_PITFALL_TRAP_DISTANCE;
	static ForgeConfigSpec.ConfigValue<Integer> MAX_BEAR_TRAP_DISTANCE;
	static ForgeConfigSpec.ConfigValue<Integer> MIN_BEAR_TRAP_DISTANCE;
	static ForgeConfigSpec.ConfigValue<Integer> MAX_LANDMINE_TRAP_DISTANCE;
	static ForgeConfigSpec.ConfigValue<Integer> MIN_LANDMINE_TRAP_DISTANCE;
	public static ForgeConfigSpec.ConfigValue<Boolean> TESLA_ARMOR_EFFECT_SOUND;
	public static ForgeConfigSpec.ConfigValue<Integer> MAX_SMOKE_BOMB_PARTICLES;
	public static ForgeConfigSpec.ConfigValue<Boolean> BULLETS_BREAK_GLASS;
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
		COMMON_CONFIG_BUILDER.push(ImmersiveWeapons.MOD_ID + "-common");

		TESLA_ARMOR_EFFECT_SOUND = COMMON_CONFIG_BUILDER.comment("Enable/Disable the Tesla Armor effect sound - Default true").define("tesla_armor_effect_sound", true);
		MAX_SMOKE_BOMB_PARTICLES = COMMON_CONFIG_BUILDER.comment("Set the maximum number of particles produced by the smoke bomb - Default 96\nSetting this higher can make clients laggy, setting to 0 effectively disables it").define("max_smoke_bomb_particles", 96);
		BULLETS_BREAK_GLASS = COMMON_CONFIG_BUILDER.comment("Enable/Disable bullets breaking glass - Default true").define("bullets_break_glass", true);

		MAX_ABANDONED_FACTORY_DISTANCE = COMMON_CONFIG_BUILDER.comment("Maximum distance in chunks between Abandoned Factories - Default 120").define("max_abandoned_factory_distance", 120);
		MIN_ABANDONED_FACTORY_DISTANCE = COMMON_CONFIG_BUILDER.comment("Minimum distance in chunks between Abandoned Factories - Default 90").define("min_abandoned_factory_distance", 90);

		MAX_PITFALL_TRAP_DISTANCE = COMMON_CONFIG_BUILDER.comment("Maximum distance in chunks between Pitfall Traps - Default 8").define("max_pitfall_trap_distance", 8);
		MIN_PITFALL_TRAP_DISTANCE = COMMON_CONFIG_BUILDER.comment("Minimum distance in chunks between Pitfall Traps - Default 2").define("min_pitfall_trap_distance", 2);

		MAX_BEAR_TRAP_DISTANCE = COMMON_CONFIG_BUILDER.comment("Maximum distance in chunks between Bear Traps - Default 10").define("max_bear_trap_distance", 10);
		MIN_BEAR_TRAP_DISTANCE = COMMON_CONFIG_BUILDER.comment("Minimum distance in chunks between Bear Traps - Default 4").define("min_bear_trap_distance", 4);

		MAX_LANDMINE_TRAP_DISTANCE = COMMON_CONFIG_BUILDER.comment("Minimum distance in chunks between Landmine Traps - Default 12").define("max_landmine_trap_distance", 12);
		MIN_LANDMINE_TRAP_DISTANCE = COMMON_CONFIG_BUILDER.comment("Minimum distance in chunks between Landmine Traps - Default 6").define("min_landmine_trap_distance", 6);

		MAX_UNDERGROUND_BUNKER_DISTANCE = COMMON_CONFIG_BUILDER.comment("Maximum distance in chunks between Underground Bunkers - Default 110").define("max_underground_bunker_distance", 110);
		MIN_UNDERGROUND_BUNKER_DISTANCE = COMMON_CONFIG_BUILDER.comment("Minimum distance in chunks between Underground Bunkers - Default 80").define("min_underground_bunker_distance", 80);

		MAX_BATTLEFIELD_CAMP_DISTANCE = COMMON_CONFIG_BUILDER.comment("Maximum distance in chunks between Battlefield Camps - Default 16").define("max_battlefield_camp_distance", 16);
		MIN_BATTLEFIELD_CAMP_DISTANCE = COMMON_CONFIG_BUILDER.comment("Minimum distance in chunks between Battlefield Camps - Default 4").define("min_battlefield_camp_distance", 4);

		MAX_BATTLEFIELD_VILLAGE_DISTANCE = COMMON_CONFIG_BUILDER.comment("Maximum distance in chunks between Battlefield Villages - Default 30").define("max_battlefield_village_distance", 30);
		MIN_BATTLEFIELD_VILLAGE_DISTANCE = COMMON_CONFIG_BUILDER.comment("Minimum distance in chunks between Battlefield Villages - Default 10").define("min_battlefield_village_distance", 10);

		MAX_CLOUD_ISLAND_DISTANCE = COMMON_CONFIG_BUILDER.comment("Maximum distance in chunks between Cloud Islands - Default 90").define("max_cloud_island_distance", 90);
		MIN_CLOUD_ISLAND_DISTANCE = COMMON_CONFIG_BUILDER.comment("Minimum distance in chunks between Cloud Islands - Default 70").define("min_cloud_island_distance", 70);

		MAX_CAMPSITE_DISTANCE = COMMON_CONFIG_BUILDER.comment("Maximum distance in chunks between Campsites - Default 50").define("max_campsite_distance", 50);
		MIN_CAMPSITE_DISTANCE = COMMON_CONFIG_BUILDER.comment("Minimum distance in chunks between Campsites - Default 30").define("min_campsite_distance", 30);

		MAX_BATTLEFIELD_HOUSE_DISTANCE = COMMON_CONFIG_BUILDER.comment("Maximum distance in chunks between Battlefield Houses - Default 12").define("max_battlefield_house_distance", 12);
		MIN_BATTLEFIELD_HOUSE_DISTANCE = COMMON_CONFIG_BUILDER.comment("Minimum distance in chunks between Battlefield Houses - Default 4").define("min_battlefield_house_distance", 4);

		MAX_OUTHOUSE_DISTANCE = COMMON_CONFIG_BUILDER.comment("Maximum distance in chunks between Outhouses - Default 40").define("max_outhouse_distance", 40);
		MIN_OUTHOUSE_DISTANCE = COMMON_CONFIG_BUILDER.comment("Minimum distance in chunks between Outhouses - Default 20").define("min_outhouse_distance", 20);

		MAX_WATER_TOWER_DISTANCE = COMMON_CONFIG_BUILDER.comment("Maximum distance in chunks between Water Towers - Default 30").define("max_water_tower_distance", 30);
		MIN_WATER_TOWER_DISTANCE = COMMON_CONFIG_BUILDER.comment("Minimum distance in chunks between Water Towers - Default 12").define("min_water_tower_distance", 12);

		MAX_GRAVEYARD_DISTANCE = COMMON_CONFIG_BUILDER.comment("Maximum distance in chunks between Graveyards - Default 50").define("max_water_tower_distance", 50);
		MIN_GRAVEYARD_DISTANCE = COMMON_CONFIG_BUILDER.comment("Minimum distance in chunks between Graveyards - Default 30").define("min_water_tower_distance", 30);

		COMMON_CONFIG_BUILDER.pop();
		COMMON_CONFIG = COMMON_CONFIG_BUILDER.build();
	}

	/**
	 * Setup a configuration file.
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