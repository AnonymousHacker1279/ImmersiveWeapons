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
	public static void addItemTooltip(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();

		// Swords
		if (stack.getItem() == DeferredRegistryHandler.MOLTEN_SWORD.get()) {
			event.getToolTip().add(new TranslationTextComponent("Kill it with fire").mergeStyle(TextFormatting.DARK_RED, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.TESLA_SWORD.get()) {
			event.getToolTip().add(new TranslationTextComponent("Provides quite a shocking experience").mergeStyle(TextFormatting.AQUA, TextFormatting.ITALIC));
		}

		// Guns
		if (stack.getItem() == DeferredRegistryHandler.FLINTLOCK_PISTOL.get()) {
			event.getToolTip().add(new TranslationTextComponent("Excels in long-range combat").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("A bit primitive in design.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.BLUNDERBUSS.get()) {
			event.getToolTip().add(new TranslationTextComponent("Good for hordes a short distance away").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("A primitive type of shotgun.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
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
			event.getToolTip().add(new TranslationTextComponent("Kind of dull and weighs at least a pound.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
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
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_ARROW.get()) {
			event.getToolTip().add(new TranslationTextComponent("Obscure your enemy's vision").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("Creates a thick cloud of smoke upon impact.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_ARROW_RED.get()) {
			event.getToolTip().add(new TranslationTextComponent("Obscure your enemy's vision").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("Creates a thick cloud of smoke upon impact.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("This one releases red smoke.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_ARROW_GREEN.get()) {
			event.getToolTip().add(new TranslationTextComponent("Obscure your enemy's vision").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("Creates a thick cloud of smoke upon impact.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("This one releases green smoke.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_ARROW_BLUE.get()) {
			event.getToolTip().add(new TranslationTextComponent("Obscure your enemy's vision").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("Creates a thick cloud of smoke upon impact.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("This one releases blue smoke.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_ARROW_PURPLE.get()) {
			event.getToolTip().add(new TranslationTextComponent("Obscure your enemy's vision").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("Creates a thick cloud of smoke upon impact.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("This one releases purple smoke.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_ARROW_YELLOW.get()) {
			event.getToolTip().add(new TranslationTextComponent("Obscure your enemy's vision").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("Creates a thick cloud of smoke upon impact.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("This one releases yellow smoke.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}

		// Bullets
		if (stack.getItem() == DeferredRegistryHandler.WOOD_MUSKET_BALL.get()) {
			event.getToolTip().add(new TranslationTextComponent("Very inaccurate, and likely to cause a misfire.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("Why would anyone make wooden musket balls anyway?").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.STONE_MUSKET_BALL.get()) {
			event.getToolTip().add(new TranslationTextComponent("A very heavy musket ball, and not too accurate.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("Less likely to cause a misfire than wooden ones.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.GOLD_MUSKET_BALL.get()) {
			event.getToolTip().add(new TranslationTextComponent("Fancier than stone musket balls, but not much better.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("At least it won't misfire.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("Look at me! I have money to throw away!").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.COPPER_MUSKET_BALL.get()) {
			event.getToolTip().add(new TranslationTextComponent("A well constructed musket ball, made from copper.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.IRON_MUSKET_BALL.get()) {
			event.getToolTip().add(new TranslationTextComponent("A hard hitting musket ball, made from iron.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.DIAMOND_MUSKET_BALL.get()) {
			event.getToolTip().add(new TranslationTextComponent("A very sharp and powerful musket ball.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("You're practically throwing away diamonds.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("Pierces through a single target.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.NETHERITE_MUSKET_BALL.get()) {
			event.getToolTip().add(new TranslationTextComponent("An extremely powerful and skillfully crafted musket ball.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("For when you need to kill a chicken from the other side of a mountain.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("Pierces through two targets.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}

		// Pikes
		if (stack.getItem() == DeferredRegistryHandler.WOOD_PIKE.get() || stack.getItem() == DeferredRegistryHandler.STONE_PIKE.get() || stack.getItem() == DeferredRegistryHandler.GOLD_PIKE.get() || stack.getItem() == DeferredRegistryHandler.COPPER_PIKE.get() || stack.getItem() == DeferredRegistryHandler.IRON_PIKE.get() || stack.getItem() == DeferredRegistryHandler.DIAMOND_PIKE.get() || stack.getItem() == DeferredRegistryHandler.NETHERITE_PIKE.get()) {
			event.getToolTip().add(new TranslationTextComponent("Stab them from way over there").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}

		// Armor
		if (stack.getItem() == DeferredRegistryHandler.MOLTEN_HELMET.get() || stack.getItem() == DeferredRegistryHandler.MOLTEN_CHESTPLATE.get() || stack.getItem() == DeferredRegistryHandler.MOLTEN_LEGGINGS.get() || stack.getItem() == DeferredRegistryHandler.MOLTEN_BOOTS.get()) {
			event.getToolTip().add(new TranslationTextComponent("Warm and toasy inside").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}

		// Bombs
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB.get()) {
			event.getToolTip().add(new TranslationTextComponent("Creates a thick cloud of smoke upon impact").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("Good for quickly escaping a situation.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_RED.get()) {
			event.getToolTip().add(new TranslationTextComponent("Creates a thick cloud of smoke upon impact").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("Good for quickly escaping a situation.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("This one releases red smoke.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_GREEN.get()) {
			event.getToolTip().add(new TranslationTextComponent("Creates a thick cloud of smoke upon impact").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("Good for quickly escaping a situation.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("This one releases green smoke.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_BLUE.get()) {
			event.getToolTip().add(new TranslationTextComponent("Creates a thick cloud of smoke upon impact").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("Good for quickly escaping a situation.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("This one releases blue smoke.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_PURPLE.get()) {
			event.getToolTip().add(new TranslationTextComponent("Creates a thick cloud of smoke upon impact").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("Good for quickly escaping a situation.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("This one releases purple smoke.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SMOKE_BOMB_YELLOW.get()) {
			event.getToolTip().add(new TranslationTextComponent("Creates a thick cloud of smoke upon impact").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("Good for quickly escaping a situation.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("This one releases yellow smoke.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}

		// Molotovs
		if (stack.getItem() == DeferredRegistryHandler.MOLOTOV_COCKTAIL.get()) {
			event.getToolTip().add(new TranslationTextComponent("Creates a ring of fire upon impact").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("Best used when thrown on flat ground.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}

		// Bottles
		if (stack.getItem() == DeferredRegistryHandler.BOTTLE_OF_ALCOHOL.get()) {
			event.getToolTip().add(new TranslationTextComponent("You could drink this, but you'll be nauseous").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("Highly flammable!").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.BOTTLE_OF_WINE.get()) {
			event.getToolTip().add(new TranslationTextComponent("A relaxing combination of sweet berries").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}

		// Traps
		if (stack.getItem() == DeferredRegistryHandler.PUNJI_STICKS_ITEM.get()) {
			event.getToolTip().add(new TranslationTextComponent("Commit war crimes using Vietnam war tactics").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.PITFALL_ITEM.get()) {
			event.getToolTip().add(new TranslationTextComponent("Hide your traps with pitfalls").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("Pairs well with punji sticks.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.BEAR_TRAP_ITEM.get()) {
			event.getToolTip().add(new TranslationTextComponent("You do not have permission to move").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.LANDMINE_ITEM.get()) {
			event.getToolTip().add(new TranslationTextComponent("They'll have to bury what's left of").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("you into a soup can").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.SPIKE_TRAP_ITEM.get()) {
			event.getToolTip().add(new TranslationTextComponent("Toggleable via redstone").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}

		// Miscellaneous
		if (stack.getItem() == DeferredRegistryHandler.BARREL_TAP_ITEM.get()) {
			event.getToolTip().add(new TranslationTextComponent("Use this to convert some plants into a fluid form").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("Connect this to a barrel to collect it.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
		if (stack.getItem() == DeferredRegistryHandler.TESLA_HOE.get()) {
			event.getToolTip().add(new TranslationTextComponent("Good thing you aren't in charge of the US Treasury").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
			event.getToolTip().add(new TranslationTextComponent("Because if you were we'd be broke.").mergeStyle(TextFormatting.DARK_GRAY, TextFormatting.ITALIC));
		}
	}
}