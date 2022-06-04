package com.anonymoushacker1279.immersiveweapons.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import org.apache.commons.lang3.tuple.Pair;

public class CommonConfig {

	public static final ForgeConfigSpec COMMON_SPEC;
	public static final CommonConfig COMMON;

	// General settings
	public static ForgeConfigSpec.ConfigValue<Integer> MAX_SMOKE_GRENADE_PARTICLES;
	public static ForgeConfigSpec.ConfigValue<Boolean> BULLETS_BREAK_GLASS;
	public static ForgeConfigSpec.ConfigValue<Boolean> TILTROS_ENABLED;

	// Celestial Tower entity specific
	public static ForgeConfigSpec.ConfigValue<Integer> CELESTIAL_TOWER_SPAWN_CHECK_RADIUS;
	public static ForgeConfigSpec.ConfigValue<Double> CELESTIAL_TOWER_MINIONS_WAVE_SIZE_MODIFIER;

	// Ore configurations

	// Molten Ore
	public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_MOLTEN_ORE;
	public static ForgeConfigSpec.ConfigValue<Integer> MOLTEN_ORE_WEIGHT;
	public static ForgeConfigSpec.ConfigValue<Integer> MOLTEN_ORE_TOP_ANCHOR;
	public static ForgeConfigSpec.ConfigValue<Integer> MOLTEN_ORE_BOTTOM_ANCHOR;
	public static ForgeConfigSpec.ConfigValue<Integer> MOLTEN_ORE_SIZE;
	public static DoubleValue MOLTEN_ORE_EXPOSED_DISCARD_CHANCE;
	// Nether Sulfur Ore
	public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_NETHER_SULFUR_ORE;
	public static ForgeConfigSpec.ConfigValue<Integer> NETHER_SULFUR_ORE_WEIGHT;
	public static ForgeConfigSpec.ConfigValue<Integer> NETHER_SULFUR_ORE_TOP_ANCHOR;
	public static ForgeConfigSpec.ConfigValue<Integer> NETHER_SULFUR_ORE_BOTTOM_ANCHOR;
	public static ForgeConfigSpec.ConfigValue<Integer> NETHER_SULFUR_ORE_SIZE;
	public static DoubleValue NETHER_SULFUR_ORE_EXPOSED_DISCARD_CHANCE;
	// Sulfur Ore
	public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_SULFUR_ORE;
	public static ForgeConfigSpec.ConfigValue<Integer> SULFUR_ORE_WEIGHT;
	public static ForgeConfigSpec.ConfigValue<Integer> SULFUR_ORE_TOP_ANCHOR;
	public static ForgeConfigSpec.ConfigValue<Integer> SULFUR_ORE_BOTTOM_ANCHOR;
	public static ForgeConfigSpec.ConfigValue<Integer> SULFUR_ORE_SIZE;
	public static DoubleValue SULFUR_ORE_EXPOSED_DISCARD_CHANCE;
	// Deepslate Sulfur Ore
	public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_DEEPSLATE_SULFUR_ORE;
	public static ForgeConfigSpec.ConfigValue<Integer> DEEPSLATE_SULFUR_ORE_WEIGHT;
	public static ForgeConfigSpec.ConfigValue<Integer> DEEPSLATE_SULFUR_ORE_TOP_ANCHOR;
	public static ForgeConfigSpec.ConfigValue<Integer> DEEPSLATE_SULFUR_ORE_BOTTOM_ANCHOR;
	public static ForgeConfigSpec.ConfigValue<Integer> DEEPSLATE_SULFUR_ORE_SIZE;
	public static DoubleValue DEEPSLATE_SULFUR_ORE_EXPOSED_DISCARD_CHANCE;
	// Cobalt Ore
	public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_COBALT_ORE;
	public static ForgeConfigSpec.ConfigValue<Integer> COBALT_ORE_WEIGHT;
	public static ForgeConfigSpec.ConfigValue<Integer> COBALT_ORE_TOP_ANCHOR;
	public static ForgeConfigSpec.ConfigValue<Integer> COBALT_ORE_BOTTOM_ANCHOR;
	public static ForgeConfigSpec.ConfigValue<Integer> COBALT_ORE_SIZE;
	public static DoubleValue COBALT_ORE_EXPOSED_DISCARD_CHANCE;
	// Deepslate Cobalt Ore
	public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_DEEPSLATE_COBALT_ORE;
	public static ForgeConfigSpec.ConfigValue<Integer> DEEPSLATE_COBALT_ORE_WEIGHT;
	public static ForgeConfigSpec.ConfigValue<Integer> DEEPSLATE_COBALT_ORE_TOP_ANCHOR;
	public static ForgeConfigSpec.ConfigValue<Integer> DEEPSLATE_COBALT_ORE_BOTTOM_ANCHOR;
	public static ForgeConfigSpec.ConfigValue<Integer> DEEPSLATE_COBALT_ORE_SIZE;
	public static DoubleValue DEEPSLATE_COBALT_ORE_EXPOSED_DISCARD_CHANCE;

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

