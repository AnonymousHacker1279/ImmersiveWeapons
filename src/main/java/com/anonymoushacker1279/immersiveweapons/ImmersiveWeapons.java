package com.anonymoushacker1279.immersiveweapons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anonymoushacker1279.immersiveweapons.util.DeferredRegistryHandler;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("immersiveweapons")
@Mod.EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = Bus.MOD)
public class ImmersiveWeapons
{
	
	public static final String MOD_ID = "immersiveweapons";
	
	// Setup logger
	public static final Logger LOGGER = LogManager.getLogger();

    public ImmersiveWeapons() {
        // Register the setup method for mod loading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the doClientStuff method for mod loading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        
        DeferredRegistryHandler.init();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
   }

    
    private void setup(final FMLCommonSetupEvent event) {

    }
    

    private void doClientStuff(final FMLClientSetupEvent event) {

    }
    
    @SubscribeEvent
    public static void loadCompleteEvent(FMLLoadCompleteEvent event) {
    	LOGGER.debug("FML_Load_Complete","Forge loading complete!");
    	
    }
    
    public static final ItemGroup TAB = new ItemGroup("immersiveweaponsTab") {
    	@Override
    	public ItemStack createIcon() {
    		return new ItemStack(DeferredRegistryHandler.MOLTEN_SWORD.get());
    	}
    };

}
