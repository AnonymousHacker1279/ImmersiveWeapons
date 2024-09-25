package tech.anonymoushacker1279.immersiveweapons.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ClientConfig {

	public final ModConfigSpec.BooleanValue teslaArmorEffectSound;
	public final ModConfigSpec.IntValue smokeGrenadeParticles;
	public final ModConfigSpec.BooleanValue fancySmokeGrenadeParticles;
	public final ModConfigSpec.IntValue gunShotBloodParticles;
	public final ModConfigSpec.BooleanValue darkModeFlashbangs;

	public ClientConfig(ModConfigSpec.Builder builder) {
		teslaArmorEffectSound = builder
				.comment("Enable or disable the Tesla Armor effect sound")
				.define("teslaArmorEffectSound", true);

		smokeGrenadeParticles = builder
				.comment("Set the number of particles produced by the smoke grenade. The server may choose to override this value to encourage fairness.")
				.defineInRange("smokeGrenadeParticles", 96, 0, 255);

		fancySmokeGrenadeParticles = builder
				.comment("Render smoke grenade particles at 66% of the regular size, spawn 3x more, and add translucency. This will negatively impact performance, but make smoke grenades appear more realistic.")
				.define("fancySmokeGrenadeParticles", false);

		gunShotBloodParticles = builder
				.comment("Set the number of blood particles created when entities are shot")
				.defineInRange("gunShotBloodParticles", 16, 0, 128);

		darkModeFlashbangs = builder
				.comment("Make the flash effect black instead of white")
				.define("darkModeFlashbangs", false);
	}
}