		builder.push("Celestial Tower");
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

		builder.push("Ore Generation");
		builder.comment("Note: A configuration will not exist for every ore. Ores found in structures (like Electric Ore) will not have configurations." +
				"\nInstead, modify the structure separation settings.");

		builder.push("Molten Ore");
		ENABLE_MOLTEN_ORE = builder.comment("Enable generation of Molten Ore")
				.define("Enable Molten Ore Generation", true);
		MOLTEN_ORE_WEIGHT = builder.defineInRange("Molten Ore weight", 6, 1, Integer.MAX_VALUE);
		MOLTEN_ORE_TOP_ANCHOR = builder.defineInRange("Molten Ore top anchor", 64, -64, 320);
		MOLTEN_ORE_BOTTOM_ANCHOR = builder.defineInRange("Molten Ore bottom anchor", -64, -64, 320);
		MOLTEN_ORE_SIZE = builder.defineInRange("Molten Ore size", 2, 0, Integer.MAX_VALUE);
		MOLTEN_ORE_EXPOSED_DISCARD_CHANCE = builder.defineInRange("Molten Ore exposed to air discard chance", 0.85D, 0D, 1D);
		builder.pop();

		builder.push("Nether Sulfur Ore");
		ENABLE_NETHER_SULFUR_ORE = builder.comment("Enable generation of Nether Sulfur Ore")
				.define("Enable Nether Sulfur Ore generation", true);
		NETHER_SULFUR_ORE_WEIGHT = builder.defineInRange("Nether Sulfur Ore weight", 16, 1, Integer.MAX_VALUE);
		NETHER_SULFUR_ORE_TOP_ANCHOR = builder.defineInRange("Nether Sulfur Ore top anchor", 128, -64, 320);
		NETHER_SULFUR_ORE_BOTTOM_ANCHOR = builder.defineInRange("Nether Sulfur Ore bottom anchor", -16, -64, 320);
		NETHER_SULFUR_ORE_SIZE = builder.defineInRange("Nether Sulfur Ore size", 12, 0, Integer.MAX_VALUE);
		NETHER_SULFUR_ORE_EXPOSED_DISCARD_CHANCE = builder.defineInRange("Nether Sulfur Ore exposed to air discard chance", 0.08D, 0D, 1D);
		builder.pop();

		builder.push("Sulfur Ore");
		ENABLE_SULFUR_ORE = builder.comment("Enable generation of Sulfur Ore")
				.define("Enable Sulfur Ore generation", true);
		SULFUR_ORE_WEIGHT = builder.defineInRange("Sulfur Ore weight", 6, 1, Integer.MAX_VALUE);
		SULFUR_ORE_TOP_ANCHOR = builder.defineInRange("Sulfur Ore top anchor", 196, -64, 320);
		SULFUR_ORE_BOTTOM_ANCHOR = builder.defineInRange("Sulfur Ore bottom anchor", 32, -64, 320);
		SULFUR_ORE_SIZE = builder.defineInRange("Sulfur Ore size", 4, 0, Integer.MAX_VALUE);
		SULFUR_ORE_EXPOSED_DISCARD_CHANCE = builder.defineInRange("Sulfur Ore exposed to air discard chance", 0.1D, 0D, 1D);
		builder.pop();

