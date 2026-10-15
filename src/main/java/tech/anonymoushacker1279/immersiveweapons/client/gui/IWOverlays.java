package tech.anonymoushacker1279.immersiveweapons.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.neoforged.neoforge.client.gui.GuiLayer;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.GunAimClient;
import tech.anonymoushacker1279.immersiveweapons.client.gui.overlays.ScopeOverlay;
import tech.anonymoushacker1279.immersiveweapons.init.DataComponentTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.gun.AbstractGunItem;

public class IWOverlays {

	public static final Identifier SCOPE_LOCATION = Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "textures/gui/overlay/musket_scope.png");

	private static final Minecraft MINECRAFT = Minecraft.getInstance();
	private static float scopeScale = 0f;

	public static final GuiLayer SCOPE_ELEMENT = (gui, deltaTracker) -> {
		if (MINECRAFT.player == null || !MINECRAFT.options.getCameraType().isFirstPerson()) {
			return;
		}

		InteractionHand gunHand = AbstractGunItem.getGunHand(MINECRAFT.player);
		boolean scoped = gunHand != null
				&& GunAimClient.isAiming()
				&& MINECRAFT.player.getItemInHand(gunHand).get(DataComponentTypeRegistry.SCOPE) != null;

		if (scoped) {
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
}