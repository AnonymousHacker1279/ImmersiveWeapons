package tech.anonymoushacker1279.immersiveweapons.data.models;

import net.kyrptonaught.customportalapi.CustomPortalBlock;
import net.minecraft.client.color.item.GrassColorSource;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.model.VariantMutator;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.template.ExtendedModelTemplate;
import net.neoforged.neoforge.client.model.generators.template.ExtendedModelTemplateBuilder;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.block.*;
import tech.anonymoushacker1279.immersiveweapons.block.skull.CustomSkullTypes;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;

public class IWBlockModelGenerator {

	private static final ResourceLocation TEMPLATE_SKULL = ModelLocationUtils.decorateItemModelLocation("template_skull");

	public static void registerModels(BlockModelGenerators blockModels) {
		blockModels.createTrivialCube(BlockRegistry.ASTRAL_ORE.get());
		blockModels.createTrivialCube(BlockRegistry.MOLTEN_ORE.get());
		blockModels.createTrivialCube(BlockRegistry.ELECTRIC_ORE.get());
		blockModels.createTrivialCube(BlockRegistry.COBALT_ORE.get());
		blockModels.createTrivialCube(BlockRegistry.DEEPSLATE_COBALT_ORE.get());
		blockModels.createTrivialCube(BlockRegistry.VENTUS_ORE.get());
		blockModels.createTrivialCube(BlockRegistry.SULFUR_ORE.get());
		blockModels.createTrivialCube(BlockRegistry.DEEPSLATE_SULFUR_ORE.get());
		blockModels.createTrivialCube(BlockRegistry.NETHER_SULFUR_ORE.get());
		blockModels.createTrivialCube(BlockRegistry.RAW_SULFUR_BLOCK.get());
		blockModels.createTrivialCube(BlockRegistry.POTASSIUM_NITRATE_ORE.get());
		blockModels.createTrivialCube(BlockRegistry.MOLTEN_BLOCK.get());
		blockModels.createHorizontallyRotatedBlock(BlockRegistry.TESLA_BLOCK.get(), TexturedModel.CUBE);
		blockModels.createTrivialCube(BlockRegistry.ASTRAL_BLOCK.get());
		blockModels.createTrivialCube(BlockRegistry.STARSTORM_BLOCK.get());
		blockModels.createTrivialCube(BlockRegistry.COBALT_BLOCK.get());
		blockModels.createTrivialCube(BlockRegistry.RAW_COBALT_BLOCK.get());
		blockModels.createTrivialCube(BlockRegistry.RUSTED_IRON_BLOCK.get());
		blockModels.createHorizontallyRotatedBlock(BlockRegistry.SMALL_PARTS_TABLE.get(), TexturedModel.ORIENTABLE);
		generateHorizontalStateOnly(blockModels, BlockRegistry.AMMUNITION_TABLE.get(), false);
		generateHorizontalStateOnly(blockModels, BlockRegistry.BARREL_TAP.get(), true);
		blockModels.createFurnace(BlockRegistry.STAR_FORGE_CONTROLLER.get(), TexturedModel.ORIENTABLE_ONLY_TOP.updateTexture(mapping -> {
			mapping.put(TextureSlot.SIDE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/star_forge_bricks"));
			mapping.put(TextureSlot.TOP, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/star_forge_bricks"));
		}));
		blockModels.createTrivialCube(BlockRegistry.STAR_FORGE_BRICKS.get());
		generateHorizontalStateOnly(blockModels, BlockRegistry.CELESTIAL_ALTAR.get(), false);
		generateStateOnly(blockModels, BlockRegistry.SOLAR_LENS.get());
		createGlassBlocks(blockModels, BlockRegistry.BULLETPROOF_GLASS.get(), BlockRegistry.BULLETPROOF_GLASS_PANE.get());
		createGlassBlocks(blockModels, BlockRegistry.WHITE_STAINED_BULLETPROOF_GLASS.get(), BlockRegistry.WHITE_STAINED_BULLETPROOF_GLASS_PANE.get());
		createGlassBlocks(blockModels, BlockRegistry.LIGHT_GRAY_STAINED_BULLETPROOF_GLASS.get(), BlockRegistry.LIGHT_GRAY_STAINED_BULLETPROOF_GLASS_PANE.get());
		createGlassBlocks(blockModels, BlockRegistry.GRAY_STAINED_BULLETPROOF_GLASS.get(), BlockRegistry.GRAY_STAINED_BULLETPROOF_GLASS_PANE.get());
		createGlassBlocks(blockModels, BlockRegistry.BLACK_STAINED_BULLETPROOF_GLASS.get(), BlockRegistry.BLACK_STAINED_BULLETPROOF_GLASS_PANE.get());
		createGlassBlocks(blockModels, BlockRegistry.ORANGE_STAINED_BULLETPROOF_GLASS.get(), BlockRegistry.ORANGE_STAINED_BULLETPROOF_GLASS_PANE.get());
		createGlassBlocks(blockModels, BlockRegistry.MAGENTA_STAINED_BULLETPROOF_GLASS.get(), BlockRegistry.MAGENTA_STAINED_BULLETPROOF_GLASS_PANE.get());
		createGlassBlocks(blockModels, BlockRegistry.LIGHT_BLUE_STAINED_BULLETPROOF_GLASS.get(), BlockRegistry.LIGHT_BLUE_STAINED_BULLETPROOF_GLASS_PANE.get());
		createGlassBlocks(blockModels, BlockRegistry.YELLOW_STAINED_BULLETPROOF_GLASS.get(), BlockRegistry.YELLOW_STAINED_BULLETPROOF_GLASS_PANE.get());
		createGlassBlocks(blockModels, BlockRegistry.LIME_STAINED_BULLETPROOF_GLASS.get(), BlockRegistry.LIME_STAINED_BULLETPROOF_GLASS_PANE.get());
		createGlassBlocks(blockModels, BlockRegistry.PINK_STAINED_BULLETPROOF_GLASS.get(), BlockRegistry.PINK_STAINED_BULLETPROOF_GLASS_PANE.get());
		createGlassBlocks(blockModels, BlockRegistry.CYAN_STAINED_BULLETPROOF_GLASS.get(), BlockRegistry.CYAN_STAINED_BULLETPROOF_GLASS_PANE.get());
		createGlassBlocks(blockModels, BlockRegistry.PURPLE_STAINED_BULLETPROOF_GLASS.get(), BlockRegistry.PURPLE_STAINED_BULLETPROOF_GLASS_PANE.get());
		createGlassBlocks(blockModels, BlockRegistry.BLUE_STAINED_BULLETPROOF_GLASS.get(), BlockRegistry.BLUE_STAINED_BULLETPROOF_GLASS_PANE.get());
		createGlassBlocks(blockModels, BlockRegistry.BROWN_STAINED_BULLETPROOF_GLASS.get(), BlockRegistry.BROWN_STAINED_BULLETPROOF_GLASS_PANE.get());
		createGlassBlocks(blockModels, BlockRegistry.GREEN_STAINED_BULLETPROOF_GLASS.get(), BlockRegistry.GREEN_STAINED_BULLETPROOF_GLASS_PANE.get());
		createGlassBlocks(blockModels, BlockRegistry.RED_STAINED_BULLETPROOF_GLASS.get(), BlockRegistry.RED_STAINED_BULLETPROOF_GLASS_PANE.get());
		generateStateOnly(blockModels, BlockRegistry.PUNJI_STICKS.get());
		generateTintedStateOnly(blockModels, BlockRegistry.PITFALL.get(), new GrassColorSource());
		generateBearTrap(blockModels, BlockRegistry.BEAR_TRAP.get());
		generateBarbedWire(blockModels, BlockRegistry.BARBED_WIRE.get());
		generateLandmine(blockModels, BlockRegistry.LANDMINE.get());
		generateSpikeTrap(blockModels, BlockRegistry.SPIKE_TRAP.get());
		generateSandbag(blockModels, BlockRegistry.SANDBAG.get());
		generateOrientableStateOnly(blockModels, BlockRegistry.IRON_PANEL.get());
		generateOrientableStateOnly(blockModels, BlockRegistry.IRON_PANEL_BARS.get());
		generateSpotlight(blockModels, BlockRegistry.SPOTLIGHT.get());
		generateMortar(blockModels, BlockRegistry.MORTAR.get());
		generateHorizontalStateOnly(blockModels, BlockRegistry.WALL_SHELF.get(), true);
		generateHorizontalStateOnly(blockModels, BlockRegistry.PANIC_ALARM.get(), true);
		generateTable(blockModels, BlockRegistry.OAK_TABLE.get(), Blocks.OAK_PLANKS);
		generateTable(blockModels, BlockRegistry.SPRUCE_TABLE.get(), Blocks.SPRUCE_PLANKS);
		generateTable(blockModels, BlockRegistry.BIRCH_TABLE.get(), Blocks.BIRCH_PLANKS);
		generateTable(blockModels, BlockRegistry.JUNGLE_TABLE.get(), Blocks.JUNGLE_PLANKS);
		generateTable(blockModels, BlockRegistry.ACACIA_TABLE.get(), Blocks.ACACIA_PLANKS);
		generateTable(blockModels, BlockRegistry.DARK_OAK_TABLE.get(), Blocks.DARK_OAK_PLANKS);
		generateTable(blockModels, BlockRegistry.CRIMSON_TABLE.get(), Blocks.CRIMSON_PLANKS);
		generateTable(blockModels, BlockRegistry.WARPED_TABLE.get(), Blocks.WARPED_PLANKS);
		generateTable(blockModels, BlockRegistry.MANGROVE_TABLE.get(), Blocks.MANGROVE_PLANKS);
		generateTable(blockModels, BlockRegistry.CHERRY_TABLE.get(), Blocks.CHERRY_PLANKS);
		generateTable(blockModels, BlockRegistry.BAMBOO_TABLE.get(), Blocks.BAMBOO_PLANKS);
		generateTable(blockModels, BlockRegistry.BURNED_OAK_TABLE.get(), BlockRegistry.BURNED_OAK_PLANKS.get());
		generateTable(blockModels, BlockRegistry.STARDUST_TABLE.get(), BlockRegistry.STARDUST_PLANKS.get());
		generateHorizontalStateOnly(blockModels, BlockRegistry.CAMP_CHAIR.get(), false);
		generateFence(blockModels, BlockRegistry.BARBED_WIRE_FENCE.get());
		generateWoodenSpikes(blockModels, BlockRegistry.WOODEN_SPIKES.get());
		generateHorizontalStateOnly(blockModels, BlockRegistry.BIOHAZARD_BOX.get(), true);
		generateHorizontalStateOnly(blockModels, BlockRegistry.MINUTEMAN_STATUE.get(), false);
		generateHorizontalStateOnly(blockModels, BlockRegistry.MEDIC_STATUE.get(), false);
		generateStateOnly(blockModels, BlockRegistry.TESLA_SYNTHESIZER.get());
		generateStateOnly(blockModels, BlockRegistry.TELEPORTER.get());
		blockModels.createTrivialBlock(BlockRegistry.CLOUD.get(), TexturedModel.CUBE.updateTemplate(modifier -> modifier.extend().renderType("translucent").build()));
		blockModels.createTrivialCube(BlockRegistry.CLOUD_MARBLE.get());
		blockModels.createTrivialCube(BlockRegistry.CLOUD_MARBLE_BRICKS.get());
		blockModels.createAxisAlignedPillarBlock(BlockRegistry.CLOUD_MARBLE_PILLAR.get(), TexturedModel.COLUMN);
		generateStairs(blockModels, BlockRegistry.CLOUD_MARBLE_BRICK_STAIRS.get(), BlockRegistry.CLOUD_MARBLE_BRICKS.get());
		generateSlab(blockModels, BlockRegistry.CLOUD_MARBLE_BRICK_SLAB.get(), BlockRegistry.CLOUD_MARBLE_BRICKS.get(), false);
		generateWall(blockModels, BlockRegistry.CLOUD_MARBLE_BRICK_WALL.get(), BlockRegistry.CLOUD_MARBLE_BRICKS.get());
		generateWood(blockModels, BlockRegistry.BURNED_OAK_WOOD.get(), BlockRegistry.BURNED_OAK_LOG.get());
		generateWoodLog(blockModels, BlockRegistry.BURNED_OAK_LOG.get());
		generateWood(blockModels, BlockRegistry.STRIPPED_BURNED_OAK_WOOD.get(), BlockRegistry.STRIPPED_BURNED_OAK_LOG.get());
		generateWoodLog(blockModels, BlockRegistry.STRIPPED_BURNED_OAK_LOG.get());
		blockModels.createTrivialCube(BlockRegistry.BURNED_OAK_PLANKS.get());
		generateStairs(blockModels, BlockRegistry.BURNED_OAK_STAIRS.get(), BlockRegistry.BURNED_OAK_PLANKS.get());
		generateSlab(blockModels, BlockRegistry.BURNED_OAK_SLAB.get(), BlockRegistry.BURNED_OAK_PLANKS.get(), false);
		generateFence(blockModels, BlockRegistry.BURNED_OAK_FENCE.get(), BlockRegistry.BURNED_OAK_PLANKS.get());
		generateFenceGate(blockModels, BlockRegistry.BURNED_OAK_FENCE_GATE.get(), BlockRegistry.BURNED_OAK_PLANKS.get());
		generateHorizontalStateOnly(blockModels, BlockRegistry.BURNED_OAK_BRANCH.get(), false);
		generateDoor(blockModels, BlockRegistry.BURNED_OAK_DOOR.get());
		generateTrapdoor(blockModels, BlockRegistry.BURNED_OAK_TRAPDOOR.get());
		generatePressurePlate(blockModels, BlockRegistry.BURNED_OAK_PRESSURE_PLATE.get(), BlockRegistry.BURNED_OAK_PLANKS.get());
		generateSign(blockModels, BlockRegistry.BURNED_OAK_SIGN.get(), BlockRegistry.BURNED_OAK_WALL_SIGN.get(), BlockRegistry.BURNED_OAK_PLANKS.get());
		generateHangingSign(blockModels, BlockRegistry.BURNED_OAK_HANGING_SIGN.get(), BlockRegistry.BURNED_OAK_WALL_HANGING_SIGN.get(), BlockRegistry.BURNED_OAK_PLANKS.get());
		generateButton(blockModels, BlockRegistry.BURNED_OAK_BUTTON.get(), BlockRegistry.BURNED_OAK_PLANKS.get());
		generateStateOnly(blockModels, BlockRegistry.COMMANDER_PEDESTAL.get());
		generateFlagPole(blockModels, BlockRegistry.FLAG_POLE.get());
		generateFlag(blockModels, BlockRegistry.AMERICAN_FLAG.get());
		generateFlag(blockModels, BlockRegistry.GADSDEN_FLAG.get());
		generateFlag(blockModels, BlockRegistry.CANADIAN_FLAG.get());
		generateFlag(blockModels, BlockRegistry.MEXICAN_FLAG.get());
		generateFlag(blockModels, BlockRegistry.BRITISH_FLAG.get());
		generateFlag(blockModels, BlockRegistry.TROLL_FLAG.get());
		generateFlag(blockModels, BlockRegistry.IMMERSIVE_WEAPONS_FLAG.get());
		blockModels.createTrivialCube(BlockRegistry.MUD.get());
		blockModels.createTrivialCube(BlockRegistry.DRIED_MUD.get());
		blockModels.createTrivialCube(BlockRegistry.HARDENED_MUD.get());
		generateStairs(blockModels, BlockRegistry.HARDENED_MUD_STAIRS.get(), BlockRegistry.HARDENED_MUD.get());
		generateSlab(blockModels, BlockRegistry.HARDENED_MUD_SLAB.get(), BlockRegistry.HARDENED_MUD.get(), false);
		generateHorizontalStateOnly(blockModels, BlockRegistry.HARDENED_MUD_WINDOW.get(), false);
		blockModels.createTrivialCube(BlockRegistry.TILTROS_PORTAL_FRAME.get());
		generateStateOnly(blockModels, BlockRegistry.CELESTIAL_LANTERN.get());
		blockModels.createHead(BlockRegistry.MINUTEMAN_HEAD.get(), BlockRegistry.MINUTEMAN_WALL_HEAD.get(), CustomSkullTypes.MINUTEMAN, TEMPLATE_SKULL);
		blockModels.createHead(BlockRegistry.FIELD_MEDIC_HEAD.get(), BlockRegistry.FIELD_MEDIC_WALL_HEAD.get(), CustomSkullTypes.FIELD_MEDIC, TEMPLATE_SKULL);
		blockModels.createHead(BlockRegistry.DYING_SOLDIER_HEAD.get(), BlockRegistry.DYING_SOLDIER_WALL_HEAD.get(), CustomSkullTypes.DYING_SOLDIER, TEMPLATE_SKULL);
		blockModels.createHead(BlockRegistry.THE_COMMANDER_HEAD.get(), BlockRegistry.THE_COMMANDER_WALL_HEAD.get(), CustomSkullTypes.THE_COMMANDER, TEMPLATE_SKULL);
		blockModels.createHead(BlockRegistry.WANDERING_WARRIOR_HEAD.get(), BlockRegistry.WANDERING_WARRIOR_WALL_HEAD.get(), CustomSkullTypes.WANDERING_WARRIOR, TEMPLATE_SKULL);
		blockModels.createHead(BlockRegistry.HANS_HEAD.get(), BlockRegistry.HANS_WALL_HEAD.get(), CustomSkullTypes.HANS, TEMPLATE_SKULL);
		blockModels.createHead(BlockRegistry.STORM_CREEPER_HEAD.get(), BlockRegistry.STORM_CREEPER_WALL_HEAD.get(), CustomSkullTypes.STORM_CREEPER, TEMPLATE_SKULL);
		blockModels.createHead(BlockRegistry.SKELETON_MERCHANT_HEAD.get(), BlockRegistry.SKELETON_MERCHANT_WALL_HEAD.get(), CustomSkullTypes.SKELETON_MERCHANT, TEMPLATE_SKULL);
		generatePlant(blockModels, BlockRegistry.MOONGLOW.get(), BlockRegistry.POTTED_MOONGLOW.get());
		generateStardustWood(blockModels, BlockRegistry.STARDUST_WOOD.get());
		generateStardustLog(blockModels, BlockRegistry.STARDUST_LOG.get());
		generateWood(blockModels, BlockRegistry.STRIPPED_STARDUST_WOOD.get(), BlockRegistry.STRIPPED_STARDUST_LOG.get());
		generateWoodLog(blockModels, BlockRegistry.STRIPPED_STARDUST_LOG.get());
		blockModels.createTrivialCube(BlockRegistry.STARDUST_PLANKS.get());
		generateStairs(blockModels, BlockRegistry.STARDUST_STAIRS.get(), BlockRegistry.STARDUST_PLANKS.get());
		generateSlab(blockModels, BlockRegistry.STARDUST_SLAB.get(), BlockRegistry.STARDUST_PLANKS.get(), false);
		generateFence(blockModels, BlockRegistry.STARDUST_FENCE.get(), BlockRegistry.STARDUST_PLANKS.get());
		generateFenceGate(blockModels, BlockRegistry.STARDUST_FENCE_GATE.get(), BlockRegistry.STARDUST_PLANKS.get());
		generateDoor(blockModels, BlockRegistry.STARDUST_DOOR.get());
		generateTrapdoor(blockModels, BlockRegistry.STARDUST_TRAPDOOR.get());
		generatePressurePlate(blockModels, BlockRegistry.STARDUST_PRESSURE_PLATE.get(), BlockRegistry.STARDUST_PLANKS.get());
		generateSign(blockModels, BlockRegistry.STARDUST_SIGN.get(), BlockRegistry.STARDUST_WALL_SIGN.get(), BlockRegistry.STARDUST_PLANKS.get());
		generateHangingSign(blockModels, BlockRegistry.STARDUST_HANGING_SIGN.get(), BlockRegistry.STARDUST_WALL_HANGING_SIGN.get(), BlockRegistry.STARDUST_PLANKS.get());
		generateButton(blockModels, BlockRegistry.STARDUST_BUTTON.get(), BlockRegistry.STARDUST_PLANKS.get());
		generateStardustLeaves(blockModels, BlockRegistry.STARDUST_LEAVES.get());
		generatePlant(blockModels, BlockRegistry.STARDUST_SAPLING.get());
		blockModels.createTrivialCube(BlockRegistry.BLOOD_SAND.get());
		blockModels.createTrivialBlock(BlockRegistry.BLOOD_SANDSTONE.get(), TexturedModel.TOP_BOTTOM_WITH_WALL);
		generateSlab(blockModels, BlockRegistry.BLOOD_SANDSTONE_SLAB.get(), BlockRegistry.BLOOD_SANDSTONE.get(), true);
		generateStairs(blockModels, BlockRegistry.BLOOD_SANDSTONE_STAIRS.get(), BlockRegistry.BLOOD_SANDSTONE.get());
		generateWall(blockModels, BlockRegistry.BLOOD_SANDSTONE_WALL.get(), BlockRegistry.BLOOD_SANDSTONE.get());
		blockModels.createTrivialBlock(BlockRegistry.CHISELED_BLOOD_SANDSTONE.get(), TexturedModel.TOP_BOTTOM_WITH_WALL.updateTexture(mapping -> mapping
				.put(TextureSlot.TOP, TextureMapping.getBlockTexture(BlockRegistry.BLOOD_SANDSTONE.get(), "_top"))
				.put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(BlockRegistry.BLOOD_SANDSTONE.get(), "_top"))));
		blockModels.createTrivialBlock(BlockRegistry.CUT_BLOOD_SANDSTONE.get(), TexturedModel.TOP_BOTTOM_WITH_WALL.updateTexture(mapping -> mapping
				.put(TextureSlot.TOP, TextureMapping.getBlockTexture(BlockRegistry.BLOOD_SANDSTONE.get(), "_top"))
				.put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(BlockRegistry.BLOOD_SANDSTONE.get(), "_top"))));
		generateSlab(blockModels, BlockRegistry.CUT_BLOOD_SANDSTONE_SLAB.get(), BlockRegistry.CUT_BLOOD_SANDSTONE.get(),
				TextureMapping.getBlockTexture(BlockRegistry.BLOOD_SANDSTONE.get()),
				TextureMapping.getBlockTexture(BlockRegistry.BLOOD_SANDSTONE.get(), "_top"),
				TextureMapping.getBlockTexture(BlockRegistry.BLOOD_SANDSTONE.get(), "_top"));
		blockModels.createTrivialBlock(BlockRegistry.SMOOTH_BLOOD_SANDSTONE.get(), TexturedModel.CUBE.updateTexture(mapping ->
				mapping.put(TextureSlot.ALL, TextureMapping.getBlockTexture(BlockRegistry.BLOOD_SANDSTONE.get(), "_top"))));
		generateSlab(blockModels, BlockRegistry.SMOOTH_BLOOD_SANDSTONE_SLAB.get(), BlockRegistry.SMOOTH_BLOOD_SANDSTONE.get(),
				TextureMapping.getBlockTexture(BlockRegistry.BLOOD_SANDSTONE.get(), "_top"),
				TextureMapping.getBlockTexture(BlockRegistry.BLOOD_SANDSTONE.get(), "_top"),
				TextureMapping.getBlockTexture(BlockRegistry.BLOOD_SANDSTONE.get(), "_top"));
		generateStairs(blockModels, BlockRegistry.SMOOTH_BLOOD_SANDSTONE_STAIRS.get(),
				TextureMapping.getBlockTexture(BlockRegistry.BLOOD_SANDSTONE.get(), "_top"));
		generatePlant(blockModels, BlockRegistry.DEATHWEED.get(), BlockRegistry.POTTED_DEATHWEED.get());
		generateCrystal(blockModels, BlockRegistry.ASTRAL_CRYSTAL.get());
		generateCrystal(blockModels, BlockRegistry.STARSTORM_CRYSTAL.get());
		generateStateOnly(blockModels, BlockRegistry.BIODOME_LIFE_SUPPORT_UNIT.get());
		blockModels.createTrivialCube(BlockRegistry.CHAMPION_BRICKS.get());
		blockModels.createTrivialCube(BlockRegistry.CHAMPION_BASE.get());
		blockModels.createTrivialCube(BlockRegistry.CHAMPION_KEYCARD_BRICKS.get());
		blockModels.createTrivialCube(BlockRegistry.VOID_ORE.get());
		generateTiltrosPortal(blockModels, BlockRegistry.TILTROS_PORTAL.get());
	}

