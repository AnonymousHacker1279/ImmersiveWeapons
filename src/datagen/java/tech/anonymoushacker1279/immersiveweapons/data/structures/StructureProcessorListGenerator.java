package tech.anonymoushacker1279.immersiveweapons.data.structures;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;

import java.util.List;

public class StructureProcessorListGenerator {

	public static final ResourceKey<StructureProcessorList> RUST_50_PERCENT = createKey("rust_50_percent");
	public static final ResourceKey<StructureProcessorList> RUST_70_PERCENT = createKey("rust_70_percent");
	public static final ResourceKey<StructureProcessorList> WEATHER_70_PERCENT = createKey("weather_70_percent");

	private static ResourceKey<StructureProcessorList> createKey(String name) {
		return ResourceKey.create(Registries.PROCESSOR_LIST, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, name));
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
	}

	protected static void register(BootstrapContext<StructureProcessorList> context, ResourceKey<StructureProcessorList> key, List<StructureProcessor> processorList) {
		context.register(key, new StructureProcessorList(processorList));
	}
}