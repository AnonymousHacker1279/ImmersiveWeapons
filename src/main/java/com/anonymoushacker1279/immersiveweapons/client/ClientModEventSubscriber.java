package com.anonymoushacker1279.immersiveweapons.client;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.client.gui.screen.SmallPartsTableScreen;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.BulletRenderer.CopperBulletRenderer;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.BulletRenderer.DiamondBulletRenderer;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.BulletRenderer.GoldBulletRenderer;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.BulletRenderer.IronBulletRenderer;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.BulletRenderer.NetheriteBulletRenderer;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.BulletRenderer.StoneBulletRenderer;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.BulletRenderer.WoodBulletRenderer;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.CustomArrowRenderer.CopperArrowRenderer;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.CustomArrowRenderer.DiamondArrowRenderer;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.CustomArrowRenderer.GoldArrowRenderer;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.CustomArrowRenderer.IronArrowRenderer;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.CustomArrowRenderer.NetheriteArrowRenderer;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.CustomArrowRenderer.StoneArrowRenderer;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.CustomArrowRenderer.WoodArrowRenderer;
import com.anonymoushacker1279.immersiveweapons.container.CustomContainerHolder;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus=EventBusSubscriber.Bus.MOD, value=Dist.CLIENT)
public class ClientModEventSubscriber {

	 @SubscribeEvent
	 public static void onClientSetup(final FMLClientSetupEvent event) {
	    ImmersiveWeapons.LOGGER.debug("Performing client-side setup");
	    
		ScreenManager.registerFactory(CustomContainerHolder.SMALL_PARTS_CONTAINER, SmallPartsTableScreen::new);
	    
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.COPPER_ARROW_ENTITY.get(), CopperArrowRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.IRON_ARROW_ENTITY.get(), IronArrowRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.DIAMOND_ARROW_ENTITY.get(), DiamondArrowRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.GOLD_ARROW_ENTITY.get(), GoldArrowRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.STONE_ARROW_ENTITY.get(), StoneArrowRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.WOOD_ARROW_ENTITY.get(), WoodArrowRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.NETHERITE_ARROW_ENTITY.get(), NetheriteArrowRenderer::new);
	    
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.COPPER_BULLET_ENTITY.get(), CopperBulletRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.WOOD_BULLET_ENTITY.get(), WoodBulletRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.STONE_BULLET_ENTITY.get(), StoneBulletRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.IRON_BULLET_ENTITY.get(), IronBulletRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.GOLD_BULLET_ENTITY.get(), GoldBulletRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.DIAMOND_BULLET_ENTITY.get(), DiamondBulletRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.NETHERITE_BULLET_ENTITY.get(), NetheriteBulletRenderer::new);
	 }
	 
	 private static final String CATEGORY = "key.categories." + ImmersiveWeapons.MOD_ID;
	 
	 public static final KeyBinding toggleArmorEffect = new KeyBinding(ImmersiveWeapons.MOD_ID + ".key.toggleArmorEffect", 78, CATEGORY); // Keycode is N
	 
	 static {
		 ClientRegistry.registerKeyBinding(toggleArmorEffect);
	 }
}
