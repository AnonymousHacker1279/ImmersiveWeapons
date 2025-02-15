package tech.anonymoushacker1279.immersiveweapons.client.tooltip;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import tech.anonymoushacker1279.immersiveweapons.init.DataComponentTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.gauntlet.GauntletItem;
import tech.anonymoushacker1279.immersiveweapons.item.gun.AbstractGunItem;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.BulletItem;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.CustomArrowItem;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.ThrowableItem;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.ArrayList;
import java.util.List;

import static tech.anonymoushacker1279.immersiveweapons.client.TooltipHandler.addShiftTooltip;

public class DynamicTooltips {

	public static class FirearmTooltip implements DynamicTooltip {

		@Override
		public void computeTooltip(ItemTooltipEvent event) {
			ItemStack stack = event.getItemStack();
			if (stack.getItem() instanceof AbstractGunItem abstractGunItem && event.getEntity() != null) {
				List<Component> shiftTooltipInfo = new ArrayList<>(10);
				shiftTooltipInfo.add(CommonComponents.EMPTY);

				shiftTooltipInfo.add(Component.translatable("tooltip.immersiveweapons.gun.meta.base_velocity", abstractGunItem.getBaseFireVelocity()).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));

				int reloadTime = abstractGunItem.getCooldown() / 20;
				shiftTooltipInfo.add(Component.translatable("tooltip.immersiveweapons.gun.meta.base_reload_time", reloadTime).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));

				if (abstractGunItem.getKnockbackLevel() > 0) {
					shiftTooltipInfo.add(Component.translatable("tooltip.immersiveweapons.gun.meta.base_knockback", abstractGunItem.getKnockbackLevel()).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
				}

				AbstractGunItem.PowderType powderType = abstractGunItem.findPowder(stack, event.getEntity());

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
		}
	}

	public static class BulletTooltip implements DynamicTooltip {

		@Override
		public void computeTooltip(ItemTooltipEvent event) {
			ItemStack stack = event.getItemStack();
			if (stack.getItem() instanceof BulletItem<?> bullet) {
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
		}
	}

	public static class SmokeGrenadeArrowTooltip implements DynamicTooltip {

		@Override
		public void computeTooltip(ItemTooltipEvent event) {
			ItemStack stack = event.getItemStack();
			if (stack.getItem() instanceof CustomArrowItem<?> arrow) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.smoke_grenade_arrow").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));

				if (arrow.color > 0) {
					// The last word in the name is the color
					String color = arrow.toString().substring(arrow.toString().lastIndexOf("_") + 1).toLowerCase();
					event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.smoke_grenade_" + color).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
				}
			}
		}

		@Override
		public boolean shouldComputeSimpleTooltips(ItemTooltipEvent event) {
			return false;
		}
	}

	public static class ThrowableItemTooltip implements DynamicTooltip {

		@Override
		public void computeTooltip(ItemTooltipEvent event) {
			ItemStack stack = event.getItemStack();
			if (stack.getItem() instanceof ThrowableItem grenade) {
				if (grenade.type == ThrowableItem.ThrowableType.SMOKE_GRENADE) {
					event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.smoke_grenade").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));

					if (grenade.color > 0) {
						// The last word in the name is the color
						String color = grenade.toString().substring(grenade.toString().lastIndexOf("_") + 1).toLowerCase();
						event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.smoke_grenade_" + color).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
					}
				}
			}
		}

		@Override
		public boolean shouldComputeSimpleTooltips(ItemTooltipEvent event) {
			return event.getItemStack().getItem() instanceof ThrowableItem throwableItem && throwableItem.type != ThrowableItem.ThrowableType.SMOKE_GRENADE;
		}
	}

	public static class GauntletTooltip implements DynamicTooltip {

		@Override
		public void computeTooltip(ItemTooltipEvent event) {
			ItemStack stack = event.getItemStack();
			if (stack.getItem() instanceof GauntletItem gauntlet) {
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.gauntlet_1").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
				event.getToolTip().add(Component.translatable("tooltip.immersiveweapons.gauntlet_2",
								(Math.round(gauntlet.getBleedChance() * 100)),
								GeneralUtilities.convertToRoman(gauntlet.getBleedLevel() + 1))
						.withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
			}
		}

		@Override
		public boolean shouldComputeSimpleTooltips(ItemTooltipEvent event) {
			return false;
		}
	}
}