package tech.anonymoushacker1279.iwcompatbridge.plugin.wthit.data_provider;

import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import tech.anonymoushacker1279.immersiveweapons.blockentity.StarForgeBlockEntity;

public class StarForgeBlockDataProvider implements IDataProvider<StarForgeBlockEntity> {

	@Override
	public void appendData(IDataWriter data, IServerAccessor<StarForgeBlockEntity> accessor, IPluginConfig config) {
		data.raw().merge(accessor.getTarget().getUpdateTag(accessor.getWorld().registryAccess()));
	}
}