package tech.anonymoushacker1279.immersiveweapons.data.features;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BlockStateProviders;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.features.OreReplacementData.OreReplacementTargets;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.feature.treedecorators.BurnedBranchDecorator;

import java.util.List;

public class IWConfiguredFeatures {

	public static final ResourceKey<Feature> PATCH_WOODEN_SPIKES_CONFIGURATION = createKey("patch_wooden_spikes");
	public static final ResourceKey<Feature> BURNED_OAK_TREE_CONFIGURATION = createKey("burned_oak_tree");
	public static final ResourceKey<Feature> PATCH_MOONGLOW_CONFIGURATION = createKey("patch_moonglow");
	public static final ResourceKey<Feature> STARDUST_TREE_CONFIGURATION = createKey("stardust_tree");
	public static final ResourceKey<Feature> PATCH_DEATHWEED_CONFIGURATION = createKey("patch_deathweed");
	public static final ResourceKey<Feature> ASTRAL_GEODE_CONFIGURATION = createKey("astral_geode");

	public static final ResourceKey<Feature> MOLTEN_ORE_CONFIGURATION = createKey("molten_ore");
	public static final ResourceKey<Feature> TESLA_ORE_CONFIGURATION = createKey("tesla_ore");
	public static final ResourceKey<Feature> DEEPSLATE_COBALT_ORE_CONFIGURATION = createKey("deepslate_cobalt_ore");
	public static final ResourceKey<Feature> COBALT_ORE_CONFIGURATION = createKey("cobalt_ore");
	public static final ResourceKey<Feature> VOID_ORE_CONFIGURATION = createKey("void_ore");

	private static ResourceKey<Feature> createKey(String name) {
		return ResourceKey.create(Registries.FEATURE, Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, name));
	}

	public static void bootstrap(BootstrapContext<Feature> context) {
		HolderGetter<Block> blockGetter = context.lookup(Registries.BLOCK);
		Holder<BlockStateProvider> belowTrunkProvider = context.lookup(Registries.BLOCK_STATE_PROVIDER).getOrThrow(BlockStateProviders.SOIL_BENEATH_TREE);

		context.register(PATCH_WOODEN_SPIKES_CONFIGURATION,
				new BlockPileFeature(BlockStateProvider.holderOf(BlockRegistry.WOODEN_SPIKES.get())));

		context.register(BURNED_OAK_TREE_CONFIGURATION,
				new TreeFeature.Builder(
						BlockStateProvider.of(BlockRegistry.BURNED_OAK_LOG.get()),
						new StraightTrunkPlacer(7, 3, 3),
						BlockStateProvider.of(Blocks.AIR),
						new BlobFoliagePlacer(ConstantInt.ZERO, ConstantInt.ZERO, 0),
						new TwoLayersFeatureSize(1, 0, 1),
						belowTrunkProvider)
						.decorators(List.of(new BurnedBranchDecorator(0.95f)))
						.ignoreVines()
						.build());

		context.register(PATCH_MOONGLOW_CONFIGURATION,
				new SimpleBlockFeature(BlockStateProvider.of(BlockRegistry.MOONGLOW.get())));

		context.register(STARDUST_TREE_CONFIGURATION,
				new TreeFeature.Builder(
						BlockStateProvider.of(BlockRegistry.STARDUST_LOG.get()),
						new FancyTrunkPlacer(5, 3, 3),
						BlockStateProvider.of(BlockRegistry.STARDUST_LEAVES.get()),
						new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
						new TwoLayersFeatureSize(1, 0, 3),
						belowTrunkProvider)
						.ignoreVines()
						.build());

		context.register(PATCH_DEATHWEED_CONFIGURATION,
				new SimpleBlockFeature(BlockStateProvider.of(BlockRegistry.DEATHWEED.get())));

		context.register(ASTRAL_GEODE_CONFIGURATION,
				new GeodeFeature(
						new GeodeBlockSettings(
								BlockStateProvider.holderOf(Blocks.AIR.defaultBlockState()),
								BlockStateProvider.holderOf(Blocks.SMOOTH_QUARTZ.defaultBlockState()),
								BlockStateProvider.holderOf(BlockRegistry.ASTRAL_ORE.get().defaultBlockState()),
								BlockStateProvider.holderOf(Blocks.CALCITE.defaultBlockState()),
								BlockStateProvider.holderOf(Blocks.TUFF.defaultBlockState()),
								List.of(BlockRegistry.ASTRAL_CRYSTAL.get().defaultBlockState()),
								blockGetter.getOrThrow(BlockTags.FEATURES_CANNOT_REPLACE),
								blockGetter.getOrThrow(BlockTags.GEODE_INVALID_BLOCKS)
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

		context.register(MOLTEN_ORE_CONFIGURATION,
				new OreFeature(OreReplacementTargets.MOLTEN_ORE_TARGETS, 4, 1.0f));

		context.register(TESLA_ORE_CONFIGURATION,
				new OreFeature(OreReplacementTargets.TESLA_ORE_TARGETS, 4, 0.8f));

		context.register(DEEPSLATE_COBALT_ORE_CONFIGURATION,
				new OreFeature(OreReplacementTargets.COBALT_ORE_TARGETS, 12, 0.1f));

		context.register(COBALT_ORE_CONFIGURATION,
				new OreFeature(OreReplacementTargets.COBALT_ORE_TARGETS, 12, 0.15f));

		context.register(VOID_ORE_CONFIGURATION,
				new OreFeature(OreReplacementTargets.VOID_ORE_TARGETS, 4, 1.0f));
	}
}
