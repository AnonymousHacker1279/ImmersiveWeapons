package tech.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.api.PluginHandler;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem.EffectBuilder.EffectScalingType;
import tech.anonymoushacker1279.immersiveweapons.util.IWCBBridge;

import java.util.*;

public class AccessoryItem extends Item {

	private final AccessorySlot slot;
	private final Map<EffectType, Double> effects;
	private final EffectScalingType effectScalingType;

	/**
	 * AccessoryItems provide various effects when equipped. There are specific categories they may be placed in, and only
	 * one item from each category may be active at a time.
	 *
	 * @param properties    the <code>Properties</code> for the item
	 * @param slot          the <code>AccessorySlot</code> the item belongs to
	 * @param effectBuilder the <code>EffectBuilder</code> for the item
	 */
	public AccessoryItem(Properties properties, AccessorySlot slot, EffectBuilder effectBuilder) {
		super(properties);

		this.slot = slot;
		this.effects = effectBuilder.getEffects();
		this.effectScalingType = effectBuilder.getScalingType();
	}

	public AccessorySlot getSlot() {
		return slot;
	}

	public Map<EffectType, Double> getEffects() {
		return effects;
	}

	public EffectScalingType getEffectScalingType() {
		return effectScalingType;
	}

	/**
	 * Check if this accessory is active. This should be used for effects that can stack, because it will ensure only one
	 * accessory of its type is active at a time. For non-stacking effects, {@link #isActive(Player)} can be used.
	 *
	 * @param player the <code>Player</code> to check
	 * @param stack  the <code>ItemStack</code> of the accessory
	 * @return true if the accessory is active, false otherwise
	 */
	public boolean isActive(Player player, ItemStack stack) {
		List<ItemStack> accessories = new ArrayList<>(10);

		// Check the player's inventory for accessories of the same type. Only one may be active at a time.
		for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
			ItemStack stack1 = player.getInventory().getItem(i);
			if (stack1.getItem() instanceof AccessoryItem accessoryItem) {
				if (accessoryItem.getSlot() == slot) {
					accessories.add(stack1);
				}
			}
		}

		if (accessories.isEmpty()) {
			return false;
		}

		// If there are multiple accessories of the same type, only the first one is active.
		return accessories.get(0) == stack;
	}

	/**
	 * Check if this accessory is active. Note, this is not the same as {@link #isActive(Player, ItemStack)}. It is not
	 * sensitive with multiple of the same accessory in the player's inventory.
	 *
	 * @param player the <code>Player</code> to check
	 * @return true if the accessory is active, false otherwise
	 */
	public boolean isActive(Player player) {
		for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
			ItemStack stack1 = player.getInventory().getItem(i);
			if (stack1.getItem() instanceof AccessoryItem accessoryItem) {
				if (accessoryItem.getSlot() == slot) {
					return accessoryItem == this;
				}
			}
		}

		return false;
	}

	/**
	 * Check if the specified accessory is active for the player. By default, this refers back to {@link #isActive(Player)}.
	 * If IWCB is installed and the Curios plugin is registered, it will defer to IWCB.
	 *
	 * @param player the <code>Player</code> to check
	 * @param item   the <code>AccessoryItem</code> to check
	 * @return true if the accessory is active, false otherwise
	 */
	public static boolean isAccessoryActive(Player player, AccessoryItem item) {
		if (ImmersiveWeapons.IWCB_LOADED && PluginHandler.isPluginActive("iwcompatbridge:curios_plugin")) {
			return IWCBBridge.isAccessoryActive(player, item);
		} else {
			return item.isActive(player);
		}
	}

	/**
	 * Builder for creating effects.
	 */
	public static class EffectBuilder {

		private final Map<EffectType, Double> effects = new HashMap<>(5);
		private EffectScalingType scalingType = EffectScalingType.NO_SCALING;

		public EffectBuilder addEffect(EffectType type, double value) {
			effects.put(type, value);
			return this;
		}

		public EffectBuilder addEffect(EffectType type, double value, EffectScalingType scalingType) {
			effects.put(type, value);
			this.scalingType = scalingType;
			return this;
		}

		public Map<EffectType, Double> getEffects() {
			return effects;
		}

		public EffectScalingType getScalingType() {
			return scalingType;
		}

		public enum EffectScalingType {
			NO_SCALING,
			DEPTH_SCALING,
			INSOMNIA_SCALING
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
		MELEE_KNOCKBACK,
		MELEE_BLEED_CHANCE,
		OTHER
	}
}