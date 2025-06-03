package tech.anonymoushacker1279.immersiveweapons;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;
import tech.anonymoushacker1279.immersiveweapons.api.PluginHandler;
import tech.anonymoushacker1279.immersiveweapons.block.core.WoodTypes;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import tech.anonymoushacker1279.immersiveweapons.init.DispenserBehaviorRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.PostSetupHandler;
import tech.anonymoushacker1279.immersiveweapons.world.level.CustomBlockSetTypes;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.IWOverworldBiomesProvider;
import terrablender.api.RegionType;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

@Mod(ImmersiveWeapons.MOD_ID)
public class ImmersiveWeapons {

	public static final String MOD_ID = "immersiveweapons";
	public static boolean IWCB_LOADED = false;

	// Setup logger
	public static final Logger LOGGER = LogUtils.getLogger();

	// Mod setup begins here
	public ImmersiveWeapons(IEventBus modEventBus, ModContainer container) {
		LOGGER.info("Immersive Weapons is starting");

		// Load configuration
		LOGGER.info("Registering configuration files");
		IWConfigs.init(container);

		// Initialize deferred registry
		DeferredRegistryHandler.init(modEventBus);

		// Add event listeners
		modEventBus.addListener(this::setup);
		modEventBus.addListener(this::constructMod);
	}

	/**
	 * Event handler for the FMLCommonSetupEvent. Most of this is registry related.
	 *
	 * @param event the <code>FMLCommonSetupEvent</code> instance
	 */
	public void setup(FMLCommonSetupEvent event) {
		DispenserBehaviorRegistry.init();
		event.enqueueWork(() -> {
			CustomBlockSetTypes.init();
			WoodTypes.init();

			Regions.register(new IWOverworldBiomesProvider(ResourceLocation.fromNamespaceAndPath(MOD_ID, "overworld_biome_provider"), RegionType.OVERWORLD, 1));
			SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID, IWOverworldBiomesProvider.makeSurfaceRules());
		});
		PostSetupHandler.init();

		PluginHandler.initializePlugins(event);

		if (ModList.get().isLoaded("iwcompatbridge")) {
			IWCB_LOADED = true;
		}
	}

	public void constructMod(FMLConstructModEvent event) {
		ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> ConfigurationScreen::new);
	}
}