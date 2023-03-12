package tech.anonymoushacker1279.immersiveweapons.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.gui.overlays.DebugTracingOverlay;
import tech.anonymoushacker1279.immersiveweapons.client.gui.overlays.ScopeOverlay;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.gun.data.GunData;

public class IWOverlays {

	public static final ResourceLocation SCOPE_LOCATION = new ResourceLocation(ImmersiveWeapons.MOD_ID + ":textures/gui/overlay/musket_scope.png");

	@Nullable
	public static IGuiOverlay SCOPE_ELEMENT;
	@Nullable
	public static IGuiOverlay DEBUG_TRACING_ELEMENT;

	public static void init() {
		ImmersiveWeapons.LOGGER.info("Initializing overlays");

		Minecraft minecraft = Minecraft.getInstance();
		Font fontRenderer = minecraft.font;

		SCOPE_ELEMENT = (gui, poseStack, partialTick, screenWidth, screenHeight) -> {
			gui.setupOverlayRenderState(true, false);

			if (GunData.changingPlayerFOV != -1 && minecraft.options.getCameraType().isFirstPerson()) {
				ScopeOverlay.renderOverlay(screenWidth, screenHeight, GunData.scopeScale);
			}
		};

		DEBUG_TRACING_ELEMENT = (gui, poseStack, partialTick, screenWidth, screenHeight) -> {
			gui.setupOverlayRenderState(true, false);
			DebugTracingOverlay.renderOverlay(poseStack, fontRenderer, screenHeight);
		};
	}
}