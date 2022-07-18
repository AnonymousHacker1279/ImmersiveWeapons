package tech.anonymoushacker1279.immersiveweapons.data.modifiers;

import com.google.gson.JsonElement;
import net.minecraft.core.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.*;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
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
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.ImmersiveWeaponsWorldGenTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.feature.treedecorators.BurnedBranchDecorator;

import java.util.HashMap;
import java.util.List;

public class FeatureBiomeModifiers {

	public static class PlacedFeatures {
		protected static final ResourceLocation PATCH_WOODEN_SPIKES = new ResourceLocation(ImmersiveWeapons.MOD_ID, "patch_wooden_spikes");
		protected static final ResourceKey<PlacedFeature> PATCH_WOODEN_SPIKES_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, PATCH_WOODEN_SPIKES);
		protected static final PlacedFeature PATCH_WOODEN_SPIKES_FEATURE = new PlacedFeature(
				Holder.direct(new ConfiguredFeature<>(Feature.BLOCK_PILE,
						new BlockPileConfiguration(BlockStateProvider.simple(DeferredRegistryHandler.WOODEN_SPIKES.get())))
				), List.of(
				HeightmapPlacement.onHeightmap(Types.MOTION_BLOCKING),
				BiomeFilter.biome(),
				InSquarePlacement.spread(),
				CountPlacement.of(UniformInt.of(4, 12)),
				RarityFilter.onAverageOnceEvery(16)
		));

