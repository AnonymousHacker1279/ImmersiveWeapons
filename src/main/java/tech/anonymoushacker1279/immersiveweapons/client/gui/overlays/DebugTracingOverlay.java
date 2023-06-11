package tech.anonymoushacker1279.immersiveweapons.client.gui.overlays;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.joml.Matrix4f;
import tech.anonymoushacker1279.immersiveweapons.event.game_effects.EnvironmentEffects;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.bullet.AbstractBulletItem;

import java.util.ArrayList;
import java.util.List;

public class DebugTracingOverlay {

	public static void renderOverlay(PoseStack poseStack, Font fontRenderer, int screenHeight) {
		// Render damage tracing metrics to the screen

		float textHeightPosition = screenHeight;
		List<Component> overlayItems = new ArrayList<>(5);

		if (DebugTracingData.meleeItemDamage > 0) {
			MutableComponent meleeItemDamage = Component.translatable("immersiveweapons.debugTracing.meleeItemDamage",
							DebugTracingData.meleeItemDamage)
					.withStyle(ChatFormatting.BOLD, ChatFormatting.RED);
			overlayItems.add(meleeItemDamage);
		}
		if (DebugTracingData.gunBaseVelocity > 0) {
			MutableComponent gunBaseVelocity = Component.translatable("immersiveweapons.debugTracing.gunBaseVelocity",
							DebugTracingData.gunBaseVelocity)
					.withStyle(ChatFormatting.BOLD, ChatFormatting.BLUE);
			overlayItems.add(gunBaseVelocity);
		}
		if (DebugTracingData.selectedAmmo instanceof AbstractBulletItem bullet) {
			MutableComponent selectedAmmo = Component.translatable("immersiveweapons.debugTracing.selectedAmmo",
							bullet.getDescription(),
							bullet.damage)
					.withStyle(ChatFormatting.BOLD, ChatFormatting.GREEN);
			overlayItems.add(selectedAmmo);
		}
		if (DebugTracingData.liveBulletDamage > 0) {
			MutableComponent liveBulletDamage = Component.translatable("immersiveweapons.debugTracing.liveBulletDamage",
							DebugTracingData.liveBulletDamage,
							DebugTracingData.isBulletCritical)
					.withStyle(ChatFormatting.BOLD, ChatFormatting.RED);
			overlayItems.add(liveBulletDamage);
		}
		if (DebugTracingData.lastDamageDealt > 0) {
			MutableComponent lastDamageDealt = Component.translatable("immersiveweapons.debugTracing.lastDamageDealt",
							DebugTracingData.lastDamageDealt)
					.withStyle(ChatFormatting.BOLD, ChatFormatting.GOLD);
			overlayItems.add(lastDamageDealt);
		}

		// Convert to a percent and round to nearest 0.1
		float generalDamageBonus = Math.round(DebugTracingData.GENERAL_DAMAGE_BONUS * 1000.0f) / 10.0f;
		float meleeDamageBonus = Math.round(DebugTracingData.MELEE_DAMAGE_BONUS * 1000.0f) / 10.0f;
		float projectileDamageBonus = Math.round(DebugTracingData.PROJECTILE_DAMAGE_BONUS * 1000.0f) / 10.0f;

		if ((generalDamageBonus + meleeDamageBonus + projectileDamageBonus) > 0) {
			MutableComponent damageBonus = Component.translatable("immersiveweapons.debugTracing.damageBonus",
							generalDamageBonus + "%",
							meleeDamageBonus + "%",
							projectileDamageBonus + "%")
					.withStyle(ChatFormatting.BOLD, ChatFormatting.GOLD);
			overlayItems.add(damageBonus);
		}

		if (EnvironmentEffects.celestialProtectionChanceForNoDamage > 0) {
			// Convert to a percent and round to nearest 0.01
			float noDamageChance = Math.round(EnvironmentEffects.celestialProtectionChanceForNoDamage * 10000.0f) / 100.0f;
			MutableComponent celestialProtectionChanceForNoDamage = Component.translatable("immersiveweapons.debugTracing.celestialProtectionChanceForNoDamage",
							noDamageChance + "%")
					.withStyle(ChatFormatting.BOLD, ChatFormatting.AQUA);
			overlayItems.add(celestialProtectionChanceForNoDamage);
		}

		MultiBufferSource.BufferSource buffer = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
		Matrix4f matrix4f = poseStack.last().pose();
		for (Component component : overlayItems) {
			textHeightPosition -= 15;
			fontRenderer.drawInBatch(component, 5f, textHeightPosition, 0xFFFFFF, false, matrix4f, buffer, DisplayMode.NORMAL, 0, 15728880);
		}
		buffer.endBatch();
	}
}