package tech.anonymoushacker1279.immersiveweapons.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.apache.commons.lang3.tuple.Pair;

public class ClientConfig {

	public static final ForgeConfigSpec CLIENT_SPEC;
	public static final ClientConfig CLIENT;

	public static ForgeConfigSpec.BooleanValue TESLA_ARMOR_EFFECT_SOUND;
	public static ForgeConfigSpec.IntValue PANIC_ALARM_RANGE;
	public static ForgeConfigSpec.IntValue SMOKE_GRENADE_PARTICLES;
	public static ForgeConfigSpec.BooleanValue FANCY_SMOKE_GRENADE_PARTICLES;

	ClientConfig(ForgeConfigSpec.Builder builder) {
		builder.push("Client Configuration");

		builder.push("Graphics");
		SMOKE_GRENADE_PARTICLES = builder
				.comment("Set the number of particles produced by the smoke grenade\n" +
						"The server may choose to override this value to encourage fairness. - Default 96")
				.translation("config.immersiveweapons.smoke_grenade_particles")
				.defineInRange("smoke_grenade_particles", 96, 0, Integer.MAX_VALUE);
		FANCY_SMOKE_GRENADE_PARTICLES = builder
				.comment("Render smoke grenade particles at 66% of the regular size, spawn 3x more, and add translucency.\n" +
						"This will negatively impact performance, but make smoke grenades appear more realistic. - Default false")
				.translation("config.immersiveweapons.fancy_smoke_grenade_particles")
				.define("fancy_smoke_grenade_particles", false);
		builder.pop();

		builder.push("Sounds");
		TESLA_ARMOR_EFFECT_SOUND = builder
				.comment("Enable the Tesla Armor effect sound - Default true")
				.translation("config.immersiveweapons.tesla_armor_effect_sound")
				.define("tesla_armor_effect_sound", true);
		PANIC_ALARM_RANGE = builder
				.comment("Set the range of the Panic Alarm's sound - Default 48")
				.translation("config.immersiveweapons.panic_alarm_range")
				.defineInRange("panic_alarm_range", 48, 0, Integer.MAX_VALUE);
		builder.pop();

		builder.pop();
	}

	static {
		Pair<ClientConfig, ForgeConfigSpec> clientForgeConfigSpecPair = new Builder().configure(ClientConfig::new);

		CLIENT_SPEC = clientForgeConfigSpecPair.getRight();
		CLIENT = clientForgeConfigSpecPair.getLeft();
	}
}