package tech.anonymoushacker1279.immersiveweapons.data.features;

import com.google.gson.JsonElement;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.*;
import net.minecraft.world.level.levelgen.carver.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.feature.WorldCarvers;

import java.util.HashMap;

public class CarversGenerator {

	public static ConfiguredWorldCarver<CanyonCarverConfiguration> TRENCH_CARVER;
	public static ConfiguredWorldCarver<CanyonCarverConfiguration> TILTROS_WASTES_CARVER;

	public static void init() {
		TRENCH_CARVER = new ConfiguredWorldCarver<>(WorldCarver.CANYON, WorldCarvers.TRENCH_CARVER_CONFIGURATION);
		TILTROS_WASTES_CARVER = new ConfiguredWorldCarver<>(WorldCarver.CANYON, WorldCarvers.TILTROS_WASTES_CARVER_CONFIGURATION);
	}

	protected static final ResourceLocation TRENCH = new ResourceLocation(ImmersiveWeapons.MOD_ID, "trench");
	public static final ResourceKey<ConfiguredWorldCarver<?>> TRENCH_KEY = ResourceKey.create(Registries.CONFIGURED_CARVER, TRENCH);

	protected static final ResourceLocation TILTROS_WASTES = new ResourceLocation(ImmersiveWeapons.MOD_ID, "tiltros_wastes");
	public static final ResourceKey<ConfiguredWorldCarver<?>> TILTROS_WASTES_KEY = ResourceKey.create(Registries.CONFIGURED_CARVER, TILTROS_WASTES);

	public static JsonCodecProvider<ConfiguredWorldCarver<?>> getCodecProvider(PackOutput output,
	                                                                           ExistingFileHelper existingFileHelper,
	                                                                           RegistryOps<JsonElement> registryOps,
	                                                                           ResourceKey<Registry<ConfiguredWorldCarver<?>>> placedFeatureKey) {

		return JsonCodecProvider.forDatapackRegistry(
				output, existingFileHelper, ImmersiveWeapons.MOD_ID, registryOps, placedFeatureKey, getConfiguredWorldCarvers());
	}

	private static HashMap<ResourceLocation, ConfiguredWorldCarver<?>> getConfiguredWorldCarvers() {
		HashMap<ResourceLocation, ConfiguredWorldCarver<?>> configuredWorldCarvers = new HashMap<>(10);
		configuredWorldCarvers.put(TRENCH, TRENCH_CARVER);
		configuredWorldCarvers.put(TILTROS_WASTES, TILTROS_WASTES_CARVER);

		return configuredWorldCarvers;
	}
}