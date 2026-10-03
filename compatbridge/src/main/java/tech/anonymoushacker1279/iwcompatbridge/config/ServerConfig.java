package tech.anonymoushacker1279.iwcompatbridge.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig {

	public final ModConfigSpec.BooleanValue accessoryStacking;

	public ServerConfig(ModConfigSpec.Builder builder) {
		builder.comment("Curios settings")
				.push("Curios");

		accessoryStacking = builder
				.comment("Allow multiple accessories of the same type to be equipped at once")
				.define("accessoryStacking", false);

		builder.pop();
	}
}