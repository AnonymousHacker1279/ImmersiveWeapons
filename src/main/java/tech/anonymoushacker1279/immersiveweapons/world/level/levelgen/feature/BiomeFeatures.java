package tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.*;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.feature.treedecorators.BurnedBranchDecorator;

import java.util.List;

public class BiomeFeatures {

	public static PlacedFeature PATCH_WOODEN_SPIKES_FEATURE;
	public static PlacedFeature BURNED_OAK_TREE_FEATURE;
	public static PlacedFeature PATCH_MOONGLOW_FEATURE;
	public static Holder<ConfiguredFeature<?, ?>> STARDUST_TREE_FEATURE_HOLDER;
	public static PlacedFeature STARDUST_TREE_FEATURE;
	public static PlacedFeature PATCH_DEATHWEED_FEATURE;
	public static PlacedFeature ASTRAL_GEODE_FEATURE;

	public static void init() {
		PATCH_WOODEN_SPIKES_FEATURE = new PlacedFeature(
				Holder.direct(new ConfiguredFeature<>(Feature.BLOCK_PILE,
						new BlockPileConfiguration(BlockStateProvider.simple(BlockRegistry.WOODEN_SPIKES.get())))
				), List.of(
				HeightmapPlacement.onHeightmap(Types.MOTION_BLOCKING),
				BiomeFilter.biome(),
				InSquarePlacement.spread(),
				CountPlacement.of(UniformInt.of(4, 12)),
				RarityFilter.onAverageOnceEvery(16)
		));

		BURNED_OAK_TREE_FEATURE = new PlacedFeature(
				Holder.direct(new ConfiguredFeature<>(Feature.TREE,
						new TreeConfiguration.TreeConfigurationBuilder(
								BlockStateProvider.simple(BlockRegistry.BURNED_OAK_LOG.get()),
								new StraightTrunkPlacer(7, 3, 3),
								BlockStateProvider.simple(Blocks.AIR),
								new BlobFoliagePlacer(ConstantInt.ZERO, ConstantInt.ZERO, 0),
								new TwoLayersFeatureSize(1, 0, 1)
						)
								.decorators(List.of(new BurnedBranchDecorator(0.95f)))
								.ignoreVines()
								.build())
				), List.of(
				HeightmapPlacement.onHeightmap(Types.WORLD_SURFACE),
				BiomeFilter.biome(),
				InSquarePlacement.spread(),
				RarityFilter.onAverageOnceEvery(16),
				BlockPredicateFilter.forPredicate(
						BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO))
		));