		builder.push("Deepslate Sulfur Ore");
		ENABLE_DEEPSLATE_SULFUR_ORE = builder.comment("Enable generation of Deepslate Sulfur Ore")
				.define("Enable Deepslate Sulfur Ore generation", true);
		DEEPSLATE_SULFUR_ORE_WEIGHT = builder.defineInRange("Deepslate Sulfur Ore weight", 16, 1, Integer.MAX_VALUE);
		DEEPSLATE_SULFUR_ORE_TOP_ANCHOR = builder.defineInRange("Deepslate Sulfur Ore top anchor", 0, -64, 320);
		DEEPSLATE_SULFUR_ORE_BOTTOM_ANCHOR = builder.defineInRange("Deepslate Sulfur Ore bottom anchor", -64, -64, 320);
		DEEPSLATE_SULFUR_ORE_SIZE = builder.defineInRange("Deepslate Sulfur Ore size", 4, 0, Integer.MAX_VALUE);
		DEEPSLATE_SULFUR_ORE_EXPOSED_DISCARD_CHANCE = builder.defineInRange("Deepslate Sulfur Ore exposed to air discard chance", 0.04D, 0D, 1D);
		builder.pop();

		builder.push("Cobalt Ore");
		ENABLE_COBALT_ORE = builder.comment("Enable generation of Cobalt Ore")
				.define("Enable Cobalt Ore generation", true);
		COBALT_ORE_WEIGHT = builder.defineInRange("Cobalt Ore weight", 12, 1, Integer.MAX_VALUE);
		COBALT_ORE_TOP_ANCHOR = builder.defineInRange("Cobalt Ore top anchor", 196, -64, 320);
		COBALT_ORE_BOTTOM_ANCHOR = builder.defineInRange("Cobalt Ore bottom anchor", 7, -64, 320);
		COBALT_ORE_SIZE = builder.defineInRange("Cobalt Ore size", 6, 0, Integer.MAX_VALUE);
		COBALT_ORE_EXPOSED_DISCARD_CHANCE = builder.defineInRange("Cobalt Ore exposed to air discard chance", 0.15D, 0D, 1D);
		builder.pop();

		builder.push("Deepslate Cobalt Ore");
		ENABLE_DEEPSLATE_COBALT_ORE = builder.comment("Enable generation of Deepslate Cobalt Ore")
				.define("Enable Deepslate Cobalt Ore generation", true);
		DEEPSLATE_COBALT_ORE_WEIGHT = builder.defineInRange("Deepslate Cobalt Ore weight", 12, 1, Integer.MAX_VALUE);
		DEEPSLATE_COBALT_ORE_TOP_ANCHOR = builder.defineInRange("Deepslate Cobalt Ore top anchor", 0, -64, 320);
		DEEPSLATE_COBALT_ORE_BOTTOM_ANCHOR = builder.defineInRange("Deepslate Cobalt Ore bottom anchor", -64, -64, 320);
		DEEPSLATE_COBALT_ORE_SIZE = builder.defineInRange("Deepslate Cobalt Ore size", 12, 0, Integer.MAX_VALUE);
		DEEPSLATE_COBALT_ORE_EXPOSED_DISCARD_CHANCE = builder.defineInRange("Deepslate Cobalt Ore exposed to air discard chance", 0.1D, 0D, 1D);
		builder.pop();

		builder.pop();
	}

	static {
		Pair<CommonConfig, ForgeConfigSpec> commonConfigForgeConfigSpecPair = new Builder().configure(CommonConfig::new);

		COMMON_SPEC = commonConfigForgeConfigSpecPair.getRight();
		COMMON = commonConfigForgeConfigSpecPair.getLeft();
	}
}