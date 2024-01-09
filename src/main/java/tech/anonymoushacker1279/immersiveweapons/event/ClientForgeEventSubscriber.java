package tech.anonymoushacker1279.immersiveweapons.event;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.shaders.FogShape;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;
import net.neoforged.fml.common.Mod.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.event.ViewportEvent.ComputeFov;
import net.neoforged.neoforge.client.event.ViewportEvent.RenderFog;
import net.neoforged.neoforge.client.gui.overlay.ExtendedGui;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.IWKeyBinds;
import tech.anonymoushacker1279.immersiveweapons.client.gui.IWOverlays;
import tech.anonymoushacker1279.immersiveweapons.client.gui.overlays.DebugTracingData;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem;
import tech.anonymoushacker1279.immersiveweapons.item.CursedItem;
import tech.anonymoushacker1279.immersiveweapons.item.gun.data.GunData;

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
		Player player = event.getPlayer();

		// Remove fire overlay from players wearing a full set of molten armor
		if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistry.MOLTEN_HELMET.get() &&
				player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistry.MOLTEN_CHESTPLATE.get() &&
				player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemRegistry.MOLTEN_LEGGINGS.get() &&
				player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemRegistry.MOLTEN_BOOTS.get()) {
			if (player.isInLava()) {
				if (event.getBlockState() == Blocks.FIRE.defaultBlockState()) {
					event.setCanceled(true);
				}
			}
		}

		if (AccessoryItem.isAccessoryActive(player, ItemRegistry.LAVA_GOGGLES.get())) {
			if (player.isInLava()) {
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

		if (player == null) {
			return;
		}

		if (player.isInLava()) {
			boolean hasLavaGoggles = AccessoryItem.isAccessoryActive(player, ItemRegistry.LAVA_GOGGLES.get());
			if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistry.MOLTEN_HELMET.get() &&
					player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistry.MOLTEN_CHESTPLATE.get() &&
					player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemRegistry.MOLTEN_LEGGINGS.get() &&
					player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemRegistry.MOLTEN_BOOTS.get()) {

				if (minecraft.level != null) {
					BlockState state = minecraft.level.getBlockState(new BlockPos(player.blockPosition().above(1)));
					if (state.is(Blocks.LAVA)) {
						float modifier = hasLavaGoggles ? 1.5f : 1.0f;
						event.setNearPlaneDistance(16.0f * modifier);
						event.setFarPlaneDistance(32.0f * modifier);
						event.setCanceled(true);
					}
				}
			} else if (hasLavaGoggles) {
				if (minecraft.level != null) {
					BlockState state = minecraft.level.getBlockState(new BlockPos(player.blockPosition().above(1)));
					if (state.is(Blocks.LAVA)) {
						event.setNearPlaneDistance(8.0f);
						event.setFarPlaneDistance(16.0f);
						event.setCanceled(true);
					}
				}
			}
		}

		if (player.getItemInHand(player.getUsedItemHand()).getItem() instanceof CursedItem && player.isUsingItem()) {
			event.setNearPlaneDistance(0.0f);
			event.setFarPlaneDistance(Math.max(CursedItem.CURSE_EFFECT_FADE * 512, 16.0f));
			event.scaleFarPlaneDistance(0.5f);

			event.setFogShape(FogShape.SPHERE);
			event.setCanceled(true);
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

		// Handle FOV change of custom bows
		if (minecraft.player != null) {
			Item itemInHand = minecraft.player.getItemInHand(minecraft.player.getUsedItemHand()).getItem();
			if ((itemInHand == ItemRegistry.ICE_BOW.get()
					|| itemInHand == ItemRegistry.DRAGONS_BREATH_BOW.get()
					|| itemInHand == ItemRegistry.AURORA_BOW.get())
					&& minecraft.player.isUsingItem()) {

				double fov = event.getFOV();
				int useTicks = minecraft.player.getTicksUsingItem();
				float fovModifier = (float) useTicks / 20.0F;

				if (fovModifier > 1.0F) {
					fovModifier = 1.0F;
				} else {
					fovModifier *= fovModifier;
				}

				fov *= 1.0F - fovModifier * 0.15F;
				event.setFOV(fov);
			}
		}
	}

	@SubscribeEvent
	public static void renderGuiOverlayPostEvent(RenderGuiOverlayEvent.Post event) {
		int screenHeight = event.getWindow().getGuiScaledHeight();
		int screenWidth = event.getWindow().getGuiScaledWidth();

		if (GunData.changingPlayerFOV != -1) {
			if (minecraft.options.getCameraType().isFirstPerson()) {
				float deltaFrame = minecraft.getDeltaFrameTime() / 8;
				GunData.scopeScale = Mth.lerp(0.25F * deltaFrame, GunData.scopeScale, 1.125F);

				IWOverlays.SCOPE_ELEMENT.render((ExtendedGui) minecraft.gui,
						event.getGuiGraphics(),
						event.getPartialTick(),
						screenWidth,
						screenHeight);
			}
		}

		if (IWKeyBinds.DEBUG_TRACING.consumeClick()) {
			DebugTracingData.isDebugTracingEnabled = !DebugTracingData.isDebugTracingEnabled;
		}

		if (DebugTracingData.isDebugTracingEnabled) {
			IWOverlays.DEBUG_TRACING_ELEMENT.render((ExtendedGui) minecraft.gui,
					event.getGuiGraphics(),
					event.getPartialTick(),
					screenWidth,
					screenHeight);
		}
	}

	@SubscribeEvent
	public static void keyInputEvent(InputEvent.Key event) {
		// Double jump ability with the Venstral Jar accessory
		Player player = minecraft.player;
		if (player != null && AccessoryItem.isAccessoryActive(player, ItemRegistry.VENSTRAL_JAR.get())) {
			// Check if the jump key is pressed
			if (event.getKey() == minecraft.options.keyJump.getKey().getValue() && event.getAction() == InputConstants.PRESS) {
				if (!player.onGround()) {
					Vec3 deltaMovement = player.getDeltaMovement();
					float jumpVelocity = 0.42f * (1 + player.getJumpBoostPower());
					player.setDeltaMovement(deltaMovement.add(deltaMovement.x, (Math.abs(deltaMovement.y) + jumpVelocity), deltaMovement.z));

					// Spawn cloud particles
					for (int i = 0; i < 8; ++i) {
						float x = player.getRandom().nextFloat() - player.getRandom().nextFloat();
						float z = player.getRandom().nextFloat() - player.getRandom().nextFloat();
						float y = player.getRandom().nextFloat() - player.getRandom().nextFloat();
						player.level().addParticle(ParticleTypes.CLOUD,
								player.getX() + x,
								player.getY() + y,
								player.getZ() + z,
								0.0D, 0.0D, 0.0D);
					}

					player.getCooldowns().addCooldown(ItemRegistry.VENSTRAL_JAR.get(), 1200);
				}
			}
		}
	}
}