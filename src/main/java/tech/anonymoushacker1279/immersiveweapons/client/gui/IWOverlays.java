package tech.anonymoushacker1279.immersiveweapons.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.gui.overlays.*;
import tech.anonymoushacker1279.immersiveweapons.item.gun.data.GunData;

public class IWOverlays {

	public static final ResourceLocation SCOPE_LOCATION = new ResourceLocation(ImmersiveWeapons.MOD_ID + ":textures/gui/overlay/musket_scope.png");

	private static final Minecraft MINECRAFT = Minecraft.getInstance();
	private static final Font FONT_RENDERER = MINECRAFT.font;

	public static final LayeredDraw.Layer SCOPE_ELEMENT = (gui, partialTick) -> {
		if (GunData.changingPlayerFOV != -1 && MINECRAFT.options.getCameraType().isFirstPerson()) {
			ScopeOverlay.renderOverlay(gui.guiWidth(), gui.guiHeight(), GunData.scopeScale);
		}
	};

	public static final LayeredDraw.Layer DEBUG_TRACING_ELEMENT = (gui, partialTick) -> {
		if (DebugTracingData.isDebugTracingEnabled) {
			DebugTracingOverlay.renderOverlay(gui, FONT_RENDERER, gui.guiHeight());
		}
	};
}