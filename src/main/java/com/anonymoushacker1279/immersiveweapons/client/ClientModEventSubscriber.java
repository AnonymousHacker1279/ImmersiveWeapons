package com.anonymoushacker1279.immersiveweapons.client;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.client.gui.screen.SmallPartsTableScreen;
import com.anonymoushacker1279.immersiveweapons.client.gui.screen.TeslaSynthesizerScreen;
import com.anonymoushacker1279.immersiveweapons.client.particle.SmokeBombParticleFactory;
import com.anonymoushacker1279.immersiveweapons.client.renderer.ChairRenderer;
import com.anonymoushacker1279.immersiveweapons.client.renderer.ShelfRenderer;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.BulletRenderer.*;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.CustomArrowRenderer.*;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.*;
import com.anonymoushacker1279.immersiveweapons.container.CustomContainerHolder;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.level.GrassColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fmlclient.registry.ClientRegistry;

import java.util.Objects;

@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientModEventSubscriber {

	private static final String CATEGORY = "key.categories." + ImmersiveWeapons.MOD_ID;
	public static final KeyMapping toggleArmorEffect = new KeyMapping(ImmersiveWeapons.MOD_ID + ".key.toggleArmorEffect", 78, CATEGORY); // Keycode is N
	private static final Minecraft mc = Minecraft.getInstance();

	/**
	 * Event handler for the FMLClientSetupEvent.
	 * @param event the <code>FMLClientSetupEvent</code> instance
	 */
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		ImmersiveWeapons.LOGGER.debug("Performing client-side setup");

		// Register GUI screens
		MenuScreens.register(CustomContainerHolder.SMALL_PARTS_CONTAINER, SmallPartsTableScreen::new);
		MenuScreens.register(CustomContainerHolder.TESLA_SYNTHESIZER_CONTAINER, TeslaSynthesizerScreen::new);

		// Register keybinds
		ClientRegistry.registerKeyBinding(toggleArmorEffect);

		// Register block renderers
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.BULLETPROOF_GLASS.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.WHITE_STAINED_BULLETPROOF_GLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.LIGHT_GRAY_STAINED_BULLETPROOF_GLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.GRAY_STAINED_BULLETPROOF_GLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.BLACK_STAINED_BULLETPROOF_GLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.ORANGE_STAINED_BULLETPROOF_GLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.MAGENTA_STAINED_BULLETPROOF_GLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.LIGHT_BLUE_STAINED_BULLETPROOF_GLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.YELLOW_STAINED_BULLETPROOF_GLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.LIME_STAINED_BULLETPROOF_GLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.PINK_STAINED_BULLETPROOF_GLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.CYAN_STAINED_BULLETPROOF_GLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.PURPLE_STAINED_BULLETPROOF_GLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.BLUE_STAINED_BULLETPROOF_GLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.BROWN_STAINED_BULLETPROOF_GLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.GREEN_STAINED_BULLETPROOF_GLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.RED_STAINED_BULLETPROOF_GLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.CORRUGATED_IRON_PANEL_BARS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.TESLA_SYNTHESIZER.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegistryHandler.CLOUD.get(), RenderType.translucent());

		BlockEntityRenderers.register(DeferredRegistryHandler.WALL_SHELF_BLOCK_ENTITY.get(), ShelfRenderer::new);

		mc.getBlockColors().register((color1, color2, color3, color4) -> BiomeColors.getAverageGrassColor(Objects.requireNonNull(color2), Objects.requireNonNull(color3)), DeferredRegistryHandler.PITFALL.get());

		mc.getItemColors().register((color1, color2) -> GrassColor.get(0.5d, 1.0d), DeferredRegistryHandler.PITFALL_ITEM.get());
	}

	/**
	 * Event handler for the EntityRenderersEvent.RegisterRenderers
	 * @param event the <code>RegisterRenderers</code> instance
	 */
	@SubscribeEvent
	public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(DeferredRegistryHandler.COPPER_ARROW_ENTITY.get(), CopperArrowRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.IRON_ARROW_ENTITY.get(), IronArrowRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.DIAMOND_ARROW_ENTITY.get(), DiamondArrowRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.GOLD_ARROW_ENTITY.get(), GoldArrowRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.STONE_ARROW_ENTITY.get(), StoneArrowRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.WOOD_ARROW_ENTITY.get(), WoodArrowRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.NETHERITE_ARROW_ENTITY.get(), NetheriteArrowRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.SMOKE_BOMB_ARROW_ENTITY.get(), SmokeBombArrowRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.COPPER_BULLET_ENTITY.get(), CopperBulletRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.WOOD_BULLET_ENTITY.get(), WoodBulletRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.STONE_BULLET_ENTITY.get(), StoneBulletRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.IRON_BULLET_ENTITY.get(), IronBulletRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.GOLD_BULLET_ENTITY.get(), GoldBulletRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.DIAMOND_BULLET_ENTITY.get(), DiamondBulletRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.NETHERITE_BULLET_ENTITY.get(), NetheriteBulletRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.FLARE_ENTITY.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.MORTAR_SHELL_ENTITY.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.SMOKE_BOMB_ENTITY.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.MOLOTOV_COCKTAIL_ENTITY.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.DYING_SOLDIER_ENTITY.get(), DyingSoldierRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.MINUTEMAN_ENTITY.get(), MinutemanRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.FIELD_MEDIC_ENTITY.get(), FieldMedicRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.WANDERING_WARRIOR_ENTITY.get(), WanderingWarriorRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.HANS_ENTITY.get(), HansRenderer::new);
		event.registerEntityRenderer(DeferredRegistryHandler.CHAIR_ENTITY.get(), ChairRenderer::new);
	}

	/**
	 * Event handler for the ParticleFactoryRegisterEvent.
	 * @param event the <code>ParticleFactoryRegisterEvent</code> instance
	 */
	@SubscribeEvent
	public static void onParticleFactoryRegistration(ParticleFactoryRegisterEvent event) {
		mc.particleEngine.register(DeferredRegistryHandler.SMOKE_BOMB_PARTICLE_TYPE.get(), SmokeBombParticleFactory::new);
	}
}