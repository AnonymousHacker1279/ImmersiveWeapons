package tech.anonymoushacker1279.immersiveweapons.client.gui.overlays;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import tech.anonymoushacker1279.immersiveweapons.event.game_effects.EnvironmentEffects;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.bullet.AbstractBulletItem;

import java.util.ArrayList;
import java.util.List;

public class DebugTracingOverlay {

	static final MutableComponent MELEE_ITEM_DAMAGE = Component.translatable("immersiveweapons.debugTracing.meleeItemDamage");
	static final MutableComponent GUN_BASE_VELOCITY = Component.translatable("immersiveweapons.debugTracing.gunBaseVelocity");
	static final MutableComponent SELECTED_AMMO = Component.translatable("immersiveweapons.debugTracing.selectedAmmo");
	static final MutableComponent LIVE_BULLET_DAMAGE = Component.translatable("immersiveweapons.debugTracing.liveBulletDamage");
	static final MutableComponent LAST_DAMAGE_DEALT = Component.translatable("immersiveweapons.debugTracing.lastDamageDealt");
	static final MutableComponent DAMAGE_BONUS = Component.translatable("immersiveweapons.debugTracing.damageBonus");
	static final MutableComponent CELESTIAL_PROTECTION_CHANCE_FOR_NO_DAMAGE = Component.translatable("immersiveweapons.debugTracing.celestialProtectionChanceForNoDamage");

	public static void renderOverlay(GuiGraphics guiGraphics, Font fontRenderer, int screenHeight) {
		float textHeightPosition = screenHeight;
		List<Component> overlayItems = new ArrayList<>(10);

		if (DebugTracingData.meleeItemDamage > 0) {
			MutableComponent meleeItemDamage = appendComponentData(MELEE_ITEM_DAMAGE, DebugTracingData.meleeItemDamage)
					.withStyle(ChatFormatting.BOLD, ChatFormatting.RED);

			overlayItems.add(meleeItemDamage);
		}
		if (DebugTracingData.gunBaseVelocity > 0) {
			MutableComponent gunBaseVelocity = appendComponentData(GUN_BASE_VELOCITY, DebugTracingData.gunBaseVelocity)
					.withStyle(ChatFormatting.BOLD, ChatFormatting.BLUE);

			overlayItems.add(gunBaseVelocity);
		}
		if (DebugTracingData.selectedAmmo instanceof AbstractBulletItem bullet) {
			MutableComponent selectedAmmo = appendComponentData(SELECTED_AMMO,
					bullet.toString(),
					bullet.damage)
					.withStyle(ChatFormatting.BOLD, ChatFormatting.GREEN);

			overlayItems.add(selectedAmmo);
		}
		if (DebugTracingData.liveBulletDamage > 0) {
			MutableComponent liveBulletDamage = appendComponentData(LIVE_BULLET_DAMAGE,
					DebugTracingData.liveBulletDamage,
					DebugTracingData.isBulletCritical)
					.withStyle(ChatFormatting.BOLD, ChatFormatting.RED);

			overlayItems.add(liveBulletDamage);
		}
		if (DebugTracingData.lastDamageDealt > 0) {
			MutableComponent lastDamageDealt = appendComponentData(LAST_DAMAGE_DEALT,
					DebugTracingData.lastDamageDealt)
					.withStyle(ChatFormatting.BOLD, ChatFormatting.GOLD);

			overlayItems.add(lastDamageDealt);
		}

		// Convert to a percent and round to nearest 0.1
		float generalDamageBonus = Math.round(DebugTracingData.GENERAL_DAMAGE_BONUS * 100.0f);
		float meleeDamageBonus = Math.round(DebugTracingData.MELEE_DAMAGE_BONUS * 100.0f);
		float projectileDamageBonus = Math.round(DebugTracingData.PROJECTILE_DAMAGE_BONUS * 100.0f);

		if ((generalDamageBonus + meleeDamageBonus + projectileDamageBonus) > 0) {
			MutableComponent damageBonus = appendComponentData(DAMAGE_BONUS,
					generalDamageBonus + "%",
					meleeDamageBonus + "%",
					projectileDamageBonus + "%")
					.withStyle(ChatFormatting.BOLD, ChatFormatting.GOLD);

			overlayItems.add(damageBonus);
		}

		if (EnvironmentEffects.celestialProtectionChanceForNoDamage > 0) {
			// Convert to a percent and round to nearest 0.01
			float noDamageChance = Math.round(EnvironmentEffects.celestialProtectionChanceForNoDamage * 100.0f);
			MutableComponent celestialProtectionChanceForNoDamage = appendComponentData(CELESTIAL_PROTECTION_CHANCE_FOR_NO_DAMAGE,
					noDamageChance + "%")
					.withStyle(ChatFormatting.BOLD, ChatFormatting.AQUA);

			overlayItems.add(celestialProtectionChanceForNoDamage);
		}

		for (Component component : overlayItems) {
			textHeightPosition -= 15;
			fontRenderer.drawInBatch(component, 5f, textHeightPosition, 0xFFFFFF, false,
					guiGraphics.pose().last().pose(),
					guiGraphics.bufferSource(),
					DisplayMode.NORMAL,
					0, 15728880);
		}
	}

	private static MutableComponent appendComponentData(MutableComponent component, Object... data) {
		return Component.literal(component.getString().formatted(data));
	}
}