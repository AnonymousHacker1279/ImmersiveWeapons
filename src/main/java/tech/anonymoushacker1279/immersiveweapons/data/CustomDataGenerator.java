package tech.anonymoushacker1279.immersiveweapons.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import tech.anonymoushacker1279.immersiveweapons.data.advancements.AdvancementProvider;
import tech.anonymoushacker1279.immersiveweapons.data.loot.LootTableGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.models.ModelProvider;
import tech.anonymoushacker1279.immersiveweapons.data.recipes.RecipeGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.tags.*;

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
			generator.addProvider(new LootTableGenerator(generator));
			generator.addProvider(new RecipeGenerator(generator));
			BlockTagsGenerator blockTagsGenerator = new BlockTagsGenerator(generator, event.getExistingFileHelper());
			generator.addProvider(blockTagsGenerator);
			generator.addProvider(new ItemTagsGenerator(generator, blockTagsGenerator, event.getExistingFileHelper()));
			generator.addProvider(new BiomeTagsGenerator(generator, event.getExistingFileHelper()));
		}
	}
}