package tech.anonymoushacker1279.immersiveweapons.world.level.levelgen;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class IWOverworldBiomesProvider extends Region {

	public static final ResourceKey<Biome> BATTLEFIELD = ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "battlefield"));

	public IWOverworldBiomesProvider(ResourceLocation name, RegionType type, int weight) {
		super(name, type, weight);
	}

	@Override
	public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
		addModifiedVanillaOverworldBiomes(mapper, modifier -> modifier.replaceBiome(Biomes.PLAINS, BATTLEFIELD));
	}

	public static SurfaceRules.RuleSource makeSurfaceRules() {
		SurfaceRules.RuleSource battlefield = SurfaceRuleBuilder.start()
				.biome(BATTLEFIELD)
				.surface(Blocks.GRASS_BLOCK.defaultBlockState())
				.subsurface(Blocks.COARSE_DIRT.defaultBlockState(), 3)
				.filler(Blocks.STONE.defaultBlockState())
				.rule(3, SurfaceRules.ifTrue(SurfaceRules.verticalGradient("deepslate",
								VerticalAnchor.absolute(0),
								VerticalAnchor.absolute(8)),
						SurfaceRules.state(Blocks.DEEPSLATE.defaultBlockState())))
				.build();

		return SurfaceRules.sequence(
				SurfaceRules.ifTrue(SurfaceRules.isBiome(BATTLEFIELD), battlefield)
		);
	}
}