package com.anonymoushacker1279.immersiveweapons.init;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class TooltipHandler {

	/**
	 * Handles adding tooltips to items.
	 * @param event the <code>ItemTooltipEvent</code> instance
	 */
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void addItemTooltip(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();

		// Swords
		if (stack.getItem() == DeferredRegistryHandler.MOLTEN_SWORD.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.molten_sword").withStyle(TextFormatting.DARK_RED, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.TESLA_SWORD.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.tesla_sword").withStyle(TextFormatting.AQUA, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.VENTUS_SWORD.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.ventus_sword").withStyle(TextFormatting.GRAY, TextFormatting.ITALIC));
		}

		// Guns
		if (stack.getItem() == DeferredRegistryHandler.FLINTLOCK_PISTOL.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.flintlock_pistol").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.BLUNDERBUSS.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.blunderbuss").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.FLARE_GUN.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.flare_gun").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}

		// Arrows
		if (stack.getItem() == DeferredRegistryHandler.COPPER_ARROW.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.copper_arrow").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.IRON_ARROW.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.iron_arrow").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.DIAMOND_ARROW.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.diamond_arrow").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.GOLD_ARROW.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.gold_arrow").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.STONE_ARROW.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.stone_arrow").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.WOOD_ARROW.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.wood_arrow").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.NETHERITE_ARROW.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.netherite_arrow").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_ARROW.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.smoke_bomb_arrow").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.smoke_bomb").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_ARROW_RED.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.smoke_bomb_arrow").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.smoke_bomb").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.smoke_bomb_red").withStyle(TextFormatting.RED, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_ARROW_GREEN.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.smoke_bomb_arrow").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.smoke_bomb").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.smoke_bomb_green").withStyle(TextFormatting.GREEN, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_ARROW_BLUE.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.smoke_bomb_arrow").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.smoke_bomb").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.smoke_bomb_blue").withStyle(TextFormatting.BLUE, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_ARROW_PURPLE.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.smoke_bomb_arrow").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.smoke_bomb").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.smoke_bomb_purple").withStyle(TextFormatting.LIGHT_PURPLE, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_ARROW_YELLOW.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.smoke_bomb_arrow").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.smoke_bomb").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.smoke_bomb_yellow").withStyle(TextFormatting.YELLOW, TextFormatting.ITALIC));
		}

		// Bullets
		if (stack.getItem() == DeferredRegistryHandler.WOOD_MUSKET_BALL.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.wood_musket_ball").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.STONE_MUSKET_BALL.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.stone_musket_ball").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.GOLD_MUSKET_BALL.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.gold_musket_ball").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.COPPER_MUSKET_BALL.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.copper_musket_ball").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.IRON_MUSKET_BALL.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.iron_musket_ball").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.DIAMOND_MUSKET_BALL.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.diamond_musket_ball").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.NETHERITE_MUSKET_BALL.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.netherite_musket_ball").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}

		// Pikes
		if (stack.getItem() == DeferredRegistryHandler.WOOD_PIKE.get() || stack.getItem() == DeferredRegistryHandler.STONE_PIKE.get() || stack.getItem() == DeferredRegistryHandler.GOLD_PIKE.get() || stack.getItem() == DeferredRegistryHandler.COPPER_PIKE.get() || stack.getItem() == DeferredRegistryHandler.IRON_PIKE.get() || stack.getItem() == DeferredRegistryHandler.DIAMOND_PIKE.get() || stack.getItem() == DeferredRegistryHandler.NETHERITE_PIKE.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.pike").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}

		// Armor
		if (stack.getItem() == DeferredRegistryHandler.MOLTEN_HELMET.get() || stack.getItem() == DeferredRegistryHandler.MOLTEN_CHESTPLATE.get() || stack.getItem() == DeferredRegistryHandler.MOLTEN_LEGGINGS.get() || stack.getItem() == DeferredRegistryHandler.MOLTEN_BOOTS.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.molten_armor").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}

		// Bombs
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.smoke_bomb").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_RED.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.smoke_bomb").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.smoke_bomb_red").withStyle(TextFormatting.RED, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_GREEN.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.smoke_bomb").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.smoke_bomb_green").withStyle(TextFormatting.GREEN, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_BLUE.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.smoke_bomb").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.smoke_bomb_blue").withStyle(TextFormatting.BLUE, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_PURPLE.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.smoke_bomb").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.smoke_bomb_purple").withStyle(TextFormatting.LIGHT_PURPLE, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_YELLOW.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.smoke_bomb").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.smoke_bomb_yellow").withStyle(TextFormatting.YELLOW, TextFormatting.ITALIC));
		}

		// Molotovs
		if (stack.getItem() == DeferredRegistryHandler.MOLOTOV_COCKTAIL.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.molotov_cocktail").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}

		// Bottles
		if (stack.getItem() == DeferredRegistryHandler.BOTTLE_OF_ALCOHOL.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.bottle_of_alcohol").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.BOTTLE_OF_WINE.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.bottle_of_wine").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}

		// Traps
		if (stack.getItem() == DeferredRegistryHandler.PUNJI_STICKS_ITEM.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.punji_sticks").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.PITFALL_ITEM.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.pitfall").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.BEAR_TRAP_ITEM.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.bear_trap").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.LANDMINE_ITEM.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.landmine").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SPIKE_TRAP_ITEM.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.spike_trap").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}

		// Miscellaneous
		if (stack.getItem() == DeferredRegistryHandler.BARREL_TAP_ITEM.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.barrel_tap").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.TESLA_HOE.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.tesla_hoe").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SPOTLIGHT_ITEM.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.spotlight").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.PANIC_ALARM_ITEM.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.panic_alarm").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.MORPHINE.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.morphine").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.USED_SYRINGE.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.used_syringe").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.VENTUS_STAFF.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.ventus_staff").withStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.HANS_SPAWN_EGG.get()) {
			event.getToolTip().add(new TranslationTextComponent("immersiveweapons.tooltip.hans_spawn_egg").withStyle(TextFormatting.DARK_PURPLE, TextFormatting.ITALIC));
		}
	}
}