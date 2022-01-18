package com.anonymoushacker1279.immersiveweapons.world.level.levelgen;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.config.ServerConfig;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.google.common.collect.*;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.PlainVillagePools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraftforge.event.world.WorldEvent;

import java.util.*;

public class Structures {

	/**
	 * Static instances of structures that can be referenced and added to biomes easily.
	 * I cannot get my own pool here during initialization, so I use PlainVillagePools.START.
	 * I'll modify this pool at runtime later in #createPiecesGenerator
	 */
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_ABANDONED_FACTORY = DeferredRegistryHandler.ABANDONED_FACTORY_STRUCTURE.get()
			.configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_BEAR_TRAP = DeferredRegistryHandler.BEAR_TRAP_STRUCTURE.get()
			.configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_CAMPSITE = DeferredRegistryHandler.CAMPSITE_STRUCTURE.get()
			.configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_CLOUD_ISLAND = DeferredRegistryHandler.CLOUD_ISLAND_STRUCTURE.get()
			.configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_LANDMINE_TRAP = DeferredRegistryHandler.LANDMINE_TRAP_STRUCTURE.get()
			.configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_OUTHOUSE = DeferredRegistryHandler.OUTHOUSE_STRUCTURE.get()
			.configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_PITFALL_TRAP = DeferredRegistryHandler.PITFALL_TRAP_STRUCTURE.get()
			.configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_UNDERGROUND_BUNKER = DeferredRegistryHandler.UNDERGROUND_BUNKER_STRUCTURE.get()
			.configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_WATER_TOWER = DeferredRegistryHandler.WATER_TOWER_STRUCTURE.get()
			.configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));


	/**
	 * Registers the configured structure, which is what gets added to biomes.
	 * I'm not using a Forge registry because there are none for configured structures.
	 */
	public static void registerConfiguredStructures() {
		Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(ImmersiveWeapons.MOD_ID, "abandoned_factory"),
				CONFIGURED_ABANDONED_FACTORY);
		Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(ImmersiveWeapons.MOD_ID, "bear_trap"),
				CONFIGURED_BEAR_TRAP);
		Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(ImmersiveWeapons.MOD_ID, "campsite"),
				CONFIGURED_CAMPSITE);
		Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(ImmersiveWeapons.MOD_ID, "cloud_island"),
				CONFIGURED_CLOUD_ISLAND);
		Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(ImmersiveWeapons.MOD_ID, "landmine_trap"),
				CONFIGURED_LANDMINE_TRAP);
		Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(ImmersiveWeapons.MOD_ID, "outhouse"),
				CONFIGURED_OUTHOUSE);
		Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(ImmersiveWeapons.MOD_ID, "pitfall_trap"),
				CONFIGURED_PITFALL_TRAP);
		Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(ImmersiveWeapons.MOD_ID, "underground_bunker"),
				CONFIGURED_UNDERGROUND_BUNKER);
		Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(ImmersiveWeapons.MOD_ID, "water_tower"),
				CONFIGURED_WATER_TOWER);
	}

	/**
	 * Set the rarity of structures and determine if land conforms to it.
	 */
	public static void setupStructures() {
		setupStructure(
				DeferredRegistryHandler.ABANDONED_FACTORY_STRUCTURE.get(),
				new StructureFeatureConfiguration(
						ServerConfig.MAX_ABANDONED_FACTORY_DISTANCE.get(),
						ServerConfig.MIN_ABANDONED_FACTORY_DISTANCE.get(),
						959874384),
				true);
		setupStructure(
				DeferredRegistryHandler.BEAR_TRAP_STRUCTURE.get(),
				new StructureFeatureConfiguration(
						ServerConfig.MAX_BEAR_TRAP_DISTANCE.get(),
						ServerConfig.MIN_BEAR_TRAP_DISTANCE.get(),
						794532168),
				false);
		setupStructure(
				DeferredRegistryHandler.CAMPSITE_STRUCTURE.get(),
				new StructureFeatureConfiguration(
						ServerConfig.MAX_CAMPSITE_DISTANCE.get(),
						ServerConfig.MIN_CAMPSITE_DISTANCE.get(),
						671249835),
				true);
		setupStructure(
				DeferredRegistryHandler.CLOUD_ISLAND_STRUCTURE.get(),
				new StructureFeatureConfiguration(
						ServerConfig.MAX_CLOUD_ISLAND_DISTANCE.get(),
						ServerConfig.MIN_CLOUD_ISLAND_DISTANCE.get(),
						349821657),
				false);
		setupStructure(
				DeferredRegistryHandler.LANDMINE_TRAP_STRUCTURE.get(),
				new StructureFeatureConfiguration(
						ServerConfig.MAX_LANDMINE_TRAP_DISTANCE.get(),
						ServerConfig.MIN_LANDMINE_TRAP_DISTANCE.get(),
						959874384),
				true);
		setupStructure(
				DeferredRegistryHandler.OUTHOUSE_STRUCTURE.get(),
				new StructureFeatureConfiguration(
						ServerConfig.MAX_OUTHOUSE_DISTANCE.get(),
						ServerConfig.MIN_OUTHOUSE_DISTANCE.get(),
						845721365),
				false);
		setupStructure(
				DeferredRegistryHandler.PITFALL_TRAP_STRUCTURE.get(),
				new StructureFeatureConfiguration(
						ServerConfig.MAX_PITFALL_TRAP_DISTANCE.get(),
						ServerConfig.MIN_PITFALL_TRAP_DISTANCE.get(),
						875412395),
				false);
		setupStructure(
				DeferredRegistryHandler.UNDERGROUND_BUNKER_STRUCTURE.get(),
				new StructureFeatureConfiguration(
						ServerConfig.MAX_UNDERGROUND_BUNKER_DISTANCE.get(),
						ServerConfig.MIN_UNDERGROUND_BUNKER_DISTANCE.get(),
						548796135),
				false);
		setupStructure(
				DeferredRegistryHandler.WATER_TOWER_STRUCTURE.get(),
				new StructureFeatureConfiguration(
						ServerConfig.MAX_WATER_TOWER_DISTANCE.get(),
						ServerConfig.MIN_WATER_TOWER_DISTANCE.get(),
						246975135),
				true);
	}

	/**
	 * Adds the provided structure to the registry, and adds separation settings.
	 * The rarity of the structure is determined based on the values passed into
	 * this method in the StructureFeatureConfiguration argument.
	 * <p></p>
	 * This method is called by #setupStructures.
	 */
	private static <F extends StructureFeature<?>> void setupStructure(
			F structure,
			StructureFeatureConfiguration structureFeatureConfiguration,
			boolean transformSurroundingLand) {
		// Add our own structure into the structure feature map. Otherwise, you get errors
		StructureFeature.STRUCTURES_REGISTRY.put(Objects.requireNonNull(structure.getRegistryName()).toString(), structure);

		// Adapt the surrounding land to the bottom of our structure
		if (transformSurroundingLand) {
			StructureFeature.NOISE_AFFECTING_FEATURES =
					ImmutableList.<StructureFeature<?>> builder()
							.addAll(StructureFeature.NOISE_AFFECTING_FEATURES)
							.add(structure)
							.build();
		}

		// This is the map that holds the default spacing of all structures
		StructureSettings.DEFAULTS =
				ImmutableMap.<StructureFeature<?>, StructureFeatureConfiguration> builder()
						.putAll(StructureSettings.DEFAULTS)
						.put(structure, structureFeatureConfiguration)
						.build();


		// Add the structure to all the noise generator settings
		BuiltinRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
			Map<StructureFeature<?>, StructureFeatureConfiguration> structureMap = settings.getValue().structureSettings().structureConfig();

			// Be careful with mods that make the structure map immutable (like datapacks do)
			if (structureMap instanceof ImmutableMap) {
				Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(structureMap);
				tempMap.put(structure, structureFeatureConfiguration);
				settings.getValue().structureSettings().structureConfig = tempMap;
			} else {
				structureMap.put(structure, structureFeatureConfiguration);
			}
		});
	}

	/**
	 * Tells the chunk generator which biomes my structures can spawn in.
	 * It will go into the world's chunk generator where I manually add structure spacing.
	 * If the spacing is not added, the structure doesn't spawn.
	 * <p>
	 * It also functions as a dimension blacklist for structures.
	 */
	public static void addDimensionalSpacing(WorldEvent.Load event) {
		if (event.getWorld() instanceof ServerLevel serverLevel) {
			ChunkGenerator chunkGenerator = serverLevel.getChunkSource().getGenerator();
			// Skip superflat generators to prevent issues.
			// Additionally, users don't want structures clogging up their superflat worlds.
			if (chunkGenerator instanceof FlatLevelSource && serverLevel.dimension().equals(Level.OVERWORLD)) {
				return;
			}

			StructureSettings worldStructureConfig = chunkGenerator.getSettings();

			/*
			 * NOTE: BiomeLoadingEvent does not work with structures anymore.
			 * Instead, I will use the below to add my structures to biomes.
			 * This is temporary until Forge finds a better solution for adding structures to biomes.
			 * TODO: Replace with better solution, as BiomeLoadingEvent doesn't work with structures
			 */

			// Create a mutable map, which biomes will be added to for now
			HashMap<StructureFeature<?>, HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> structureToMultiMap =
					new HashMap<>(1);

			// Add the resource key of all biomes that the configured structures can spawn in.
			for (Map.Entry<ResourceKey<Biome>, Biome> biomeEntry :
					serverLevel.registryAccess().ownedRegistryOrThrow(Registry.BIOME_REGISTRY).entrySet()) {

				BiomeCategory category = biomeEntry.getValue().getBiomeCategory();
				// Add structures to biomes here
				if (category == BiomeCategory.FOREST) {
					associateBiomeToConfiguredStructure(structureToMultiMap, CONFIGURED_ABANDONED_FACTORY, biomeEntry.getKey());
					associateBiomeToConfiguredStructure(structureToMultiMap, CONFIGURED_BEAR_TRAP, biomeEntry.getKey());
					associateBiomeToConfiguredStructure(structureToMultiMap, CONFIGURED_OUTHOUSE, biomeEntry.getKey());
					associateBiomeToConfiguredStructure(structureToMultiMap, CONFIGURED_UNDERGROUND_BUNKER, biomeEntry.getKey());
				}
				if (category == BiomeCategory.PLAINS) {
					associateBiomeToConfiguredStructure(structureToMultiMap, CONFIGURED_ABANDONED_FACTORY, biomeEntry.getKey());
					associateBiomeToConfiguredStructure(structureToMultiMap, CONFIGURED_BEAR_TRAP, biomeEntry.getKey());
					associateBiomeToConfiguredStructure(structureToMultiMap, CONFIGURED_CAMPSITE, biomeEntry.getKey());
					associateBiomeToConfiguredStructure(structureToMultiMap, CONFIGURED_OUTHOUSE, biomeEntry.getKey());
					associateBiomeToConfiguredStructure(structureToMultiMap, CONFIGURED_UNDERGROUND_BUNKER, biomeEntry.getKey());
					associateBiomeToConfiguredStructure(structureToMultiMap, CONFIGURED_WATER_TOWER, biomeEntry.getKey());
				}
				if (category == BiomeCategory.DESERT) {
					associateBiomeToConfiguredStructure(structureToMultiMap, CONFIGURED_CAMPSITE, biomeEntry.getKey());
					associateBiomeToConfiguredStructure(structureToMultiMap, CONFIGURED_LANDMINE_TRAP, biomeEntry.getKey());
				}
				if (category == BiomeCategory.TAIGA) {
					associateBiomeToConfiguredStructure(structureToMultiMap, CONFIGURED_CLOUD_ISLAND, biomeEntry.getKey());
				}
				if (category == BiomeCategory.JUNGLE) {
					associateBiomeToConfiguredStructure(structureToMultiMap, CONFIGURED_PITFALL_TRAP, biomeEntry.getKey());
				}
			}

			// Grab the map that holds what ConfigureStructures a structure has and what biomes it can spawn in.
			ImmutableMap.Builder<StructureFeature<?>, ImmutableMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> tempStructureToMultiMap =
					ImmutableMap.builder();
			worldStructureConfig.configuredStructures.entrySet()
					.stream()
					.filter(entry -> !structureToMultiMap.containsKey(entry.getKey()))
					.forEach(tempStructureToMultiMap::put);

			// Add our structures to the structure map/multimap and set the world to use this combined map/multimap.
			structureToMultiMap.forEach((key, value) -> tempStructureToMultiMap.put(key, ImmutableMultimap.copyOf(value)));

			worldStructureConfig.configuredStructures = tempStructureToMultiMap.build();
		}
	}

	/**
	 * Helper method that handles setting up the map to multimap relationship to help prevent issues.
	 */
	private static void associateBiomeToConfiguredStructure(Map<StructureFeature<?>, HashMultimap<ConfiguredStructureFeature<?, ?>,
			ResourceKey<Biome>>> structureToMultimap, ConfiguredStructureFeature<?, ?> configuredStructureFeature,
	                                                        ResourceKey<Biome> biomeRegistryKey) {

		structureToMultimap.putIfAbsent(configuredStructureFeature.feature, HashMultimap.create());
		HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>> configuredStructureToBiomeMultiMap =
				structureToMultimap.get(configuredStructureFeature.feature);

		if (configuredStructureToBiomeMultiMap.containsValue(biomeRegistryKey)) {
			ImmersiveWeapons.LOGGER.error("""
							    Detected 2 ConfiguredStructureFeatures that share the same base StructureFeature trying to be added to same biome. One will be prevented from spawning.
							    The two conflicting ConfiguredStructures are: {}, {}
							    The biome that is attempting to be shared: {}
							""",
					BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(configuredStructureFeature),
					BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(configuredStructureToBiomeMultiMap.entries()
							.stream()
							.filter(e -> e.getValue() == biomeRegistryKey)
							.findFirst()
							.get().getKey()),
					biomeRegistryKey
			);
		} else {
			configuredStructureToBiomeMultiMap.put(configuredStructureFeature, biomeRegistryKey);
		}
	}

	/**
	 * Create a copy of a piece generator context with another config. This is used by the structures.
	 */
	public static PieceGeneratorSupplier.Context<JigsawConfiguration> createContextWithConfig(PieceGeneratorSupplier.Context<JigsawConfiguration> context, JigsawConfiguration newConfig) {
		return new PieceGeneratorSupplier.Context<>(
				context.chunkGenerator(),
				context.biomeSource(),
				context.seed(),
				context.chunkPos(),
				newConfig,
				context.heightAccessor(),
				context.validBiome(),
				context.structureManager(),
				context.registryAccess()
		);
	}
}