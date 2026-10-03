package tech.anonymoushacker1279.iwcompatbridge.plugin.wthit;

import mcp.mobius.waila.api.IClientRegistrar;
import mcp.mobius.waila.api.IWailaClientPlugin;
import net.neoforged.fml.ModList;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

@SuppressWarnings("unused")
public class WTHITClientPluginHandler implements IWailaClientPlugin {
	private final ModList modList = ModList.get();

	@Override
	public void register(IClientRegistrar registrar) {
		if (modList.isLoaded("jade")) {
			ImmersiveWeapons.LOGGER.info("Jade is installed, which is incompatible with the Waila plugin. The plugin will be disabled to prevent crashes.");
		} else {
			WTHITPluginRegisters.registerClientPlugin(registrar);
		}
	}
}