package tech.anonymoushacker1279.immersiveweapons.data.models;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelBuilder.FaceRotation;
import net.minecraftforge.client.model.generators.ModelFile.ExistingModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.block.*;
import tech.anonymoushacker1279.immersiveweapons.block.decoration.CelestialLanternBlock;
import tech.anonymoushacker1279.immersiveweapons.block.decoration.FlagPoleBlock;
import tech.anonymoushacker1279.immersiveweapons.block.misc.warrior_statue.WarriorStatueHead;
import tech.anonymoushacker1279.immersiveweapons.block.misc.warrior_statue.WarriorStatueTorso;
import tech.anonymoushacker1279.immersiveweapons.data.models.lists.BlockLists;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;

import java.util.Objects;

public class BlockStateGenerator extends BlockStateProvider {

	public BlockStateGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, ImmersiveWeapons.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		ResourceLocation stardust_granule_overlay = new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/stardust_granule_overlay");

		// Generate simple, six-sided blocks
		for (Block block : BlockLists.simpleBlocks) {
			simpleBlock(block);
		}

		// Generate data for simple, six-sided blocks with custom render types
		simpleBlock(BlockRegistry.BULLETPROOF_GLASS.get(), models().cubeAll("bulletproof_glass",
						new ResourceLocation(ImmersiveWeapons.MOD_ID + ":block/bulletproof_glass"))
				.renderType("minecraft:cutout_mipped"));
		simpleBlock(BlockRegistry.CLOUD.get(), models().cubeAll("cloud",
						new ResourceLocation(ImmersiveWeapons.MOD_ID + ":block/cloud"))
				.renderType("minecraft:translucent"));
		for (Block block : BlockLists.stainedGlassBlocks) {
			String color = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath().split("stained_bulletproof_glass")[0];
			simpleBlock(block, models().cubeAll(color + "stained_bulletproof_glass",
							new ResourceLocation(ImmersiveWeapons.MOD_ID + ":block/" + color + "stained_bulletproof_glass"))
					.renderType("minecraft:translucent"));
		}
		// Stardust leaves have emissive layers, requiring a separate model
		ResourceLocation stardust_leaves_all = new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/stardust_leaves");
		Block stardust_leaves = BlockRegistry.STARDUST_LEAVES.get();
		simpleBlock(BlockRegistry.STARDUST_LEAVES.get(),
				models().withExistingParent(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(stardust_leaves)).getPath(), "minecraft:block/cube_all")
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
		for (Block block : BlockLists.stoneBasedOres) {
			simpleBlock(block, models().withExistingParent(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath(),
							new ResourceLocation(ImmersiveWeapons.MOD_ID, "simple_overlay"))
					.texture("all", "minecraft:block/stone")
					.texture("overlay", new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/"
							+ Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath()))
					.renderType("minecraft:cutout_mipped"));
		}
		for (Block block : BlockLists.deepslateBasedOres) {
			simpleBlock(block, models().withExistingParent(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath(),
							new ResourceLocation(ImmersiveWeapons.MOD_ID, "simple_overlay"))
					.texture("all", "minecraft:block/deepslate")
					.texture("overlay", new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/"
							+ Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath()
							.replace("deepslate_", "")))
					.renderType("minecraft:cutout_mipped"));
		}
		for (Block block : BlockLists.netherrackBasedOres) {
			simpleBlock(block, models().withExistingParent(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath(),
							new ResourceLocation(ImmersiveWeapons.MOD_ID, "simple_overlay"))
					.texture("all", "minecraft:block/netherrack")
					.texture("overlay", new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/"
							+ Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath()
							.replace("nether_", "")))
					.renderType("minecraft:cutout_mipped"));
		}
		// Astral ore uses smooth quartz for a base
		simpleBlock(BlockRegistry.ASTRAL_ORE.get(), models().withExistingParent(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(BlockRegistry.ASTRAL_ORE.get())).getPath(),
						new ResourceLocation(ImmersiveWeapons.MOD_ID, "simple_overlay"))
				.texture("all", "minecraft:block/quartz_block_bottom")
				.texture("overlay", new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/astral_ore"))
				.renderType("minecraft:cutout_mipped"));

		// Generate data for tables
		for (Block block : BlockLists.tableBlocks) {
			if (block == BlockRegistry.BURNED_OAK_TABLE.get()) {
				simpleBlock(block, models().withExistingParent(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath(),
								new ResourceLocation(ImmersiveWeapons.MOD_ID, "table"))
						.texture("all", ImmersiveWeapons.MOD_ID
								+ ":block/" + Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath()
								.replace("table", "planks"))
						.renderType("minecraft:cutout_mipped"));
			} else {
				simpleBlock(block, models().withExistingParent(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath(),
								new ResourceLocation(ImmersiveWeapons.MOD_ID, "table"))
						.texture("all", "minecraft:block/" + Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath()
								.replace("table", "planks"))
						.renderType("minecraft:cutout_mipped"));
			}
		}

		// Generate data for flags
		getVariantBuilder(BlockRegistry.FLAG_POLE.get())
				.partialState().with(FlagPoleBlock.IS_BASE, true)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "flag_pole_base"))))
				.partialState().with(FlagPoleBlock.IS_BASE, false)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "flag_pole"))));

		for (Block block : BlockLists.flagBlocks) {
			horizontalBlock(block, models().withExistingParent(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath(),
							new ResourceLocation(ImmersiveWeapons.MOD_ID, "flag"))
					.texture("flag", ImmersiveWeapons.MOD_ID +
							":block/" + Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath()));
		}

		// Generate data for cube-bottom-top blocks
		simpleBlock(BlockRegistry.BLOOD_SANDSTONE.get(), models()
				.cubeBottomTop("blood_sandstone",
						new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/blood_sandstone"),
						new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/blood_sandstone_bottom"),
						new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/blood_sandstone_top")));

		// Generate data for rotatable pillar blocks
		axisBlock(BlockRegistry.CLOUD_MARBLE_PILLAR.get());
		logBlock(BlockRegistry.BURNED_OAK_LOG.get());
		axisBlock(BlockRegistry.BURNED_OAK_WOOD.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/burned_oak_log"),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/burned_oak_log"));
		logBlock(BlockRegistry.STRIPPED_BURNED_OAK_LOG.get());
		axisBlock(BlockRegistry.STRIPPED_BURNED_OAK_WOOD.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/stripped_burned_oak_log"),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/stripped_burned_oak_log"));
		logBlock(BlockRegistry.STRIPPED_STARDUST_LOG.get());
		axisBlock(BlockRegistry.STRIPPED_STARDUST_WOOD.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/stripped_stardust_log"),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/stripped_stardust_log"));
		// The stardust log is a bit different as it has emissive texture layers. We need to generate the models for that.
		ResourceLocation stardust_log_side = new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/stardust_log");
		ResourceLocation stardust_log_top = new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/stardust_log_top");
		RotatedPillarBlock stardust_log = BlockRegistry.STARDUST_LOG.get();
		axisBlock(BlockRegistry.STARDUST_LOG.get(),
				models().withExistingParent(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(stardust_log)).getPath(), "minecraft:block/cube_column")
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
				models().withExistingParent(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(stardust_log)).getPath() + "_horizontal", "minecraft:block/cube_column_horizontal")
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
				models().withExistingParent(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(stardust_wood)).getPath(), "minecraft:block/cube_column")
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
				models().withExistingParent(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(stardust_wood)).getPath() + "_horizontal", "minecraft:block/cube_column_horizontal")
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
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/cloud_marble_bricks"));
		stairsBlock(BlockRegistry.HARDENED_MUD_STAIRS.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/hardened_mud"));
		stairsBlock(BlockRegistry.BURNED_OAK_STAIRS.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/burned_oak_planks"));
		stairsBlock(BlockRegistry.STARDUST_STAIRS.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/stardust_planks"));
		stairsBlock(BlockRegistry.BLOOD_SANDSTONE_STAIRS.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/blood_sandstone"));
		stairsBlock(BlockRegistry.SMOOTH_BLOOD_SANDSTONE_STAIRS.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/blood_sandstone_top"));

		// Do these here because it's required for variants.
		simpleBlock(BlockRegistry.SMOOTH_BLOOD_SANDSTONE.get(), models()
				.cubeAll("smooth_blood_sandstone", new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/blood_sandstone_top")));
		horizontalBlock(BlockRegistry.CHISELED_BLOOD_SANDSTONE.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/chiseled_blood_sandstone"),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/chiseled_blood_sandstone"),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/blood_sandstone_top"));
		horizontalBlock(BlockRegistry.CUT_BLOOD_SANDSTONE.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/cut_blood_sandstone"),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/cut_blood_sandstone"),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/blood_sandstone_top"));

		// Generate data for slab blocks
		slabBlock(BlockRegistry.CLOUD_MARBLE_BRICK_SLAB.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/cloud_marble_bricks"),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/cloud_marble_bricks"));
		slabBlock(BlockRegistry.HARDENED_MUD_SLAB.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/hardened_mud"),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/hardened_mud"));
		slabBlock(BlockRegistry.BURNED_OAK_SLAB.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/burned_oak_planks"),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/burned_oak_planks"));
		slabBlock(BlockRegistry.STARDUST_SLAB.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/stardust_planks"),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/stardust_planks"));
		slabBlock(BlockRegistry.BLOOD_SANDSTONE_SLAB.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/blood_sandstone"),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/blood_sandstone"));
		slabBlock(BlockRegistry.CUT_BLOOD_SANDSTONE_SLAB.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/cut_blood_sandstone"),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/cut_blood_sandstone"),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/blood_sandstone_top"),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/blood_sandstone_top"));
		slabBlock(BlockRegistry.SMOOTH_BLOOD_SANDSTONE_SLAB.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/smooth_blood_sandstone"),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/blood_sandstone_top"));

		// Generate data for fence and fence gate blocks
		fenceBlock(BlockRegistry.BURNED_OAK_FENCE.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/burned_oak_planks"));
		fenceGateBlock(BlockRegistry.BURNED_OAK_FENCE_GATE.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/burned_oak_planks"));
		fourWayBlock(BlockRegistry.BARBED_WIRE_FENCE.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/barbed_wire_fence_post")),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/barbed_wire_fence_side")));
		fenceBlock(BlockRegistry.STARDUST_FENCE.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/stardust_planks"));
		fenceGateBlock(BlockRegistry.STARDUST_FENCE_GATE.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/stardust_planks"));

		// Generate data for wall blocks
		wallBlock(BlockRegistry.CLOUD_MARBLE_BRICK_WALL.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/cloud_marble_bricks"));
		wallBlock(BlockRegistry.BLOOD_SANDSTONE_WALL.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/blood_sandstone"));

		// Generate data for door and trapdoor blocks
		doorBlockWithRenderType(BlockRegistry.BURNED_OAK_DOOR.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/burned_oak_door_bottom"),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/burned_oak_door_top"),
				"minecraft:cutout_mipped");
		doorBlockWithRenderType(BlockRegistry.STARDUST_DOOR.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/stardust_door_bottom"),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/stardust_door_top"),
				"minecraft:cutout_mipped");
		trapdoorBlockWithRenderType(BlockRegistry.BURNED_OAK_TRAPDOOR.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/burned_oak_trapdoor"), true,
				"minecraft:cutout_mipped");
		trapdoorBlockWithRenderType(BlockRegistry.STARDUST_TRAPDOOR.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/stardust_trapdoor"), true,
				"minecraft:cutout_mipped");

		// Generate data for button blocks
		buttonBlock(BlockRegistry.BURNED_OAK_BUTTON.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/burned_oak_planks"));
		buttonBlock(BlockRegistry.STARDUST_BUTTON.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/stardust_planks"));

		// Generate data for pressure plate blocks
		pressurePlateBlock(BlockRegistry.BURNED_OAK_PRESSURE_PLATE.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/burned_oak_planks"));
		pressurePlateBlock(BlockRegistry.STARDUST_PRESSURE_PLATE.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/stardust_planks"));

		// Generate data for sign blocks
		signBlock(BlockRegistry.BURNED_OAK_SIGN.get(), BlockRegistry.BURNED_OAK_WALL_SIGN.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/burned_oak_planks"));
		signBlock(BlockRegistry.STARDUST_SIGN.get(), BlockRegistry.STARDUST_WALL_SIGN.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/stardust_planks"));

		// Generate data for skulls
		for (Block block : BlockLists.headBlocks) {
			getVariantBuilder(block).forAllStates(blockState -> ConfiguredModel.allRotations(
					models().getExistingFile(new ResourceLocation("block/skull")),
					true
			));
		}

		// Generate data for cross blocks
		simpleBlock(BlockRegistry.AZUL_STAINED_ORCHID.get(), models()
				.cross(BlockRegistry.AZUL_STAINED_ORCHID.getId().toString(),
						new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/azul_stained_orchid"))
				.renderType("minecraft:cutout_mipped"));
		simpleBlock(BlockRegistry.MOONGLOW.get(), models()
				.cross(BlockRegistry.MOONGLOW.getId().toString(),
						new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/moonglow"))
				.renderType("minecraft:cutout_mipped"));
		simpleBlock(BlockRegistry.STARDUST_SAPLING.get(), models()
				.cross(BlockRegistry.STARDUST_SAPLING.getId().toString(),
						new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/stardust_sapling"))
				.renderType("minecraft:cutout_mipped"));
		simpleBlock(BlockRegistry.DEATHWEED.get(), models()
				.cross(BlockRegistry.DEATHWEED.getId().toString(),
						new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/deathweed"))
				.renderType("minecraft:cutout_mipped"));

		// Generate data for miscellaneous blocks not covered above
		horizontalBlock(BlockRegistry.SMALL_PARTS_TABLE.get(), models()
				.cube(BlockRegistry.SMALL_PARTS_TABLE.getId().toString(),
						new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/small_parts_table_bottom"),
						new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/small_parts_table_top"),
						new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/small_parts_table_front"),
						new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/small_parts_table_front"),
						new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/small_parts_table_side"),
						new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/small_parts_table_side"))
				.texture("particle", "minecraft:block/copper_block"));
		simpleBlock(BlockRegistry.POTTED_MOONGLOW.get(), models()
				.withExistingParent(BlockRegistry.POTTED_MOONGLOW.getId().toString(),
						"minecraft:block/flower_pot_cross")
				.texture("plant", new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/moonglow"))
				.renderType("minecraft:cutout_mipped"));
		simpleBlock(BlockRegistry.POTTED_DEATHWEED.get(), models()
				.withExistingParent(BlockRegistry.POTTED_DEATHWEED.getId().toString(),
						"minecraft:block/flower_pot_cross")
				.texture("plant", new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/deathweed"))
				.renderType("minecraft:cutout_mipped"));

		getVariantBuilder(BlockRegistry.BEAR_TRAP.get())
				.partialState().with(BearTrapBlock.TRIGGERED, true).with(BearTrapBlock.VINES, false)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "bear_trap_closed"))))
				.partialState().with(BearTrapBlock.TRIGGERED, false).with(BearTrapBlock.VINES, false)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "bear_trap_open"))))
				.partialState().with(BearTrapBlock.TRIGGERED, true).with(BearTrapBlock.VINES, true)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "bear_trap_closed_vines"))))
				.partialState().with(BearTrapBlock.TRIGGERED, false).with(BearTrapBlock.VINES, true)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "bear_trap_open_vines"))));
		getVariantBuilder(BlockRegistry.LANDMINE.get())
				.partialState()
				.with(LandmineBlock.ARMED, false)
				.with(LandmineBlock.VINES, false)
				.with(LandmineBlock.SAND, false)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "landmine_disarmed"))))

				.partialState()
				.with(LandmineBlock.ARMED, true)
				.with(LandmineBlock.VINES, false)
				.with(LandmineBlock.SAND, false)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "landmine_armed"))))

				.partialState()
				.with(LandmineBlock.ARMED, false)
				.with(LandmineBlock.VINES, true)
				.with(LandmineBlock.SAND, false)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "landmine_disarmed_vines"))))

				.partialState()
				.with(LandmineBlock.ARMED, true)
				.with(LandmineBlock.VINES, true)
				.with(LandmineBlock.SAND, false)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "landmine_armed_vines"))))

				.partialState()
				.with(LandmineBlock.ARMED, false)
				.with(LandmineBlock.VINES, false)
				.with(LandmineBlock.SAND, true)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "landmine_disarmed_sand"))))

				.partialState()
				.with(LandmineBlock.ARMED, true)
				.with(LandmineBlock.VINES, false)
				.with(LandmineBlock.SAND, true)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "landmine_armed_sand"))))

				// These should only ever exist if the player is using commands to place a landmine.
				.partialState()
				.with(LandmineBlock.ARMED, false)
				.with(LandmineBlock.VINES, true)
				.with(LandmineBlock.SAND, true)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "landmine_disarmed"))))

				.partialState()
				.with(LandmineBlock.ARMED, true)
				.with(LandmineBlock.VINES, true)
				.with(LandmineBlock.SAND, true)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "landmine_armed"))));
		getVariantBuilder(BlockRegistry.SPIKE_TRAP.get())
				.partialState().with(SpikeTrapBlock.POWERED, false)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "spike_trap_deactivated"))))
				.partialState().with(SpikeTrapBlock.POWERED, true)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "spike_trap_activated"))));
		getVariantBuilder(BlockRegistry.SANDBAG.get())
				.forAllStates(state -> {
					int bags = state.getValue(SandbagBlock.BAGS);
					return ConfiguredModel.builder()
							.modelFile(models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "sandbag_" + bags)))
							.rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
							.build();
				});
		getVariantBuilder(BlockRegistry.SPOTLIGHT.get())
				.forAllStates(state -> {
					boolean lit = state.getValue(SpotlightBlock.LIT);
					ExistingModelFile model = models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "spotlight_wall_" + (lit ? "lit" : "unlit")));
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
							.modelFile(models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, path)))
							.rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot()))
							.build();
				});
		horizontalBlock(BlockRegistry.WARRIOR_STATUE_BASE.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "warrior_statue_base")), 0);
		getVariantBuilder(BlockRegistry.WARRIOR_STATUE_TORSO.get())
				.forAllStates(state -> {
					boolean powered = state.getValue(WarriorStatueTorso.POWERED);

					String path = "warrior_statue_torso" + (powered ? "_powered" : "");

					return ConfiguredModel.builder()
							.modelFile(models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, path)))
							.rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot()))
							.build();
				});
		getVariantBuilder(BlockRegistry.WARRIOR_STATUE_HEAD.get())
				.forAllStates(state -> {
					boolean powered = state.getValue(WarriorStatueHead.POWERED);

					String path = "warrior_statue_head" + (powered ? "_powered" : "");

					return ConfiguredModel.builder()
							.modelFile(models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, path)))
							.rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot()))
							.build();
				});
		getVariantBuilder(BlockRegistry.CELESTIAL_LANTERN.get())
				.forAllStates(state -> {
					boolean hanging = state.getValue(CelestialLanternBlock.HANGING);

					String path = "celestial_lantern" + (hanging ? "_hanging" : "");

					return ConfiguredModel.builder()
							.modelFile(models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, path)))
							.build();
				});
		getVariantBuilder(BlockRegistry.ASTRAL_CRYSTAL.get())
				.forAllStates(state -> {
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
									.cross(BlockRegistry.ASTRAL_CRYSTAL.getId().toString(),
											new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/astral_crystal"))
									.renderType("minecraft:cutout_mipped"))
							.rotationY(yRot)
							.rotationX(xRot)
							.build();
				});

		getVariantBuilder(BlockRegistry.STARSTORM_CRYSTAL.get())
				.forAllStates(state -> {
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
									.cross(BlockRegistry.STARSTORM_CRYSTAL.getId().toString(),
											new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/starstorm_crystal"))
									.renderType("minecraft:cutout_mipped"))
							.rotationY(yRot)
							.rotationX(xRot)
							.build();
				});

		horizontalBlock(BlockRegistry.BARBED_WIRE.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "barbed_wire")));
		horizontalBlock(BlockRegistry.WOODEN_SPIKES.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "wooden_spikes")));
		horizontalBlock(BlockRegistry.WALL_SHELF.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "wall_shelf")), 0);
		horizontalBlock(BlockRegistry.PANIC_ALARM.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "panic_alarm")), 0);
		horizontalBlock(BlockRegistry.MINUTEMAN_STATUE.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "minuteman_statue")));
		horizontalBlock(BlockRegistry.MEDIC_STATUE.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "medic_statue")));
		horizontalBlock(BlockRegistry.HARDENED_MUD_WINDOW.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "hardened_mud_window")));
		horizontalBlock(BlockRegistry.CORRUGATED_IRON_PANEL.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "corrugated_iron_panel")), 0);
		horizontalBlock(BlockRegistry.CORRUGATED_IRON_PANEL_BARS.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "corrugated_iron_panel_bars")), 0);
		horizontalBlock(BlockRegistry.CORRUGATED_IRON_PANEL_FLAT.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "corrugated_iron_panel_flat")));
		horizontalBlock(BlockRegistry.CORRUGATED_IRON_PANEL_FLAT_BARS.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "corrugated_iron_panel_flat_bars")));
		horizontalBlock(BlockRegistry.CAMP_CHAIR.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "camp_chair")), 0);
		horizontalBlock(BlockRegistry.BURNED_OAK_BRANCH.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "burned_oak_branch")));
		horizontalBlock(BlockRegistry.BIOHAZARD_BOX.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "biohazard_box")), 0);
		horizontalBlock(BlockRegistry.BARREL_TAP.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "barrel_tap")), 0);
		simpleBlock(BlockRegistry.PUNJI_STICKS.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "punji_sticks")));
		simpleBlock(BlockRegistry.TESLA_SYNTHESIZER.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "tesla_synthesizer")));
		simpleBlock(BlockRegistry.PITFALL.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "pitfall")));
		horizontalBlock(BlockRegistry.TESLA_BLOCK.get(),
				models().cubeAll("tesla_block", new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/tesla_block")));
		simpleBlock(BlockRegistry.BIODOME_LIFE_SUPPORT_UNIT.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "biodome_life_support_unit")));
	}
}