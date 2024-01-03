package tech.anonymoushacker1279.immersiveweapons.data.biomes;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class IWBiomes {

	public static final ResourceKey<Biome> BATTLEFIELD = createKey("battlefield");
	public static final ResourceKey<Biome> TILTROS_WASTES = createKey("tiltros_wastes");
	public static final ResourceKey<Biome> STARLIGHT_PLAINS = createKey("starlight_plains");
	public static final ResourceKey<Biome> DEADMANS_DESERT = createKey("deadmans_desert");

	private static ResourceKey<Biome> createKey(String name) {
		return ResourceKey.create(Registries.BIOME, new ResourceLocation(ImmersiveWeapons.MOD_ID, name));
	}

	public static void bootstrap(BootstapContext<Biome> context) {
		HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
		HolderGetter<ConfiguredWorldCarver<?>> vanillaConfiguredCarvers = context.lookup(Registries.CONFIGURED_CARVER);

		context.register(BATTLEFIELD, BiomesGenerator.battlefieldBiome(placedFeatures, vanillaConfiguredCarvers));
		context.register(TILTROS_WASTES, BiomesGenerator.tiltrosWastesBiome(placedFeatures, vanillaConfiguredCarvers));
		context.register(STARLIGHT_PLAINS, BiomesGenerator.starlightPlainsBiome(placedFeatures, vanillaConfiguredCarvers));
		context.register(DEADMANS_DESERT, BiomesGenerator.deadmansDesertBiome(placedFeatures, vanillaConfiguredCarvers));
	}
}