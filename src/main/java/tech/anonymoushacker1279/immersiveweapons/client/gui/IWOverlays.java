package tech.anonymoushacker1279.immersiveweapons.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.gui.overlays.DebugTracingData;
import tech.anonymoushacker1279.immersiveweapons.client.gui.overlays.DebugTracingOverlay;
import tech.anonymoushacker1279.immersiveweapons.client.gui.overlays.ScopeOverlay;
import tech.anonymoushacker1279.immersiveweapons.init.DataComponentTypeRegistry;

public class IWOverlays {

	public static final ResourceLocation SCOPE_LOCATION = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/gui/overlay/musket_scope.png");

	private static final Minecraft MINECRAFT = Minecraft.getInstance();
	private static final Font FONT_RENDERER = MINECRAFT.font;
	private static float scopeScale = 0f;

	public static final LayeredDraw.Layer SCOPE_ELEMENT = (gui, deltaTracker) -> {
		if (MINECRAFT.player == null || !MINECRAFT.options.getCameraType().isFirstPerson()) {
			return;
		}

		if (MINECRAFT.player.getUseItem().get(DataComponentTypeRegistry.SCOPE) != null) {
			scopeScale = Mth.lerp(0.25f * deltaTracker.getGameTimeDeltaTicks(), scopeScale, 1.125f);
		} else {
			scopeScale = Mth.lerp(2.25f * deltaTracker.getGameTimeDeltaTicks(), scopeScale, 0.0f);
		}

		if (scopeScale < 0.01f) {
			scopeScale = 0f;
		}

		if (scopeScale > 0f) {
			ScopeOverlay.renderOverlay(gui, gui.guiWidth(), gui.guiHeight(), scopeScale);
		}
	};

	public static final LayeredDraw.Layer DEBUG_TRACING_ELEMENT = (gui, deltaTracker) -> {
		if (DebugTracingData.isDebugTracingEnabled) {
			DebugTracingOverlay.renderOverlay(gui, FONT_RENDERER, gui.guiHeight());
		}
	};
}