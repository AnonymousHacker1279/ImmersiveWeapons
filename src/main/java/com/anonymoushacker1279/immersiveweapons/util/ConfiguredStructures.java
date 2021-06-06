package com.anonymoushacker1279.immersiveweapons.util;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class ConfiguredStructures {

	public static StructureFeature<?, ?> CONFIGURED_ABANDONED_FACTORY = Structures.ABANDONED_FACTORY.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
	public static StructureFeature<?, ?> CONFIGURED_PITFALL_TRAP = Structures.PITFALL_TRAP.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
	public static StructureFeature<?, ?> CONFIGURED_BEAR_TRAP = Structures.BEAR_TRAP.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
	public static StructureFeature<?, ?> CONFIGURED_LANDMINE_TRAP = Structures.LANDMINE_TRAP.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
	public static StructureFeature<?, ?> CONFIGURED_UNDERGROUND_BUNKER = Structures.UNDERGROUND_BUNKER.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
	public static StructureFeature<?, ?> CONFIGURED_BATTLEFIELD_CAMP = Structures.BATTLEFIELD_CAMP.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
	public static StructureFeature<?, ?> CONFIGURED_BATTLEFIELD_VILLAGE = Structures.BATTLEFIELD_VILLAGE.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);

	public static void registerConfiguredStructures() {
		Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
		Registry.register(registry, new ResourceLocation(ImmersiveWeapons.MOD_ID, "configured_abandoned_factory"), CONFIGURED_ABANDONED_FACTORY);
		FlatGenerationSettings.STRUCTURES.put(Structures.ABANDONED_FACTORY.get(), CONFIGURED_ABANDONED_FACTORY);

		Registry.register(registry, new ResourceLocation(ImmersiveWeapons.MOD_ID, "configured_pitfall_trap"), CONFIGURED_PITFALL_TRAP);
		FlatGenerationSettings.STRUCTURES.put(Structures.PITFALL_TRAP.get(), CONFIGURED_PITFALL_TRAP);

		Registry.register(registry, new ResourceLocation(ImmersiveWeapons.MOD_ID, "configured_bear_trap"), CONFIGURED_BEAR_TRAP);
		FlatGenerationSettings.STRUCTURES.put(Structures.BEAR_TRAP.get(), CONFIGURED_BEAR_TRAP);

		Registry.register(registry, new ResourceLocation(ImmersiveWeapons.MOD_ID, "configured_landmine_trap"), CONFIGURED_LANDMINE_TRAP);
		FlatGenerationSettings.STRUCTURES.put(Structures.LANDMINE_TRAP.get(), CONFIGURED_LANDMINE_TRAP);

		Registry.register(registry, new ResourceLocation(ImmersiveWeapons.MOD_ID, "configured_underground_bunker"), CONFIGURED_UNDERGROUND_BUNKER);
		FlatGenerationSettings.STRUCTURES.put(Structures.UNDERGROUND_BUNKER.get(), CONFIGURED_UNDERGROUND_BUNKER);

		Registry.register(registry, new ResourceLocation(ImmersiveWeapons.MOD_ID, "configured_battlefield_camp"), CONFIGURED_BATTLEFIELD_CAMP);
		FlatGenerationSettings.STRUCTURES.put(Structures.BATTLEFIELD_CAMP.get(), CONFIGURED_BATTLEFIELD_CAMP);

		Registry.register(registry, new ResourceLocation(ImmersiveWeapons.MOD_ID, "configured_battlefield_village"), CONFIGURED_BATTLEFIELD_VILLAGE);
		FlatGenerationSettings.STRUCTURES.put(Structures.BATTLEFIELD_VILLAGE.get(), CONFIGURED_BATTLEFIELD_VILLAGE);
	}

}