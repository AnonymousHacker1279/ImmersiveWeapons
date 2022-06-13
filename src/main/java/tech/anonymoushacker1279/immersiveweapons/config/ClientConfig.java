package tech.anonymoushacker1279.immersiveweapons.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.apache.commons.lang3.tuple.Pair;

public class ClientConfig {

	public static final ForgeConfigSpec CLIENT_SPEC;
	public static final ClientConfig CLIENT;

	public static ForgeConfigSpec.ConfigValue<Boolean> TESLA_ARMOR_EFFECT_SOUND;
	public static ForgeConfigSpec.ConfigValue<Integer> PANIC_ALARM_RANGE;

	ClientConfig(ForgeConfigSpec.Builder builder) {
		builder.push("Client Configuration");

		builder.push("Sounds");
		TESLA_ARMOR_EFFECT_SOUND = builder
				.comment("Enable the Tesla Armor effect sound - Default true")
				.translation("config.immersiveweapons.tesla_armor_effect_sound")
				.define("tesla_armor_effect_sound", true);
		PANIC_ALARM_RANGE = builder
				.comment("Set the range of the Panic Alarm's sound - Default 48")
				.translation("config.immersiveweapons.panic_alarm_range")
				.define("panic_alarm_range", 48);
		builder.pop();

		builder.pop();
	}

	static {
		Pair<ClientConfig, ForgeConfigSpec> clientForgeConfigSpecPair = new Builder().configure(ClientConfig::new);

		CLIENT_SPEC = clientForgeConfigSpecPair.getRight();
		CLIENT = clientForgeConfigSpecPair.getLeft();
	}
}