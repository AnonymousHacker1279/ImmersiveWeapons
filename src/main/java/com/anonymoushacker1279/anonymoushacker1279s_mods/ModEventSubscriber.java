package com.anonymoushacker1279.anonymoushacker1279s_mods;

import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistryEntry;

import com.anonymoushacker1279.anonymoushacker1279s_mods.init.ModItemGroups;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@EventBusSubscriber(modid = AnonymousHacker1279s_Mods.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class ModEventSubscriber {
	
	// Item import phase
	@SubscribeEvent
	public static void onRegisterItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(
				setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP).defaultMaxDamage(0)), "molten_sword")
			);
	}
	// Block import phase
	@SubscribeEvent
	public static void onRegisterBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(
				setup(new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 3.0F)), "molten_ore")
			);
	}
	
	public static <T extends IForgeRegistryEntry<T>> Item setup(final Item item, final String name) {
		return setup(item, new ResourceLocation(AnonymousHacker1279s_Mods.MODID, name));
	}

	public static <T extends IForgeRegistryEntry<T>> Block setup(final Block block, final String name) {
		return setup(block, new ResourceLocation(AnonymousHacker1279s_Mods.MODID, name));
	}
	
	public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final ResourceLocation registryName) {
		entry.setRegistryName(registryName);
		return entry;
	}
}