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

	// Mixin settings
	public static ForgeConfigSpec.ConfigValue<Double> MAX_ARMOR_PROTECTION;

	// Entity settings

	// General
	public static ForgeConfigSpec.ConfigValue<Integer> DISCOVERY_ADVANCEMENT_RANGE;

	// Celestial Tower
	public static ForgeConfigSpec.ConfigValue<Integer> CELESTIAL_TOWER_SPAWN_CHECK_RADIUS;
	public static ForgeConfigSpec.ConfigValue<Double> CELESTIAL_TOWER_MINIONS_WAVE_SIZE_MODIFIER;

	// Weapon settings

	// General
	public static ForgeConfigSpec.ConfigValue<Double> GUN_CRIT_CHANCE;
	public static ForgeConfigSpec.ConfigValue<Boolean> ALLOW_INFINITE_AMMO_ON_ALl_TIERS;

	// Flintlock Pistol
	public static ForgeConfigSpec.ConfigValue<Double> FLINTLOCK_PISTOL_FIRE_VELOCITY;
	public static ForgeConfigSpec.ConfigValue<Double> FLINTLOCK_PISTOL_FIRE_INACCURACY;

	// Blunderbuss
	public static ForgeConfigSpec.ConfigValue<Double> BLUNDERBUSS_FIRE_VELOCITY;
	public static ForgeConfigSpec.ConfigValue<Double> BLUNDERBUSS_FIRE_INACCURACY;

	// Musket
	public static ForgeConfigSpec.ConfigValue<Double> MUSKET_FIRE_VELOCITY;
	public static ForgeConfigSpec.ConfigValue<Double> MUSKET_FIRE_INACCURACY;

	// Meteor Staff
	public static ForgeConfigSpec.ConfigValue<Integer> METEOR_STAFF_MAX_USE_RANGE;
	public static ForgeConfigSpec.ConfigValue<Double> METEOR_STAFF_EXPLOSION_RADIUS;
	public static ForgeConfigSpec.ConfigValue<Boolean> METEOR_STAFF_EXPLOSION_BREAK_BLOCKS;

	// Cursed Sight Staff
	public static ForgeConfigSpec.ConfigValue<Integer> CURSED_SIGHT_STAFF_MAX_USE_RANGE;

	CommonConfig(ForgeConfigSpec.Builder builder) {
		builder.push("Server Configuration");

		builder.push("General");
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

		builder.push("Mixin");

		builder.push("Combat Rules");
		MAX_ARMOR_PROTECTION = builder
				.comment("""
						Set the maximum armor protection value. The vanilla default is 20. Setting this value higher
						 allows higher tiers of armor to work properly. - Default 50.0
						""")
				.translation("config.immersiveweapons.max_armor_protection")
				.define("max_armor_protection", 50.0D);
		builder.pop();

		builder.push("Entity Settings");

		builder.push("General");
		DISCOVERY_ADVANCEMENT_RANGE = builder
				.comment("Set the range for checking criteria of the discovery advancement (value is squared) - Default 50")
				.comment("Lowering this value may improve server performance.")
				.translation("config.immersiveweapons.discovery_advancement_range")
				.define("discovery_advancement_range", 50);
		builder.pop();

		builder.push("Celestial Tower");
		CELESTIAL_TOWER_SPAWN_CHECK_RADIUS = builder
				.comment("""
						Set the spawn checking radius for the Celestial Tower.
						Higher values increase the effectiveness of Celestial Lanterns - Default 256
						""")
				.translation("config.immersiveweapons.celestial_tower_spawn_check_radius")
				.define("celestial_tower_spawn_checking_radius", 256);
		CELESTIAL_TOWER_MINIONS_WAVE_SIZE_MODIFIER = builder
				.comment("""
						Multiplier to change the wave size from Celestial Tower summons.
						Set less than 1 to reduce, greater than 1 to increase.
						Increasing the wave size will negatively affect the server ticks in Tiltros. - Default 1.0""")
				.translation("config.immersiveweapons.celestial_tower_minions_wave_size_modifier")
				.define("celestial_tower_wave_size_modifier", 1.0D);
		builder.pop();

		builder.pop();

		builder.push("Weapon Settings");

		builder.push("General");
		GUN_CRIT_CHANCE = builder
				.comment("Set the chance for a fired bullet to be critical - Default 0.1")
				.translation("config.immersiveweapons.gun_crit_chance")
				.defineInRange("gun_crit_chance", 0.1D, 0.0D, 1.0D);
		ALLOW_INFINITE_AMMO_ON_ALl_TIERS = builder
				.comment("""
						Allow infinity-type enchantments to work on all ammo tiers.
						By default, it is restricted to cobalt and lower tiers. - Default false
						""")
				.translation("config.immersiveweapons.allow_infinite_ammo_on_all_tiers")
				.define("allow_infinite_ammo_on_all_tiers", false);
		builder.pop();

		builder.push("Flintlock Pistol");
		FLINTLOCK_PISTOL_FIRE_VELOCITY = builder
				.comment("Set the velocity of bullets fired by the Flintlock Pistol - Default 2.5")
				.translation("config.immersiveweapons.flintlock_pistol_fire_velocity")
				.define("flintlock_pistol_fire_velocity", 2.5D);
		FLINTLOCK_PISTOL_FIRE_INACCURACY = builder
				.comment("Set the inaccuracy of bullets fired by the Flintlock Pistol - Default 1.75")
				.translation("config.immersiveweapons.flintlock_pistol_fire_inaccuracy")
				.define("flintlock_pistol_fire_inaccuracy", 1.75D);
		builder.pop();

		builder.push("Blunderbuss");
		BLUNDERBUSS_FIRE_VELOCITY = builder
				.comment("Set the velocity of bullets fired by the Blunderbuss - Default 1.7")
				.translation("config.immersiveweapons.blunderbuss_fire_velocity")
				.define("blunderbuss_fire_velocity", 1.7D);
		BLUNDERBUSS_FIRE_INACCURACY = builder
				.comment("Set the inaccuracy of bullets fired by the Blunderbuss - Default 2.0")
				.translation("config.immersiveweapons.blunderbuss_fire_inaccuracy")
				.define("blunderbuss_fire_inaccuracy", 2.0D);
		builder.pop();

		builder.push("Musket");
		MUSKET_FIRE_VELOCITY = builder
				.comment("Set the velocity of bullets fired by the Musket - Default 4.0")
				.translation("config.immersiveweapons.musket_fire_velocity")
				.define("musket_fire_velocity", 4.0D);
		MUSKET_FIRE_INACCURACY = builder
				.comment("Set the inaccuracy of bullets fired by the Musket - Default 0.15")
				.translation("config.immersiveweapons.musket_fire_inaccuracy")
				.define("musket_fire_inaccuracy", 0.15D);
		builder.pop();

		builder.push("Meteor Staff");
		METEOR_STAFF_MAX_USE_RANGE = builder
				.comment("Set the maximum range in blocks of the Meteor Staff - Default 100")
				.translation("config.immersiveweapons.meteor_staff_max_use_range")
				.defineInRange("meteor_staff_max_use_range", 100, 0, Integer.MAX_VALUE);
		METEOR_STAFF_EXPLOSION_RADIUS = builder
				.comment("Set the radius of the explosion created by the Meteor Staff - Default 3.0")
				.translation("config.immersiveweapons.meteor_staff_explosion_radius")
				.defineInRange("meteor_staff_explosion_radius", 3.0D, 0.0D, Double.MAX_VALUE);
		METEOR_STAFF_EXPLOSION_BREAK_BLOCKS = builder
				.comment("Set whether the Meteor Staff explosion breaks blocks - Default false")
				.translation("config.immersiveweapons.meteor_staff_explosion_break_blocks")
				.define("meteor_staff_explosion_break_blocks", false);
		builder.pop();

		builder.push("Cursed Sight Staff");
		CURSED_SIGHT_STAFF_MAX_USE_RANGE = builder
				.comment("Set the maximum range in blocks of the Cursed Sight Staff - Default 50")
				.translation("config.immersiveweapons.cursed_sight_staff_max_use_range")
				.defineInRange("cursed_sight_staff_max_use_range", 50, 0, Integer.MAX_VALUE);

		builder.pop();
	}

	static {
		Pair<CommonConfig, ForgeConfigSpec> commonConfigForgeConfigSpecPair = new Builder().configure(CommonConfig::new);

		COMMON_SPEC = commonConfigForgeConfigSpecPair.getRight();
		COMMON = commonConfigForgeConfigSpecPair.getLeft();
	}
}