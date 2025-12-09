package tech.anonymoushacker1279.immersiveweapons.data.noise;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import tech.anonymoushacker1279.immersiveweapons.data.biomes.IWBiomes;
import tech.anonymoushacker1279.immersiveweapons.data.dimensions.DimensionTypeGenerator;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.SurfaceRuleBuilder;

import java.util.List;

public class NoiseGenerator {

	public static final ResourceKey<NoiseGeneratorSettings> TILTROS = ResourceKey.create(Registries.NOISE_SETTINGS, DimensionTypeGenerator.TILTROS_LEVEL_ID);

	public static void bootstrap(BootstrapContext<NoiseGeneratorSettings> context) {
		register(context, TILTROS, new NoiseGeneratorSettings(
				NoiseSettings.create(-64, 256, 2, 1),
				Blocks.STONE.defaultBlockState(),
				Blocks.WATER.defaultBlockState(),
				modifiedFloatingIslands(context.lookup(Registries.DENSITY_FUNCTION), context.lookup(Registries.NOISE)),
				makeSurfaceRules(),
				List.of(),
				-64,
				false,
				true,
				true,
				false
		));
	}

	public static SurfaceRules.RuleSource makeSurfaceRules() {
		SurfaceRules.RuleSource starlightPlains = SurfaceRuleBuilder.start()
				.biome(IWBiomes.STARLIGHT_PLAINS)
				.surface(Blocks.GRASS_BLOCK.defaultBlockState())
				.subsurface(Blocks.DIRT.defaultBlockState(), 3)
				.filler(Blocks.STONE.defaultBlockState())
				.rule(3, SurfaceRules.ifTrue(SurfaceRules.verticalGradient("deepslate",
								VerticalAnchor.absolute(0),
								VerticalAnchor.absolute(8)),
						SurfaceRules.state(Blocks.DEEPSLATE.defaultBlockState())))
				.build();

		SurfaceRules.RuleSource tiltrosWastes = SurfaceRuleBuilder.start()
				.biome(IWBiomes.TILTROS_WASTES)
				.surface(Blocks.GRASS_BLOCK.defaultBlockState())
				.subsurface(Blocks.COARSE_DIRT.defaultBlockState(), 3)
				.filler(Blocks.STONE.defaultBlockState())
				.rule(3, SurfaceRules.ifTrue(SurfaceRules.verticalGradient("deepslate",
								VerticalAnchor.absolute(0),
								VerticalAnchor.absolute(8)),
						SurfaceRules.state(Blocks.DEEPSLATE.defaultBlockState())))
				.build();

		SurfaceRules.RuleSource deadmansDesert = SurfaceRuleBuilder.start()
				.biome(IWBiomes.DEADMANS_DESERT)
				.surface(BlockRegistry.BLOOD_SAND.get().defaultBlockState())
				.subsurface(BlockRegistry.BLOOD_SANDSTONE.get().defaultBlockState(), 3)
				.filler(Blocks.STONE.defaultBlockState())
				.rule(3, SurfaceRules.ifTrue(SurfaceRules.verticalGradient("deepslate",
								VerticalAnchor.absolute(0),
								VerticalAnchor.absolute(8)),
						SurfaceRules.state(Blocks.DEEPSLATE.defaultBlockState())))
				.build();

		return SurfaceRules.sequence(
				SurfaceRules.ifTrue(SurfaceRules.isBiome(IWBiomes.STARLIGHT_PLAINS), starlightPlains),
				SurfaceRules.ifTrue(SurfaceRules.isBiome(IWBiomes.TILTROS_WASTES), tiltrosWastes),
				SurfaceRules.ifTrue(SurfaceRules.isBiome(IWBiomes.DEADMANS_DESERT), deadmansDesert)
		);
	}

	public static NoiseRouter modifiedFloatingIslands(HolderGetter<DensityFunction> densityFunction, HolderGetter<NormalNoise.NoiseParameters> noiseParameters) {
		return noNewCaves(densityFunction,
				noiseParameters,
				slideEndLike(getFunction(densityFunction, createKey("end/base_3d_noise")), -64, 256));
	}

	private static NoiseRouter noNewCaves(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise.NoiseParameters> noiseParameters, DensityFunction postProcessor) {
		DensityFunction shiftX = getFunction(densityFunctions, createKey("shift_x"));
		DensityFunction shiftZ = getFunction(densityFunctions, createKey("shift_z"));
		DensityFunction temp = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25, noiseParameters.getOrThrow(Noises.TEMPERATURE));
		DensityFunction vegetation = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25, noiseParameters.getOrThrow(Noises.VEGETATION));
		DensityFunction post = postProcess(postProcessor);
		return new NoiseRouter(
				DensityFunctions.zero(),
				DensityFunctions.zero(),
				DensityFunctions.zero(),
				DensityFunctions.zero(),
				temp,
				vegetation,
				DensityFunctions.zero(),
				DensityFunctions.zero(),
				DensityFunctions.zero(),
				DensityFunctions.zero(),
				DensityFunctions.zero(),
				post,
				DensityFunctions.zero(),
				DensityFunctions.zero(),
				DensityFunctions.zero()
		);
	}

	private static DensityFunction getFunction(HolderGetter<DensityFunction> densityFunctionRegistry, ResourceKey<DensityFunction> key) {
		return new DensityFunctions.HolderHolder(densityFunctionRegistry.getOrThrow(key));
	}

	private static DensityFunction postProcess(DensityFunction densityFunction) {
		DensityFunction densityfunction = DensityFunctions.blendDensity(densityFunction);
		return DensityFunctions.mul(DensityFunctions.interpolated(densityfunction), DensityFunctions.constant(0.64)).squeeze();
	}

	private static DensityFunction slideEndLike(DensityFunction densityFunction, int minY, int height) {
		return slide(densityFunction, minY, height, 72, -184, -23.4375, 4, 32, -0.234375);
	}

	private static DensityFunction slide(DensityFunction input, int minY, int height, int topStartOffset, int topEndOffset, double topDelta, int bottomStartOffset, int bottomEndOffset, double bottomDelta) {
		DensityFunction topGradient = DensityFunctions.yClampedGradient(minY + height - topStartOffset, minY + height - topEndOffset, 1.0, 0.0);
		DensityFunction lerpTop = DensityFunctions.lerp(topGradient, topDelta, input);
		DensityFunction bottomOffset = DensityFunctions.yClampedGradient(minY + bottomStartOffset, minY + bottomEndOffset, 0.0, 1.0);
		return DensityFunctions.lerp(bottomOffset, bottomDelta, lerpTop);
	}

	private static ResourceKey<DensityFunction> createKey(String location) {
		return ResourceKey.create(Registries.DENSITY_FUNCTION, ResourceLocation.withDefaultNamespace(location));
	}

	private static void register(BootstrapContext<NoiseGeneratorSettings> context, ResourceKey<NoiseGeneratorSettings> key, NoiseGeneratorSettings noiseGeneratorSettings) {
		context.register(key, noiseGeneratorSettings);
	}
}