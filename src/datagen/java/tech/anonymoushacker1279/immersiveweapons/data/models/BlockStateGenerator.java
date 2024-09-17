package tech.anonymoushacker1279.immersiveweapons.data.models;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.client.model.generators.ModelBuilder.FaceRotation;
import net.neoforged.neoforge.client.model.generators.ModelFile.ExistingModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.block.*;
import tech.anonymoushacker1279.immersiveweapons.block.barbed_wire.BarbedWireBlock;
import tech.anonymoushacker1279.immersiveweapons.block.decoration.*;
import tech.anonymoushacker1279.immersiveweapons.block.star_forge.StarForgeControllerBlock;
import tech.anonymoushacker1279.immersiveweapons.data.CustomDataGenerator;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;

import java.util.*;
import java.util.function.Supplier;

public class BlockStateGenerator extends BlockStateProvider {

	public static final List<Block> SIMPLE_BLOCKS = new ArrayList<>(25);
	public static final List<Block> STONE_BASED_ORES = new ArrayList<>(10);
	public static final List<Block> DEEPSLATE_BASED_ORES = new ArrayList<>(10);
	public static final List<Block> NETHERRACK_BASED_ORES = new ArrayList<>(10);

	public BlockStateGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, ImmersiveWeapons.MOD_ID, existingFileHelper);
	}

	private void buildLists() {
		SIMPLE_BLOCKS.add(BlockRegistry.CLOUD_MARBLE.get());
		SIMPLE_BLOCKS.add(BlockRegistry.CLOUD_MARBLE_BRICKS.get());
		SIMPLE_BLOCKS.add(BlockRegistry.RAW_SULFUR_BLOCK.get());
		SIMPLE_BLOCKS.add(BlockRegistry.VENTUS_ORE.get());
		SIMPLE_BLOCKS.add(BlockRegistry.COBALT_BLOCK.get());
		SIMPLE_BLOCKS.add(BlockRegistry.RAW_COBALT_BLOCK.get());
		SIMPLE_BLOCKS.add(BlockRegistry.MOLTEN_BLOCK.get());
		SIMPLE_BLOCKS.add(BlockRegistry.BURNED_OAK_PLANKS.get());
		SIMPLE_BLOCKS.add(BlockRegistry.MUD.get());
		SIMPLE_BLOCKS.add(BlockRegistry.DRIED_MUD.get());
		SIMPLE_BLOCKS.add(BlockRegistry.HARDENED_MUD.get());
		SIMPLE_BLOCKS.add(BlockRegistry.STARDUST_PLANKS.get());
		SIMPLE_BLOCKS.add(BlockRegistry.BLOOD_SAND.get());
		SIMPLE_BLOCKS.add(BlockRegistry.ASTRAL_BLOCK.get());
		SIMPLE_BLOCKS.add(BlockRegistry.STARSTORM_BLOCK.get());
		SIMPLE_BLOCKS.add(BlockRegistry.CHAMPION_BRICKS.get());
		SIMPLE_BLOCKS.add(BlockRegistry.CHAMPION_BASE.get());
		SIMPLE_BLOCKS.add(BlockRegistry.CHAMPION_KEYCARD_BRICKS.get());
		SIMPLE_BLOCKS.add(BlockRegistry.STAR_FORGE_BRICKS.get());
		SIMPLE_BLOCKS.add(BlockRegistry.TILTROS_PORTAL_FRAME.get());

		STONE_BASED_ORES.add(BlockRegistry.SULFUR_ORE.get());
		STONE_BASED_ORES.add(BlockRegistry.ELECTRIC_ORE.get());
		STONE_BASED_ORES.add(BlockRegistry.COBALT_ORE.get());
		STONE_BASED_ORES.add(BlockRegistry.POTASSIUM_NITRATE_ORE.get());

		DEEPSLATE_BASED_ORES.add(BlockRegistry.DEEPSLATE_SULFUR_ORE.get());
		DEEPSLATE_BASED_ORES.add(BlockRegistry.DEEPSLATE_COBALT_ORE.get());

		NETHERRACK_BASED_ORES.add(BlockRegistry.MOLTEN_ORE.get());
		NETHERRACK_BASED_ORES.add(BlockRegistry.NETHER_SULFUR_ORE.get());
	}

	@Override
	protected void registerStatesAndModels() {
		buildLists();

		List<Block> blocks = new ArrayList<>(250);
		BlockRegistry.BLOCKS.getEntries().stream().map(Supplier::get).forEach(blocks::add);

		ResourceLocation stardust_granule_overlay = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/stardust_granule_overlay");

		// Generate simple, six-sided blocks
		for (Block block : SIMPLE_BLOCKS) {
			simpleBlock(block);
		}

		// Generate data for simple, six-sided blocks with custom render types
		simpleBlock(BlockRegistry.BULLETPROOF_GLASS.get(), models().cubeAll("bulletproof_glass",
						ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/bulletproof_glass"))
				.renderType("minecraft:cutout_mipped"));
		simpleBlock(BlockRegistry.CLOUD.get(), models().cubeAll("cloud",
						ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/cloud"))
				.renderType("minecraft:translucent"));
		for (Block block : CustomDataGenerator.ALL_BLOCKS) {
			if (block.getDescriptionId().contains("stained_bulletproof_glass")) {
				String color = Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block)).getPath().split("stained_bulletproof_glass")[0];
				simpleBlock(block, models().cubeAll(color + "stained_bulletproof_glass",
								ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/" + color + "stained_bulletproof_glass"))
						.renderType("minecraft:translucent"));
			}
		}
		// Stardust leaves have emissive layers, requiring a separate model
		ResourceLocation stardust_leaves_all = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/stardust_leaves");
		Block stardust_leaves = BlockRegistry.STARDUST_LEAVES.get();
		simpleBlock(BlockRegistry.STARDUST_LEAVES.get(),
				models().withExistingParent(Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(stardust_leaves)).getPath(), "minecraft:block/cube_all")
						.texture("all", stardust_leaves_all)
						.texture("overlay", stardust_granule_overlay)
						.renderType("minecraft:cutout_mipped")
						.element()
						.allFaces((direction, faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16)
								.texture("#all")
								.end()).end()
						.element()
						.allFaces((direction, faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16)
								.texture("#overlay")
								.rotation(FaceRotation.CLOCKWISE_90)
								.emissivity(15, 3)
								.end()).end());

		// Generate data for simple, six-sized blocks that use overlays (ores)
		for (Block block : STONE_BASED_ORES) {
			simpleBlock(block, models().withExistingParent(Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block)).getPath(),
							ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "simple_overlay"))
					.texture("all", "minecraft:block/stone")
					.texture("overlay", ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/"
							+ Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block)).getPath()))
					.renderType("minecraft:cutout_mipped"));
		}
		for (Block block : DEEPSLATE_BASED_ORES) {
			simpleBlock(block, models().withExistingParent(Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block)).getPath(),
							ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "simple_overlay"))
					.texture("all", "minecraft:block/deepslate")
					.texture("overlay", ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/"
							+ Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block)).getPath()
							.replace("deepslate_", "")))
					.renderType("minecraft:cutout_mipped"));
		}
		for (Block block : NETHERRACK_BASED_ORES) {
			simpleBlock(block, models().withExistingParent(Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block)).getPath(),
							ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "simple_overlay"))
					.texture("all", "minecraft:block/netherrack")
					.texture("overlay", ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/"
							+ Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block)).getPath()
							.replace("nether_", "")))
					.renderType("minecraft:cutout_mipped"));
		}
		// Astral ore uses smooth quartz for a base
		simpleBlock(BlockRegistry.ASTRAL_ORE.get(), models().withExistingParent(Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(BlockRegistry.ASTRAL_ORE.get())).getPath(),
						ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "simple_overlay"))
				.texture("all", "minecraft:block/quartz_block_bottom")
				.texture("overlay", ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/astral_ore"))
				.renderType("minecraft:cutout_mipped"));

		// Generate data for tables
		blocks.stream().filter(WoodenTableBlock.class::isInstance).forEach(block -> {
			String namespace = "minecraft:block/";

			if (block == BlockRegistry.BURNED_OAK_TABLE.get() || block == BlockRegistry.STARDUST_TABLE.get()) {
				namespace = ImmersiveWeapons.MOD_ID + ":block/";
			}

			simpleBlock(block, models().withExistingParent(Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block)).getPath(),
							ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "table"))
					.texture("all", namespace + Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block)).getPath()
							.replace("table", "planks"))
					.renderType("minecraft:cutout_mipped"));
		});

		// Generate data for flags
		getVariantBuilder(BlockRegistry.FLAG_POLE.get())
				.partialState().with(FlagPoleBlock.IS_BASE, true)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "flag_pole_base"))))
				.partialState().with(FlagPoleBlock.IS_BASE, false)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "flag_pole"))));

		blocks.stream().filter(FlagBlock.class::isInstance).forEach(block -> horizontalBlock(block, models()
				.withExistingParent(Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block)).getPath(),
						ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "flag"))
				.texture("flag", ImmersiveWeapons.MOD_ID +
						":block/" + Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block)).getPath())));

		// Generate data for cube-bottom-top blocks
		simpleBlock(BlockRegistry.BLOOD_SANDSTONE.get(), models()
				.cubeBottomTop("blood_sandstone",
						ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/blood_sandstone"),
						ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/blood_sandstone_bottom"),
						ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/blood_sandstone_top")));

		// Generate data for rotatable pillar blocks
		axisBlock(BlockRegistry.CLOUD_MARBLE_PILLAR.get());
		logBlock(BlockRegistry.BURNED_OAK_LOG.get());
		axisBlock(BlockRegistry.BURNED_OAK_WOOD.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/burned_oak_log"),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/burned_oak_log"));
		logBlock(BlockRegistry.STRIPPED_BURNED_OAK_LOG.get());
		axisBlock(BlockRegistry.STRIPPED_BURNED_OAK_WOOD.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/stripped_burned_oak_log"),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/stripped_burned_oak_log"));
		logBlock(BlockRegistry.STRIPPED_STARDUST_LOG.get());
		axisBlock(BlockRegistry.STRIPPED_STARDUST_WOOD.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/stripped_stardust_log"),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/stripped_stardust_log"));
		// The stardust log is a bit different as it has emissive texture layers. We need to generate the models for that.
		ResourceLocation stardust_log_side = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/stardust_log");
		ResourceLocation stardust_log_top = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/stardust_log_top");
		RotatedPillarBlock stardust_log = BlockRegistry.STARDUST_LOG.get();
		axisBlock(BlockRegistry.STARDUST_LOG.get(),
				models().withExistingParent(Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(stardust_log)).getPath(), "minecraft:block/cube_column")
						.texture("side", stardust_log_side)
						.texture("end", stardust_log_top)
						.texture("overlay", stardust_granule_overlay)
						.renderType("minecraft:cutout_mipped")
						.element()
						.allFaces((direction, faceBuilder) -> {
							switch (direction) {
								case NORTH, SOUTH, EAST, WEST -> faceBuilder.uvs(0, 0, 16, 16)
										.texture("#side")
										.end();
								case UP, DOWN -> faceBuilder.texture("#end").end();
							}
						}).end()
						.element()
						.allFaces((direction, faceBuilder) -> {
							switch (direction) {
								case NORTH, SOUTH, EAST, WEST -> faceBuilder.uvs(0, 0, 16, 16)
										.texture("#overlay")
										.emissivity(15, 3)
										.end();
								case UP, DOWN -> faceBuilder.texture("#end").end();
							}
						}).end(),
				models().withExistingParent(Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(stardust_log)).getPath() + "_horizontal", "minecraft:block/cube_column_horizontal")
						.texture("side", stardust_log_side)
						.texture("end", stardust_log_top)
						.texture("overlay", stardust_granule_overlay)
						.renderType("minecraft:cutout_mipped")
						.element()
						.allFaces((direction, faceBuilder) -> {
							switch (direction) {
								case NORTH, SOUTH, EAST, WEST -> faceBuilder.uvs(0, 0, 16, 16)
										.texture("#side")
										.end();
								case UP, DOWN -> faceBuilder.texture("#end").end();
							}
						}).end()
						.element()
						.allFaces((direction, faceBuilder) -> {
							switch (direction) {
								case NORTH, SOUTH, EAST, WEST -> faceBuilder.uvs(0, 0, 16, 16)
										.texture("#overlay")
										.emissivity(15, 3)
										.end();
								case UP, DOWN -> faceBuilder.texture("#end").end();
							}
						}).end());
		RotatedPillarBlock stardust_wood = BlockRegistry.STARDUST_WOOD.get();
		axisBlock(stardust_wood,
				models().withExistingParent(Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(stardust_wood)).getPath(), "minecraft:block/cube_column")
						.texture("side", stardust_log_side)
						.texture("end", stardust_log_side)
						.texture("overlay", stardust_granule_overlay)
						.renderType("minecraft:cutout_mipped")
						.element()
						.allFaces((direction, faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16)
								.texture("#side")
								.end()).end()
						.element()
						.allFaces((direction, faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16)
								.texture("#overlay")
								.emissivity(15, 3)
								.end()).end(),
				models().withExistingParent(Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(stardust_wood)).getPath() + "_horizontal", "minecraft:block/cube_column_horizontal")
						.texture("side", stardust_log_side)
						.texture("end", stardust_log_side)
						.texture("overlay", stardust_granule_overlay)
						.renderType("minecraft:cutout_mipped")
						.element()
						.allFaces((direction, faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16)
								.texture("#side")
								.end()).end()
						.element()
						.allFaces((direction, faceBuilder) -> faceBuilder.uvs(0, 0, 16, 16)
								.texture("#overlay")
								.emissivity(15, 3)
								.end()).end());

		// Generate data for stair blocks
		stairsBlock(BlockRegistry.CLOUD_MARBLE_BRICK_STAIRS.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/cloud_marble_bricks"));
		stairsBlock(BlockRegistry.HARDENED_MUD_STAIRS.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/hardened_mud"));
		stairsBlock(BlockRegistry.BURNED_OAK_STAIRS.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/burned_oak_planks"));
		stairsBlock(BlockRegistry.STARDUST_STAIRS.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/stardust_planks"));
		stairsBlock(BlockRegistry.BLOOD_SANDSTONE_STAIRS.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/blood_sandstone"));
		stairsBlock(BlockRegistry.SMOOTH_BLOOD_SANDSTONE_STAIRS.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/blood_sandstone_top"));

		// Do these here because it's required for variants.
		simpleBlock(BlockRegistry.SMOOTH_BLOOD_SANDSTONE.get(), models()
				.cubeAll("smooth_blood_sandstone", ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/blood_sandstone_top")));
		horizontalBlock(BlockRegistry.CHISELED_BLOOD_SANDSTONE.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/chiseled_blood_sandstone"),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/chiseled_blood_sandstone"),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/blood_sandstone_top"));
		horizontalBlock(BlockRegistry.CUT_BLOOD_SANDSTONE.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/cut_blood_sandstone"),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/cut_blood_sandstone"),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/blood_sandstone_top"));

		// Generate data for slab blocks
		slabBlock(BlockRegistry.CLOUD_MARBLE_BRICK_SLAB.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/cloud_marble_bricks"),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/cloud_marble_bricks"));
		slabBlock(BlockRegistry.HARDENED_MUD_SLAB.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/hardened_mud"),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/hardened_mud"));
		slabBlock(BlockRegistry.BURNED_OAK_SLAB.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/burned_oak_planks"),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/burned_oak_planks"));
		slabBlock(BlockRegistry.STARDUST_SLAB.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/stardust_planks"),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/stardust_planks"));
		slabBlock(BlockRegistry.BLOOD_SANDSTONE_SLAB.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/blood_sandstone"),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/blood_sandstone"));
		slabBlock(BlockRegistry.CUT_BLOOD_SANDSTONE_SLAB.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/cut_blood_sandstone"),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/cut_blood_sandstone"),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/blood_sandstone_top"),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/blood_sandstone_top"));
		slabBlock(BlockRegistry.SMOOTH_BLOOD_SANDSTONE_SLAB.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/smooth_blood_sandstone"),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/blood_sandstone_top"));

		// Generate data for fence and fence gate blocks
		fenceBlock(BlockRegistry.BURNED_OAK_FENCE.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/burned_oak_planks"));
		fenceGateBlock(BlockRegistry.BURNED_OAK_FENCE_GATE.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/burned_oak_planks"));
		fourWayBlock(BlockRegistry.BARBED_WIRE_FENCE.get(),
				models().getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/barbed_wire_fence_post")),
				models().getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/barbed_wire_fence_side")));
		fenceBlock(BlockRegistry.STARDUST_FENCE.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/stardust_planks"));
		fenceGateBlock(BlockRegistry.STARDUST_FENCE_GATE.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/stardust_planks"));

		// Generate data for wall blocks
		wallBlock(BlockRegistry.CLOUD_MARBLE_BRICK_WALL.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/cloud_marble_bricks"));
		wallBlock(BlockRegistry.BLOOD_SANDSTONE_WALL.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/blood_sandstone"));

		// Generate data for door and trapdoor blocks
		doorBlockWithRenderType(BlockRegistry.BURNED_OAK_DOOR.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/burned_oak_door_bottom"),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/burned_oak_door_top"),
				"minecraft:cutout_mipped");
		doorBlockWithRenderType(BlockRegistry.STARDUST_DOOR.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/stardust_door_bottom"),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/stardust_door_top"),
				"minecraft:cutout_mipped");
		trapdoorBlockWithRenderType(BlockRegistry.BURNED_OAK_TRAPDOOR.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/burned_oak_trapdoor"), true,
				"minecraft:cutout_mipped");
		trapdoorBlockWithRenderType(BlockRegistry.STARDUST_TRAPDOOR.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/stardust_trapdoor"), true,
				"minecraft:cutout_mipped");

		// Generate data for button blocks
		buttonBlock(BlockRegistry.BURNED_OAK_BUTTON.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/burned_oak_planks"));
		buttonBlock(BlockRegistry.STARDUST_BUTTON.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/stardust_planks"));

		// Generate data for pressure plate blocks
		pressurePlateBlock(BlockRegistry.BURNED_OAK_PRESSURE_PLATE.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/burned_oak_planks"));
		pressurePlateBlock(BlockRegistry.STARDUST_PRESSURE_PLATE.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/stardust_planks"));

		// Generate data for sign blocks
		signBlock(BlockRegistry.BURNED_OAK_SIGN.get(), BlockRegistry.BURNED_OAK_WALL_SIGN.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/burned_oak_planks"));
		hangingSignBlock(BlockRegistry.BURNED_OAK_HANGING_SIGN.get(), BlockRegistry.BURNED_OAK_WALL_HANGING_SIGN.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/stripped_burned_oak_log"));
		signBlock(BlockRegistry.STARDUST_SIGN.get(), BlockRegistry.STARDUST_WALL_SIGN.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/stardust_planks"));
		hangingSignBlock(BlockRegistry.STARDUST_HANGING_SIGN.get(), BlockRegistry.STARDUST_WALL_HANGING_SIGN.get(),
				ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/stripped_stardust_log"));

		// Generate data for skulls
		for (Block block : CustomDataGenerator.ALL_BLOCKS) {
			if (block.builtInRegistryHolder().key().location().getPath().contains("_head") || block.builtInRegistryHolder().key().location().getPath().contains("_wall_head")) {
				getVariantBuilder(block).forAllStates(blockState -> ConfiguredModel.allRotations(
						models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull")),
						true
				));
			}
		}

		// Generate data for cross blocks
		simpleBlock(BlockRegistry.MOONGLOW.get(), models()
				.cross(BuiltInRegistries.BLOCK.getKey(BlockRegistry.MOONGLOW.get()).toString(),
						ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/moonglow"))
				.renderType("minecraft:cutout_mipped"));
		simpleBlock(BlockRegistry.STARDUST_SAPLING.get(), models()
				.cross(BuiltInRegistries.BLOCK.getKey(BlockRegistry.STARDUST_SAPLING.get()).toString(),
						ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/stardust_sapling"))
				.renderType("minecraft:cutout_mipped"));
		simpleBlock(BlockRegistry.DEATHWEED.get(), models()
				.cross(BuiltInRegistries.BLOCK.getKey(BlockRegistry.DEATHWEED.get()).toString(),
						ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/deathweed"))
				.renderType("minecraft:cutout_mipped"));

		// Generate data for miscellaneous blocks not covered above
		horizontalBlock(BlockRegistry.SMALL_PARTS_TABLE.get(), models()
				.cube(BuiltInRegistries.BLOCK.getKey(BlockRegistry.SMALL_PARTS_TABLE.get()).toString(),
						ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/small_parts_table_bottom"),
						ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/small_parts_table_top"),
						ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/small_parts_table_front"),
						ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/small_parts_table_front"),
						ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/small_parts_table_side"),
						ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/small_parts_table_side"))
				.texture("particle", "minecraft:block/copper_block"));
		simpleBlock(BlockRegistry.POTTED_MOONGLOW.get(), models()
				.withExistingParent(BuiltInRegistries.BLOCK.getKey(BlockRegistry.POTTED_MOONGLOW.get()).toString(),
						"minecraft:block/flower_pot_cross")
				.texture("plant", ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/moonglow"))
				.renderType("minecraft:cutout_mipped"));
		simpleBlock(BlockRegistry.POTTED_DEATHWEED.get(), models()
				.withExistingParent(BuiltInRegistries.BLOCK.getKey(BlockRegistry.POTTED_DEATHWEED.get()).toString(),
						"minecraft:block/flower_pot_cross")
				.texture("plant", ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/deathweed"))
				.renderType("minecraft:cutout_mipped"));

		getVariantBuilder(BlockRegistry.BEAR_TRAP.get())
				.partialState().with(BearTrapBlock.TRIGGERED, true).with(BearTrapBlock.VINES, false)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "bear_trap_closed"))))
				.partialState().with(BearTrapBlock.TRIGGERED, false).with(BearTrapBlock.VINES, false)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "bear_trap_open"))))
				.partialState().with(BearTrapBlock.TRIGGERED, true).with(BearTrapBlock.VINES, true)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "bear_trap_closed_vines"))))
				.partialState().with(BearTrapBlock.TRIGGERED, false).with(BearTrapBlock.VINES, true)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "bear_trap_open_vines"))));
		getVariantBuilder(BlockRegistry.LANDMINE.get())
				.partialState()
				.with(LandmineBlock.ARMED, false)
				.with(LandmineBlock.VINES, false)
				.with(LandmineBlock.SAND, false)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "landmine_disarmed"))))

				.partialState()
				.with(LandmineBlock.ARMED, true)
				.with(LandmineBlock.VINES, false)
				.with(LandmineBlock.SAND, false)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "landmine_armed"))))

				.partialState()
				.with(LandmineBlock.ARMED, false)
				.with(LandmineBlock.VINES, true)
				.with(LandmineBlock.SAND, false)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "landmine_disarmed_vines"))))

				.partialState()
				.with(LandmineBlock.ARMED, true)
				.with(LandmineBlock.VINES, true)
				.with(LandmineBlock.SAND, false)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "landmine_armed_vines"))))

				.partialState()
				.with(LandmineBlock.ARMED, false)
				.with(LandmineBlock.VINES, false)
				.with(LandmineBlock.SAND, true)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "landmine_disarmed_sand"))))

				.partialState()
				.with(LandmineBlock.ARMED, true)
				.with(LandmineBlock.VINES, false)
				.with(LandmineBlock.SAND, true)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "landmine_armed_sand"))))

				// These should only ever exist if the player is using commands to place a landmine.
				.partialState()
				.with(LandmineBlock.ARMED, false)
				.with(LandmineBlock.VINES, true)
				.with(LandmineBlock.SAND, true)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "landmine_disarmed"))))

				.partialState()
				.with(LandmineBlock.ARMED, true)
				.with(LandmineBlock.VINES, true)
				.with(LandmineBlock.SAND, true)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "landmine_armed"))));
		getVariantBuilder(BlockRegistry.SPIKE_TRAP.get())
				.partialState().with(SpikeTrapBlock.POWERED, false)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "spike_trap_deactivated"))))
				.partialState().with(SpikeTrapBlock.POWERED, true)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "spike_trap_activated"))));
		getVariantBuilder(BlockRegistry.SANDBAG.get())
				.forAllStates(state -> {
					int bags = state.getValue(SandbagBlock.BAGS);
					return ConfiguredModel.builder()
							.modelFile(models().getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "sandbag_" + bags)))
							.rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
							.build();
				});
		getVariantBuilder(BlockRegistry.SPOTLIGHT.get())
				.forAllStates(state -> {
					boolean lit = state.getValue(SpotlightBlock.LIT);
					ExistingModelFile model = models().getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "spotlight_wall_" + (lit ? "lit" : "unlit")));
					return ConfiguredModel.builder()
							.modelFile(model)
							.rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
							.build();
				});
		getVariantBuilder(BlockRegistry.MORTAR.get())
				.forAllStates(state -> {
					int rotation_level = state.getValue(MortarBlock.ROTATION);
					boolean loaded = state.getValue(MortarBlock.LOADED);

					String path = "mortar_" + rotation_level + "_" + (loaded ? "loaded" : "unloaded");

					return ConfiguredModel.builder()
							.modelFile(models().getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, path)))
							.rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot()))
							.build();
				});
		getVariantBuilder(BlockRegistry.CELESTIAL_LANTERN.get())
				.forAllStates(state -> {
					boolean hanging = state.getValue(CelestialLanternBlock.HANGING);

					String path = "celestial_lantern" + (hanging ? "_hanging" : "");

					return ConfiguredModel.builder()
							.modelFile(models().getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, path)))
							.build();
				});
		getVariantBuilder(BlockRegistry.ASTRAL_CRYSTAL.get())
				.forAllStates(state -> crystalBlock(BlockRegistry.ASTRAL_CRYSTAL, "astral_crystal", state));
		getVariantBuilder(BlockRegistry.STARSTORM_CRYSTAL.get())
				.forAllStates(state -> crystalBlock(BlockRegistry.STARSTORM_CRYSTAL, "starstorm_crystal", state));
		getVariantBuilder(BlockRegistry.WOODEN_SPIKES.get())
				.forAllStates(state -> {
					Direction facing = state.getValue(WoodenSpikesBlock.FACING);
					int stage = state.getValue(WoodenSpikesBlock.DAMAGE_STAGE);

					int yRot;

					switch (facing) {
						case NORTH, SOUTH -> yRot = 180;
						case EAST, WEST -> yRot = 90;
						default -> yRot = 0;
					}

					return ConfiguredModel.builder()
							.modelFile(models().getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
									"wooden_spikes_" + stage)))
							.rotationY(yRot)
							.build();
				});
		getVariantBuilder(BlockRegistry.BARBED_WIRE.get())
				.forAllStates(state -> {
					Direction facing = state.getValue(BarbedWireBlock.FACING);
					int stage = state.getValue(BarbedWireBlock.DAMAGE_STAGE);

					int yRot;

					switch (facing) {
						case NORTH, SOUTH -> yRot = 180;
						case EAST, WEST -> yRot = 90;
						default -> yRot = 0;
					}

					return ConfiguredModel.builder()
							.modelFile(models().getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
									"barbed_wire_" + stage)))
							.rotationY(yRot)
							.build();
				});

		// Star Forge Controller (a furnace-like block with a lit state and horizontal rotation)
		getVariantBuilder(BlockRegistry.STAR_FORGE_CONTROLLER.get())
				.forAllStates(state -> {
					boolean lit = state.getValue(StarForgeControllerBlock.LIT);

					String path = "star_forge_controller" + (lit ? "_lit" : "");

					return ConfiguredModel.builder()
							.modelFile(models().withExistingParent(path, "minecraft:block/orientable")
									.texture("top", ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/star_forge_bricks"))
									.texture("front", ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/star_forge_controller" + (lit ? "_lit" : "")))
									.texture("side", ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/star_forge_bricks"))
							)
							.rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot()))
							.build();
				});

		// Iron panels
		getVariantBuilder(BlockRegistry.IRON_PANEL.get())
				.forAllStates(state -> panelBlock("iron_panel", state));
		getVariantBuilder(BlockRegistry.IRON_PANEL_BARS.get())
				.forAllStates(state -> panelBlock("iron_panel_bars", state));

		horizontalBlock(BlockRegistry.WALL_SHELF.get(),
				models().getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "wall_shelf")), 0);
		horizontalBlock(BlockRegistry.PANIC_ALARM.get(),
				models().getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "panic_alarm")), 0);
		horizontalBlock(BlockRegistry.MINUTEMAN_STATUE.get(),
				models().getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "minuteman_statue")));
		horizontalBlock(BlockRegistry.MEDIC_STATUE.get(),
				models().getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "medic_statue")));
		horizontalBlock(BlockRegistry.HARDENED_MUD_WINDOW.get(),
				models().getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "hardened_mud_window")));
		horizontalBlock(BlockRegistry.CAMP_CHAIR.get(),
				models().getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "camp_chair")), 0);
		horizontalBlock(BlockRegistry.BURNED_OAK_BRANCH.get(),
				models().getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "burned_oak_branch")));
		horizontalBlock(BlockRegistry.BIOHAZARD_BOX.get(),
				models().getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "biohazard_box")), 0);
		horizontalBlock(BlockRegistry.BARREL_TAP.get(),
				models().getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "barrel_tap")), 0);
		simpleBlock(BlockRegistry.PUNJI_STICKS.get(),
				models().getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "punji_sticks")));
		simpleBlock(BlockRegistry.TESLA_SYNTHESIZER.get(),
				models().getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "tesla_synthesizer")));
		horizontalBlock(BlockRegistry.AMMUNITION_TABLE.get(),
				models().getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "ammunition_table")));
		simpleBlock(BlockRegistry.PITFALL.get(),
				models().getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "pitfall")));
		horizontalBlock(BlockRegistry.TESLA_BLOCK.get(),
				models().cubeAll("tesla_block", ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/tesla_block")));
		simpleBlock(BlockRegistry.BIODOME_LIFE_SUPPORT_UNIT.get(),
				models().getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "biodome_life_support_unit")));
		simpleBlock(BlockRegistry.RUSTED_IRON_BLOCK.get(), models().withExistingParent("rusted_iron_block",
						ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "simple_overlay"))
				.texture("all", "minecraft:block/iron_block")
				.texture("overlay", ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/rusted_iron_block_overlay"))
				.renderType("minecraft:translucent"));
		simpleBlock(BlockRegistry.SOLAR_LENS.get(),
				models().getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "solar_lens")));
		simpleBlock(BlockRegistry.COMMANDER_PEDESTAL.get(),
				models().getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "commander_pedestal")));

		// Tiltros Portal and Frame
		getVariantBuilder(BlockRegistry.TILTROS_PORTAL.get())
				.forAllStates(state -> ConfiguredModel.builder()
						.modelFile(models().getBuilder("tiltros_portal")
								.texture("particle", ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/tiltros_portal"))
								.texture("portal", ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/tiltros_portal"))
								.renderType("minecraft:translucent")
								.element()
								.from(0, 6, 0).to(16, 10, 16)
								.face(Direction.UP)
								.uvs(0, 0, 16, 16)
								.texture("#portal")
								.end()
								.face(Direction.DOWN)
								.uvs(0, 0, 16, 16)
								.texture("#portal")
								.end()
								.end()
						)
						.build());
	}

	private ConfiguredModel[] crystalBlock(Supplier<? extends AmethystClusterBlock> crystal, String name, BlockState state) {
		Direction facing = state.getValue(AmethystClusterBlock.FACING);

		int xRot;
		int yRot;

		switch (facing) {
			case NORTH -> {
				xRot = 270;
				yRot = 180;
			}
			case SOUTH -> {
				xRot = 90;
				yRot = 180;
			}
			case EAST -> {
				xRot = 270;
				yRot = 270;
			}
			case WEST -> {
				xRot = 90;
				yRot = 270;
			}
			case DOWN -> {
				xRot = 180;
				yRot = 0;
			}
			default -> {
				xRot = 0;
				yRot = 0;
			}
		}

		return ConfiguredModel.builder()
				.modelFile(models()
						.cross(BuiltInRegistries.BLOCK.getKey(crystal.get()).toString(),
								ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/" + name))
						.renderType("minecraft:cutout_mipped"))
				.rotationY(yRot)
				.rotationX(xRot)
				.build();
	}

	private ConfiguredModel[] panelBlock(String name, BlockState state) {
		Direction facing = state.getValue(PanelBlock.FACING);

		int yRot;
		int xRot = 0;

		switch (facing) {
			case NORTH -> yRot = 180;
			case EAST -> yRot = 270;
			case WEST -> yRot = 90;
			case DOWN -> {
				xRot = 270;
				yRot = 0;
			}
			case UP -> {
				xRot = 90;
				yRot = 180;
			}
			default -> yRot = 0;
		}

		return ConfiguredModel.builder()
				.modelFile(models().getExistingFile(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID,
						name)))
				.rotationY(yRot)
				.rotationX(xRot)
				.build();
	}

	public void hangingSignBlock(CeilingHangingSignBlock signBlock, WallHangingSignBlock wallSignBlock, ResourceLocation texture) {
		ModelFile sign = models().sign(BuiltInRegistries.BLOCK.getKey(signBlock).getPath(), texture);
		hangingSignBlock(signBlock, wallSignBlock, sign);
	}

	public void hangingSignBlock(CeilingHangingSignBlock signBlock, WallHangingSignBlock wallSignBlock, ModelFile sign) {
		simpleBlock(signBlock, sign);
		simpleBlock(wallSignBlock, sign);
	}
}