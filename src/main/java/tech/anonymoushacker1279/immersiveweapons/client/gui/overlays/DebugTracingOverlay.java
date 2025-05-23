package tech.anonymoushacker1279.immersiveweapons.client.gui.overlays;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class DebugTracingOverlay {

	private static final String ARMOR_VALUES = Component.translatable("immersiveweapons.debug_tracing.armor_values").getString();
	private static final String GUN_BASE_VELOCITY = Component.translatable("immersiveweapons.debug_tracing.gun_base_velocity").getString();
	private static final String LIVE_BULLET_DAMAGE = Component.translatable("immersiveweapons.debug_tracing.live_bullet_damage").getString();
	private static final String LAST_DAMAGE_VALUES = Component.translatable("immersiveweapons.debug_tracing.last_damage_values").getString();
	private static final String DAMAGE_BONUS = Component.translatable("immersiveweapons.debug_tracing.damage_bonus").getString();
	private static final String DR_AND_KBR = Component.translatable("immersiveweapons.debug_tracing.dr_and_kbr").getString();
	private static final String CELESTIAL_PROTECTION_CHANCE_FOR_NO_DAMAGE = Component.translatable("immersiveweapons.debug_tracing.celestial_protection_chance_for_no_damage").getString();

	public static void renderOverlay(GuiGraphics guiGraphics, Font fontRenderer, int screenHeight) {
		int textHeightPosition = screenHeight;
		List<String> overlayItems = new ArrayList<>(10);

		if (DebugTracingData.ARMOR_VALUE > 0 || DebugTracingData.ARMOR_TOUGHNESS_VALUE > 0) {
			String armorValues = appendData(ARMOR_VALUES,
					DebugTracingData.ARMOR_VALUE,
					Math.round(DebugTracingData.ARMOR_TOUGHNESS_VALUE * 10f) / 10f);

			overlayItems.add(armorValues);
		}
		if (DebugTracingData.GENERAL_DAMAGE_RESISTANCE != 0 || DebugTracingData.KNOCKBACK_RESISTANCE != 0) {
			String drAndKbr = appendData(DR_AND_KBR,
					Math.round(DebugTracingData.GENERAL_DAMAGE_RESISTANCE * 100) + "%",
					Math.round(DebugTracingData.KNOCKBACK_RESISTANCE * 100) + "%");

			overlayItems.add(drAndKbr);
		}
		if (DebugTracingData.gunBaseVelocity > 0) {
			String gunBaseVelocity = appendData(GUN_BASE_VELOCITY, DebugTracingData.gunBaseVelocity);

			overlayItems.add(gunBaseVelocity);
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
			guiGraphics.drawString(
					fontRenderer,
					str,
					5f,
					textHeightPosition,
					0xFFFFFF,
					false
			);
		}
	}

	private static String appendData(String component, Object... data) {
		return component.formatted(data);
	}
}