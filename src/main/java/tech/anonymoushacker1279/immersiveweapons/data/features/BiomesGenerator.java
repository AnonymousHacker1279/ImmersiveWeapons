package tech.anonymoushacker1279.immersiveweapons.data.features;

import com.google.gson.JsonElement;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.resources.*;
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
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.CustomDataGenerator;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.feature.BiomeFeatures;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.feature.VanillaFeatures;

import java.util.HashMap;
import java.util.Optional;

public class BiomesGenerator {

	protected static Biome BATTLEFIELD_BIOME;
	protected static Biome TILTROS_WASTES_BIOME;
	protected static Biome STARLIGHT_PLAINS_BIOME;
	protected static Biome DEADMANS_DESERT_BIOME;

	private static Optional<? extends Registry<PlacedFeature>> FEATURE_REGISTRY;
	private static Optional<? extends Registry<ConfiguredWorldCarver<?>>> CARVER_REGISTRY;

	public static void init() {
		FEATURE_REGISTRY =
				CustomDataGenerator.REGISTRY_BUILTIN_COPY.registry(Registry.PLACED_FEATURE_REGISTRY);
		CARVER_REGISTRY =
				CustomDataGenerator.REGISTRY_BUILTIN_COPY.registry(Registry.CONFIGURED_CARVER_REGISTRY);

		BATTLEFIELD_BIOME = new Biome.BiomeBuilder()
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
						.ambientLoopSound(DeferredRegistryHandler.BATTLEFIELD_AMBIENT.get())
						.ambientAdditionsSound(new AmbientAdditionsSettings(
								DeferredRegistryHandler.FLINTLOCK_PISTOL_FIRE.get(), 0.00002d
						))
						.build())
				.mobSpawnSettings(getBattlefieldSpawns())
				.generationSettings(getBattlefieldGenerationSettings())
				.build();

		// TODO: Custom ambient sounds for these biomes

