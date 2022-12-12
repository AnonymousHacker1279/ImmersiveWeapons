package tech.anonymoushacker1279.immersiveweapons.client;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.init.BlockItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

@Mod.EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class TooltipHandler {

	/**
	 * Handles adding tooltips to items.
	 *
	 * @param event the <code>ItemTooltipEvent</code> instance
	 */
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void addItemTooltip(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();

		// Swords
		if (stack.getItem() == ItemRegistry.MOLTEN_SWORD.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.molten_sword").withStyle(ChatFormatting.DARK_RED, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.TESLA_SWORD.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.tesla_sword").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.VENTUS_SWORD.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.ventus_sword").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
		}

		// Guns
		if (stack.getItem() == ItemRegistry.FLINTLOCK_PISTOL.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.flintlock_pistol").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.BLUNDERBUSS.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.blunderbuss").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.FLARE_GUN.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.flare_gun").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.MUSKET.get() || stack.getItem() == ItemRegistry.MUSKET_SCOPE.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.musket").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}

		// Arrows
		if (stack.getItem() == ItemRegistry.COPPER_ARROW.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.copper_arrow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.IRON_ARROW.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.iron_arrow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.COBALT_ARROW.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.cobalt_arrow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.DIAMOND_ARROW.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.diamond_arrow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.GOLDEN_ARROW.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.golden_arrow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.STONE_ARROW.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.stone_arrow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.WOODEN_ARROW.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.wooden_arrow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.NETHERITE_ARROW.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.netherite_arrow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.SMOKE_GRENADE_ARROW.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.smoke_grenade_arrow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.smoke_grenade").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.SMOKE_GRENADE_ARROW_RED.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.smoke_grenade_arrow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.smoke_grenade").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.smoke_grenade_red").withStyle(ChatFormatting.RED, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.SMOKE_GRENADE_ARROW_GREEN.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.smoke_grenade_arrow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.smoke_grenade").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.smoke_grenade_green").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.SMOKE_GRENADE_ARROW_BLUE.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.smoke_grenade_arrow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.smoke_grenade").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.smoke_grenade_blue").withStyle(ChatFormatting.BLUE, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.SMOKE_GRENADE_ARROW_PURPLE.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.smoke_grenade_arrow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.smoke_grenade").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.smoke_grenade_purple").withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.SMOKE_GRENADE_ARROW_YELLOW.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.smoke_grenade_arrow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.smoke_grenade").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.smoke_grenade_yellow").withStyle(ChatFormatting.YELLOW, ChatFormatting.ITALIC));
		}

		// Bullets
		if (stack.getItem() == ItemRegistry.WOODEN_MUSKET_BALL.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.wooden_musket_ball").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.STONE_MUSKET_BALL.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.stone_musket_ball").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.GOLDEN_MUSKET_BALL.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.golden_musket_ball").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.COPPER_MUSKET_BALL.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.copper_musket_ball").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.IRON_MUSKET_BALL.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.iron_musket_ball").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.COBALT_MUSKET_BALL.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.cobalt_musket_ball").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.DIAMOND_MUSKET_BALL.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.diamond_musket_ball").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.NETHERITE_MUSKET_BALL.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.netherite_musket_ball").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}

		// Pikes
		if (stack.getItem() == ItemRegistry.WOODEN_PIKE.get() || stack.getItem() == ItemRegistry.STONE_PIKE.get() || stack.getItem() == ItemRegistry.GOLDEN_PIKE.get() || stack.getItem() == ItemRegistry.COPPER_PIKE.get() || stack.getItem() == ItemRegistry.IRON_PIKE.get() || stack.getItem() == ItemRegistry.COBALT_PIKE.get() || stack.getItem() == ItemRegistry.DIAMOND_PIKE.get() || stack.getItem() == ItemRegistry.NETHERITE_PIKE.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.pike").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}

		// Armor
		if (stack.getItem() == ItemRegistry.MOLTEN_HELMET.get() || stack.getItem() == ItemRegistry.MOLTEN_CHESTPLATE.get() || stack.getItem() == ItemRegistry.MOLTEN_LEGGINGS.get() || stack.getItem() == ItemRegistry.MOLTEN_BOOTS.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.molten_armor").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.TESLA_HELMET.get() || stack.getItem() == ItemRegistry.TESLA_CHESTPLATE.get() || stack.getItem() == ItemRegistry.TESLA_LEGGINGS.get() || stack.getItem() == ItemRegistry.TESLA_BOOTS.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.tesla_armor").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.VENTUS_HELMET.get() || stack.getItem() == ItemRegistry.VENTUS_CHESTPLATE.get() || stack.getItem() == ItemRegistry.VENTUS_LEGGINGS.get() || stack.getItem() == ItemRegistry.VENTUS_BOOTS.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.ventus_armor").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}

		// Throwables
		if (stack.getItem() == ItemRegistry.SMOKE_GRENADE.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.smoke_grenade").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.SMOKE_GRENADE_RED.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.smoke_grenade").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.smoke_grenade_red").withStyle(ChatFormatting.RED, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.SMOKE_GRENADE_GREEN.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.smoke_grenade").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.smoke_grenade_green").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.SMOKE_GRENADE_BLUE.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.smoke_grenade").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.smoke_grenade_blue").withStyle(ChatFormatting.BLUE, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.SMOKE_GRENADE_PURPLE.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.smoke_grenade").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.smoke_grenade_purple").withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.SMOKE_GRENADE_YELLOW.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.smoke_grenade").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.smoke_grenade_yellow").withStyle(ChatFormatting.YELLOW, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.MOLOTOV_COCKTAIL.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.molotov_cocktail").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.MUD_BALL.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.mud_ball").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}

		// Bottles
		if (stack.getItem() == ItemRegistry.BOTTLE_OF_ALCOHOL.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.bottle_of_alcohol").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.BOTTLE_OF_WINE.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.bottle_of_wine").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}

		// Traps
		if (stack.getItem() == BlockItemRegistry.PUNJI_STICKS_ITEM.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.punji_sticks").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == BlockItemRegistry.PITFALL_ITEM.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.pitfall").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == BlockItemRegistry.BEAR_TRAP_ITEM.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.bear_trap").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == BlockItemRegistry.LANDMINE_ITEM.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.landmine").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == BlockItemRegistry.SPIKE_TRAP_ITEM.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.spike_trap").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == BlockItemRegistry.WOODEN_SPIKES_ITEM.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.wooden_spikes").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}

		// Gauntlets
		if (stack.getItem() == ItemRegistry.WOODEN_GAUNTLET.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.gauntlet").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.wooden_gauntlet").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.STONE_GAUNTLET.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.gauntlet").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.stone_gauntlet").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.GOLDEN_GAUNTLET.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.gauntlet").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.golden_gauntlet").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.COPPER_GAUNTLET.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.gauntlet").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.copper_gauntlet").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.IRON_GAUNTLET.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.gauntlet").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.iron_gauntlet").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.COBALT_GAUNTLET.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.gauntlet").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.cobalt_gauntlet").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.DIAMOND_GAUNTLET.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.gauntlet").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.diamond_gauntlet").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.NETHERITE_GAUNTLET.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.gauntlet").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.netherite_gauntlet").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}

		// Player utility
		if (stack.getItem() == ItemRegistry.BANDAGE.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.bandage").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.PAINKILLERS.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.painkillers").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.MRE.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.mre").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.FIRST_AID_KIT.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.first_aid_kit").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
		}

		// Miscellaneous
		if (stack.getItem() == BlockItemRegistry.BARREL_TAP_ITEM.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.barrel_tap").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.TESLA_HOE.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.tesla_hoe").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == BlockItemRegistry.SPOTLIGHT_ITEM.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.spotlight").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == BlockItemRegistry.PANIC_ALARM_ITEM.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.panic_alarm").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.MORPHINE.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.morphine").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.USED_SYRINGE.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.used_syringe").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.VENTUS_STAFF.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.ventus_staff").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.HANS_SPAWN_EGG.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.hans_spawn_egg").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == BlockItemRegistry.MORTAR_ITEM.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.mortar").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.CELESTIAL_FRAGMENT.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.celestial_fragment").withStyle(ChatFormatting.YELLOW, ChatFormatting.ITALIC));
		}


		// Lore

		// Summoning Statues
		if (stack.getItem() == BlockItemRegistry.MINUTEMAN_STATUE_ITEM.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.minuteman_statue").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == BlockItemRegistry.MEDIC_STATUE_ITEM.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.medic_statue").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
		}
		// Tiltros
		if (stack.getItem() == ItemRegistry.AZUL_KEYSTONE.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.azul_keystone").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.AZUL_KEYSTONE_FRAGMENT.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.azul_keystone_fragment").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == BlockItemRegistry.AZUL_STAINED_ORCHID_ITEM.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.azul_stained_orchid").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == BlockItemRegistry.WARRIOR_STATUE_HEAD_ITEM.get() || stack.getItem() == BlockItemRegistry.WARRIOR_STATUE_TORSO_ITEM.get() || stack.getItem() == BlockItemRegistry.WARRIOR_STATUE_BASE_ITEM.get()) {
			event.getToolTip().add(Component.translatable("immersiveweapons.tooltip.warrior_statue").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
		}
	}
}