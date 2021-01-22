package com.anonymoushacker1279.immersiveweapons.util;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.item.CustomArrowItem;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistryEntry;

@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {

	@SubscribeEvent
	public static void onRegisterItems(RegistryEvent.Register<Item> event) {
		((CustomArrowItem) (DeferredRegistryHandler.COPPER_ARROW.get())).setItemReference(DeferredRegistryHandler.COPPER_ARROW);
		((CustomArrowItem) (DeferredRegistryHandler.IRON_ARROW.get())).setItemReference(DeferredRegistryHandler.IRON_ARROW);
		((CustomArrowItem) (DeferredRegistryHandler.DIAMOND_ARROW.get())).setItemReference(DeferredRegistryHandler.DIAMOND_ARROW);
		((CustomArrowItem) (DeferredRegistryHandler.GOLD_ARROW.get())).setItemReference(DeferredRegistryHandler.GOLD_ARROW);
		((CustomArrowItem) (DeferredRegistryHandler.STONE_ARROW.get())).setItemReference(DeferredRegistryHandler.STONE_ARROW);
		((CustomArrowItem) (DeferredRegistryHandler.WOOD_ARROW.get())).setItemReference(DeferredRegistryHandler.WOOD_ARROW);
		((CustomArrowItem) (DeferredRegistryHandler.NETHERITE_ARROW.get())).setItemReference(DeferredRegistryHandler.NETHERITE_ARROW);
		
		((CustomArrowItem) (DeferredRegistryHandler.COPPER_MUSKET_BALL.get())).setItemReference(DeferredRegistryHandler.COPPER_MUSKET_BALL);
		((CustomArrowItem) (DeferredRegistryHandler.WOOD_MUSKET_BALL.get())).setItemReference(DeferredRegistryHandler.WOOD_MUSKET_BALL);
		((CustomArrowItem) (DeferredRegistryHandler.STONE_MUSKET_BALL.get())).setItemReference(DeferredRegistryHandler.STONE_MUSKET_BALL);
		((CustomArrowItem) (DeferredRegistryHandler.IRON_MUSKET_BALL.get())).setItemReference(DeferredRegistryHandler.IRON_MUSKET_BALL);
		((CustomArrowItem) (DeferredRegistryHandler.GOLD_MUSKET_BALL.get())).setItemReference(DeferredRegistryHandler.GOLD_MUSKET_BALL);
		((CustomArrowItem) (DeferredRegistryHandler.DIAMOND_MUSKET_BALL.get())).setItemReference(DeferredRegistryHandler.DIAMOND_MUSKET_BALL);
		((CustomArrowItem) (DeferredRegistryHandler.NETHERITE_MUSKET_BALL.get())).setItemReference(DeferredRegistryHandler.NETHERITE_MUSKET_BALL);
	}
	
	public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final String name) {
		return setup(entry, new ResourceLocation(ImmersiveWeapons.MOD_ID, name));
	}
	
	public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final ResourceLocation registryName) {
		entry.setRegistryName(registryName);
		return entry;
	}
}
