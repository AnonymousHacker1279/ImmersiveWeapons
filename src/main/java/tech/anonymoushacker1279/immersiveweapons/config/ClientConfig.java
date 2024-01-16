package tech.anonymoushacker1279.immersiveweapons.config;

import tech.anonymoushacker1729.cobaltconfig.config.ConfigEntry;

@SuppressWarnings("CanBeFinal")
public class ClientConfig {

	@ConfigEntry(comment = "Enable or disable the Tesla Armor effect sound", group = "Sounds")
	public static boolean teslaArmorEffectSound = true;

	@ConfigEntry(comment = "Set the range of the Panic Alarm's sound", group = "Sounds", min = 0, max = 128)
	public static int panicAlarmRange = 48;

	@ConfigEntry(comment = "Set the number of particles produced by the smoke grenade. The server may choose to override this value to encourage fairness.", group = "Graphics", min = 0, max = 256)
	public static int smokeGrenadeParticles = 96;

	@ConfigEntry(comment = "Render smoke grenade particles at 66% of the regular size, spawn 3x more, and add translucency. This will negatively impact performance, but make smoke grenades appear more realistic.", group = "Graphics")
	public static boolean fancySmokeGrenadeParticles = false;
}