package tech.anonymoushacker1279.immersiveweapons.data.dimensions;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate.*;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import tech.anonymoushacker1279.immersiveweapons.data.biomes.IWBiomes;

import java.util.List;

public class DimensionGenerator {

	public static final ResourceKey<LevelStem> TILTROS_STEM = ResourceKey.create(Registries.LEVEL_STEM, DimensionTypeGenerator.TILTROS_LEVEL_ID);
	public static final ResourceKey<NoiseGeneratorSettings> TILTROS_NOISE = ResourceKey.create(Registries.NOISE_SETTINGS, DimensionTypeGenerator.TILTROS_LEVEL_ID);
	public static final ResourceKey<Level> TILTROS_LEVEL = ResourceKey.create(Registries.DIMENSION, DimensionTypeGenerator.TILTROS_LEVEL_ID);

	public static void bootstrap(BootstrapContext<LevelStem> context) {
		HolderGetter<DimensionType> dimensionTypeHolderGetter = context.lookup(Registries.DIMENSION_TYPE);
		HolderGetter<NoiseGeneratorSettings> noiseGeneratorSettingsHolderGetter = context.lookup(Registries.NOISE_SETTINGS);
		HolderGetter<Biome> biomeHolderGetter = context.lookup(Registries.BIOME);

		register(context, TILTROS_STEM, new LevelStem(dimensionTypeHolderGetter.getOrThrow(DimensionTypeGenerator.TILTROS_DIMENSION_TYPE),
				new NoiseBasedChunkGenerator(
						MultiNoiseBiomeSource.createFromList(
								new ParameterList<>(
										List.of(
												Pair.of(
														new ParameterPoint(
																Parameter.point(0.6f),
																Parameter.point(0.25f),
																Parameter.point(1.0f),
																Parameter.point(0.85f),
																Parameter.point(0.3f),
																Parameter.point(0.1f),
																0
														),
														biomeHolderGetter.getOrThrow(IWBiomes.TILTROS_WASTES)
												),
												Pair.of(
														new ParameterPoint(
																Parameter.point(0.6f),
																Parameter.point(0.3f),
																Parameter.point(1.0f),
																Parameter.point(0.7f),
																Parameter.point(0.3f),
																Parameter.point(0.1f),
																0
														),
														biomeHolderGetter.getOrThrow(IWBiomes.STARLIGHT_PLAINS)
												),
												Pair.of(
														new ParameterPoint(
																Parameter.point(0.2f),
																Parameter.point(0.1f),
																Parameter.point(1.0f),
																Parameter.point(0.75f),
																Parameter.point(0.3f),
																Parameter.point(0.3f),
																0
														),
														biomeHolderGetter.getOrThrow(IWBiomes.DEADMANS_DESERT)
												)
										)
								)
						),
						noiseGeneratorSettingsHolderGetter.getOrThrow(TILTROS_NOISE)
				)));
	}

	private static void register(BootstrapContext<LevelStem> context, ResourceKey<LevelStem> key, LevelStem levelStem) {
		context.register(key, levelStem);
	}
}