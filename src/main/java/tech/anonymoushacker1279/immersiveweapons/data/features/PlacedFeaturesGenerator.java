package tech.anonymoushacker1279.immersiveweapons.data.features;

import com.google.gson.JsonElement;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.*;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.feature.BiomeFeatures;

import java.util.HashMap;

public class PlacedFeaturesGenerator {

	public static JsonCodecProvider<PlacedFeature> getCodecProvider(DataGenerator generator,
	                                                                ExistingFileHelper existingFileHelper,
	                                                                RegistryOps<JsonElement> registryOps,
	                                                                ResourceKey<Registry<PlacedFeature>> placedFeatureKey) {

		return JsonCodecProvider.forDatapackRegistry(
				generator, existingFileHelper, ImmersiveWeapons.MOD_ID, registryOps, placedFeatureKey, getPlacedFeatures());
	}

	private static HashMap<ResourceLocation, PlacedFeature> getPlacedFeatures() {
		HashMap<ResourceLocation, PlacedFeature> placedFeatures = new HashMap<>(10);
		placedFeatures.put(BiomeFeatures.PATCH_WOODEN_SPIKES, BiomeFeatures.PATCH_WOODEN_SPIKES_FEATURE);
		placedFeatures.put(BiomeFeatures.BURNED_OAK_TREE, BiomeFeatures.BURNED_OAK_TREE_FEATURE);
		placedFeatures.put(BiomeFeatures.PATCH_MOONGLOW, BiomeFeatures.PATCH_MOONGLOW_FEATURE);
		placedFeatures.put(BiomeFeatures.STARDUST_TREE, BiomeFeatures.STARDUST_TREE_FEATURE);
		placedFeatures.put(BiomeFeatures.PATCH_DEATHWEED, BiomeFeatures.PATCH_DEATHWEED_FEATURE);

		return placedFeatures;
	}
}