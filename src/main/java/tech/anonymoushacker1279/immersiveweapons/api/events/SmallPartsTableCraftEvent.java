package tech.anonymoushacker1279.immersiveweapons.api.events;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;

/**
 * This event is fired when a player crafts an item using the small parts table.
 */
@SuppressWarnings("unused")
public class SmallPartsTableCraftEvent extends Event {

	private final Player player;
	private final ItemStack craftedItem;

	/**
	 * This event is fired when a player crafts an item using the small parts table.
	 *
	 * @param player      the {@link Player} who crafted the item
	 * @param craftedItem the {@link ItemStack} that was crafted
	 */
	public SmallPartsTableCraftEvent(Player player, ItemStack craftedItem) {
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