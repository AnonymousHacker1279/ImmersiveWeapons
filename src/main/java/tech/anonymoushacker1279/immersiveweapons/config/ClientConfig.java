package tech.anonymoushacker1279.immersiveweapons.config;

@SuppressWarnings("CanBeFinal")
// TODO: rework config
public class ClientConfig {

	// @ConfigEntry(comment = "Enable or disable the Tesla Armor effect sound", group = "Sounds")
	public static boolean teslaArmorEffectSound = true;

	// @ConfigEntry(comment = "Set the number of particles produced by the smoke grenade. The server may choose to override this value to encourage fairness.", group = "Graphics", min = 0, max = 256)
	public static int smokeGrenadeParticles = 96;

	// @ConfigEntry(comment = "Render smoke grenade particles at 66% of the regular size, spawn 3x more, and add translucency. This will negatively impact performance, but make smoke grenades appear more realistic.", group = "Graphics")
	public static boolean fancySmokeGrenadeParticles = false;

	// @ConfigEntry(comment = "Set the number of blood particles created when entities are shot", group = "Graphics", min = 0, max = 128)
	public static int gunShotBloodParticles = 16;

	// @ConfigEntry(comment = "Make the flash effect black instead of white", group = "Graphics")
	public static boolean darkModeFlashbangs = false;
}