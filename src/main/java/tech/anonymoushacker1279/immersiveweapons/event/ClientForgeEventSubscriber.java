package tech.anonymoushacker1279.immersiveweapons.event;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent.FieldOfView;
import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.block.crafting.small_parts.SmallPartsCraftables;
import tech.anonymoushacker1279.immersiveweapons.client.gui.IWOverlays;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.gun.data.GunData;

@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeEventSubscriber {

	final static Minecraft minecraft = Minecraft.getInstance();

	/**
	 * Event handler for the RenderBlockOverlayEvent.
	 *
	 * @param event the <code>RenderBlockOverlayEvent</code> instance
	 */
	@SubscribeEvent
	public static void renderBlockOverlayEvent(RenderBlockOverlayEvent event) {
		// Remove fire overlay from players wearing a full set of molten armor
		if (event.getPlayer().getItemBySlot(EquipmentSlot.HEAD).getItem() == DeferredRegistryHandler.MOLTEN_HELMET.get() &&
				event.getPlayer().getItemBySlot(EquipmentSlot.CHEST).getItem() == DeferredRegistryHandler.MOLTEN_CHESTPLATE.get() &&
				event.getPlayer().getItemBySlot(EquipmentSlot.LEGS).getItem() == DeferredRegistryHandler.MOLTEN_LEGGINGS.get() &&
				event.getPlayer().getItemBySlot(EquipmentSlot.FEET).getItem() == DeferredRegistryHandler.MOLTEN_BOOTS.get()) {
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
	public static void fogDensityEvent(RenderFogEvent event) {
		// Reduce lava fog from players wearing a full set of molten armor
		Player player = minecraft.player;
		if (player != null && player.getItemBySlot(EquipmentSlot.HEAD).getItem() == DeferredRegistryHandler.MOLTEN_HELMET.get() &&
				player.getItemBySlot(EquipmentSlot.CHEST).getItem() == DeferredRegistryHandler.MOLTEN_CHESTPLATE.get() &&
				player.getItemBySlot(EquipmentSlot.LEGS).getItem() == DeferredRegistryHandler.MOLTEN_LEGGINGS.get() &&
				player.getItemBySlot(EquipmentSlot.FEET).getItem() == DeferredRegistryHandler.MOLTEN_BOOTS.get()) {
			if (player.isInLava()) {
				if (minecraft.level != null) {
					BlockState state = minecraft.level.getBlockState(player.eyeBlockPosition());
					if (state.is(Blocks.LAVA)) {
						event.setNearPlaneDistance(16.0f);
						event.setFarPlaneDistance(32.0f);
						event.setCanceled(true);
					}
				}
			}
		}
	}

	/**
	 * Event handler for the RecipesUpdatedEvent event.
	 *
	 * @param event the <code>RecipesUpdatedEvent</code> instance
	 */
	@SubscribeEvent
	public static void recipesUpdatedEvent(RecipesUpdatedEvent event) {
		ImmersiveWeapons.LOGGER.info("Recipes have updated, re-initializing custom crafting systems");
		SmallPartsCraftables.init(event.getRecipeManager());
	}

	@SubscribeEvent
	public static void playerFOVEvent(FieldOfView event) {
		if (event.getFOV() != 70) {
			GunData.playerFOV = event.getFOV();
		}
		if (GunData.changingPlayerFOV != -1 && minecraft.options.getCameraType().isFirstPerson()) {
			event.setFOV(GunData.changingPlayerFOV);
		}
	}

	@SubscribeEvent
	public static void renderOverlayEvent(RenderGameOverlayEvent.Post event) {
		if (GunData.changingPlayerFOV != -1 && event.getType() == ElementType.LAYER) {
			if (minecraft.options.getCameraType().isFirstPerson()) {
				int screenHeight = event.getWindow().getGuiScaledHeight();
				int screenWidth = event.getWindow().getGuiScaledWidth();

				float deltaFrame = minecraft.getDeltaFrameTime() / 8;
				GunData.scopeScale = Mth.lerp(0.25F * deltaFrame, GunData.scopeScale, 1.125F);

				IWOverlays.SCOPE_ELEMENT.render((ForgeIngameGui) minecraft.gui,
						event.getMatrixStack(),
						event.getPartialTicks(),
						screenWidth,
						screenHeight);
			}
		}
	}
}