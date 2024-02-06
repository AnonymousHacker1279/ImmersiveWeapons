package tech.anonymoushacker1279.immersiveweapons.data;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.Mod.EventBusSubscriber.Bus;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.advancements.AdvancementsGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.data_maps.DataMapsGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.lang.LanguageGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.loot.GlobalLootModifierGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.loot.LootTableGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.models.BlockStateGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.models.ItemModelGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.particles.ParticleDescriptionGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.recipes.families.FamilyGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.sounds.SoundGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.structures.StructureUpdater;
import tech.anonymoushacker1279.immersiveweapons.data.tags.*;

import java.util.concurrent.CompletableFuture;

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
		PackOutput output = generator.getPackOutput();

		CompletableFuture<Provider> lookupProvider = event.getLookupProvider();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

		// Client data
		generator.addProvider(event.includeClient(), new BlockStateGenerator(output, existingFileHelper));
		generator.addProvider(event.includeClient(), new ItemModelGenerator(output, existingFileHelper));
		generator.addProvider(event.includeClient(), new SoundGenerator(output, ImmersiveWeapons.MOD_ID, existingFileHelper));
		generator.addProvider(event.includeClient(), new LanguageGenerator(output));
		generator.addProvider(event.includeClient(), new ParticleDescriptionGenerator(output, existingFileHelper));

		// Server data
		generator.addProvider(event.includeServer(), new AdvancementsGenerator(output, lookupProvider, existingFileHelper));
		generator.addProvider(event.includeServer(), new LootTableGenerator(output));
		generator.addProvider(event.includeServer(), new FamilyGenerator(output));
		BlockTagsGenerator blockTagsGenerator = new BlockTagsGenerator(output, lookupProvider, existingFileHelper);
		generator.addProvider(event.includeServer(), blockTagsGenerator);
		generator.addProvider(event.includeServer(), new ItemTagsGenerator(output, lookupProvider, blockTagsGenerator, existingFileHelper));
		generator.addProvider(event.includeServer(), new EntityTypeTagsGenerator(output, lookupProvider, existingFileHelper));
		generator.addProvider(event.includeServer(), new GameEventTagsGenerator(output, lookupProvider, existingFileHelper));
		generator.addProvider(event.includeServer(), new DataMapsGenerator(output, lookupProvider));
		generator.addProvider(event.includeServer(), new GlobalLootModifierGenerator(output));
		generator.addProvider(event.includeServer(), new StructureUpdater(existingFileHelper, output));

		DatapackRegistriesGenerator datapackRegistriesGenerator = new DatapackRegistriesGenerator(output, lookupProvider);
		generator.addProvider(event.includeServer(), datapackRegistriesGenerator);
		generator.addProvider(event.includeServer(), new BiomeTagsGenerator(output, datapackRegistriesGenerator.getRegistryProvider(), existingFileHelper));
		generator.addProvider(event.includeServer(), new DamageTypeTagsGenerator(output, datapackRegistriesGenerator.getRegistryProvider(), existingFileHelper));
	}
}