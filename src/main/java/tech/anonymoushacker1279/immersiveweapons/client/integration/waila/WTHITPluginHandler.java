package tech.anonymoushacker1279.immersiveweapons.client.integration.waila;

import mcp.mobius.waila.api.IRegistrar;
import tech.anonymoushacker1279.immersiveweapons.block.trap.PitfallBlock;
import tech.anonymoushacker1279.immersiveweapons.client.integration.waila.overrides.PitfallBlockOverride;

public class WTHITPluginHandler {

	protected static void registerPlugin(IRegistrar registrar) {
		registrar.addOverride(new PitfallBlockOverride(), PitfallBlock.class);
	}
}