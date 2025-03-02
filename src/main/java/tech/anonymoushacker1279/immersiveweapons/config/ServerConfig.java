package tech.anonymoushacker1279.immersiveweapons.config;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerConfig {

	public final ModConfigSpec.BooleanValue tiltrosEnabled;
	public final ModConfigSpec.IntValue forceSmokeGrenadeParticles;
	public final ModConfigSpec.IntValue panicAlarmRange;
	public final ModConfigSpec.BooleanValue blockDecay;
	public final ModConfigSpec.DoubleValue maxArmorProtection;
	public final ModConfigSpec.IntValue bulletDespawnTimeModifier;
	public final ModConfigSpec.IntValue discoveryAdvancementRange;
	public final ModConfigSpec.IntValue celestialTowerSpawnCheckingRadius;
	public final ModConfigSpec.DoubleValue celestialTowerWaveSizeModifier;
	public final ConfigValue<List<?>> celestialAltarEnchantCaps;
	public final ModConfigSpec.IntValue celestialAltarMaxEnchantUpgradeCost;
	public final ModConfigSpec.DoubleValue theCommanderWaveSizeModifier;
	public final ModConfigSpec.DoubleValue gunCritChance;
	public final ModConfigSpec.BooleanValue bulletsBreakGlass;
	public final ModConfigSpec.BooleanValue infiniteAmmoOnAllTiers;
	public final ModConfigSpec.DoubleValue flintlockPistolFireVelocity;
	public final ModConfigSpec.DoubleValue flintlockPistolFireInaccuracy;
	public final ModConfigSpec.DoubleValue blunderbussFireVelocity;
	public final ModConfigSpec.DoubleValue blunderbussFireInaccuracy;
	public final ModConfigSpec.DoubleValue flareGunFireVelocity;
	public final ModConfigSpec.DoubleValue flareGunFireInaccuracy;
	public final ModConfigSpec.DoubleValue musketFireVelocity;
	public final ModConfigSpec.DoubleValue musketFireInaccuracy;
	public final ModConfigSpec.DoubleValue handCannonFireVelocity;
	public final ModConfigSpec.DoubleValue handCannonFireInaccuracy;
	public final ModConfigSpec.DoubleValue dragonsBreathCannonFireVelocity;
	public final ModConfigSpec.DoubleValue dragonsBreathCannonFireInaccuracy;
	public final ModConfigSpec.IntValue ventusStaffRadius;
	public final ModConfigSpec.IntValue meteorStaffMaxUseRange;
	public final ModConfigSpec.DoubleValue meteorStaffExplosionRadius;
	public final ModConfigSpec.BooleanValue meteorStaffExplosionBreakBlocks;
	public final ModConfigSpec.IntValue cursedSightStaffMaxUseRange;
	public final ModConfigSpec.IntValue sculkStaffMaxUseRange;
	public final ModConfigSpec.BooleanValue sculkStaffSonicBlastThroughBlocks;
	public final ModConfigSpec.IntValue recoveryStaffMaxUseRange;
	public final ModConfigSpec.DoubleValue smokeGrenadeEffectRange;
	public final ModConfigSpec.DoubleValue flashbangEffectRange;
	public final ModConfigSpec.IntValue flashbangDisorientTime;

	public ServerConfig(ModConfigSpec.Builder builder) {
		builder.comment("General settings")
				.push("general");

		tiltrosEnabled = builder
				.comment("Enable or disable the Tiltros dimension portal")
				.define("tiltrosEnabled", true);

		forceSmokeGrenadeParticles = builder
				.comment("Force the number of particles produced by the smoke grenade to be the same on all clients. A value of -1 will allow clients to define their own values.")
				.defineInRange("forceSmokeGrenadeParticles", -1, -1, 255);

		panicAlarmRange = builder
				.comment("Set the range of the Panic Alarm's sound")
				.defineInRange("panicAlarmRange", 48, 0, 128);

		blockDecay = builder
				.comment("Allow damageable blocks like barbed wire and wooden spikes to decay as they are used")
				.define("blockDecay", true);

		maxArmorProtection = builder
				.comment("Set the maximum armor protection cap. The vanilla default is 20, but higher values allow better armor to work as intended. 25 is fully unlocked.")
				.defineInRange("maxArmorProtection", 25.0d, 20.0d, 25.0d);

		bulletDespawnTimeModifier = builder
				.comment("Set the bullet despawn time modifier. Increasing this will cause bullets to despawn faster. By default, these take 60 seconds to despawn once they hit the ground.")
				.defineInRange("customArrowDespawnTimeModifier", 1, 1, 10);

		builder.comment("Celestial Altar")
				.push("celestial_altar");

		celestialAltarEnchantCaps = builder
				.comment("Specify maximum caps on enchantments to prevent the Celestial Altar from upgrading them past a certain level. Max level enchants appear with a gold tooltip.")
				.defineListAllowEmpty("celestialAltarEnchantCaps",
						enchantCapMapToList(),
						() -> "namespace:path;cap",
						validator -> {
							String[] split = validator.toString().split(";");
							boolean validLocation = ResourceLocation.tryParse(split[0]) != null;
							boolean validCap = split.length == 2 && split[1].matches("\\d+");

							return validLocation && validCap;
						});

		celestialAltarMaxEnchantUpgradeCost = builder
				.comment("Set the maximum Celestial Fragment cost for enchantment upgrades")
				.defineInRange("celestialAltarMaxEnchantUpgradeCost", 32, 0, 64);

		builder.pop();

		builder.pop();

		builder.comment("Entity settings")
				.push("entity");

		discoveryAdvancementRange = builder
				.comment("Set the range for checking criteria of the entity discovery advancement")
				.defineInRange("discoveryAdvancementRange", 50, 0, 255);

		builder.comment("Celestial Tower")
				.push("celestial_tower");

		celestialTowerSpawnCheckingRadius = builder
				.comment("Set the spawn checking radius for the Celestial Tower. Higher values increase the effectiveness of Celestial Lanterns.")
				.defineInRange("celestialTowerSpawnCheckingRadius", 256, 0, 512);

		celestialTowerWaveSizeModifier = builder
				.comment("Multiplier to change the wave size of Celestial Tower summons")
				.defineInRange("celestialTowerWaveSizeModifier", 1.0d, 0.0d, 5.0d);

		builder.pop();

		builder.comment("The Commander")
				.push("the_commander");

		theCommanderWaveSizeModifier = builder
				.comment("Multiplier to change the wave size of The Commander summons")
				.defineInRange("theCommanderWaveSizeModifier", 1.0d, 0.0d, 5.0d);

		builder.pop();

		builder.pop();

		builder.comment("Weapons")
				.push("weapons");

		gunCritChance = builder
				.comment("Set the chance for a fired bullet to be critical")
				.defineInRange("gunCritChance", 0.1d, 0.0d, 1.0d);

		bulletsBreakGlass = builder
				.comment("Allow bullets to break glass")
				.define("bulletsBreakGlass", true);

		infiniteAmmoOnAllTiers = builder
				.comment("Allow infinity-type enchantments to work on all ammo tiers. By default, it is restricted to cobalt and lower tiers.")
				.define("infiniteAmmoOnAllTiers", false);

		builder.comment("Flintlock Pistol")
				.push("flintlock_pistol");

		flintlockPistolFireVelocity = builder
				.comment("Set the base velocity of bullets")
				.defineInRange("flintlockPistolFireVelocity", 2.5f, 0.0f, 10.0f);

		flintlockPistolFireInaccuracy = builder
				.comment("Set the inaccuracy modifier")
				.defineInRange("flintlockPistolFireInaccuracy", 1.75f, 0.0f, 10.0f);

		builder.pop();

		builder.comment("Blunderbuss")
				.push("blunderbuss");

		blunderbussFireVelocity = builder
				.comment("Set the base velocity of bullets")
				.defineInRange("blunderbussFireVelocity", 1.7f, 0.0f, 10.0f);

		blunderbussFireInaccuracy = builder
				.comment("Set the inaccuracy modifier")
				.defineInRange("blunderbussFireInaccuracy", 2.0f, 0.0f, 10.0f);

		builder.pop();

		builder.comment("Flare Gun")
				.push("flare_gun");

		flareGunFireVelocity = builder
				.comment("Set the base velocity of bullets")
				.defineInRange("flareGunFireVelocity", 2.5f, 0.0f, 10.0f);

		flareGunFireInaccuracy = builder
				.comment("Set the inaccuracy modifier")
				.defineInRange("flareGunFireInaccuracy", 1.75f, 0.0f, 10.0f);

		builder.pop();

		builder.comment("Musket")
				.push("musket");

		musketFireVelocity = builder
				.comment("Set the base velocity of bullets")
				.defineInRange("musketFireVelocity", 4.0f, 0.0f, 10.0f);

		musketFireInaccuracy = builder
				.comment("Set the inaccuracy modifier")
				.defineInRange("musketFireInaccuracy", 0.15f, 0.0f, 10.0f);

		builder.pop();

		builder.comment("Hand Cannon")
				.push("hand_cannon");

		handCannonFireVelocity = builder
				.comment("Set the base velocity of bullets")
				.defineInRange("handCannonFireVelocity", 2.55f, 0.0f, 10.0f);

		handCannonFireInaccuracy = builder
				.comment("Set the inaccuracy modifier")
				.defineInRange("handCannonFireInaccuracy", 1.85f, 0.0f, 10.0f);

		builder.pop();

		builder.comment("Dragons Breath Cannon")
				.push("dragons_breath_cannon");

		dragonsBreathCannonFireVelocity = builder
				.comment("Set the base velocity of bullets")
				.defineInRange("dragonsBreathCannonFireVelocity", 2.65f, 0.0f, 10.0f);

		dragonsBreathCannonFireInaccuracy = builder
				.comment("Set the inaccuracy modifier")
				.defineInRange("dragonsBreathCannonFireInaccuracy", 1.8f, 0.0f, 10.0f);

		builder.pop();

		builder.comment("Ventus Staff")
				.push("ventus_staff");

		ventusStaffRadius = builder
				.comment("Set radius in blocks where the Ventus Staff can push things")
				.defineInRange("ventusStaffRadius", 3, 0, 64);

		builder.pop();

		builder.comment("Meteor Staff")
				.push("meteor_staff");

		meteorStaffMaxUseRange = builder
				.comment("Set the maximum range the staff can be used")
				.defineInRange("meteorStaffMaxUseRange", 100, 0, 255);

		meteorStaffExplosionRadius = builder
				.comment("Set the radius of the explosion created")
				.defineInRange("meteorStaffExplosionRadius", 3.0f, 0.0f, 10.0f);

		meteorStaffExplosionBreakBlocks = builder
				.comment("Allow explosions to break blocks")
				.define("meteorStaffExplosionBreakBlocks", false);

		builder.pop();

		builder.comment("Cursed Sight Staff")
				.push("cursed_sight_staff");

		cursedSightStaffMaxUseRange = builder
				.comment("Set the maximum range the staff can be used")
				.defineInRange("cursedSightStaffMaxUseRange", 50, 0, 255);

		builder.pop();

		builder.comment("Sculk Staff")
				.push("sculk_staff");

		sculkStaffMaxUseRange = builder
				.comment("Set the maximum range the staff can be used")
				.defineInRange("sculkStaffMaxUseRange", 15, 0, 255);

		sculkStaffSonicBlastThroughBlocks = builder
				.comment("Allow the sonic blast to travel through blocks")
				.define("sculkStaffSonicBlastThroughBlocks", true);

		builder.pop();

		builder.comment("Recovery Staff")
				.push("recovery_staff");

		recoveryStaffMaxUseRange = builder
				.comment("Set the maximum range the staff can be used")
				.defineInRange("recoveryStaffMaxUseRange", 15, 0, 255);

		builder.pop();

		builder.pop();

		builder.comment("Throwables")
				.push("throwables");

		builder.comment("Smoke Grenade")
				.push("smoke_grenade");

		smokeGrenadeEffectRange = builder
				.comment("Set the effect range of smoke grenades. This is specifically for the target-clearing effect on non-player entities.")
				.defineInRange("smokeGrenadeEffectRange", 7.0d, 0.0d, 32.0d);

		builder.pop();

		builder.comment("Flashbang")
				.push("flashbang");

		flashbangEffectRange = builder
				.comment("Set the effect range of flashbangs")
				.defineInRange("flashbangEffectRange", 10.0d, 0.0d, 32.0d);

		flashbangDisorientTime = builder
				.comment("Set the time in ticks that entities are disoriented by flashbangs")
				.defineInRange("flashbangDisorientTime", 100, 0, 200);

		builder.pop();

		builder.pop();
	}

	/**
	 * Finds a given enchant cap within the configuration list
	 *
	 * @param enchantment the enchantment to find, must be namespaced
	 * @return the enchantment cap
	 */
	public static int getEnchantCap(String enchantment) {
		List<?> caps = IWConfigs.SERVER.celestialAltarEnchantCaps.get();
		AtomicInteger enchantCap = new AtomicInteger(-1);

		caps.forEach((entry) -> {
			// Separate the enchantment from the cap
			String[] split = entry.toString().split(";");
			if (split[0].equals(enchantment)) {
				enchantCap.set(Integer.parseInt(split[1]));
			}
		});

		return enchantCap.get();
	}

	/**
	 * Convert the enchant caps map into a list.
	 *
	 * @return a list of enchant caps
	 */
	private static List<?> enchantCapMapToList() {
		List<Object> enchantCaps = new ArrayList<>(50);
		Map<String, Integer> enchantCapsMap = getEnchantCapsMap();
		enchantCapsMap.forEach((enchant, cap) -> {
			enchantCaps.add(enchant + ";" + cap);
		});

		return enchantCaps;
	}

	private static Map<String, Integer> getEnchantCapsMap() {
		Map<String, Integer> enchantCaps = new LinkedHashMap<>(40);
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
		enchantCaps.put("minecraft:density", 5);
		enchantCaps.put("minecraft:wind_burst", 10);
		enchantCaps.put("minecraft:breach", 4);
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
		enchantCaps.put("immersiveweapons:nightmarish_stare", 19);
		enchantCaps.put("immersiveweapons:crimson_claw", 5);
		return enchantCaps;
	}
}