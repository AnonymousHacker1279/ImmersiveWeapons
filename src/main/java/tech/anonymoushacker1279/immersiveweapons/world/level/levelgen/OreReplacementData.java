package tech.anonymoushacker1279.immersiveweapons.world.level.levelgen;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration.TargetBlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

import java.util.List;

public class OreReplacementData {

	static final class ReplacementRules {
		public static final RuleTest REGULAR_STONE = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
		public static final RuleTest DEEPSLATE_STONE = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

		public static final RuleTest NETHER_STONE = createRuleFromTag("forge:ore_bearing_ground/netherrack");

		public static RuleTest createRuleFromTag(String tagLocation) {
			TagKey<Block> blockTag = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(tagLocation));
			return new TagMatchTest(blockTag);
		}
	}

	public static class OreReplacementTargets {
		public static final List<TargetBlockState> MOLTEN_ORE_TARGETS = List.of(
				OreConfiguration.target(ReplacementRules.NETHER_STONE,
						DeferredRegistryHandler.MOLTEN_ORE.get().defaultBlockState())
		);
		public static final List<TargetBlockState> SULFUR_ORE_TARGETS = List.of(
				OreConfiguration.target(ReplacementRules.REGULAR_STONE,
						DeferredRegistryHandler.SULFUR_ORE.get().defaultBlockState()),

				OreConfiguration.target(ReplacementRules.DEEPSLATE_STONE,
						DeferredRegistryHandler.DEEPSLATE_SULFUR_ORE.get().defaultBlockState()),

				OreConfiguration.target(ReplacementRules.NETHER_STONE,
						DeferredRegistryHandler.NETHER_SULFUR_ORE.get().defaultBlockState())
		);
		public static final List<TargetBlockState> COBALT_ORE_TARGETS = List.of(
				OreConfiguration.target(ReplacementRules.REGULAR_STONE,
						DeferredRegistryHandler.COBALT_ORE.get().defaultBlockState()),
				OreConfiguration.target(ReplacementRules.DEEPSLATE_STONE,
						DeferredRegistryHandler.DEEPSLATE_COBALT_ORE.get().defaultBlockState())
		);
	}
}