		TILTROS_WASTES_BIOME = new Biome.BiomeBuilder()
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
						.ambientLoopSound(DeferredRegistryHandler.TILTROS_AMBIENT.get())
						.build())
				.mobSpawnSettings(getTiltrosWastesSpawns())
				.generationSettings(getTiltrosWastesGenerationSettings())
				.build();

		STARLIGHT_PLAINS_BIOME = new Biome.BiomeBuilder()
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
						.ambientLoopSound(DeferredRegistryHandler.TILTROS_AMBIENT.get())
						.build())
				.mobSpawnSettings(getStarlightPlainsSpawns())
				.generationSettings(getStarlightPlainsGenerationSettings())
				.build();

		DEADMANS_DESERT_BIOME = new Biome.BiomeBuilder()
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
						.ambientLoopSound(DeferredRegistryHandler.TILTROS_AMBIENT.get())
						.ambientParticle(new AmbientParticleSettings(
								DeferredRegistryHandler.DEADMANS_DESERT_AMBIENT_PARTICLE.get(), 0.002f
						))
						.build())
				.mobSpawnSettings(getDeadmansDesertSpawns())
				.generationSettings(getDeadmansDesertGenerationSettings())
				.build();
	}

	protected static final ResourceLocation BATTLEFIELD = new ResourceLocation(ImmersiveWeapons.MOD_ID, "battlefield");
	protected static final ResourceLocation TILTROS_WASTES = new ResourceLocation(ImmersiveWeapons.MOD_ID, "tiltros_wastes");
	protected static final ResourceLocation STARLIGHT_PLAINS = new ResourceLocation(ImmersiveWeapons.MOD_ID, "starlight_plains");
	protected static final ResourceLocation DEADMANS_DESERT = new ResourceLocation(ImmersiveWeapons.MOD_ID, "deadmans_desert");

	public static JsonCodecProvider<Biome> getCodecProvider(DataGenerator generator,
	                                                        ExistingFileHelper existingFileHelper,
	                                                        RegistryOps<JsonElement> registryOps,
	                                                        ResourceKey<Registry<Biome>> placedFeatureKey) {

		return JsonCodecProvider.forDatapackRegistry(
				generator, existingFileHelper, ImmersiveWeapons.MOD_ID, registryOps, placedFeatureKey, getPlacedFeatures());
	}

	private static HashMap<ResourceLocation, Biome> getPlacedFeatures() {
		HashMap<ResourceLocation, Biome> placedFeatures = new HashMap<>(10);
		placedFeatures.put(BATTLEFIELD, BATTLEFIELD_BIOME);
		placedFeatures.put(TILTROS_WASTES, TILTROS_WASTES_BIOME);
		placedFeatures.put(STARLIGHT_PLAINS, STARLIGHT_PLAINS_BIOME);
		placedFeatures.put(DEADMANS_DESERT, DEADMANS_DESERT_BIOME);

		return placedFeatures;
	}

	private static MobSpawnSettings getBattlefieldSpawns() {
		MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder()
				.addSpawn(MobCategory.MONSTER, new SpawnerData(
						DeferredRegistryHandler.DYING_SOLDIER_ENTITY.get(), 95, 1, 3));

		BiomeDefaultFeatures.commonSpawns(spawnBuilder);

		return spawnBuilder.build();
	}

	private static MobSpawnSettings getTiltrosWastesSpawns() {
		MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder()
				.creatureGenerationProbability(0.65f)
				.addSpawn(MobCategory.MONSTER, new SpawnerData(
						DeferredRegistryHandler.ROCK_SPIDER_ENTITY.get(), 65, 2, 4))
				.addSpawn(MobCategory.MONSTER, new SpawnerData(
						DeferredRegistryHandler.LAVA_REVENANT_ENTITY.get(), 35, 1, 1))
				.addSpawn(MobCategory.MONSTER, new SpawnerData(
						DeferredRegistryHandler.CELESTIAL_TOWER_ENTITY.get(), 5, 1, 1));

		return spawnBuilder.build();
	}

	// TODO: Add more mobs to this biome
	private static MobSpawnSettings getStarlightPlainsSpawns() {
		MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder()
				.creatureGenerationProbability(0.65f);

		return spawnBuilder.build();
	}

	// TODO: Also add more mobs to this biome
	private static MobSpawnSettings getDeadmansDesertSpawns() {
		MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder()
				.creatureGenerationProbability(0.85f);

		return spawnBuilder.build();
	}

	private static BiomeGenerationSettings getBattlefieldGenerationSettings() {
		BiomeGenerationSettings.Builder generationBuilder = new BiomeGenerationSettings.Builder()
				.addCarver(Carving.AIR, CARVER_REGISTRY.get().getOrCreateHolderOrThrow(CarversGenerator.TRENCH_KEY))
				.addFeature(Decoration.VEGETAL_DECORATION, FEATURE_REGISTRY.get().getOrCreateHolderOrThrow(BiomeFeatures.PATCH_WOODEN_SPIKES_KEY))
				.addFeature(Decoration.VEGETAL_DECORATION, FEATURE_REGISTRY.get().getOrCreateHolderOrThrow(BiomeFeatures.BURNED_OAK_TREE_KEY));

		VanillaFeatures.getOverworldBaseGeneration(FEATURE_REGISTRY.get(), CARVER_REGISTRY.get(), generationBuilder);
		VanillaFeatures.getPlainsLikeGeneration(FEATURE_REGISTRY.get(), generationBuilder);

		return generationBuilder.build();
	}

	private static BiomeGenerationSettings getTiltrosWastesGenerationSettings() {
		BiomeGenerationSettings.Builder generationBuilder = new BiomeGenerationSettings.Builder()
				.addCarver(Carving.AIR, CARVER_REGISTRY.get().getOrCreateHolderOrThrow(CarversGenerator.TILTROS_WASTES_KEY))
				.addFeature(Decoration.LOCAL_MODIFICATIONS, FEATURE_REGISTRY.get().getOrCreateHolderOrThrow(BiomeFeatures.ASTRAL_GEODE_KEY));

		VanillaFeatures.getOverworldBaseGeneration(FEATURE_REGISTRY.get(), CARVER_REGISTRY.get(), generationBuilder);
		VanillaFeatures.addPlainGrass(FEATURE_REGISTRY.get(), generationBuilder);

		return generationBuilder.build();
	}

	private static BiomeGenerationSettings getStarlightPlainsGenerationSettings() {
		BiomeGenerationSettings.Builder generationBuilder = new BiomeGenerationSettings.Builder()
				.addFeature(Decoration.VEGETAL_DECORATION, FEATURE_REGISTRY.get().getOrCreateHolderOrThrow(BiomeFeatures.PATCH_MOONGLOW_KEY))
				.addFeature(Decoration.VEGETAL_DECORATION, FEATURE_REGISTRY.get().getOrCreateHolderOrThrow(BiomeFeatures.STARDUST_TREE_KEY));

		VanillaFeatures.getOverworldBaseGeneration(FEATURE_REGISTRY.get(), CARVER_REGISTRY.get(), generationBuilder);
		VanillaFeatures.addPlainGrass(FEATURE_REGISTRY.get(), generationBuilder);

		return generationBuilder.build();
	}

	private static BiomeGenerationSettings getDeadmansDesertGenerationSettings() {
		BiomeGenerationSettings.Builder generationBuilder = new BiomeGenerationSettings.Builder()
				.addFeature(Decoration.VEGETAL_DECORATION, FEATURE_REGISTRY.get().getOrCreateHolderOrThrow(BiomeFeatures.PATCH_DEATHWEED_KEY));

		VanillaFeatures.getOverworldBaseGeneration(FEATURE_REGISTRY.get(), CARVER_REGISTRY.get(), generationBuilder);
		VanillaFeatures.addDefaultSoftDisks(FEATURE_REGISTRY.get(), generationBuilder);
		VanillaFeatures.addFossilDecoration(FEATURE_REGISTRY.get(), generationBuilder);
		VanillaFeatures.addDesertVegetation(FEATURE_REGISTRY.get(), generationBuilder);

		return generationBuilder.build();
	}


}