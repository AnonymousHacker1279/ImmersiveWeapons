package tech.anonymoushacker1279.iwcompatbridge.data;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.iwcompatbridge.data.lang.IWCBLanguageGenerator;
import tech.anonymoushacker1279.iwcompatbridge.data.model.IWCBModelProvider;
import tech.anonymoushacker1279.iwcompatbridge.data.tags.CuriosTagsGenerator;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber
public class CustomDataGenerator {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		/*
		This is to ensure that this generator does not run in its parent project. This is probably not the best
		approach, but I have no idea how to do it better.
		*/
		if (event.getGenerator().getPackOutput().getOutputFolder().toString().toLowerCase().contains(ImmersiveWeapons.MOD_ID)) {
			return;
		}

		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();

		CompletableFuture<Provider> lookupProvider = event.getLookupProvider();

		// Client data
		generator.addProvider(true, new IWCBModelProvider(output));
		generator.addProvider(true, new IWCBLanguageGenerator(output));

		// Server data
		// generator.addProvider(event.includeServer(), new MekanismRecipeGenerator(output, event.getLookupProvider()));
		generator.addProvider(true, new CuriosTagsGenerator(output, lookupProvider));
	}
}