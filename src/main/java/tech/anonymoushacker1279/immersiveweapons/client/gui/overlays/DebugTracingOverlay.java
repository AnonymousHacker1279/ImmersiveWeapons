package tech.anonymoushacker1279.immersiveweapons.client.gui.overlays;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.bullet.AbstractBulletItem;

public class DebugTracingOverlay {

	public static void renderOverlay(PoseStack poseStack, Font fontRenderer, int screenWidth, int screenHeight) {
		RenderSystem.depthMask(false);
		RenderSystem.setShaderColor(0.0f, 0.0f, 0.0f, 0.1f);

		// Render damage tracing metrics to the screen
		int textHeightPosition = screenHeight;

		if (DebugTracingData.meleeItemDamage > 0) {
			MutableComponent meleeItemDamage = Component.translatable("immersiveweapons.debugTracing.meleeItemDamage",
							DebugTracingData.meleeItemDamage)
					.withStyle(ChatFormatting.BOLD, ChatFormatting.RED);
			textHeightPosition -= 15;
			fontRenderer.draw(poseStack, meleeItemDamage, 5, textHeightPosition, 0xFFFFFF);
		}
		if (DebugTracingData.gunBaseVelocity > 0) {
			MutableComponent gunBaseVelocity = Component.translatable("immersiveweapons.debugTracing.gunBaseVelocity",
							DebugTracingData.gunBaseVelocity)
					.withStyle(ChatFormatting.BOLD, ChatFormatting.BLUE);
			textHeightPosition -= 15;
			fontRenderer.draw(poseStack, gunBaseVelocity, 5, textHeightPosition, 0xFFFFFF);
		}
		if (DebugTracingData.selectedAmmo instanceof AbstractBulletItem bullet) {
			MutableComponent selectedAmmo = Component.translatable("immersiveweapons.debugTracing.selectedAmmo",
							bullet.getDescription(),
							bullet.damage)
					.withStyle(ChatFormatting.BOLD, ChatFormatting.GREEN);
			textHeightPosition -= 15;
			fontRenderer.draw(poseStack, selectedAmmo, 5, textHeightPosition, 0xFFFFFF);
		}
		if (DebugTracingData.liveBulletDamage > 0) {
			MutableComponent liveBulletDamage = Component.translatable("immersiveweapons.debugTracing.liveBulletDamage",
							DebugTracingData.liveBulletDamage,
							DebugTracingData.isBulletCritical)
					.withStyle(ChatFormatting.BOLD, ChatFormatting.RED);
			textHeightPosition -= 15;
			fontRenderer.draw(poseStack, liveBulletDamage, 5, textHeightPosition, 0xFFFFFF);
		}

		RenderSystem.enableTexture();
		RenderSystem.depthMask(true);
		RenderSystem.enableDepthTest();
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	}
}