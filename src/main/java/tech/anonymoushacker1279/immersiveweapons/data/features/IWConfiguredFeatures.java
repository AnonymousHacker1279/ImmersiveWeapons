package tech.anonymoushacker1279.immersiveweapons.data.features;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.OreReplacementData.OreReplacementTargets;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.feature.treedecorators.BurnedBranchDecorator;

import java.util.List;

public class IWConfiguredFeatures {

	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WOODEN_SPIKES_CONFIGURATION = createKey("patch_wooden_spikes");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BURNED_OAK_TREE_CONFIGURATION = createKey("burned_oak_tree");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_MOONGLOW_CONFIGURATION = createKey("patch_moonglow");
	public static final ResourceKey<ConfiguredFeature<?, ?>> STARDUST_TREE_CONFIGURATION = createKey("stardust_tree");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_DEATHWEED_CONFIGURATION = createKey("patch_deathweed");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ASTRAL_GEODE_CONFIGURATION = createKey("astral_geode");

	public static final ResourceKey<ConfiguredFeature<?, ?>> MOLTEN_ORE_CONFIGURATION = createKey("molten_ore");
	public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_SULFUR_ORE_CONFIGURATION = createKey("nether_sulfur_ore");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DEEPSLATE_SULFUR_ORE_CONFIGURATION = createKey("deepslate_sulfur_ore");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SULFUR_ORE_CONFIGURATION = createKey("sulfur_ore");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DEEPSLATE_COBALT_ORE_CONFIGURATION = createKey("deepslate_cobalt_ore");
	public static final ResourceKey<ConfiguredFeature<?, ?>> COBALT_ORE_CONFIGURATION = createKey("cobalt_ore");

	private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(ImmersiveWeapons.MOD_ID, name));
	}

	public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
		HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

		register(context, PATCH_WOODEN_SPIKES_CONFIGURATION, Feature.BLOCK_PILE,
				new BlockPileConfiguration(BlockStateProvider.simple(BlockRegistry.WOODEN_SPIKES.get()))
		);

		register(context, BURNED_OAK_TREE_CONFIGURATION, Feature.TREE,
				new TreeConfiguration.TreeConfigurationBuilder(
						BlockStateProvider.simple(BlockRegistry.BURNED_OAK_LOG.get()),
						new StraightTrunkPlacer(7, 3, 3),
						BlockStateProvider.simple(Blocks.AIR),
						new BlobFoliagePlacer(ConstantInt.ZERO, ConstantInt.ZERO, 0),
						new TwoLayersFeatureSize(1, 0, 1)
				)
						.decorators(List.of(new BurnedBranchDecorator(0.95f)))
						.ignoreVines()
						.build());

		register(context, PATCH_MOONGLOW_CONFIGURATION, Feature.RANDOM_PATCH,
				new RandomPatchConfiguration(16, 6, 2,
						PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
								new SimpleBlockConfiguration(BlockStateProvider.simple(
										BlockRegistry.MOONGLOW.get()
								)))));

		register(context, STARDUST_TREE_CONFIGURATION, Feature.TREE,
				new TreeConfiguration.TreeConfigurationBuilder(
						BlockStateProvider.simple(BlockRegistry.STARDUST_LOG.get()),
						new FancyTrunkPlacer(5, 3, 3),
						BlockStateProvider.simple(BlockRegistry.STARDUST_LEAVES.get()),
						new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
						new TwoLayersFeatureSize(1, 0, 3)
				)
						.ignoreVines()
						.build());

		register(context, PATCH_DEATHWEED_CONFIGURATION, Feature.RANDOM_PATCH,
				new RandomPatchConfiguration(8, 6, 2,
						PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
								new SimpleBlockConfiguration(BlockStateProvider.simple(
										BlockRegistry.DEATHWEED.get()
								)))));

		register(context, ASTRAL_GEODE_CONFIGURATION, Feature.GEODE,
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
				));

		register(context, MOLTEN_ORE_CONFIGURATION, Feature.ORE,
				new OreConfiguration(OreReplacementTargets.MOLTEN_ORE_TARGETS, 2, 1.0f));

		register(context, NETHER_SULFUR_ORE_CONFIGURATION, Feature.ORE,
				new OreConfiguration(OreReplacementTargets.SULFUR_ORE_TARGETS, 16, 0.08f));

		register(context, DEEPSLATE_SULFUR_ORE_CONFIGURATION, Feature.ORE,
				new OreConfiguration(OreReplacementTargets.SULFUR_ORE_TARGETS, 16, 0.04f));

		register(context, SULFUR_ORE_CONFIGURATION, Feature.ORE,
				new OreConfiguration(OreReplacementTargets.SULFUR_ORE_TARGETS, 6, 0.1f));

		register(context, DEEPSLATE_COBALT_ORE_CONFIGURATION, Feature.ORE,
				new OreConfiguration(OreReplacementTargets.COBALT_ORE_TARGETS, 12, 0.1f));

		register(context, COBALT_ORE_CONFIGURATION, Feature.ORE,
				new OreConfiguration(OreReplacementTargets.COBALT_ORE_TARGETS, 12, 0.15f));
	}

	private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
	                                                                                      ResourceKey<ConfiguredFeature<?, ?>> key,
	                                                                                      F feature, FC configuration) {

		context.register(key, new ConfiguredFeature<>(feature, configuration));
	}
}