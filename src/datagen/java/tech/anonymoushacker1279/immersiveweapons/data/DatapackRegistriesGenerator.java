package tech.anonymoushacker1279.immersiveweapons.data;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.biomes.IWBiomes;
import tech.anonymoushacker1279.immersiveweapons.data.damage_types.DamageTypesGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.dimensions.DimensionGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.dimensions.DimensionTypeGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.enchantments.EnchantmentsGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.features.*;
import tech.anonymoushacker1279.immersiveweapons.data.modifiers.IWBiomeModifiers;
import tech.anonymoushacker1279.immersiveweapons.data.structures.*;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

public class DatapackRegistriesGenerator extends DatapackBuiltinEntriesProvider {

	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.CONFIGURED_FEATURE, IWConfiguredFeatures::bootstrap)
			.add(Registries.PLACED_FEATURE, IWPlacedFeatures::bootstrap)
			.add(Registries.BIOME, IWBiomes::bootstrap)
			.add(Registries.DIMENSION_TYPE, DimensionTypeGenerator::bootstrap)
			.add(Registries.LEVEL_STEM, DimensionGenerator::bootstrap)
			.add(Registries.CONFIGURED_CARVER, IWConfiguredCarvers::bootstrap)
			.add(Keys.BIOME_MODIFIERS, IWBiomeModifiers::bootstrap)
			.add(Registries.DAMAGE_TYPE, DamageTypesGenerator::bootstrap)
			.add(Registries.PROCESSOR_LIST, StructureProcessorListGenerator::bootstrap)
			.add(Registries.TEMPLATE_POOL, StructureTemplatePoolGenerator::bootstrap)
			.add(Registries.STRUCTURE, StructureGenerator::bootstrap)
			.add(Registries.STRUCTURE_SET, StructureSetGenerator::bootstrap)
			.add(Registries.ENCHANTMENT, EnchantmentsGenerator::bootstrap)
			.add(Registries.JUKEBOX_SONG, IWJukeboxSongs::bootstrap);

	public DatapackRegistriesGenerator(PackOutput output, CompletableFuture<Provider> registries) {
		super(output, registries, BUILDER, Collections.singleton(ImmersiveWeapons.MOD_ID));
	}

	@Override
	public String getName() {
		return "Datapack Registries";
	}
}