package tech.anonymoushacker1279.immersiveweapons.config;

import tech.anonymoushacker1729.cobaltconfig.config.ConfigEntry;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("CanBeFinal")
public class CommonConfig {

	@ConfigEntry(comment = "Enable the Tiltros dimension portal", group = "General")
	public static boolean tiltrosEnabled = true;

	@ConfigEntry(comment = "Force the number of particles produced by the smoke grenade to be the same on all clients. A value of -1 will allow clients to define their own values.", group = "General", min = -1, max = 256)
	public static int forceSmokeGrenadeParticles = -1;

	@ConfigEntry(comment = "Set the range of the Panic Alarm's sound", group = "General", min = 0, max = 128)
	public static int panicAlarmRange = 48;

	@ConfigEntry(comment = "Set the maximum armor protection cap. The vanilla default is 20, but higher values allow better armor to work as intended. 25 is fully unlocked.", group = "Combat Rules", min = 20, max = 25)
	public static double maxArmorProtection = 25.0d;

	@ConfigEntry(comment = "Set the range for checking criteria of the entity discovery advancement", group = "Entity Settings", min = 0, max = 256)
	public static int discoveryAdvancementRange = 50;

	@ConfigEntry(comment = "Set the spawn checking radius for the Celestial Tower. Higher values increase the effectiveness of Celestial Lanterns.", group = "Entity Settings", min = 0, max = 512)
	public static int celestialTowerSpawnCheckingRadius = 256;

	@ConfigEntry(comment = "Multiplier to change the wave size of Celestial Tower summons", group = "Entity Settings", min = 0, max = 5.0d)
	public static double celestialTowerWaveSizeModifier = 1.0d;

	@ConfigEntry(comment = "Specify maximum caps on enchantments to prevent the Skygazer from upgrading them past a certain level. Max level enchants appear with a gold tooltip.", group = "Entity Settings")
	public static Map<String, Integer> skygazerEnchantCaps = getEnchantCapsMap();

	@ConfigEntry(comment = "Set the maximum Celestial Fragment cost for enchantment upgrades", group = "Entity Settings", min = 0, max = 64)
	public static int skygazerMaxEnchantUpgradeCost = 32;

	@ConfigEntry(comment = "Set the chance for a fired bullet to be critical", group = "General Weapon Settings", min = 0, max = 1.0d)
	public static double gunCritChance = 0.1d;

	@ConfigEntry(comment = "Allow bullets to break glass", group = "General Weapon Settings")
	public static boolean bulletsBreakGlass = true;

	@ConfigEntry(comment = "Allow infinity-type enchantments to work on all ammo tiers. By default, it is restricted to cobalt and lower tiers.", group = "General Weapon Settings")
	public static boolean infiniteAmmoOnAllTiers = false;

	@ConfigEntry(comment = "Set the base velocity of bullets", group = "Flintlock Pistol", min = 0, max = 10)
	public static float flintlockPistolFireVelocity = 2.5f;

	@ConfigEntry(comment = "Set the inaccuracy modifier", group = "Flintlock Pistol", min = 0, max = 10)
	public static float flintlockPistolFireInaccuracy = 1.75f;

	@ConfigEntry(comment = "Set the base velocity of bullets", group = "Blunderbuss", min = 0, max = 10)
	public static float blunderbussFireVelocity = 1.7f;

	@ConfigEntry(comment = "Set the inaccuracy modifier", group = "Blunderbuss", min = 0, max = 10)
	public static float blunderbussFireInaccuracy = 2.0f;

	@ConfigEntry(comment = "Set the base velocity of bullets", group = "Musket", min = 0, max = 10)
	public static float musketFireVelocity = 4.0f;

	@ConfigEntry(comment = "Set the inaccuracy modifier", group = "Musket", min = 0, max = 10)
	public static float musketFireInaccuracy = 0.15f;

	@ConfigEntry(comment = "Set the base velocity of bullets", group = "Hand Cannon", min = 0, max = 10)
	public static float handCannonFireVelocity = 2.55f;

	@ConfigEntry(comment = "Set the inaccuracy modifier", group = "Hand Cannon", min = 0, max = 10)
	public static float handCannonFireInaccuracy = 1.85f;

	@ConfigEntry(comment = "Set the maximum range the staff can be used", group = "Meteor Staff", min = 0, max = 256)
	public static int meteorStaffMaxUseRange = 100;

	@ConfigEntry(comment = "Set the radius of the explosion created", group = "Meteor Staff", min = 0, max = 10)
	public static float meteorStaffExplosionRadius = 3.0f;

	@ConfigEntry(comment = "Allow explosions to break blocks", group = "Meteor Staff")
	public static boolean meteorStaffExplosionBreakBlocks = false;

	@ConfigEntry(comment = "Set the maximum range the staff can be used", group = "Cursed Sight Staff", min = 0, max = 256)
	public static int cursedSightStaffMaxUseRange = 50;

	@ConfigEntry(comment = "Set the maximum range the staff can be used", group = "Sculk Staff", min = 0, max = 256)
	public static int sculkStaffMaxUseRange = 15;

	@ConfigEntry(comment = "Allow the sonic blast to travel through blocks", group = "Sculk Staff")
	public static boolean sculkStaffSonicBlastThroughBlocks = true;

	@ConfigEntry(comment = "Set the maximum range the staff can be used", group = "Recovery Staff", min = 0, max = 256)
	public static int recoveryStaffMaxUseRange = 15;

	@ConfigEntry(comment = "Set the effect range of smoke grenades. This is specifically for the target-clearing effect on non-player entities.", group = "Throwables", min = 0, max = 32.0d)
	public static double smokeGrenadeEffectRange = 7.0d;

	@ConfigEntry(comment = "Set the effect range of flashbangs", group = "Throwables", min = 0, max = 32.0d)
	public static double flashbangEffectRange = 10.0d;

	@ConfigEntry(comment = "Set the disorient time of flashbangs in seconds. This is specifically for non-player entities.", group = "Throwables", min = 0, max = 60)
	public static int flashbangDisorientTime = 10;

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