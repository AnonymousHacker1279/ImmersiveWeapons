package tech.anonymoushacker1279.iwcompatbridge.plugin.wthit.components;

import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.ITooltip;
import net.minecraft.network.chat.Component;
import tech.anonymoushacker1279.immersiveweapons.blockentity.StarForgeBlockEntity;

public class StarForgeComponent implements IBlockComponentProvider {

	@Override
	public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config) {
		if (accessor.getBlockEntity() instanceof StarForgeBlockEntity) {
			int smeltTime = accessor.getData().raw().getIntOr("smeltTime", 0);

			if (smeltTime > 0) {
				tooltip.addLine(Component.translatable("iwcompatbridge.wthit.star_forge.progress", smeltTime / 20));
			}
		}
	}
}