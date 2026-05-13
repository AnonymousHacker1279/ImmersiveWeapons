package tech.anonymoushacker1279.immersiveweapons.event.game_effects;

import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.api.PluginHandler;
import tech.anonymoushacker1279.immersiveweapons.api.events.AccessoryEvent;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.Accessory;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.AccessoryLoader;

@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID)
public class AccessoryEvents {

	@SubscribeEvent
	public static void collectEffects(AccessoryEvent.CollectEffects event) {
		if (!PluginHandler.isPluginActive("iwcompatbridge:curios_plugin")) {
			double value = 0.0d;
			for (int i = 0; i < event.getPlayer().getInventory().getContainerSize(); i++) {
				ItemStack stack = event.getPlayer().getInventory().getItem(i);
				Accessory accessory = AccessoryLoader.ACCESSORIES.get(stack.getItem());
				if (accessory != null) {
					if (accessory.isActive(event.getPlayer(), stack, accessory.slot())) {
						value += accessory.getEffectValue(event.getType(), event.getPlayer());
					}
				}
			}

			event.setEffect(value);
		}
	}

	@SubscribeEvent
	public static void collectStandardAttributes(AccessoryEvent.CollectStandardAttributes event) {
		if (!PluginHandler.isPluginActive("iwcompatbridge:curios_plugin")) {
			for (int i = 0; i < event.getPlayer().getInventory().getContainerSize(); i++) {
				ItemStack stack = event.getPlayer().getInventory().getItem(i);
				Accessory accessory = AccessoryLoader.ACCESSORIES.get(stack.getItem());
				if (accessory != null) {
					if (accessory.isActive(event.getPlayer(), stack, accessory.slot())) {
						event.addAttributes(accessory.attributeModifiers());
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void collectDynamicAttributes(AccessoryEvent.CollectDynamicAttributes event) {
		if (!PluginHandler.isPluginActive("iwcompatbridge:curios_plugin")) {
			for (int i = 0; i < event.getPlayer().getInventory().getContainerSize(); i++) {
				ItemStack stack = event.getPlayer().getInventory().getItem(i);
				Accessory accessory = AccessoryLoader.ACCESSORIES.get(stack.getItem());
				if (accessory != null) {
					if (accessory.isActive(event.getPlayer(), stack, accessory.slot())) {
						event.addAttributes(accessory.dynamicAttributeModifiers());
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void collectMobEffects(AccessoryEvent.CollectMobEffects event) {
		if (!PluginHandler.isPluginActive("iwcompatbridge:curios_plugin")) {
			for (int i = 0; i < event.getPlayer().getInventory().getContainerSize(); i++) {
				ItemStack stack = event.getPlayer().getInventory().getItem(i);
				Accessory accessory = AccessoryLoader.ACCESSORIES.get(stack.getItem());
				if (accessory != null) {
					if (accessory.isActive(event.getPlayer(), stack, accessory.slot())) {
						event.addEffects(accessory.mobEffectInstances());
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void accessoryActive(AccessoryEvent.AccessoryActive event) {
		if (!PluginHandler.isPluginActive("iwcompatbridge:curios_plugin")) {
			event.setActive(
					event.getAccessory().isActive(
							event.getPlayer(),
							event.getStack(),
							event.getAccessory().slot()
					)
			);
		}
	}
}