package tech.anonymoushacker1279.immersiveweapons.client;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.api.PluginHandler;
import tech.anonymoushacker1279.immersiveweapons.init.BlockItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.*;
import tech.anonymoushacker1279.immersiveweapons.item.armor.*;
import tech.anonymoushacker1279.immersiveweapons.item.gauntlet.GauntletItem;
import tech.anonymoushacker1279.immersiveweapons.item.pike.PikeItem;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.arrow.AbstractArrowItem;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.arrow.SmokeGrenadeArrowItem;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.bullet.AbstractBulletItem;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.throwable.SmokeGrenadeItem;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class TooltipHandler {

	static int jonnyCurseRandomizer = (int) (Math.random() * 11 + 1);

	/**
	 * Handles adding tooltips to items.
	 *
	 * @param event the <code>ItemTooltipEvent</code> instance
	 */
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void addItemTooltip(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();

		if (stack.getItem() != ItemRegistry.JONNYS_CURSE.get()) {
			jonnyCurseRandomizer = (int) (Math.random() * 11 + 1);
		}

		if (KillCountWeapon.hasKillCount(stack)) {
			event.getToolTip().add(1, KillCountWeapon.getTierText(stack));
			event.getToolTip().add(2, Component.literal(""));
			event.getToolTip().add(KillCountWeapon.getTooltipText(stack).withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
		}

		// Swords
		if (stack.getItem() == ItemRegistry.MOLTEN_SWORD.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.molten_sword").withStyle(ChatFormatting.DARK_RED, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.TESLA_SWORD.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.tesla_sword").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.VENTUS_SWORD.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.ventus_sword").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.ASTRAL_SWORD.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.astral_sword").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.STARSTORM_SWORD.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.starstorm_sword").withStyle(ChatFormatting.RED, ChatFormatting.ITALIC));
		}

		// Molten-specific
		if (stack.getItem().toString().contains("molten") && stack.getItem() instanceof DiggerItem) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.molten_tool").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}

		// Guns
		if (stack.getItem() == ItemRegistry.FLINTLOCK_PISTOL.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.flintlock_pistol").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.BLUNDERBUSS.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.blunderbuss").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.FLARE_GUN.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.flare_gun").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.MUSKET.get() || stack.getItem() == ItemRegistry.MUSKET_SCOPE.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.musket").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.HAND_CANNON.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.hand_cannon").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}

		// Arrows
		if (stack.getItem() instanceof SmokeGrenadeArrowItem smokeArrow) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.smoke_grenade_arrow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));

			if (smokeArrow.color > 0) {
				// The last word in the name is the color
				String color = smokeArrow.toString().substring(smokeArrow.toString().lastIndexOf("_") + 1).toLowerCase();
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.smoke_grenade_" + color).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			}
		} else if (stack.getItem() instanceof AbstractArrowItem arrow) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons." + arrow).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}

		// Bullets
		if (stack.getItem() instanceof AbstractBulletItem bullet) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons." + bullet).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}

		// Pikes
		if (stack.getItem() instanceof PikeItem) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.pike").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}

		// Armor
		if (stack.getItem() instanceof MoltenArmorItem) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.molten_armor").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() instanceof TeslaArmorItem) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.tesla_armor").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() instanceof VentusArmorItem) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.ventus_armor").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() instanceof AstralArmorItem) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.astral_armor").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() instanceof StarstormArmorItem) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.starstorm_armor").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() instanceof PaddedLeatherArmorItem) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.padded_leather_armor").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}

		// Throwables
		if (stack.getItem() instanceof SmokeGrenadeItem grenade) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.smoke_grenade").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));

			if (grenade.color > 0) {
				// The last word in the name is the color
				String color = grenade.toString().substring(grenade.toString().lastIndexOf("_") + 1).toLowerCase();
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.smoke_grenade_" + color).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			}
		}
		if (stack.getItem() == ItemRegistry.MOLOTOV_COCKTAIL.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.molotov_cocktail").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.MUD_BALL.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.mud_ball").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}

		// Bottles
		if (stack.getItem() == ItemRegistry.BOTTLE_OF_ALCOHOL.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.bottle_of_alcohol").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.BOTTLE_OF_WINE.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.bottle_of_wine").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}

		// Traps
		if (stack.getItem() == BlockItemRegistry.PUNJI_STICKS_ITEM.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.punji_sticks").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == BlockItemRegistry.PITFALL_ITEM.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.pitfall").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == BlockItemRegistry.BEAR_TRAP_ITEM.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.bear_trap").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == BlockItemRegistry.LANDMINE_ITEM.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.landmine").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == BlockItemRegistry.SPIKE_TRAP_ITEM.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.spike_trap").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == BlockItemRegistry.WOODEN_SPIKES_ITEM.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.wooden_spikes").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}

		// Gauntlets
		if (stack.getItem() instanceof GauntletItem gauntlet) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.gauntlet").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons." + gauntlet).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}

		// Staffs
		if (stack.getItem() == ItemRegistry.VENTUS_STAFF.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.ventus_staff").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.METEOR_STAFF.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.meteor_staff").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.CURSED_SIGHT_STAFF.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.cursed_sight_staff").withStyle(ChatFormatting.DARK_RED, ChatFormatting.ITALIC));
		}

		// Player utility
		if (stack.getItem() == ItemRegistry.BANDAGE.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.bandage").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.PAINKILLERS.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.painkillers").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.MRE.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.mre").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.FIRST_AID_KIT.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.first_aid_kit").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
		}

		// Miscellaneous
		if (stack.getItem() == BlockItemRegistry.BARREL_TAP_ITEM.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.barrel_tap").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.TESLA_HOE.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.tesla_hoe").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == BlockItemRegistry.SPOTLIGHT_ITEM.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.spotlight").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == BlockItemRegistry.PANIC_ALARM_ITEM.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.panic_alarm").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.MORPHINE.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.morphine").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.USED_SYRINGE.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.used_syringe").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.HANS_SPAWN_EGG.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.hans_spawn_egg").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.SUPER_HANS_SPAWN_EGG.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.super_hans_spawn_egg").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == BlockItemRegistry.MORTAR_ITEM.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.mortar").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.CELESTIAL_FRAGMENT.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.celestial_fragment").withStyle(ChatFormatting.YELLOW, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.AZUL_LOCATOR.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.azul_locator").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.CURSE_CLEANING_SOAP.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.curse_cleaning_soap").withStyle(ChatFormatting.DARK_AQUA, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.creative_only").withStyle(ChatFormatting.BLUE, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.CHAMPION_KEYCARD.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.champion_keycard").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.KILL_COUNTER.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.kill_counter").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}

		// Accessories
		if (event.getEntity() != null && stack.getItem() instanceof AccessoryItem item) {
			if (stack.getItem() == ItemRegistry.SATCHEL.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.satchel").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			}
			if (stack.getItem() == ItemRegistry.POWDER_HORN.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.powder_horn").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			}
			if (stack.getItem() == ItemRegistry.BERSERKERS_AMULET.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.berserkers_amulet_1").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.berserkers_amulet_2").withStyle(ChatFormatting.DARK_RED, ChatFormatting.ITALIC));
			}
			if (stack.getItem() == ItemRegistry.HANS_BLESSING.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.hans_blessing_1").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.hans_blessing_2").withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.ITALIC));
			}
			if (stack.getItem() == ItemRegistry.CELESTIAL_SPIRIT.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.celestial_spirit_1").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.celestial_spirit_2").withStyle(ChatFormatting.DARK_GREEN, ChatFormatting.ITALIC));
			}
			if (stack.getItem() == ItemRegistry.BLADEMASTER_EMBLEM.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.blademaster_emblem").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			}
			if (stack.getItem() == ItemRegistry.DEADEYE_PENDANT.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.deadeye_pendant_1").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.deadeye_pendant_2").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			}
			if (stack.getItem() == ItemRegistry.BLOATED_HEART.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.bloated_heart").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			}
			if (stack.getItem() == ItemRegistry.NETHERITE_SHIELD.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.netherite_shield").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			}
			if (stack.getItem() == ItemRegistry.MELEE_MASTERS_MOLTEN_GLOVE.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.melee_masters_molten_glove").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			}
			// Regex check for "immersiveweapons:X_ring" where X is any string
			ResourceLocation path = ForgeRegistries.ITEMS.getKey(item.asItem());
			if (path != null && path.toString().matches("immersiveweapons:.+_ring")) {
				// Get the name of the ring
				String ringName = path.toString().substring(17);
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.%s".formatted(ringName)).withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			}
			if (stack.getItem() == ItemRegistry.MEDAL_OF_ADEQUACY.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.medal_of_adequacy").withStyle(ChatFormatting.WHITE, ChatFormatting.ITALIC));
			}
			if (stack.getItem() == ItemRegistry.DEPTH_CHARM.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.depth_charm_1").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.depth_charm_2").withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.ITALIC));
			}
			if (stack.getItem() == ItemRegistry.INSOMNIA_AMULET.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.insomnia_amulet_1").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.insomnia_amulet_2").withStyle(ChatFormatting.DARK_RED, ChatFormatting.ITALIC));
			}
			if (stack.getItem() == ItemRegistry.GOGGLES.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.goggles").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			}
			if (stack.getItem() == ItemRegistry.LAVA_GOGGLES.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.lava_goggles").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			}
			if (stack.getItem() == ItemRegistry.NIGHT_VISION_GOGGLES.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.night_vision_goggles").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			}
			if (stack.getItem() == ItemRegistry.AGILITY_BRACELET.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.agility_bracelet").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			}
			if (stack.getItem() == ItemRegistry.BLOODY_CLOTH.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.bloody_cloth").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			}
			if (stack.getItem() == ItemRegistry.IRON_FIST.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.iron_fist_1").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.iron_fist_2").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
			}
			if (stack.getItem() == ItemRegistry.ANCIENT_SCROLL.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.ancient_scroll").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			}
			if (stack.getItem() == ItemRegistry.GLOVE_OF_RAPID_SWINGING.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.glove_of_rapid_swinging").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			}
			if (stack.getItem() == ItemRegistry.HAND_OF_DOOM.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.hand_of_doom_1").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.hand_of_doom_2").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
			}

			addShiftTooltip(event.getToolTip(), addAccessoryTooltips(item, event.getEntity(), stack));
		} else if (stack.getItem() instanceof CursedItem) {
			if (stack.getItem() == ItemRegistry.BLOODY_SACRIFICE.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.bloody_sacrifice").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.bloody_sacrifice_charge_note").withStyle(ChatFormatting.DARK_RED, ChatFormatting.ITALIC));
				if (stack.isDamaged()) {
					int charge = 100 - stack.getDamageValue();
					event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.bloody_sacrifice_charge", charge).withStyle(ChatFormatting.DARK_RED, ChatFormatting.ITALIC));
				} else if (stack.getDamageValue() == 0) {
					event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.bloody_sacrifice_fully_charged").withStyle(ChatFormatting.DARK_RED, ChatFormatting.ITALIC));
				}

				List<Component> effects = new ArrayList<>(5);
				effects.add(Component.translatable("tooltip.immersiveweapons.bloody_sacrifice_effect_1").withStyle(ChatFormatting.DARK_RED, ChatFormatting.ITALIC));
				effects.add(Component.translatable("tooltip.immersiveweapons.bloody_sacrifice_effect_2").withStyle(ChatFormatting.DARK_RED, ChatFormatting.ITALIC));
				effects.add(Component.translatable("tooltip.immersiveweapons.bloody_sacrifice_effect_3").withStyle(ChatFormatting.DARK_RED, ChatFormatting.ITALIC));
				effects.add(Component.translatable("tooltip.immersiveweapons.bloody_sacrifice_effect_4").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
				effects.add(Component.translatable("tooltip.immersiveweapons.bloody_sacrifice_effect_5").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));

				addShiftTooltip(event.getToolTip(), effects);
			}
			if (stack.getItem() == ItemRegistry.JONNYS_CURSE.get()) {
				// There are 10 randomized tooltips, (1-11), as "jonnys_curse_x"
				// Pick a random one each time
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.jonnys_curse_%s".formatted(jonnyCurseRandomizer)).withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));

				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.jonnys_curse_charge_note").withStyle(ChatFormatting.DARK_RED, ChatFormatting.ITALIC));
				if (stack.isDamaged()) {
					int charge = 100 - stack.getDamageValue();
					event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.jonnys_curse_charge", charge).withStyle(ChatFormatting.DARK_RED, ChatFormatting.ITALIC));
				} else if (stack.getDamageValue() == 0) {
					event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.jonnys_curse_fully_charged").withStyle(ChatFormatting.DARK_RED, ChatFormatting.ITALIC));
				}

				List<Component> effects = new ArrayList<>(5);
				effects.add(Component.translatable("tooltip.immersiveweapons.jonnys_curse_effect_1").withStyle(ChatFormatting.DARK_RED, ChatFormatting.ITALIC));
				effects.add(Component.translatable("tooltip.immersiveweapons.jonnys_curse_effect_2").withStyle(ChatFormatting.DARK_RED, ChatFormatting.ITALIC));
				effects.add(Component.translatable("tooltip.immersiveweapons.jonnys_curse_effect_3").withStyle(ChatFormatting.DARK_RED, ChatFormatting.ITALIC));
				effects.add(Component.translatable("tooltip.immersiveweapons.jonnys_curse_effect_4").withStyle(ChatFormatting.DARK_RED, ChatFormatting.ITALIC));
				effects.add(Component.translatable("tooltip.immersiveweapons.jonnys_curse_effect_5").withStyle(ChatFormatting.DARK_RED, ChatFormatting.ITALIC));

				addShiftTooltip(event.getToolTip(), effects);
			}
		}

		// Lore

		// Summoning Statues
		if (stack.getItem() == BlockItemRegistry.MINUTEMAN_STATUE_ITEM.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.minuteman_statue").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == BlockItemRegistry.MEDIC_STATUE_ITEM.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.medic_statue").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
		}
		// Tiltros
		if (stack.getItem() == ItemRegistry.AZUL_KEYSTONE.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.azul_keystone").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.AZUL_KEYSTONE_FRAGMENT.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.azul_keystone_fragment").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == BlockItemRegistry.AZUL_STAINED_ORCHID_ITEM.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.azul_stained_orchid").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == BlockItemRegistry.WARRIOR_STATUE_HEAD_ITEM.get() || stack.getItem() == BlockItemRegistry.WARRIOR_STATUE_TORSO_ITEM.get() || stack.getItem() == BlockItemRegistry.WARRIOR_STATUE_BASE_ITEM.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.warrior_statue").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
		}
	}

	/**
	 * Adds a tooltip which appears while the player holds SHIFT.
	 *
	 * @param existingTooltips The list of tooltips already on the item.
	 * @param shiftTooltip     The tooltip to add if the player is holding SHIFT.
	 */
	private static void addShiftTooltip(List<Component> existingTooltips, MutableComponent shiftTooltip) {
		if (!Screen.hasShiftDown()) {
			existingTooltips.add(Component.translatable("tooltip.immersiveweapons.shift_for_info").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
		} else {
			existingTooltips.add(shiftTooltip);
		}
	}

	/**
	 * Adds tooltips which appear while the player holds SHIFT.
	 *
	 * @param existingTooltips The list of tooltips already on the item.
	 * @param shiftTooltips    The list of tooltips to add if the player is holding SHIFT.
	 */
	private static void addShiftTooltip(List<Component> existingTooltips, List<Component> shiftTooltips) {
		if (!Screen.hasShiftDown()) {
			existingTooltips.add(Component.translatable("tooltip.immersiveweapons.shift_for_info").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
		} else {
			existingTooltips.addAll(shiftTooltips);
		}
	}

	/**
	 * Add accessory-specific tooltips (typically for use inside addShiftTooltip)
	 *
	 * @param item   the accessory item
	 * @param player the player holding the item
	 * @return a list of tooltips
	 */
	private static List<Component> addAccessoryTooltips(AccessoryItem item, Player player, ItemStack stack) {
		List<Component> tooltips = new ArrayList<>(5);

		if (ImmersiveWeapons.IWCB_LOADED && PluginHandler.isPluginActive("iwcompatbridge:curios_plugin")) {
			tooltips.add(Component.translatable("tooltip.iwcompatbridge.accessory_note", item.getSlot()).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
		} else {
			tooltips.add(Component.translatable("tooltip.immersiveweapons.accessory_slot", item.getSlot()).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));

			if (item.isActive(player, stack)) {
				tooltips.add(Component.translatable("tooltip.immersiveweapons.accessory_note").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
			} else {
				tooltips.add(Component.translatable("tooltip.immersiveweapons.accessory_inactive").withStyle(ChatFormatting.RED, ChatFormatting.ITALIC));
			}
		}

		return tooltips;
	}
}