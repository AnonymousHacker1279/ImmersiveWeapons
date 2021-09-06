package com.anonymoushacker1279.immersiveweapons.client;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
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
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.molten_sword").withStyle(ChatFormatting.DARK_RED, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.TESLA_SWORD.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.tesla_sword").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.VENTUS_SWORD.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.ventus_sword").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
		}

		// Guns
		if (stack.getItem() == DeferredRegistryHandler.FLINTLOCK_PISTOL.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.flintlock_pistol").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.BLUNDERBUSS.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.blunderbuss").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.FLARE_GUN.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.flare_gun").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}

		// Arrows
		if (stack.getItem() == DeferredRegistryHandler.COPPER_ARROW.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.copper_arrow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.IRON_ARROW.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.iron_arrow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.DIAMOND_ARROW.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.diamond_arrow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.GOLD_ARROW.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.gold_arrow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.STONE_ARROW.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.stone_arrow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.WOOD_ARROW.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.wood_arrow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.NETHERITE_ARROW.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.netherite_arrow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_ARROW.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.smoke_bomb_arrow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.smoke_bomb").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_ARROW_RED.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.smoke_bomb_arrow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.smoke_bomb").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.smoke_bomb_red").withStyle(ChatFormatting.RED, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_ARROW_GREEN.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.smoke_bomb_arrow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.smoke_bomb").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.smoke_bomb_green").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_ARROW_BLUE.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.smoke_bomb_arrow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.smoke_bomb").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.smoke_bomb_blue").withStyle(ChatFormatting.BLUE, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_ARROW_PURPLE.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.smoke_bomb_arrow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.smoke_bomb").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.smoke_bomb_purple").withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_ARROW_YELLOW.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.smoke_bomb_arrow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.smoke_bomb").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.smoke_bomb_yellow").withStyle(ChatFormatting.YELLOW, ChatFormatting.ITALIC));
		}

		// Bullets
		if (stack.getItem() == DeferredRegistryHandler.WOOD_MUSKET_BALL.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.wood_musket_ball").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.STONE_MUSKET_BALL.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.stone_musket_ball").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.GOLD_MUSKET_BALL.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.gold_musket_ball").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.COPPER_MUSKET_BALL.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.copper_musket_ball").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.IRON_MUSKET_BALL.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.iron_musket_ball").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.DIAMOND_MUSKET_BALL.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.diamond_musket_ball").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.NETHERITE_MUSKET_BALL.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.netherite_musket_ball").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}

		// Pikes
		if (stack.getItem() == DeferredRegistryHandler.WOOD_PIKE.get() || stack.getItem() == DeferredRegistryHandler.STONE_PIKE.get() || stack.getItem() == DeferredRegistryHandler.GOLD_PIKE.get() || stack.getItem() == DeferredRegistryHandler.COPPER_PIKE.get() || stack.getItem() == DeferredRegistryHandler.IRON_PIKE.get() || stack.getItem() == DeferredRegistryHandler.DIAMOND_PIKE.get() || stack.getItem() == DeferredRegistryHandler.NETHERITE_PIKE.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.pike").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}

		// Armor
		if (stack.getItem() == DeferredRegistryHandler.MOLTEN_HELMET.get() || stack.getItem() == DeferredRegistryHandler.MOLTEN_CHESTPLATE.get() || stack.getItem() == DeferredRegistryHandler.MOLTEN_LEGGINGS.get() || stack.getItem() == DeferredRegistryHandler.MOLTEN_BOOTS.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.molten_armor").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}

		// Bombs
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.smoke_bomb").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_RED.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.smoke_bomb").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.smoke_bomb_red").withStyle(ChatFormatting.RED, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_GREEN.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.smoke_bomb").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.smoke_bomb_green").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_BLUE.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.smoke_bomb").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.smoke_bomb_blue").withStyle(ChatFormatting.BLUE, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_PURPLE.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.smoke_bomb").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.smoke_bomb_purple").withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_YELLOW.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.smoke_bomb").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.smoke_bomb_yellow").withStyle(ChatFormatting.YELLOW, ChatFormatting.ITALIC));
		}

		// Molotovs
		if (stack.getItem() == DeferredRegistryHandler.MOLOTOV_COCKTAIL.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.molotov_cocktail").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}

		// Bottles
		if (stack.getItem() == DeferredRegistryHandler.BOTTLE_OF_ALCOHOL.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.bottle_of_alcohol").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.BOTTLE_OF_WINE.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.bottle_of_wine").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}

		// Traps
		if (stack.getItem() == DeferredRegistryHandler.PUNJI_STICKS_ITEM.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.punji_sticks").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.PITFALL_ITEM.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.pitfall").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.BEAR_TRAP_ITEM.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.bear_trap").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.LANDMINE_ITEM.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.landmine").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SPIKE_TRAP_ITEM.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.spike_trap").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}

		// Gauntlets
		if (stack.getItem() == DeferredRegistryHandler.WOOD_GAUNTLET.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.gauntlet").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.wood_gauntlet").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.STONE_GAUNTLET.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.gauntlet").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.stone_gauntlet").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.GOLD_GAUNTLET.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.gauntlet").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.gold_gauntlet").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.COPPER_GAUNTLET.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.gauntlet").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.copper_gauntlet").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.IRON_GAUNTLET.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.gauntlet").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.iron_gauntlet").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.DIAMOND_GAUNTLET.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.gauntlet").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.diamond_gauntlet").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.NETHERITE_GAUNTLET.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.gauntlet").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.netherite_gauntlet").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}

		// Miscellaneous
		if (stack.getItem() == DeferredRegistryHandler.BARREL_TAP_ITEM.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.barrel_tap").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.TESLA_HOE.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.tesla_hoe").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SPOTLIGHT_ITEM.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.spotlight").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.PANIC_ALARM_ITEM.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.panic_alarm").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.MORPHINE.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.morphine").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.USED_SYRINGE.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.used_syringe").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.VENTUS_STAFF.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.ventus_staff").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.HANS_SPAWN_EGG.get()) {
			event.getToolTip().add(new TranslatableComponent("immersiveweapons.tooltip.hans_spawn_egg").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
		}
	}
}