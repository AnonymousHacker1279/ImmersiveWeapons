package com.anonymoushacker1279.immersiveweapons.client.integration.waila;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.block.trap.PitfallBlock;
import com.anonymoushacker1279.immersiveweapons.client.integration.waila.overrides.PitfallBlockOverride;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.WailaPlugin;

@WailaPlugin(id = ImmersiveWeapons.MOD_ID + ":waila_plugin")
public class ImmersiveWeaponsWailaPlugin implements IWailaPlugin {
	@Override
	public void register(IRegistrar registrar) {
		registrar.addOverride(new PitfallBlockOverride(), PitfallBlock.class);
	}
}