package tech.anonymoushacker1279.immersiveweapons.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.apache.commons.lang3.tuple.Pair;

public class CommonConfig {

	public static final ForgeConfigSpec COMMON_SPEC;
	public static final CommonConfig COMMON;

	// General settings
	public static ForgeConfigSpec.ConfigValue<Integer> MAX_SMOKE_GRENADE_PARTICLES;
	public static ForgeConfigSpec.ConfigValue<Boolean> BULLETS_BREAK_GLASS;
	public static ForgeConfigSpec.ConfigValue<Boolean> TILTROS_ENABLED;

	// Spawn settings
	
	// Hans
	public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_HANS_SPAWN;
	public static ForgeConfigSpec.ConfigValue<Integer> HANS_SPAWN_WEIGHT;
	public static ForgeConfigSpec.ConfigValue<Integer> HANS_SPAWN_MIN_COUNT;
	public static ForgeConfigSpec.ConfigValue<Integer> HANS_SPAWN_MAX_COUNT;
	// Lava Revenant
	public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_LAVA_REVENANT_SPAWN;
	public static ForgeConfigSpec.ConfigValue<Integer> LAVA_REVENANT_SPAWN_WEIGHT;
	public static ForgeConfigSpec.ConfigValue<Integer> LAVA_REVENANT_SPAWN_MIN_COUNT;
	public static ForgeConfigSpec.ConfigValue<Integer> LAVA_REVENANT_SPAWN_MAX_COUNT;
	// Rock Spider
	public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_ROCK_SPIDER_SPAWN;
	public static ForgeConfigSpec.ConfigValue<Integer> ROCK_SPIDER_SPAWN_WEIGHT;
	public static ForgeConfigSpec.ConfigValue<Integer> ROCK_SPIDER_SPAWN_MIN_COUNT;
	public static ForgeConfigSpec.ConfigValue<Integer> ROCK_SPIDER_SPAWN_MAX_COUNT;
	// Celestial Tower
	public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_CELESTIAL_TOWER_SPAWN;
	public static ForgeConfigSpec.ConfigValue<Integer> CELESTIAL_TOWER_SPAWN_WEIGHT;
	public static ForgeConfigSpec.ConfigValue<Integer> CELESTIAL_TOWER_SPAWN_MIN_COUNT;
	public static ForgeConfigSpec.ConfigValue<Integer> CELESTIAL_TOWER_SPAWN_MAX_COUNT;

	// Celestial Tower entity specific
	public static ForgeConfigSpec.ConfigValue<Integer> CELESTIAL_TOWER_SPAWN_CHECK_RADIUS;
	public static ForgeConfigSpec.ConfigValue<Double> CELESTIAL_TOWER_MINIONS_WAVE_SIZE_MODIFIER;

