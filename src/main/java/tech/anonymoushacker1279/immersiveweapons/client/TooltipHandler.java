package tech.anonymoushacker1279.immersiveweapons.client;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.api.PluginHandler;
import tech.anonymoushacker1279.immersiveweapons.data.groups.immersiveweapons.IWItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.item.CursedItem;
import tech.anonymoushacker1279.immersiveweapons.item.KillCountWeapon;
import tech.anonymoushacker1279.immersiveweapons.item.RecoveryStaffItem;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.Accessory;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.AccessoryEffectInstance;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.scaling.AccessoryEffectScalingType;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.scaling.AttributeOperation;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.scaling.DynamicAttributeOperationInstance;
import tech.anonymoushacker1279.immersiveweapons.item.gauntlet.GauntletItem;
import tech.anonymoushacker1279.immersiveweapons.item.gun.AbstractGunItem;
import tech.anonymoushacker1279.immersiveweapons.item.gun.AbstractGunItem.PowderType;
import tech.anonymoushacker1279.immersiveweapons.item.pike.PikeItem;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.BulletItem;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.CustomArrowItem;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.ThrowableItem;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.ThrowableItem.ThrowableType;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = Bus.GAME, value = Dist.CLIENT)
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
			event.getToolTip().add(1, KillCountWeapon.getTierComponent(stack));
			event.getToolTip().add(2, Component.literal(""));
			event.getToolTip().add(KillCountWeapon.getKillComponent(stack));
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
		if (stack.getItem() == ItemRegistry.VOID_SWORD.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.void_sword").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.THE_SWORD.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.the_sword_1").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.the_sword_2").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
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
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.flare_gun_1").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.flare_gun_2").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.MUSKET.get() || stack.getItem() == ItemRegistry.MUSKET_SCOPE.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.musket").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.HAND_CANNON.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.hand_cannon").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.DRAGONS_BREATH_CANNON.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.dragons_breath_cannon").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() instanceof AbstractGunItem abstractGunItem && event.getEntity() != null) {
			List<Component> shiftTooltipInfo = new ArrayList<>(10);
			shiftTooltipInfo.add(CommonComponents.EMPTY);

			shiftTooltipInfo.add(Component.translatable("tooltip.immersiveweapons.gun.meta.base_velocity", abstractGunItem.getBaseFireVelocity()).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));

			int reloadTime = abstractGunItem.getCooldown() / 20;
			shiftTooltipInfo.add(Component.translatable("tooltip.immersiveweapons.gun.meta.base_reload_time", reloadTime).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));

			if (abstractGunItem.getKnockbackLevel() > 0) {
				shiftTooltipInfo.add(Component.translatable("tooltip.immersiveweapons.gun.meta.base_knockback", abstractGunItem.getKnockbackLevel()).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
			}

			PowderType powderType = abstractGunItem.findPowder(stack, event.getEntity());

			ChatFormatting powderColor = ChatFormatting.GRAY;
			String powderName = "None";
			String velocityModifier = "0%";
			if (powderType != null) {
				ItemStack powder = powderType.powder();
				powderName = powder.getHoverName().getString();
				velocityModifier = (float) Math.round(powderType.data().velocityModifier() * 1000f) / 10f + "%";
			} else {
				powderColor = ChatFormatting.RED;
			}

			shiftTooltipInfo.add(Component.translatable("tooltip.immersiveweapons.gun.meta.selected_powder", powderName, velocityModifier).withStyle(powderColor, ChatFormatting.ITALIC));

			ItemStack ammo = abstractGunItem.findAmmo(stack, event.getEntity());
			String ammoName = ammo == ItemStack.EMPTY ? "None" : ammo.getHoverName().getString();
			ChatFormatting ammoColor = ammo == ItemStack.EMPTY ? ChatFormatting.RED : ChatFormatting.GRAY;

			shiftTooltipInfo.add(Component.translatable("tooltip.immersiveweapons.gun.meta.selected_ammo", ammoName).withStyle(ammoColor, ChatFormatting.ITALIC));

			addShiftTooltip(event.getToolTip(), shiftTooltipInfo);
		}

		// Bows
		if (stack.getItem() == ItemRegistry.ICE_BOW.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.ice_bow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.DRAGONS_BREATH_BOW.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.dragons_breath_bow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.AURORA_BOW.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.aurora_bow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}

		// Arrows
		if (stack.getItem() instanceof CustomArrowItem<?> arrow) {
			if (arrow.color == -1) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons." + BuiltInRegistries.ITEM.getKey(arrow).getPath()).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			} else {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.smoke_grenade_arrow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));

				if (arrow.color > 0) {
					// The last word in the name is the color
					String color = arrow.toString().substring(arrow.toString().lastIndexOf("_") + 1).toLowerCase();
					event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.smoke_grenade_" + color).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
				}
			}
		}

		// Bullets
		if (stack.getItem() instanceof BulletItem<?> bullet) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons." + BuiltInRegistries.ITEM.getKey(bullet).getPath()).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));

			List<Component> shiftTooltipInfo = new ArrayList<>(10);
			shiftTooltipInfo.add(CommonComponents.EMPTY);

			shiftTooltipInfo.add(Component.translatable("tooltip.immersiveweapons.bullet.meta.base_damage", bullet.damage).withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			shiftTooltipInfo.add(Component.translatable("tooltip.immersiveweapons.bullet.meta.gravity_modifier", -bullet.gravityModifier).withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));

			if (bullet.knockbackStrength > 0) {
				shiftTooltipInfo.add(Component.translatable("tooltip.immersiveweapons.bullet.meta.base_knockback_level", bullet.knockbackStrength).withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			}
			if (bullet.pierceLevel > 0) {
				shiftTooltipInfo.add(Component.translatable("tooltip.immersiveweapons.bullet.meta.piercing_level", bullet.pierceLevel).withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			}
			if (bullet.misfireChance > 0) {
				float misfireChance = Math.round(bullet.misfireChance * 100f);
				shiftTooltipInfo.add(Component.translatable("tooltip.immersiveweapons.bullet.meta.misfire_chance", misfireChance).withStyle(ChatFormatting.RED, ChatFormatting.ITALIC));
			}

			float densityModifier = stack.getOrDefault(DataComponentTypeRegistry.DENSITY_MODIFIER.get(), -1f);
			if (densityModifier != -1f) {
				densityModifier = (float) Math.round(densityModifier * 100f) / 100f;
				shiftTooltipInfo.add(Component.translatable("tooltip.immersiveweapons.bullet.meta.density_modifier", densityModifier).withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			}

			addShiftTooltip(event.getToolTip(), shiftTooltipInfo);
		}

		// Pikes
		if (stack.getItem() instanceof PikeItem) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.pike").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}

		// Armor
		if (stack.is(IWItemTagGroups.MOLTEN_ARMOR)) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.molten_armor").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.is(IWItemTagGroups.TESLA_ARMOR)) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.tesla_armor").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.is(IWItemTagGroups.VENTUS_ARMOR)) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.ventus_armor").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.is(IWItemTagGroups.ASTRAL_ARMOR)) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.astral_armor").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.is(IWItemTagGroups.STARSTORM_ARMOR)) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.starstorm_armor").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.is(IWItemTagGroups.PADDED_LEATHER)) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.padded_leather_armor").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
		if (stack.is(IWItemTagGroups.VOID_ARMOR)) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.void_armor").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}

		// Throwables
		if (stack.getItem() instanceof ThrowableItem grenade) {
			if (grenade.type == ThrowableType.SMOKE_GRENADE) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.smoke_grenade").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));

				if (grenade.color > 0) {
					// The last word in the name is the color
					String color = grenade.toString().substring(grenade.toString().lastIndexOf("_") + 1).toLowerCase();
					event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.smoke_grenade_" + color).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
				}
			} else if (grenade.type == ThrowableType.FLASHBANG) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.flashbang").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
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
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons." + BuiltInRegistries.ITEM.getKey(gauntlet).getPath()).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
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
		if (stack.getItem() == ItemRegistry.SCULK_STAFF.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.sculk_staff").withStyle(ChatFormatting.DARK_AQUA, ChatFormatting.ITALIC));
		}
		if (stack.getItem() instanceof RecoveryStaffItem staffItem) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.recovery_staff_1").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.recovery_staff_2").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
			float healAmount = (float) Math.round(staffItem.getHealAmount() * 10) / 10;
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.recovery_staff_3", healAmount).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
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
		if (stack.getItem() == ItemRegistry.MOLDY_BREAD.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.moldy_bread").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
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
		if (stack.getItem() == BlockItemRegistry.TELEPORTER_ITEM.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.teleporter").withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.ITALIC));
		}

		// Accessories
		Accessory accessory = stack.getItemHolder().getData(Accessory.ACCESSORY);
		if (event.getEntity() != null && accessory != null) {
			if (stack.getItem() == ItemRegistry.SATCHEL.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.satchel").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			}
			if (stack.getItem() == ItemRegistry.POWDER_HORN.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.powder_horn_1").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.powder_horn_2").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
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
			ResourceLocation path = BuiltInRegistries.ITEM.getKey(stack.getItem().asItem());
			if (path.toString().matches("immersiveweapons:.+_ring")) {
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
			if (stack.getItem() == ItemRegistry.REINFORCED_DEPTH_CHARM.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.reinforced_depth_charm").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
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
			if (stack.getItem() == ItemRegistry.HOLY_MANTLE.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.holy_mantle_1").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.holy_mantle_2").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.holy_mantle_3").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			}
			if (stack.getItem() == ItemRegistry.VENSTRAL_JAR.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.venstral_jar").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
			}
			if (stack.getItem() == ItemRegistry.SUPER_BLANKET_CAPE.get()) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.super_blanket_cape_1").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.super_blanket_cape_2").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
			}

			addAccessoryTooltips(accessory, event.getEntity(), stack, event.getToolTip());
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
		// Regex check for "immersiveweapons:pedestal_augment_X" where X is any string
		ResourceLocation path = BuiltInRegistries.ITEM.getKey(stack.getItem().asItem());
		if (path.toString().matches("immersiveweapons:pedestal_augment_.+")) {
			// Get the name of the augment
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.%s".formatted(path.getPath())).withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.pedestal_augment.note").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}

		// Tiltros
		if (stack.getItem() == ItemRegistry.AZUL_KEYSTONE.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.azul_keystone").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
		}
		if (stack.getItem() == ItemRegistry.AZUL_KEYSTONE_FRAGMENT.get()) {
			event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.azul_keystone_fragment").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
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