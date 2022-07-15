package tech.anonymoushacker1279.immersiveweapons.data.modifiers;

import com.google.gson.JsonElement;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.*;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddSpawnsBiomeModifier;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.ImmersiveWeaponsWorldGenTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

import java.util.HashMap;

public class SpawnBiomeModifiers {

	private static final ResourceLocation ADD_DYING_SOLDIER_SPAWN = new ResourceLocation(ImmersiveWeapons.MOD_ID, "add_dying_soldier_spawn");
	private static BiomeModifier addDyingSoldierSpawn;
	private static final ResourceLocation ADD_WANDERING_WARRIOR_SPAWN = new ResourceLocation(ImmersiveWeapons.MOD_ID, "add_wandering_warrior_spawn");
	private static BiomeModifier addWanderingWarriorSpawn;
	private static final ResourceLocation ADD_HANS_SPAWN = new ResourceLocation(ImmersiveWeapons.MOD_ID, "add_hans_spawn");
	private static BiomeModifier addHansSpawn;
	private static final ResourceLocation ADD_ROCK_SPIDER_SPAWN = new ResourceLocation(ImmersiveWeapons.MOD_ID, "add_rock_spider_spawn");
	private static BiomeModifier addRockSpiderSpawn;
	private static final ResourceLocation ADD_LAVA_REVENANT_SPAWN = new ResourceLocation(ImmersiveWeapons.MOD_ID, "add_lava_revenant_spawn");
	private static BiomeModifier addLavaRevenantSpawn;
	private static final ResourceLocation ADD_CELESTIAL_TOWER_SPAWN = new ResourceLocation(ImmersiveWeapons.MOD_ID, "add_celestial_tower_spawn");
	private static BiomeModifier addCelestialTowerSpawn;

	public static JsonCodecProvider<BiomeModifier> getCodecProvider(DataGenerator generator,
	                                                                ExistingFileHelper existingFileHelper,
	                                                                RegistryOps<JsonElement> registryOps,
	                                                                ResourceKey<Registry<BiomeModifier>> biomeModifiersKey) {

		fillFeatures(registryOps);
		return JsonCodecProvider.forDatapackRegistry(
				generator, existingFileHelper, ImmersiveWeapons.MOD_ID, registryOps, biomeModifiersKey, getBiomeModifiers());
	}

	private static void fillFeatures(RegistryOps<JsonElement> registryOps) {
		HolderSet.Named<Biome> battlefieldTag = new HolderSet.Named<>(registryOps.registry(Registry.BIOME_REGISTRY).get(), ImmersiveWeaponsWorldGenTagGroups.IS_BATTLEFIELD);
		HolderSet.Named<Biome> overworldTag = new HolderSet.Named<>(registryOps.registry(Registry.BIOME_REGISTRY).get(), BiomeTags.IS_OVERWORLD);
		HolderSet.Named<Biome> tiltrosTag = new HolderSet.Named<>(registryOps.registry(Registry.BIOME_REGISTRY).get(), ImmersiveWeaponsWorldGenTagGroups.IS_TILTROS);

		addDyingSoldierSpawn = AddSpawnsBiomeModifier.singleSpawn(
				battlefieldTag,
				new SpawnerData(DeferredRegistryHandler.DYING_SOLDIER_ENTITY.get(), 95, 1, 3)
		);
		addWanderingWarriorSpawn = AddSpawnsBiomeModifier.singleSpawn(
				overworldTag,
				new SpawnerData(DeferredRegistryHandler.WANDERING_WARRIOR_ENTITY.get(), 65, 1, 1)
		);
		addHansSpawn = AddSpawnsBiomeModifier.singleSpawn(
				overworldTag,
				new SpawnerData(DeferredRegistryHandler.HANS_ENTITY.get(), 5, 1, 1)
		);
		addRockSpiderSpawn = AddSpawnsBiomeModifier.singleSpawn(
				tiltrosTag,
				new SpawnerData(DeferredRegistryHandler.ROCK_SPIDER_ENTITY.get(), 65, 2, 4)
		);
		addLavaRevenantSpawn = AddSpawnsBiomeModifier.singleSpawn(
				tiltrosTag,
				new SpawnerData(DeferredRegistryHandler.LAVA_REVENANT_ENTITY.get(), 35, 1, 1)
		);
		addCelestialTowerSpawn = AddSpawnsBiomeModifier.singleSpawn(
				tiltrosTag,
				new SpawnerData(DeferredRegistryHandler.CELESTIAL_TOWER_ENTITY.get(), 5, 1, 1)
		);
	}

	private static HashMap<ResourceLocation, BiomeModifier> getBiomeModifiers() {
		HashMap<ResourceLocation, BiomeModifier> modifiers = new HashMap<>(10);
		modifiers.put(ADD_DYING_SOLDIER_SPAWN, addDyingSoldierSpawn);
		modifiers.put(ADD_WANDERING_WARRIOR_SPAWN, addWanderingWarriorSpawn);
		modifiers.put(ADD_HANS_SPAWN, addHansSpawn);
		modifiers.put(ADD_ROCK_SPIDER_SPAWN, addRockSpiderSpawn);
		modifiers.put(ADD_LAVA_REVENANT_SPAWN, addLavaRevenantSpawn);
		modifiers.put(ADD_CELESTIAL_TOWER_SPAWN, addCelestialTowerSpawn);

		return modifiers;
	}
}