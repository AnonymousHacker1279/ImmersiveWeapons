package com.anonymoushacker1279.immersiveweapons;

import com.anonymoushacker1279.immersiveweapons.config.ClientConfig;
import com.anonymoushacker1279.immersiveweapons.config.ServerConfig;
import com.anonymoushacker1279.immersiveweapons.init.*;
import com.anonymoushacker1279.immersiveweapons.util.*;
import com.anonymoushacker1279.immersiveweapons.world.level.levelgen.OreGeneratorHandler;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.common.*;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

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

		// TODO: Rework when Forge API updates
		// Register surface builders
		// ConfiguredSurfaceBuilders.register();

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
	 * Sets up a custom biome. Adds the types to the BiomeDictionary
	 * and adds the biome to the BiomeManager.
	 *
	 * @param biome     the <code>Biome</code> being setup
	 * @param biomeType the <code>BiomeType</code> for the biome
	 * @param weight    weight to generate biomes
	 * @param types     the dimension type: leave null for a modded dimension
	 */
	private static void setupBiome(Biome biome, BiomeType biomeType, int weight, BiomeDictionary.Type... types) {
		BiomeDictionary.addTypes(key(biome), types);
		BiomeManager.addBiome(biomeType, new BiomeManager.BiomeEntry(key(biome), weight));
	}

	/**
	 * Create a ResourceKey for Biomes.
	 *
	 * @param biome the <code>Biome</code> being registered
	 * @return ResourceKey extending Biome
	 */
	private static ResourceKey<Biome> key(Biome biome) {
		return ResourceKey.create(ForgeRegistries.Keys.BIOMES, Objects.requireNonNull(ForgeRegistries.BIOMES.getKey(biome), "Biome registry name was null"));
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
			// TODO: Rework when Forge API updates
			// setupBiome(DeferredRegistryHandler.BATTLEFIELD.get(), BiomeManager.BiomeType.WARM, 3, Type.PLAINS, Type.OVERWORLD);
			// setupBiome(DeferredRegistryHandler.TILTROS.get(), BiomeType.COOL, 0, Type.PLAINS, Type.OVERWORLD);
			Structures.setupStructures();
			Structures.registerAllPieces();
			// TODO: Rework when Forge API updates
			// ConfiguredStructures.registerConfiguredStructures();
		});
		PostSetupHandler.init();
	}
}