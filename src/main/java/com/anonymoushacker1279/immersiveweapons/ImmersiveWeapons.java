package com.anonymoushacker1279.immersiveweapons;

import com.anonymoushacker1279.immersiveweapons.config.ClientConfig;
import com.anonymoushacker1279.immersiveweapons.config.ServerConfig;
import com.anonymoushacker1279.immersiveweapons.init.*;
import com.anonymoushacker1279.immersiveweapons.util.CustomWoodTypes;
import com.anonymoushacker1279.immersiveweapons.util.PacketHandler;
import com.anonymoushacker1279.immersiveweapons.world.level.levelgen.OreGeneratorHandler;
import com.anonymoushacker1279.immersiveweapons.world.level.levelgen.Structures;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ImmersiveWeapons.MOD_ID)
public class ImmersiveWeapons {

	public static final String MOD_ID = "immersiveweapons";

	// Setup logger
	public static final Logger LOGGER = LogManager.getLogger();

	// Mod setup begins here
	public ImmersiveWeapons() {
		// Load configuration
		ServerConfig.setup(FMLPaths.CONFIGDIR.get().resolve(MOD_ID + "-server.toml"));
		ClientConfig.setup(FMLPaths.CONFIGDIR.get().resolve(MOD_ID + "-client.toml"));

		// Initialize deferred registry
		DeferredRegistryHandler.init();

		// Register on the event bus
		MinecraftForge.EVENT_BUS.register(this);

		// Add event listeners
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addListener(this::setup);
		MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, Structures::addDimensionalSpacing);

		// Register packet handlers
		PacketHandler.registerPackets();
	}

	/**
	 * Event handler for the FMLCommonSetupEvent.
	 * Most of this is registry related.
	 *
	 * @param event the <code>FMLCommonSetupEvent</code> instance
	 */
	public void setup(FMLCommonSetupEvent event) {
		DispenserBehaviorRegistry.init();
		OreGeneratorHandler.init();
		event.enqueueWork(() -> {
			WoodType.register(CustomWoodTypes.BURNED_OAK);
			Structures.setupStructures();
			Structures.registerConfiguredStructures();
		});
		PostSetupHandler.init();
	}
}