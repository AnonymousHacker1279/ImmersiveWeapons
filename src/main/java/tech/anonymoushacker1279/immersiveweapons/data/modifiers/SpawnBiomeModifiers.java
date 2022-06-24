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

		addDyingSoldierSpawn = AddSpawnsBiomeModifier.singleSpawn(
				battlefieldTag,
				new SpawnerData(DeferredRegistryHandler.DYING_SOLDIER_ENTITY.get(), 95, 1, 3)
		);
		addWanderingWarriorSpawn = AddSpawnsBiomeModifier.singleSpawn(
				overworldTag,
				new SpawnerData(DeferredRegistryHandler.WANDERING_WARRIOR_ENTITY.get(), 65, 1, 1)
		);
	}

	private static HashMap<ResourceLocation, BiomeModifier> getBiomeModifiers() {
		HashMap<ResourceLocation, BiomeModifier> modifiers = new HashMap<>(10);
		modifiers.put(ADD_DYING_SOLDIER_SPAWN, addDyingSoldierSpawn);
		modifiers.put(ADD_WANDERING_WARRIOR_SPAWN, addWanderingWarriorSpawn);

		return modifiers;
	}
}