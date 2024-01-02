package tech.anonymoushacker1279.immersiveweapons;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;
import tech.anonymoushacker1279.immersiveweapons.api.PluginHandler;
import tech.anonymoushacker1279.immersiveweapons.block.properties.WoodTypes;
import tech.anonymoushacker1279.immersiveweapons.config.*;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.world.level.CustomBlockSetTypes;

@Mod(ImmersiveWeapons.MOD_ID)
public class ImmersiveWeapons {

	public static final String MOD_ID = "immersiveweapons";
	public static boolean IWCB_LOADED = false;

	// Setup logger
	public static final Logger LOGGER = LogUtils.getLogger();

	public final static CommonConfig COMMON_CONFIG = ConfigHelper.register(Type.COMMON, CommonConfig::create);

	// Mod setup begins here
	public ImmersiveWeapons(IEventBus modEventBus) {
		LOGGER.info("Immersive Weapons is starting");

		// Load configuration
		LOGGER.info("Registering configuration files");
		ModLoadingContext.get().registerConfig(Type.CLIENT, ClientConfig.CLIENT_SPEC);

		// Initialize deferred registry
		DeferredRegistryHandler.init(modEventBus);

		// Add event listeners
		modEventBus.addListener(this::setup);
	}

	/**
	 * Event handler for the FMLCommonSetupEvent.
	 * Most of this is registry related.
	 *
	 * @param event the <code>FMLCommonSetupEvent</code> instance
	 */
	public void setup(FMLCommonSetupEvent event) {
		DispenserBehaviorRegistry.init();
		event.enqueueWork(() -> {
			CustomBlockSetTypes.init();
			WoodTypes.init();
		});
		PostSetupHandler.init();

		PluginHandler.initializePlugins(event);

		if (ModList.get().isLoaded("iwcompatbridge")) {
			IWCB_LOADED = true;
		}
	}
}