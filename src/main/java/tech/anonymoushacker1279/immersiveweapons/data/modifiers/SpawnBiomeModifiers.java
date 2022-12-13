package tech.anonymoushacker1279.immersiveweapons.data.modifiers;

import com.google.gson.JsonElement;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.*;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddSpawnsBiomeModifier;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;

import java.util.HashMap;

public class SpawnBiomeModifiers {

	private static final ResourceLocation ADD_WANDERING_WARRIOR_SPAWN = new ResourceLocation(ImmersiveWeapons.MOD_ID, "add_wandering_warrior_spawn");
	private static BiomeModifier addWanderingWarriorSpawn;
	private static final ResourceLocation ADD_HANS_SPAWN = new ResourceLocation(ImmersiveWeapons.MOD_ID, "add_hans_spawn");
	private static BiomeModifier addHansSpawn;

	public static JsonCodecProvider<BiomeModifier> getCodecProvider(PackOutput output,
	                                                                ExistingFileHelper existingFileHelper,
	                                                                RegistryOps<JsonElement> registryOps,
	                                                                ResourceKey<Registry<BiomeModifier>> biomeModifiersKey) {

		fillFeatures(registryOps);
		return JsonCodecProvider.forDatapackRegistry(
				output, existingFileHelper, ImmersiveWeapons.MOD_ID, registryOps, biomeModifiersKey, getBiomeModifiers());
	}

	private static void fillFeatures(RegistryOps<JsonElement> registryOps) {
		HolderSet.Named<Biome> overworldTag = registryOps.getter(Registries.BIOME).get().getOrThrow(BiomeTags.IS_OVERWORLD);

		addWanderingWarriorSpawn = AddSpawnsBiomeModifier.singleSpawn(
				overworldTag,
				new SpawnerData(EntityRegistry.WANDERING_WARRIOR_ENTITY.get(), 65, 1, 1)
		);
		addHansSpawn = AddSpawnsBiomeModifier.singleSpawn(
				overworldTag,
				new SpawnerData(EntityRegistry.HANS_ENTITY.get(), 5, 1, 1)
		);
	}

	private static HashMap<ResourceLocation, BiomeModifier> getBiomeModifiers() {
		HashMap<ResourceLocation, BiomeModifier> modifiers = new HashMap<>(10);
		modifiers.put(ADD_WANDERING_WARRIOR_SPAWN, addWanderingWarriorSpawn);
		modifiers.put(ADD_HANS_SPAWN, addHansSpawn);

		return modifiers;
	}
}