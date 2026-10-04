package tech.anonymoushacker1279.iwcompatbridge.data;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import tech.anonymoushacker1279.iwcompatbridge.data.lang.IWCBLanguageGenerator;
import tech.anonymoushacker1279.iwcompatbridge.data.tags.CuriosTagsGenerator;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber
public class CustomDataGenerator {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();

		CompletableFuture<Provider> lookupProvider = event.getReloadableLookupProvider();

		// Client data
		generator.addProvider(true, new IWCBLanguageGenerator(output));

		// Server data
		generator.addProvider(true, new CuriosTagsGenerator(output, lookupProvider));
	}
}