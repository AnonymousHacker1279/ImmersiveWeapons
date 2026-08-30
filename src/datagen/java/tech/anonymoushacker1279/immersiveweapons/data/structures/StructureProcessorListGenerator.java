package tech.anonymoushacker1279.immersiveweapons.data.structures;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;

import java.util.List;

public class StructureProcessorListGenerator {

	public static final ResourceKey<StructureProcessorList> RUST_50_PERCENT = createKey("rust_50_percent");
	public static final ResourceKey<StructureProcessorList> RUST_70_PERCENT = createKey("rust_70_percent");
	public static final ResourceKey<StructureProcessorList> WEATHER_70_PERCENT = createKey("weather_70_percent");
	public static final ResourceKey<StructureProcessorList> ABANDONED_FACTORY_DECAY = createKey("abandoned_factory_decay");

	private static ResourceKey<StructureProcessorList> createKey(String name) {
		return ResourceKey.create(Registries.PROCESSOR_LIST, Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, name));
	}

	public static void bootstrap(BootstrapContext<StructureProcessorList> context) {
		register(context, RUST_50_PERCENT, ImmutableList.of(
				new RuleProcessor(ImmutableList.of(
						new ProcessorRule(new RandomBlockMatchTest(Blocks.IRON_BLOCK, 0.5f), AlwaysTrueTest.INSTANCE, BlockRegistry.RUSTED_IRON_BLOCK.get().defaultBlockState())
				))
		));

		register(context, RUST_70_PERCENT, ImmutableList.of(
				new RuleProcessor(ImmutableList.of(
						new ProcessorRule(new RandomBlockMatchTest(Blocks.IRON_BLOCK, 0.7f), AlwaysTrueTest.INSTANCE, BlockRegistry.RUSTED_IRON_BLOCK.get().defaultBlockState())
				))
		));

		register(context, WEATHER_70_PERCENT, ImmutableList.of(
				new RuleProcessor(ImmutableList.of(
						new ProcessorRule(new RandomBlockMatchTest(Blocks.IRON_BLOCK, 0.7f), AlwaysTrueTest.INSTANCE, BlockRegistry.RUSTED_IRON_BLOCK.get().defaultBlockState())
				)),
				new RuleProcessor(ImmutableList.of(
						new ProcessorRule(new RandomBlockMatchTest(Blocks.COBBLESTONE, 0.7f), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE.defaultBlockState())
				)),
				new RuleProcessor(ImmutableList.of(
						new ProcessorRule(new RandomBlockMatchTest(Blocks.STONE_BRICKS, 0.7f), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_STONE_BRICKS.defaultBlockState())
				))
		));

		register(context, ABANDONED_FACTORY_DECAY, ImmutableList.of(
				new RuleProcessor(ImmutableList.of(
						new ProcessorRule(new RandomBlockMatchTest(Blocks.STONE_BRICKS, 0.25f),
								AlwaysTrueTest.INSTANCE,
								Blocks.MOSSY_STONE_BRICKS.defaultBlockState()),
						new ProcessorRule(new RandomBlockMatchTest(Blocks.STONE_BRICKS, 0.25f),
								AlwaysTrueTest.INSTANCE,
								Blocks.CRACKED_STONE_BRICKS.defaultBlockState()),
						new ProcessorRule(new RandomBlockStateMatchTest(Blocks.STONE_BRICK_SLAB.defaultBlockState()
								.setValue(SlabBlock.TYPE, SlabType.TOP), 0.25f),
								AlwaysTrueTest.INSTANCE,
								Blocks.MOSSY_STONE_BRICK_SLAB.defaultBlockState()
										.setValue(SlabBlock.TYPE, SlabType.TOP))
				)),
				new RuleProcessor(stairRules(Blocks.STONE_BRICK_STAIRS, Blocks.MOSSY_STONE_BRICK_STAIRS, 0.25f)),
				new RuleProcessor(ImmutableList.of(
						new ProcessorRule(
								new RandomBlockMatchTest(Blocks.COPPER_BLOCK.weathering().unaffected(), 0.1F),
								AlwaysTrueTest.INSTANCE,
								Blocks.COPPER_BLOCK.weathering().oxidized().defaultBlockState()),
						new ProcessorRule(
								new RandomBlockMatchTest(Blocks.COPPER_BLOCK.weathering().unaffected(), 0.33333334F),
								AlwaysTrueTest.INSTANCE,
								Blocks.COPPER_BLOCK.weathering().weathered().defaultBlockState()),
						new ProcessorRule(
								new RandomBlockMatchTest(Blocks.COPPER_BLOCK.weathering().unaffected(), 0.5F),
								AlwaysTrueTest.INSTANCE,
								Blocks.COPPER_BLOCK.weathering().exposed().defaultBlockState()),
						new ProcessorRule(
								new RandomBlockMatchTest(Blocks.COPPER_GRATE.weathering().unaffected(), 0.1F),
								AlwaysTrueTest.INSTANCE,
								Blocks.COPPER_GRATE.weathering().oxidized().defaultBlockState()),
						new ProcessorRule(
								new RandomBlockMatchTest(Blocks.COPPER_GRATE.weathering().unaffected(), 0.33333334F),
								AlwaysTrueTest.INSTANCE,
								Blocks.COPPER_GRATE.weathering().weathered().defaultBlockState()),
						new ProcessorRule(
								new RandomBlockMatchTest(Blocks.COPPER_GRATE.weathering().unaffected(), 0.5F),
								AlwaysTrueTest.INSTANCE,
								Blocks.COPPER_GRATE.weathering().exposed().defaultBlockState()),
						new ProcessorRule(
								new RandomBlockMatchTest(Blocks.CHISELED_COPPER.weathering().unaffected(), 0.1F),
								AlwaysTrueTest.INSTANCE,
								Blocks.CHISELED_COPPER.weathering().oxidized().defaultBlockState()),
						new ProcessorRule(
								new RandomBlockMatchTest(Blocks.CHISELED_COPPER.weathering().unaffected(), 0.33333334F),
								AlwaysTrueTest.INSTANCE,
								Blocks.CHISELED_COPPER.weathering().weathered().defaultBlockState()),
						new ProcessorRule(
								new RandomBlockMatchTest(Blocks.CHISELED_COPPER.weathering().unaffected(), 0.5F),
								AlwaysTrueTest.INSTANCE,
								Blocks.CHISELED_COPPER.weathering().exposed().defaultBlockState())
				)),
				new RuleProcessor(stairRules(Blocks.CUT_COPPER_STAIRS.weathering().unaffected(), Blocks.CUT_COPPER_STAIRS.weathering().oxidized(), 0.1F)),
				new RuleProcessor(stairRules(Blocks.CUT_COPPER_STAIRS.weathering().unaffected(), Blocks.CUT_COPPER_STAIRS.weathering().weathered(), 0.3333334F)),
				new RuleProcessor(stairRules(Blocks.CUT_COPPER_STAIRS.weathering().unaffected(), Blocks.CUT_COPPER_STAIRS.weathering().exposed(), 0.5F))
		));
	}

	/// Create a processor rule set for stair type blocks. Enumerates through all three blockstate properties: facing,
	/// half, and shape, and applies the same probability to each combination.
	private static ImmutableList<ProcessorRule> stairRules(Block original, Block target, float probability) {
		ImmutableList.Builder<ProcessorRule> builder = ImmutableList.builder();

		for (Direction direction : Direction.Plane.HORIZONTAL) {
			for (Half half : Half.values()) {
				for (StairsShape shape : StairsShape.values()) {
					BlockState originalState = original.defaultBlockState()
							.setValue(StairBlock.FACING, direction)
							.setValue(StairBlock.HALF, half)
							.setValue(StairBlock.SHAPE, shape);

					BlockState replacementState = target.defaultBlockState()
							.setValue(StairBlock.FACING, direction)
							.setValue(StairBlock.HALF, half)
							.setValue(StairBlock.SHAPE, shape);

					builder.add(new ProcessorRule(
							new RandomBlockStateMatchTest(originalState, probability),
							AlwaysTrueTest.INSTANCE,
							replacementState));
				}
			}
		}

		return builder.build();
	}

	protected static void register(BootstrapContext<StructureProcessorList> context, ResourceKey<StructureProcessorList> key, List<StructureProcessor> processorList) {
		context.register(key, new StructureProcessorList(processorList));
	}
}