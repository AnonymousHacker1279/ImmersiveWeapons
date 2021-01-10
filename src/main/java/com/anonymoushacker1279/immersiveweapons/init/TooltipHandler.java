package com.anonymoushacker1279.immersiveweapons.init;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TooltipHandler {

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true) 
	public static void addItemTooltip(ItemTooltipEvent event){
		ItemStack stack = event.getItemStack();
		
		// Swords
		if (stack.getItem() == DeferredRegistryHandler.MOLTEN_SWORD.get()) {
			event.getToolTip().add(new TranslationTextComponent("Kill it with fire").mergeStyle(TextFormatting.DARK_RED, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.ELECTRIC_BLADE.get()) {
			event.getToolTip().add(new TranslationTextComponent("Provides quite a shocking experience").mergeStyle(TextFormatting.AQUA, TextFormatting.ITALIC));
		}
		
		// Arrows
		if (stack.getItem() == DeferredRegistryHandler.COPPER_ARROW.get()) {
			event.getToolTip().add(new TranslationTextComponent("15% more powerful than normal arrows").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.IRON_ARROW.get()) {
			event.getToolTip().add(new TranslationTextComponent("35% more powerful than normal arrows").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.DIAMOND_ARROW.get()) {
			event.getToolTip().add(new TranslationTextComponent("100% more powerful than normal arrows").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.GOLD_ARROW.get()) {
			event.getToolTip().add(new TranslationTextComponent("10% more powerful than normal arrows").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.STONE_ARROW.get()) {
			event.getToolTip().add(new TranslationTextComponent("15% less powerful than normal arrows").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("Kind of dull and weighs at least a pound").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.WOOD_ARROW.get()) {
			event.getToolTip().add(new TranslationTextComponent("35% less powerful than normal arrows").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("Highly inaccurate. At this point its just").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("blunt-force trauma.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.NETHERITE_ARROW.get()) {
			event.getToolTip().add(new TranslationTextComponent("375% more powerful than normal arrows").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("Extremely sharp, powerful, and accurate.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("How can you even afford to shoot these?").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		
		// Pikes
		if (stack.getItem() == DeferredRegistryHandler.WOOD_PIKE.get() || stack.getItem() == DeferredRegistryHandler.STONE_PIKE.get() || stack.getItem() == DeferredRegistryHandler.GOLD_PIKE.get() || stack.getItem() == DeferredRegistryHandler.COPPER_PIKE.get() || stack.getItem() == DeferredRegistryHandler.IRON_PIKE.get() || stack.getItem() == DeferredRegistryHandler.DIAMOND_PIKE.get() || stack.getItem() == DeferredRegistryHandler.NETHERITE_PIKE.get()) {
			event.getToolTip().add(new TranslationTextComponent("Stab them from way over there").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		
		// Armor
		if (stack.getItem() == DeferredRegistryHandler.MOLTEN_HELMET.get() || stack.getItem() == DeferredRegistryHandler.MOLTEN_CHESTPLATE.get() || stack.getItem() == DeferredRegistryHandler.MOLTEN_LEGGINGS.get() || stack.getItem() == DeferredRegistryHandler.MOLTEN_BOOTS.get()) {
			event.getToolTip().add(new TranslationTextComponent("Warm and toasy inside").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
	}
}
