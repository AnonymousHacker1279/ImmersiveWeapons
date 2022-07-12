package tech.anonymoushacker1279.immersiveweapons.data.models;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.block.base.*;
import tech.anonymoushacker1279.immersiveweapons.block.decoration.CelestialLanternBlock;
import tech.anonymoushacker1279.immersiveweapons.block.decoration.FlagPoleBlock;
import tech.anonymoushacker1279.immersiveweapons.block.misc.warrior_statue.WarriorStatueHead;
import tech.anonymoushacker1279.immersiveweapons.block.misc.warrior_statue.WarriorStatueTorso;
import tech.anonymoushacker1279.immersiveweapons.block.trap.*;
import tech.anonymoushacker1279.immersiveweapons.data.models.lists.BlockLists;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

import java.util.Objects;

public class BlockStateGenerator extends BlockStateProvider {

	public BlockStateGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, ImmersiveWeapons.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		// Generate simple, six-sided blocks
		for (Block block : BlockLists.simpleBlocks) {
			simpleBlock(block);
		}

		// Generate data for simple, six-sided blocks with custom render types
		simpleBlock(DeferredRegistryHandler.BULLETPROOF_GLASS.get(), models().cubeAll("bulletproof_glass",
						new ResourceLocation(ImmersiveWeapons.MOD_ID + ":block/bulletproof_glass"))
				.renderType("minecraft:cutout_mipped"));
		simpleBlock(DeferredRegistryHandler.CLOUD.get(), models().cubeAll("cloud",
						new ResourceLocation(ImmersiveWeapons.MOD_ID + ":block/cloud"))
				.renderType("minecraft:translucent"));
		for (Block block : BlockLists.stainedGlassBlocks) {
			String color = ForgeRegistries.BLOCKS.getKey(block).getPath().split("stained_bulletproof_glass")[0];
			simpleBlock(block, models().cubeAll(color + "stained_bulletproof_glass",
							new ResourceLocation(ImmersiveWeapons.MOD_ID + ":block/" + color + "stained_bulletproof_glass"))
					.renderType("minecraft:translucent"));
		}

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

		// Generate data for tables
		for (Block block : BlockLists.tableBlocks) {
			if (block == DeferredRegistryHandler.BURNED_OAK_TABLE.get()) {
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
		getVariantBuilder(DeferredRegistryHandler.FLAG_POLE.get())
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

		// Generate data for rotatable pillar blocks
		axisBlock(DeferredRegistryHandler.CLOUD_MARBLE_PILLAR.get());
		logBlock(DeferredRegistryHandler.BURNED_OAK_LOG.get());
		axisBlock(DeferredRegistryHandler.BURNED_OAK_WOOD.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/burned_oak_log"),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/burned_oak_log"));
		logBlock(DeferredRegistryHandler.STRIPPED_BURNED_OAK_LOG.get());
		axisBlock(DeferredRegistryHandler.STRIPPED_BURNED_OAK_WOOD.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/stripped_burned_oak_log"),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/stripped_burned_oak_log"));

		// Generate data for stair blocks
		stairsBlock(DeferredRegistryHandler.CLOUD_MARBLE_BRICK_STAIRS.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/cloud_marble_bricks"));
		stairsBlock(DeferredRegistryHandler.HARDENED_MUD_STAIRS.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/hardened_mud"));
		stairsBlock(DeferredRegistryHandler.BURNED_OAK_STAIRS.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/burned_oak_planks"));

		// Generate data for slab blocks
		slabBlock(DeferredRegistryHandler.CLOUD_MARBLE_BRICK_SLAB.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/cloud_marble_bricks"),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/cloud_marble_bricks"));
		slabBlock(DeferredRegistryHandler.HARDENED_MUD_SLAB.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/hardened_mud"),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/hardened_mud"));
		slabBlock(DeferredRegistryHandler.BURNED_OAK_SLAB.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/burned_oak_planks"),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/burned_oak_planks"));

