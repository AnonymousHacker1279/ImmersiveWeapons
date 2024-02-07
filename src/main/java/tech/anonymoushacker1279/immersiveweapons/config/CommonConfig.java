package tech.anonymoushacker1279.immersiveweapons.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.config.ConfigHelper.TomlConfigOps;

import java.util.HashMap;
import java.util.Map;

public record CommonConfig(
		ConfigValue<Boolean> bulletsBreakGlass,
		ConfigValue<Boolean> tiltrosEnabled,
		ConfigValue<Integer> forceSmokeGrenadeParticles,
		ConfigValue<Integer> panicAlarmRange,
		ConfigValue<Double> maxArmorProtection,
		ConfigValue<Integer> discoveryAdvancementRange,
		ConfigValue<Integer> celestialTowerSpawnCheckingRadius,
		ConfigValue<Double> celestialTowerWaveSizeModifier,
		ConfigValue<Object> skygazerEnchantCaps,
		ConfigValue<Double> gunCritChance,
		ConfigValue<Boolean> allowInfiniteAmmoOnAllTiers,
		ConfigValue<Double> flintlockPistolFireVelocity,
		ConfigValue<Double> flintlockPistolFireInaccuracy,
		ConfigValue<Double> blunderbussFireVelocity,
		ConfigValue<Double> blunderbussFireInaccuracy,
		ConfigValue<Double> musketFireVelocity,
		ConfigValue<Double> musketFireInaccuracy,
		ConfigValue<Double> handCannonFireVelocity,
		ConfigValue<Double> handCannonFireInaccuracy,
		ConfigValue<Integer> meteorStaffMaxUseRange,
		ConfigValue<Double> meteorStaffExplosionRadius,
		ConfigValue<Boolean> meteorStaffExplosionBreakBlocks,
		ConfigValue<Integer> cursedSightStaffMaxUseRange) {

	public static CommonConfig create(ForgeConfigSpec.Builder builder) {
		builder.push("Common Configuration");

		builder.push("General");
		ConfigValue<Boolean> bulletsBreakGlass = builder
				.comment("Enable bullets breaking glass - Default true")
				.translation("config.immersiveweapons.bullets_break_glass")
				.define("bullets_break_glass", true);

		ConfigValue<Boolean> tiltrosEnabled = builder
				.comment("Enable the Tiltros dimension portal - Default true")
				.translation("config.immersiveweapons.tiltros_enabled")
				.define("tiltros_dimension_accessible_via_portal", true);

		ConfigValue<Integer> forceSmokeGrenadeParticles = builder
				.comment("""
						Force the number of particles produced by the smoke grenade to be the same on all clients.
						A value of -1 will not force any value, and will allow clients to use their own values.
						Setting this to a high value may cause clients to lag. - Default -1""")
				.translation("config.immersiveweapons.force_smoke_grenade_particles")
				.defineInRange("force_smoke_grenade_particles", -1, -1, Integer.MAX_VALUE);

		ConfigValue<Integer> panicAlarmRange = builder
				.comment("Set the range of the Panic Alarm's sound - Default 48")
				.translation("config.immersiveweapons.panic_alarm_range")
				.defineInRange("panic_alarm_range", 48, 0, Integer.MAX_VALUE);
		builder.pop();

		builder.push("Mixin");

		builder.push("Combat Rules");
		ConfigValue<Double> maxArmorProtection = builder
				.comment("""
						Set the maximum armor protection cap. The vanilla default is 20. Setting this value higher
						allows higher tiers of armor to work as intended. A value of 25 is fully unlocked. - Default 25.0""")
				.translation("config.immersiveweapons.max_armor_protection")
				.defineInRange("max_armor_protection", 25.0D, 0, 25.0D);
		builder.pop();

		builder.pop();

		builder.push("Entity Settings");

		builder.push("General");
		ConfigValue<Integer> discoveryAdvancementRange = builder
				.comment("Set the range for checking criteria of the discovery advancement (value is squared) - Default 50")
				.comment("Lowering this value may improve server performance.")
				.translation("config.immersiveweapons.discovery_advancement_range")
				.defineInRange("discovery_advancement_range", 50, 0, Integer.MAX_VALUE);
		builder.pop();

		builder.push("Celestial Tower");
		ConfigValue<Integer> celestialTowerSpawnCheckingRadius = builder
				.comment("""
						Set the spawn checking radius for the Celestial Tower.
						Higher values increase the effectiveness of Celestial Lanterns - Default 256""")
				.translation("config.immersiveweapons.celestial_tower_spawn_check_radius")
				.defineInRange("celestial_tower_spawn_checking_radius", 256, 0, Integer.MAX_VALUE);
		ConfigValue<Double> celestialTowerWaveSizeModifier = builder
				.comment("""
						Multiplier to change the wave size from Celestial Tower summons.
						Set less than 1 to reduce, greater than 1 to increase.
						Increasing the wave size will negatively affect the server ticks in Tiltros. - Default 1.0""")
				.translation("config.immersiveweapons.celestial_tower_minions_wave_size_modifier")
				.defineInRange("celestial_tower_wave_size_modifier", 1.0D, 0, Double.MAX_VALUE);
		builder.pop();

		builder.push("Skygazer");
		ConfigValue<Object> skygazerEnchantCaps = builder
				.comment("""
						Specify maximum caps on enchantments to prevent the Skygazer from being able to upgrade them
						past a certain level. Enchantments at their max level will appear with a gold tooltip.""")
				.translation("config.immersiveweapons.skygazer_enchant_caps")
				.define("skygazer_enchant_caps", TomlConfigOps.INSTANCE.createMap(getEnchantCapsMap()));

		builder.pop();

		builder.push("Weapon Settings");

		builder.push("General");
		ConfigValue<Double> gunCritChance = builder
				.comment("Set the chance for a fired bullet to be critical - Default 0.1")
				.translation("config.immersiveweapons.gun_crit_chance")
				.defineInRange("gun_crit_chance", 0.1D, 0.0D, 1.0D);
		ConfigValue<Boolean> allowInfiniteAmmoOnAllTiers = builder
				.comment("""
						Allow infinity-type enchantments to work on all ammo tiers.
						By default, it is restricted to cobalt and lower tiers. - Default false""")
				.translation("config.immersiveweapons.allow_infinite_ammo_on_all_tiers")
				.define("allow_infinite_ammo_on_all_tiers", false);
		builder.pop();

		builder.push("Flintlock Pistol");
		ConfigValue<Double> flintlockPistolFireVelocity = builder
				.comment("Set the velocity of bullets fired by the Flintlock Pistol - Default 2.5")
				.translation("config.immersiveweapons.flintlock_pistol_fire_velocity")
				.defineInRange("flintlock_pistol_fire_velocity", 2.5D, 0, Double.MAX_VALUE);
		ConfigValue<Double> flintlockPistolFireInaccuracy = builder
				.comment("Set the inaccuracy of bullets fired by the Flintlock Pistol - Default 1.75")
				.translation("config.immersiveweapons.flintlock_pistol_fire_inaccuracy")
				.defineInRange("flintlock_pistol_fire_inaccuracy", 1.75D, 0, Double.MAX_VALUE);
		builder.pop();

		builder.push("Blunderbuss");
		ConfigValue<Double> blunderbussFireVelocity = builder
				.comment("Set the velocity of bullets fired by the Blunderbuss - Default 1.7")
				.translation("config.immersiveweapons.blunderbuss_fire_velocity")
				.defineInRange("blunderbuss_fire_velocity", 1.7D, 0, Double.MAX_VALUE);
		ConfigValue<Double> blunderbussFireInaccuracy = builder
				.comment("Set the inaccuracy of bullets fired by the Blunderbuss - Default 2.0")
				.translation("config.immersiveweapons.blunderbuss_fire_inaccuracy")
				.defineInRange("blunderbuss_fire_inaccuracy", 2.0D, 0, Double.MAX_VALUE);
		builder.pop();

		builder.push("Musket");
		ConfigValue<Double> musketFireVelocity = builder
				.comment("Set the velocity of bullets fired by the Musket - Default 4.0")
				.translation("config.immersiveweapons.musket_fire_velocity")
				.defineInRange("musket_fire_velocity", 4.0D, 0, Double.MAX_VALUE);
		ConfigValue<Double> musketFireInaccuracy = builder
				.comment("Set the inaccuracy of bullets fired by the Musket - Default 0.15")
				.translation("config.immersiveweapons.musket_fire_inaccuracy")
				.defineInRange("musket_fire_inaccuracy", 0.15D, 0, Double.MAX_VALUE);
		builder.pop();

		builder.push("Hand Cannon");
		ConfigValue<Double> handCannonFireVelocity = builder
				.comment("Set the velocity of cannonballs fired by the Hand Cannon - Default 2.75")
				.translation("config.immersiveweapons.hand_cannon_fire_velocity")
				.defineInRange("hand_cannon_fire_velocity", 2.55D, 0, Double.MAX_VALUE);
		ConfigValue<Double> handCannonFireInaccuracy = builder
				.comment("Set the inaccuracy of cannonballs fired by the Hand Cannon - Default 1.85")
				.translation("config.immersiveweapons.hand_cannon_fire_inaccuracy")
				.defineInRange("hand_cannon_fire_inaccuracy", 1.85D, 0, Double.MAX_VALUE);
		builder.pop();

		builder.push("Meteor Staff");
		ConfigValue<Integer> meteorStaffMaxUseRange = builder
				.comment("Set the maximum range in blocks of the Meteor Staff - Default 100")
				.translation("config.immersiveweapons.meteor_staff_max_use_range")
				.defineInRange("meteor_staff_max_use_range", 100, 0, Integer.MAX_VALUE);
		ConfigValue<Double> meteorStaffExplosionRadius = builder
				.comment("Set the radius of the explosion created by the Meteor Staff - Default 3.0")
				.translation("config.immersiveweapons.meteor_staff_explosion_radius")
				.defineInRange("meteor_staff_explosion_radius", 3.0D, 0.0D, Double.MAX_VALUE);
		ConfigValue<Boolean> meteorStaffExplosionBreakBlocks = builder
				.comment("Set whether the Meteor Staff explosion breaks blocks - Default false")
				.translation("config.immersiveweapons.meteor_staff_explosion_break_blocks")
				.define("meteor_staff_explosion_break_blocks", false);
		builder.pop();

		builder.push("Cursed Sight Staff");
		ConfigValue<Integer> cursedSightStaffMaxUseRange = builder
				.comment("Set the maximum range in blocks of the Cursed Sight Staff - Default 50")
				.translation("config.immersiveweapons.cursed_sight_staff_max_use_range")
				.defineInRange("cursed_sight_staff_max_use_range", 50, 0, Integer.MAX_VALUE);
		builder.pop();

		builder.pop();

		return new CommonConfig(
				bulletsBreakGlass,
				tiltrosEnabled,
				forceSmokeGrenadeParticles,
				panicAlarmRange,
				maxArmorProtection,
				discoveryAdvancementRange,
				celestialTowerSpawnCheckingRadius, celestialTowerWaveSizeModifier,
				skygazerEnchantCaps,
				gunCritChance,
				allowInfiniteAmmoOnAllTiers,
				flintlockPistolFireVelocity, flintlockPistolFireInaccuracy,
				blunderbussFireVelocity, blunderbussFireInaccuracy,
				musketFireVelocity, musketFireInaccuracy,
				handCannonFireVelocity, handCannonFireInaccuracy,
				meteorStaffMaxUseRange, meteorStaffExplosionRadius, meteorStaffExplosionBreakBlocks,
				cursedSightStaffMaxUseRange
		);
	}

	@NotNull
	private static Map<Object, Object> getEnchantCapsMap() {
		Map<Object, Object> enchantCaps = new HashMap<>(40);
		enchantCaps.put("minecraft:mending", 1);
		enchantCaps.put("minecraft:silk_touch", 1);
		enchantCaps.put("minecraft:knockback", 5);
		enchantCaps.put("minecraft:punch", 5);
		enchantCaps.put("minecraft:flame", 1);
		enchantCaps.put("minecraft:infinity", 1);
		enchantCaps.put("minecraft:channeling", 1);
		enchantCaps.put("minecraft:multishot", 1);
		enchantCaps.put("minecraft:protection", 5);
		enchantCaps.put("minecraft:blast_protection", 5);
		enchantCaps.put("minecraft:fire_protection", 5);
		enchantCaps.put("minecraft:projectile_protection", 5);
		enchantCaps.put("minecraft:feather_falling", 5);
		enchantCaps.put("minecraft:swift_sneak", 5);
		enchantCaps.put("minecraft:lure", 5);
		enchantCaps.put("minecraft:aqua_affinity", 1);
		enchantCaps.put("immersiveweapons:extended_reach", 1);
		enchantCaps.put("immersiveweapons:endless_musket_pouch", 1);
		enchantCaps.put("immersiveweapons:scorch_shot", 3);
		enchantCaps.put("immersiveweapons:velocity", 5);
		enchantCaps.put("immersiveweapons:impact", 5);
		enchantCaps.put("immersiveweapons:burning_heat", 1);
		enchantCaps.put("immersiveweapons:celestial_fury", 1);
		enchantCaps.put("immersiveweapons:heavy_comet", 4);
		enchantCaps.put("immersiveweapons:regenerative_assault", 5);
		enchantCaps.put("immersiveweapons:malevolent_gaze", 5);
		return enchantCaps;
	}
}