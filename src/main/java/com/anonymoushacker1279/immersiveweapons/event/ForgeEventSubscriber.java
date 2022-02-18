package com.anonymoushacker1279.immersiveweapons.event;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.config.CommonConfig;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.world.level.levelgen.OreGeneratorHandler;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import java.util.Objects;

@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = Bus.FORGE)
public class ForgeEventSubscriber {

	/**
	 * Event handler for the BiomeLoadingEvent.
	 * Configures custom ores, carvers, spawns, etc.
	 *
	 * @param event the <code>BiomeLoadingEvent</code> instance
	 */
	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onBiomeLoading(BiomeLoadingEvent event) {
		BiomeGenerationSettingsBuilder generation = event.getGeneration();
		if (event.getCategory() != BiomeCategory.NETHER && event.getCategory() != BiomeCategory.THEEND) {
			generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OreGeneratorHandler.COBALT_ORE_FEATURE);
			generation.addFeature(Decoration.UNDERGROUND_ORES, OreGeneratorHandler.DEEPSLATE_COBALT_ORE_FEATURE);

			if (event.getCategory() == BiomeCategory.RIVER || event.getCategory() == BiomeCategory.OCEAN) {
				generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OreGeneratorHandler.SULFUR_ORE_FEATURE);
				generation.addFeature(Decoration.UNDERGROUND_ORES, OreGeneratorHandler.DEEPSLATE_SULFUR_ORE_FEATURE);
			}

			if (event.getCategory() != BiomeCategory.OCEAN && event.getCategory() != BiomeCategory.RIVER) {
				if (CommonConfig.WANDERING_WARRIOR_SPAWN.get())
					event.getSpawns().addSpawn(MobCategory.MONSTER, new SpawnerData(DeferredRegistryHandler.WANDERING_WARRIOR_ENTITY.get(), 13, 1, 1));
				if (CommonConfig.HANS_SPAWN.get())
					event.getSpawns().addSpawn(MobCategory.MONSTER, new SpawnerData(DeferredRegistryHandler.HANS_ENTITY.get(), 1, 1, 1));
			}
		}
		if (event.getCategory() == BiomeCategory.NETHER) {
			generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OreGeneratorHandler.MOLTEN_ORE_FEATURE);
			generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OreGeneratorHandler.NETHER_SULFUR_ORE_FEATURE);
		}
		if (Objects.requireNonNull(event.getName()).toString().equals("immersiveweapons:tiltros")) {
			if (CommonConfig.LAVA_REVENANT_SPAWN.get())
				event.getSpawns().addSpawn(MobCategory.MONSTER, new SpawnerData(DeferredRegistryHandler.LAVA_REVENANT_ENTITY.get(), 1, 0, 1));
			if (CommonConfig.ROCK_SPIDER_SPAWN.get())
				event.getSpawns().addSpawn(MobCategory.MONSTER, new SpawnerData(DeferredRegistryHandler.ROCK_SPIDER_ENTITY.get(), 5, 4, 12));
			if (CommonConfig.CELESTIAL_TOWER_SPAWN.get())
				event.getSpawns().addSpawn(MobCategory.MONSTER, new SpawnerData(DeferredRegistryHandler.CELESTIAL_TOWER_ENTITY.get(), 2, 0, 1));
		}
	}
}