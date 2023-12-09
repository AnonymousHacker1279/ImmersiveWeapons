package tech.anonymoushacker1279.immersiveweapons.event.game_effects;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.api.PluginHandler;
import tech.anonymoushacker1279.immersiveweapons.client.gui.overlays.DebugTracingData;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem.EffectBuilder.EffectScalingType;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem.EffectType;
import tech.anonymoushacker1279.immersiveweapons.util.IWCBBridge;

import java.util.*;

public class AccessoryManager {

	/**
	 * Collect the value of the given effect from all {@link AccessoryItem}s in the player's inventory.
	 * <p>
	 * This will check to see if IWCB is loaded, and if so, defer to it for collecting effects as it will utilize Curios.
	 *
	 * @param type   the <code>EffectType</code> to collect
	 * @param player the <code>Player</code> to collect from
	 * @return the value of the effect
	 */
	public static double collectEffects(EffectType type, Player player) {
		double effectValue = 0;
		if (ImmersiveWeapons.IWCB_LOADED && PluginHandler.isPluginActive("iwcompatbridge:curios_plugin")) {
			effectValue = IWCBBridge.collectEffects(type, player);
		} else {
			for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
				ItemStack stack = player.getInventory().getItem(i);
				if (stack.getItem() instanceof AccessoryItem accessoryItem) {
					if (accessoryItem.isActive(player, stack)) {
						effectValue += handleEffectScaling(accessoryItem, type, player);
					}
				}
			}
		}

