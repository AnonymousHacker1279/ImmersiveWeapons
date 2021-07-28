package com.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration.Predicates;
import net.minecraft.world.level.levelgen.feature.configurations.RangeDecoratorConfiguration;
import net.minecraft.world.level.levelgen.heightproviders.BiasedToBottomHeight;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class OreGeneratorHandler {

	public static ConfiguredFeature<?, ?> ORE_MOLTEN_CONFIG;
	public static ConfiguredFeature<?, ?> ORE_COBALT_CONFIG;

	/**
	 * Initialize ore generation setup.
	 * @param event the <code>FMLCommonSetupEvent</code> instance
	 */
	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		ORE_MOLTEN_CONFIG = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, "ore_molten",
				Feature.ORE.configured(
						new OreConfiguration(
								OreConfiguration.Predicates.NETHER_ORE_REPLACEABLES,
								DeferredRegistryHandler.MOLTEN_ORE.get().defaultBlockState(), 3)
				).range(new RangeDecoratorConfiguration(BiasedToBottomHeight.of(VerticalAnchor.bottom(), VerticalAnchor.belowTop(48), 8))).squared().count(6)
		);

		ORE_COBALT_CONFIG = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, "ore_cobalt",
				Feature.ORE.configured(
						new OreConfiguration(
								Predicates.NATURAL_STONE,
								DeferredRegistryHandler.COBALT_ORE.get().defaultBlockState(), 4)
				).range(new RangeDecoratorConfiguration(BiasedToBottomHeight.of(VerticalAnchor.bottom(), VerticalAnchor.belowTop(24), 4))).squared().count(4)
		);
	}
}