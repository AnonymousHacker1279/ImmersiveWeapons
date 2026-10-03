package tech.anonymoushacker1279.iwcompatbridge.plugin.curios;

import net.minecraft.util.TriState;
import tech.anonymoushacker1279.immersiveweapons.api.events.AccessoryEvent;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.Accessory;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.AccessoryLoader;
import tech.anonymoushacker1279.iwcompatbridge.config.IWCBConfigs;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.event.CurioCanEquipEvent;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.List;
import java.util.Optional;

public class CuriosEventHandler {

	/// Prevent multiple IW accessories of the same item from being equipped at once, if the config option is enabled.
	///
	/// @param event the `CurioCanEquipEvent` instance
	public static void curioEquipEvent(CurioCanEquipEvent event) {
		if (!IWCBConfigs.SERVER.accessoryStacking.getAsBoolean()) {
			CuriosApi.getCuriosInventory(event.getEntity())
					.ifPresent(iCuriosItemHandler -> iCuriosItemHandler.findCurios(event.getSlotContext().identifier())
							.forEach(slotResult -> {
								if (slotResult.stack().getItem() == event.getStack().getItem() && AccessoryLoader.ACCESSORIES.get(event.getStack().getItem()) != null) {
									event.setEquipResult(TriState.FALSE);
								}
							}));
		}
	}

	public static void collectEffects(AccessoryEvent.CollectEffects event) {
		double value = 0;

		Optional<ICuriosItemHandler> optional = CuriosApi.getCuriosInventory(event.getPlayer());
		if (optional.isPresent()) {
			ICuriosItemHandler itemHandler = optional.get();
			List<SlotResult> curios = itemHandler.findCurios(itemStack -> AccessoryLoader.ACCESSORIES.get(itemStack.getItem()) != null);

			for (SlotResult slotResult : curios) {
				if (AccessoryLoader.ACCESSORIES.get(slotResult.stack().getItem()) instanceof Accessory accessory) {
					if (!event.getPlayer().getCooldowns().isOnCooldown(slotResult.stack())) {
						value += accessory.getEffectValue(event.getType(), event.getPlayer());
					}
				}
			}
		}

		event.setEffect(value);
	}

	public static void collectStandardAttributes(AccessoryEvent.CollectStandardAttributes event) {
		Optional<ICuriosItemHandler> optional = CuriosApi.getCuriosInventory(event.getPlayer());
		if (optional.isPresent()) {
			ICuriosItemHandler itemHandler = optional.get();
			List<SlotResult> curios = itemHandler.findCurios(itemStack -> AccessoryLoader.ACCESSORIES.get(itemStack.getItem()) != null);

			for (SlotResult slotResult : curios) {
				if (AccessoryLoader.ACCESSORIES.get(slotResult.stack().getItem()) instanceof Accessory accessory) {
					event.addAttributes(accessory.attributeModifiers());
				}
			}
		}
	}

	public static void collectDynamicAttributes(AccessoryEvent.CollectDynamicAttributes event) {
		Optional<ICuriosItemHandler> optional = CuriosApi.getCuriosInventory(event.getPlayer());
		if (optional.isPresent()) {
			ICuriosItemHandler itemHandler = optional.get();
			List<SlotResult> curios = itemHandler.findCurios(itemStack -> AccessoryLoader.ACCESSORIES.get(itemStack.getItem()) != null);

			for (SlotResult slotResult : curios) {
				if (AccessoryLoader.ACCESSORIES.get(slotResult.stack().getItem()) instanceof Accessory accessory) {
					event.addAttributes(accessory.dynamicAttributeModifiers());
				}
			}
		}
	}

	public static void collectMobEffects(AccessoryEvent.CollectMobEffects event) {
		Optional<ICuriosItemHandler> optional = CuriosApi.getCuriosInventory(event.getPlayer());
		if (optional.isPresent()) {
			ICuriosItemHandler itemHandler = optional.get();
			List<SlotResult> curios = itemHandler.findCurios(itemStack -> AccessoryLoader.ACCESSORIES.get(itemStack.getItem()) != null);

			for (SlotResult slotResult : curios) {
				if (AccessoryLoader.ACCESSORIES.get(slotResult.stack().getItem()) instanceof Accessory accessory) {
					event.addEffects(accessory.mobEffectInstances());
				}
			}
		}
	}

	public static void accessoryActive(AccessoryEvent.AccessoryActive event) {
		Optional<ICuriosItemHandler> optional = CuriosApi.getCuriosInventory(event.getPlayer());
		if (optional.isPresent()) {
			ICuriosItemHandler itemHandler = optional.get();
			List<SlotResult> curios = itemHandler.findCurios(itemStack -> itemStack.getItem() == event.getStack().getItem());

			if (!event.getPlayer().getCooldowns().isOnCooldown(event.getStack())) {
				event.setActive(!curios.isEmpty());
			}
		}
	}
}