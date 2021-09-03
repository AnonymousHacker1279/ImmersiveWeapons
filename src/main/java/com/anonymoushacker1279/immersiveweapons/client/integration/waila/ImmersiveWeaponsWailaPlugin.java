package com.anonymoushacker1279.immersiveweapons.client.integration.waila;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.WailaPlugin;
import net.minecraftforge.fml.ModList;

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