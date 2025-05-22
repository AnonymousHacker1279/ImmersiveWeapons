package tech.anonymoushacker1279.immersiveweapons.client;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import oshi.util.tuples.Triplet;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.api.PluginHandler;
import tech.anonymoushacker1279.immersiveweapons.client.tooltip.DynamicTooltip;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.item.CursedItem;
import tech.anonymoushacker1279.immersiveweapons.item.KillCountWeapon;
import tech.anonymoushacker1279.immersiveweapons.item.RecoveryStaffItem;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.Accessory;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.AccessoryEffectInstance;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.scaling.AccessoryEffectScalingType;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.scaling.AttributeOperation;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.scaling.DynamicAttributeOperationInstance;
import tech.anonymoushacker1279.immersiveweapons.util.markers.TooltipMarker;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = Bus.GAME, value = Dist.CLIENT)
public class TooltipHandler {

	static int jonnyCurseRandomizer = (int) (Math.random() * 11 + 1);

	static final Map<Item, Triplet<String[], ChatFormatting[], Class<? extends DynamicTooltip>>> TOOLTIP_MAP = new HashMap<>(150);

	/**
	 * Compiles all item tooltips with a {@link TooltipMarker} annotation.
	 */
	public static void compileTooltips() {
		ImmersiveWeapons.LOGGER.info("Compiling item tooltips...");

		int tooltips = 0;
		long time = System.currentTimeMillis();
		Class<?>[] classes = {ItemRegistry.class, BlockItemRegistry.class};
		for (Class<?> clazz : classes) {
			for (Field field : clazz.getDeclaredFields()) {
				if (field.isAnnotationPresent(TooltipMarker.class)) {
					if (Modifier.isStatic(field.getModifiers()) && field.getType() == Supplier.class) {
						try {
							if (field.get(null) instanceof DeferredHolder<?, ?> holder) {
								if (holder.get() instanceof Item item) {
									TooltipMarker marker = field.getAnnotation(TooltipMarker.class);

									String baseKey = marker.key().isEmpty()
											? "tooltip.immersiveweapons." + item.builtInRegistryHolder().getRegisteredName().replaceAll(ImmersiveWeapons.MOD_ID + ":", "")
											: "tooltip.immersiveweapons." + marker.key();

									String[] keys = new String[marker.components()];
									if (marker.components() == 1) {
										keys[0] = baseKey;
									} else {
										for (int i = 0; i < marker.components(); i++) {
											keys[i] = baseKey + "_" + (i + 1);
										}
									}

									TOOLTIP_MAP.putIfAbsent(item, new Triplet<>(keys, marker.style(), marker.dynamicTooltip() == DynamicTooltip.class ? null : marker.dynamicTooltip()));
									tooltips++;
								}
							}
						} catch (IllegalAccessException e) {
							ImmersiveWeapons.LOGGER.error("Failed to compile tooltips for item: {}", field.getName());
						}
					}
				}
			}
		}

		ImmersiveWeapons.LOGGER.info("Compiled {} item tooltips in {}ms", tooltips, System.currentTimeMillis() - time);
	}

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
			event.getToolTip().add(1, KillCountWeapon.getTierComponent(stack));
			event.getToolTip().add(2, Component.literal(""));
			event.getToolTip().add(KillCountWeapon.getKillComponent(stack));
		}

		if (stack.has(DataComponentTypeRegistry.SCOPE)) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.gun.meta.scope").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
		}

		TOOLTIP_MAP.computeIfPresent(stack.getItem(), (item, triplet) -> {
			if (triplet.getC() != null) {
				DynamicTooltip dynamicTooltip;
				try {
					dynamicTooltip = triplet.getC().getDeclaredConstructor().newInstance();
				} catch (Exception e) {
					ImmersiveWeapons.LOGGER.error("Failed to create instance of dynamic tooltip for item: {}", item);
					return triplet;
				}

				if (dynamicTooltip.shouldComputeSimpleTooltips(event)) {
					for (int i = 0; i < triplet.getA().length; i++) {
						event.getToolTip().add(Component.translatable(triplet.getA()[i]).withStyle(triplet.getB()));
					}

					dynamicTooltip.computeTooltip(event);
				} else {
					dynamicTooltip.computeTooltip(event);
				}
			} else {
				for (int i = 0; i < triplet.getA().length; i++) {
					event.getToolTip().add(Component.translatable(triplet.getA()[i]).withStyle(triplet.getB()));
				}
			}


			return triplet;
		});

		// Swords
		if (stack.getItem() == ItemRegistry.THE_SWORD.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.the_sword_1").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.the_sword_2").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
			return;
		}

		// Staffs
		if (stack.getItem() instanceof RecoveryStaffItem staffItem) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.recovery_staff_1").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.recovery_staff_2").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
			float healAmount = (float) Math.round(staffItem.getHealAmount() * 10) / 10;
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.recovery_staff_3", healAmount).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
			return;
		}

		// Miscellaneous
		if (stack.getItem() == ItemRegistry.CURSE_CLEANING_SOAP.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.curse_cleaning_soap").withStyle(ChatFormatting.DARK_AQUA, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.creative_only").withStyle(ChatFormatting.BLUE, ChatFormatting.ITALIC));
			return;
		}

		// Accessories
		Accessory accessory = stack.getItemHolder().getData(Accessory.ACCESSORY);
		if (event.getEntity() != null && accessory != null) {
			if (stack.getItem() == ItemRegistry.SATCHEL.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.satchel").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			} else if (stack.getItem() == ItemRegistry.POWDER_HORN.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.powder_horn_1").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.powder_horn_2").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			} else if (stack.getItem() == ItemRegistry.BERSERKERS_AMULET.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.berserkers_amulet_1").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.berserkers_amulet_2").withStyle(ChatFormatting.DARK_RED, ChatFormatting.ITALIC));
			} else if (stack.getItem() == ItemRegistry.HANS_BLESSING.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.hans_blessing_1").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.hans_blessing_2").withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.ITALIC));
			} else if (stack.getItem() == ItemRegistry.CELESTIAL_SPIRIT.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.celestial_spirit_1").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.celestial_spirit_2").withStyle(ChatFormatting.DARK_GREEN, ChatFormatting.ITALIC));
			} else if (stack.getItem() == ItemRegistry.VOID_BLESSING.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.void_blessing").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
			} else if (stack.getItem() == ItemRegistry.BLADEMASTER_EMBLEM.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.blademaster_emblem").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			} else if (stack.getItem() == ItemRegistry.DEADEYE_PENDANT.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.deadeye_pendant_1").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.deadeye_pendant_2").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			} else if (stack.getItem() == ItemRegistry.BLOATED_HEART.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.bloated_heart").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			} else if (stack.getItem() == ItemRegistry.NETHERITE_SHIELD.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.netherite_shield").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			} else if (stack.getItem() == ItemRegistry.MELEE_MASTERS_MOLTEN_GLOVE.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.melee_masters_molten_glove").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			} else if (stack.getItem() == ItemRegistry.MEDAL_OF_ADEQUACY.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.medal_of_adequacy").withStyle(ChatFormatting.WHITE, ChatFormatting.ITALIC));
			} else if (stack.getItem() == ItemRegistry.DEPTH_CHARM.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.depth_charm_1").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.depth_charm_2").withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.ITALIC));
			} else if (stack.getItem() == ItemRegistry.REINFORCED_DEPTH_CHARM.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.reinforced_depth_charm").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			} else if (stack.getItem() == ItemRegistry.INSOMNIA_AMULET.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.insomnia_amulet_1").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.insomnia_amulet_2").withStyle(ChatFormatting.DARK_RED, ChatFormatting.ITALIC));
			} else if (stack.getItem() == ItemRegistry.GOGGLES.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.goggles").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			} else if (stack.getItem() == ItemRegistry.LAVA_GOGGLES.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.lava_goggles").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			} else if (stack.getItem() == ItemRegistry.NIGHT_VISION_GOGGLES.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.night_vision_goggles").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			} else if (stack.getItem() == ItemRegistry.AGILITY_BRACELET.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.agility_bracelet").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			} else if (stack.getItem() == ItemRegistry.BLOODY_CLOTH.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.bloody_cloth").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			} else if (stack.getItem() == ItemRegistry.IRON_FIST.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.iron_fist_1").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.iron_fist_2").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
			} else if (stack.getItem() == ItemRegistry.ANCIENT_SCROLL.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.ancient_scroll").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			} else if (stack.getItem() == ItemRegistry.GLOVE_OF_RAPID_SWINGING.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.glove_of_rapid_swinging").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			} else if (stack.getItem() == ItemRegistry.HAND_OF_DOOM.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.hand_of_doom_1").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.hand_of_doom_2").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
			} else if (stack.getItem() == ItemRegistry.HOLY_MANTLE.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.holy_mantle_1").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.holy_mantle_2").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.holy_mantle_3").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			} else if (stack.getItem() == ItemRegistry.VENSTRAL_JAR.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.venstral_jar").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			} else if (stack.getItem() == ItemRegistry.SUPER_BLANKET_CAPE.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.super_blanket_cape_1").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.super_blanket_cape_2").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
			}
			// Regex check for "immersiveweapons:X_ring" where X is any string
			ResourceLocation path = BuiltInRegistries.ITEM.getKey(stack.getItem().asItem());
			if (path.toString().matches("immersiveweapons:.+_ring")) {
				// Get the name of the ring
				String ringName = path.toString().substring(17);
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.%s".formatted(ringName)).withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			}

			addAccessoryTooltips(accessory, event.getEntity(), stack, event.getToolTip());
			return;
		}

		if (stack.getItem() instanceof CursedItem) {
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
				return;
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
				return;
			}
		}

		// Regex check for "immersiveweapons:pedestal_augment_X" where X is any string
		ResourceLocation path = BuiltInRegistries.ITEM.getKey(stack.getItem().asItem());
		if (path.toString().matches("immersiveweapons:pedestal_augment_.+")) {
			// Get the name of the augment
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.%s".formatted(path.getPath())).withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.pedestal_augment.note").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
	}

	/**
	 * Adds tooltips which appear while the player holds SHIFT.
	 *
	 * @param existingTooltips The list of tooltips already on the item.
	 * @param shiftTooltips    The list of tooltips to add if the player is holding SHIFT.
	 */
	public static void addShiftTooltip(List<Component> existingTooltips, List<Component> shiftTooltips) {
		if (!Screen.hasShiftDown()) {
			existingTooltips.add(Component.translatable("tooltip.immersiveweapons.shift_for_info").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
		} else {
			existingTooltips.addAll(shiftTooltips);
		}
	}

	/**
	 * Adds tooltips which appear while the player holds ALT.
	 *
	 * @param existingTooltips The list of tooltips already on the item.
	 * @param altTooltips      The list of tooltips to add if the player is holding ALT.
	 */
	private static void addAltTooltip(List<Component> existingTooltips, List<Component> altTooltips) {
		if (!Screen.hasAltDown()) {
			existingTooltips.add(Component.translatable("tooltip.immersiveweapons.alt_for_info").withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.ITALIC));
		} else {
			existingTooltips.addAll(altTooltips);
		}
	}

	/**
	 * Add accessory-specific tooltips (typically for use inside {@link #addShiftTooltip(List, List)})
	 *
	 * @param accessory the accessory to add tooltips for
	 * @param player    the player holding the item
	 */
	private static void addAccessoryTooltips(Accessory accessory, Player player, ItemStack stack, List<Component> existingTooltips) {
		List<Component> shiftTooltips = new ArrayList<>(5);
		List<Component> altTooltips = new ArrayList<>(10);

		if (ImmersiveWeapons.IWCB_LOADED && PluginHandler.isPluginActive("iwcompatbridge:curios_plugin")) {
			shiftTooltips.add(Component.translatable("tooltip.iwcompatbridge.accessory_note").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
		} else {
			shiftTooltips.add(Component.translatable("tooltip.immersiveweapons.accessory_slot", accessory.slot().getComponent()).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));

			if (accessory.isActive(player, stack, accessory.slot())) {
				shiftTooltips.add(Component.translatable("tooltip.immersiveweapons.accessory_note").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
			} else {
				shiftTooltips.add(Component.translatable("tooltip.immersiveweapons.accessory_inactive").withStyle(ChatFormatting.RED, ChatFormatting.ITALIC));
			}
		}

		addShiftTooltip(existingTooltips, shiftTooltips);

		if (!accessory.effects().isEmpty() || !accessory.attributeModifiers().isEmpty() || !accessory.dynamicAttributeModifiers().isEmpty()) {
			altTooltips.add(CommonComponents.EMPTY);

			// Add basic effects
			if (!accessory.effects().isEmpty()) {
				altTooltips.add(Component.translatable("tooltip.immersiveweapons.accessory.effects").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
				for (AccessoryEffectInstance entry : accessory.effects()) {
					String value;

					if (entry.type() == AccessoryEffectTypeRegistry.LOOTING_LEVEL.get()) {
						value = entry.value() + " levels";
					} else {
						value = (float) Math.round(entry.value() * 1000f) / 10f + "%";
					}

					AccessoryEffectScalingType scalingType = accessory.effectScalingTypes().get(entry.type().name());

					if (scalingType != null && !scalingType.getName().equals(AccessoryEffectScalingTypeRegistry.NONE.get().getName())) {
						Component scaling = Component.translatable(accessory.effectScalingTypes().get(entry.type().name()).createTranslation());
						altTooltips.add(Component.translatable(entry.type().createTranslation(), value).append(" ").append(scaling).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
					} else {
						altTooltips.add(Component.translatable(entry.type().createTranslation(), value).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
					}
				}
			}

			// Add attribute modifier effects
			if (!accessory.attributeModifiers().isEmpty()) {
				altTooltips.add(Component.translatable("tooltip.immersiveweapons.accessory.attribute_modifier").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
				for (AttributeOperation entry : accessory.attributeModifiers()) {
					String amount;

					if (entry.attribute() == Attributes.MAX_HEALTH.value()) {
						// Convert to hearts
						amount = (float) Math.round(entry.modifier().amount() / 2f) + " hearts";
					} else {
						amount = (float) Math.round(entry.modifier().amount() * 1000f) / 10f + "%";
					}

					altTooltips.add(Component.translatable(entry.attribute().value().getDescriptionId()).append(": " + amount).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
				}
			}
			if (!accessory.dynamicAttributeModifiers().isEmpty()) {
				altTooltips.add(Component.translatable("tooltip.immersiveweapons.accessory.dynamic_attribute_modifier").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
				for (DynamicAttributeOperationInstance entry : accessory.dynamicAttributeModifiers()) {
					String amount = (float) Math.round(entry.targetValue() * 1000f) / 10f + "%";
					altTooltips.add(Component.translatable(entry.attributeOperation().attribute().value().getDescriptionId()).append(": " + amount).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
				}
			}

			addAltTooltip(existingTooltips, altTooltips);
		}
	}
}