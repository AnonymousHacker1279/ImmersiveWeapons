package tech.anonymoushacker1279.immersiveweapons.client.gui.overlays;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.bullet.AbstractBulletItem;

import java.util.ArrayList;
import java.util.List;

public class DebugTracingOverlay {

	private static final String ARMOR_VALUES = Component.translatable("immersiveweapons.debugTracing.armorValues").getString();
	private static final String MELEE_ITEM_DAMAGE = Component.translatable("immersiveweapons.debugTracing.meleeItemDamage").getString();
	private static final String GUN_BASE_VELOCITY = Component.translatable("immersiveweapons.debugTracing.gunBaseVelocity").getString();
	private static final String SELECTED_AMMO = Component.translatable("immersiveweapons.debugTracing.selectedAmmo").getString();
	private static final String LIVE_BULLET_DAMAGE = Component.translatable("immersiveweapons.debugTracing.liveBulletDamage").getString();
	private static final String LAST_DAMAGE_VALUES = Component.translatable("immersiveweapons.debugTracing.lastDamageValues").getString();
	private static final String DAMAGE_BONUS = Component.translatable("immersiveweapons.debugTracing.damageBonus").getString();
	private static final String DR_AND_KBR = Component.translatable("immersiveweapons.debugTracing.drAndKbr").getString();
	private static final String CELESTIAL_PROTECTION_CHANCE_FOR_NO_DAMAGE = Component.translatable("immersiveweapons.debugTracing.celestialProtectionChanceForNoDamage").getString();

	public static void renderOverlay(GuiGraphics guiGraphics, Font fontRenderer, int screenHeight) {
		int textHeightPosition = screenHeight;
		List<String> overlayItems = new ArrayList<>(10);

		if (DebugTracingData.ARMOR_VALUE > 0 || DebugTracingData.ARMOR_TOUGHNESS_VALUE > 0) {
			String armorValues = appendData(ARMOR_VALUES,
					DebugTracingData.ARMOR_VALUE,
					Math.round(DebugTracingData.ARMOR_TOUGHNESS_VALUE * 10f) / 10f);

			overlayItems.add(armorValues);
		}
		if (DebugTracingData.GENERAL_DAMAGE_RESISTANCE + DebugTracingData.KNOCKBACK_RESISTANCE != 0) {
			String drAndKbr = appendData(DR_AND_KBR,
					Math.round(DebugTracingData.GENERAL_DAMAGE_RESISTANCE * 100) + "%",
					Math.round(DebugTracingData.KNOCKBACK_RESISTANCE * 100) + "%");

			overlayItems.add(drAndKbr);
		}
		if (DebugTracingData.meleeItemDamage > 0) {
			String meleeItemDamage = appendData(MELEE_ITEM_DAMAGE, DebugTracingData.meleeItemDamage);

			overlayItems.add(meleeItemDamage);
		}
		if (DebugTracingData.gunBaseVelocity > 0) {
			String gunBaseVelocity = appendData(GUN_BASE_VELOCITY, DebugTracingData.gunBaseVelocity);

			overlayItems.add(gunBaseVelocity);
		}
		if (DebugTracingData.selectedAmmo instanceof AbstractBulletItem bullet) {
			String selectedAmmo = appendData(SELECTED_AMMO,
					bullet.toString(),
					bullet.damage);

			overlayItems.add(selectedAmmo);
		}
		if (DebugTracingData.liveBulletDamage > 0) {
			String liveBulletDamage = appendData(LIVE_BULLET_DAMAGE,
					DebugTracingData.liveBulletDamage,
					DebugTracingData.isBulletCritical);

			overlayItems.add(liveBulletDamage);
		}
		if (DebugTracingData.lastDamageDealt + DebugTracingData.lastDamageTaken > 0) {
			String lastDamageDealt = appendData(LAST_DAMAGE_VALUES,
					Math.round(DebugTracingData.lastDamageDealt),
					Math.round(DebugTracingData.lastDamageTaken));

			overlayItems.add(lastDamageDealt);
		}

		// Convert to a percent and round to nearest 0.1
		float generalDamageBonus = Math.round(DebugTracingData.GENERAL_DAMAGE_BONUS * 100);
		float meleeDamageBonus = Math.round(DebugTracingData.MELEE_DAMAGE_BONUS * 100);
		float projectileDamageBonus = Math.round(DebugTracingData.PROJECTILE_DAMAGE_BONUS * 100);

		if ((generalDamageBonus + meleeDamageBonus + projectileDamageBonus) > 0) {
			String damageBonus = appendData(DAMAGE_BONUS,
					generalDamageBonus + "%",
					meleeDamageBonus + "%",
					projectileDamageBonus + "%");

			overlayItems.add(damageBonus);
		}

		if (DebugTracingData.CELESTIAL_PROTECTION_NO_DAMAGE_CHANCE > 0) {
			// Convert to a percent and round to nearest 0.01
			float noDamageChance = Math.round(DebugTracingData.CELESTIAL_PROTECTION_NO_DAMAGE_CHANCE * 100);
			String celestialProtectionChanceForNoDamage = appendData(CELESTIAL_PROTECTION_CHANCE_FOR_NO_DAMAGE,
					noDamageChance + "%");

			overlayItems.add(celestialProtectionChanceForNoDamage);
		}

		for (String str : overlayItems) {
			textHeightPosition -= 15;
			fontRenderer.drawInBatch(str, 5f, textHeightPosition, 0xFFFFFF, false,
					guiGraphics.pose().last().pose(),
					guiGraphics.bufferSource(),
					DisplayMode.NORMAL,
					0, 15728880);
		}
	}

	private static String appendData(String component, Object... data) {
		return component.formatted(data);
	}
}