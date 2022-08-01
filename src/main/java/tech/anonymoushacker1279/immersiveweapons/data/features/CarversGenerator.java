package tech.anonymoushacker1279.immersiveweapons.data.features;

import com.google.gson.JsonElement;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.*;
import net.minecraft.world.level.levelgen.carver.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.feature.WorldCarvers;

import java.util.HashMap;

public class CarversGenerator {

	public static ConfiguredWorldCarver<CanyonCarverConfiguration> TRENCH_CARVER;

	public static void init() {
		TRENCH_CARVER = new ConfiguredWorldCarver<>(WorldCarver.CANYON, WorldCarvers.TRENCH_CARVER_CONFIGURATION);
	}

	protected static final ResourceLocation TRENCH = new ResourceLocation(ImmersiveWeapons.MOD_ID, "trench");
	public static final ResourceKey<ConfiguredWorldCarver<?>> TRENCH_KEY = ResourceKey.create(Registry.CONFIGURED_CARVER_REGISTRY, TRENCH);

	public static JsonCodecProvider<ConfiguredWorldCarver<?>> getCodecProvider(DataGenerator generator,
	                                                                           ExistingFileHelper existingFileHelper,
	                                                                           RegistryOps<JsonElement> registryOps,
	                                                                           ResourceKey<Registry<ConfiguredWorldCarver<?>>> placedFeatureKey) {

		return JsonCodecProvider.forDatapackRegistry(
				generator, existingFileHelper, ImmersiveWeapons.MOD_ID, registryOps, placedFeatureKey, getConfiguredWorldCarvers());
	}

	private static HashMap<ResourceLocation, ConfiguredWorldCarver<?>> getConfiguredWorldCarvers() {
		HashMap<ResourceLocation, ConfiguredWorldCarver<?>> configuredWorldCarvers = new HashMap<>(10);


		configuredWorldCarvers.put(TRENCH, TRENCH_CARVER);

		return configuredWorldCarvers;
	}
}