	/**
	 * A close copy of the vanilla generator in {@link BlockModelGenerators#createGlassBlocks(Block, Block)}, but with a
	 * translucent render type specified.
	 *
	 * @param models     the block model generator
	 * @param glassBlock the glass block
	 * @param paneBlock  the glass pane block
	 */
	private static void createGlassBlocks(BlockModelGenerators models, Block glassBlock, Block paneBlock) {
		models.createTrivialBlock(glassBlock, TexturedModel.CUBE.updateTemplate(modifier -> modifier.extend().renderType("translucent").build()));
		TextureMapping paneMapping = TextureMapping.pane(glassBlock, paneBlock);
		MultiVariant panePost = BlockModelGenerators.plainVariant(ModelTemplates.STAINED_GLASS_PANE_POST.extend().renderType("translucent").build().create(paneBlock, paneMapping, models.modelOutput));
		MultiVariant paneSide = BlockModelGenerators.plainVariant(ModelTemplates.STAINED_GLASS_PANE_SIDE.extend().renderType("translucent").build().create(paneBlock, paneMapping, models.modelOutput));
		MultiVariant paneSideAlt = BlockModelGenerators.plainVariant(ModelTemplates.STAINED_GLASS_PANE_SIDE_ALT.extend().renderType("translucent").build().create(paneBlock, paneMapping, models.modelOutput));
		MultiVariant paneNoSide = BlockModelGenerators.plainVariant(ModelTemplates.STAINED_GLASS_PANE_NOSIDE.extend().renderType("translucent").build().create(paneBlock, paneMapping, models.modelOutput));
		MultiVariant paneNoSideAlt = BlockModelGenerators.plainVariant(ModelTemplates.STAINED_GLASS_PANE_NOSIDE_ALT.extend().renderType("translucent").build().create(paneBlock, paneMapping, models.modelOutput));
		Item paneItem = paneBlock.asItem();
		models.registerSimpleItemModel(paneItem, models.createFlatItemModelWithBlockTexture(paneItem, glassBlock));
		models.blockStateOutput.accept(
				MultiPartGenerator.multiPart(paneBlock)
						.with(panePost)
						.with(BlockModelGenerators.condition().term(BlockStateProperties.NORTH, true), paneSide)
						.with(BlockModelGenerators.condition().term(BlockStateProperties.EAST, true), paneSide.with(BlockModelGenerators.Y_ROT_90))
						.with(BlockModelGenerators.condition().term(BlockStateProperties.SOUTH, true), paneSideAlt)
						.with(BlockModelGenerators.condition().term(BlockStateProperties.WEST, true), paneSideAlt.with(BlockModelGenerators.Y_ROT_90))
						.with(BlockModelGenerators.condition().term(BlockStateProperties.NORTH, false), paneNoSide)
						.with(BlockModelGenerators.condition().term(BlockStateProperties.EAST, false), paneNoSideAlt)
						.with(BlockModelGenerators.condition().term(BlockStateProperties.SOUTH, false), paneNoSideAlt.with(BlockModelGenerators.Y_ROT_90))
						.with(BlockModelGenerators.condition().term(BlockStateProperties.WEST, false), paneNoSide.with(BlockModelGenerators.Y_ROT_270))
		);
	}

