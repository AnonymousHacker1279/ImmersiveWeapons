package com.anonymoushacker1279.immersiveweapons.world.level.levelgen;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.config.CommonConfig;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class OreGeneratorHandler {

	public static PlacedFeature MOLTEN_ORE_BLOB_PLACEMENT;
	public static PlacedFeature NETHER_SULFUR_ORE_BLOB_PLACEMENT;
	public static PlacedFeature SULFUR_ORE_BLOB_PLACEMENT;
	public static PlacedFeature DEEPSLATE_SULFUR_ORE_BLOB_PLACEMENT;
	public static PlacedFeature COBALT_ORE_BLOB_PLACEMENT;
	public static PlacedFeature DEEPSLATE_COBALT_ORE_BLOB_PLACEMENT;

	public static void init() {
		ImmersiveWeapons.LOGGER.info("Initializing ore generation handler");

		MOLTEN_ORE_BLOB_PLACEMENT = new PlacedFeature(
				Holder.direct(new ConfiguredFeature<>(Feature.SCATTERED_ORE,
						new OreConfiguration(OreReplacementTargets.MOLTEN_ORE_TARGETS, CommonConfig.MOLTEN_ORE_SIZE.get(),
								(float) ((double) CommonConfig.MOLTEN_ORE_EXPOSED_DISCARD_CHANCE.get())))),

				List.of(HeightRangePlacement.triangle(VerticalAnchor.absolute(CommonConfig.MOLTEN_ORE_BOTTOM_ANCHOR.get()),
								VerticalAnchor.absolute(CommonConfig.MOLTEN_ORE_TOP_ANCHOR.get())),
						BiomeFilter.biome(),
						InSquarePlacement.spread(),
						CountPlacement.of(CommonConfig.MOLTEN_ORE_WEIGHT.get())
				));
		NETHER_SULFUR_ORE_BLOB_PLACEMENT = new PlacedFeature(
				Holder.direct(new ConfiguredFeature<>(Feature.ORE,
						new OreConfiguration(OreReplacementTargets.SULFUR_ORE_TARGETS, CommonConfig.NETHER_SULFUR_ORE_SIZE.get(),
								(float) ((double) CommonConfig.NETHER_SULFUR_ORE_EXPOSED_DISCARD_CHANCE.get())))),

				List.of(HeightRangePlacement.uniform(VerticalAnchor.absolute(CommonConfig.NETHER_SULFUR_ORE_BOTTOM_ANCHOR.get()),
								VerticalAnchor.absolute(CommonConfig.NETHER_SULFUR_ORE_TOP_ANCHOR.get())),
						BiomeFilter.biome(),
						InSquarePlacement.spread(),
						CountPlacement.of(CommonConfig.NETHER_SULFUR_ORE_WEIGHT.get())
				));
		SULFUR_ORE_BLOB_PLACEMENT = new PlacedFeature(
				Holder.direct(new ConfiguredFeature<>(Feature.ORE,
						new OreConfiguration(OreReplacementTargets.SULFUR_ORE_TARGETS, CommonConfig.SULFUR_ORE_SIZE.get(),
								(float) ((double) CommonConfig.SULFUR_ORE_EXPOSED_DISCARD_CHANCE.get())))),

				List.of(HeightRangePlacement.uniform(VerticalAnchor.absolute(CommonConfig.SULFUR_ORE_BOTTOM_ANCHOR.get()),
								VerticalAnchor.absolute(CommonConfig.SULFUR_ORE_TOP_ANCHOR.get())),
						InSquarePlacement.spread(),
						BiomeFilter.biome(),
						CountPlacement.of(CommonConfig.SULFUR_ORE_WEIGHT.get())
				));
		DEEPSLATE_SULFUR_ORE_BLOB_PLACEMENT = new PlacedFeature(
				Holder.direct(new ConfiguredFeature<>(Feature.ORE,
						new OreConfiguration(OreReplacementTargets.SULFUR_ORE_TARGETS, CommonConfig.DEEPSLATE_SULFUR_ORE_SIZE.get(),
								(float) ((double) CommonConfig.DEEPSLATE_SULFUR_ORE_EXPOSED_DISCARD_CHANCE.get())))),

				List.of(HeightRangePlacement.uniform(VerticalAnchor.absolute(CommonConfig.DEEPSLATE_SULFUR_ORE_BOTTOM_ANCHOR.get()),
								VerticalAnchor.absolute(CommonConfig.DEEPSLATE_SULFUR_ORE_TOP_ANCHOR.get())),
						InSquarePlacement.spread(),
						BiomeFilter.biome(),
						CountPlacement.of(CommonConfig.DEEPSLATE_SULFUR_ORE_WEIGHT.get())
				));
		COBALT_ORE_BLOB_PLACEMENT = new PlacedFeature(
				Holder.direct(new ConfiguredFeature<>(Feature.ORE,
						new OreConfiguration(OreReplacementTargets.COBALT_ORE_TARGETS, CommonConfig.COBALT_ORE_SIZE.get(),
								(float) ((double) CommonConfig.COBALT_ORE_EXPOSED_DISCARD_CHANCE.get())))),

				List.of(HeightRangePlacement.triangle(VerticalAnchor.absolute(CommonConfig.COBALT_ORE_BOTTOM_ANCHOR.get()),
								VerticalAnchor.absolute(CommonConfig.COBALT_ORE_TOP_ANCHOR.get())),
						InSquarePlacement.spread(),
						BiomeFilter.biome(),
						CountPlacement.of(CommonConfig.COBALT_ORE_WEIGHT.get())
				));
		DEEPSLATE_COBALT_ORE_BLOB_PLACEMENT = new PlacedFeature(
				Holder.direct(new ConfiguredFeature<>(Feature.ORE,
						new OreConfiguration(OreReplacementTargets.COBALT_ORE_TARGETS, CommonConfig.DEEPSLATE_COBALT_ORE_SIZE.get(),
								(float) ((double) CommonConfig.DEEPSLATE_COBALT_ORE_EXPOSED_DISCARD_CHANCE.get())))),

				List.of(HeightRangePlacement.uniform(VerticalAnchor.absolute(CommonConfig.DEEPSLATE_COBALT_ORE_BOTTOM_ANCHOR.get()),
								VerticalAnchor.absolute(CommonConfig.DEEPSLATE_COBALT_ORE_TOP_ANCHOR.get())),
						InSquarePlacement.spread(),
						BiomeFilter.biome(),
						CountPlacement.of(CommonConfig.DEEPSLATE_COBALT_ORE_WEIGHT.get())
				));
	}

	public static class OreReplacementTargets {
		public static final List<OreConfiguration.TargetBlockState> MOLTEN_ORE_TARGETS = List.of(
				OreConfiguration.target(ReplacementRules.NETHER_STONE,
						DeferredRegistryHandler.MOLTEN_ORE.get().defaultBlockState())
		);
		public static final List<OreConfiguration.TargetBlockState> SULFUR_ORE_TARGETS = List.of(
				OreConfiguration.target(ReplacementRules.REGULAR_STONE,
						DeferredRegistryHandler.SULFUR_ORE.get().defaultBlockState()),

				OreConfiguration.target(ReplacementRules.DEEPSLATE_STONE,
						DeferredRegistryHandler.DEEPSLATE_SULFUR_ORE.get().defaultBlockState()),

				OreConfiguration.target(ReplacementRules.NETHER_STONE,
						DeferredRegistryHandler.NETHER_SULFUR_ORE.get().defaultBlockState())
		);
		public static final List<OreConfiguration.TargetBlockState> COBALT_ORE_TARGETS = List.of(
				OreConfiguration.target(ReplacementRules.REGULAR_STONE,
						DeferredRegistryHandler.COBALT_ORE.get().defaultBlockState()),
				OreConfiguration.target(ReplacementRules.DEEPSLATE_STONE,
						DeferredRegistryHandler.DEEPSLATE_COBALT_ORE.get().defaultBlockState())
		);
	}

	public static final class ReplacementRules {
		public static final RuleTest REGULAR_STONE = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
		public static final RuleTest DEEPSLATE_STONE = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

		public static final RuleTest NETHER_STONE = createRuleFromTag("forge:ore_bearing_ground/netherrack");

		public static RuleTest createRuleFromTag(String tagLocation) {
			TagKey<Block> blockTag = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(tagLocation));
			return new TagMatchTest(blockTag);
		}
	}
}