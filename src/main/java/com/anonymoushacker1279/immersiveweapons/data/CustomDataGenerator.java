package com.anonymoushacker1279.immersiveweapons.data;

import com.anonymoushacker1279.immersiveweapons.data.advancements.AdvancementProvider;
import com.anonymoushacker1279.immersiveweapons.data.models.ModelProvider;
import com.anonymoushacker1279.immersiveweapons.data.tags.BlockTagsGenerator;
import com.anonymoushacker1279.immersiveweapons.data.tags.ItemTagsGenerator;
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

		if (event.includeClient()) {
			generator.addProvider(new ModelProvider(generator));
		}
		if (event.includeServer()) {
			generator.addProvider(new AdvancementProvider(generator));
			BlockTagsGenerator blockTagsGenerator = new BlockTagsGenerator(generator, event.getExistingFileHelper());
			generator.addProvider(blockTagsGenerator);
			generator.addProvider(new ItemTagsGenerator(generator, blockTagsGenerator, event.getExistingFileHelper()));
		}
	}
}