	/**
	 * Generate a horizontally rotated blockstate for a pre-existing model.
	 *
	 * @param models  the block model generator
	 * @param block   the block to generate the state for
	 * @param flipped whether to flip the model 180 degrees
	 */
	private static void generateHorizontalStateOnly(BlockModelGenerators models, Block block, boolean flipped) {
		MultiVariant variant = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(block));
		PropertyDispatch<VariantMutator> rotationMutator = flipped
				? BlockModelGenerators.ROTATION_HORIZONTAL_FACING_ALT
				: BlockModelGenerators.ROTATION_HORIZONTAL_FACING;
		models.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, variant).with(rotationMutator));
	}

	/**
	 * Generate a blockstate orientable in all directions for a pre-existing model.
	 *
	 * @param models the block model generator
	 * @param block  the block to generate the state for
	 */
	private static void generateOrientableStateOnly(BlockModelGenerators models, Block block) {
		MultiVariant variant = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(block));
		models.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, variant).with(BlockModelGenerators.ROTATION_FACING));
	}

	/**
	 * Generate a basic blockstate for a pre-existing model.
	 *
	 * @param models the block model generator
	 * @param block  the block to generate the state for
	 */
	private static void generateStateOnly(BlockModelGenerators models, Block block) {
		MultiVariant variant = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(block));
		models.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, variant));
	}

	/**
	 * Generate a basic blockstate for a pre-existing model with a tint source.
	 *
	 * @param models     the block model generator
	 * @param block      the block to generate the state for
	 * @param tintSource the tint source for the item model
	 */
	private static void generateTintedStateOnly(BlockModelGenerators models, Block block, ItemTintSource tintSource) {
		MultiVariant variant = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(block));
		models.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, variant));
		models.registerSimpleTintedItemModel(block, ModelLocationUtils.getModelLocation(block), tintSource);
	}

	/**
	 * Generate a bear trap type blockstate with a pre-existing model. Bear traps have multiple states:
	 * {@link BearTrapBlock#TRIGGERED} and {@link BearTrapBlock#VINES}.
	 *
	 * @param models the block model generator
	 * @param block  the block to generate the state for
	 */
	private static void generateBearTrap(BlockModelGenerators models, Block block) {
		ResourceLocation openPath = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/bear_trap_open");
		MultiVariant open = BlockModelGenerators.plainVariant(openPath);
		MultiVariant closed = BlockModelGenerators.plainVariant(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/bear_trap_closed"));
		MultiVariant openVines = BlockModelGenerators.plainVariant(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/bear_trap_open_vines"));
		MultiVariant closedVines = BlockModelGenerators.plainVariant(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/bear_trap_closed_vines"));

		models.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
				.with(PropertyDispatch.initial(BearTrapBlock.TRIGGERED, BearTrapBlock.VINES)
						.select(true, false, closed)
						.select(false, false, open)
						.select(true, true, closedVines)
						.select(false, true, openVines))
		);
		models.registerSimpleItemModel(block, openPath);
	}

	/**
	 * Generate a barbed wire type blockstate with a pre-existing model. Barbed wire has multiple states:
	 * {@link BarbedWireBlock#DAMAGE_STAGE}, and then an inherited horizontal state.
	 *
	 * @param models the block model generator
	 * @param block  the block to generate the state for
	 */
	private static void generateBarbedWire(BlockModelGenerators models, Block block) {
		ResourceLocation damageStagePath0 = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/barbed_wire_0");
		MultiVariant damageStage0 = BlockModelGenerators.plainVariant(damageStagePath0);
		MultiVariant damageStage1 = BlockModelGenerators.plainVariant(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/barbed_wire_1"));
		MultiVariant damageStage2 = BlockModelGenerators.plainVariant(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/barbed_wire_2"));

		models.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
				.with(PropertyDispatch.initial(BarbedWireBlock.DAMAGE_STAGE)
						.select(0, damageStage0)
						.select(1, damageStage1)
						.select(2, damageStage2))
				.with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING)
		);
		models.registerSimpleItemModel(block, damageStagePath0);
	}

	/**
	 * Generate a landmine type blockstate with a pre-existing model. Landmines have multiple states:
	 * {@link LandmineBlock#ARMED}, {@link LandmineBlock#SAND}, and {@link LandmineBlock#VINES}.
	 *
	 * @param models the block model generator
	 * @param block  the block to generate the state for
	 */
	private static void generateLandmine(BlockModelGenerators models, Block block) {
		ResourceLocation disarmedPath = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/landmine_disarmed");
		MultiVariant disarmed = BlockModelGenerators.plainVariant(disarmedPath);
		MultiVariant armed = BlockModelGenerators.plainVariant(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/landmine_armed"));
		MultiVariant armedVines = BlockModelGenerators.plainVariant(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/landmine_armed_vines"));
		MultiVariant disarmedVines = BlockModelGenerators.plainVariant(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/landmine_disarmed_vines"));
		MultiVariant armedSand = BlockModelGenerators.plainVariant(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/landmine_armed_sand"));
		MultiVariant disarmedSand = BlockModelGenerators.plainVariant(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/landmine_disarmed_sand"));

		models.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
				.with(PropertyDispatch.initial(LandmineBlock.ARMED, LandmineBlock.SAND, LandmineBlock.VINES)
						.select(false, false, false, disarmed)
						.select(true, false, false, armed)
						.select(false, true, false, disarmedSand)
						.select(true, true, false, armedSand)
						.select(false, false, true, disarmedVines)
						.select(true, false, true, armedVines)
						.select(false, true, true, disarmed)    // These last two should never exist outside commands
						.select(true, true, true, armed))
		);
		models.registerSimpleItemModel(block, disarmedPath);
	}

	/**
	 * Generate a spike trap type blockstate with a pre-existing model. Spike traps have a single
	 * {@link SpikeTrapBlock#POWERED} state.
	 *
	 * @param models the block model generator
	 * @param block  the block to generate the state for
	 */
	private static void generateSpikeTrap(BlockModelGenerators models, Block block) {
		ResourceLocation poweredPath = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/spike_trap_powered");
		MultiVariant powered = BlockModelGenerators.plainVariant(poweredPath);
		MultiVariant unpowered = BlockModelGenerators.plainVariant(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/spike_trap_unpowered"));

		models.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
				.with(PropertyDispatch.initial(SpikeTrapBlock.POWERED)
						.select(true, powered)
						.select(false, unpowered))
		);
		models.registerSimpleItemModel(block, poweredPath);
	}

	/**
	 * Generate a sandbag type blockstate with a pre-existing model. Sandbags have multiple states:
	 * {@link SandbagBlock#BAGS}, and then an inherited horizontal state.
	 *
	 * @param models the block model generator
	 * @param block  the block to generate the state for
	 */
	private static void generateSandbag(BlockModelGenerators models, Block block) {
		ResourceLocation shape0Path = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/sandbag_0");
		MultiVariant shape0 = BlockModelGenerators.plainVariant(shape0Path);
		MultiVariant shape1 = BlockModelGenerators.plainVariant(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/sandbag_1"));
		MultiVariant shape2 = BlockModelGenerators.plainVariant(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/sandbag_2"));
		MultiVariant shape3 = BlockModelGenerators.plainVariant(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/sandbag_3"));

		models.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
				.with(PropertyDispatch.initial(SandbagBlock.BAGS)
						.select(0, shape0)
						.select(1, shape1)
						.select(2, shape2)
						.select(3, shape3))
				.with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING)
		);
		models.registerSimpleItemModel(block, shape0Path);
	}

	/**
	 * Generate a spotlight type blockstate with a pre-existing model. Spotlights have multiple states:
	 * {@link SpotlightBlock#LIT}, and then an inherited horizontal state.
	 *
	 * @param models the block model generator
	 * @param block  the block to generate the state for
	 */
	private static void generateSpotlight(BlockModelGenerators models, Block block) {
		ResourceLocation unlitPath = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/spotlight_unlit");
		MultiVariant unlit = BlockModelGenerators.plainVariant(unlitPath);
		MultiVariant lit = BlockModelGenerators.plainVariant(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/spotlight_lit"));

		models.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
				.with(PropertyDispatch.initial(SpotlightBlock.LIT)
						.select(true, lit)
						.select(false, unlit))
				.with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING)
		);
		models.registerSimpleItemModel(block, unlitPath);
	}

	/**
	 * Generate a mortar type blockstate with a pre-existing model. Mortars have multiple states:
	 * {@link MortarBlock#LOADED}, {@link MortarBlock#ROTATION}, and then an inherited horizontal state.
	 *
	 * @param models the block model generator
	 * @param block  the block to generate the state for
	 */
	private static void generateMortar(BlockModelGenerators models, Block block) {
		ResourceLocation unloadedPath0 = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/mortar_0_unloaded");
		MultiVariant unloaded0 = BlockModelGenerators.plainVariant(unloadedPath0);
		MultiVariant unloaded1 = BlockModelGenerators.plainVariant(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/mortar_1_unloaded"));
		MultiVariant unloaded2 = BlockModelGenerators.plainVariant(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/mortar_2_unloaded"));
		MultiVariant loaded0 = BlockModelGenerators.plainVariant(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/mortar_0_loaded"));
		MultiVariant loaded1 = BlockModelGenerators.plainVariant(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/mortar_1_loaded"));
		MultiVariant loaded2 = BlockModelGenerators.plainVariant(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/mortar_2_loaded"));

		models.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
				.with(PropertyDispatch.initial(MortarBlock.LOADED, MortarBlock.ROTATION)
						.select(false, 0, unloaded0)
						.select(false, 1, unloaded1)
						.select(false, 2, unloaded2)
						.select(true, 0, loaded0)
						.select(true, 1, loaded1)
						.select(true, 2, loaded2))
				.with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING)
		);
		models.registerSimpleItemModel(block, unloadedPath0);
	}

	/**
	 * Generate a table type blockstate and model.
	 *
	 * @param models the block model generator
	 * @param block  the block to generate the state and model for
	 */
	private static void generateTable(BlockModelGenerators models, Block block, Block base) {
		models.createTrivialBlock(block, TexturedModel.createDefault(b -> new TextureMapping()
						.put(TextureSlot.ALL, ModelLocationUtils.getModelLocation(base)),
				IWModelTemplates.TABLE));
	}

	/**
	 * Generate a fence type blockstate with a pre-existing model.
	 *
	 * @param models the block model generator
	 * @param block  the block to generate the state for
	 */
	private static void generateFence(BlockModelGenerators models, Block block) {
		MultiVariant post = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(block).withSuffix("_post"));
		MultiVariant side = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(block).withSuffix("_side"));
		models.blockStateOutput.accept(BlockModelGenerators.createFence(block, post, side));
	}

	/**
	 * Generate a fence type blockstate and model.
	 *
	 * @param models the block model generator
	 * @param block  the block to generate the state and model for
	 * @param base   the base block to use for the fence
	 */
	private static void generateFence(BlockModelGenerators models, Block block, Block base) {
		TextureMapping mapping = new TextureMapping().put(TextureSlot.TEXTURE, ModelLocationUtils.getModelLocation(base));
		MultiVariant post = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_POST.create(block, mapping, models.modelOutput));
		MultiVariant side = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_SIDE.create(block, mapping, models.modelOutput));
		models.blockStateOutput.accept(BlockModelGenerators.createFence(block, post, side));
		ResourceLocation inventoryLocation = ModelTemplates.FENCE_INVENTORY.create(block, mapping, models.modelOutput);
		models.registerSimpleItemModel(block, inventoryLocation);
	}


	/**
	 * Generate a fence gate type blockstate and model.
	 *
	 * @param models the block model generator
	 * @param block  the block to generate the state and model for
	 * @param base   the base block to use for the fence gate
	 */
	private static void generateFenceGate(BlockModelGenerators models, Block block, Block base) {
		TextureMapping mapping = new TextureMapping().put(TextureSlot.TEXTURE, ModelLocationUtils.getModelLocation(base));
		MultiVariant open = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_GATE_OPEN.create(block, mapping, models.modelOutput));
		MultiVariant closed = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_GATE_CLOSED.create(block, mapping, models.modelOutput));
		MultiVariant wallOpen = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_GATE_WALL_OPEN.create(block, mapping, models.modelOutput));
		MultiVariant wallClosed = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_GATE_WALL_CLOSED.create(block, mapping, models.modelOutput));
		models.blockStateOutput.accept(BlockModelGenerators.createFenceGate(block, open, closed, wallOpen, wallClosed, true));
	}

	/**
	 * Generate a wood log type blockstate and model.
	 *
	 * @param models the block model generator
	 * @param block  the block to generate the state and model for
	 */
	private static void generateWoodLog(BlockModelGenerators models, Block block) {
		models.createAxisAlignedPillarBlock(block, TexturedModel.COLUMN.updateTexture(mapping -> {
			mapping.put(TextureSlot.END, ModelLocationUtils.getModelLocation(block).withSuffix("_top"));
			mapping.put(TextureSlot.SIDE, ModelLocationUtils.getModelLocation(block));
		}));
	}

	/**
	 * Generate a wood type blockstate and model.
	 *
	 * @param models the block model generator
	 * @param block  the block to generate the state and model for
	 * @param log    the log block to use for the wood
	 */
	private static void generateWood(BlockModelGenerators models, Block block, Block log) {
		models.createRotatedPillarWithHorizontalVariant(block,
				TexturedModel.COLUMN.updateTexture(mapping -> {
					mapping.put(TextureSlot.END, ModelLocationUtils.getModelLocation(log));
					mapping.put(TextureSlot.SIDE, ModelLocationUtils.getModelLocation(log));
				}),
				TexturedModel.COLUMN_HORIZONTAL.updateTexture(mapping -> {
					mapping.put(TextureSlot.END, ModelLocationUtils.getModelLocation(log));
					mapping.put(TextureSlot.SIDE, ModelLocationUtils.getModelLocation(log));
				}));
	}

	/**
	 * Generate a blockstate and model for a stairs type block.
	 *
	 * @param models the block model generator
	 * @param block  the block to generate the state and model for
	 * @param base   the base block to use for the stairs
	 */
	private static void generateStairs(BlockModelGenerators models, Block block, Block base) {
		generateStairs(models, block, ModelLocationUtils.getModelLocation(base));
	}

	/**
	 * Generate a blockstate and model for a stairs type block.
	 *
	 * @param models      the block model generator
	 * @param block       the block to generate the state and model for
	 * @param baseTexture the base texture to use for the stairs
	 */
	private static void generateStairs(BlockModelGenerators models, Block block, ResourceLocation baseTexture) {
		TextureMapping mapping = new TextureMapping()
				.put(TextureSlot.TOP, baseTexture)
				.put(TextureSlot.BOTTOM, baseTexture)
				.put(TextureSlot.SIDE, baseTexture);

		MultiVariant inner = BlockModelGenerators.plainVariant(ModelTemplates.STAIRS_INNER.create(block, mapping, models.modelOutput));
		MultiVariant outer = BlockModelGenerators.plainVariant(ModelTemplates.STAIRS_OUTER.create(block, mapping, models.modelOutput));
		ResourceLocation straightLocation = ModelTemplates.STAIRS_STRAIGHT.create(block, mapping, models.modelOutput);
		models.blockStateOutput.accept(BlockModelGenerators.createStairs(block, inner, BlockModelGenerators.plainVariant(straightLocation), outer));
		models.registerSimpleItemModel(block, straightLocation);
	}

	/**
	 * Generate a slab type blockstate and model.
	 *
	 * @param models the block model generator
	 * @param block  the block to generate the state and model for
	 * @param base   the base block to use for the slab
	 */
	private static void generateSlab(BlockModelGenerators models, Block block, Block base, boolean isColumn) {
		ResourceLocation baseLocation = ModelLocationUtils.getModelLocation(base);
		if (isColumn) {
			generateSlab(models, block, base,
					baseLocation,
					baseLocation.withSuffix("_top"),
					baseLocation.withSuffix("_bottom"));
		} else {
			generateSlab(models, block, base, baseLocation, baseLocation, baseLocation);
		}
	}

	/**
	 * Generate a slab type blockstate and model.
	 *
	 * @param models        the block model generator
	 * @param block         the block to generate the state and model for
	 * @param sideTexture   the texture to use for the sides
	 * @param topTexture    the texture to use for the top
	 * @param bottomTexture the texture to use for the bottom
	 */
	private static void generateSlab(BlockModelGenerators models, Block block, Block base, ResourceLocation sideTexture, ResourceLocation topTexture, ResourceLocation bottomTexture) {
		TextureMapping mapping = new TextureMapping()
				.put(TextureSlot.TOP, topTexture)
				.put(TextureSlot.BOTTOM, bottomTexture)
				.put(TextureSlot.SIDE, sideTexture);

		MultiVariant top = BlockModelGenerators.plainVariant(ModelTemplates.SLAB_TOP.create(block, mapping, models.modelOutput));
		MultiVariant doubleSlab = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(base));
		ResourceLocation bottomLocation = ModelTemplates.SLAB_BOTTOM.create(block, mapping, models.modelOutput);
		models.blockStateOutput.accept(BlockModelGenerators.createSlab(block, BlockModelGenerators.plainVariant(bottomLocation), top, doubleSlab));
		models.registerSimpleItemModel(block, bottomLocation);
	}

	/**
	 * Generate a wall type blockstate and model.
	 *
	 * @param models the block model generator
	 * @param block  the block to generate the state and model for
	 * @param base   the base block to use for the wall
	 */
	private static void generateWall(BlockModelGenerators models, Block block, Block base) {
		TextureMapping mapping = new TextureMapping().put(TextureSlot.WALL, ModelLocationUtils.getModelLocation(base));
		MultiVariant post = BlockModelGenerators.plainVariant(ModelTemplates.WALL_POST.create(block, mapping, models.modelOutput));
		MultiVariant lowSide = BlockModelGenerators.plainVariant(ModelTemplates.WALL_LOW_SIDE.create(block, mapping, models.modelOutput));
		MultiVariant tallSide = BlockModelGenerators.plainVariant(ModelTemplates.WALL_TALL_SIDE.create(block, mapping, models.modelOutput));
		models.blockStateOutput.accept(BlockModelGenerators.createWall(block, post, lowSide, tallSide));
		ResourceLocation inventoryLocation = ModelTemplates.WALL_INVENTORY.create(block, mapping, models.modelOutput);
		models.registerSimpleItemModel(block, inventoryLocation);
	}

	/**
	 * Generate a door type blockstate and model.
	 *
	 * @param models the block model generator
	 * @param block  the block to generate the state and model for
	 */
	private static void generateDoor(BlockModelGenerators models, Block block) {
		TextureMapping mapping = TextureMapping.door(block);
		MultiVariant bottomLeft = BlockModelGenerators.plainVariant(ModelTemplates.DOOR_BOTTOM_LEFT.extend().renderType("cutout").build().create(block, mapping, models.modelOutput));
		MultiVariant bottomLeftOpen = BlockModelGenerators.plainVariant(ModelTemplates.DOOR_BOTTOM_LEFT_OPEN.extend().renderType("cutout").build().create(block, mapping, models.modelOutput));
		MultiVariant bottomRight = BlockModelGenerators.plainVariant(ModelTemplates.DOOR_BOTTOM_RIGHT.extend().renderType("cutout").build().create(block, mapping, models.modelOutput));
		MultiVariant bottomRightOpen = BlockModelGenerators.plainVariant(ModelTemplates.DOOR_BOTTOM_RIGHT_OPEN.extend().renderType("cutout").build().create(block, mapping, models.modelOutput));
		MultiVariant topLeft = BlockModelGenerators.plainVariant(ModelTemplates.DOOR_TOP_LEFT.extend().renderType("cutout").build().create(block, mapping, models.modelOutput));
		MultiVariant topLeftOpen = BlockModelGenerators.plainVariant(ModelTemplates.DOOR_TOP_LEFT_OPEN.extend().renderType("cutout").build().create(block, mapping, models.modelOutput));
		MultiVariant topRight = BlockModelGenerators.plainVariant(ModelTemplates.DOOR_TOP_RIGHT.extend().renderType("cutout").build().create(block, mapping, models.modelOutput));
		MultiVariant topRightOpen = BlockModelGenerators.plainVariant(ModelTemplates.DOOR_TOP_RIGHT_OPEN.extend().renderType("cutout").build().create(block, mapping, models.modelOutput));
		models.blockStateOutput.accept(BlockModelGenerators.createDoor(block, bottomLeft, bottomLeftOpen, bottomRight, bottomRightOpen, topLeft, topLeftOpen, topRight, topRightOpen));
		models.registerSimpleFlatItemModel(block.asItem());
	}

	/**
	 * Generate a trapdoor type blockstate and model.
	 *
	 * @param models the block model generator
	 * @param block  the block to generate the state and model for
	 */
	private static void generateTrapdoor(BlockModelGenerators models, Block block) {
		TextureMapping mapping = TextureMapping.defaultTexture(block);
		MultiVariant top = BlockModelGenerators.plainVariant(ModelTemplates.ORIENTABLE_TRAPDOOR_TOP.extend().renderType("cutout").build().create(block, mapping, models.modelOutput));
		ResourceLocation bottomLocation = ModelTemplates.ORIENTABLE_TRAPDOOR_BOTTOM.extend().renderType("cutout").build().create(block, mapping, models.modelOutput);
		MultiVariant open = BlockModelGenerators.plainVariant(ModelTemplates.ORIENTABLE_TRAPDOOR_OPEN.extend().renderType("cutout").build().create(block, mapping, models.modelOutput));
		models.blockStateOutput.accept(BlockModelGenerators.createOrientableTrapdoor(block, top, BlockModelGenerators.plainVariant(bottomLocation), open));
		models.registerSimpleItemModel(block, bottomLocation);
	}

	/**
	 * Generate a pressure plate type blockstate and model.
	 *
	 * @param models the block model generator
	 * @param block  the block to generate the state and model for
	 */
	private static void generatePressurePlate(BlockModelGenerators models, Block block, Block base) {
		TextureMapping mapping = TextureMapping.defaultTexture(base);
		MultiVariant up = BlockModelGenerators.plainVariant(ModelTemplates.PRESSURE_PLATE_UP.create(block, mapping, models.modelOutput));
		MultiVariant down = BlockModelGenerators.plainVariant(ModelTemplates.PRESSURE_PLATE_DOWN.create(block, mapping, models.modelOutput));
		models.blockStateOutput.accept(BlockModelGenerators.createPressurePlate(block, up, down));
		models.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
	}

	/**
	 * Generate a sign/wall sign type blockstate and model.
	 *
	 * @param models   the block model generator
	 * @param sign     the sign block to generate the state and model for
	 * @param wallSign the wall sign block to generate the state and model for
	 * @param base     the base block to use for the particle
	 */
	private static void generateSign(BlockModelGenerators models, Block sign, Block wallSign, Block base) {
		TextureMapping mapping = TextureMapping.particle(base);
		MultiVariant particle = BlockModelGenerators.plainVariant(ModelTemplates.PARTICLE_ONLY.create(sign, mapping, models.modelOutput));
		models.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(sign, particle));
		models.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(wallSign, particle));
		models.registerSimpleFlatItemModel(sign.asItem());
	}

	/**
	 * Generate a hanging sign/wall sign type blockstate and model.
	 *
	 * @param models   the block model generator
	 * @param sign     the sign block to generate the state and model for
	 * @param wallSign the wall sign block to generate the state and model for
	 * @param base     the base block to use for the particle
	 */
	private static void generateHangingSign(BlockModelGenerators models, Block sign, Block wallSign, Block base) {
		MultiVariant particle = models.createParticleOnlyBlockModel(sign, base);
		models.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(sign, particle));
		models.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(wallSign, particle));
		models.registerSimpleFlatItemModel(sign.asItem());
	}

	/**
	 * Generate a button type blockstate and model.
	 *
	 * @param models the block model generator
	 * @param block  the block to generate the state and model for
	 * @param base   the base block to use for the button
	 */
	private static void generateButton(BlockModelGenerators models, Block block, Block base) {
		TextureMapping mapping = TextureMapping.defaultTexture(base);
		MultiVariant normal = BlockModelGenerators.plainVariant(ModelTemplates.BUTTON.create(block, mapping, models.modelOutput));
		MultiVariant pressed = BlockModelGenerators.plainVariant(ModelTemplates.BUTTON_PRESSED.create(block, mapping, models.modelOutput));
		models.blockStateOutput.accept(BlockModelGenerators.createButton(block, normal, pressed));
		ResourceLocation inventoryLocation = ModelTemplates.BUTTON_INVENTORY.create(block, mapping, models.modelOutput);
		models.registerSimpleItemModel(block, inventoryLocation);
	}

	/**
	 * Generate a wooden spikes type blockstate with a pre-existing model. Wooden spikes have multiple states:
	 * {@link WoodenSpikesBlock#DAMAGE_STAGE}, and then an inherited horizontal state.
	 *
	 * @param models the block model generator
	 * @param block  the block to generate the state for
	 */
	private static void generateWoodenSpikes(BlockModelGenerators models, Block block) {
		ResourceLocation damageStagePath0 = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/wooden_spikes_0");
		MultiVariant damageStage0 = BlockModelGenerators.plainVariant(damageStagePath0);
		MultiVariant damageStage1 = BlockModelGenerators.plainVariant(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/wooden_spikes_1"));
		MultiVariant damageStage2 = BlockModelGenerators.plainVariant(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/wooden_spikes_2"));
		MultiVariant damageStage3 = BlockModelGenerators.plainVariant(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/wooden_spikes_3"));

		models.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
				.with(PropertyDispatch.initial(WoodenSpikesBlock.DAMAGE_STAGE)
						.select(0, damageStage0)
						.select(1, damageStage1)
						.select(2, damageStage2)
						.select(3, damageStage3))
				.with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING)
		);
		models.registerSimpleItemModel(block, damageStagePath0);
	}

	/**
	 * Generate a flagpole type blockstate with a pre-existing model. Flagpoles have a single
	 * {@link FlagPoleBlock#IS_BASE} state.
	 *
	 * @param models the block model generator
	 * @param block  the block to generate the state for
	 */
	private static void generateFlagPole(BlockModelGenerators models, Block block) {
		ResourceLocation path = ModelLocationUtils.getModelLocation(block);
		MultiVariant base = BlockModelGenerators.plainVariant(path.withSuffix("_base"));

		models.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
				.with(PropertyDispatch.initial(FlagPoleBlock.IS_BASE)
						.select(true, base)
						.select(false, BlockModelGenerators.plainVariant(path)))
		);
		models.registerSimpleItemModel(block, path);
	}

	/**
	 * Generate a flag type blockstate with a pre-existing model. Flags have a single
	 *
	 * @param models the block model generator
	 * @param block  the block to generate the state for
	 */
	private static void generateFlag(BlockModelGenerators models, Block block) {
		models.createHorizontallyRotatedBlock(block, TexturedModel.createDefault(b -> new TextureMapping()
						.put(IWModelTemplates.Slots.FLAG, ModelLocationUtils.getModelLocation(block)),
				IWModelTemplates.FLAG));
	}

	/**
	 * Generate a plant type blockstate and model.
	 *
	 * @param models      the block model generator
	 * @param plant       the block to generate the state and model for
	 * @param pottedPlant the potted plant block to generate the state and model for
	 */
	private static void generatePlant(BlockModelGenerators models, Block plant, Block pottedPlant) {
		TextureMapping mapping = TextureMapping.plant(plant);
		MultiVariant crossPot = BlockModelGenerators.plainVariant(ModelTemplates.FLOWER_POT_CROSS.extend().renderType("cutout").build().create(pottedPlant, mapping, models.modelOutput));
		models.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(pottedPlant, crossPot));
		generatePlant(models, plant);
	}

	/**
	 * Generate a plant type blockstate and model.
	 *
	 * @param models the block model generator
	 * @param plant  the block to generate the state and model for
	 */
	private static void generatePlant(BlockModelGenerators models, Block plant) {
		TextureMapping mapping = TextureMapping.cross(plant);
		MultiVariant cross = BlockModelGenerators.plainVariant(ModelTemplates.CROSS.extend().renderType("cutout").build().create(plant, mapping, models.modelOutput));
		models.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(plant, cross));
		models.registerSimpleItemModel(plant.asItem(), models.createFlatItemModelWithBlockTexture(plant.asItem(), plant));
	}

	/**
	 * Generate a crystal type blockstate and model.
	 *
	 * @param models the block model generator
	 * @param block  the block to generate the state and model for
	 */
	private static void generateCrystal(BlockModelGenerators models, Block block) {
		MultiVariant cross = BlockModelGenerators.plainVariant(ModelTemplates.CROSS.extend().renderType("cutout").build().create(block, TextureMapping.cross(block), models.modelOutput));
		models.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, cross).with(BlockModelGenerators.ROTATIONS_COLUMN_WITH_FACING));
		models.registerSimpleItemModel(block.asItem(), models.createFlatItemModelWithBlockTexture(block.asItem(), block));
	}

	/**
	 * Generate a stardust leaves type blockstate and model. Stardust leaves have a second overlay layer.
	 *
	 * @param models the block model generator
	 * @param block  the block to generate the state and model for
	 */
	private static void generateStardustLeaves(BlockModelGenerators models, Block block) {
		TextureMapping mapping = TextureMapping.cube(block)
				.put(TextureSlot.LAYER1, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/stardust_granule_overlay"));
		ModelTemplate template = ModelTemplates.LEAVES.extend()
				.renderType("cutout_mipped")
				.requiredTextureSlot(TextureSlot.LAYER1)
				.element(builder -> builder
						.allFaces(((direction, faceBuilder) -> faceBuilder
								.texture(TextureSlot.ALL))))
				.element(builder -> builder
						.allFaces(((direction, faceBuilder) -> faceBuilder
								.texture(TextureSlot.LAYER1)
								.emissivity(15, 3))))
				.build();
		models.createTrivialBlock(block, TexturedModel.createDefault(b -> mapping, template));
	}

	/**
	 * Generate a stardust log type blockstate and model. Stardust logs have a second overlay layer.
	 *
	 * @param models the block model generator
	 * @param block  the block to generate the state and model for
	 */
	private static void generateStardustLog(BlockModelGenerators models, Block block) {
		TextureMapping mapping = TextureMapping.column(block)
				.put(TextureSlot.END, ModelLocationUtils.getModelLocation(block).withSuffix("_top"))
				.put(TextureSlot.SIDE, ModelLocationUtils.getModelLocation(block))
				.put(TextureSlot.LAYER1, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/stardust_granule_overlay"));
		ModelTemplate template = ModelTemplates.CUBE_COLUMN.extend()
				.renderType("cutout")
				.requiredTextureSlot(TextureSlot.LAYER1)
				.element(builder -> builder
						.allFaces(((direction, faceBuilder) -> {
							switch (direction) {
								case NORTH, SOUTH, EAST, WEST -> faceBuilder
										.texture(TextureSlot.SIDE);
								case UP, DOWN -> faceBuilder.texture(TextureSlot.END);
							}
						})))
				.element(builder -> builder
						.allFaces(((direction, faceBuilder) -> {
							switch (direction) {
								case NORTH, SOUTH, EAST, WEST -> faceBuilder
										.texture(TextureSlot.LAYER1)
										.emissivity(15, 3);
								case UP, DOWN -> faceBuilder.texture(TextureSlot.END);
							}
						})))
				.build();

		models.createAxisAlignedPillarBlock(block, TexturedModel.createDefault(b -> mapping, template));
	}

	/**
	 * Generate a stardust wood type blockstate and model. Stardust wood has a second overlay layer.
	 *
	 * @param models the block model generator
	 * @param block  the block to generate the state and model for
	 */
	private static void generateStardustWood(BlockModelGenerators models, Block block) {
		TextureMapping mapping = TextureMapping.column(block)
				.put(TextureSlot.END, ModelLocationUtils.getModelLocation(BlockRegistry.STARDUST_LOG.get()))
				.put(TextureSlot.SIDE, ModelLocationUtils.getModelLocation(BlockRegistry.STARDUST_LOG.get()))
				.put(TextureSlot.LAYER1, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "block/stardust_granule_overlay"));
		ModelTemplate normalTemplate = ModelTemplates.CUBE_COLUMN.extend()
				.renderType("cutout")
				.requiredTextureSlot(TextureSlot.LAYER1)
				.element(builder -> builder
						.allFaces(((direction, faceBuilder) -> {
							switch (direction) {
								case NORTH, SOUTH, EAST, WEST -> faceBuilder
										.texture(TextureSlot.SIDE);
								case UP, DOWN -> faceBuilder.texture(TextureSlot.END);
							}
						})))
				.element(builder -> builder
						.allFaces(((direction, faceBuilder) -> faceBuilder
								.texture(TextureSlot.LAYER1)
								.emissivity(15, 3))))
				.build();
		ModelTemplate horizontalTemplate = ModelTemplates.CUBE_COLUMN_HORIZONTAL.extend()
				.renderType("cutout")
				.requiredTextureSlot(TextureSlot.LAYER1)
				.element(builder -> builder
						.allFaces(((direction, faceBuilder) -> {
							switch (direction) {
								case NORTH, SOUTH, EAST, WEST -> faceBuilder
										.texture(TextureSlot.SIDE);
								case UP, DOWN -> faceBuilder.texture(TextureSlot.END);
							}
						})))
				.element(builder -> builder
						.allFaces(((direction, faceBuilder) -> faceBuilder
								.texture(TextureSlot.LAYER1)
								.emissivity(15, 3))))
				.build();

		models.createRotatedPillarWithHorizontalVariant(block,
				TexturedModel.createDefault(b -> mapping, normalTemplate),
				TexturedModel.createDefault(b -> mapping, horizontalTemplate));
	}

	/**
	 * Generate a tiltros portal type blockstate and model.
	 *
	 * @param models the block model generator
	 * @param block  the block to generate the state and model for
	 */
	private static void generateTiltrosPortal(BlockModelGenerators models, CustomPortalBlock block) {
		TextureMapping mapping = TextureMapping.defaultTexture(block)
				.put(TextureSlot.PARTICLE, ModelLocationUtils.getModelLocation(block));

		ExtendedModelTemplate templateEW = ExtendedModelTemplateBuilder.builder()
				.suffix("_ew")
				.renderType("translucent")
				.requiredTextureSlot(TextureSlot.TEXTURE)
				.requiredTextureSlot(TextureSlot.PARTICLE)
				.element(builder -> builder
						.from(6, 0, 0)
						.to(10, 16, 16)
						.texture(TextureSlot.TEXTURE)
						.face(Direction.EAST, faceBuilder -> faceBuilder.texture(TextureSlot.TEXTURE))
						.face(Direction.WEST, faceBuilder -> faceBuilder.texture(TextureSlot.TEXTURE)))
				.build();

		ExtendedModelTemplate templateNS = ExtendedModelTemplateBuilder.builder()
				.suffix("_ns")
				.renderType("translucent")
				.requiredTextureSlot(TextureSlot.TEXTURE)
				.requiredTextureSlot(TextureSlot.PARTICLE)
				.element(builder -> builder
						.from(0, 0, 6)
						.to(16, 16, 10)
						.texture(TextureSlot.TEXTURE)
						.face(Direction.NORTH, faceBuilder -> faceBuilder.texture(TextureSlot.TEXTURE))
						.face(Direction.SOUTH, faceBuilder -> faceBuilder.texture(TextureSlot.TEXTURE)))
				.build();

		ExtendedModelTemplate templateUD = ExtendedModelTemplateBuilder.builder()
				.suffix("_ud")
				.renderType("translucent")
				.requiredTextureSlot(TextureSlot.TEXTURE)
				.requiredTextureSlot(TextureSlot.PARTICLE)
				.element(builder -> builder
						.from(0, 6, 0)
						.to(16, 10, 16)
						.texture(TextureSlot.TEXTURE)
						.face(Direction.UP, faceBuilder -> faceBuilder.texture(TextureSlot.TEXTURE))
						.face(Direction.DOWN, faceBuilder -> faceBuilder.texture(TextureSlot.TEXTURE)))
				.build();

		models.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
				.with(PropertyDispatch.initial(CustomPortalBlock.AXIS)
						.select(Direction.Axis.X, BlockModelGenerators.plainVariant(templateNS.create(block, mapping, models.modelOutput)))
						.select(Direction.Axis.Z, BlockModelGenerators.plainVariant(templateEW.create(block, mapping, models.modelOutput)))
						.select(Direction.Axis.Y, BlockModelGenerators.plainVariant(templateUD.create(block, mapping, models.modelOutput)))
				)
		);
	}
}