		// Clamp the value at a maximum of 100%
		return Mth.clamp(effectValue, 0, 1);
	}

	/**
	 * Collect the attribute modifiers from all active {@link AccessoryItem}s in the player's inventory.
	 * <p>
	 * This will check to see if IWCB is loaded, and if so, defer to it for collecting attributes as it will utilize Curios.
	 *
	 * @param player the <code>Player</code> to collect from
	 * @return a <code>Map</code> of attribute modifiers
	 */
	public static Map<AttributeModifier, Attribute> collectStandardAttributes(Player player) {
		Map<AttributeModifier, Attribute> attributeMap = new HashMap<>(5);
		if (ImmersiveWeapons.IWCB_LOADED && PluginHandler.isPluginActive("iwcompatbridge:curios_plugin")) {
			attributeMap = IWCBBridge.collectStandardAttributes(player);
		} else {
			for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
				ItemStack stack = player.getInventory().getItem(i);
				if (stack.getItem() instanceof AccessoryItem accessoryItem) {
					if (accessoryItem.isActive(player, stack) && !accessoryItem.getStandardAttributeModifiers().isEmpty()) {
						attributeMap.putAll(accessoryItem.getStandardAttributeModifiers());
					}
				}
			}
		}

		return attributeMap;
	}

	/**
	 * Collect the dynamic attribute modifiers from all active {@link AccessoryItem}s in the player's inventory.
	 * <p>
	 * This will check to see if IWCB is loaded, and if so, defer to it for collecting attributes as it will utilize Curios.
	 *
	 * @param player the <code>Player</code> to collect from
	 * @return a <code>Map</code> of dynamic attribute modifiers with their target values
	 */
	public static Map<Map<AttributeModifier, Attribute>, Double> collectDynamicAttributes(Player player) {
		Map<Map<AttributeModifier, Attribute>, Double> attributeMap = new HashMap<>(5);
		if (ImmersiveWeapons.IWCB_LOADED && PluginHandler.isPluginActive("iwcompatbridge:curios_plugin")) {
			attributeMap = IWCBBridge.collectDynamicAttributes(player);
		} else {
			for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
				ItemStack stack = player.getInventory().getItem(i);
				if (stack.getItem() instanceof AccessoryItem accessoryItem) {
					if (accessoryItem.isActive(player, stack) && !accessoryItem.getDynamicAttributeModifiers().isEmpty()) {
						attributeMap.putAll(accessoryItem.getDynamicAttributeModifiers());
					}
				}
			}
		}

		return attributeMap;
	}

	/**
	 * Collect the mob effect instances from all active {@link AccessoryItem}s in the player's inventory.
	 * <p>
	 * This will check to see if IWCB is loaded, and if so, defer to it for collecting mob effects as it will utilize Curios.
	 *
	 * @param player the <code>Player</code> to collect from
	 * @return a <code>List</code> of mob effect instances
	 */
	public static List<MobEffectInstance> collectMobEffects(Player player) {
		List<MobEffectInstance> effectList = new ArrayList<>(5);
		if (ImmersiveWeapons.IWCB_LOADED && PluginHandler.isPluginActive("iwcompatbridge:curios_plugin")) {
			effectList = IWCBBridge.collectMobEffects(player);
		} else {
			for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
				ItemStack stack = player.getInventory().getItem(i);
				if (stack.getItem() instanceof AccessoryItem accessoryItem) {
					if (accessoryItem.isActive(player, stack) && !accessoryItem.getMobEffects().isEmpty()) {
						effectList.addAll(accessoryItem.getMobEffects());
					}
				}
			}
		}

		return effectList;
	}

	/**
	 * Handle the scaling of effects based on the accessory's scaling type.
	 *
	 * @param accessoryItem the <code>AccessoryItem</code> to handle for
	 * @param type          the <code>EffectType</code> to handle for
	 * @param player        the <code>Player</code> to handle for
	 * @return the scaled effect value
	 */
	public static double handleEffectScaling(AccessoryItem accessoryItem, EffectType type, Player player) {
		if (accessoryItem.getEffectScalingType() == EffectScalingType.DEPTH_SCALING) {
			// Depth scaling increases the value inverse proportionally to the player's depth (y-level), starting at y<64
			// Note, this should continue to the bottom of the world, which may be lower than y=0

			double depth = player.getY();
			if (depth < 64) {
				double rawValue = accessoryItem.getEffects().getOrDefault(type, 0d);
				int worldFloor = player.level().getMinBuildHeight();

				// The scaling is inverse proportionally to the player's depth
				double depthScaling = Mth.clamp(Math.min(1.0, ((64 - depth) / (64 - worldFloor))) * 100, 0, 100);
				return rawValue * depthScaling;
			}
		} else if (accessoryItem.getEffectScalingType() == EffectScalingType.INSOMNIA_SCALING) {
			// Insomnia scaling increases the value proportionally to the player's insomnia level, starting after
			// one full day/night cycle without sleep (24000 ticks)

			int timeSinceRest;
			if (player instanceof ServerPlayer serverPlayer) {
				timeSinceRest = serverPlayer.getStats().getValue(Stats.CUSTOM.get(Stats.TIME_SINCE_REST));
			} else {
				// If this is on the client, use the debug tracing data. The result here does not need to be accurate
				// since it will only ever be used in the debug overlay
				timeSinceRest = DebugTracingData.TICKS_SINCE_REST;
			}

			if (timeSinceRest > 24000) {
				double rawValue = accessoryItem.getEffects().getOrDefault(type, 0d);
				// Scaling should max out after 7 in-game days and nights (168000 ticks)
				double insomniaScaling = Mth.clamp(Math.min(1.0, ((timeSinceRest - 24000) / 144000.0)) * 100, 0, 100);
				return rawValue * insomniaScaling;
			}
		}

		// If no scaling is needed, just return the effect value
		return accessoryItem.getEffects().getOrDefault(type, 0d);
	}

	/**
	 * Handle accessories with mob effects during the player tick event.
	 *
	 * @param player the <code>Player</code> to handle for
	 */
	public static void handleAccessoryMobEffects(Player player) {
		List<MobEffectInstance> activeMobEffects = AccessoryManager.collectMobEffects(player);
		for (MobEffectInstance effect : activeMobEffects) {
			// Duplicate the effect to avoid modifying the original
			MobEffectInstance newEffect = new MobEffectInstance(effect.getEffect(), effect.getDuration(), effect.getAmplifier(), effect.isAmbient(), effect.isVisible(), effect.showIcon());
			player.addEffect(newEffect);
		}
	}

	/**
	 * Handle accessories with attribute modifiers during the player tick event.
	 *
	 * @param player the <code>Player</code> to handle for
	 */
	public static void handleAccessoryAttributes(Player player) {
		Map<AttributeModifier, Attribute> activeStandardAttributeModifiers = handleStandardAttributeModifiers(player);
		Map<Map<AttributeModifier, Attribute>, Double> activeDynamicAttributeModifiers = handleDynamicAttributeModifiers(player);
		handleRemovedAttributes(player, activeStandardAttributeModifiers, activeDynamicAttributeModifiers);
	}

	/**
	 * Handle the adding of standard (non-dynamic) attribute modifiers from accessories.
	 *
	 * @param player the <code>Player</code> to handle for
	 * @return a <code>Map</code> of active standard attribute modifiers
	 */
	private static Map<AttributeModifier, Attribute> handleStandardAttributeModifiers(Player player) {
		Map<AttributeModifier, Attribute> activeStandardAttributeModifiers = AccessoryManager.collectStandardAttributes(player);
		for (Map.Entry<AttributeModifier, Attribute> entry : activeStandardAttributeModifiers.entrySet()) {
			AttributeModifier modifier = entry.getKey();
			Attribute attribute = entry.getValue();

			AttributeInstance attributeInstance = player.getAttributes().getInstance(attribute);
			if (attributeInstance != null) {
				if (!attributeInstance.hasModifier(modifier)) {
					attributeInstance.addTransientModifier(modifier);
				}
			}
		}

		return activeStandardAttributeModifiers;
	}

	/**
	 * Handle the adding of dynamic attribute modifiers from accessories.
	 *
	 * @param player the <code>Player</code> to handle for
	 * @return a <code>Map</code> of active dynamic attribute modifiers with their target values
	 */
	private static Map<Map<AttributeModifier, Attribute>, Double> handleDynamicAttributeModifiers(Player player) {
		Map<Map<AttributeModifier, Attribute>, Double> activeDynamicAttributeModifiers = AccessoryManager.collectDynamicAttributes(player);
		for (Map.Entry<Map<AttributeModifier, Attribute>, Double> entry : activeDynamicAttributeModifiers.entrySet()) {
			Map<AttributeModifier, Attribute> modifiers = entry.getKey();
			double targetValue = entry.getValue();

			for (Map.Entry<AttributeModifier, Attribute> modifierEntry : modifiers.entrySet()) {
				AttributeModifier modifier = modifierEntry.getKey();
				Attribute attribute = modifierEntry.getValue();

				AttributeInstance attributeInstance = player.getAttributes().getInstance(attribute);
				if (attributeInstance != null) {
					if (!attributeInstance.hasModifier(modifier)) {
						double amount = targetValue - attributeInstance.getValue();
						AttributeModifier newModifier = new AttributeModifier(modifier.getId(), modifier.name, amount, modifier.getOperation());

						attributeInstance.addTransientModifier(newModifier);
					}
					if (attributeInstance.getValue() != targetValue) {
						attributeInstance.removeModifier(modifier.getId());

						double amount = targetValue - attributeInstance.getValue();
						AttributeModifier newModifier = new AttributeModifier(modifier.getId(), modifier.name, amount, modifier.getOperation());

						attributeInstance.addTransientModifier(newModifier);
					}
				}
			}
		}

		return activeDynamicAttributeModifiers;
	}

	/**
	 * Handle the removal of attribute modifiers from accessories.
	 *
	 * @param player                           the <code>Player</code> to handle for
	 * @param activeStandardAttributeModifiers the <code>Map</code> of active standard attribute modifiers
	 * @param activeDynamicAttributeModifiers  the <code>Map</code> of active dynamic attribute modifiers
	 */
	private static void handleRemovedAttributes(Player player, Map<AttributeModifier, Attribute> activeStandardAttributeModifiers, Map<Map<AttributeModifier, Attribute>, Double> activeDynamicAttributeModifiers) {
		// Remove any attribute modifiers that are no present in the activeAttributeModifiers map
		for (Map.Entry<AttributeModifier, Attribute> entry : AccessoryItem.getGlobalAttributeModifierMap().entrySet()) {
			AttributeModifier modifier = entry.getKey();
			Attribute attribute = entry.getValue();

			AttributeInstance attributeInstance = player.getAttributes().getInstance(attribute);
			if (attributeInstance != null) {
				if (attributeInstance.hasModifier(modifier)) {
					// Check for matching UUIDs in the activeAttributeModifiers map, rather than instances
					boolean found = false;

					Map<AttributeModifier, Attribute> allActiveModifiers = new HashMap<>(activeStandardAttributeModifiers);
					for (Map.Entry<Map<AttributeModifier, Attribute>, Double> dynamicEntry : activeDynamicAttributeModifiers.entrySet()) {
						allActiveModifiers.putAll(dynamicEntry.getKey());
					}

					for (Map.Entry<AttributeModifier, Attribute> activeEntry : allActiveModifiers.entrySet()) {
						AttributeModifier activeModifier = activeEntry.getKey();
						if (activeModifier.getId().equals(modifier.getId())) {
							found = true;
							break;
						}
					}

					if (!found) {
						attributeInstance.removeModifier(modifier.getId());
					}
				}
			}
		}
	}
}