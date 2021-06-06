package com.anonymoushacker1279.immersiveweapons.util;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.StainedGlassBlock;
import net.minecraft.entity.EntityClassification;
import net.minecraft.item.DyeColor;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.*;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;

public class GeneralUtilities {

	public static float getRandomNumber(float min, float max) {
		return (float) ((Math.random() * (max - min)) + min);
	}

	public static double getRandomNumber(double min, double max) {
		return (Math.random() * (max - min)) + min;
	}

	public static int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}

	public static StainedGlassBlock createStainedGlassFromColor(DyeColor color, Properties properties) {
		return new StainedGlassBlock(color, properties);
	}

	public static ConfiguredSurfaceBuilder<?> getSurfaceBuilder(final RegistryKey<ConfiguredSurfaceBuilder<?>> key) {
		return WorldGenRegistries.CONFIGURED_SURFACE_BUILDER.getOrThrow(key);
	}

	public static Biome makeBattlefieldBiome(
			final ConfiguredSurfaceBuilder<?> surfaceBuilder,
			final float depth,
			final float scale,
			final boolean hasVillageAndOutpost
	) {
		final MobSpawnInfo.Builder mobSpawnInfoBuilder = new MobSpawnInfo.Builder()
				.isValidSpawnBiomeForPlayer()
				.withSpawner(EntityClassification.MONSTER, new Spawners(DeferredRegistryHandler.DYING_SOLDIER_ENTITY.get(), 50, 1, 4));

		final MoodSoundAmbience ambienceSound = new MoodSoundAmbience(DeferredRegistryHandler.FLINTLOCK_PISTOL_FIRE.get(), 5000, 8, 2.0D);

		final BiomeGenerationSettings.Builder biomeGenerationSettingBuilder = new BiomeGenerationSettings.Builder()
				.withSurfaceBuilder(surfaceBuilder)
				.withFeature(Decoration.VEGETAL_DECORATION, Features.PLAIN_VEGETATION)
				.withFeature(Decoration.VEGETAL_DECORATION, Features.PATCH_GRASS_PLAIN);

		if (hasVillageAndOutpost) {
			biomeGenerationSettingBuilder.withStructure(StructureFeatures.PILLAGER_OUTPOST);
		}

		DefaultBiomeFeatures.withStrongholdAndMineshaft(biomeGenerationSettingBuilder);
		DefaultBiomeFeatures.withCavesAndCanyons(biomeGenerationSettingBuilder);
		DefaultBiomeFeatures.withMonsterRoom(biomeGenerationSettingBuilder);
		DefaultBiomeFeatures.withCommonOverworldBlocks(biomeGenerationSettingBuilder);
		DefaultBiomeFeatures.withOverworldOres(biomeGenerationSettingBuilder);
		DefaultBiomeFeatures.withBadlandsGrass(biomeGenerationSettingBuilder);
		DefaultBiomeFeatures.withLavaAndWaterSprings(biomeGenerationSettingBuilder);

		return new Biome.Builder()
				.precipitation(Biome.RainType.RAIN)
				.category(Category.PLAINS)
				.depth(depth)
				.scale(scale)
				.temperature(0.8f)
				.downfall(0.3f)
				.setEffects(
						new BiomeAmbience.Builder()
								.setWaterColor(0x55274c)
								.setWaterFogColor(0x503b72)
								.setFogColor(0x906c80)
								.withSkyColor(0x746776)
								.withGrassColor(0x551710)
								.withFoliageColor(0x442e35)
								.setMoodSound(ambienceSound)
								.setAmbientSound(DeferredRegistryHandler.BATTLEFIELD_AMBIENT.get())
								.build()
				)
				.withMobSpawnSettings(mobSpawnInfoBuilder.build())
				.withGenerationSettings(biomeGenerationSettingBuilder.build())
				.build();
	}
}