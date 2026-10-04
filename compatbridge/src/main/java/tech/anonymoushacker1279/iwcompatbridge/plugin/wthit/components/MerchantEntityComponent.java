package tech.anonymoushacker1279.iwcompatbridge.plugin.wthit.components;

import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.ITooltip;
import net.minecraft.network.chat.Component;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.AbstractMerchantEntity;

public class MerchantEntityComponent implements IEntityComponentProvider {

	@Override
	public void appendBody(ITooltip tooltip, IEntityAccessor accessor, IPluginConfig config) {
		if (accessor.getEntity() instanceof AbstractMerchantEntity merchant) {
			int refreshTime = merchant.getTradeRefreshTime(); // Time in ticks before trades are reset

			// Display the time in minutes:seconds
			int minutes = refreshTime / 1200;
			int seconds = (refreshTime % 1200) / 20;

			tooltip.addLine(Component.translatable("iwcompatbridge.wthit.merchant.trade_refresh_time", minutes, seconds));
		}
	}
}