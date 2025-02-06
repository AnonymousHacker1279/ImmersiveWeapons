package tech.anonymoushacker1279.immersiveweapons.event.game_effects;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.api.PluginHandler;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.Accessory;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.AccessoryEffectType;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.scaling.*;
import tech.anonymoushacker1279.immersiveweapons.util.IWCBBridge;

import java.util.ArrayList;
import java.util.List;

public class AccessoryManager {

	/**
	 * Collect the value of the given effect from all accessories in the player's inventory.
	 * <p>
	 * This will check to see if IWCB is loaded, and if so, defer to it for collecting effects as it will utilize Curios.
	 *
	 * @param type   the <code>EffectType</code> to collect
	 * @param player the <code>Player</code> to collect from
	 * @return the value of the effect
	 */
	public static double collectEffects(AccessoryEffectType type, Player player) {
		double effectValue = 0;
		if (ImmersiveWeapons.IWCB_LOADED && PluginHandler.isPluginActive("iwcompatbridge:curios_plugin")) {
			effectValue = IWCBBridge.collectEffects(type, player);
		} else {
			for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
				ItemStack stack = player.getInventory().getItem(i);
				Accessory accessory = stack.getItemHolder().getData(Accessory.ACCESSORY);
				if (accessory != null) {
					if (accessory.isActive(player, stack, accessory.slot())) {
						AccessoryEffectScalingType scalingType = accessory.effectScalingTypes().get(type.name());
						if (scalingType != null) {
							effectValue += scalingType.getEffectValue(accessory, type, player);
						}
					}
				}
			}
		}

