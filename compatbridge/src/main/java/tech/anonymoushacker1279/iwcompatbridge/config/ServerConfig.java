package tech.anonymoushacker1279.iwcompatbridge.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public record ServerConfig(ModConfigSpec.BooleanValue accessoryStacking) {

	public ServerConfig(ModConfigSpec.Builder builder) {
		builder.comment("Curios settings")
				.push("Curios");

		this(builder
				.comment("Allow multiple accessories of the same type to be equipped at once")
				.define("accessoryStacking", false));

		builder.pop();
	}
}