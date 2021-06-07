package com.anonymoushacker1279.immersiveweapons.util;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = Bus.FORGE)
public class ForgeEventSubscriber {

	@SubscribeEvent
	public static void renderBlockOverlayEvent(final RenderBlockOverlayEvent event) {

		// Remove fire overlay from players wearing a full set of molten armor
		if (event.getPlayer().getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == DeferredRegistryHandler.MOLTEN_HELMET.get() &&
				event.getPlayer().getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() == DeferredRegistryHandler.MOLTEN_CHESTPLATE.get() &&
				event.getPlayer().getItemStackFromSlot(EquipmentSlotType.LEGS).getItem() == DeferredRegistryHandler.MOLTEN_LEGGINGS.get() &&
				event.getPlayer().getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == DeferredRegistryHandler.MOLTEN_BOOTS.get()) {
			if (event.getPlayer().isInLava()) {
				if (event.getBlockForOverlay() == Blocks.FIRE.getDefaultState()) {
					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent
	public static void entityViewRenderEvent(final FogDensity event) {

		// Reduce lava fog from players wearing a full set of molten armor
		PlayerEntity player = Minecraft.getInstance().player;
		if (player != null && player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == DeferredRegistryHandler.MOLTEN_HELMET.get() &&
				player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() == DeferredRegistryHandler.MOLTEN_CHESTPLATE.get() &&
				player.getItemStackFromSlot(EquipmentSlotType.LEGS).getItem() == DeferredRegistryHandler.MOLTEN_LEGGINGS.get() &&
				player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == DeferredRegistryHandler.MOLTEN_BOOTS.get()) {
			if (player.isInLava()) {
				event.setDensity(0.05F);
				event.setCanceled(true);
			}
		}
	}
}