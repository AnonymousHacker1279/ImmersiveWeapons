package com.anonymoushacker1279.immersiveweapons.util;

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
		if (stack.getItem() == DeferredRegistryHandler.MOLTEN_SWORD.get()) {
			event.getToolTip().add(new TranslationTextComponent("Kill it with fire").mergeStyle(TextFormatting.DARK_RED, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.ELECTRIC_BLADE.get()) {
			event.getToolTip().add(new TranslationTextComponent("Provides quite a shocking experience").mergeStyle(TextFormatting.AQUA, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.COPPER_ARROW.get()) {
			event.getToolTip().add(new TranslationTextComponent("15% more powerful than normal arrows").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.WOOD_PIKE.get() || stack.getItem() == DeferredRegistryHandler.STONE_PIKE.get() || stack.getItem() == DeferredRegistryHandler.GOLD_PIKE.get() || stack.getItem() == DeferredRegistryHandler.COPPER_PIKE.get() || stack.getItem() == DeferredRegistryHandler.IRON_PIKE.get() || stack.getItem() == DeferredRegistryHandler.DIAMOND_PIKE.get() || stack.getItem() == DeferredRegistryHandler.NETHERITE_PIKE.get()) {
			event.getToolTip().add(new TranslationTextComponent("Stab them from way over there").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
	}
}
