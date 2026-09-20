package tech.anonymoushacker1279.immersiveweapons.data;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.accessories.AccessoryDataGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.advancements.AdvancementGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.data_maps.DataMapsGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.lang.LanguageGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.loot.GlobalLootModifierGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.loot.LootTableGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.models.IWEquipmentAssetsGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.models.IWModelProvider;
import tech.anonymoushacker1279.immersiveweapons.data.particles.ParticleDescriptionGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.recipes.families.FamilyGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.sounds.SoundGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.structures.StructureUpdater;
import tech.anonymoushacker1279.immersiveweapons.data.tags.*;
import tech.anonymoushacker1279.immersiveweapons.data.textures.TextureMetadataGenerator;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber
public class CustomDataGenerator {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();

		// World-layer datapack registries (biomes, dimensions, features, etc.)
		event.createWorldRegistryObjects(DatapackRegistriesGenerator.BUILDER);

		// Reloadable datapack registries (advancements, loot tables, recipes)
		// The vanilla namespace is included as some recipes (e.g. gunpowder) are generated under it
		event.createReloadableRegistryObjects(new RegistrySetBuilder()
						.add(Registries.ADVANCEMENT, new AdvancementProvider(List.of(AdvancementGenerator::new)))
						.add(Registries.LOOT_TABLE, new LootTableGenerator())
						.add(FamilyGenerator.create()),
				Set.of(ImmersiveWeapons.MOD_ID, "minecraft"));

		CompletableFuture<Provider> lookupProvider = event.getReloadableLookupProvider();

		// Client data
		generator.addProvider(true, new IWModelProvider(output));
		generator.addProvider(true, new IWEquipmentAssetsGenerator(output));
		generator.addProvider(true, new SoundGenerator(output));
		generator.addProvider(true, new LanguageGenerator(output, lookupProvider));
		generator.addProvider(true, new ParticleDescriptionGenerator(output));
		generator.addProvider(true, new TextureMetadataGenerator(output));

		// Server data
		BlockTagsGenerator blockTagsGenerator = new BlockTagsGenerator(output, lookupProvider);
		generator.addProvider(true, blockTagsGenerator);
		generator.addProvider(true, new ItemTagsGenerator(output, lookupProvider, blockTagsGenerator.contentsGetter()));
		generator.addProvider(true, new TradeTagsGenerator(output, lookupProvider));
		generator.addProvider(true, new EntityTypeTagsGenerator(output, lookupProvider));
		generator.addProvider(true, new GameEventTagsGenerator(output, lookupProvider));
		generator.addProvider(true, new EnchantmentTagsGenerator(output, lookupProvider));
		generator.addProvider(true, new DataMapsGenerator(output, lookupProvider));
		generator.addProvider(true, new GlobalLootModifierGenerator(output, lookupProvider));
		generator.addProvider(true, new StructureUpdater(output, (MultiPackResourceManager) event.getResourceManager(PackType.SERVER_DATA)));
		generator.addProvider(true, new AccessoryDataGenerator(output));
		generator.addProvider(true, PackMetadataGenerator.forFeaturePack(output, Component.translatable("immersiveweapons.datapack.description")));
		generator.addProvider(true, new BiomeTagsGenerator(output, lookupProvider));
		generator.addProvider(true, new DamageTypeTagsGenerator(output, lookupProvider));
	}
}
