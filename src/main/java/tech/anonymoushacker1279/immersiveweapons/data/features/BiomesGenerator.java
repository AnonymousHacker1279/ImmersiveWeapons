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

	public static void init() {
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
	}

	protected static final ResourceLocation BATTLEFIELD = new ResourceLocation(ImmersiveWeapons.MOD_ID, "battlefield");

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

		return placedFeatures;
	}

	private static MobSpawnSettings getBattlefieldSpawns() {
		MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder()
				.addSpawn(MobCategory.MONSTER, new SpawnerData(
						DeferredRegistryHandler.DYING_SOLDIER_ENTITY.get(), 95, 1, 3));

		BiomeDefaultFeatures.commonSpawns(spawnBuilder);

		return spawnBuilder.build();
	}

	private static BiomeGenerationSettings getBattlefieldGenerationSettings() {
		Optional<? extends Registry<PlacedFeature>> featureRegistry =
				CustomDataGenerator.REGISTRY_BUILTIN_COPY.registry(Registry.PLACED_FEATURE_REGISTRY);
		Optional<? extends Registry<ConfiguredWorldCarver<?>>> carverRegistry =
				CustomDataGenerator.REGISTRY_BUILTIN_COPY.registry(Registry.CONFIGURED_CARVER_REGISTRY);

		BiomeGenerationSettings.Builder generationBuilder = new BiomeGenerationSettings.Builder()
				.addCarver(Carving.AIR, carverRegistry.get().getOrCreateHolderOrThrow(CarversGenerator.TRENCH_KEY))
				.addFeature(Decoration.VEGETAL_DECORATION, featureRegistry.get().getOrCreateHolderOrThrow(BiomeFeatures.PATCH_WOODEN_SPIKES_KEY))
				.addFeature(Decoration.VEGETAL_DECORATION, featureRegistry.get().getOrCreateHolderOrThrow(BiomeFeatures.BURNED_OAK_TREE_KEY));

		VanillaFeatures.addDefaultCarversAndLakes(featureRegistry.get(), carverRegistry.get(), generationBuilder);
		VanillaFeatures.addDefaultCrystalFormations(featureRegistry.get(), generationBuilder);
		VanillaFeatures.addDefaultMonsterRoom(featureRegistry.get(), generationBuilder);
		VanillaFeatures.addDefaultUndergroundVariety(featureRegistry.get(), generationBuilder);
		VanillaFeatures.addDefaultSprings(featureRegistry.get(), generationBuilder);
		VanillaFeatures.addSurfaceFreezing(featureRegistry.get(), generationBuilder);
		VanillaFeatures.addPlainVegetation(featureRegistry.get(), generationBuilder);
		VanillaFeatures.addDefaultMushrooms(featureRegistry.get(), generationBuilder);
		VanillaFeatures.addDefaultExtraVegetation(featureRegistry.get(), generationBuilder);
		VanillaFeatures.addDefaultOres(featureRegistry.get(), generationBuilder);

		return generationBuilder.build();
	}
}