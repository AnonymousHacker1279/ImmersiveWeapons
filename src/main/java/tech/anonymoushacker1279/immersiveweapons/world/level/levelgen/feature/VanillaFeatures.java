package tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.feature;

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeGenerationSettings.Builder;
import net.minecraft.world.level.levelgen.GenerationStep;


public class VanillaFeatures {

	public static void addPlainGrass(BiomeGenerationSettings.Builder pBuilder) {
		pBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
	}

	public static void getOverworldBaseGeneration(Builder generationBuilder) {
		BiomeDefaultFeatures.addDefaultCarversAndLakes(generationBuilder);
		BiomeDefaultFeatures.addDefaultCrystalFormations(generationBuilder);
		BiomeDefaultFeatures.addDefaultMonsterRoom(generationBuilder);
		BiomeDefaultFeatures.addDefaultUndergroundVariety(generationBuilder);
		BiomeDefaultFeatures.addDefaultOres(generationBuilder);
		BiomeDefaultFeatures.addSurfaceFreezing(generationBuilder);
		BiomeDefaultFeatures.addDefaultSprings(generationBuilder);
	}

	public static void getPlainsLikeGeneration(Builder generationBuilder) {
		BiomeDefaultFeatures.addPlainVegetation(generationBuilder);
		BiomeDefaultFeatures.addDefaultMushrooms(generationBuilder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(generationBuilder);
	}
}