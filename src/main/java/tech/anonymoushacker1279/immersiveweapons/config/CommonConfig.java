package tech.anonymoushacker1279.immersiveweapons.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.apache.commons.lang3.tuple.Pair;

public class CommonConfig {

	public static final ForgeConfigSpec COMMON_SPEC;
	public static final CommonConfig COMMON;

	// General settings
	public static ForgeConfigSpec.ConfigValue<Boolean> BULLETS_BREAK_GLASS;
	public static ForgeConfigSpec.ConfigValue<Boolean> TILTROS_ENABLED;
	public static ForgeConfigSpec.ConfigValue<Integer> FORCE_SMOKE_GRENADE_PARTICLES;

	// Entity settings

	// Celestial Tower
	public static ForgeConfigSpec.ConfigValue<Integer> CELESTIAL_TOWER_SPAWN_CHECK_RADIUS;
	public static ForgeConfigSpec.ConfigValue<Float> CELESTIAL_TOWER_MINIONS_WAVE_SIZE_MODIFIER;

	// Weapon settings

	// General
	public static ForgeConfigSpec.ConfigValue<Double> GUN_CRIT_CHANCE;

	// Flintlock Pistol
	public static ForgeConfigSpec.ConfigValue<Float> FLINTLOCK_PISTOL_FIRE_VELOCITY;
	public static ForgeConfigSpec.ConfigValue<Float> FLINTLOCK_PISTOL_FIRE_INACCURACY;

	// Blunderbuss
	public static ForgeConfigSpec.ConfigValue<Float> BLUNDERBUSS_FIRE_VELOCITY;
	public static ForgeConfigSpec.ConfigValue<Float> BLUNDERBUSS_FIRE_INACCURACY;

	// Musket
	public static ForgeConfigSpec.ConfigValue<Float> MUSKET_FIRE_VELOCITY;
	public static ForgeConfigSpec.ConfigValue<Float> MUSKET_FIRE_INACCURACY;

	CommonConfig(ForgeConfigSpec.Builder builder) {
		builder.push("Server Configuration");

		builder.push("Miscellaneous");
		BULLETS_BREAK_GLASS = builder
				.comment("Enable bullets breaking glass - Default true")
				.translation("config.immersiveweapons.bullets_break_glass")
				.define("bullets_break_glass", true);
		TILTROS_ENABLED = builder
				.comment("Enable the Tiltros dimension portal - Default true")
				.translation("config.immersiveweapons.tiltros_enabled")
				.define("tiltros_dimension_accessible_via_portal", true);
		FORCE_SMOKE_GRENADE_PARTICLES = builder
				.comment("""
						Force the number of particles produced by the smoke grenade to be the same on all clients.
						A value of -1 will not force any value, and will allow clients to use their own values.
						Setting this to a high value may cause clients to lag. - Default -1""")
				.translation("config.immersiveweapons.force_smoke_grenade_particles")
				.define("force_smoke_grenade_particles", -1);
		builder.pop();

		builder.push("Entity Settings");

		builder.push("Celestial Tower");
		CELESTIAL_TOWER_SPAWN_CHECK_RADIUS = builder
				.comment("Set the spawn checking radius for the Celestial Tower.\n" +
						"Setting this higher may slightly negatively impact server ticks in Tiltros, but make Celestial Lanterns more effective - Default 128")
				.translation("config.immersiveweapons.celestial_tower_spawn_check_radius")
				.define("celestial_tower_spawn_checking_radius", 128);
		CELESTIAL_TOWER_MINIONS_WAVE_SIZE_MODIFIER = builder
				.comment("""
						Multiplier to change the wave size from Celestial Tower summons.
						Set less than 1 to reduce, greater than 1 to increase.
						Increasing the wave size will negatively affect the server ticks in Tiltros. - Default 1.0""")
				.translation("config.immersiveweapons.celestial_tower_minions_wave_size_modifier")
				.define("celestial_tower_wave_size_modifier", 1.0F);
		builder.pop();

		builder.pop();

		builder.push("Weapon Settings");

		builder.push("General");
		GUN_CRIT_CHANCE = builder
				.comment("Set the chance for a fired bullet to be critical - Default 0.1")
				.translation("config.immersiveweapons.gun_crit_chance")
				.defineInRange("gun_crit_chance", 0.1D, 0.0D, 1.0D);
		builder.pop();

		builder.push("Flintlock Pistol");
		FLINTLOCK_PISTOL_FIRE_VELOCITY = builder
				.comment("Set the velocity of bullets fired by the Flintlock Pistol - Default 2.5")
				.translation("config.immersiveweapons.flintlock_pistol_fire_velocity")
				.define("flintlock_pistol_fire_velocity", 2.5f);
		FLINTLOCK_PISTOL_FIRE_INACCURACY = builder
				.comment("Set the inaccuracy of bullets fired by the Flintlock Pistol - Default 1.75")
				.translation("config.immersiveweapons.flintlock_pistol_fire_inaccuracy")
				.define("flintlock_pistol_fire_inaccuracy", 1.75f);
		builder.pop();

		builder.push("Blunderbuss");
		BLUNDERBUSS_FIRE_VELOCITY = builder
				.comment("Set the velocity of bullets fired by the Blunderbuss - Default 1.7")
				.translation("config.immersiveweapons.blunderbuss_fire_velocity")
				.define("blunderbuss_fire_velocity", 1.7f);
		BLUNDERBUSS_FIRE_INACCURACY = builder
				.comment("Set the inaccuracy of bullets fired by the Blunderbuss - Default 2.0")
				.translation("config.immersiveweapons.blunderbuss_fire_inaccuracy")
				.define("blunderbuss_fire_inaccuracy", 2.0f);
		builder.pop();

		builder.push("Musket");
		MUSKET_FIRE_VELOCITY = builder
				.comment("Set the velocity of bullets fired by the Musket - Default 4.0")
				.translation("config.immersiveweapons.musket_fire_velocity")
				.define("musket_fire_velocity", 4.0f);
		MUSKET_FIRE_INACCURACY = builder
				.comment("Set the inaccuracy of bullets fired by the Musket - Default 0.15")
				.translation("config.immersiveweapons.musket_fire_inaccuracy")
				.define("musket_fire_inaccuracy", 0.15f);
		builder.pop();

		builder.pop();
	}

	static {
		Pair<CommonConfig, ForgeConfigSpec> commonConfigForgeConfigSpecPair = new Builder().configure(CommonConfig::new);

		COMMON_SPEC = commonConfigForgeConfigSpecPair.getRight();
		COMMON = commonConfigForgeConfigSpecPair.getLeft();
	}
}