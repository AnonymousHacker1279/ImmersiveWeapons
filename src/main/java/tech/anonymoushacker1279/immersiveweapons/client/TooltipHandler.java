package tech.anonymoushacker1279.immersiveweapons.client;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import oshi.util.tuples.Triplet;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.api.PluginHandler;
import tech.anonymoushacker1279.immersiveweapons.client.tooltip.DynamicTooltip;
import tech.anonymoushacker1279.immersiveweapons.client.tooltip.SerializableTooltip;
import tech.anonymoushacker1279.immersiveweapons.init.AccessoryEffectTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.BlockItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.DataComponentTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.CursedItem;
import tech.anonymoushacker1279.immersiveweapons.item.KillCountWeapon;
import tech.anonymoushacker1279.immersiveweapons.item.RecoveryStaffItem;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.Accessory;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.AccessoryEffectInstance;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.AccessoryLoader;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.scaling.AccessoryEffectScalingType;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.scaling.AttributeOperation;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.scaling.DynamicAttributeOperationInstance;
import tech.anonymoushacker1279.immersiveweapons.util.markers.TooltipMarker;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Supplier;

@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, value = Dist.CLIENT)
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
	@SubscribeEvent
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
		Accessory accessory = AccessoryLoader.ACCESSORIES.get(stack.getItem());
		if (event.getEntity() != null && accessory != null) {
			if (!accessory.tooltips().isEmpty()) {
				event.getToolTip().addAll(accessory.tooltips().stream()
						.map(SerializableTooltip::getComponent)
						.toList());
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
		if (!Minecraft.getInstance().hasShiftDown()) {
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
		if (!Minecraft.getInstance().hasAltDown()) {
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
				for (AccessoryEffectInstance effect : accessory.effects()) {
					String value;

					if (effect.type().equals(AccessoryEffectTypeRegistry.LOOTING_LEVEL.get())) {
						value = (int) effect.value() + " levels";
					} else {
						value = (float) Math.round(effect.value() * 1000f) / 10f + "%";
					}

					Optional<AccessoryEffectScalingType> scalingType = effect.scalingType();
					if (scalingType.isPresent()) {
						Component scaling = Component.translatable(scalingType.get().createTranslation());
						altTooltips.add(Component.translatable(effect.type().createTranslation(), value).append(" ").append(scaling).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
					} else {
						altTooltips.add(Component.translatable(effect.type().createTranslation(), value).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
					}
				}
			}

			// Add attribute modifier effects
			if (!accessory.attributeModifiers().isEmpty()) {
				altTooltips.add(Component.translatable("tooltip.immersiveweapons.accessory.attribute_modifier").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
				for (AttributeOperation attributeOp : accessory.attributeModifiers()) {
					String amount;

					if (attributeOp.attribute().equals(Attributes.MAX_HEALTH)) {
						// Convert to hearts
						amount = (float) Math.round(attributeOp.modifier().amount() / 2f) + " hearts";
					} else {
						amount = (float) Math.round(attributeOp.modifier().amount() * 1000f) / 10f + "%";
					}

					altTooltips.add(Component.translatable(attributeOp.attribute().value().getDescriptionId()).append(": " + amount).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
				}
			}
			if (!accessory.dynamicAttributeModifiers().isEmpty()) {
				altTooltips.add(Component.translatable("tooltip.immersiveweapons.accessory.dynamic_attribute_modifier").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
				for (DynamicAttributeOperationInstance dynamicAttributeOp : accessory.dynamicAttributeModifiers()) {
					String amount = (float) Math.round(dynamicAttributeOp.targetValue() * 1000f) / 10f + "%";
					altTooltips.add(Component.translatable(dynamicAttributeOp.attributeOperation().attribute().value().getDescriptionId()).append(": " + amount).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
				}
			}

			addAltTooltip(existingTooltips, altTooltips);
		}
	}
}