		PATCH_MOONGLOW_FEATURE = new PlacedFeature(
				Holder.direct(new ConfiguredFeature<>(Feature.RANDOM_PATCH,
						new RandomPatchConfiguration(16, 6, 2,
								PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
										new SimpleBlockConfiguration(BlockStateProvider.simple(
												BlockRegistry.MOONGLOW.get()
										)))))
				), List.of(
				HeightmapPlacement.onHeightmap(Types.WORLD_SURFACE),
				BiomeFilter.biome(),
				InSquarePlacement.spread()
		));

		STARDUST_TREE_FEATURE_HOLDER = Holder.direct(new ConfiguredFeature<>(Feature.TREE,
				new TreeConfiguration.TreeConfigurationBuilder(
						BlockStateProvider.simple(BlockRegistry.STARDUST_LOG.get()),
						new FancyTrunkPlacer(5, 3, 3),
						BlockStateProvider.simple(BlockRegistry.STARDUST_LEAVES.get()),
						new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
						new TwoLayersFeatureSize(1, 0, 3)
				)
						.ignoreVines()
						.build())
		);

		STARDUST_TREE_FEATURE = new PlacedFeature(
				STARDUST_TREE_FEATURE_HOLDER, List.of(
				HeightmapPlacement.onHeightmap(Types.WORLD_SURFACE),
				BiomeFilter.biome(),
				InSquarePlacement.spread(),
				RarityFilter.onAverageOnceEvery(8),
				BlockPredicateFilter.forPredicate(
						BlockPredicate.wouldSurvive(BlockRegistry.STARDUST_SAPLING.get().defaultBlockState(), BlockPos.ZERO))
		));

		PATCH_DEATHWEED_FEATURE = new PlacedFeature(
				Holder.direct(new ConfiguredFeature<>(Feature.RANDOM_PATCH,
						new RandomPatchConfiguration(8, 6, 2,
								PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
										new SimpleBlockConfiguration(BlockStateProvider.simple(
												BlockRegistry.DEATHWEED.get()
										)))))
				), List.of(
				HeightmapPlacement.onHeightmap(Types.WORLD_SURFACE),
				BiomeFilter.biome(),
				InSquarePlacement.spread(),
				RarityFilter.onAverageOnceEvery(3)
		));

		ASTRAL_GEODE_FEATURE = new PlacedFeature(
				Holder.direct(new ConfiguredFeature<>(Feature.GEODE,
						new GeodeConfiguration(
								new GeodeBlockSettings(
										BlockStateProvider.simple(Blocks.AIR.defaultBlockState()),
										BlockStateProvider.simple(Blocks.SMOOTH_QUARTZ.defaultBlockState()),
										BlockStateProvider.simple(BlockRegistry.ASTRAL_ORE.get().defaultBlockState()),
										BlockStateProvider.simple(Blocks.CALCITE.defaultBlockState()),
										BlockStateProvider.simple(Blocks.TUFF.defaultBlockState()),
										List.of(BlockRegistry.ASTRAL_CRYSTAL.get().defaultBlockState()),
										BlockTags.FEATURES_CANNOT_REPLACE,
										BlockTags.GEODE_INVALID_BLOCKS
								),
								new GeodeLayerSettings(
										1.7d,
										2.2d,
										3.2d,
										4.2d
								),
								new GeodeCrackSettings(
										0.65f,
										1.4d,
										1
								),
								0.05d,
								0.05d,
								false,
								UniformInt.of(3, 5),
								UniformInt.of(3, 4),
								UniformInt.of(1, 2),
								16,
								-16,
								0.035d,
								1
						)
				)), List.of(
				HeightmapPlacement.onHeightmap(Types.WORLD_SURFACE),
				BiomeFilter.biome(),
				InSquarePlacement.spread(),
				RarityFilter.onAverageOnceEvery(4)
		));
	}

	public static final ResourceLocation PATCH_WOODEN_SPIKES = new ResourceLocation(ImmersiveWeapons.MOD_ID, "patch_wooden_spikes");
	public static final ResourceKey<PlacedFeature> PATCH_WOODEN_SPIKES_KEY = ResourceKey.create(Registries.PLACED_FEATURE, PATCH_WOODEN_SPIKES);

	public static final ResourceLocation BURNED_OAK_TREE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "burned_oak_tree");
	public static final ResourceKey<PlacedFeature> BURNED_OAK_TREE_KEY = ResourceKey.create(Registries.PLACED_FEATURE, BURNED_OAK_TREE);

	public static final ResourceLocation PATCH_MOONGLOW = new ResourceLocation(ImmersiveWeapons.MOD_ID, "patch_moonglow");
	public static final ResourceKey<PlacedFeature> PATCH_MOONGLOW_KEY = ResourceKey.create(Registries.PLACED_FEATURE, PATCH_MOONGLOW);

	public static final ResourceLocation STARDUST_TREE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "stardust_tree");
	public static final ResourceKey<PlacedFeature> STARDUST_TREE_KEY = ResourceKey.create(Registries.PLACED_FEATURE, STARDUST_TREE);

	public static final ResourceLocation PATCH_DEATHWEED = new ResourceLocation(ImmersiveWeapons.MOD_ID, "patch_deathweed");
	public static final ResourceKey<PlacedFeature> PATCH_DEATHWEED_KEY = ResourceKey.create(Registries.PLACED_FEATURE, PATCH_DEATHWEED);

	public static final ResourceLocation ASTRAL_GEODE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "astral_geode");
	public static final ResourceKey<PlacedFeature> ASTRAL_GEODE_KEY = ResourceKey.create(Registries.PLACED_FEATURE, ASTRAL_GEODE);
}