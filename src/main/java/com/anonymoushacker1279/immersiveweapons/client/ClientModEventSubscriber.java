package com.anonymoushacker1279.immersiveweapons.client;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.CustomArrowRenderer.CopperArrowRenderer;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.CustomArrowRenderer.DiamondArrowRenderer;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.CustomArrowRenderer.GoldArrowRenderer;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.CustomArrowRenderer.IronArrowRenderer;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.CustomArrowRenderer.NetheriteArrowRenderer;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.CustomArrowRenderer.StoneArrowRenderer;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.CustomArrowRenderer.WoodArrowRenderer;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

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
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.COPPER_ARROW_ENTITY.get(), CopperArrowRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.IRON_ARROW_ENTITY.get(), IronArrowRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.DIAMOND_ARROW_ENTITY.get(), DiamondArrowRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.GOLD_ARROW_ENTITY.get(), GoldArrowRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.STONE_ARROW_ENTITY.get(), StoneArrowRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.WOOD_ARROW_ENTITY.get(), WoodArrowRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.NETHERITE_ARROW_ENTITY.get(), NetheriteArrowRenderer::new);
	 }
	 
	 private static final String CATEGORY = "key.categories." + ImmersiveWeapons.MOD_ID;
	 
	 public static final KeyBinding toggleArmorEffect = new KeyBinding(ImmersiveWeapons.MOD_ID + ".key.toggleArmorEffect", 78, CATEGORY); // Keycode is N
	 
	 static {
		 ClientRegistry.registerKeyBinding(toggleArmorEffect);
	 }
}
