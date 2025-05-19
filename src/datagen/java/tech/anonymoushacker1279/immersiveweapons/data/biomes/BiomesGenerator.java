package tech.anonymoushacker1279.immersiveweapons.data.biomes;

import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.sounds.Music;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.biome.Biome.TemperatureModifier;
import net.minecraft.world.level.biome.BiomeSpecialEffects.GrassColorModifier;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import tech.anonymoushacker1279.immersiveweapons.data.features.IWConfiguredCarvers;
import tech.anonymoushacker1279.immersiveweapons.data.features.IWPlacedFeatures;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ParticleTypesRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;

public class BiomesGenerator {

	public static Biome battlefieldBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		return new Biome.BiomeBuilder()
				.temperature(0.8f)
				.downfall(0.3f)
				.hasPrecipitation(true)
				.temperatureAdjustment(TemperatureModifier.NONE)
				.specialEffects(new BiomeSpecialEffects.Builder()
						.skyColor(7628662)
						.fogColor(9464960)
						.waterColor(5580620)
						.waterFogColor(5258098)
						.grassColorOverride(5576464)
						.foliageColorOverride(4468277)
						.grassColorModifier(GrassColorModifier.NONE)
						.ambientLoopSound(SoundEventRegistry.BATTLEFIELD_AMBIENT)
						.build())
				.mobSpawnSettings(getBattlefieldSpawns())
				.generationSettings(getBattlefieldGenerationSettings(placedFeatures, worldCarvers))
				.build();
	}

	public static Biome tiltrosWastesBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		return new Biome.BiomeBuilder()
				.temperature(0.6f)
				.downfall(0.0f)
				.hasPrecipitation(false)
				.temperatureAdjustment(TemperatureModifier.NONE)
				.specialEffects(new BiomeSpecialEffects.Builder()
						.skyColor(461620)
						.fogColor(2830199)
						.waterColor(7031172)
						.waterFogColor(4732021)
						.grassColorOverride(16113331)
						.foliageColorOverride(14665365)
						.grassColorModifier(GrassColorModifier.NONE)
						.backgroundMusic(new Music(SoundEventRegistry.TILTROS_WASTES_MUSIC, 6000, 24000, true))
						.build())
				.mobSpawnSettings(getTiltrosWastesSpawns())
				.generationSettings(getTiltrosWastesGenerationSettings(placedFeatures, worldCarvers))
				.build();
	}

	public static Biome starlightPlainsBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		return new Biome.BiomeBuilder()
				.temperature(0.6f)
				.downfall(0.0f)
				.hasPrecipitation(false)
				.temperatureAdjustment(TemperatureModifier.NONE)
				.specialEffects(new BiomeSpecialEffects.Builder()
						.skyColor(461620)
						.fogColor(2830199)
						.waterColor(16316908)
						.waterFogColor(13356221)
						.grassColorOverride(12312020)
						.foliageColorOverride(13885404)
						.grassColorModifier(GrassColorModifier.NONE)
						.backgroundMusic(new Music(SoundEventRegistry.STARLIGHT_PLAINS_BACKGROUND_MUSIC, 6000, 24000, true))
						.build())
				.mobSpawnSettings(getStarlightPlainsSpawns())
				.generationSettings(getStarlightPlainsGenerationSettings(placedFeatures, worldCarvers))
				.build();
	}

	public static Biome deadmansDesertBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		return new Biome.BiomeBuilder()
				.temperature(0.35f)
				.downfall(0.0f)
				.hasPrecipitation(false)
				.temperatureAdjustment(TemperatureModifier.NONE)
				.specialEffects(new BiomeSpecialEffects.Builder()
						.skyColor(10885401)
						.fogColor(11422796)
						.waterColor(4140076)
						.waterFogColor(5063491)
						.grassColorOverride(6176026)
						.foliageColorOverride(6242850)
						.grassColorModifier(GrassColorModifier.NONE)
						.backgroundMusic(new Music(SoundEventRegistry.DEADMANS_DESERT_BACKGROUND_MUSIC, 6000, 24000, true))
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
				.addSpawn(MobCategory.MONSTER, 95, new SpawnerData(
						EntityRegistry.DYING_SOLDIER_ENTITY.get(), 1, 3));

		BiomeDefaultFeatures.commonSpawns(spawnBuilder);

		return spawnBuilder.build();
	}

	private static MobSpawnSettings getTiltrosWastesSpawns() {
		MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder()
				.creatureGenerationProbability(0.65f)
				.addSpawn(MobCategory.MONSTER, 65, new SpawnerData(
						EntityRegistry.ROCK_SPIDER_ENTITY.get(), 2, 4))
				.addSpawn(MobCategory.MONSTER, 35, new SpawnerData(
						EntityRegistry.LAVA_REVENANT_ENTITY.get(), 1, 1));

		return spawnBuilder.build();
	}

	private static MobSpawnSettings getStarlightPlainsSpawns() {
		MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder()
				.addSpawn(MobCategory.CREATURE, 10, new SpawnerData(
						EntityRegistry.FIREFLY_ENTITY.get(), 1, 4))
				.addSpawn(MobCategory.CREATURE, 2, new SpawnerData(
						EntityRegistry.STAR_WOLF_ENTITY.get(), 1, 2))
				.addMobCharge(EntityRegistry.FIREFLY_ENTITY.get(), 0.01d, 15d)
				.addMobCharge(EntityRegistry.STAR_WOLF_ENTITY.get(), 0.1d, 7d);

		return spawnBuilder.build();
	}

	private static MobSpawnSettings getDeadmansDesertSpawns() {
		MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder()
				.creatureGenerationProbability(0.85f)
				.addSpawn(MobCategory.MONSTER, 5, new SpawnerData(
						EntityRegistry.CELESTIAL_TOWER_ENTITY.get(), 1, 1))
				.addSpawn(MobCategory.MONSTER, 10, new SpawnerData(
						EntityRegistry.STORM_CREEPER_ENTITY.get(), 1, 1))
				.addSpawn(MobCategory.MONSTER, 7, new SpawnerData(
						EntityRegistry.EVIL_EYE_ENTITY.get(), 1, 2))
				.addMobCharge(EntityRegistry.EVIL_EYE_ENTITY.get(), 0.15d, 12d);

		return spawnBuilder.build();
	}

	private static BiomeGenerationSettings getBattlefieldGenerationSettings(HolderGetter<PlacedFeature> placedFeatures,
	                                                                        HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {

		BiomeGenerationSettings.Builder generationBuilder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
				.addCarver(IWConfiguredCarvers.TRENCH)
				.addFeature(Decoration.VEGETAL_DECORATION, IWPlacedFeatures.PATCH_WOODEN_SPIKES)
				.addFeature(Decoration.VEGETAL_DECORATION, IWPlacedFeatures.BURNED_OAK_TREE);

		VanillaFeatures.getOverworldBaseGeneration(generationBuilder);
		VanillaFeatures.getPlainsLikeGeneration(generationBuilder);

		return generationBuilder.build();
	}

	private static BiomeGenerationSettings getTiltrosWastesGenerationSettings(HolderGetter<PlacedFeature> placedFeatures,
	                                                                          HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {

		BiomeGenerationSettings.Builder generationBuilder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
				.addCarver(IWConfiguredCarvers.TILTROS_WASTES)
				.addFeature(Decoration.LOCAL_MODIFICATIONS, IWPlacedFeatures.ASTRAL_GEODE);

		VanillaFeatures.getOverworldBaseGeneration(generationBuilder);
		VanillaFeatures.addPlainGrass(generationBuilder);

		return generationBuilder.build();
	}

	private static BiomeGenerationSettings getStarlightPlainsGenerationSettings(HolderGetter<PlacedFeature> placedFeatures,
	                                                                            HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {

		BiomeGenerationSettings.Builder generationBuilder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
				.addFeature(Decoration.VEGETAL_DECORATION, IWPlacedFeatures.PATCH_MOONGLOW)
				.addFeature(Decoration.VEGETAL_DECORATION, IWPlacedFeatures.STARDUST_TREE)
				.addFeature(Decoration.VEGETAL_DECORATION, IWPlacedFeatures.PATCH_FIREFLY_BUSH);

		VanillaFeatures.getOverworldBaseGeneration(generationBuilder);
		VanillaFeatures.addPlainGrass(generationBuilder);
		BiomeDefaultFeatures.addMossyStoneBlock(generationBuilder);

		return generationBuilder.build();
	}

	private static BiomeGenerationSettings getDeadmansDesertGenerationSettings(HolderGetter<PlacedFeature> placedFeatures,
	                                                                           HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {

		BiomeGenerationSettings.Builder generationBuilder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
				.addFeature(Decoration.VEGETAL_DECORATION, IWPlacedFeatures.PATCH_DEATHWEED);

		VanillaFeatures.getOverworldBaseGeneration(generationBuilder);
		BiomeDefaultFeatures.addDefaultSoftDisks(generationBuilder);
		BiomeDefaultFeatures.addFossilDecoration(generationBuilder);
		BiomeDefaultFeatures.addDesertVegetation(generationBuilder);

		return generationBuilder.build();
	}
}