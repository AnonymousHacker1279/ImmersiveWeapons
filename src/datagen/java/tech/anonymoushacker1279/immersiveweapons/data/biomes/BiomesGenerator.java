package tech.anonymoushacker1279.immersiveweapons.data.biomes;

import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.attribute.*;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.TemperatureModifier;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.BiomeSpecialEffects.GrassColorModifier;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import tech.anonymoushacker1279.immersiveweapons.data.features.IWConfiguredCarvers;
import tech.anonymoushacker1279.immersiveweapons.data.features.IWPlacedFeatures;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;

import java.util.List;
import java.util.Optional;

public class BiomesGenerator {

	public static Biome battlefieldBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		return new Biome.BiomeBuilder()
				.temperature(0.8f)
				.downfall(0.3f)
				.hasPrecipitation(true)
				.temperatureAdjustment(TemperatureModifier.NONE)
				.specialEffects(new BiomeSpecialEffects.Builder()
						.waterColor(5580620)
						.grassColorOverride(5576464)
						.foliageColorOverride(4468277)
						.grassColorModifier(GrassColorModifier.NONE)
						.build())
				.mobSpawnSettings(getBattlefieldSpawns())
				.generationSettings(getBattlefieldGenerationSettings(placedFeatures, worldCarvers))
				.setAttribute(EnvironmentAttributes.SKY_COLOR, 7628662)
				.setAttribute(EnvironmentAttributes.FOG_COLOR, 9464960)
				.setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 5258098)
				.setAttribute(EnvironmentAttributes.AMBIENT_SOUNDS, new AmbientSounds(
						Optional.of(SoundEventRegistry.BATTLEFIELD_AMBIENT),
						Optional.empty(),
						List.of()
				))
				.build();
	}

	public static Biome tiltrosWastesBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		return new Biome.BiomeBuilder()
				.temperature(0.6f)
				.downfall(0.0f)
				.hasPrecipitation(false)
				.temperatureAdjustment(TemperatureModifier.NONE)
				.specialEffects(new BiomeSpecialEffects.Builder()
						.waterColor(7031172)
						.grassColorOverride(16113331)
						.foliageColorOverride(14665365)
						.grassColorModifier(GrassColorModifier.NONE)
						.build())
				.mobSpawnSettings(getTiltrosWastesSpawns())
				.generationSettings(getTiltrosWastesGenerationSettings(placedFeatures, worldCarvers))
				.setAttribute(EnvironmentAttributes.SKY_COLOR, 461620)
				.setAttribute(EnvironmentAttributes.FOG_COLOR, 2830199)
				.setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 4732021)
				.setAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(SoundEventRegistry.TILTROS_WASTES_MUSIC))
				.setAttribute(EnvironmentAttributes.AMBIENT_SOUNDS, new AmbientSounds(
						Optional.of(SoundEventRegistry.TILTROS_WASTES_AMBIENT),
						Optional.of(new AmbientMoodSettings(
								SoundEventRegistry.TILTROS_WASTES_MOOD,
								6000,
								8,
								2.0D)),
						List.of(new AmbientAdditionsSettings(SoundEventRegistry.TILTROS_WASTES_AMBIENT_ADDITIONS, 0.0111d))
				))
				.build();
	}

	public static Biome starlightPlainsBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		return new Biome.BiomeBuilder()
				.temperature(0.6f)
				.downfall(0.0f)
				.hasPrecipitation(false)
				.temperatureAdjustment(TemperatureModifier.NONE)
				.specialEffects(new BiomeSpecialEffects.Builder()
						.waterColor(16316908)
						.grassColorOverride(12312020)
						.foliageColorOverride(13885404)
						.grassColorModifier(GrassColorModifier.NONE)
						.build())
				.mobSpawnSettings(getStarlightPlainsSpawns())
				.generationSettings(getStarlightPlainsGenerationSettings(placedFeatures, worldCarvers))
				.setAttribute(EnvironmentAttributes.SKY_COLOR, 461620)
				.setAttribute(EnvironmentAttributes.FOG_COLOR, 2830199)
				.setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 13356221)
				.setAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(SoundEventRegistry.STARLIGHT_PLAINS_BACKGROUND_MUSIC))
				.setAttribute(EnvironmentAttributes.AMBIENT_SOUNDS, new AmbientSounds(
						Optional.of(SoundEventRegistry.STARLIGHT_PLAINS_AMBIENT),
						Optional.of(new AmbientMoodSettings(
								SoundEventRegistry.STARLIGHT_PLAINS_MOOD,
								6000,
								8,
								2.0D)),
						List.of(new AmbientAdditionsSettings(SoundEventRegistry.STARLIGHT_PLAINS_AMBIENT_ADDITIONS, 0.0111d))
				))
				.build();
	}

	public static Biome deadmansDesertBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
		return new Biome.BiomeBuilder()
				.temperature(0.35f)
				.downfall(0.0f)
				.hasPrecipitation(false)
				.temperatureAdjustment(TemperatureModifier.NONE)
				.specialEffects(new BiomeSpecialEffects.Builder()
						.waterColor(4140076)
						.grassColorOverride(6176026)
						.foliageColorOverride(6242850)
						.grassColorModifier(GrassColorModifier.NONE)
						.build())
				.mobSpawnSettings(getDeadmansDesertSpawns())
				.generationSettings(getDeadmansDesertGenerationSettings(placedFeatures, worldCarvers))
				.setAttribute(EnvironmentAttributes.SKY_COLOR, 10885401)
				.setAttribute(EnvironmentAttributes.FOG_COLOR, 5063491)
				.setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 13356221)
				.setAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(SoundEventRegistry.DEADMANS_DESERT_BACKGROUND_MUSIC))
				.setAttribute(EnvironmentAttributes.AMBIENT_SOUNDS, new AmbientSounds(
						Optional.of(SoundEventRegistry.DEADMANS_DESERT_AMBIENT),
						Optional.of(new AmbientMoodSettings(
								SoundEventRegistry.DEADMANS_DESERT_MOOD,
								6000,
								8,
								2.0D)),
						List.of(new AmbientAdditionsSettings(SoundEventRegistry.DEADMANS_DESERT_AMBIENT_ADDITIONS, 0.0111d))
				))
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
				.addSpawn(MobCategory.MONSTER, 85, new SpawnerData(
						EntityRegistry.ROCK_SPIDER_ENTITY.get(), 2, 4))
				.addSpawn(MobCategory.MONSTER, 10, new SpawnerData(
						EntityRegistry.LAVA_REVENANT_ENTITY.get(), 1, 1))
				.addSpawn(MobCategory.AMBIENT, 50, new SpawnerData(
						EntityRegistry.WISP_ENTITY.get(), 1, 2))
				.addMobCharge(EntityRegistry.LAVA_REVENANT_ENTITY.get(), 1d, 2d);

		return spawnBuilder.build();
	}

	private static MobSpawnSettings getStarlightPlainsSpawns() {
		MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder()
				.creatureGenerationProbability(0.45f)
				.addSpawn(MobCategory.CREATURE, 10, new SpawnerData(
						EntityRegistry.FIREFLY_ENTITY.get(), 4, 20))
				.addSpawn(MobCategory.CREATURE, 2, new SpawnerData(
						EntityRegistry.STAR_WOLF_ENTITY.get(), 1, 2))
				.addSpawn(MobCategory.CREATURE, 6, new SpawnerData(
						EntityRegistry.MOOGLOW_ENTITY.get(), 1, 4));

		return spawnBuilder.build();
	}

	private static MobSpawnSettings getDeadmansDesertSpawns() {
		MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder()
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