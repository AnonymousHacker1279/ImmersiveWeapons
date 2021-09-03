package com.anonymoushacker1279.immersiveweapons.client.integration.waila;

import com.anonymoushacker1279.immersiveweapons.block.trap.PitfallBlock;
import com.anonymoushacker1279.immersiveweapons.client.integration.waila.overrides.PitfallBlockOverride;
import mcp.mobius.waila.api.IRegistrar;

public class WTHITPluginHandler {

	protected static void registerPlugin(IRegistrar registrar) {
		registrar.addOverride(new PitfallBlockOverride(), PitfallBlock.class);
	}
}