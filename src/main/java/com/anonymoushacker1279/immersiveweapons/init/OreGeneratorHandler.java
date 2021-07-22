package com.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig.FillerBlockType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class OreGeneratorHandler {

	public static ConfiguredFeature<?, ?> ORE_COPPER_CONFIG;
	public static ConfiguredFeature<?, ?> ORE_MOLTEN_CONFIG;
	public static ConfiguredFeature<?, ?> ORE_COBALT_CONFIG;

	/**
	 * Initialize ore generation setup.
	 * @param event the <code>FMLCommonSetupEvent</code> instance
	 */
	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		ORE_COPPER_CONFIG = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, "ore_copper",
				Feature.ORE.configured(
						new OreFeatureConfig(
								OreFeatureConfig.FillerBlockType.NATURAL_STONE,
								DeferredRegistryHandler.COPPER_ORE.get().defaultBlockState(), 8)
				).range(76).squared().count(15)
		);

		ORE_MOLTEN_CONFIG = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, "ore_molten",
				Feature.ORE.configured(
						new OreFeatureConfig(
								OreFeatureConfig.FillerBlockType.NETHER_ORE_REPLACEABLES,
								DeferredRegistryHandler.MOLTEN_ORE.get().defaultBlockState(), 3)
				).range(28).squared().count(6)
		);

		ORE_COBALT_CONFIG = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, "ore_cobalt",
				Feature.ORE.configured(
						new OreFeatureConfig(
								FillerBlockType.NATURAL_STONE,
								DeferredRegistryHandler.COBALT_ORE.get().defaultBlockState(), 5)
				).range(32).squared().count(11)
		);
	}
}