package com.anonymoushacker1279.immersiveweapons;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.init.DispenserBehaviorRegistry;
import com.anonymoushacker1279.immersiveweapons.init.PostSetupHandler;
import com.anonymoushacker1279.immersiveweapons.util.*;
import com.anonymoushacker1279.immersiveweapons.world.level.levelgen.OreGeneratorHandler;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.TrapezoidFloat;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.GenerationStep.Carving;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CanyonWorldCarver;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraft.world.level.levelgen.heightproviders.BiasedToBottomHeight;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
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
		Config.setup(FMLPaths.CONFIGDIR.get().resolve(MOD_ID + ".toml"));

		// Register surface builders
		ConfiguredSurfaceBuilders.register();

		// Initialize deferred registry
		DeferredRegistryHandler.init();

		// Register on the event bus
		MinecraftForge.EVENT_BUS.register(this);

		// Add event listeners
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addListener(this::setup);
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		forgeBus.addListener(EventPriority.NORMAL, this::worldLoadEvent);
		forgeBus.addListener(EventPriority.HIGH, this::onBiomeLoading);

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
		OreGeneratorHandler.init(event);
		DispenserBehaviorRegistry.init();
		event.enqueueWork(() -> {
			WoodType.register(CustomWoodTypes.BURNED_OAK);
			setupBiome(DeferredRegistryHandler.BATTLEFIELD.get(), BiomeManager.BiomeType.WARM, 3, Type.PLAINS, Type.OVERWORLD);
			setupBiome(DeferredRegistryHandler.TILTROS.get(), BiomeType.COOL, 0, Type.PLAINS, Type.OVERWORLD);
			Structures.setupStructures();
			Structures.registerAllPieces();
			ConfiguredStructures.registerConfiguredStructures();
		});
		PostSetupHandler.init();
	}

	/**
	 * Event handler for the BiomeLoadingEvent.
	 * Configures custom ores, carvers, spawns, structures, etc.
	 *
	 * @param event the <code>BiomeLoadingEvent</code> instance
	 */
	public void onBiomeLoading(BiomeLoadingEvent event) {
		// Biome modifications
		BiomeGenerationSettingsBuilder generation = event.getGeneration();
		if (event.getCategory() != BiomeCategory.NETHER && event.getCategory() != BiomeCategory.THEEND && !Objects.requireNonNull(event.getName()).toString().equals("immersiveweapons:tiltros")) {
			event.getGeneration().getFeatures(Decoration.UNDERGROUND_ORES)
					.add(() -> OreGeneratorHandler.ORE_COBALT_CONFIG);

			if (event.getCategory() == BiomeCategory.RIVER || event.getCategory() == BiomeCategory.OCEAN) {
				event.getGeneration().getFeatures(Decoration.UNDERGROUND_ORES)
						.add(() -> OreGeneratorHandler.ORE_SULFUR_CONFIG);
			}

			if (event.getCategory() != BiomeCategory.OCEAN && event.getCategory() != BiomeCategory.RIVER) {
				if (Config.WANDERING_WARRIOR_SPAWN.get())
					event.getSpawns().addSpawn(MobCategory.MONSTER, new SpawnerData(DeferredRegistryHandler.WANDERING_WARRIOR_ENTITY.get(), 13, 1, 1));
				if (Config.HANS_SPAWN.get())
					event.getSpawns().addSpawn(MobCategory.MONSTER, new SpawnerData(DeferredRegistryHandler.HANS_ENTITY.get(), 1, 1, 1));
			}
		}
		if (event.getCategory() == BiomeCategory.NETHER) {
			event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES)
					.add(() -> OreGeneratorHandler.ORE_MOLTEN_CONFIG);
			event.getGeneration().getFeatures(Decoration.UNDERGROUND_ORES)
					.add(() -> OreGeneratorHandler.ORE_NETHER_SULFUR_CONFIG);
		}
		if (Objects.requireNonNull(event.getName()).toString().equals("immersiveweapons:tiltros")) {
			if (Config.LAVA_REVENANT_SPAWN.get())
				event.getSpawns().addSpawn(MobCategory.MONSTER, new SpawnerData(DeferredRegistryHandler.LAVA_REVENANT_ENTITY.get(), 1, 0, 1));
			if (Config.ROCK_SPIDER_SPAWN.get())
				event.getSpawns().addSpawn(MobCategory.MONSTER, new SpawnerData(DeferredRegistryHandler.ROCK_SPIDER_ENTITY.get(), 5, 4, 12));
			if (Config.CELESTIAL_TOWER_SPAWN.get())
				event.getSpawns().addSpawn(MobCategory.MONSTER, new SpawnerData(DeferredRegistryHandler.CELESTIAL_TOWER_ENTITY.get(), 2, 0, 1));
		}

		if (event.getCategory() == BiomeCategory.FOREST) {
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_ABANDONED_FACTORY);
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_UNDERGROUND_BUNKER);
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_BEAR_TRAP);
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_OUTHOUSE);
		}
		if (event.getCategory() == BiomeCategory.PLAINS) {
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_ABANDONED_FACTORY);
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_CAMPSITE);
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_OUTHOUSE);
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_WATER_TOWER);
		}
		if (event.getCategory() == BiomeCategory.JUNGLE) {
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_PITFALL_TRAP);
		}
		if (event.getCategory() == BiomeCategory.DESERT) {
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_LANDMINE_TRAP);
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_CAMPSITE);
		}
		if (event.getCategory() == BiomeCategory.TAIGA) {
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_CLOUD_ISLAND);
		}
		if (Objects.requireNonNull(event.getName()).toString().equals(Objects.requireNonNull(DeferredRegistryHandler.BATTLEFIELD.get().getRegistryName()).toString())) {
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_BATTLEFIELD_CAMP);
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_UNDERGROUND_BUNKER);
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_BEAR_TRAP);
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_BATTLEFIELD_VILLAGE);
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_BATTLEFIELD_HOUSE);
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_GRAVEYARD);
			generation.addFeature(Decoration.VEGETAL_DECORATION, ConfiguredStructures.CONFIGURED_WOODEN_SPIKES);
			generation.addFeature(Decoration.VEGETAL_DECORATION, ConfiguredStructures.CONFIGURED_BURNED_OAK_TREE);
			generation.addCarver(Carving.AIR, new ConfiguredWorldCarver<>(CanyonWorldCarver.CANYON, new CanyonCarverConfiguration(0.15F, BiasedToBottomHeight.of(VerticalAnchor.absolute(68), VerticalAnchor.absolute(70), 2), ConstantFloat.of(0.5F), VerticalAnchor.aboveBottom(20), false, CarverDebugSettings.of(false, Blocks.WARPED_BUTTON.defaultBlockState()), UniformFloat.of(-0.125F, 0.125F), new CanyonCarverConfiguration.CanyonShapeConfiguration(UniformFloat.of(0.5F, 0.75F), TrapezoidFloat.of(0.0F, 4.0F, 1.0F), 3, UniformFloat.of(0.75F, 1.0F), 0.5F, 0.0F))));
		}
		if (Objects.requireNonNull(event.getName()).toString().equals(Objects.requireNonNull(DeferredRegistryHandler.TILTROS.get().getRegistryName()).toString())) {
			generation.addCarver(Carving.AIR, new ConfiguredWorldCarver<>(CanyonWorldCarver.CANYON, new CanyonCarverConfiguration(0.15F, BiasedToBottomHeight.of(VerticalAnchor.absolute(68), VerticalAnchor.absolute(70), 2), ConstantFloat.of(0.65F), VerticalAnchor.aboveBottom(10), false, CarverDebugSettings.of(false, Blocks.WARPED_BUTTON.defaultBlockState()), UniformFloat.of(-0.125F, 0.125F), new CanyonCarverConfiguration.CanyonShapeConfiguration(UniformFloat.of(0.0F, 1.0F), TrapezoidFloat.of(0.0F, 106.0F, 2.0F), 3, UniformFloat.of(0.75F, 1.0F), 1.0F, 0.0F))));
		}
	}

	/**
	 * Event handler for the WorldEvent.Load event.
	 * Most importantly, we are building a Map
	 * to contain our structures.
	 *
	 * @param event the <code>WorldEvent.Load</code> instance
	 */
	public void worldLoadEvent(WorldEvent.Load event) {
		if (event.getWorld() instanceof ServerLevel serverWorld) {

			if (serverWorld.getChunkSource().getGenerator() instanceof FlatLevelSource &&
					serverWorld.dimension().equals(Level.OVERWORLD)) {
				return;
			}

			Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(serverWorld.getChunkSource().generator.getSettings().structureConfig());
			tempMap.put(DeferredRegistryHandler.ABANDONED_FACTORY_STRUCTURE.get(), StructureSettings.DEFAULTS.get(DeferredRegistryHandler.ABANDONED_FACTORY_STRUCTURE.get()));
			tempMap.put(DeferredRegistryHandler.PITFALL_TRAP_STRUCTURE.get(), StructureSettings.DEFAULTS.get(DeferredRegistryHandler.PITFALL_TRAP_STRUCTURE.get()));
			tempMap.put(DeferredRegistryHandler.BEAR_TRAP_STRUCTURE.get(), StructureSettings.DEFAULTS.get(DeferredRegistryHandler.BEAR_TRAP_STRUCTURE.get()));
			tempMap.put(DeferredRegistryHandler.LANDMINE_TRAP_STRUCTURE.get(), StructureSettings.DEFAULTS.get(DeferredRegistryHandler.LANDMINE_TRAP_STRUCTURE.get()));
			tempMap.put(DeferredRegistryHandler.UNDERGROUND_BUNKER_STRUCTURE.get(), StructureSettings.DEFAULTS.get(DeferredRegistryHandler.UNDERGROUND_BUNKER_STRUCTURE.get()));
			tempMap.put(DeferredRegistryHandler.BATTLEFIELD_CAMP_STRUCTURE.get(), StructureSettings.DEFAULTS.get(DeferredRegistryHandler.BATTLEFIELD_CAMP_STRUCTURE.get()));
			tempMap.put(DeferredRegistryHandler.BATTLEFIELD_VILLAGE_STRUCTURE.get(), StructureSettings.DEFAULTS.get(DeferredRegistryHandler.BATTLEFIELD_VILLAGE_STRUCTURE.get()));
			tempMap.put(DeferredRegistryHandler.CLOUD_ISLAND_STRUCTURE.get(), StructureSettings.DEFAULTS.get(DeferredRegistryHandler.CLOUD_ISLAND_STRUCTURE.get()));
			tempMap.put(DeferredRegistryHandler.CAMPSITE_STRUCTURE.get(), StructureSettings.DEFAULTS.get(DeferredRegistryHandler.CAMPSITE_STRUCTURE.get()));
			tempMap.put(DeferredRegistryHandler.BATTLEFIELD_HOUSE_STRUCTURE.get(), StructureSettings.DEFAULTS.get(DeferredRegistryHandler.BATTLEFIELD_HOUSE_STRUCTURE.get()));
			tempMap.put(DeferredRegistryHandler.OUTHOUSE_STRUCTURE.get(), StructureSettings.DEFAULTS.get(DeferredRegistryHandler.OUTHOUSE_STRUCTURE.get()));
			tempMap.put(DeferredRegistryHandler.WATER_TOWER_STRUCTURE.get(), StructureSettings.DEFAULTS.get(DeferredRegistryHandler.WATER_TOWER_STRUCTURE.get()));
			tempMap.put(DeferredRegistryHandler.GRAVEYARD_STRUCTURE.get(), StructureSettings.DEFAULTS.get(DeferredRegistryHandler.GRAVEYARD_STRUCTURE.get()));
			serverWorld.getChunkSource().generator.getSettings().structureConfig = tempMap;
		}
	}
}