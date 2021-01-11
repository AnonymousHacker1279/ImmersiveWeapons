package com.anonymoushacker1279.immersiveweapons.util;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class STConfiguredStructures {

	public static StructureFeature<?, ?> CONFIGURED_ABANDONED_FACTORY = STStructures.ABANDONED_FACTORY.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);

    public static void registerConfiguredStructures() {
          Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
          Registry.register(registry, new ResourceLocation(ImmersiveWeapons.MOD_ID, "configured_abandoned_factory"), CONFIGURED_ABANDONED_FACTORY);
          FlatGenerationSettings.STRUCTURES.put(STStructures.ABANDONED_FACTORY.get(), CONFIGURED_ABANDONED_FACTORY);
    }

}
