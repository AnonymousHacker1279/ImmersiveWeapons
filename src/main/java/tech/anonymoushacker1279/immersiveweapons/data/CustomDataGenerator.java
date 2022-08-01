package tech.anonymoushacker1279.immersiveweapons.data;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistryAccess.Writable;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import tech.anonymoushacker1279.immersiveweapons.data.advancements.AdvancementProvider;
import tech.anonymoushacker1279.immersiveweapons.data.features.BiomesGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.features.CarversGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.loot.LootTableGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.models.BlockStateGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.models.ItemModelGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.modifiers.*;
import tech.anonymoushacker1279.immersiveweapons.data.recipes.RecipeGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.recipes.families.FamilyGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.tags.*;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.feature.BiomeFeatures;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.feature.WorldCarvers;

@Mod.EventBusSubscriber(bus = Bus.MOD)
public class CustomDataGenerator {

	public static Writable REGISTRY_BUILTIN_COPY;

	/**
	 * Event handler for the GatherDataEvent.
	 *
	 * @param event the <code>GatherDataEvent</code> instance
	 */
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		REGISTRY_BUILTIN_COPY = RegistryAccess.builtinCopy();

		DataGenerator generator = event.getGenerator();
		RegistryOps<JsonElement> registryOps = RegistryOps.create(JsonOps.INSTANCE, REGISTRY_BUILTIN_COPY);

		// Client data
		generator.addProvider(event.includeClient(), new BlockStateGenerator(generator, event.getExistingFileHelper()));
		generator.addProvider(event.includeClient(), new ItemModelGenerator(generator, event.getExistingFileHelper()));

		// Server data
		generator.addProvider(event.includeServer(), new AdvancementProvider(generator));
		generator.addProvider(event.includeServer(), new LootTableGenerator(generator));
		generator.addProvider(event.includeServer(), new RecipeGenerator(generator));
		generator.addProvider(event.includeServer(), new FamilyGenerator(generator));
		BlockTagsGenerator blockTagsGenerator = new BlockTagsGenerator(generator, event.getExistingFileHelper());
		generator.addProvider(event.includeServer(), blockTagsGenerator);
		generator.addProvider(event.includeServer(), new ItemTagsGenerator(generator, blockTagsGenerator, event.getExistingFileHelper()));
		generator.addProvider(event.includeServer(), new BiomeTagsGenerator(generator, event.getExistingFileHelper()));

		WorldCarvers.init();
		BiomeFeatures.init();
		CarversGenerator.init();
		BiomesGenerator.init();

		// Ore biome modifiers
		generator.addProvider(event.includeServer(), OreBiomeModifiers.PlacedFeatures.getCodecProvider(generator, event.getExistingFileHelper(),
				registryOps, Registry.PLACED_FEATURE_REGISTRY));
		generator.addProvider(event.includeServer(), OreBiomeModifiers.getCodecProvider(generator, event.getExistingFileHelper(),
				registryOps, Keys.BIOME_MODIFIERS));

		// Spawn biome modifiers
		generator.addProvider(event.includeServer(), SpawnBiomeModifiers.getCodecProvider(generator, event.getExistingFileHelper(),
				registryOps, Keys.BIOME_MODIFIERS));

		// Feature biome modifiers
		generator.addProvider(event.includeServer(), FeatureBiomeModifiers.PlacedFeatures.getCodecProvider(generator, event.getExistingFileHelper(),
				registryOps, Registry.PLACED_FEATURE_REGISTRY));
		generator.addProvider(event.includeServer(), FeatureBiomeModifiers.getCodecProvider(generator, event.getExistingFileHelper(),
				registryOps, Keys.BIOME_MODIFIERS));

		// Carver generator
		generator.addProvider(event.includeServer(), CarversGenerator.getCodecProvider(generator, event.getExistingFileHelper(),
				registryOps, Registry.CONFIGURED_CARVER_REGISTRY));

		// Biome generator
		generator.addProvider(event.includeServer(), BiomesGenerator.getCodecProvider(generator, event.getExistingFileHelper(),
				registryOps, Registry.BIOME_REGISTRY));
	}
}