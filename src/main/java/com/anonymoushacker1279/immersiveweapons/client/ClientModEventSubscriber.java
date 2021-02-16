package com.anonymoushacker1279.immersiveweapons.client;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.client.gui.screen.SmallPartsTableScreen;
import com.anonymoushacker1279.immersiveweapons.client.particle.SmokeBombParticleFactory;
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
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.CustomArrowRenderer.SmokeBombArrowRenderer;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.CustomArrowRenderer.StoneArrowRenderer;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.CustomArrowRenderer.WoodArrowRenderer;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.ThrowableItemRenderer.MolotovRenderer;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.ThrowableItemRenderer.SmokeBombRenderer;
import com.anonymoushacker1279.immersiveweapons.container.CustomContainerHolder;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GrassColors;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
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
	    
	    // Register GUI screens
		ScreenManager.registerFactory(CustomContainerHolder.SMALL_PARTS_CONTAINER, SmallPartsTableScreen::new);
	    
		// Register projectile entity renderers
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.COPPER_ARROW_ENTITY.get(), CopperArrowRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.IRON_ARROW_ENTITY.get(), IronArrowRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.DIAMOND_ARROW_ENTITY.get(), DiamondArrowRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.GOLD_ARROW_ENTITY.get(), GoldArrowRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.STONE_ARROW_ENTITY.get(), StoneArrowRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.WOOD_ARROW_ENTITY.get(), WoodArrowRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.NETHERITE_ARROW_ENTITY.get(), NetheriteArrowRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.SMOKE_BOMB_ARROW_ENTITY.get(), SmokeBombArrowRenderer::new);

	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.COPPER_BULLET_ENTITY.get(), CopperBulletRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.WOOD_BULLET_ENTITY.get(), WoodBulletRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.STONE_BULLET_ENTITY.get(), StoneBulletRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.IRON_BULLET_ENTITY.get(), IronBulletRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.GOLD_BULLET_ENTITY.get(), GoldBulletRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.DIAMOND_BULLET_ENTITY.get(), DiamondBulletRenderer::new);
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.NETHERITE_BULLET_ENTITY.get(), NetheriteBulletRenderer::new);
	    
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.SMOKE_BOMB_ENTITY.get(), new SmokeBombRenderer());
	    RenderingRegistry.registerEntityRenderingHandler(DeferredRegistryHandler.MOLOTOV_COCKTAIL_ENTITY.get(), new MolotovRenderer());
	    
	    // Register block renderers
	    RenderTypeLookup.setRenderLayer(DeferredRegistryHandler.BULLETPROOF_GLASS.get(), RenderType.getCutoutMipped());
	    RenderTypeLookup.setRenderLayer(DeferredRegistryHandler.WHITE_STAINED_BULLETPROOF_GLASS.get(), RenderType.getTranslucent());
	    RenderTypeLookup.setRenderLayer(DeferredRegistryHandler.LIGHT_GRAY_STAINED_BULLETPROOF_GLASS.get(), RenderType.getTranslucent());
	    RenderTypeLookup.setRenderLayer(DeferredRegistryHandler.GRAY_STAINED_BULLETPROOF_GLASS.get(), RenderType.getTranslucent());
	    RenderTypeLookup.setRenderLayer(DeferredRegistryHandler.BLACK_STAINED_BULLETPROOF_GLASS.get(), RenderType.getTranslucent());
	    RenderTypeLookup.setRenderLayer(DeferredRegistryHandler.ORANGE_STAINED_BULLETPROOF_GLASS.get(), RenderType.getTranslucent());
	    RenderTypeLookup.setRenderLayer(DeferredRegistryHandler.MAGENTA_STAINED_BULLETPROOF_GLASS.get(), RenderType.getTranslucent());
	    RenderTypeLookup.setRenderLayer(DeferredRegistryHandler.LIGHT_BLUE_STAINED_BULLETPROOF_GLASS.get(), RenderType.getTranslucent());
	    RenderTypeLookup.setRenderLayer(DeferredRegistryHandler.YELLOW_STAINED_BULLETPROOF_GLASS.get(), RenderType.getTranslucent());
	    RenderTypeLookup.setRenderLayer(DeferredRegistryHandler.LIME_STAINED_BULLETPROOF_GLASS.get(), RenderType.getTranslucent());
	    RenderTypeLookup.setRenderLayer(DeferredRegistryHandler.PINK_STAINED_BULLETPROOF_GLASS.get(), RenderType.getTranslucent());
	    RenderTypeLookup.setRenderLayer(DeferredRegistryHandler.CYAN_STAINED_BULLETPROOF_GLASS.get(), RenderType.getTranslucent());
	    RenderTypeLookup.setRenderLayer(DeferredRegistryHandler.PURPLE_STAINED_BULLETPROOF_GLASS.get(), RenderType.getTranslucent());
	    RenderTypeLookup.setRenderLayer(DeferredRegistryHandler.BLUE_STAINED_BULLETPROOF_GLASS.get(), RenderType.getTranslucent());
	    RenderTypeLookup.setRenderLayer(DeferredRegistryHandler.BROWN_STAINED_BULLETPROOF_GLASS.get(), RenderType.getTranslucent());
	    RenderTypeLookup.setRenderLayer(DeferredRegistryHandler.GREEN_STAINED_BULLETPROOF_GLASS.get(), RenderType.getTranslucent());
	    RenderTypeLookup.setRenderLayer(DeferredRegistryHandler.RED_STAINED_BULLETPROOF_GLASS.get(), RenderType.getTranslucent());
	    
	    Minecraft.getInstance().getBlockColors().register(new IBlockColor() {
	    	@Override
	    	public int getColor(BlockState p_getColor_1_, IBlockDisplayReader p_getColor_2_, BlockPos p_getColor_3_,
	    			int p_getColor_4_) {
	    		return BiomeColors.getGrassColor(p_getColor_2_, p_getColor_3_);
	    	}
	    }, DeferredRegistryHandler.PITFALL.get());
	    
	    Minecraft.getInstance().getItemColors().register(new IItemColor() {		
	    	@Override
	    	public int getColor(ItemStack p_getColor_1_, int p_getColor_2_) {
	    		return GrassColors.get(0.5d, 1.0d);
	    	}
	    }, DeferredRegistryHandler.PITFALL_ITEM.get());
	 }
	 
	 @SubscribeEvent
	 public static void onParticleFactoryRegistration(ParticleFactoryRegisterEvent event) {
		 Minecraft mc = Minecraft.getInstance();
		 
		 mc.particles.registerFactory(DeferredRegistryHandler.SMOKE_BOMB_PARTICLE_TYPE.get(), sprite -> new SmokeBombParticleFactory(sprite));
	 }
	 
	 private static final String CATEGORY = "key.categories." + ImmersiveWeapons.MOD_ID;
	 
	 public static final KeyBinding toggleArmorEffect = new KeyBinding(ImmersiveWeapons.MOD_ID + ".key.toggleArmorEffect", 78, CATEGORY); // Keycode is N
	 
	 static {
		 ClientRegistry.registerKeyBinding(toggleArmorEffect);
	 }
}