	CommonConfig(ForgeConfigSpec.Builder builder) {
		builder.push("Server Configuration");

		builder.push("Miscellaneous");
		MAX_SMOKE_GRENADE_PARTICLES = builder
				.comment("Set the maximum number of particles produced by the smoke grenade - Default 96\n" +
						"Setting this higher can make clients laggy, setting to 0 effectively disables it")
				.translation("config.immersiveweapons.max_smoke_grenade_particles")
				.define("Max smoke grenade particles", 96);
		BULLETS_BREAK_GLASS = builder
				.comment("Enable bullets breaking glass - Default true")
				.translation("config.immersiveweapons.bullets_break_glass")
				.define("Bullets can break glass", true);
		TILTROS_ENABLED = builder
				.comment("Enable the Tiltros dimension portal - Default true")
				.translation("config.immersiveweapons.tiltros_enabled")
				.define("Tiltros dimension is accessible via the portal", true);
		builder.pop();

		builder.push("Entity Spawns");

		builder.push("Hans");
		ENABLE_HANS_SPAWN = builder
				.comment("Enable the natural spawning of Hans the Almighty - Default true")
				.translation("config.immersiveweapons.hans_spawn")
				.define("enable_hans_spawn", true);
		HANS_SPAWN_WEIGHT = builder
				.comment("Set the spawn weight of Hans the Almighty - Default 3")
				.translation("config.immersiveweapons.hans_spawn_weight")
				.define("hans_spawn_weight", 3);
		HANS_SPAWN_MIN_COUNT = builder
				.comment("Set the minimum spawn count of Hans the Almighty - Default 1")
				.translation("config.immersiveweapons.hans_spawn_min_count")
				.define("hans_spawn_min_count", 1);
		HANS_SPAWN_MAX_COUNT = builder
				.comment("Set the maximum spawn count of Hans the Almighty - Default 1")
				.translation("config.immersiveweapons.hans_spawn_max_count")
				.define("hans_spawn_max_count", 1);
		builder.pop();

		builder.push("Lava Revenant");
		ENABLE_LAVA_REVENANT_SPAWN = builder
				.comment("Enable the natural spawning of Lava Revenants - Default true")
				.translation("config.immersiveweapons.lava_revenant_spawn")
				.define("enable_lava_revenant_spawn", true);
		LAVA_REVENANT_SPAWN_WEIGHT = builder
				.comment("Set the spawn weight of Lava Revenants - Default 35")
				.translation("config.immersiveweapons.lava_revenant_spawn_weight")
				.define("lava_revenant_spawn_weight", 35);
		LAVA_REVENANT_SPAWN_MIN_COUNT = builder
				.comment("Set the minimum spawn count of Lava Revenants - Default 1")
				.translation("config.immersiveweapons.lava_revenant_spawn_min_count")
				.define("lava_revenant_spawn_min_count", 1);
		LAVA_REVENANT_SPAWN_MAX_COUNT = builder
				.comment("Set the maximum spawn count of Lava Revenants - Default 1")
				.translation("config.immersiveweapons.lava_revenant_spawn_max_count")
				.define("lava_revenant_spawn_max_count", 1);
		builder.pop();

		builder.push("Rock Spider");
		ENABLE_ROCK_SPIDER_SPAWN = builder
				.comment("Enable the natural spawning of Rock Spiders - Default true")
				.translation("config.immersiveweapons.rock_spider_spawn")
				.define("enable_rock_spider_spawn", true);
		ROCK_SPIDER_SPAWN_WEIGHT = builder
				.comment("Set the spawn weight of Rock Spiders - Default 65")
				.translation("config.immersiveweapons.rock_spider_spawn_weight")
				.define("rock_spider_spawn_weight", 65);
		ROCK_SPIDER_SPAWN_MIN_COUNT = builder
				.comment("Set the minimum spawn count of Rock Spiders - Default 2")
				.translation("config.immersiveweapons.rock_spider_spawn_min_count")
				.define("rock_spider_spawn_min_count", 2);
		ROCK_SPIDER_SPAWN_MAX_COUNT = builder
				.comment("Set the maximum spawn count of Rock Spiders - Default 4")
				.translation("config.immersiveweapons.rock_spider_spawn_max_count")
				.define("rock_spider_spawn_max_count", 4);
		builder.pop();

		builder.push("Celestial Tower");
		ENABLE_CELESTIAL_TOWER_SPAWN = builder
				.comment("Enable the natural spawning of Celestial Towers - Default true")
				.translation("config.immersiveweapons.celestial_tower_spawn")
				.define("enable_celestial_tower_spawn", true);
		CELESTIAL_TOWER_SPAWN_WEIGHT = builder
				.comment("Set the spawn weight of Celestial Towers - Default 5")
				.translation("config.immersiveweapons.rock_spider_spawn_weight")
				.define("celestial_tower_spawn_weight", 5);
		CELESTIAL_TOWER_SPAWN_MIN_COUNT = builder
				.comment("Set the minimum spawn count of Celestial Towers - Default 1")
				.translation("config.immersiveweapons.rock_spider_spawn_min_count")
				.define("celestial_tower_spawn_min_count", 1);
		CELESTIAL_TOWER_SPAWN_MAX_COUNT = builder
				.comment("Set the maximum spawn count of Celestial Towers - Default 1")
				.translation("config.immersiveweapons.rock_spider_spawn_max_count")
				.define("celestial_tower_spawn_max_count", 1);
		CELESTIAL_TOWER_SPAWN_CHECK_RADIUS = builder
				.comment("Set the spawn checking radius for the Celestial Tower.\n" +
						"Setting this higher may slightly negatively impact server ticks in Tiltros, but make Celestial Lanterns more effective - Default 128")
				.translation("config.immersiveweapons.celestial_tower_spawn_check_radius")
				.define("Celestial Tower spawn checking radius", 128);
		CELESTIAL_TOWER_MINIONS_WAVE_SIZE_MODIFIER = builder
				.comment("""
						Multiplier to change the wave size from Celestial Tower summons.
						Set less than 1 to reduce, greater than 1 to increase.
						Increasing the wave size will negatively affect the server ticks in Tiltros. - Default 1.0""")
				.translation("config.immersiveweapons.celestial_tower_minions_wave_size_modifier")
				.define("Celestial Tower wave size modifier", 1.0D);
		builder.pop();

		builder.pop();
	}

	static {
		Pair<CommonConfig, ForgeConfigSpec> commonConfigForgeConfigSpecPair = new Builder().configure(CommonConfig::new);

		COMMON_SPEC = commonConfigForgeConfigSpecPair.getRight();
		COMMON = commonConfigForgeConfigSpecPair.getLeft();
	}
}