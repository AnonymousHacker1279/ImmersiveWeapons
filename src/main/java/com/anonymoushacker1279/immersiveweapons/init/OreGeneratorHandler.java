package com.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class OreGeneratorHandler {
	
	public static ConfiguredFeature<?, ?> ORE_COPPER_CONFIG;
	public static ConfiguredFeature<?, ?> ORE_MOLTEN_CONFIG;
	
	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		ORE_COPPER_CONFIG = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, "ore_copper", 
				Feature.ORE.withConfiguration(
						new OreFeatureConfig(
								OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD,
								DeferredRegistryHandler.COPPER_ORE.get().getDefaultState(), 8)
						).range(76).square().func_242731_b(25)
				);
		
		ORE_MOLTEN_CONFIG = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, "ore_molten", 
				Feature.ORE.withConfiguration(
						new OreFeatureConfig(
								OreFeatureConfig.FillerBlockType.BASE_STONE_NETHER,
								DeferredRegistryHandler.MOLTEN_ORE.get().getDefaultState(), 3)
						).range(28).square().func_242731_b(18)
				);
	}
}
