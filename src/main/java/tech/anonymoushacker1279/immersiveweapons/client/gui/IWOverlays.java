package tech.anonymoushacker1279.immersiveweapons.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.gui.overlay.IGuiOverlay;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.gui.overlays.*;
import tech.anonymoushacker1279.immersiveweapons.item.gun.data.GunData;

public class IWOverlays {

	public static final ResourceLocation SCOPE_LOCATION = new ResourceLocation(ImmersiveWeapons.MOD_ID + ":textures/gui/overlay/musket_scope.png");

	private static final Minecraft MINECRAFT = Minecraft.getInstance();
	private static final Font FONT_RENDERER = MINECRAFT.font;

	public static final IGuiOverlay SCOPE_ELEMENT = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
		gui.setupOverlayRenderState(true, false);

		if (GunData.changingPlayerFOV != -1 && MINECRAFT.options.getCameraType().isFirstPerson()) {
			ScopeOverlay.renderOverlay(screenWidth, screenHeight, GunData.scopeScale);
		}
	};

	public static final IGuiOverlay DEBUG_TRACING_ELEMENT = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
		gui.setupOverlayRenderState(true, false);

		if (DebugTracingData.isDebugTracingEnabled) {
			DebugTracingOverlay.renderOverlay(guiGraphics, FONT_RENDERER, screenHeight);
		}
	};
}