package tech.anonymoushacker1279.immersiveweapons;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import tech.anonymoushacker1279.immersiveweapons.advancement.IWCriteriaTriggers;
import tech.anonymoushacker1279.immersiveweapons.api.PluginHandler;
import tech.anonymoushacker1279.immersiveweapons.block.properties.WoodTypes;
import tech.anonymoushacker1279.immersiveweapons.config.*;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.world.level.CustomBlockSetTypes;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.biomes.IWOverworldBiomesProvider;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.biomes.IWSurfaceRuleData;
import terrablender.api.*;
import terrablender.api.SurfaceRuleManager.RuleCategory;

@Mod(ImmersiveWeapons.MOD_ID)
public class ImmersiveWeapons {

	public static final String MOD_ID = "immersiveweapons";
	public static boolean IWCB_LOADED = false;

	// Setup logger
	public static final Logger LOGGER = LogUtils.getLogger();

	public final static CommonConfig COMMON_CONFIG = ConfigHelper.register(Type.COMMON, CommonConfig::create);

	// Mod setup begins here
	public ImmersiveWeapons() {
		LOGGER.info("Immersive Weapons is starting");

		// Load configuration
		LOGGER.info("Registering configuration files");
		ModLoadingContext.get().registerConfig(Type.CLIENT, ClientConfig.CLIENT_SPEC);

		// Initialize deferred registry
		DeferredRegistryHandler.init();

		// Register on the event bus
		MinecraftForge.EVENT_BUS.register(this);

		// Add event listeners
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addListener(this::setup);

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
		IWCriteriaTriggers.init();
		event.enqueueWork(() -> {
			CustomBlockSetTypes.init();
			WoodTypes.init();
			Regions.register(new IWOverworldBiomesProvider(new ResourceLocation(MOD_ID, "overworld_biome_provider"),
					RegionType.OVERWORLD, 1));
			SurfaceRuleManager.addSurfaceRules(RuleCategory.OVERWORLD, MOD_ID, IWSurfaceRuleData.makeRules());
		});
		PostSetupHandler.init();

		PluginHandler.initializePlugins(event);

		if (ModList.get().isLoaded("iwcompatbridge")) {
			IWCB_LOADED = true;
		}
	}
}