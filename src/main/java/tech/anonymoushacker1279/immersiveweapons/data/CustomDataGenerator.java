package tech.anonymoushacker1279.immersiveweapons.data;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import tech.anonymoushacker1279.immersiveweapons.data.advancements.AdvancementProvider;
import tech.anonymoushacker1279.immersiveweapons.data.loot.LootTableGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.models.BlockStateGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.models.ItemModelGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.modifiers.OreBiomeModifiers;
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
		RegistryOps<JsonElement> registryOps = RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.builtinCopy());


		generator.addProvider(event.includeClient(), new BlockStateGenerator(generator, event.getExistingFileHelper()));
		generator.addProvider(event.includeClient(), new ItemModelGenerator(generator, event.getExistingFileHelper()));

		generator.addProvider(event.includeServer(), new AdvancementProvider(generator));
		generator.addProvider(event.includeServer(), new LootTableGenerator(generator));
		generator.addProvider(event.includeServer(), new RecipeGenerator(generator));
		BlockTagsGenerator blockTagsGenerator = new BlockTagsGenerator(generator, event.getExistingFileHelper());
		generator.addProvider(event.includeServer(), blockTagsGenerator);
		generator.addProvider(event.includeServer(), new ItemTagsGenerator(generator, blockTagsGenerator, event.getExistingFileHelper()));
		generator.addProvider(event.includeServer(), new BiomeTagsGenerator(generator, event.getExistingFileHelper()));
		generator.addProvider(event.includeServer(), OreBiomeModifiers.PlacedFeatures.getCodecProvider(generator, event.getExistingFileHelper(),
				registryOps, Registry.PLACED_FEATURE_REGISTRY));
		generator.addProvider(event.includeServer(), OreBiomeModifiers.getCodecProvider(generator, event.getExistingFileHelper(),
				registryOps, Keys.BIOME_MODIFIERS));
	}
}