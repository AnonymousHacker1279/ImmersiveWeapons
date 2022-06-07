package com.anonymoushacker1279.immersiveweapons.event;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.config.CommonConfig;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.world.level.levelgen.OreGeneratorHandler;
import com.anonymoushacker1279.immersiveweapons.world.level.levelgen.biomes.BiomesAndDimensions;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.RegistryEvent.MissingMappings;
import net.minecraftforge.event.RegistryEvent.MissingMappings.Mapping;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import java.util.*;

@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = Bus.FORGE)
public class ForgeEventSubscriber {

	/**
	 * Event handler for the BiomeLoadingEvent.
	 * Configures custom ores, carvers, spawns, etc.
	 *
	 * @param event the <code>BiomeLoadingEvent</code> instance
	 */
	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void biomeLoading(BiomeLoadingEvent event) {
		BiomeGenerationSettingsBuilder generation = event.getGeneration();
		BiomeCategory biomeCategory = event.getCategory();

		if (biomeCategory == BiomeCategory.NETHER) {
			if (CommonConfig.ENABLE_MOLTEN_ORE.get()) {
				generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES,
						Holder.direct(OreGeneratorHandler.MOLTEN_ORE_BLOB_PLACEMENT));
			}
			if (CommonConfig.ENABLE_NETHER_SULFUR_ORE.get()) {
				generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES,
						Holder.direct(OreGeneratorHandler.NETHER_SULFUR_ORE_BLOB_PLACEMENT));
			}
		} else if (biomeCategory != BiomeCategory.THEEND) {
			if (CommonConfig.ENABLE_COBALT_ORE.get()) {
				generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES,
						Holder.direct(OreGeneratorHandler.COBALT_ORE_BLOB_PLACEMENT));
			}
			if (CommonConfig.ENABLE_DEEPSLATE_COBALT_ORE.get()) {
				generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES,
						Holder.direct(OreGeneratorHandler.DEEPSLATE_COBALT_ORE_BLOB_PLACEMENT));
			}

			if (biomeCategory != BiomeCategory.RIVER && biomeCategory != BiomeCategory.OCEAN
					&& biomeCategory != BiomeCategory.ICY && biomeCategory != BiomeCategory.MUSHROOM
					&& biomeCategory != BiomeCategory.UNDERGROUND && biomeCategory != BiomeCategory.NONE) {

				if (Objects.equals(event.getName(), BiomesAndDimensions.BATTLEFIELD.location())) {
					if (CommonConfig.ENABLE_DYING_SOLDIER_SPAWN.get()) {
						event.getSpawns().addSpawn(MobCategory.MONSTER,
								new SpawnerData(DeferredRegistryHandler.DYING_SOLDIER_ENTITY.get(),
										CommonConfig.DYING_SOLDIER_SPAWN_WEIGHT.get(),
										CommonConfig.DYING_SOLDIER_SPAWN_MIN_COUNT.get(),
										CommonConfig.DYING_SOLDIER_SPAWN_MAX_COUNT.get()));
						event.getSpawns().addMobCharge(DeferredRegistryHandler.HANS_ENTITY.get(), 0.1f, 2.5f);
					}
				}

				if (CommonConfig.ENABLE_WANDERING_WARRIOR_SPAWN.get()) {
					event.getSpawns().addSpawn(MobCategory.MONSTER,
							new SpawnerData(DeferredRegistryHandler.WANDERING_WARRIOR_ENTITY.get(),
									CommonConfig.WANDERING_WARRIOR_SPAWN_WEIGHT.get(),
									CommonConfig.WANDERING_WARRIOR_SPAWN_MIN_COUNT.get(),
									CommonConfig.WANDERING_WARRIOR_SPAWN_MAX_COUNT.get()));
					event.getSpawns().addMobCharge(DeferredRegistryHandler.WANDERING_WARRIOR_ENTITY.get(), 0.1f, 0.8f);
				}
				if (CommonConfig.ENABLE_HANS_SPAWN.get()) {
					event.getSpawns().addSpawn(MobCategory.MONSTER,
							new SpawnerData(DeferredRegistryHandler.HANS_ENTITY.get(),
									CommonConfig.HANS_SPAWN_WEIGHT.get(),
									CommonConfig.HANS_SPAWN_MIN_COUNT.get(),
									CommonConfig.HANS_SPAWN_MAX_COUNT.get()));
					event.getSpawns().addMobCharge(DeferredRegistryHandler.HANS_ENTITY.get(), 0.1f, 0.1f);
				}
			}
		}

		if (biomeCategory == BiomeCategory.RIVER || biomeCategory == BiomeCategory.OCEAN) {
			if (CommonConfig.ENABLE_SULFUR_ORE.get()) {
				generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES,
						Holder.direct(OreGeneratorHandler.SULFUR_ORE_BLOB_PLACEMENT));
			}
		}
		if (Objects.equals(event.getName(), Biomes.LUSH_CAVES.location())
				|| Objects.equals(event.getName(), Biomes.DRIPSTONE_CAVES.location())) {
			if (CommonConfig.ENABLE_DEEPSLATE_SULFUR_ORE.get()) {
				generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES,
						Holder.direct(OreGeneratorHandler.DEEPSLATE_SULFUR_ORE_BLOB_PLACEMENT));
			}
		}
		if (Objects.equals(event.getName(), BiomesAndDimensions.B_TILTROS.location())) {
			if (CommonConfig.ENABLE_ROCK_SPIDER_SPAWN.get()) {
				event.getSpawns().addSpawn(MobCategory.MONSTER,
						new SpawnerData(DeferredRegistryHandler.ROCK_SPIDER_ENTITY.get(),
								CommonConfig.ROCK_SPIDER_SPAWN_WEIGHT.get(),
								CommonConfig.ROCK_SPIDER_SPAWN_MIN_COUNT.get(),
								CommonConfig.ROCK_SPIDER_SPAWN_MAX_COUNT.get()));
				event.getSpawns().addMobCharge(DeferredRegistryHandler.ROCK_SPIDER_ENTITY.get(), 0.1f, 2.0f);
			}
			if (CommonConfig.ENABLE_LAVA_REVENANT_SPAWN.get()) {
				event.getSpawns().addSpawn(MobCategory.MONSTER,
						new SpawnerData(DeferredRegistryHandler.LAVA_REVENANT_ENTITY.get(),
								CommonConfig.LAVA_REVENANT_SPAWN_WEIGHT.get(),
								CommonConfig.LAVA_REVENANT_SPAWN_MIN_COUNT.get(),
								CommonConfig.LAVA_REVENANT_SPAWN_MAX_COUNT.get()));
				event.getSpawns().addMobCharge(DeferredRegistryHandler.LAVA_REVENANT_ENTITY.get(), 0.1f, 0.3f);
			}
			if (CommonConfig.ENABLE_CELESTIAL_TOWER_SPAWN.get()) {
				event.getSpawns().addSpawn(MobCategory.MONSTER,
						new SpawnerData(DeferredRegistryHandler.CELESTIAL_TOWER_ENTITY.get(),
								CommonConfig.CELESTIAL_TOWER_SPAWN_WEIGHT.get(),
								CommonConfig.CELESTIAL_TOWER_SPAWN_MIN_COUNT.get(),
								CommonConfig.CELESTIAL_TOWER_SPAWN_MAX_COUNT.get()));
				event.getSpawns().addMobCharge(DeferredRegistryHandler.CELESTIAL_TOWER_ENTITY.get(), 0.1f, 0.1f);
			}
		}
	}

	/**
	 * Event handler for the MissingMappings (Item) event.
	 * Migrates old registry names to newer ones.
	 *
	 * @param event the <code>MissingMappings</code> instance
	 */
	@SubscribeEvent
	public static void missingItemMappings(MissingMappings<Item> event) {
		ImmutableList<Mapping<Item>> mappings = event.getMappings(ImmersiveWeapons.MOD_ID);

		if (!mappings.isEmpty()) {

			ImmersiveWeapons.LOGGER.warn("Missing item mappings were found. This probably means an item was renamed or deleted. Attempting to remap...");

			List<String> remappedItems = new ArrayList<>(0);

			ResourceLocation SMALL_PARTS_METAL_THROWABLE_BOMB = new ResourceLocation(ImmersiveWeapons.MOD_ID, "small_parts_metal_throwable_bomb");
			ResourceLocation SMALL_PARTS_METAL_TOOL = new ResourceLocation(ImmersiveWeapons.MOD_ID, "small_parts_metal_tool");
			ResourceLocation SMOKE_BOMB = new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb");
			ResourceLocation SMOKE_BOMB_RED = new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb_red");
			ResourceLocation SMOKE_BOMB_GREEN = new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb_green");
			ResourceLocation SMOKE_BOMB_BLUE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb_blue");
			ResourceLocation SMOKE_BOMB_PURPLE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb_purple");
			ResourceLocation SMOKE_BOMB_YELLOW = new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb_yellow");
			ResourceLocation SMOKE_BOMB_ARROW = new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb_arrow");
			ResourceLocation SMOKE_BOMB_ARROW_RED = new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb_arrow_red");
			ResourceLocation SMOKE_BOMB_ARROW_GREEN = new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb_arrow_green");
			ResourceLocation SMOKE_BOMB_ARROW_BLUE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb_arrow_blue");
			ResourceLocation SMOKE_BOMB_ARROW_PURPLE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb_arrow_purple");
			ResourceLocation SMOKE_BOMB_ARROW_YELLOW = new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb_arrow_yellow");
			ResourceLocation GOLD_PIKE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "gold_pike");
			ResourceLocation GOLD_GAUNTLET = new ResourceLocation(ImmersiveWeapons.MOD_ID, "gold_gauntlet");
			ResourceLocation GOLD_PIKE_HEAD = new ResourceLocation(ImmersiveWeapons.MOD_ID, "gold_pike_head");
			ResourceLocation GOLD_ARROW = new ResourceLocation(ImmersiveWeapons.MOD_ID, "gold_arrow");
			ResourceLocation GOLD_MUSKET_BALL = new ResourceLocation(ImmersiveWeapons.MOD_ID, "gold_musket_ball");
			ResourceLocation WOOD_PIKE = new ResourceLocation(ImmersiveWeapons.MOD_ID, "wood_pike");
			ResourceLocation WOOD_GAUNTLET = new ResourceLocation(ImmersiveWeapons.MOD_ID, "wood_gauntlet");
			ResourceLocation WOOD_SHARD = new ResourceLocation(ImmersiveWeapons.MOD_ID, "wood_shard");
			ResourceLocation WOOD_TOOL_ROD = new ResourceLocation(ImmersiveWeapons.MOD_ID, "wood_tool_rod");
			ResourceLocation WOOD_PIKE_HEAD = new ResourceLocation(ImmersiveWeapons.MOD_ID, "wood_pike_head");
			ResourceLocation WOOD_ARROW = new ResourceLocation(ImmersiveWeapons.MOD_ID, "wood_arrow");
			ResourceLocation WOOD_MUSKET_BALL = new ResourceLocation(ImmersiveWeapons.MOD_ID, "wood_musket_ball");

			for (Mapping<Item> itemMapping : mappings) {
				if (itemMapping.key.equals(SMALL_PARTS_METAL_THROWABLE_BOMB)) {
					itemMapping.remap(DeferredRegistryHandler.GRENADE_ASSEMBLY.get());
					remappedItems.add(itemMapping.key.getPath());
				}
				if (itemMapping.key.equals(SMALL_PARTS_METAL_TOOL)) {
					itemMapping.remap(DeferredRegistryHandler.TOOL_JOINT.get());
					remappedItems.add(itemMapping.key.getPath());
				}
				if (itemMapping.key.equals(SMOKE_BOMB)) {
					itemMapping.remap(DeferredRegistryHandler.SMOKE_GRENADE.get());
					remappedItems.add(itemMapping.key.getPath());
				}
				if (itemMapping.key.equals(SMOKE_BOMB_RED)) {
					itemMapping.remap(DeferredRegistryHandler.SMOKE_GRENADE_RED.get());
					remappedItems.add(itemMapping.key.getPath());
				}
				if (itemMapping.key.equals(SMOKE_BOMB_GREEN)) {
					itemMapping.remap(DeferredRegistryHandler.SMOKE_GRENADE_GREEN.get());
					remappedItems.add(itemMapping.key.getPath());
				}
				if (itemMapping.key.equals(SMOKE_BOMB_BLUE)) {
					itemMapping.remap(DeferredRegistryHandler.SMOKE_GRENADE_BLUE.get());
					remappedItems.add(itemMapping.key.getPath());
				}
				if (itemMapping.key.equals(SMOKE_BOMB_PURPLE)) {
					itemMapping.remap(DeferredRegistryHandler.SMOKE_GRENADE_PURPLE.get());
					remappedItems.add(itemMapping.key.getPath());
				}
				if (itemMapping.key.equals(SMOKE_BOMB_YELLOW)) {
					itemMapping.remap(DeferredRegistryHandler.SMOKE_GRENADE_YELLOW.get());
					remappedItems.add(itemMapping.key.getPath());
				}
				if (itemMapping.key.equals(SMOKE_BOMB_ARROW)) {
					itemMapping.remap(DeferredRegistryHandler.SMOKE_GRENADE_ARROW.get());
					remappedItems.add(itemMapping.key.getPath());
				}
				if (itemMapping.key.equals(SMOKE_BOMB_ARROW_RED)) {
					itemMapping.remap(DeferredRegistryHandler.SMOKE_GRENADE_ARROW_RED.get());
					remappedItems.add(itemMapping.key.getPath());
				}
				if (itemMapping.key.equals(SMOKE_BOMB_ARROW_GREEN)) {
					itemMapping.remap(DeferredRegistryHandler.SMOKE_GRENADE_ARROW_GREEN.get());
					remappedItems.add(itemMapping.key.getPath());
				}
				if (itemMapping.key.equals(SMOKE_BOMB_ARROW_BLUE)) {
					itemMapping.remap(DeferredRegistryHandler.SMOKE_GRENADE_ARROW_BLUE.get());
					remappedItems.add(itemMapping.key.getPath());
				}
				if (itemMapping.key.equals(SMOKE_BOMB_ARROW_PURPLE)) {
					itemMapping.remap(DeferredRegistryHandler.SMOKE_GRENADE_ARROW_PURPLE.get());
					remappedItems.add(itemMapping.key.getPath());
				}
				if (itemMapping.key.equals(SMOKE_BOMB_ARROW_YELLOW)) {
					itemMapping.remap(DeferredRegistryHandler.SMOKE_GRENADE_ARROW_YELLOW.get());
					remappedItems.add(itemMapping.key.getPath());
				}
				if (itemMapping.key.equals(GOLD_PIKE)) {
					itemMapping.remap(DeferredRegistryHandler.GOLDEN_PIKE.get());
					remappedItems.add(itemMapping.key.getPath());
				}
				if (itemMapping.key.equals(GOLD_GAUNTLET)) {
					itemMapping.remap(DeferredRegistryHandler.GOLDEN_GAUNTLET.get());
					remappedItems.add(itemMapping.key.getPath());
				}
				if (itemMapping.key.equals(GOLD_PIKE_HEAD)) {
					itemMapping.remap(DeferredRegistryHandler.GOLDEN_PIKE_HEAD.get());
					remappedItems.add(itemMapping.key.getPath());
				}
				if (itemMapping.key.equals(GOLD_ARROW)) {
					itemMapping.remap(DeferredRegistryHandler.GOLDEN_ARROW.get());
					remappedItems.add(itemMapping.key.getPath());
				}
				if (itemMapping.key.equals(GOLD_MUSKET_BALL)) {
					itemMapping.remap(DeferredRegistryHandler.GOLDEN_MUSKET_BALL.get());
					remappedItems.add(itemMapping.key.getPath());
				}
				if (itemMapping.key.equals(WOOD_PIKE)) {
					itemMapping.remap(DeferredRegistryHandler.WOODEN_PIKE.get());
					remappedItems.add(itemMapping.key.getPath());
				}
				if (itemMapping.key.equals(WOOD_GAUNTLET)) {
					itemMapping.remap(DeferredRegistryHandler.WOODEN_GAUNTLET.get());
					remappedItems.add(itemMapping.key.getPath());
				}
				if (itemMapping.key.equals(WOOD_SHARD)) {
					itemMapping.remap(DeferredRegistryHandler.WOODEN_SHARD.get());
					remappedItems.add(itemMapping.key.getPath());
				}
				if (itemMapping.key.equals(WOOD_TOOL_ROD)) {
					itemMapping.remap(DeferredRegistryHandler.WOODEN_TOOL_ROD.get());
					remappedItems.add(itemMapping.key.getPath());
				}
				if (itemMapping.key.equals(WOOD_PIKE_HEAD)) {
					itemMapping.remap(DeferredRegistryHandler.WOODEN_PIKE_HEAD.get());
					remappedItems.add(itemMapping.key.getPath());
				}
				if (itemMapping.key.equals(WOOD_ARROW)) {
					itemMapping.remap(DeferredRegistryHandler.WOODEN_ARROW.get());
					remappedItems.add(itemMapping.key.getPath());
				}
				if (itemMapping.key.equals(WOOD_MUSKET_BALL)) {
					itemMapping.remap(DeferredRegistryHandler.WOODEN_MUSKET_BALL.get());
					remappedItems.add(itemMapping.key.getPath());
				}
			}

			ImmersiveWeapons.LOGGER.warn("Item remapping complete. Remapped entries: {}", remappedItems);
			ImmersiveWeapons.LOGGER.warn("{}/{} items remapped.", remappedItems.size(), mappings.size());
		}
	}

	/**
	 * Event handler for the MissingMappings (Entity) event.
	 * Migrates old registry names to newer ones.
	 *
	 * @param event the <code>MissingMappings</code> instance
	 */
	@SubscribeEvent
	public static void missingEntityMappings(MissingMappings<EntityType<?>> event) {
		ImmutableList<Mapping<EntityType<?>>> mappings = event.getMappings(ImmersiveWeapons.MOD_ID);

		if (!mappings.isEmpty()) {

			ImmersiveWeapons.LOGGER.warn("Missing entity mappings were found. This probably means an entity was renamed or deleted. Attempting to remap...");

			List<String> remappedEntities = new ArrayList<>(0);

			ResourceLocation SMOKE_BOMB = new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb");
			ResourceLocation SMOKE_BOMB_ARROW = new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb_arrow");
			ResourceLocation GOLD_ARROW = new ResourceLocation(ImmersiveWeapons.MOD_ID, "gold_arrow");
			ResourceLocation GOLD_MUSKET_BALL = new ResourceLocation(ImmersiveWeapons.MOD_ID, "gold_musket_ball");
			ResourceLocation WOOD_ARROW = new ResourceLocation(ImmersiveWeapons.MOD_ID, "wood_arrow");
			ResourceLocation WOOD_MUSKET_BALL = new ResourceLocation(ImmersiveWeapons.MOD_ID, "wood_musket_ball");

			for (Mapping<EntityType<?>> entityTypeMapping : mappings) {
				if (entityTypeMapping.key.equals(SMOKE_BOMB)) {
					entityTypeMapping.remap(DeferredRegistryHandler.SMOKE_GRENADE_ENTITY.get());
					remappedEntities.add(entityTypeMapping.key.getPath());
				}
				if (entityTypeMapping.key.equals(SMOKE_BOMB_ARROW)) {
					entityTypeMapping.remap(DeferredRegistryHandler.SMOKE_GRENADE_ARROW_ENTITY.get());
					remappedEntities.add(entityTypeMapping.key.getPath());
				}
				if (entityTypeMapping.key.equals(GOLD_ARROW)) {
					entityTypeMapping.remap(DeferredRegistryHandler.GOLDEN_ARROW_ENTITY.get());
					remappedEntities.add(entityTypeMapping.key.getPath());
				}
				if (entityTypeMapping.key.equals(GOLD_MUSKET_BALL)) {
					entityTypeMapping.remap(DeferredRegistryHandler.GOLDEN_MUSKET_BALL_ENTITY.get());
					remappedEntities.add(entityTypeMapping.key.getPath());
				}
				if (entityTypeMapping.key.equals(WOOD_ARROW)) {
					entityTypeMapping.remap(DeferredRegistryHandler.WOODEN_ARROW_ENTITY.get());
					remappedEntities.add(entityTypeMapping.key.getPath());
				}
				if (entityTypeMapping.key.equals(WOOD_MUSKET_BALL)) {
					entityTypeMapping.remap(DeferredRegistryHandler.WOODEN_MUSKET_BALL_ENTITY.get());
					remappedEntities.add(entityTypeMapping.key.getPath());
				}
			}

			ImmersiveWeapons.LOGGER.warn("Entity remapping complete. Remapped entries: {}", remappedEntities);
			ImmersiveWeapons.LOGGER.warn("{}/{} entities remapped.", remappedEntities.size(), mappings.size());
		}
	}

	/**
	 * Event handler for the MissingMappings (Sound) event.
	 * Migrates old registry names to newer ones.
	 *
	 * @param event the <code>MissingMappings</code> instance
	 */
	@SubscribeEvent
	public static void missingSoundEventMappings(MissingMappings<SoundEvent> event) {
		ImmutableList<Mapping<SoundEvent>> mappings = event.getMappings(ImmersiveWeapons.MOD_ID);

		if (!mappings.isEmpty()) {

			ImmersiveWeapons.LOGGER.warn("Missing sound event mappings were found. This probably means a sound was renamed or deleted. Attempting to remap...");

			List<String> remappedSoundEvents = new ArrayList<>(0);

			ResourceLocation SMOKE_BOMB_HISS = new ResourceLocation(ImmersiveWeapons.MOD_ID, "smoke_bomb_hiss");

			for (Mapping<SoundEvent> soundEventMapping : mappings) {
				if (soundEventMapping.key.equals(SMOKE_BOMB_HISS)) {
					soundEventMapping.remap(DeferredRegistryHandler.SMOKE_GRENADE_HISS.get());
					remappedSoundEvents.add(soundEventMapping.key.getPath());
				}
			}

			ImmersiveWeapons.LOGGER.warn("Sound event remapping complete. Remapped entries: {}", remappedSoundEvents);
			ImmersiveWeapons.LOGGER.warn("{}/{} sounds remapped.", remappedSoundEvents.size(), mappings.size());
		}
	}
}