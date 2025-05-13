package tech.anonymoushacker1279.immersiveweapons.data;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.data.event.GatherDataEvent;
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
import tech.anonymoushacker1279.immersiveweapons.data.trades.TradeDataGenerator;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@EventBusSubscriber(bus = Bus.MOD, value = Dist.CLIENT)
public class CustomDataGenerator {

	public static final List<Block> ALL_BLOCKS = new ArrayList<>(250);
	public static final List<EntityType<?>> ALL_ENTITIES = new ArrayList<>(250);

	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		prepareLists();

		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();

		DatapackRegistriesGenerator datapackGenerator = generator.addProvider(true,
				new DatapackRegistriesGenerator(output, event.getLookupProvider()));

		CompletableFuture<Provider> lookupProvider = datapackGenerator.getRegistryProvider();

		// Client data
		generator.addProvider(true, new IWModelProvider(output));
		generator.addProvider(true, new IWEquipmentAssetsGenerator(output));
		generator.addProvider(true, new SoundGenerator(output));
		generator.addProvider(true, new LanguageGenerator(output, lookupProvider));
		generator.addProvider(true, new ParticleDescriptionGenerator(output));
		generator.addProvider(true, new TextureMetadataGenerator(output));

		// Server data
		generator.addProvider(true, new AdvancementProvider(output, lookupProvider, List.of(new AdvancementGenerator())));
		generator.addProvider(true, new LootTableGenerator(output, lookupProvider));
		BlockTagsGenerator blockTagsGenerator = new BlockTagsGenerator(output, lookupProvider);
		generator.addProvider(true, blockTagsGenerator);
		generator.addProvider(true, new ItemTagsGenerator(output, lookupProvider, blockTagsGenerator));
		generator.addProvider(true, new FamilyGenerator.Runner(output, lookupProvider));
		generator.addProvider(true, new EntityTypeTagsGenerator(output, lookupProvider));
		generator.addProvider(true, new GameEventTagsGenerator(output, lookupProvider));
		generator.addProvider(true, new EnchantmentTagsGenerator(output, lookupProvider));
		generator.addProvider(true, new DataMapsGenerator(output, lookupProvider));
		generator.addProvider(true, new GlobalLootModifierGenerator(output, lookupProvider));
		generator.addProvider(true, new StructureUpdater(output, (MultiPackResourceManager) event.getResourceManager(PackType.SERVER_DATA)));
		generator.addProvider(true, new TradeDataGenerator(output));
		generator.addProvider(true, PackMetadataGenerator.forFeaturePack(output, Component.translatable("immersiveweapons.datapack.description")));
		generator.addProvider(true, new BiomeTagsGenerator(output, lookupProvider));
		generator.addProvider(true, new DamageTypeTagsGenerator(output, lookupProvider));
	}

	private static void prepareLists() {
		BlockRegistry.BLOCKS.getEntries().stream().map(Supplier::get).forEach(ALL_BLOCKS::add);
		EntityRegistry.ENTITY_TYPES.getEntries().stream().map(Supplier::get).forEach(ALL_ENTITIES::add);
	}
}