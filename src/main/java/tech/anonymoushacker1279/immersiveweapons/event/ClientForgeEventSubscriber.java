package tech.anonymoushacker1279.immersiveweapons.event;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.shaders.FogShape;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;
import net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.event.ViewportEvent.ComputeFogColor;
import net.neoforged.neoforge.client.event.ViewportEvent.ComputeFov;
import net.neoforged.neoforge.client.event.ViewportEvent.RenderFog;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.IWKeyBinds;
import tech.anonymoushacker1279.immersiveweapons.client.gui.IWOverlays;
import tech.anonymoushacker1279.immersiveweapons.client.gui.overlays.DebugTracingData;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;
import tech.anonymoushacker1279.immersiveweapons.init.DataComponentTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.CursedItem;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.Accessory;
import tech.anonymoushacker1279.immersiveweapons.item.armor.ArmorUtils;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.ThrowableItem;
import tech.anonymoushacker1279.immersiveweapons.menu.SmallPartsMenu;
import tech.anonymoushacker1279.immersiveweapons.menu.StarForgeMenu;

@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = Bus.GAME, value = Dist.CLIENT)
public class ClientForgeEventSubscriber {


	/**
	 * Event handler for the RenderBlockScreenEffectEvent.
	 *
	 * @param event the <code>RenderBlockScreenEffectEvent</code> instance
	 */
	@SubscribeEvent
	public static void renderBlockScreenEffectEvent(RenderBlockScreenEffectEvent event) {
		Player player = event.getPlayer();

		// Remove fire overlay from players wearing a full set of molten armor
		if (ArmorUtils.isWearingMoltenArmor(player)) {
			if (player.isInLava()) {
				if (event.getBlockState() == Blocks.FIRE.defaultBlockState()) {
					event.setCanceled(true);
				}
			}
		}

		if (Accessory.isAccessoryActive(player, ItemRegistry.LAVA_GOGGLES.get())) {
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
		Player player = Minecraft.getInstance().player;
		Level level = Minecraft.getInstance().level;

		if (player == null) {
			return;
		}

		if (player.isInLava()) {
			boolean hasLavaGoggles = Accessory.isAccessoryActive(player, ItemRegistry.LAVA_GOGGLES.get());
			if (ArmorUtils.isWearingMoltenArmor(player)) {
				if (level != null) {
					BlockState state = level.getBlockState(new BlockPos(player.blockPosition().above(1)));
					if (state.is(Blocks.LAVA)) {
						float modifier = hasLavaGoggles ? 1.5f : 1.0f;
						event.setNearPlaneDistance(16.0f * modifier);
						event.setFarPlaneDistance(32.0f * modifier);
						event.setCanceled(true);
					}
				}
			} else if (hasLavaGoggles) {
				if (level != null) {
					BlockState state = level.getBlockState(new BlockPos(player.blockPosition().above(1)));
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

		if (player.hasEffect(EffectRegistry.FLASHBANG_EFFECT)) {
			// Slowly increase distance as the effect ticks closer to zero
			float distance = 1.0f / (player.getEffect(EffectRegistry.FLASHBANG_EFFECT).getDuration() / 20.0f);

			event.setNearPlaneDistance(0.0f);
			event.setFarPlaneDistance(Math.max(distance * 32, 0.25f));
			event.scaleFarPlaneDistance(0.5f);

			event.setFogShape(FogShape.SPHERE);
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void computeFogColorEvent(ComputeFogColor event) {
		if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.hasEffect(EffectRegistry.FLASHBANG_EFFECT)) {
			if (IWConfigs.CLIENT.darkModeFlashbangs.getAsBoolean()) {
				event.setRed(0.0f);
				event.setGreen(0.0f);
				event.setBlue(0.0f);
			} else {
				event.setRed(1.0f);
				event.setGreen(1.0f);
				event.setBlue(1.0f);
			}
		}
	}

	@SubscribeEvent
	public static void computeFovEvent(ComputeFov event) {
		Player player = Minecraft.getInstance().player;

		// Handle FOV changes with some items
		if (player != null) {
			Item itemInHand = player.getItemInHand(player.getUsedItemHand()).getItem();

			if (player.getUseItem().get(DataComponentTypeRegistry.SCOPE) != null && Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
				event.setFOV(15f);
			}

			if ((itemInHand == ItemRegistry.ICE_BOW.get()
					|| itemInHand == ItemRegistry.DRAGONS_BREATH_BOW.get()
					|| itemInHand == ItemRegistry.AURORA_BOW.get()
					|| (itemInHand.asItem() instanceof ThrowableItem throwableItem && throwableItem.type.canCharge))
					&& player.isUsingItem()) {

				float fov = event.getFOV();
				int useTicks = player.getTicksUsingItem();
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
	public static void renderGuiOverlayPostEvent(RenderGuiLayerEvent.Post event) {
		if (IWKeyBinds.DEBUG_TRACING.consumeClick()) {
			DebugTracingData.isDebugTracingEnabled = !DebugTracingData.isDebugTracingEnabled;
		}

		if (DebugTracingData.isDebugTracingEnabled) {
			IWOverlays.DEBUG_TRACING_ELEMENT.render(event.getGuiGraphics(), event.getPartialTick());
		}
	}

	@SubscribeEvent
	public static void keyInputEvent(InputEvent.Key event) {
		// Double jump ability with the Venstral Jar accessory
		if (Minecraft.getInstance().level == null) {
			return;
		}

		Player player = Minecraft.getInstance().player;
		if (player != null && Accessory.isAccessoryActive(player, ItemRegistry.VENSTRAL_JAR.get())) {
			// Check if the jump key is pressed
			if (event.getKey() == Minecraft.getInstance().options.keyJump.getKey().getValue() && event.getAction() == InputConstants.PRESS) {
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

					player.getCooldowns().addCooldown(ItemRegistry.VENSTRAL_JAR.get().getDefaultInstance(), 1200);
				}
			}
		}
	}

	@SubscribeEvent
	public static void recipesReceivedEvent(RecipesReceivedEvent event) {
		ImmersiveWeapons.LOGGER.info("Client received {} recipes", event.getRecipeMap().values().size());

		StarForgeMenu.ALL_RECIPES.clear();
		StarForgeMenu.ALL_RECIPES.addAll(event.getRecipeMap().byType(RecipeTypeRegistry.STAR_FORGE_RECIPE_TYPE.get()));

		SmallPartsMenu.ALL_CRAFTABLES.clear();
		SmallPartsMenu.initializeRecipes(event.getRecipeMap().byType(RecipeTypeRegistry.SMALL_PARTS_RECIPE_TYPE.get()));
	}
}