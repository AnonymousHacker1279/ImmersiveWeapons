package com.anonymoushacker1279.immersiveweapons.util;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.Features;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.blockplacers.SimpleBlockPlacer;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;

public class ConfiguredStructures {

	public static ConfiguredStructureFeature<?, ?> CONFIGURED_ABANDONED_FACTORY = Structures.ABANDONED_FACTORY.get().configured(FeatureConfiguration.NONE);
	public static ConfiguredStructureFeature<?, ?> CONFIGURED_PITFALL_TRAP = Structures.PITFALL_TRAP.get().configured(FeatureConfiguration.NONE);
	public static ConfiguredStructureFeature<?, ?> CONFIGURED_BEAR_TRAP = Structures.BEAR_TRAP.get().configured(FeatureConfiguration.NONE);
	public static ConfiguredStructureFeature<?, ?> CONFIGURED_LANDMINE_TRAP = Structures.LANDMINE_TRAP.get().configured(FeatureConfiguration.NONE);
	public static ConfiguredStructureFeature<?, ?> CONFIGURED_UNDERGROUND_BUNKER = Structures.UNDERGROUND_BUNKER.get().configured(FeatureConfiguration.NONE);
	public static ConfiguredStructureFeature<?, ?> CONFIGURED_BATTLEFIELD_CAMP = Structures.BATTLEFIELD_CAMP.get().configured(FeatureConfiguration.NONE);
	public static ConfiguredStructureFeature<?, ?> CONFIGURED_BATTLEFIELD_VILLAGE = Structures.BATTLEFIELD_VILLAGE.get().configured(FeatureConfiguration.NONE);
	public static ConfiguredStructureFeature<?, ?> CONFIGURED_CLOUD_ISLAND = Structures.CLOUD_ISLAND.get().configured(FeatureConfiguration.NONE);
	public static ConfiguredStructureFeature<?, ?> CONFIGURED_CAMPSITE = Structures.CAMPSITE.get().configured(FeatureConfiguration.NONE);

	public static ConfiguredFeature<?, ?> CONFIGURED_WOODEN_SPIKES = Feature.RANDOM_PATCH.configured(new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(DeferredRegistryHandler.WOODEN_SPIKES.get().defaultBlockState()), SimpleBlockPlacer.INSTANCE).tries(32).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).yspread(1).xspread(4).zspread(4).noProjection().build()).decorated(Features.Decorators.TOP_SOLID_HEIGHTMAP_SQUARE).rarity(12);

	/**
	 * Register configured structures.
	 */
	public static void registerConfiguredStructures() {
		Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
		Registry.register(registry, new ResourceLocation(ImmersiveWeapons.MOD_ID, "configured_abandoned_factory"), CONFIGURED_ABANDONED_FACTORY);
		FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(Structures.ABANDONED_FACTORY.get(), CONFIGURED_ABANDONED_FACTORY);

		Registry.register(registry, new ResourceLocation(ImmersiveWeapons.MOD_ID, "configured_pitfall_trap"), CONFIGURED_PITFALL_TRAP);
		FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(Structures.PITFALL_TRAP.get(), CONFIGURED_PITFALL_TRAP);

		Registry.register(registry, new ResourceLocation(ImmersiveWeapons.MOD_ID, "configured_bear_trap"), CONFIGURED_BEAR_TRAP);
		FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(Structures.BEAR_TRAP.get(), CONFIGURED_BEAR_TRAP);

		Registry.register(registry, new ResourceLocation(ImmersiveWeapons.MOD_ID, "configured_landmine_trap"), CONFIGURED_LANDMINE_TRAP);
		FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(Structures.LANDMINE_TRAP.get(), CONFIGURED_LANDMINE_TRAP);

		Registry.register(registry, new ResourceLocation(ImmersiveWeapons.MOD_ID, "configured_underground_bunker"), CONFIGURED_UNDERGROUND_BUNKER);
		FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(Structures.UNDERGROUND_BUNKER.get(), CONFIGURED_UNDERGROUND_BUNKER);

		Registry.register(registry, new ResourceLocation(ImmersiveWeapons.MOD_ID, "configured_battlefield_camp"), CONFIGURED_BATTLEFIELD_CAMP);
		FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(Structures.BATTLEFIELD_CAMP.get(), CONFIGURED_BATTLEFIELD_CAMP);

		Registry.register(registry, new ResourceLocation(ImmersiveWeapons.MOD_ID, "configured_battlefield_village"), CONFIGURED_BATTLEFIELD_VILLAGE);
		FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(Structures.BATTLEFIELD_VILLAGE.get(), CONFIGURED_BATTLEFIELD_VILLAGE);

		Registry.register(registry, new ResourceLocation(ImmersiveWeapons.MOD_ID, "configured_cloud_island"), CONFIGURED_CLOUD_ISLAND);
		FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(Structures.CLOUD_ISLAND.get(), CONFIGURED_CLOUD_ISLAND);

		Registry.register(registry, new ResourceLocation(ImmersiveWeapons.MOD_ID, "configured_campsite"), CONFIGURED_CAMPSITE);
		FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(Structures.CAMPSITE.get(), CONFIGURED_CAMPSITE);
	}

}