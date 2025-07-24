package tech.anonymoushacker1279.immersiveweapons.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ClientConfig {

	public final ModConfigSpec.BooleanValue teslaArmorEffectSound;
	public final ModConfigSpec.IntValue smokeGrenadeParticles;
	public final ModConfigSpec.BooleanValue fancySmokeGrenadeParticles;
	public final ModConfigSpec.IntValue gunShotBloodParticles;
	public final ModConfigSpec.BooleanValue darkModeFlashbangs;
	public final ModConfigSpec.BooleanValue enableDamageIndicatorParticles;
	public final ModConfigSpec.ConfigValue<String> damageIndicatorLowDamageColor;
	public final ModConfigSpec.ConfigValue<String> damageIndicatorMediumDamageColor;
	public final ModConfigSpec.ConfigValue<String> damageIndicatorHighDamageColor;
	public final ModConfigSpec.DoubleValue damageIndicatorParticleScale;
	public final ModConfigSpec.IntValue damageIndicatorParticleLifetime;

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

		builder.comment("Damage Indicators")
				.push("damageIndicators");

		enableDamageIndicatorParticles = builder
				.comment("Enable or disable damage indicator particles when an entity is struck")
				.define("enableDamageIndicatorParticles", false);

		damageIndicatorLowDamageColor = builder
				.comment("Set the color of the damage indicator particles for low damage. Hexadecimal format (e.g. \"#FFFFFF\" for white).")
				.define("damageIndicatorLowDamageColor", "#FFFFFF", this::isValidHexColor);

		damageIndicatorMediumDamageColor = builder
				.comment("Set the color of the damage indicator particles for medium damage. Hexadecimal format (e.g. \"#FFFF00\" for yellow).")
				.define("damageIndicatorMediumDamageColor", "#FFFF00", this::isValidHexColor);

		damageIndicatorHighDamageColor = builder
				.comment("Set the color of the damage indicator particles for high damage. Hexadecimal format (e.g. \"#FF0000\" for red).")
				.define("damageIndicatorHighDamageColor", "#FF0000", this::isValidHexColor);

		damageIndicatorParticleScale = builder
				.comment("Set the scale of the damage indicator particles. Default is 1.0.")
				.defineInRange("damageIndicatorParticleScale", 1.0, 0.01D, 2.0D);

		damageIndicatorParticleLifetime = builder
				.comment("Set the lifetime of the damage indicator particles. Default is three seconds.")
				.defineInRange("damageIndicatorParticleLifetime", 3, 1, 10);

		builder.pop();
	}

	private boolean isValidHexColor(Object color) {
		return color instanceof String str && str.matches("#[0-9a-fA-F]{6}");
	}
}