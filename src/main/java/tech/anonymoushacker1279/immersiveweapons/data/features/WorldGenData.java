package tech.anonymoushacker1279.immersiveweapons.data.features;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.biomes.IWBiomes;
import tech.anonymoushacker1279.immersiveweapons.data.modifiers.IWBiomeModifiers;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

public class WorldGenData extends DatapackBuiltinEntriesProvider {

	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.CONFIGURED_FEATURE, IWConfiguredFeatures::bootstrap)
			.add(Registries.PLACED_FEATURE, IWPlacedFeatures::bootstrap)
			.add(Registries.BIOME, IWBiomes::bootstrap)
			.add(Registries.CONFIGURED_CARVER, IWConfiguredCarvers::bootstrap)
			.add(Keys.BIOME_MODIFIERS, IWBiomeModifiers::bootstrap);

	public WorldGenData(PackOutput output, CompletableFuture<Provider> registries) {
		super(output, registries, BUILDER, Collections.singleton(ImmersiveWeapons.MOD_ID));
	}
}