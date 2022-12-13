package tech.anonymoushacker1279.immersiveweapons.data;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.RegistryOps;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.advancements.AdvancementsGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.features.*;
import tech.anonymoushacker1279.immersiveweapons.data.loot.LootTableGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.models.BlockStateGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.models.ItemModelGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.modifiers.OreBiomeModifiers;
import tech.anonymoushacker1279.immersiveweapons.data.modifiers.SpawnBiomeModifiers;
import tech.anonymoushacker1279.immersiveweapons.data.recipes.RecipeGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.recipes.families.FamilyGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.sounds.SoundGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.tags.*;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.feature.BiomeFeatures;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.feature.WorldCarvers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
		CompletableFuture<Provider> lookup = event.getLookupProvider();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

		RegistryOps<JsonElement> registryOps;
		try {
			registryOps = RegistryOps.create(JsonOps.INSTANCE, (Provider) lookup.get().asGetterLookup());
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}

		// Client data
		generator.addProvider(event.includeClient(), new BlockStateGenerator(output, event.getExistingFileHelper()));
		generator.addProvider(event.includeClient(), new ItemModelGenerator(output, event.getExistingFileHelper()));
		generator.addProvider(event.includeClient(), new SoundGenerator(output, ImmersiveWeapons.MOD_ID, event.getExistingFileHelper()));

		// Server data
		generator.addProvider(event.includeServer(), new AdvancementsGenerator(output, lookup, existingFileHelper));
		generator.addProvider(event.includeServer(), new LootTableGenerator(output));
		generator.addProvider(event.includeServer(), new RecipeGenerator(output));
		generator.addProvider(event.includeServer(), new FamilyGenerator(output));
		BlockTagsGenerator blockTagsGenerator = new BlockTagsGenerator(output, lookup, existingFileHelper);
		generator.addProvider(event.includeServer(), blockTagsGenerator);
		generator.addProvider(event.includeServer(), new ItemTagsGenerator(output, lookup, blockTagsGenerator, existingFileHelper));
		generator.addProvider(event.includeServer(), new BiomeTagsGenerator(output, lookup, existingFileHelper));

		WorldCarvers.init(registryOps);
		BiomeFeatures.init();
		CarversGenerator.init();
		BiomesGenerator.init(lookup);

		// Ore biome modifiers
		generator.addProvider(event.includeServer(), OreBiomeModifiers.PlacedFeatures.getCodecProvider(output, event.getExistingFileHelper(),
				registryOps, Registries.PLACED_FEATURE));
		generator.addProvider(event.includeServer(), OreBiomeModifiers.getCodecProvider(output, event.getExistingFileHelper(),
				registryOps, Keys.BIOME_MODIFIERS));

		// Spawn biome modifiers
		generator.addProvider(event.includeServer(), SpawnBiomeModifiers.getCodecProvider(output, event.getExistingFileHelper(),
				registryOps, Keys.BIOME_MODIFIERS));

		// Placed feature generator
		generator.addProvider(event.includeServer(), PlacedFeaturesGenerator.getCodecProvider(output, event.getExistingFileHelper(),
				registryOps, Registries.PLACED_FEATURE));

		// Carver generator
		generator.addProvider(event.includeServer(), CarversGenerator.getCodecProvider(output, event.getExistingFileHelper(),
				registryOps, Registries.CONFIGURED_CARVER));

		// Biome generator
		generator.addProvider(event.includeServer(), BiomesGenerator.getCodecProvider(output, event.getExistingFileHelper(),
				registryOps, Registries.BIOME));
	}
}