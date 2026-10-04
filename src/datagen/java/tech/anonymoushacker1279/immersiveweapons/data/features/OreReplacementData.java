package tech.anonymoushacker1279.immersiveweapons.data.features;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.BlockReplacement;
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
		public static final List<BlockReplacement> MOLTEN_ORE_TARGETS = List.of(
				BlockReplacement.replace(ReplacementRules.NETHER_STONE,
						BlockRegistry.MOLTEN_ORE.get().defaultBlockState())
		);
		public static final List<BlockReplacement> TESLA_ORE_TARGETS = List.of(
				BlockReplacement.replace(ReplacementRules.DEEPSLATE_STONE,
						BlockRegistry.DORMANT_TESLA_ORE.get().defaultBlockState())
		);
		public static final List<BlockReplacement> COBALT_ORE_TARGETS = List.of(
				BlockReplacement.replace(ReplacementRules.REGULAR_STONE,
						BlockRegistry.COBALT_ORE.get().defaultBlockState()),
				BlockReplacement.replace(ReplacementRules.DEEPSLATE_STONE,
						BlockRegistry.DEEPSLATE_COBALT_ORE.get().defaultBlockState())
		);
		public static final List<BlockReplacement> VOID_ORE_TARGETS = List.of(
				BlockReplacement.replace(ReplacementRules.END_STONE,
						BlockRegistry.VOID_ORE.get().defaultBlockState())
		);
	}
}
