package tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.biomes;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import tech.anonymoushacker1279.immersiveweapons.data.features.IWBiomes;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.surface.SurfaceRuleBuilder;

public class SurfaceRuleData {

	public static SurfaceRules.RuleSource makeRules() {

		RuleSource battlefield = SurfaceRuleBuilder.start()
				.biome(IWBiomes.BATTLEFIELD)
				.surface(Blocks.GRASS_BLOCK.defaultBlockState())
				.subsurface(Blocks.COARSE_DIRT.defaultBlockState(), 3)
				.filler(Blocks.STONE.defaultBlockState())
				.rule(3, SurfaceRules.ifTrue(SurfaceRules.verticalGradient("deepslate",
								VerticalAnchor.absolute(0),
								VerticalAnchor.absolute(8)),
						SurfaceRules.state(Blocks.DEEPSLATE.defaultBlockState())))
				.build();

		return SurfaceRules.sequence(
				SurfaceRules.ifTrue(SurfaceRules.isBiome(IWBiomes.BATTLEFIELD), battlefield)
		);
	}
}