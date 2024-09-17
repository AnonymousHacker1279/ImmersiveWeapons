package tech.anonymoushacker1279.immersiveweapons;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;
import tech.anonymoushacker1279.immersiveweapons.api.PluginHandler;
import tech.anonymoushacker1279.immersiveweapons.block.properties.WoodTypes;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.world.level.CustomBlockSetTypes;

@Mod(ImmersiveWeapons.MOD_ID)
public class ImmersiveWeapons {

	public static final String MOD_ID = "immersiveweapons";
	public static boolean IWCB_LOADED = false;

	// Setup logger
	public static final Logger LOGGER = LogUtils.getLogger();

	// Mod setup begins here
	public ImmersiveWeapons(IEventBus modEventBus) {
		LOGGER.info("Immersive Weapons is starting");

		// Load configuration
		LOGGER.info("Registering configuration files");

		// TODO: rework config
		/*new ConfigBuilder(MOD_ID, CommonConfig.class)
				.setConfigName("Common Config")
				.build();
		new ConfigBuilder(MOD_ID, "client", ClientConfig.class)
				.setConfigName("Client Config")
				.setClientOnly(true)
				.build();*/

		// Initialize deferred registry
		DeferredRegistryHandler.init(modEventBus);

		// Add event listeners
		modEventBus.addListener(this::setup);
		// modEventBus.addListener(this::constructMod);
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

	/*public void constructMod(FMLConstructModEvent event) {
		ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class,
				() -> (minecraft, modListScreen) -> CobaltConfigScreen.getScreen(modListScreen, MOD_ID));
	}*/
}