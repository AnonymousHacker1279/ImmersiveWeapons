package tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.biomes;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import terrablender.api.ParameterUtils.*;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class IWOverworldBiomesProvider extends Region {

	public IWOverworldBiomesProvider(ResourceLocation name, RegionType type, int weight) {
		super(name, type, weight);
	}

	@Override
	public void addBiomes(Registry<Biome> registry, Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper) {
		addBiome(mapper,
				Temperature.WARM.parameter(),
				Humidity.span(Humidity.ARID, Humidity.NEUTRAL),
				Continentalness.span(Continentalness.NEAR_INLAND, Continentalness.FAR_INLAND),
				Erosion.span(Erosion.EROSION_0, Erosion.EROSION_1),
				Weirdness.span(Weirdness.MID_SLICE_VARIANT_ASCENDING, Weirdness.MID_SLICE_VARIANT_DESCENDING),
				Depth.SURFACE.parameter(),
				0.5f,
				BiomesAndDimensions.BATTLEFIELD);
	}
}