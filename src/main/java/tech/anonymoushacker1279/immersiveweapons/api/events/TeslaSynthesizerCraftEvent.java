package tech.anonymoushacker1279.immersiveweapons.api.events;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;

/**
 * This event is fired when a player crafts an item using the tesla synthesizer.
 */
@SuppressWarnings("unused")
public class TeslaSynthesizerCraftEvent extends Event {

	private final Player player;
	private final ItemStack craftedItem;

	/**
	 * This event is fired when a player crafts an item using the tesla synthesizer.
	 *
	 * @param player      the {@link Player} who crafted the item
	 * @param craftedItem the {@link ItemStack} that was crafted
	 */
	public TeslaSynthesizerCraftEvent(Player player, ItemStack craftedItem) {
		this.player = player;
		this.craftedItem = craftedItem;
	}

	public Player getPlayer() {
		return player;
	}

	public ItemStack getCraftedItem() {
		return craftedItem;
	}
}