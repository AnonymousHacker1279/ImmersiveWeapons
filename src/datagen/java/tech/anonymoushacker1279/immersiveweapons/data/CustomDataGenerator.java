package tech.anonymoushacker1279.immersiveweapons.data;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider.SubProviderEntry;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.data.event.GatherDataRegistryEntriesEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.accessories.AccessoryDataGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.advancements.AdvancementGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.biomes.IWBiomes;
import tech.anonymoushacker1279.immersiveweapons.data.damage_types.DamageTypesGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.data_maps.DataMapsGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.dimensions.DimensionGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.dimensions.DimensionTypeGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.enchantments.EnchantmentsGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.features.IWConfiguredCarvers;
import tech.anonymoushacker1279.immersiveweapons.data.features.IWConfiguredFeatures;
import tech.anonymoushacker1279.immersiveweapons.data.features.IWPlacedFeatures;
import tech.anonymoushacker1279.immersiveweapons.data.lang.LanguageGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.loot.BlockLootTables;
import tech.anonymoushacker1279.immersiveweapons.data.loot.ChestLootTables;
import tech.anonymoushacker1279.immersiveweapons.data.loot.EntityLootTables;
import tech.anonymoushacker1279.immersiveweapons.data.loot.GlobalLootModifierGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.models.IWEquipmentAssetsGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.models.IWModelProvider;
import tech.anonymoushacker1279.immersiveweapons.data.modifiers.IWBiomeModifiers;
import tech.anonymoushacker1279.immersiveweapons.data.noise.NoiseGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.particles.ParticleDescriptionGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.recipes.families.FamilyGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.sounds.SoundGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.structures.*;
import tech.anonymoushacker1279.immersiveweapons.data.tags.*;
import tech.anonymoushacker1279.immersiveweapons.data.textures.TextureMetadataGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.trades.TradeGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.trades.TradeSetGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.trim.TrimMaterialsGenerator;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber
public class CustomDataGenerator {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();

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
		generator.addProvider(true, new PoiTypeTagsGenerator(output, lookupProvider));
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

	@SubscribeEvent
	public static void gatherRegistries(GatherDataRegistryEntriesEvent event) {
		event.add(Registries.FEATURE, IWConfiguredFeatures::bootstrap)
				.add(Registries.PLACED_FEATURE, IWPlacedFeatures::bootstrap)
				.add(Registries.BIOME, IWBiomes::bootstrap)
				.add(Registries.DIMENSION_TYPE, DimensionTypeGenerator::bootstrap)
				.add(Registries.LEVEL_STEM, DimensionGenerator::bootstrap)
				.add(Registries.NOISE_SETTINGS, NoiseGenerator::bootstrap)
				.add(Registries.CARVER, IWConfiguredCarvers::bootstrap)
				.add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, IWBiomeModifiers::bootstrap)
				.add(Registries.DAMAGE_TYPE, DamageTypesGenerator::bootstrap)
				.add(Registries.PROCESSOR_LIST, StructureProcessorListGenerator::bootstrap)
				.add(Registries.TEMPLATE_POOL, StructureTemplatePoolGenerator::bootstrap)
				.add(Registries.STRUCTURE, StructureGenerator::bootstrap)
				.add(Registries.STRUCTURE_SET, StructureSetGenerator::bootstrap)
				.add(Registries.ENCHANTMENT, EnchantmentsGenerator::bootstrap)
				.add(Registries.JUKEBOX_SONG, IWJukeboxSongs::bootstrap)
				.add(Registries.TRADE_SET, TradeSetGenerator::bootstrap)
				.add(Registries.VILLAGER_TRADE, TradeGenerator::bootstrap)
				.add(Registries.TRIM_MATERIAL, TrimMaterialsGenerator::bootstrap);

		SubProviderEntry blockLootTables = new SubProviderEntry(BlockLootTables::new, LootContextParamSets.BLOCK);
		SubProviderEntry chestLootTables = new SubProviderEntry(ChestLootTables::new, LootContextParamSets.CHEST);
		SubProviderEntry entityLootTables = new SubProviderEntry(EntityLootTables::new, LootContextParamSets.ENTITY);

		event.gatherFor(ImmersiveWeapons.MOD_ID, "minecraft")
				.advancement(AdvancementGenerator::new)
				.lootTable(blockLootTables, chestLootTables, entityLootTables)
				.add(FamilyGenerator.create());
	}
}