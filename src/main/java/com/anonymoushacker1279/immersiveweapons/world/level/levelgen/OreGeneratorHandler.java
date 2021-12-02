package com.anonymoushacker1279.immersiveweapons.world.level.levelgen;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
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
	public static ConfiguredFeature<?, ?> ORE_SULFUR_CONFIG;
	public static ConfiguredFeature<?, ?> ORE_NETHER_SULFUR_CONFIG;

	/**
	 * Initialize ore generation setup.
	 *
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
				).range(new RangeDecoratorConfiguration(BiasedToBottomHeight.of(VerticalAnchor.bottom(), VerticalAnchor.belowTop(24), 4))).squared().count(8)
		);

		ORE_SULFUR_CONFIG = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, "ore_sulfur",
				Feature.ORE.configured(
						new OreConfiguration(
								Predicates.NATURAL_STONE,
								DeferredRegistryHandler.SULFUR_ORE.get().defaultBlockState(), 8)
				).range(new RangeDecoratorConfiguration(BiasedToBottomHeight.of(VerticalAnchor.bottom(), VerticalAnchor.top(), 4))).squared().count(14)
		);

		ORE_NETHER_SULFUR_CONFIG = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, "ore_nether_sulfur",
				Feature.ORE.configured(
						new OreConfiguration(
								Predicates.NETHERRACK,
								DeferredRegistryHandler.NETHER_SULFUR_ORE.get().defaultBlockState(), 12)
				).range(new RangeDecoratorConfiguration(BiasedToBottomHeight.of(VerticalAnchor.bottom(), VerticalAnchor.top(), 6))).squared().count(16)
		);
	}
}