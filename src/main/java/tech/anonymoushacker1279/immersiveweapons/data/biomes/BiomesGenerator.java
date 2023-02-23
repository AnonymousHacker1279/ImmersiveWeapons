package tech.anonymoushacker1279.immersiveweapons.data.biomes;

import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.sounds.Music;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.biome.Biome.TemperatureModifier;
import net.minecraft.world.level.biome.BiomeSpecialEffects.GrassColorModifier;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.GenerationStep.Carving;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import tech.anonymoushacker1279.immersiveweapons.data.features.IWConfiguredCarvers;
import tech.anonymoushacker1279.immersiveweapons.data.features.IWPlacedFeatures;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.feature.VanillaFeatures;

public class BiomesGenerator {

	public static Biome battlefieldBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		return new Biome.BiomeBuilder()
				.temperature(0.8f)
				.downfall(0.3f)
				.precipitation(Precipitation.RAIN)
				.temperatureAdjustment(TemperatureModifier.NONE)
				.specialEffects(new BiomeSpecialEffects.Builder()
						.skyColor(7628662)
						.fogColor(9464960)
						.waterColor(5580620)
						.waterFogColor(5258098)
						.grassColorOverride(5576464)
						.foliageColorOverride(4468277)
						.grassColorModifier(GrassColorModifier.NONE)
						.ambientLoopSound(SoundEventRegistry.BATTLEFIELD_AMBIENT.getHolder().get())
						.ambientAdditionsSound(new AmbientAdditionsSettings(
								SoundEventRegistry.FLINTLOCK_PISTOL_FIRE.getHolder().get(), 0.00002d
						))
						.build())
				.mobSpawnSettings(getBattlefieldSpawns())
				.generationSettings(getBattlefieldGenerationSettings(placedFeatures, worldCarvers))
				.build();
	}

	// TODO: Custom ambient sounds for these biomes

	public static Biome tiltrosWastesBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		return new Biome.BiomeBuilder()
				.temperature(0.6f)
				.downfall(0.0f)
				.precipitation(Precipitation.NONE)
				.temperatureAdjustment(TemperatureModifier.NONE)
				.specialEffects(new BiomeSpecialEffects.Builder()
						.skyColor(461620)
						.fogColor(2830199)
						.waterColor(7031172)
						.waterFogColor(4732021)
						.grassColorOverride(16113331)
						.foliageColorOverride(14665365)
						.grassColorModifier(GrassColorModifier.NONE)
						.backgroundMusic(new Music(SoundEventRegistry.TILTROS_MUSIC.getHolder().get(), 6000, 24000, true))
						.build())
				.mobSpawnSettings(getTiltrosWastesSpawns())
				.generationSettings(getTiltrosWastesGenerationSettings(placedFeatures, worldCarvers))
				.build();
	}

	public static Biome starlightPlainsBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		return new Biome.BiomeBuilder()
				.temperature(0.8f)
				.downfall(0.0f)
				.precipitation(Precipitation.NONE)
				.temperatureAdjustment(TemperatureModifier.NONE)
				.specialEffects(new BiomeSpecialEffects.Builder()
						.skyColor(461620)
						.fogColor(2830199)
						.waterColor(16316908)
						.waterFogColor(13356221)
						.grassColorOverride(12312020)
						.foliageColorOverride(13885404)
						.grassColorModifier(GrassColorModifier.NONE)
						.backgroundMusic(new Music(SoundEventRegistry.STARLIGHT_PLAINS_MUSIC.getHolder().get(), 6000, 24000, true))
						.build())
				.mobSpawnSettings(getStarlightPlainsSpawns())
				.generationSettings(getStarlightPlainsGenerationSettings(placedFeatures, worldCarvers))
				.build();
	}

	public static Biome deadmansDesertBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		return new Biome.BiomeBuilder()
				.temperature(1.8f)
				.downfall(0.0f)
				.precipitation(Precipitation.NONE)
				.temperatureAdjustment(TemperatureModifier.NONE)
				.specialEffects(new BiomeSpecialEffects.Builder()
						.skyColor(10885401)
						.fogColor(11422796)
						.waterColor(4140076)
						.waterFogColor(5063491)
						.grassColorOverride(6176026)
						.foliageColorOverride(6242850)
						.grassColorModifier(GrassColorModifier.NONE)
						.backgroundMusic(new Music(SoundEventRegistry.TILTROS_MUSIC.getHolder().get(), 6000, 24000, true))
						.ambientParticle(new AmbientParticleSettings(
								ParticleTypesRegistry.DEADMANS_DESERT_AMBIENT_PARTICLE.get(), 0.002f
						))
						.build())
				.mobSpawnSettings(getDeadmansDesertSpawns())
				.generationSettings(getDeadmansDesertGenerationSettings(placedFeatures, worldCarvers))
				.build();
	}

	private static MobSpawnSettings getBattlefieldSpawns() {
		MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder()
				.addSpawn(MobCategory.MONSTER, new SpawnerData(
						EntityRegistry.DYING_SOLDIER_ENTITY.get(), 95, 1, 3));

		BiomeDefaultFeatures.commonSpawns(spawnBuilder);

		return spawnBuilder.build();
	}

	private static MobSpawnSettings getTiltrosWastesSpawns() {
		MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder()
				.creatureGenerationProbability(0.65f)
				.addSpawn(MobCategory.MONSTER, new SpawnerData(
						EntityRegistry.ROCK_SPIDER_ENTITY.get(), 65, 2, 4))
				.addSpawn(MobCategory.MONSTER, new SpawnerData(
						EntityRegistry.LAVA_REVENANT_ENTITY.get(), 35, 1, 1));

		return spawnBuilder.build();
	}

	// TODO: Add more mobs to this biome
	private static MobSpawnSettings getStarlightPlainsSpawns() {
		MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder()
				.creatureGenerationProbability(0.75f)
				.addSpawn(MobCategory.AMBIENT, new SpawnerData(
						EntityRegistry.FIREFLY_ENTITY.get(), 100, 1, 4))
				.addSpawn(MobCategory.CREATURE, new SpawnerData(
						EntityRegistry.STAR_WOLF_ENTITY.get(), 60, 1, 2));

		return spawnBuilder.build();
	}

	private static MobSpawnSettings getDeadmansDesertSpawns() {
		MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder()
				.creatureGenerationProbability(0.85f)
				.addSpawn(MobCategory.MONSTER, new SpawnerData(
						EntityRegistry.CELESTIAL_TOWER_ENTITY.get(), 5, 1, 1))
				.addSpawn(MobCategory.MONSTER, new SpawnerData(
						EntityRegistry.STORM_CREEPER_ENTITY.get(), 10, 1, 1))
				.addSpawn(MobCategory.MONSTER, new SpawnerData(
						EntityRegistry.EVIL_EYE_ENTITY.get(), 7, 1, 2));

		return spawnBuilder.build();
	}

	private static BiomeGenerationSettings getBattlefieldGenerationSettings(HolderGetter<PlacedFeature> placedFeatures,
	                                                                        HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {

		BiomeGenerationSettings.Builder generationBuilder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
				.addCarver(Carving.AIR, IWConfiguredCarvers.TRENCH)
				.addFeature(Decoration.VEGETAL_DECORATION, IWPlacedFeatures.PATCH_WOODEN_SPIKES)
				.addFeature(Decoration.VEGETAL_DECORATION, IWPlacedFeatures.BURNED_OAK_TREE);

		VanillaFeatures.getOverworldBaseGeneration(generationBuilder);
		VanillaFeatures.getPlainsLikeGeneration(generationBuilder);

		return generationBuilder.build();
	}

	private static BiomeGenerationSettings getTiltrosWastesGenerationSettings(HolderGetter<PlacedFeature> placedFeatures,
	                                                                          HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {

		BiomeGenerationSettings.Builder generationBuilder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
				.addCarver(Carving.AIR, IWConfiguredCarvers.TILTROS_WASTES)
				.addFeature(Decoration.LOCAL_MODIFICATIONS, IWPlacedFeatures.ASTRAL_GEODE);

		VanillaFeatures.getOverworldBaseGeneration(generationBuilder);
		VanillaFeatures.addPlainGrass(generationBuilder);

		return generationBuilder.build();
	}

	private static BiomeGenerationSettings getStarlightPlainsGenerationSettings(HolderGetter<PlacedFeature> placedFeatures,
	                                                                            HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {

		BiomeGenerationSettings.Builder generationBuilder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
				.addFeature(Decoration.VEGETAL_DECORATION, IWPlacedFeatures.PATCH_MOONGLOW)
				.addFeature(Decoration.VEGETAL_DECORATION, IWPlacedFeatures.STARDUST_TREE);

		VanillaFeatures.getOverworldBaseGeneration(generationBuilder);
		VanillaFeatures.addPlainGrass(generationBuilder);

		return generationBuilder.build();
	}

	private static BiomeGenerationSettings getDeadmansDesertGenerationSettings(HolderGetter<PlacedFeature> placedFeatures,
	                                                                           HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {

		BiomeGenerationSettings.Builder generationBuilder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
				.addFeature(Decoration.VEGETAL_DECORATION, IWPlacedFeatures.PATCH_DEATHWEED);

		VanillaFeatures.getOverworldBaseGeneration(generationBuilder);
		VanillaFeatures.addDefaultSoftDisks(generationBuilder);
		VanillaFeatures.addFossilDecoration(generationBuilder);
		VanillaFeatures.addDesertVegetation(generationBuilder);

		return generationBuilder.build();
	}
}