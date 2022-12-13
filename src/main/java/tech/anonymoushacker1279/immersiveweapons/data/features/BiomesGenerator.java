package tech.anonymoushacker1279.immersiveweapons.data.features;

import com.google.gson.JsonElement;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
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
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.feature.BiomeFeatures;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.feature.VanillaFeatures;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class BiomesGenerator {

	protected static Biome BATTLEFIELD_BIOME;
	protected static Biome TILTROS_WASTES_BIOME;
	protected static Biome STARLIGHT_PLAINS_BIOME;
	protected static Biome DEADMANS_DESERT_BIOME;

	private static RegistryLookup<PlacedFeature> FEATURE_REGISTRY;
	private static RegistryLookup<ConfiguredWorldCarver<?>> CARVER_REGISTRY;

	public static void init(CompletableFuture<Provider> lookup) {
		try {
			FEATURE_REGISTRY = lookup.get().lookup(Registries.PLACED_FEATURE).get();
			CARVER_REGISTRY = lookup.get().lookup(Registries.CONFIGURED_CARVER).get();
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}

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
						.ambientLoopSound(SoundEventRegistry.BATTLEFIELD_AMBIENT.getHolder().get())
						.ambientAdditionsSound(new AmbientAdditionsSettings(
								SoundEventRegistry.FLINTLOCK_PISTOL_FIRE.getHolder().get(), 0.00002d
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
						.ambientLoopSound(SoundEventRegistry.TILTROS_AMBIENT.getHolder().get())
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
						.ambientLoopSound(SoundEventRegistry.TILTROS_AMBIENT.getHolder().get())
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
						.ambientLoopSound(SoundEventRegistry.TILTROS_AMBIENT.getHolder().get())
						.ambientParticle(new AmbientParticleSettings(
								ParticleTypesRegistry.DEADMANS_DESERT_AMBIENT_PARTICLE.get(), 0.002f
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

	public static JsonCodecProvider<Biome> getCodecProvider(PackOutput output,
	                                                        ExistingFileHelper existingFileHelper,
	                                                        RegistryOps<JsonElement> registryOps,
	                                                        ResourceKey<Registry<Biome>> placedFeatureKey) {

		return JsonCodecProvider.forDatapackRegistry(
				output, existingFileHelper, ImmersiveWeapons.MOD_ID, registryOps, placedFeatureKey, getPlacedFeatures());
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
						EntityRegistry.FIREFLY_ENTITY.get(), 100, 1, 4));

		return spawnBuilder.build();
	}

	// TODO: Also add more mobs to this biome
	private static MobSpawnSettings getDeadmansDesertSpawns() {
		MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder()
				.creatureGenerationProbability(0.85f)
				.addSpawn(MobCategory.MONSTER, new SpawnerData(
						EntityRegistry.CELESTIAL_TOWER_ENTITY.get(), 5, 1, 1));

		return spawnBuilder.build();
	}

	private static BiomeGenerationSettings getBattlefieldGenerationSettings() {
		BiomeGenerationSettings.Builder generationBuilder = new BiomeGenerationSettings.Builder(FEATURE_REGISTRY, CARVER_REGISTRY)
				.addCarver(Carving.AIR, CarversGenerator.TRENCH_KEY)
				.addFeature(Decoration.VEGETAL_DECORATION, BiomeFeatures.PATCH_WOODEN_SPIKES_KEY)
				.addFeature(Decoration.VEGETAL_DECORATION, BiomeFeatures.BURNED_OAK_TREE_KEY);

		VanillaFeatures.getOverworldBaseGeneration(generationBuilder);
		VanillaFeatures.getPlainsLikeGeneration(generationBuilder);

		return generationBuilder.build();
	}

	private static BiomeGenerationSettings getTiltrosWastesGenerationSettings() {
		BiomeGenerationSettings.Builder generationBuilder = new BiomeGenerationSettings.Builder(FEATURE_REGISTRY, CARVER_REGISTRY)
				.addCarver(Carving.AIR, CarversGenerator.TILTROS_WASTES_KEY)
				.addFeature(Decoration.LOCAL_MODIFICATIONS, BiomeFeatures.ASTRAL_GEODE_KEY);

		VanillaFeatures.getOverworldBaseGeneration(generationBuilder);
		VanillaFeatures.addPlainGrass(generationBuilder);

		return generationBuilder.build();
	}

	private static BiomeGenerationSettings getStarlightPlainsGenerationSettings() {
		BiomeGenerationSettings.Builder generationBuilder = new BiomeGenerationSettings.Builder(FEATURE_REGISTRY, CARVER_REGISTRY)
				.addFeature(Decoration.VEGETAL_DECORATION, BiomeFeatures.PATCH_MOONGLOW_KEY)
				.addFeature(Decoration.VEGETAL_DECORATION, BiomeFeatures.STARDUST_TREE_KEY);

		VanillaFeatures.getOverworldBaseGeneration(generationBuilder);
		VanillaFeatures.addPlainGrass(generationBuilder);

		return generationBuilder.build();
	}

	private static BiomeGenerationSettings getDeadmansDesertGenerationSettings() {
		BiomeGenerationSettings.Builder generationBuilder = new BiomeGenerationSettings.Builder(FEATURE_REGISTRY, CARVER_REGISTRY)
				.addFeature(Decoration.VEGETAL_DECORATION, BiomeFeatures.PATCH_DEATHWEED_KEY);

		VanillaFeatures.getOverworldBaseGeneration(generationBuilder);
		VanillaFeatures.addDefaultSoftDisks(generationBuilder);
		VanillaFeatures.addFossilDecoration(generationBuilder);
		VanillaFeatures.addDesertVegetation(generationBuilder);

		return generationBuilder.build();
	}
}