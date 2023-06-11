package tech.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class AccessoryItem extends Item {

	private final AccessorySlot slot;
	private final Map<EffectType, Double> effects;

	/**
	 * AccessoryItems provide various effects when equipped. There are specific categories they may be placed in, and only
	 * one item from each category may be active at a time.
	 *
	 * @param properties the <code>Properties</code> for the item
	 * @param slot       the <code>AccessorySlot</code> the item belongs to
	 * @param effects    a <code>Map</code> of <code>EffectType</code> to <code>Double</code> values
	 */
	public AccessoryItem(Properties properties, AccessorySlot slot, Map<EffectType, Double> effects) {
		super(properties);

		this.slot = slot;
		this.effects = effects;
	}

	public AccessorySlot getSlot() {
		return slot;
	}

	public Map<EffectType, Double> getEffects() {
		return effects;
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

	/**
	 * Builder for creating effect maps.
	 */
	public static class EffectMapBuilder {

		private final Map<EffectType, Double> effects = new HashMap<>(5);

		public EffectMapBuilder addEffect(EffectType type, double value) {
			effects.put(type, value);
			return this;
		}

		public Map<EffectType, Double> build() {
			return effects;
		}
	}

	public enum AccessorySlot {
		BODY,
		NECKLACE,
		HAND,
		RING,
		BELT,
		CHARM,
		SPIRIT
	}

	public enum EffectType {
		FIREARM_AMMO_CONSERVATION_CHANCE,
		FIREARM_RELOAD_SPEED,
		MELEE_DAMAGE,
		PROJECTILE_DAMAGE,
		GENERAL_DAMAGE,
		DAMAGE_RESISTANCE,
		KNOCKBACK_RESISTANCE,
		MELEE_KNOCKBACK,
		MELEE_BLEED_CHANCE,
		OTHER
	}
}