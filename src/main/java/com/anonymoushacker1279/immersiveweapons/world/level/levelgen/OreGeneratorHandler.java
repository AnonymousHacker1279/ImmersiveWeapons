package com.anonymoushacker1279.immersiveweapons.world.level.levelgen;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration.TargetBlockState;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class OreGeneratorHandler {

	static List<TargetBlockState> targetBlockStates;
	static ConfiguredFeature<?, ?> configuredFeature;

	public static PlacedFeature MOLTEN_ORE_FEATURE;
	public static PlacedFeature NETHER_SULFUR_ORE_FEATURE;
	public static PlacedFeature SULFUR_ORE_FEATURE;
	public static PlacedFeature COBALT_ORE_FEATURE;
	public static PlacedFeature DEEPSLATE_SULFUR_ORE_FEATURE;
	public static PlacedFeature DEEPSLATE_COBALT_ORE_FEATURE;

	/**
	 * Initialize ore generation setup.
	 */
	public static void init() {
		setupMoltenOre();
		setupNetherSulfurOre();
		setupSulfurOre();
		setupCobaltOre();
		setupDeepslateCobaltOre();
		setupDeepslateSulfurOre();
	}

	public static void setupMoltenOre() {
		int orePerVein = 4, veinsPerChunk = 8, maxY = 64;

		BlockState blockState = DeferredRegistryHandler.MOLTEN_ORE.get().defaultBlockState();

		targetBlockStates = List.of(OreConfiguration.target(OreFeatures.NETHERRACK, blockState));

		configuredFeature = FeatureUtils.register(ImmersiveWeapons.MOD_ID + "block/netherrack_molten_ore",
				Feature.ORE.configured(new OreConfiguration(targetBlockStates, orePerVein)));

		MOLTEN_ORE_FEATURE = PlacementUtils.register(ImmersiveWeapons.MOD_ID + "ore/netherrack_molten_ore",
				configuredFeature.placed(List.of(CountPlacement.of(veinsPerChunk), InSquarePlacement.spread(),
						HeightRangePlacement.triangle(VerticalAnchor.bottom(),
								VerticalAnchor.absolute(maxY)),
						BiomeFilter.biome())));
	}

	public static void setupNetherSulfurOre() {
		int orePerVein = 12, veinsPerChunk = 16;

		BlockState blockState = DeferredRegistryHandler.NETHER_SULFUR_ORE.get().defaultBlockState();

		targetBlockStates = List.of(OreConfiguration.target(OreFeatures.NETHERRACK, blockState));

		configuredFeature = FeatureUtils.register(ImmersiveWeapons.MOD_ID + "block/netherrack_nether_sulfur_ore",
				Feature.ORE.configured(new OreConfiguration(targetBlockStates, orePerVein)));

		NETHER_SULFUR_ORE_FEATURE = PlacementUtils.register(ImmersiveWeapons.MOD_ID + "ore/netherrack_nether_sulfur_ore",
				configuredFeature.placed(List.of(CountPlacement.of(veinsPerChunk), InSquarePlacement.spread(),
						HeightRangePlacement.triangle(VerticalAnchor.bottom(),
								VerticalAnchor.top()),
						BiomeFilter.biome())));
	}

	public static void setupSulfurOre() {
		int orePerVein = 8, veinsPerChunk = 14;

		BlockState blockState = DeferredRegistryHandler.SULFUR_ORE.get().defaultBlockState();

		targetBlockStates = List.of(OreConfiguration.target(OreFeatures.NATURAL_STONE, blockState));

		configuredFeature = FeatureUtils.register(ImmersiveWeapons.MOD_ID + "block/stone_sulfur_ore",
				Feature.ORE.configured(new OreConfiguration(targetBlockStates, orePerVein)));

		SULFUR_ORE_FEATURE = PlacementUtils.register(ImmersiveWeapons.MOD_ID + "ore/stone_sulfur_ore",
				configuredFeature.placed(List.of(CountPlacement.of(veinsPerChunk), InSquarePlacement.spread(),
						HeightRangePlacement.triangle(VerticalAnchor.absolute(32),
								VerticalAnchor.top()),
						BiomeFilter.biome())));
	}

	public static void setupCobaltOre() {
		int orePerVein = 6, veinsPerChunk = 12, belowTop = 24;

		BlockState blockState = DeferredRegistryHandler.COBALT_ORE.get().defaultBlockState();

		targetBlockStates = List.of(OreConfiguration.target(OreFeatures.NATURAL_STONE, blockState));

		configuredFeature = FeatureUtils.register(ImmersiveWeapons.MOD_ID + "block/stone_cobalt_ore",
				Feature.ORE.configured(new OreConfiguration(targetBlockStates, orePerVein)));

		COBALT_ORE_FEATURE = PlacementUtils.register(ImmersiveWeapons.MOD_ID + "ore/stone_cobalt_ore",
				configuredFeature.placed(List.of(CountPlacement.of(veinsPerChunk), InSquarePlacement.spread(),
						HeightRangePlacement.triangle(VerticalAnchor.absolute(7),
								VerticalAnchor.belowTop(belowTop)),
						BiomeFilter.biome())));
	}

	public static void setupDeepslateSulfurOre() {
		int orePerVein = 8, veinsPerChunk = 14;

		BlockState blockState = DeferredRegistryHandler.DEEPSLATE_SULFUR_ORE.get().defaultBlockState();

		targetBlockStates = List.of(OreConfiguration.target(OreFeatures.NATURAL_STONE, blockState));

		configuredFeature = FeatureUtils.register(ImmersiveWeapons.MOD_ID + "block/deepslate_sulfur_ore",
				Feature.ORE.configured(new OreConfiguration(targetBlockStates, orePerVein)));

		DEEPSLATE_SULFUR_ORE_FEATURE = PlacementUtils.register(ImmersiveWeapons.MOD_ID + "ore/deepslate_sulfur_ore",
				configuredFeature.placed(List.of(CountPlacement.of(veinsPerChunk), InSquarePlacement.spread(),
						HeightRangePlacement.triangle(VerticalAnchor.bottom(),
								VerticalAnchor.absolute(7)),
						BiomeFilter.biome())));
	}

	public static void setupDeepslateCobaltOre() {
		int orePerVein = 8, veinsPerChunk = 16;

		BlockState blockState = DeferredRegistryHandler.DEEPSLATE_COBALT_ORE.get().defaultBlockState();

		targetBlockStates = List.of(OreConfiguration.target(OreFeatures.NATURAL_STONE, blockState));

		configuredFeature = FeatureUtils.register(ImmersiveWeapons.MOD_ID + "block/deepslate_cobalt_ore",
				Feature.ORE.configured(new OreConfiguration(targetBlockStates, orePerVein)));

		DEEPSLATE_COBALT_ORE_FEATURE = PlacementUtils.register(ImmersiveWeapons.MOD_ID + "ore/deepslate_cobalt_ore",
				configuredFeature.placed(List.of(CountPlacement.of(veinsPerChunk), InSquarePlacement.spread(),
						HeightRangePlacement.triangle(VerticalAnchor.bottom(),
								VerticalAnchor.absolute(7)),
						BiomeFilter.biome())));
	}
}