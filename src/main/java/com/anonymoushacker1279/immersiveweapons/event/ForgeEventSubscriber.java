package com.anonymoushacker1279.immersiveweapons.event;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.config.CommonConfig;
import com.anonymoushacker1279.immersiveweapons.world.level.levelgen.OreGeneratorHandler;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import java.util.Objects;

@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID, bus = Bus.FORGE)
public class ForgeEventSubscriber {

	/**
	 * Event handler for the BiomeLoadingEvent.
	 * Configures custom ores, carvers, spawns, etc.
	 *
	 * @param event the <code>BiomeLoadingEvent</code> instance
	 */
	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onBiomeLoading(BiomeLoadingEvent event) {
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
	}
}