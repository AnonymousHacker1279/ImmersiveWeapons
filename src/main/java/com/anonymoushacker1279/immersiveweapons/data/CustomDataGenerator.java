package com.anonymoushacker1279.immersiveweapons.data;

import com.anonymoushacker1279.immersiveweapons.data.advancements.AdvancementProvider;
import com.anonymoushacker1279.immersiveweapons.data.loot.LootTableGenerator;
import com.anonymoushacker1279.immersiveweapons.data.models.ModelProvider;
import com.anonymoushacker1279.immersiveweapons.data.recipes.RecipeGenerator;
import com.anonymoushacker1279.immersiveweapons.data.tags.*;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Bus.MOD)
public class CustomDataGenerator {

	/**
	 * Event handler for the GatherDataEvent.
	 *
	 * @param event the <code>GatherDataEvent</code> instance
	 */
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {

		DataGenerator generator = event.getGenerator();

		generator.addProvider(event.includeClient(), new ModelProvider(generator));

		generator.addProvider(event.includeServer(), new AdvancementProvider(generator));
		generator.addProvider(event.includeServer(), new LootTableGenerator(generator));
		generator.addProvider(event.includeServer(), new RecipeGenerator(generator));
		BlockTagsGenerator blockTagsGenerator = new BlockTagsGenerator(generator, event.getExistingFileHelper());
		generator.addProvider(event.includeServer(), blockTagsGenerator);
		generator.addProvider(event.includeServer(), new ItemTagsGenerator(generator, blockTagsGenerator, event.getExistingFileHelper()));
		generator.addProvider(event.includeServer(), new BiomeTagsGenerator(generator, event.getExistingFileHelper()));

	}
}