		protected static final ResourceLocation BURNED_OAK_TREE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "burned_oak_tree");
		protected static final ResourceKey<PlacedFeature> BURNED_OAK_TREE_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, BURNED_OAK_TREE);
		protected static final PlacedFeature BURNED_OAK_TREE_FEATURE = new PlacedFeature(
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

		protected static final ResourceLocation PATCH_MOONGLOW = new ResourceLocation(ImmersiveWeapons.MOD_ID, "patch_moonglow");
		protected static final ResourceKey<PlacedFeature> PATCH_MOONGLOW_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, PATCH_MOONGLOW);
		protected static final PlacedFeature PATCH_MOONGLOW_FEATURE = new PlacedFeature(
				Holder.direct(new ConfiguredFeature<>(Feature.RANDOM_PATCH,
						new RandomPatchConfiguration(16, 6, 2,
								PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
										new SimpleBlockConfiguration(BlockStateProvider.simple(
												DeferredRegistryHandler.MOONGLOW.get()
										)))))
				), List.of(
				HeightmapPlacement.onHeightmap(Types.WORLD_SURFACE),
				BiomeFilter.biome(),
				InSquarePlacement.spread()
		));

		protected static final ResourceLocation STARDUST_TREE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "stardust_tree");
		protected static final ResourceKey<PlacedFeature> STARDUST_TREE_KEY = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, STARDUST_TREE);
		public static final Holder<ConfiguredFeature<?, ?>> STARDUST_TREE_HOLDER = Holder.direct(new ConfiguredFeature<>(Feature.TREE,
				new TreeConfiguration.TreeConfigurationBuilder(
						BlockStateProvider.simple(DeferredRegistryHandler.STARDUST_LOG.get()),
						new FancyTrunkPlacer(5, 3, 3),
						BlockStateProvider.simple(DeferredRegistryHandler.STARDUST_LEAVES.get()),
						new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
						new TwoLayersFeatureSize(1, 0, 3)
				)
						.ignoreVines()
						.build())
		);
		protected static final PlacedFeature STARDUST_TREE_FEATURE = new PlacedFeature(
				STARDUST_TREE_HOLDER, List.of(
				HeightmapPlacement.onHeightmap(Types.WORLD_SURFACE),
				BiomeFilter.biome(),
				InSquarePlacement.spread(),
				RarityFilter.onAverageOnceEvery(8),
				BlockPredicateFilter.forPredicate(
						BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO)) // TODO: Replace with custom sapling when available
		));

		public static JsonCodecProvider<PlacedFeature> getCodecProvider(DataGenerator generator,
		                                                                ExistingFileHelper existingFileHelper,
		                                                                RegistryOps<JsonElement> registryOps,
		                                                                ResourceKey<Registry<PlacedFeature>> placedFeatureKey) {

			return JsonCodecProvider.forDatapackRegistry(
					generator, existingFileHelper, ImmersiveWeapons.MOD_ID, registryOps, placedFeatureKey, getPlacedFeatures());
		}

		private static HashMap<ResourceLocation, PlacedFeature> getPlacedFeatures() {
			HashMap<ResourceLocation, PlacedFeature> placedFeatures = new HashMap<>(10);
			placedFeatures.put(PATCH_WOODEN_SPIKES, PATCH_WOODEN_SPIKES_FEATURE);
			placedFeatures.put(BURNED_OAK_TREE, BURNED_OAK_TREE_FEATURE);
			placedFeatures.put(PATCH_MOONGLOW, PATCH_MOONGLOW_FEATURE);
			placedFeatures.put(STARDUST_TREE, STARDUST_TREE_FEATURE);

			return placedFeatures;
		}
	}

	private static final ResourceLocation ADD_PATCH_WOODEN_SPIKES = new ResourceLocation(ImmersiveWeapons.MOD_ID, "add_patch_wooden_spikes");
	private static BiomeModifier addPatchWoodenSpikesFeature;
	private static final ResourceLocation ADD_BURNED_OAK_TREE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "add_burned_oak_tree");
	private static BiomeModifier addBurnedOakTreeFeature;
	private static final ResourceLocation ADD_PATCH_MOONGLOW = new ResourceLocation(ImmersiveWeapons.MOD_ID, "add_patch_moonglow");
	private static BiomeModifier addPatchMoonglowFeature;
	private static final ResourceLocation ADD_STARDUST_TREE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "add_stardust_tree");
	private static BiomeModifier addStardustTreeFeature;

	public static JsonCodecProvider<BiomeModifier> getCodecProvider(DataGenerator generator,
	                                                                ExistingFileHelper existingFileHelper,
	                                                                RegistryOps<JsonElement> registryOps,
	                                                                ResourceKey<Registry<BiomeModifier>> biomeModifiersKey) {

		fillFeatures(registryOps);
		return JsonCodecProvider.forDatapackRegistry(
				generator, existingFileHelper, ImmersiveWeapons.MOD_ID, registryOps, biomeModifiersKey, getBiomeModifiers());
	}

	private static void fillFeatures(RegistryOps<JsonElement> registryOps) {
		HolderSet.Named<Biome> battlefieldTag = new HolderSet.Named<>(registryOps.registry(Registry.BIOME_REGISTRY).get(), ImmersiveWeaponsWorldGenTagGroups.IS_BATTLEFIELD);
		HolderSet.Named<Biome> starlightPlainsTag = new HolderSet.Named<>(registryOps.registry(Registry.BIOME_REGISTRY).get(), ImmersiveWeaponsWorldGenTagGroups.IS_STARLIGHT_PLAINS);

		addPatchWoodenSpikesFeature = new AddFeaturesBiomeModifier(
				battlefieldTag,
				HolderSet.direct(registryOps.registry(Registry.PLACED_FEATURE_REGISTRY).get()
						.getOrCreateHolderOrThrow(ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, PlacedFeatures.PATCH_WOODEN_SPIKES_KEY.location()))),
				Decoration.VEGETAL_DECORATION);
		addBurnedOakTreeFeature = new AddFeaturesBiomeModifier(
				battlefieldTag,
				HolderSet.direct(registryOps.registry(Registry.PLACED_FEATURE_REGISTRY).get()
						.getOrCreateHolderOrThrow(ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, PlacedFeatures.BURNED_OAK_TREE_KEY.location()))),
				Decoration.VEGETAL_DECORATION);
		addPatchMoonglowFeature = new AddFeaturesBiomeModifier(
				starlightPlainsTag,
				HolderSet.direct(registryOps.registry(Registry.PLACED_FEATURE_REGISTRY).get()
						.getOrCreateHolderOrThrow(ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, PlacedFeatures.PATCH_MOONGLOW_KEY.location()))),
				Decoration.VEGETAL_DECORATION);
		addStardustTreeFeature = new AddFeaturesBiomeModifier(
				starlightPlainsTag,
				HolderSet.direct(registryOps.registry(Registry.PLACED_FEATURE_REGISTRY).get()
						.getOrCreateHolderOrThrow(ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, PlacedFeatures.STARDUST_TREE_KEY.location()))),
				Decoration.VEGETAL_DECORATION);
	}

	private static HashMap<ResourceLocation, BiomeModifier> getBiomeModifiers() {
		HashMap<ResourceLocation, BiomeModifier> modifiers = new HashMap<>(10);
		modifiers.put(ADD_PATCH_WOODEN_SPIKES, addPatchWoodenSpikesFeature);
		modifiers.put(ADD_BURNED_OAK_TREE, addBurnedOakTreeFeature);
		modifiers.put(ADD_PATCH_MOONGLOW, addPatchMoonglowFeature);
		modifiers.put(ADD_STARDUST_TREE, addStardustTreeFeature);

		return modifiers;
	}
}