		return type.clamp() ? Mth.clamp(effectValue, 0, 1) : effectValue;
	}

	/**
	 * Collect the attribute modifiers from all active accessories in the player's inventory.
	 * <p>
	 * This will check to see if IWCB is loaded, and if so, defer to it for collecting attributes as it will utilize Curios.
	 *
	 * @param player the <code>Player</code> to collect from
	 * @return a <code>Map</code> of attribute modifiers
	 */
	public static List<AttributeOperation> collectStandardAttributes(Player player) {
		List<AttributeOperation> attributes = new ArrayList<>(5);
		if (ImmersiveWeapons.IWCB_LOADED && PluginHandler.isPluginActive("iwcompatbridge:curios_plugin")) {
			attributes = IWCBBridge.collectStandardAttributes(player);
		} else {
			for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
				ItemStack stack = player.getInventory().getItem(i);
				Accessory accessory = stack.getItemHolder().getData(Accessory.ACCESSORY);
				if (accessory != null) {
					if (accessory.isActive(player, stack, accessory.slot())) {
						attributes.addAll(accessory.attributeModifiers());
					}
				}
			}
		}

		return attributes;
	}

	/**
	 * Collect the dynamic attribute modifiers from all active accessories in the player's inventory.
	 * <p>
	 * This will check to see if IWCB is loaded, and if so, defer to it for collecting attributes as it will utilize Curios.
	 *
	 * @param player the <code>Player</code> to collect from
	 * @return a <code>Map</code> of dynamic attribute modifiers with their target values
	 */
	public static List<DynamicAttributeOperationInstance> collectDynamicAttributes(Player player) {
		List<DynamicAttributeOperationInstance> dynamicAttributes = new ArrayList<>(5);
		if (ImmersiveWeapons.IWCB_LOADED && PluginHandler.isPluginActive("iwcompatbridge:curios_plugin")) {
			dynamicAttributes = IWCBBridge.collectDynamicAttributes(player);
		} else {
			for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
				ItemStack stack = player.getInventory().getItem(i);
				Accessory accessory = stack.getItemHolder().getData(Accessory.ACCESSORY);
				if (accessory != null) {
					if (accessory.isActive(player, stack, accessory.slot())) {
						dynamicAttributes.addAll(accessory.dynamicAttributeModifiers());
					}
				}
			}
		}

		return dynamicAttributes;
	}

	/**
	 * Collect the mob effect instances from all active accessories in the player's inventory.
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
				Accessory accessory = stack.getItemHolder().getData(Accessory.ACCESSORY);
				if (accessory != null) {
					if (accessory.isActive(player, stack, accessory.slot())) {
						effectList.addAll(accessory.mobEffectInstances());
					}
				}
			}
		}

		return effectList;
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
		List<AttributeOperation> activeStandardAttributeModifiers = handleStandardAttributeModifiers(player);
		List<DynamicAttributeOperationInstance> activeDynamicAttributeModifiers = handleDynamicAttributeModifiers(player);
		handleRemovedAttributes(player, activeStandardAttributeModifiers, activeDynamicAttributeModifiers);
	}

	/**
	 * Handle the adding of standard (non-dynamic) attribute modifiers from accessories.
	 *
	 * @param player the <code>Player</code> to handle for
	 * @return a <code>Map</code> of active standard attribute modifiers
	 */
	private static List<AttributeOperation> handleStandardAttributeModifiers(Player player) {
		List<AttributeOperation> activeStandardAttributeModifiers = AccessoryManager.collectStandardAttributes(player);
		for (AttributeOperation entry : activeStandardAttributeModifiers) {
			AttributeModifier modifier = entry.modifier();
			Attribute attribute = entry.attribute().value();

			AttributeInstance attributeInstance = player.getAttributes().getInstance(BuiltInRegistries.ATTRIBUTE.wrapAsHolder(attribute));
			if (attributeInstance != null) {
				if (!attributeInstance.hasModifier(modifier.id())) {
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
	private static List<DynamicAttributeOperationInstance> handleDynamicAttributeModifiers(Player player) {
		List<DynamicAttributeOperationInstance> activeDynamicAttributeModifiers = AccessoryManager.collectDynamicAttributes(player);
		for (DynamicAttributeOperationInstance entry : activeDynamicAttributeModifiers) {
			AttributeOperation attributeOperation = entry.attributeOperation();
			double targetValue = entry.targetValue();

			AttributeModifier modifier = attributeOperation.modifier();
			Attribute attribute = attributeOperation.attribute().value();

			AttributeInstance attributeInstance = player.getAttributes().getInstance(BuiltInRegistries.ATTRIBUTE.wrapAsHolder(attribute));
			if (attributeInstance != null) {
				if (!attributeInstance.hasModifier(modifier.id())) {
					double amount = targetValue - attributeInstance.getValue();
					AttributeModifier newModifier = new AttributeModifier(modifier.id(), amount, modifier.operation());

					attributeInstance.addTransientModifier(newModifier);
				}
				if (attributeInstance.getValue() != targetValue) {
					attributeInstance.removeModifier(modifier.id());

					double amount = targetValue - attributeInstance.getValue();
					AttributeModifier newModifier = new AttributeModifier(modifier.id(), amount, modifier.operation());

					attributeInstance.addTransientModifier(newModifier);
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
	private static void handleRemovedAttributes(Player player, List<AttributeOperation> activeStandardAttributeModifiers, List<DynamicAttributeOperationInstance> activeDynamicAttributeModifiers) {
		// Remove any attribute modifiers that are no present in the activeAttributeModifiers map
		for (AttributeOperation entry : Accessory.getGlobalAttributeModifiers()) {
			AttributeModifier modifier = entry.modifier();
			Attribute attribute = entry.attribute().value();

			AttributeInstance attributeInstance = player.getAttributes().getInstance(BuiltInRegistries.ATTRIBUTE.wrapAsHolder(attribute));
			if (attributeInstance != null) {
				if (attributeInstance.hasModifier(modifier.id())) {
					// Check for matching UUIDs in the activeAttributeModifiers map, rather than instances
					boolean found = false;

					for (AttributeOperation activeEntry : activeStandardAttributeModifiers) {
						if (activeEntry.modifier().id().equals(modifier.id())) {
							found = true;
							break;
						}
					}

					if (!found) {
						for (DynamicAttributeOperationInstance activeEntry : activeDynamicAttributeModifiers) {
							if (activeEntry.attributeOperation().modifier().id().equals(modifier.id())) {
								found = true;
								break;
							}
						}
					}

					if (!found) {
						attributeInstance.removeModifier(modifier.id());
					}
				}
			}
		}
	}
}