package tech.anonymoushacker1279.iwcompatbridge.config;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class IWCBConfigs {

	public static final ClientConfig CLIENT;
	public static final ModConfigSpec CLIENT_SPEC;
	public static final ServerConfig SERVER;
	public static final ModConfigSpec SERVER_SPEC;

	public static void init(ModContainer container) {
		container.registerConfig(Type.CLIENT, CLIENT_SPEC);
		container.registerConfig(Type.SERVER, SERVER_SPEC);
	}

	static {
		final Pair<ClientConfig, ModConfigSpec> clientSpecPair = new ModConfigSpec.Builder().configure(ClientConfig::new);
		final Pair<ServerConfig, ModConfigSpec> serverSpecPair = new ModConfigSpec.Builder().configure(ServerConfig::new);

		CLIENT = clientSpecPair.getLeft();
		CLIENT_SPEC = clientSpecPair.getRight();
		SERVER = serverSpecPair.getLeft();
		SERVER_SPEC = serverSpecPair.getRight();
	}
}