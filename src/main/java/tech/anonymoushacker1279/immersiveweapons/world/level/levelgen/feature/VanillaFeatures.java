package tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.feature;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.placement.*;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeGenerationSettings.Builder;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

/**
 * Contains default biome features from {@link net.minecraft.data.worldgen.BiomeDefaultFeatures}. The methods here
 * accept a {@link Registry} so that the features can be grabbed from a builtinCopy(). Using {@link net.minecraft.data.worldgen.BiomeDefaultFeatures}
 * directly will result in a crash as it uses a different registry, and holders must be from the same set of registries.
 */
public class VanillaFeatures {

	public static void addDefaultCarversAndLakes(Registry<PlacedFeature> featureRegistry,
	                                             Registry<ConfiguredWorldCarver<?>> carverRegistry,
	                                             Builder pBuilder) {
		pBuilder.addCarver(GenerationStep.Carving.AIR, getCarverHolder(carverRegistry, Carvers.CAVE));
		pBuilder.addCarver(GenerationStep.Carving.AIR, getCarverHolder(carverRegistry, Carvers.CAVE_EXTRA_UNDERGROUND));
		pBuilder.addCarver(GenerationStep.Carving.AIR, getCarverHolder(carverRegistry, Carvers.CANYON));

		pBuilder.addFeature(GenerationStep.Decoration.LAKES, getFeatureHolder(featureRegistry, MiscOverworldPlacements.LAKE_LAVA_UNDERGROUND));
		pBuilder.addFeature(GenerationStep.Decoration.LAKES, getFeatureHolder(featureRegistry, MiscOverworldPlacements.LAKE_LAVA_SURFACE));
	}

	public static void addDefaultCrystalFormations(Registry<PlacedFeature> featureRegistry,
	                                               BiomeGenerationSettings.Builder pBuilder) {
		pBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, getFeatureHolder(featureRegistry, CavePlacements.AMETHYST_GEODE));
	}

	public static void addDefaultMonsterRoom(Registry<PlacedFeature> featureRegistry, BiomeGenerationSettings.Builder pBuilder) {
		pBuilder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, getFeatureHolder(featureRegistry, CavePlacements.MONSTER_ROOM));
		pBuilder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, getFeatureHolder(featureRegistry, CavePlacements.MONSTER_ROOM_DEEP));
	}

	public static void addDefaultUndergroundVariety(Registry<PlacedFeature> featureRegistry,
	                                                BiomeGenerationSettings.Builder pBuilder) {
		pBuilder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, getFeatureHolder(featureRegistry, OrePlacements.ORE_DIRT));
		pBuilder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, getFeatureHolder(featureRegistry, OrePlacements.ORE_GRAVEL));
		pBuilder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, getFeatureHolder(featureRegistry, OrePlacements.ORE_GRANITE_UPPER));
		pBuilder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, getFeatureHolder(featureRegistry, OrePlacements.ORE_GRANITE_LOWER));
		pBuilder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, getFeatureHolder(featureRegistry, OrePlacements.ORE_DIORITE_UPPER));
		pBuilder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, getFeatureHolder(featureRegistry, OrePlacements.ORE_DIORITE_LOWER));
		pBuilder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, getFeatureHolder(featureRegistry, OrePlacements.ORE_ANDESITE_UPPER));
		pBuilder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, getFeatureHolder(featureRegistry, OrePlacements.ORE_ANDESITE_LOWER));
		pBuilder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, getFeatureHolder(featureRegistry, OrePlacements.ORE_TUFF));
		pBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, getFeatureHolder(featureRegistry, CavePlacements.GLOW_LICHEN));
	}

	public static void addDefaultSprings(Registry<PlacedFeature> featureRegistry,
	                                     BiomeGenerationSettings.Builder pBuilder) {
		pBuilder.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, getFeatureHolder(featureRegistry, MiscOverworldPlacements.SPRING_WATER));
		pBuilder.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, getFeatureHolder(featureRegistry, MiscOverworldPlacements.SPRING_LAVA));
	}

	public static void addSurfaceFreezing(Registry<PlacedFeature> featureRegistry,
	                                      BiomeGenerationSettings.Builder pBuilder) {
		pBuilder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, getFeatureHolder(featureRegistry, MiscOverworldPlacements.FREEZE_TOP_LAYER));
	}

	public static void addPlainVegetation(Registry<PlacedFeature> featureRegistry,
	                                      BiomeGenerationSettings.Builder pBuilder) {
		pBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, getFeatureHolder(featureRegistry, VegetationPlacements.TREES_PLAINS));
		pBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, getFeatureHolder(featureRegistry, VegetationPlacements.FLOWER_PLAINS));
		pBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, getFeatureHolder(featureRegistry, VegetationPlacements.PATCH_GRASS_PLAIN));
	}

	public static void addDefaultMushrooms(Registry<PlacedFeature> featureRegistry,
	                                       BiomeGenerationSettings.Builder pBuilder) {
		pBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, getFeatureHolder(featureRegistry, VegetationPlacements.BROWN_MUSHROOM_NORMAL));
		pBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, getFeatureHolder(featureRegistry, VegetationPlacements.RED_MUSHROOM_NORMAL));
	}

	public static void addDefaultExtraVegetation(Registry<PlacedFeature> featureRegistry,
	                                             BiomeGenerationSettings.Builder pBuilder) {
		pBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, getFeatureHolder(featureRegistry, VegetationPlacements.PATCH_SUGAR_CANE));
		pBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, getFeatureHolder(featureRegistry, VegetationPlacements.PATCH_PUMPKIN));
	}

	/**
	 * Gets a {@link Holder} for the given {@link PlacedFeature} holder. Useful for adding vanilla features
	 * from a builtinCopy() of a registry.
	 *
	 * @param featureRegistry the registry to get the holder from
	 * @param holder          the holder to get
	 * @return a holder containing a {@link PlacedFeature}
	 */
	private static Holder<PlacedFeature> getFeatureHolder(Registry<PlacedFeature> featureRegistry, Holder<?> holder) {
		return featureRegistry.getOrCreateHolderOrThrow(holder.unwrapKey().flatMap(resourceKey -> resourceKey.cast(Registry.PLACED_FEATURE_REGISTRY)).get());
	}

	/**
	 * Gets a {@link Holder} for the given {@link ConfiguredWorldCarver} holder. Useful for adding vanilla features
	 * from a builtinCopy() of a registry.
	 *
	 * @param carverRegistry the registry to get the holder from
	 * @param holder         the holder to get
	 * @return a holder containing a {@link ConfiguredWorldCarver}
	 */
	private static Holder<? extends ConfiguredWorldCarver<?>> getCarverHolder(Registry<ConfiguredWorldCarver<?>> carverRegistry, Holder<?> holder) {
		return carverRegistry.getOrCreateHolderOrThrow(holder.unwrapKey().flatMap(resourceKey -> resourceKey.cast(Registry.CONFIGURED_CARVER_REGISTRY)).get());
	}
}