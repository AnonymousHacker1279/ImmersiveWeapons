package com.anonymoushacker1279.immersiveweapons.event;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeEventSubscriber {

	/**
	 * Event handler for the RenderBlockOverlayEvent.
	 *
	 * @param event the <code>RenderBlockOverlayEvent</code> instance
	 */
	@SubscribeEvent
	public static void renderBlockOverlayEvent(RenderBlockOverlayEvent event) {

		// Remove fire overlay from players wearing a full set of molten armor
		if (event.getPlayer().getItemBySlot(EquipmentSlot.HEAD).getItem() == DeferredRegistryHandler.MOLTEN_HELMET.get() &&
				event.getPlayer().getItemBySlot(EquipmentSlot.CHEST).getItem() == DeferredRegistryHandler.MOLTEN_CHESTPLATE.get() &&
				event.getPlayer().getItemBySlot(EquipmentSlot.LEGS).getItem() == DeferredRegistryHandler.MOLTEN_LEGGINGS.get() &&
				event.getPlayer().getItemBySlot(EquipmentSlot.FEET).getItem() == DeferredRegistryHandler.MOLTEN_BOOTS.get()) {
			if (event.getPlayer().isInLava()) {
				if (event.getBlockState() == Blocks.FIRE.defaultBlockState()) {
					event.setCanceled(true);
				}
			}
		}
	}

	/**
	 * Event handler for the RenderFogEvent event.
	 *
	 * @param event the <code>RenderFogEvent</code> instance
	 */
	@SubscribeEvent
	public static void fogDensityEvent(RenderFogEvent event) {

		// Reduce lava fog from players wearing a full set of molten armor
		Player player = Minecraft.getInstance().player;
		if (player != null && player.getItemBySlot(EquipmentSlot.HEAD).getItem() == DeferredRegistryHandler.MOLTEN_HELMET.get() &&
				player.getItemBySlot(EquipmentSlot.CHEST).getItem() == DeferredRegistryHandler.MOLTEN_CHESTPLATE.get() &&
				player.getItemBySlot(EquipmentSlot.LEGS).getItem() == DeferredRegistryHandler.MOLTEN_LEGGINGS.get() &&
				player.getItemBySlot(EquipmentSlot.FEET).getItem() == DeferredRegistryHandler.MOLTEN_BOOTS.get()) {
			if (player.isInLava()) {
				if (Minecraft.getInstance().level != null) {
					BlockState state = Minecraft.getInstance().level.getBlockState(player.eyeBlockPosition());
					if (state.is(Blocks.LAVA)) {
						event.setNearPlaneDistance(16.0f);
						event.setFarPlaneDistance(32.0f);
						event.setCanceled(true);
					}
				}
			}
		}
	}
}