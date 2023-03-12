package tech.anonymoushacker1279.immersiveweapons.event;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderBlockScreenEffectEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.ViewportEvent.ComputeFov;
import net.minecraftforge.client.event.ViewportEvent.RenderFog;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.IWKeyBinds;
import tech.anonymoushacker1279.immersiveweapons.client.gui.IWOverlays;
import tech.anonymoushacker1279.immersiveweapons.client.gui.overlays.DebugTracingData;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.gun.data.GunData;

@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeEventSubscriber {

	private static final Minecraft minecraft = Minecraft.getInstance();

	/**
	 * Event handler for the RenderBlockScreenEffectEvent.
	 *
	 * @param event the <code>RenderBlockScreenEffectEvent</code> instance
	 */
	@SubscribeEvent
	public static void renderBlockScreenEffectEvent(RenderBlockScreenEffectEvent event) {
		// Remove fire overlay from players wearing a full set of molten armor
		if (event.getPlayer().getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistry.MOLTEN_HELMET.get() &&
				event.getPlayer().getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistry.MOLTEN_CHESTPLATE.get() &&
				event.getPlayer().getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemRegistry.MOLTEN_LEGGINGS.get() &&
				event.getPlayer().getItemBySlot(EquipmentSlot.FEET).getItem() == ItemRegistry.MOLTEN_BOOTS.get()) {
			if (event.getPlayer().isInLava()) {
				if (event.getBlockState() == Blocks.FIRE.defaultBlockState()) {
					event.setCanceled(true);
				}
			}
		}
	}

	/**
	 * Event handler for the RenderFogEvent event.
	 *
	 * @param event the <code>RenderFogEvent</code> instance
	 */
	@SubscribeEvent
	public static void renderFogEvent(RenderFog event) {
		// Reduce lava fog from players wearing a full set of molten armor
		Player player = minecraft.player;
		if (player != null && player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistry.MOLTEN_HELMET.get() &&
				player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistry.MOLTEN_CHESTPLATE.get() &&
				player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemRegistry.MOLTEN_LEGGINGS.get() &&
				player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemRegistry.MOLTEN_BOOTS.get()) {
			if (player.isInLava()) {
				if (minecraft.level != null) {
					BlockState state = minecraft.level.getBlockState(new BlockPos(player.blockPosition().above(1)));
					if (state.is(Blocks.LAVA)) {
						event.setNearPlaneDistance(16.0f);
						event.setFarPlaneDistance(32.0f);
						event.setCanceled(true);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void computeFovEvent(ComputeFov event) {
		if (event.getFOV() != 70) {
			GunData.playerFOV = event.getFOV();
		}
		if (GunData.changingPlayerFOV != -1 && minecraft.options.getCameraType().isFirstPerson()) {
			event.setFOV(GunData.changingPlayerFOV);
		}
	}


	@SubscribeEvent
	public static void RenderGuiOverlayPostEvent(RenderGuiOverlayEvent.Post event) {
		int screenHeight = event.getWindow().getGuiScaledHeight();
		int screenWidth = event.getWindow().getGuiScaledWidth();

		if (GunData.changingPlayerFOV != -1) {
			if (minecraft.options.getCameraType().isFirstPerson()) {
				float deltaFrame = minecraft.getDeltaFrameTime() / 8;
				GunData.scopeScale = Mth.lerp(0.25F * deltaFrame, GunData.scopeScale, 1.125F);

				if (IWOverlays.SCOPE_ELEMENT != null) {
					IWOverlays.SCOPE_ELEMENT.render((ForgeGui) minecraft.gui,
							event.getPoseStack(),
							event.getPartialTick(),
							screenWidth,
							screenHeight);
				}
			}
		}

		if (IWKeyBinds.DEBUG_TRACING.consumeClick()) {
			DebugTracingData.isDebugTracingEnabled = !DebugTracingData.isDebugTracingEnabled;
		}

		if (DebugTracingData.isDebugTracingEnabled) {
			if (IWOverlays.DEBUG_TRACING_ELEMENT != null) {
				IWOverlays.DEBUG_TRACING_ELEMENT.render((ForgeGui) minecraft.gui,
						event.getPoseStack(),
						event.getPartialTick(),
						screenWidth,
						screenHeight);
			}
		}
	}
}