		// Generate data for fence and fence gate blocks
		fenceBlock(DeferredRegistryHandler.BURNED_OAK_FENCE.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/burned_oak_planks"));
		fenceGateBlock(DeferredRegistryHandler.BURNED_OAK_FENCE_GATE.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/burned_oak_planks"));
		fourWayBlock(DeferredRegistryHandler.BARBED_WIRE_FENCE.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/barbed_wire_fence_post")),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/barbed_wire_fence_side")));

		// Generate data for door and trapdoor blocks
		doorBlockWithRenderType(DeferredRegistryHandler.BURNED_OAK_DOOR.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/burned_oak_door_bottom"),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/burned_oak_door_top"),
				"minecraft:cutout_mipped");
		trapdoorBlockWithRenderType(DeferredRegistryHandler.BURNED_OAK_TRAPDOOR.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/burned_oak_trapdoor"), true,
				"minecraft:cutout_mipped");

		// Generate data for button blocks
		buttonBlock(DeferredRegistryHandler.BURNED_OAK_BUTTON.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/burned_oak_planks"));

		// Generate data for pressure plate blocks
		pressurePlateBlock(DeferredRegistryHandler.BURNED_OAK_PRESSURE_PLATE.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/burned_oak_planks"));

		// Generate data for sign blocks
		signBlock(DeferredRegistryHandler.BURNED_OAK_SIGN.get(), DeferredRegistryHandler.BURNED_OAK_WALL_SIGN.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/burned_oak_planks"));

		// Generate data for skulls
		for (Block block : BlockLists.headBlocks) {
			getVariantBuilder(block).forAllStates(blockState -> ConfiguredModel.allRotations(
					models().getExistingFile(new ResourceLocation("block/skull")),
					true
			));
		}

		// Generate data for miscellaneous blocks not covered above
		horizontalBlock(DeferredRegistryHandler.SMALL_PARTS_TABLE.get(), models()
				.cube(DeferredRegistryHandler.SMALL_PARTS_TABLE.getId().toString(),
						new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/small_parts_table_bottom"),
						new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/small_parts_table_top"),
						new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/small_parts_table_front"),
						new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/small_parts_table_front"),
						new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/small_parts_table_side"),
						new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/small_parts_table_side"))
				.texture("particle", "minecraft:block/copper_block"));
		simpleBlock(DeferredRegistryHandler.AZUL_STAINED_ORCHID.get(), models()
				.cross(DeferredRegistryHandler.AZUL_STAINED_ORCHID.getId().toString(),
						new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/azul_stained_orchid"))
				.renderType("minecraft:cutout_mipped"));
		getVariantBuilder(DeferredRegistryHandler.BEAR_TRAP.get())
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
		getVariantBuilder(DeferredRegistryHandler.LANDMINE.get())
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
		getVariantBuilder(DeferredRegistryHandler.SPIKE_TRAP.get())
				.partialState().with(SpikeTrapBlock.POWERED, false)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "spike_trap_deactivated"))))
				.partialState().with(SpikeTrapBlock.POWERED, true)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "spike_trap_activated"))));
		getVariantBuilder(DeferredRegistryHandler.SANDBAG.get())
				.forAllStates(state -> {
					int bags = state.getValue(SandbagBlock.BAGS);
					return ConfiguredModel.builder()
							.modelFile(models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "sandbag_" + bags)))
							.rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
							.build();
				});
		getVariantBuilder(DeferredRegistryHandler.SPOTLIGHT.get())
				.partialState().with(SpotlightBlock.LIT, false)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "spotlight_wall_unlit"))))
				.partialState().with(SpotlightBlock.LIT, true)
				.addModels(new ConfiguredModel(models()
						.getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "spotlight_wall_lit"))));
		getVariantBuilder(DeferredRegistryHandler.MORTAR.get())
				.forAllStates(state -> {
					int rotation_level = state.getValue(MortarBlock.ROTATION);
					boolean loaded = state.getValue(MortarBlock.LOADED);

					String path = "mortar_" + rotation_level + "_" + (loaded ? "loaded" : "unloaded");

					return ConfiguredModel.builder()
							.modelFile(models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, path)))
							.rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot()))
							.build();
				});
		horizontalBlock(DeferredRegistryHandler.WARRIOR_STATUE_BASE.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "warrior_statue_base")), 0);
		getVariantBuilder(DeferredRegistryHandler.WARRIOR_STATUE_TORSO.get())
				.forAllStates(state -> {
					boolean powered = state.getValue(WarriorStatueTorso.POWERED);

					String path = "warrior_statue_torso" + (powered ? "_powered" : "");

					return ConfiguredModel.builder()
							.modelFile(models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, path)))
							.rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot()))
							.build();
				});
		getVariantBuilder(DeferredRegistryHandler.WARRIOR_STATUE_HEAD.get())
				.forAllStates(state -> {
					boolean powered = state.getValue(WarriorStatueHead.POWERED);

					String path = "warrior_statue_head" + (powered ? "_powered" : "");

					return ConfiguredModel.builder()
							.modelFile(models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, path)))
							.rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot()))
							.build();
				});
		getVariantBuilder(DeferredRegistryHandler.CELESTIAL_LANTERN.get())
				.forAllStates(state -> {
					boolean hanging = state.getValue(CelestialLanternBlock.HANGING);

					String path = "celestial_lantern" + (hanging ? "_hanging" : "");

					return ConfiguredModel.builder()
							.modelFile(models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, path)))
							.build();
				});

		horizontalBlock(DeferredRegistryHandler.BARBED_WIRE.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "barbed_wire")));
		horizontalBlock(DeferredRegistryHandler.WOODEN_SPIKES.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "wooden_spikes")));
		horizontalBlock(DeferredRegistryHandler.WALL_SHELF.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "wall_shelf")), 0);
		horizontalBlock(DeferredRegistryHandler.PANIC_ALARM.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "panic_alarm")), 0);
		horizontalBlock(DeferredRegistryHandler.MINUTEMAN_STATUE.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "minuteman_statue")));
		horizontalBlock(DeferredRegistryHandler.MEDIC_STATUE.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "medic_statue")));
		horizontalBlock(DeferredRegistryHandler.HARDENED_MUD_WINDOW.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "hardened_mud_window")));
		horizontalBlock(DeferredRegistryHandler.CORRUGATED_IRON_PANEL.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "corrugated_iron_panel")), 0);
		horizontalBlock(DeferredRegistryHandler.CORRUGATED_IRON_PANEL_BARS.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "corrugated_iron_panel_bars")), 0);
		horizontalBlock(DeferredRegistryHandler.CORRUGATED_IRON_PANEL_FLAT.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "corrugated_iron_panel_flat")));
		horizontalBlock(DeferredRegistryHandler.CORRUGATED_IRON_PANEL_FLAT_BARS.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "corrugated_iron_panel_flat_bars")));
		horizontalBlock(DeferredRegistryHandler.CAMP_CHAIR.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "camp_chair")), 0);
		horizontalBlock(DeferredRegistryHandler.BURNED_OAK_BRANCH.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "burned_oak_branch")));
		horizontalBlock(DeferredRegistryHandler.BIOHAZARD_BOX.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "biohazard_box")), 0);
		horizontalBlock(DeferredRegistryHandler.BARREL_TAP.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "barrel_tap")), 0);
		simpleBlock(DeferredRegistryHandler.PUNJI_STICKS.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "punji_sticks")));
		simpleBlock(DeferredRegistryHandler.TESLA_SYNTHESIZER.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "tesla_synthesizer")));
		simpleBlock(DeferredRegistryHandler.PITFALL.get(),
				models().getExistingFile(new ResourceLocation(ImmersiveWeapons.MOD_ID, "pitfall")));
	}
}