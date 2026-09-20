package tech.anonymoushacker1279.immersiveweapons.data.noise;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.NoiseRouterData;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.densityfunction.DensityFunction;
import net.minecraft.world.level.levelgen.densityfunction.DensityFunctions;
import net.minecraft.world.level.levelgen.material.MaterialRules;
import net.minecraft.world.level.levelgen.material.rule.MaterialRule;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import tech.anonymoushacker1279.immersiveweapons.data.biomes.IWBiomes;
import tech.anonymoushacker1279.immersiveweapons.data.dimensions.DimensionTypeGenerator;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.SurfaceRuleBuilder;

import java.util.List;
import java.util.Optional;

public class NoiseGenerator {

	public static final ResourceKey<NoiseGeneratorSettings> TILTROS = ResourceKey.create(Registries.NOISE_SETTINGS, DimensionTypeGenerator.TILTROS);

	public static void bootstrap(BootstrapContext<NoiseGeneratorSettings> context) {
		HolderGetter<Biome> getter = context.lookup(Registries.BIOME);
		register(context, TILTROS, new NoiseGeneratorSettings(
				NoiseSettings.create(-64, 256),
				Blocks.STONE.defaultBlockState(),
				Blocks.WATER.defaultBlockState(),
				modifiedFloatingIslands(context.lookup(Registries.DENSITY_FUNCTION), context.lookup(Registries.NOISE)),
				Holder.direct(makeSurfaceRules(getter)),
				List.of(),
				-64,
				false,
				Optional.empty(),
				false,
				NoiseGeneratorSettings.DebugFunctions.EMPTY
		));
	}

	public static MaterialRule makeSurfaceRules(HolderGetter<Biome> getter) {
		MaterialRule starlightPlains = SurfaceRuleBuilder.start(getter)
				.biome(IWBiomes.STARLIGHT_PLAINS)
				.surface(Blocks.GRASS_BLOCK.defaultBlockState())
				.subsurface(Blocks.DIRT.defaultBlockState(), 3)
				.filler(Blocks.STONE.defaultBlockState())
				.rule(3, MaterialRules.ifTrue(MaterialRules.verticalGradient("deepslate",
								VerticalAnchor.absolute(0),
								VerticalAnchor.absolute(8)),
						MaterialRules.state(Blocks.DEEPSLATE.defaultBlockState())))
				.build();

		MaterialRule tiltrosWastes = SurfaceRuleBuilder.start(getter)
				.biome(IWBiomes.TILTROS_WASTES)
				.surface(Blocks.GRASS_BLOCK.defaultBlockState())
				.subsurface(Blocks.COARSE_DIRT.defaultBlockState(), 3)
				.filler(Blocks.STONE.defaultBlockState())
				.rule(3, MaterialRules.ifTrue(MaterialRules.verticalGradient("deepslate",
								VerticalAnchor.absolute(0),
								VerticalAnchor.absolute(8)),
						MaterialRules.state(Blocks.DEEPSLATE.defaultBlockState())))
				.build();

		MaterialRule deadmansDesert = SurfaceRuleBuilder.start(getter)
				.biome(IWBiomes.DEADMANS_DESERT)
				.surface(BlockRegistry.BLOOD_SAND.get().defaultBlockState())
				.subsurface(BlockRegistry.BLOOD_SANDSTONE.get().defaultBlockState(), 3)
				.filler(Blocks.STONE.defaultBlockState())
				.rule(3, MaterialRules.ifTrue(MaterialRules.verticalGradient("deepslate",
								VerticalAnchor.absolute(0),
								VerticalAnchor.absolute(8)),
						MaterialRules.state(Blocks.DEEPSLATE.defaultBlockState())))
				.build();

		return MaterialRules.sequence(
				MaterialRules.ifTrue(MaterialRules.isBiome(getter, IWBiomes.STARLIGHT_PLAINS), starlightPlains),
				MaterialRules.ifTrue(MaterialRules.isBiome(getter, IWBiomes.TILTROS_WASTES), tiltrosWastes),
				MaterialRules.ifTrue(MaterialRules.isBiome(getter, IWBiomes.DEADMANS_DESERT), deadmansDesert)
		);
	}

	public static NoiseRouter modifiedFloatingIslands(HolderGetter<DensityFunction> densityFunction, HolderGetter<NormalNoise> noiseParameters) {
		return noNewCaves(densityFunction,
				noiseParameters,
				slideEndLike(NoiseRouterData.getFunction(densityFunction, createKey("end/base_3d_noise")), -64, 256));
	}

	private static NoiseRouter noNewCaves(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise> noiseParameters, DensityFunction postProcessor) {
		DensityFunction shiftX = NoiseRouterData.getFunction(densityFunctions, createKey("shift_x"));
		DensityFunction shiftZ = NoiseRouterData.getFunction(densityFunctions, createKey("shift_z"));
		DensityFunction temp = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25, noiseParameters.getOrThrow(Noises.TEMPERATURE));
		DensityFunction vegetation = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25, noiseParameters.getOrThrow(Noises.VEGETATION));
		DensityFunction post = postProcess(postProcessor);
		return new NoiseRouter(
				temp,
				vegetation,
				DensityFunctions.zero(),
				DensityFunctions.zero(),
				DensityFunctions.zero(),
				DensityFunctions.zero(),
				DensityFunctions.zero(),
				post
		);
	}

	private static DensityFunction postProcess(DensityFunction densityFunction) {
		DensityFunction blended = DensityFunctions.blendDensity(densityFunction);
		// Cell sizes match the 26.2 noise settings: horizontal size 2 (8 blocks), vertical size 1 (4 blocks)
		return DensityFunctions.interpolated(DensityFunctions.mul(blended, DensityFunctions.constant(0.64f)), 8, 4).squeeze();
	}

	private static DensityFunction slideEndLike(DensityFunction densityFunction, int minY, int height) {
		return slide(densityFunction, minY, height, 72, -184, -23.4375f, 4, 32, -0.234375f);
	}

	private static DensityFunction slide(DensityFunction input, int minY, int height, int topStartOffset, int topEndOffset, float topDelta, int bottomStartOffset, int bottomEndOffset, float bottomDelta) {
		DensityFunction topGradient = DensityFunctions.yClampedGradient(minY + height - topStartOffset, minY + height - topEndOffset, 1.0f, 0.0f);
		DensityFunction lerpTop = DensityFunctions.lerp(topGradient, topDelta, input);
		DensityFunction bottomOffset = DensityFunctions.yClampedGradient(minY + bottomStartOffset, minY + bottomEndOffset, 0.0f, 1.0f);
		return DensityFunctions.lerp(bottomOffset, bottomDelta, lerpTop);
	}

	private static ResourceKey<DensityFunction> createKey(String location) {
		return ResourceKey.create(Registries.DENSITY_FUNCTION, Identifier.withDefaultNamespace(location));
	}

	private static void register(BootstrapContext<NoiseGeneratorSettings> context, ResourceKey<NoiseGeneratorSettings> key, NoiseGeneratorSettings noiseGeneratorSettings) {
		context.register(key, noiseGeneratorSettings);
	}
}