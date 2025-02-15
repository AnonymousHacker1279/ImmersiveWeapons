package tech.anonymoushacker1279.immersiveweapons.client.tooltip;

import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

public interface DynamicTooltip {

	void computeTooltip(ItemTooltipEvent event);

	default boolean shouldComputeSimpleTooltips(ItemTooltipEvent event) {
		return true;
	}
}