package tech.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

public class AccessoryItem extends Item {

	private final AccessorySlot slot;

	/**
	 * AccessoryItems provide various effects when equipped. There are specific categories they may be placed in, and only
	 * one item from each category may be active at a time.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	public AccessoryItem(Properties properties, AccessorySlot slot) {
		super(properties);

		this.slot = slot;
	}

	public AccessorySlot getSlot() {
		return slot;
	}

	public boolean isActive(Player player) {
		// Check for other items in the inventory which have the same slot
		// The first item will be active, all others inactive

		for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
			if (player.getInventory().getItem(i).getItem() instanceof AccessoryItem accessoryItem) {
				if (accessoryItem.getSlot() == slot) {
					return accessoryItem == this;
				}
			}
		}

		return false;
	}

	/**
	 * Get the accessory in the specified slot from the player's inventory.
	 * <p>
	 * Because this checks for the first item, it will always be an active one.
	 */
	@Nullable
	public static AccessoryItem getAccessory(Player player, AccessorySlot slot) {
		for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
			if (player.getInventory().getItem(i).getItem() instanceof AccessoryItem accessoryItem) {
				if (accessoryItem.getSlot() == slot) {
					return accessoryItem;
				}
			}
		}

		return null;
	}

	public enum AccessorySlot {
		BODY,
		BELT,
		CHARM,
		SPIRIT
	}
}