package tech.anonymoushacker1279.immersiveweapons.data.features;

import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;

import java.util.List;

public class IWPlacedFeatures {

	public static final ResourceKey<PlacedFeature> PATCH_WOODEN_SPIKES = createKey("patch_wooden_spikes");
	public static final ResourceKey<PlacedFeature> BURNED_OAK_TREE = createKey("burned_oak_tree");
	public static final ResourceKey<PlacedFeature> PATCH_MOONGLOW = createKey("patch_moonglow");
	public static final ResourceKey<PlacedFeature> STARDUST_TREE = createKey("stardust_tree");
	public static final ResourceKey<PlacedFeature> PATCH_DEATHWEED = createKey("patch_deathweed");
	public static final ResourceKey<PlacedFeature> ASTRAL_GEODE = createKey("astral_geode");

	public static final ResourceKey<PlacedFeature> MOLTEN_ORE = createKey("molten_ore");
	public static final ResourceKey<PlacedFeature> NETHER_SULFUR_ORE = createKey("nether_sulfur_ore");
	public static final ResourceKey<PlacedFeature> DEEPSLATE_SULFUR_ORE = createKey("deepslate_sulfur_ore");
	public static final ResourceKey<PlacedFeature> SULFUR_ORE = createKey("sulfur_ore");
	public static final ResourceKey<PlacedFeature> DEEPSLATE_COBALT_ORE = createKey("deepslate_cobalt_ore");
	public static final ResourceKey<PlacedFeature> COBALT_ORE = createKey("cobalt_ore");
	public static final ResourceKey<PlacedFeature> POTASSIUM_NITRATE_ORE = createKey("potassium_nitrate_ore");

	private static ResourceKey<PlacedFeature> createKey(String name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(ImmersiveWeapons.MOD_ID, name));
	}

	public static void bootstrap(BootstapContext<PlacedFeature> context) {
		HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

		register(context, PATCH_WOODEN_SPIKES, configuredFeatures.getOrThrow(IWConfiguredFeatures.PATCH_WOODEN_SPIKES_CONFIGURATION),
				List.of(
						HeightmapPlacement.onHeightmap(Types.MOTION_BLOCKING),
						BiomeFilter.biome(),
						InSquarePlacement.spread(),
						CountPlacement.of(UniformInt.of(4, 12)),
						RarityFilter.onAverageOnceEvery(16)
				));

		register(context, BURNED_OAK_TREE, configuredFeatures.getOrThrow(IWConfiguredFeatures.BURNED_OAK_TREE_CONFIGURATION),
				List.of(
						HeightmapPlacement.onHeightmap(Types.WORLD_SURFACE),
						BiomeFilter.biome(),
						InSquarePlacement.spread(),
						RarityFilter.onAverageOnceEvery(16),
						BlockPredicateFilter.forPredicate(
								BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO))
				));

		register(context, PATCH_MOONGLOW, configuredFeatures.getOrThrow(IWConfiguredFeatures.PATCH_MOONGLOW_CONFIGURATION),
				List.of(
						HeightmapPlacement.onHeightmap(Types.WORLD_SURFACE),
						BiomeFilter.biome(),
						InSquarePlacement.spread()
				));

		register(context, STARDUST_TREE, configuredFeatures.getOrThrow(IWConfiguredFeatures.STARDUST_TREE_CONFIGURATION),
				List.of(
						HeightmapPlacement.onHeightmap(Types.WORLD_SURFACE),
						BiomeFilter.biome(),
						InSquarePlacement.spread(),
						RarityFilter.onAverageOnceEvery(8),
						BlockPredicateFilter.forPredicate(
								BlockPredicate.wouldSurvive(BlockRegistry.STARDUST_SAPLING.get().defaultBlockState(), BlockPos.ZERO))
				));

		register(context, PATCH_DEATHWEED, configuredFeatures.getOrThrow(IWConfiguredFeatures.PATCH_DEATHWEED_CONFIGURATION),
				List.of(
						HeightmapPlacement.onHeightmap(Types.WORLD_SURFACE),
						BiomeFilter.biome(),
						InSquarePlacement.spread(),
						RarityFilter.onAverageOnceEvery(3)
				));

		register(context, ASTRAL_GEODE, configuredFeatures.getOrThrow(IWConfiguredFeatures.ASTRAL_GEODE_CONFIGURATION),
				List.of(
						HeightmapPlacement.onHeightmap(Types.WORLD_SURFACE),
						BiomeFilter.biome(),
						InSquarePlacement.spread(),
						RarityFilter.onAverageOnceEvery(4)
				));

		register(context, MOLTEN_ORE, configuredFeatures.getOrThrow(IWConfiguredFeatures.MOLTEN_ORE_CONFIGURATION),
				List.of(HeightRangePlacement.triangle(VerticalAnchor.absolute(0),
								VerticalAnchor.absolute(48)),
						BiomeFilter.biome(),
						InSquarePlacement.spread(),
						CountPlacement.of(6)
				));

		register(context, NETHER_SULFUR_ORE, configuredFeatures.getOrThrow(IWConfiguredFeatures.NETHER_SULFUR_ORE_CONFIGURATION),
				List.of(HeightRangePlacement.triangle(VerticalAnchor.absolute(-16),
								VerticalAnchor.absolute(128)),
						BiomeFilter.biome(),
						InSquarePlacement.spread(),
						CountPlacement.of(12)
				));

		register(context, DEEPSLATE_SULFUR_ORE, configuredFeatures.getOrThrow(IWConfiguredFeatures.DEEPSLATE_SULFUR_ORE_CONFIGURATION),
				List.of(HeightRangePlacement.triangle(VerticalAnchor.absolute(-64),
								VerticalAnchor.absolute(0)),
						BiomeFilter.biome(),
						InSquarePlacement.spread(),
						CountPlacement.of(4)
				));

		register(context, SULFUR_ORE, configuredFeatures.getOrThrow(IWConfiguredFeatures.SULFUR_ORE_CONFIGURATION),
				List.of(HeightRangePlacement.uniform(VerticalAnchor.absolute(32),
								VerticalAnchor.absolute(72)),
						BiomeFilter.biome(),
						InSquarePlacement.spread(),
						CountPlacement.of(6)
				));

		register(context, DEEPSLATE_COBALT_ORE, configuredFeatures.getOrThrow(IWConfiguredFeatures.DEEPSLATE_COBALT_ORE_CONFIGURATION),
				List.of(HeightRangePlacement.triangle(VerticalAnchor.absolute(-64),
								VerticalAnchor.absolute(0)),
						BiomeFilter.biome(),
						InSquarePlacement.spread(),
						CountPlacement.of(12)
				));

		register(context, COBALT_ORE, configuredFeatures.getOrThrow(IWConfiguredFeatures.COBALT_ORE_CONFIGURATION),
				List.of(HeightRangePlacement.triangle(VerticalAnchor.absolute(7),
								VerticalAnchor.absolute(196)),
						BiomeFilter.biome(),
						InSquarePlacement.spread(),
						CountPlacement.of(4)
				));

		register(context, POTASSIUM_NITRATE_ORE, configuredFeatures.getOrThrow(IWConfiguredFeatures.POTASSIUM_NITRATE_ORE_CONFIGURATION),
				List.of(HeightRangePlacement.triangle(VerticalAnchor.absolute(10),
								VerticalAnchor.absolute(50)),
						BiomeFilter.biome(),
						InSquarePlacement.spread(),
						CountPlacement.of(8)
				));
	}

	private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
		context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
	}
}