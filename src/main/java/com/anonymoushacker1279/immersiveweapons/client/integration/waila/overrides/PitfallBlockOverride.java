package com.anonymoushacker1279.immersiveweapons.client.integration.waila.overrides;

import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class PitfallBlockOverride implements IBlockComponentProvider {
	@Override
	public BlockState getOverride(IBlockAccessor accessor, IPluginConfig config) {
		return Blocks.GRASS_BLOCK.defaultBlockState();
	}
}