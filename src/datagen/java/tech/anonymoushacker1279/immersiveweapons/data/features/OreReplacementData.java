package tech.anonymoushacker1279.immersiveweapons.data.features;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration.TargetBlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.common.Tags.Blocks;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;

import java.util.List;

public class OreReplacementData {

	static final class ReplacementRules {
		public static final RuleTest REGULAR_STONE = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
		public static final RuleTest DEEPSLATE_STONE = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
		public static final RuleTest NETHER_STONE = new TagMatchTest(Blocks.ORE_BEARING_GROUND_NETHERRACK);
		public static final RuleTest END_STONE = new TagMatchTest(Blocks.END_STONES);
	}

	public static class OreReplacementTargets {
		public static final List<TargetBlockState> MOLTEN_ORE_TARGETS = List.of(
				OreConfiguration.target(ReplacementRules.NETHER_STONE,
						BlockRegistry.MOLTEN_ORE.get().defaultBlockState())
		);
		public static final List<TargetBlockState> SULFUR_ORE_TARGETS = List.of(
				OreConfiguration.target(ReplacementRules.REGULAR_STONE,
						BlockRegistry.SULFUR_ORE.get().defaultBlockState()),

				OreConfiguration.target(ReplacementRules.DEEPSLATE_STONE,
						BlockRegistry.DEEPSLATE_SULFUR_ORE.get().defaultBlockState()),

				OreConfiguration.target(ReplacementRules.NETHER_STONE,
						BlockRegistry.NETHER_SULFUR_ORE.get().defaultBlockState())
		);
		public static final List<TargetBlockState> COBALT_ORE_TARGETS = List.of(
				OreConfiguration.target(ReplacementRules.REGULAR_STONE,
						BlockRegistry.COBALT_ORE.get().defaultBlockState()),
				OreConfiguration.target(ReplacementRules.DEEPSLATE_STONE,
						BlockRegistry.DEEPSLATE_COBALT_ORE.get().defaultBlockState())
		);
		public static final List<TargetBlockState> POTASSIUM_NITRATE_ORE_TARGETS = List.of(
				OreConfiguration.target(ReplacementRules.REGULAR_STONE,
						BlockRegistry.POTASSIUM_NITRATE_ORE.get().defaultBlockState())
		);
		public static final List<TargetBlockState> VOID_ORE_TARGETS = List.of(
				OreConfiguration.target(ReplacementRules.END_STONE,
						BlockRegistry.VOID_ORE.get().defaultBlockState())
		);
	}
}