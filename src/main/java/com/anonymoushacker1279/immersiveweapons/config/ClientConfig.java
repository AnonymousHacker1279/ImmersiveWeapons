package com.anonymoushacker1279.immersiveweapons.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

public class ClientConfig {

	private static final ForgeConfigSpec.Builder CONFIG_BUILDER = new ForgeConfigSpec.Builder();
	private static final ForgeConfigSpec CONFIG;

	public static ForgeConfigSpec.ConfigValue<Boolean> TESLA_ARMOR_EFFECT_SOUND;

	/*
	 * Initialize the client configuration file.
	 */
	static {
		CONFIG_BUILDER.push("Client Configuration");

		CONFIG_BUILDER.push("Sounds");
		TESLA_ARMOR_EFFECT_SOUND = CONFIG_BUILDER
				.comment("Enable the Tesla Armor effect sound - Default true")
				.translation("config.immersiveweapons.tesla_armor_effect_sound")
				.define("tesla_armor_effect_sound", true);
		CONFIG_BUILDER.pop();

		CONFIG_BUILDER.pop();
		CONFIG = CONFIG_BUILDER.build();
	}

	/**
	 * Setup a configuration file.
	 *
	 * @param path the <code>Path</code> of the file
	 */
	public static void setup(Path path) {
		CommentedFileConfig configData = CommentedFileConfig.builder(path)
				.sync()
				.autosave()
				.writingMode(WritingMode.REPLACE)
				.build();

		configData.load();
		CONFIG.setConfig(configData);
	}
}