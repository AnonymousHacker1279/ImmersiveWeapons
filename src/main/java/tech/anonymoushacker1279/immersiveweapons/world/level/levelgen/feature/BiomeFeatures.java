package tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.feature;

import net.minecraft.core.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.*;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.feature.treedecorators.BurnedBranchDecorator;

import java.util.List;

public class BiomeFeatures {

	public static PlacedFeature PATCH_WOODEN_SPIKES_FEATURE;
	public static PlacedFeature BURNED_OAK_TREE_FEATURE;

	public static void init() {
		PATCH_WOODEN_SPIKES_FEATURE = new PlacedFeature(
				Holder.direct(new ConfiguredFeature<>(Feature.BLOCK_PILE,
						new BlockPileConfiguration(BlockStateProvider.simple(DeferredRegistryHandler.WOODEN_SPIKES.get())))
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
								BlockStateProvider.simple(DeferredRegistryHandler.BURNED_OAK_LOG.get()),
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
	}

	public static final ResourceLocation PATCH_WOODEN_SPIKES = new ResourceLocation(ImmersiveWeapons.MOD_ID, "patch_wooden_spikes");
	public static final ResourceKey<PlacedFeature> PATCH_WOODEN_SPIKES_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, PATCH_WOODEN_SPIKES);

	public static final ResourceLocation BURNED_OAK_TREE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "burned_oak_tree");
	public static final ResourceKey<PlacedFeature> BURNED_OAK_TREE_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, BURNED_OAK_TREE);
}