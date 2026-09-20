package tech.anonymoushacker1279.immersiveweapons.data.features;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.Feature;
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
	public static final ResourceKey<PlacedFeature> PATCH_FIREFLY_BUSH = createKey("patch_firefly_bush");

	public static final ResourceKey<PlacedFeature> MOLTEN_ORE = createKey("molten_ore");
	public static final ResourceKey<PlacedFeature> TESLA_ORE = createKey("tesla_ore");
	public static final ResourceKey<PlacedFeature> DEEPSLATE_COBALT_ORE = createKey("deepslate_cobalt_ore");
	public static final ResourceKey<PlacedFeature> COBALT_ORE = createKey("cobalt_ore");
	public static final ResourceKey<PlacedFeature> VOID_ORE = createKey("void_ore");

	private static ResourceKey<PlacedFeature> createKey(String name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, name));
	}

	public static void bootstrap(BootstrapContext<PlacedFeature> context) {
		HolderGetter<Feature> configuredFeatures = context.lookup(Registries.FEATURE);

		// Modifier order matters: the count/rarity comes first, then the horizontal spread, then the height, and
		// the biome check last (the same as vanilla). Otherwise, every attempt lands at the same position, or the
		// height is taken from a different column than the one the feature is placed in.
		register(context, PATCH_WOODEN_SPIKES, configuredFeatures.getOrThrow(IWConfiguredFeatures.PATCH_WOODEN_SPIKES_CONFIGURATION),
				List.of(
						CountPlacement.of(UniformInt.of(4, 12)),
						RarityFilter.onAverageOnceEvery(16),
						InSquarePlacement.spread(),
						HeightmapPlacement.onHeightmap(Types.MOTION_BLOCKING),
						BiomeFilter.biome()
				));

		register(context, BURNED_OAK_TREE, configuredFeatures.getOrThrow(IWConfiguredFeatures.BURNED_OAK_TREE_CONFIGURATION),
				List.of(
						RarityFilter.onAverageOnceEvery(16),
						InSquarePlacement.spread(),
						HeightmapPlacement.onHeightmap(Types.WORLD_SURFACE),
						BiomeFilter.biome(),
						PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
				));

		register(context, PATCH_MOONGLOW, configuredFeatures.getOrThrow(IWConfiguredFeatures.PATCH_MOONGLOW_CONFIGURATION),
				List.of(
						InSquarePlacement.spread(),
						HeightmapPlacement.onHeightmap(Types.WORLD_SURFACE),
						BiomeFilter.biome()
				));

		register(context, STARDUST_TREE, configuredFeatures.getOrThrow(IWConfiguredFeatures.STARDUST_TREE_CONFIGURATION),
				List.of(
						RarityFilter.onAverageOnceEvery(8),
						InSquarePlacement.spread(),
						HeightmapPlacement.onHeightmap(Types.WORLD_SURFACE),
						BiomeFilter.biome(),
						PlacementUtils.filteredByBlockSurvival(BlockRegistry.STARDUST_SAPLING.get())
				));

		register(context, PATCH_DEATHWEED, configuredFeatures.getOrThrow(IWConfiguredFeatures.PATCH_DEATHWEED_CONFIGURATION),
				List.of(
						RarityFilter.onAverageOnceEvery(3),
						InSquarePlacement.spread(),
						HeightmapPlacement.onHeightmap(Types.WORLD_SURFACE),
						BiomeFilter.biome()
				));

		register(context, ASTRAL_GEODE, configuredFeatures.getOrThrow(IWConfiguredFeatures.ASTRAL_GEODE_CONFIGURATION),
				List.of(
						RarityFilter.onAverageOnceEvery(4),
						InSquarePlacement.spread(),
						HeightmapPlacement.onHeightmap(Types.WORLD_SURFACE),
						BiomeFilter.biome()
				));

		register(context, PATCH_FIREFLY_BUSH, configuredFeatures.getOrThrow(VegetationFeatures.FIREFLY_BUSH),
				List.of(
						CountPlacement.of(2),
						InSquarePlacement.spread(),
						PlacementUtils.HEIGHTMAP_NO_LEAVES,
						BiomeFilter.biome()
				));

		register(context, MOLTEN_ORE, configuredFeatures.getOrThrow(IWConfiguredFeatures.MOLTEN_ORE_CONFIGURATION),
				orePlacement(6, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(48))));

		register(context, TESLA_ORE, configuredFeatures.getOrThrow(IWConfiguredFeatures.TESLA_ORE_CONFIGURATION),
				orePlacement(2, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-28))));

		register(context, DEEPSLATE_COBALT_ORE, configuredFeatures.getOrThrow(IWConfiguredFeatures.DEEPSLATE_COBALT_ORE_CONFIGURATION),
				orePlacement(12, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(0))));

		register(context, COBALT_ORE, configuredFeatures.getOrThrow(IWConfiguredFeatures.COBALT_ORE_CONFIGURATION),
				orePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.absolute(7), VerticalAnchor.absolute(196))));

		register(context, VOID_ORE, configuredFeatures.getOrThrow(IWConfiguredFeatures.VOID_ORE_CONFIGURATION),
				orePlacement(5, HeightRangePlacement.triangle(VerticalAnchor.absolute(16), VerticalAnchor.absolute(112))));
	}

	/// Ore placement in the same order as vanilla. The count must come first, so that each attempt gets its own
	/// horizontal position and height.
	private static List<PlacementModifier> orePlacement(int count, PlacementModifier heightRange) {
		return List.of(CountPlacement.of(count), InSquarePlacement.spread(), heightRange, BiomeFilter.biome());
	}

	private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<Feature> configuration, List<PlacementModifier> modifiers) {
		context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
	}
}
