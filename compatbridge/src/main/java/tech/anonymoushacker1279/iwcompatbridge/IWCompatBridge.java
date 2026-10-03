package tech.anonymoushacker1279.iwcompatbridge;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import tech.anonymoushacker1279.immersiveweapons.api.PluginHandler;
import tech.anonymoushacker1279.iwcompatbridge.config.IWCBConfigs;
import tech.anonymoushacker1279.iwcompatbridge.init.IWCBDeferredRegistryHandler;
import tech.anonymoushacker1279.iwcompatbridge.plugin.curios.CuriosEventHandler;
import tech.anonymoushacker1279.iwcompatbridge.plugin.curios.CuriosPlugin;
import tech.anonymoushacker1279.iwcompatbridge.plugin.jei.JEIPlugin;
import tech.anonymoushacker1279.iwcompatbridge.plugin.wthit.WTHITPlugin;

@Mod(IWCompatBridge.MOD_ID)
public class IWCompatBridge {

	public static final String MOD_ID = "iwcompatbridge";

	// Setup logger
	public static final Logger LOGGER = LogUtils.getLogger();

	// Mod setup begins here
	public IWCompatBridge(IEventBus modEventBus, ModContainer container) {
		LOGGER.info("IWCompatBridge is starting");

		// Load configuration
		LOGGER.info("Registering configuration files");
		IWCBConfigs.init(container);

		// Initialize deferred registry
		IWCBDeferredRegistryHandler.init(modEventBus);

		modEventBus.addListener(IWCBDeferredRegistryHandler::setupCreativeTabs);
		modEventBus.addListener(this::constructMod);

		// Register plugins
		PluginHandler.registerPlugin(new WTHITPlugin());
		PluginHandler.registerPlugin(new CuriosPlugin());
		PluginHandler.registerPlugin(new JEIPlugin());

		if (ModList.get().isLoaded("curios")) {
			NeoForge.EVENT_BUS.addListener(CuriosEventHandler::curioEquipEvent);
			NeoForge.EVENT_BUS.addListener(CuriosEventHandler::collectEffects);
			NeoForge.EVENT_BUS.addListener(CuriosEventHandler::collectStandardAttributes);
			NeoForge.EVENT_BUS.addListener(CuriosEventHandler::collectDynamicAttributes);
			NeoForge.EVENT_BUS.addListener(CuriosEventHandler::collectMobEffects);
			NeoForge.EVENT_BUS.addListener(CuriosEventHandler::accessoryActive);
		}
	}

	public void constructMod(FMLConstructModEvent event) {
		ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> ConfigurationScreen::new);
	}
}