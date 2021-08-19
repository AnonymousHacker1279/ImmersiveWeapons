package com.anonymoushacker1279.immersiveweapons.util;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Features;
import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;

public class BiomeBuilder {

	/**
	 * Get the surface builder.
	 * @param key the <code>ResourceKey</code>, must extend ConfiguredSurfaceBuilder
	 * @return ConfiguredSurfaceBuilder
	 */
	public static ConfiguredSurfaceBuilder<?> getSurfaceBuilder(ResourceKey<ConfiguredSurfaceBuilder<?>> key) {
		return BuiltinRegistries.CONFIGURED_SURFACE_BUILDER.getOrThrow(key);
	}

	/**
	 * Make a Battlefield biome.
	 * @param surfaceBuilder the <code>ConfiguredSurfaceBuilder</code> instance
	 * @param depth the biome depth
	 * @param scale the biome scale
	 * @return Biome
	 */
	public static Biome makeBattlefieldBiome(ConfiguredSurfaceBuilder<?> surfaceBuilder, float depth, float scale) {
		MobSpawnSettings.Builder mobSpawnInfoBuilder = new MobSpawnSettings.Builder()
				.setPlayerCanSpawn()
				.addSpawn(MobCategory.MONSTER, new SpawnerData(DeferredRegistryHandler.DYING_SOLDIER_ENTITY.get(), 50, 1, 4));

		AmbientMoodSettings ambienceSound = new AmbientMoodSettings(DeferredRegistryHandler.FLINTLOCK_PISTOL_FIRE.get(), 5000, 8, 2.0D);

		BiomeGenerationSettings.Builder biomeGenerationSettingBuilder = new BiomeGenerationSettings.Builder()
				.surfaceBuilder(surfaceBuilder)
				.addFeature(Decoration.VEGETAL_DECORATION, Features.PATCH_GRASS_PLAIN);

		biomeGenerationSettingBuilder.addStructureStart(StructureFeatures.PILLAGER_OUTPOST);

		BiomeDefaultFeatures.addDefaultOverworldLandStructures(biomeGenerationSettingBuilder);
		BiomeDefaultFeatures.addDefaultCarvers(biomeGenerationSettingBuilder);
		BiomeDefaultFeatures.addDefaultMonsterRoom(biomeGenerationSettingBuilder);
		BiomeDefaultFeatures.addDefaultUndergroundVariety(biomeGenerationSettingBuilder);
		BiomeDefaultFeatures.addDefaultOres(biomeGenerationSettingBuilder);
		BiomeDefaultFeatures.addDefaultGrass(biomeGenerationSettingBuilder);
		BiomeDefaultFeatures.addDefaultSprings(biomeGenerationSettingBuilder);

		return new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.RAIN)
				.biomeCategory(BiomeCategory.PLAINS)
				.depth(depth)
				.scale(scale)
				.temperature(0.8f)
				.downfall(0.3f)
				.specialEffects(
						new BiomeSpecialEffects.Builder()
								.waterColor(0x55274c)
								.waterFogColor(0x503b72)
								.fogColor(0x906c80)
								.skyColor(0x746776)
								.grassColorOverride(0x551710)
								.foliageColorOverride(0x442e35)
								.ambientMoodSound(ambienceSound)
								.ambientLoopSound(DeferredRegistryHandler.BATTLEFIELD_AMBIENT.get())
								.build()
				)
				.mobSpawnSettings(mobSpawnInfoBuilder.build())
				.generationSettings(biomeGenerationSettingBuilder.build())
				.build();
	}
}