package com.anonymoushacker1279.immersiveweapons.events;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.Config;
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
	 * Configures custom ores, carvers, spawns, structures, etc.
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
				if (Config.WANDERING_WARRIOR_SPAWN.get())
					event.getSpawns().addSpawn(MobCategory.MONSTER, new SpawnerData(DeferredRegistryHandler.WANDERING_WARRIOR_ENTITY.get(), 13, 1, 1));
				if (Config.HANS_SPAWN.get())
					event.getSpawns().addSpawn(MobCategory.MONSTER, new SpawnerData(DeferredRegistryHandler.HANS_ENTITY.get(), 1, 1, 1));
			}
		}
		if (event.getCategory() == BiomeCategory.NETHER) {
			generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OreGeneratorHandler.MOLTEN_ORE_FEATURE);
			generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OreGeneratorHandler.NETHER_SULFUR_ORE_FEATURE);
		}
		if (Objects.requireNonNull(event.getName()).toString().equals("immersiveweapons:tiltros")) {
			if (Config.LAVA_REVENANT_SPAWN.get())
				event.getSpawns().addSpawn(MobCategory.MONSTER, new SpawnerData(DeferredRegistryHandler.LAVA_REVENANT_ENTITY.get(), 1, 0, 1));
			if (Config.ROCK_SPIDER_SPAWN.get())
				event.getSpawns().addSpawn(MobCategory.MONSTER, new SpawnerData(DeferredRegistryHandler.ROCK_SPIDER_ENTITY.get(), 5, 4, 12));
			if (Config.CELESTIAL_TOWER_SPAWN.get())
				event.getSpawns().addSpawn(MobCategory.MONSTER, new SpawnerData(DeferredRegistryHandler.CELESTIAL_TOWER_ENTITY.get(), 2, 0, 1));
		}

		// TODO: Rework when Forge API updates
		/*if (event.getCategory() == BiomeCategory.FOREST) {
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_ABANDONED_FACTORY);
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_UNDERGROUND_BUNKER);
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_BEAR_TRAP);
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_OUTHOUSE);
		}
		if (event.getCategory() == BiomeCategory.PLAINS) {
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_ABANDONED_FACTORY);
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_CAMPSITE);
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_OUTHOUSE);
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_WATER_TOWER);
		}
		if (event.getCategory() == BiomeCategory.JUNGLE) {
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_PITFALL_TRAP);
		}
		if (event.getCategory() == BiomeCategory.DESERT) {
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_LANDMINE_TRAP);
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_CAMPSITE);
		}
		if (event.getCategory() == BiomeCategory.TAIGA) {
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_CLOUD_ISLAND);
		}
		if (Objects.requireNonNull(event.getName()).toString().equals(Objects.requireNonNull(DeferredRegistryHandler.BATTLEFIELD.get().getRegistryName()).toString())) {
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_BATTLEFIELD_CAMP);
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_UNDERGROUND_BUNKER);
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_BEAR_TRAP);
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_BATTLEFIELD_VILLAGE);
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_BATTLEFIELD_HOUSE);
			generation.addStructureStart(ConfiguredStructures.CONFIGURED_GRAVEYARD);
			generation.addFeature(Decoration.VEGETAL_DECORATION, ConfiguredStructures.CONFIGURED_WOODEN_SPIKES);
			generation.addFeature(Decoration.VEGETAL_DECORATION, ConfiguredStructures.CONFIGURED_BURNED_OAK_TREE);
			generation.addCarver(Carving.AIR, new ConfiguredWorldCarver<>(CanyonWorldCarver.CANYON, new CanyonCarverConfiguration(0.15F, BiasedToBottomHeight.of(VerticalAnchor.absolute(68), VerticalAnchor.absolute(70), 2), ConstantFloat.of(0.5F), VerticalAnchor.aboveBottom(20), false, CarverDebugSettings.of(false, Blocks.WARPED_BUTTON.defaultBlockState()), UniformFloat.of(-0.125F, 0.125F), new CanyonCarverConfiguration.CanyonShapeConfiguration(UniformFloat.of(0.5F, 0.75F), TrapezoidFloat.of(0.0F, 4.0F, 1.0F), 3, UniformFloat.of(0.75F, 1.0F), 0.5F, 0.0F))));
		}
		if (Objects.requireNonNull(event.getName()).toString().equals(Objects.requireNonNull(DeferredRegistryHandler.TILTROS.get().getRegistryName()).toString())) {
			generation.addCarver(Carving.AIR, new ConfiguredWorldCarver<>(CanyonWorldCarver.CANYON, new CanyonCarverConfiguration(0.15F, BiasedToBottomHeight.of(VerticalAnchor.absolute(68), VerticalAnchor.absolute(70), 2), ConstantFloat.of(0.65F), VerticalAnchor.aboveBottom(10), false, CarverDebugSettings.of(false, Blocks.WARPED_BUTTON.defaultBlockState()), UniformFloat.of(-0.125F, 0.125F), new CanyonCarverConfiguration.CanyonShapeConfiguration(UniformFloat.of(0.0F, 1.0F), TrapezoidFloat.of(0.0F, 106.0F, 2.0F), 3, UniformFloat.of(0.75F, 1.0F), 1.0F, 0.0F))));
		}*/
	}

	/**
	 * Event handler for the WorldEvent.Load event.
	 * Most importantly, we are building a Map
	 * to contain our structures.
	 *
	 * @param event the <code>WorldEvent.Load</code> instance
	 */
	// TODO: Rework when Forge API updates
	/*public void worldLoadEvent(WorldEvent.Load event) {
		if (event.getWorld() instanceof ServerLevel serverWorld) {

			if (serverWorld.getChunkSource().getGenerator() instanceof FlatLevelSource &&
					serverWorld.dimension().equals(Level.OVERWORLD)) {
				return;
			}

			Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(serverWorld.getChunkSource().getGenerator().getSettings().structureConfig());
			tempMap.put(DeferredRegistryHandler.ABANDONED_FACTORY_STRUCTURE.get(), StructureSettings.DEFAULTS.get(DeferredRegistryHandler.ABANDONED_FACTORY_STRUCTURE.get()));
			tempMap.put(DeferredRegistryHandler.PITFALL_TRAP_STRUCTURE.get(), StructureSettings.DEFAULTS.get(DeferredRegistryHandler.PITFALL_TRAP_STRUCTURE.get()));
			tempMap.put(DeferredRegistryHandler.BEAR_TRAP_STRUCTURE.get(), StructureSettings.DEFAULTS.get(DeferredRegistryHandler.BEAR_TRAP_STRUCTURE.get()));
			tempMap.put(DeferredRegistryHandler.LANDMINE_TRAP_STRUCTURE.get(), StructureSettings.DEFAULTS.get(DeferredRegistryHandler.LANDMINE_TRAP_STRUCTURE.get()));
			tempMap.put(DeferredRegistryHandler.UNDERGROUND_BUNKER_STRUCTURE.get(), StructureSettings.DEFAULTS.get(DeferredRegistryHandler.UNDERGROUND_BUNKER_STRUCTURE.get()));
			tempMap.put(DeferredRegistryHandler.BATTLEFIELD_CAMP_STRUCTURE.get(), StructureSettings.DEFAULTS.get(DeferredRegistryHandler.BATTLEFIELD_CAMP_STRUCTURE.get()));
			tempMap.put(DeferredRegistryHandler.BATTLEFIELD_VILLAGE_STRUCTURE.get(), StructureSettings.DEFAULTS.get(DeferredRegistryHandler.BATTLEFIELD_VILLAGE_STRUCTURE.get()));
			tempMap.put(DeferredRegistryHandler.CLOUD_ISLAND_STRUCTURE.get(), StructureSettings.DEFAULTS.get(DeferredRegistryHandler.CLOUD_ISLAND_STRUCTURE.get()));
			tempMap.put(DeferredRegistryHandler.CAMPSITE_STRUCTURE.get(), StructureSettings.DEFAULTS.get(DeferredRegistryHandler.CAMPSITE_STRUCTURE.get()));
			tempMap.put(DeferredRegistryHandler.BATTLEFIELD_HOUSE_STRUCTURE.get(), StructureSettings.DEFAULTS.get(DeferredRegistryHandler.BATTLEFIELD_HOUSE_STRUCTURE.get()));
			tempMap.put(DeferredRegistryHandler.OUTHOUSE_STRUCTURE.get(), StructureSettings.DEFAULTS.get(DeferredRegistryHandler.OUTHOUSE_STRUCTURE.get()));
			tempMap.put(DeferredRegistryHandler.WATER_TOWER_STRUCTURE.get(), StructureSettings.DEFAULTS.get(DeferredRegistryHandler.WATER_TOWER_STRUCTURE.get()));
			tempMap.put(DeferredRegistryHandler.GRAVEYARD_STRUCTURE.get(), StructureSettings.DEFAULTS.get(DeferredRegistryHandler.GRAVEYARD_STRUCTURE.get()));
			serverWorld.getChunkSource().getGenerator().getSettings().structureConfig = tempMap;
		}
	}*/
}