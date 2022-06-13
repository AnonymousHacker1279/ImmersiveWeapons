package tech.anonymoushacker1279.immersiveweapons.client.integration.waila;

import mcp.mobius.waila.api.*;
import net.minecraftforge.fml.ModList;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

@WailaPlugin(id = ImmersiveWeapons.MOD_ID + ":waila_plugin")
public class ImmersiveWeaponsWailaPlugin implements IWailaPlugin {
	private final ModList modList = ModList.get();

	@Override
	public void register(IRegistrar registrar) {
		if (modList.isLoaded("jade")) {
			ImmersiveWeapons.LOGGER.info("Jade is installed, which is incompatible with the Waila plugin. The plugin will be disabled to prevent crashes.");
		} else {
			WTHITPluginHandler.registerPlugin(registrar);
		}
	}
}