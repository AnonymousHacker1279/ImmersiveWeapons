package com.anonymoushacker1279.immersiveweapons.data.models;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.data.tags.lists.BlockTagLists;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.google.common.collect.*;
import com.google.gson.JsonElement;
import net.minecraft.core.Direction;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.*;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.*;

public class BlockModelGenerator {

	final Consumer<BlockStateGenerator> blockStateOutput;
	final BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput;
	private final Consumer<Item> skippedAutoModelsOutput;
	final List<Block> nonOrientableTrapdoor = ImmutableList.of(Blocks.OAK_TRAPDOOR, Blocks.DARK_OAK_TRAPDOOR, Blocks.IRON_TRAPDOOR);
	final Map<Block, BlockStateGeneratorSupplier> fullBlockModelCustomGenerators = ImmutableMap.<Block, BlockStateGeneratorSupplier> builder().put(Blocks.STONE, BlockModelGenerator::createMirroredCubeGenerator).put(Blocks.DEEPSLATE, BlockModelGenerator::createMirroredColumnGenerator).build();
	final Map<Block, TexturedModel> texturedModels = ImmutableMap.<Block, TexturedModel> builder().build();

	public BlockModelGenerator(Consumer<BlockStateGenerator> pBlockStateOutput, BiConsumer<ResourceLocation, Supplier<JsonElement>> pModelOutput, Consumer<Item> pSkippedAutoModelsOutput) {
		blockStateOutput = pBlockStateOutput;
		modelOutput = pModelOutput;
		skippedAutoModelsOutput = pSkippedAutoModelsOutput;
	}

	public void run() {
		BlockTagLists.init();

		// Create models and blockstates for all glasses
		// Combine lists and remove duplicates
		for (Block block : combineBlockLists(BlockTagLists.BULLETPROOF_GLASS, BlockTagLists.STAINED_GLASS)) {
			createTrivialCube(block);
		}

		// Mud blocks
		createTrivialCube(DeferredRegistryHandler.MUD.get());
		createTrivialCube(DeferredRegistryHandler.DRIED_MUD.get());
		family(DeferredRegistryHandler.HARDENED_MUD.get())
				.slab(DeferredRegistryHandler.HARDENED_MUD_SLAB.get())
				.stairs(DeferredRegistryHandler.HARDENED_MUD_STAIRS.get());

		// Burned Oak blocks
		woodProvider(DeferredRegistryHandler.BURNED_OAK_LOG.get())
				.logWithHorizontal(DeferredRegistryHandler.BURNED_OAK_LOG.get())
				.wood(DeferredRegistryHandler.BURNED_OAK_WOOD.get());
		woodProvider(DeferredRegistryHandler.STRIPPED_BURNED_OAK_LOG.get())
				.logWithHorizontal(DeferredRegistryHandler.STRIPPED_BURNED_OAK_LOG.get())
				.wood(DeferredRegistryHandler.STRIPPED_BURNED_OAK_WOOD.get());
		family(DeferredRegistryHandler.BURNED_OAK_PLANKS.get())
				.door(DeferredRegistryHandler.BURNED_OAK_DOOR.get())
				.button(DeferredRegistryHandler.BURNED_OAK_BUTTON.get())
				.fence(DeferredRegistryHandler.BURNED_OAK_FENCE.get())
				.fenceGate(DeferredRegistryHandler.BURNED_OAK_FENCE_GATE.get())
				.pressurePlate(DeferredRegistryHandler.BURNED_OAK_PRESSURE_PLATE.get())
				.slab(DeferredRegistryHandler.BURNED_OAK_SLAB.get())
				.stairs(DeferredRegistryHandler.BURNED_OAK_STAIRS.get())
				.sign(DeferredRegistryHandler.BURNED_OAK_SIGN.get(), DeferredRegistryHandler.BURNED_OAK_WALL_SIGN.get())
				.trapdoor(DeferredRegistryHandler.BURNED_OAK_TRAPDOOR.get());

		// Cloud Marble blocks
		createTrivialCube(DeferredRegistryHandler.CLOUD_MARBLE.get());
		createRotatedPillarWithHorizontalVariant(DeferredRegistryHandler.CLOUD_MARBLE_PILLAR.get(), TexturedModel.COLUMN_ALT, TexturedModel.COLUMN_HORIZONTAL_ALT);
		family(DeferredRegistryHandler.CLOUD_MARBLE_BRICKS.get())
				.slab(DeferredRegistryHandler.CLOUD_MARBLE_BRICK_SLAB.get())
				.stairs(DeferredRegistryHandler.CLOUD_MARBLE_BRICK_STAIRS.get());

		// Multi textured blocks
		createCraftingTableLike(DeferredRegistryHandler.SMALL_PARTS_TABLE.get(), Blocks.OAK_PLANKS, TextureMapping::fletchingTable);

		// Horizontally-oriented blocks
		createHorizontallyRotatedBlock(DeferredRegistryHandler.TESLA_BLOCK.get(), TexturedModel.CUBE);

		// Other simple blocks
		createTrivialCube(DeferredRegistryHandler.COBALT_BLOCK.get());
		createTrivialCube(DeferredRegistryHandler.RAW_COBALT_BLOCK.get());
		createTrivialCube(DeferredRegistryHandler.MOLTEN_BLOCK.get());
		createTrivialCube(DeferredRegistryHandler.VENTUS_ORE.get());
		createTrivialCube(DeferredRegistryHandler.CLOUD.get());

		// Blockstates
		createSimpleCubeBlockstate(DeferredRegistryHandler.COBALT_ORE.get());
		createSimpleCubeBlockstate(DeferredRegistryHandler.SULFUR_ORE.get());
		createSimpleCubeBlockstate(DeferredRegistryHandler.NETHER_SULFUR_ORE.get());
		createSimpleCubeBlockstate(DeferredRegistryHandler.ELECTRIC_ORE.get());
		createSimpleCubeBlockstate(DeferredRegistryHandler.MOLTEN_ORE.get());
		createSimpleCubeBlockstate(DeferredRegistryHandler.DEEPSLATE_COBALT_ORE.get());
		createSimpleCubeBlockstate(DeferredRegistryHandler.DEEPSLATE_SULFUR_ORE.get());
	}

	private List<Block> combineBlockLists(List<Block> blockList1, List<Block> blockList2) {
		Set<Block> set = new HashSet<>(blockList1);
		set.addAll(blockList2);
		return new ArrayList<>(set);
	}

	private void createSimpleCubeBlockstate(Block block) {
		blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, getModelLocationForBlockstateIW(block))));
	}

	private ResourceLocation getModelLocationForBlockstateIW(Block block) {
		return new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/" + Objects.requireNonNull(block.getRegistryName()).getPath());
	}

	private void createRotatedPillarWithHorizontalVariant(Block pRotatedPillarBlock, TexturedModel.Provider pModelProvider, TexturedModel.Provider pHorizontalModelProvider) {
		ResourceLocation resourceLocation = pModelProvider.create(pRotatedPillarBlock, modelOutput);
		ResourceLocation resourceLocation1 = pHorizontalModelProvider.create(pRotatedPillarBlock, modelOutput);
		blockStateOutput.accept(createRotatedPillarWithHorizontalVariant(pRotatedPillarBlock, resourceLocation, resourceLocation1));
	}

	private void createCraftingTableLike(Block pCraftingTableBlock, Block pCraftingTableMaterialBlock, BiFunction<Block, Block, TextureMapping> pTextureMappingGetter) {
		TextureMapping texturemapping = pTextureMappingGetter.apply(pCraftingTableBlock, pCraftingTableMaterialBlock);
		blockStateOutput.accept(createSimpleBlock(pCraftingTableBlock, ModelTemplates.CUBE.create(pCraftingTableBlock, texturemapping, modelOutput)));
	}

	private void createHorizontallyRotatedBlock(Block pHorizontallyRotatedBlock, TexturedModel.Provider pProvider) {
		ResourceLocation resourcelocation = pProvider.create(pHorizontallyRotatedBlock, modelOutput);
		blockStateOutput.accept(MultiVariantGenerator.multiVariant(pHorizontallyRotatedBlock, Variant.variant()
						.with(VariantProperties.MODEL, resourcelocation))
				.with(createHorizontalFacingDispatch()));
	}

	private static PropertyDispatch createHorizontalFacingDispatch() {
		return PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING)
				.select(Direction.EAST, Variant.variant()
						.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
				.select(Direction.SOUTH, Variant.variant()
						.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
				.select(Direction.WEST, Variant.variant()
						.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
				.select(Direction.NORTH, Variant.variant());
	}

	private BlockFamilyProvider family(Block pBlock) {
		TexturedModel texturedmodel = texturedModels.getOrDefault(pBlock, TexturedModel.CUBE.get(pBlock));
		return (new BlockFamilyProvider(texturedmodel.getMapping(), pBlock)).fullBlock(pBlock, texturedmodel.getTemplate());
	}

	private void createTrivialCube(Block pBlock) {
		createTrivialBlock(pBlock, TexturedModel.CUBE);
	}

	private void createTrivialBlock(Block pBlock, TexturedModel.Provider pProvider) {
		blockStateOutput.accept(createSimpleBlock(pBlock, pProvider.create(pBlock, modelOutput)));
	}

	static MultiVariantGenerator createSimpleBlock(Block pBlock, ResourceLocation pModelLocation) {
		return MultiVariantGenerator.multiVariant(pBlock, Variant.variant().with(VariantProperties.MODEL, pModelLocation));
	}

	void createDoor(Block pDoorBlock) {
		TextureMapping door = TextureMapping.door(pDoorBlock);
		ResourceLocation resourceLocation = ModelTemplates.DOOR_BOTTOM.create(pDoorBlock, door, modelOutput);
		ResourceLocation resourceLocation1 = ModelTemplates.DOOR_BOTTOM_HINGE.create(pDoorBlock, door, modelOutput);
		ResourceLocation resourceLocation2 = ModelTemplates.DOOR_TOP.create(pDoorBlock, door, modelOutput);
		ResourceLocation resourceLocation3 = ModelTemplates.DOOR_TOP_HINGE.create(pDoorBlock, door, modelOutput);
		createSimpleFlatItemModel(pDoorBlock.asItem());
		blockStateOutput.accept(createDoor(pDoorBlock, resourceLocation, resourceLocation1, resourceLocation2, resourceLocation3));
	}

	private static BlockStateGenerator createMirroredCubeGenerator(Block block, ResourceLocation resourceLocation1, TextureMapping textureMapping, BiConsumer<ResourceLocation, Supplier<JsonElement>> supplierBiConsumer) {
		ResourceLocation resourceLocation = ModelTemplates.CUBE_MIRRORED_ALL.create(block, textureMapping, supplierBiConsumer);
		return createRotatedVariant(block, resourceLocation1, resourceLocation);
	}

	private static BlockStateGenerator createMirroredColumnGenerator(Block block, ResourceLocation resourceLocation, TextureMapping textureMapping, BiConsumer<ResourceLocation, Supplier<JsonElement>> locationSupplierBiConsumer) {
		ResourceLocation resourcelocation = ModelTemplates.CUBE_COLUMN_MIRRORED.create(block, textureMapping, locationSupplierBiConsumer);
		return createRotatedVariant(block, resourceLocation, resourcelocation).with(createRotatedPillar());
	}

	private static MultiVariantGenerator createRotatedVariant(Block pBlock, ResourceLocation pNormalModelLocation, ResourceLocation pMirroredModelLocation) {
		return MultiVariantGenerator.multiVariant(pBlock, Variant.variant().with(VariantProperties.MODEL, pNormalModelLocation), Variant.variant().with(VariantProperties.MODEL, pMirroredModelLocation), Variant.variant().with(VariantProperties.MODEL, pNormalModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180), Variant.variant().with(VariantProperties.MODEL, pMirroredModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180));
	}

	private static BlockStateGenerator createDoor(Block pDoorBlock, ResourceLocation pBottomHalfModelLocation, ResourceLocation pBottomHalfRightHingeModelLocation, ResourceLocation pTopHalfModelLocation, ResourceLocation pTopHalfRightHingeModelLocation) {
		return MultiVariantGenerator.multiVariant(pDoorBlock).with(configureDoorHalf(configureDoorHalf(PropertyDispatch.properties(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.DOUBLE_BLOCK_HALF, BlockStateProperties.DOOR_HINGE, BlockStateProperties.OPEN), DoubleBlockHalf.LOWER, pBottomHalfModelLocation, pBottomHalfRightHingeModelLocation), DoubleBlockHalf.UPPER, pTopHalfModelLocation, pTopHalfRightHingeModelLocation));
	}

	void createSimpleFlatItemModel(Item pFlatItem) {
		ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(pFlatItem), TextureMapping.layer0(pFlatItem), modelOutput);
	}

	private static PropertyDispatch.C4<Direction, DoubleBlockHalf, DoorHingeSide, Boolean> configureDoorHalf(PropertyDispatch.C4<Direction, DoubleBlockHalf, DoorHingeSide, Boolean> pDoorProperties, DoubleBlockHalf pDoorHalf, ResourceLocation pDoorModelLocation, ResourceLocation pDoorRightHingeModelLocation) {
		return pDoorProperties.select(Direction.EAST, pDoorHalf, DoorHingeSide.LEFT, false, Variant.variant().with(VariantProperties.MODEL, pDoorModelLocation)).select(Direction.SOUTH, pDoorHalf, DoorHingeSide.LEFT, false, Variant.variant().with(VariantProperties.MODEL, pDoorModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)).select(Direction.WEST, pDoorHalf, DoorHingeSide.LEFT, false, Variant.variant().with(VariantProperties.MODEL, pDoorModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)).select(Direction.NORTH, pDoorHalf, DoorHingeSide.LEFT, false, Variant.variant().with(VariantProperties.MODEL, pDoorModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)).select(Direction.EAST, pDoorHalf, DoorHingeSide.RIGHT, false, Variant.variant().with(VariantProperties.MODEL, pDoorRightHingeModelLocation)).select(Direction.SOUTH, pDoorHalf, DoorHingeSide.RIGHT, false, Variant.variant().with(VariantProperties.MODEL, pDoorRightHingeModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)).select(Direction.WEST, pDoorHalf, DoorHingeSide.RIGHT, false, Variant.variant().with(VariantProperties.MODEL, pDoorRightHingeModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)).select(Direction.NORTH, pDoorHalf, DoorHingeSide.RIGHT, false, Variant.variant().with(VariantProperties.MODEL, pDoorRightHingeModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)).select(Direction.EAST, pDoorHalf, DoorHingeSide.LEFT, true, Variant.variant().with(VariantProperties.MODEL, pDoorRightHingeModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)).select(Direction.SOUTH, pDoorHalf, DoorHingeSide.LEFT, true, Variant.variant().with(VariantProperties.MODEL, pDoorRightHingeModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)).select(Direction.WEST, pDoorHalf, DoorHingeSide.LEFT, true, Variant.variant().with(VariantProperties.MODEL, pDoorRightHingeModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)).select(Direction.NORTH, pDoorHalf, DoorHingeSide.LEFT, true, Variant.variant().with(VariantProperties.MODEL, pDoorRightHingeModelLocation)).select(Direction.EAST, pDoorHalf, DoorHingeSide.RIGHT, true, Variant.variant().with(VariantProperties.MODEL, pDoorModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)).select(Direction.SOUTH, pDoorHalf, DoorHingeSide.RIGHT, true, Variant.variant().with(VariantProperties.MODEL, pDoorModelLocation)).select(Direction.WEST, pDoorHalf, DoorHingeSide.RIGHT, true, Variant.variant().with(VariantProperties.MODEL, pDoorModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)).select(Direction.NORTH, pDoorHalf, DoorHingeSide.RIGHT, true, Variant.variant().with(VariantProperties.MODEL, pDoorModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180));
	}

	static BlockStateGenerator createAxisAlignedPillarBlock(Block pAxisAlignedPillarBlock, ResourceLocation pModelLocation) {
		return MultiVariantGenerator.multiVariant(pAxisAlignedPillarBlock, Variant.variant()
				.with(VariantProperties.MODEL, pModelLocation)).with(createRotatedPillar());
	}

	private static PropertyDispatch createRotatedPillar() {
		return PropertyDispatch.property(BlockStateProperties.AXIS).select(Direction.Axis.Y, Variant.variant())
				.select(Direction.Axis.Z, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
				.select(Direction.Axis.X, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
						.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90));
	}

	static BlockStateGenerator createRotatedPillarWithHorizontalVariant(Block pRotatedPillarBlock, ResourceLocation pModelLocation
			, ResourceLocation pHorizontalModelLocation) {
		return MultiVariantGenerator.multiVariant(pRotatedPillarBlock)
				.with(PropertyDispatch.property(BlockStateProperties.AXIS)
						.select(Direction.Axis.Y, Variant.variant()
								.with(VariantProperties.MODEL, pModelLocation))
						.select(Direction.Axis.Z, Variant.variant().with(VariantProperties.MODEL, pHorizontalModelLocation)
								.with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
						.select(Direction.Axis.X, Variant.variant().with(VariantProperties.MODEL, pHorizontalModelLocation)
								.with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
								.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)));
	}

	private WoodProvider woodProvider(Block pLogBlock) {
		return new WoodProvider(TextureMapping.logColumn(pLogBlock));
	}

	void delegateItemModel(Block pBlock, ResourceLocation pDelegateModelLocation) {
		modelOutput.accept(ModelLocationUtils.getModelLocation(pBlock.asItem()), new DelegatedModel(pDelegateModelLocation));
	}

	static BlockStateGenerator createButton(Block pButtonBlock, ResourceLocation pUnpoweredModelLocation, ResourceLocation pPoweredModelLocation) {
		return MultiVariantGenerator.multiVariant(pButtonBlock).with(PropertyDispatch.property(BlockStateProperties.POWERED).select(false, Variant.variant().with(VariantProperties.MODEL, pUnpoweredModelLocation)).select(true, Variant.variant().with(VariantProperties.MODEL, pPoweredModelLocation))).with(PropertyDispatch.properties(BlockStateProperties.ATTACH_FACE, BlockStateProperties.HORIZONTAL_FACING).select(AttachFace.FLOOR, Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)).select(AttachFace.FLOOR, Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)).select(AttachFace.FLOOR, Direction.SOUTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)).select(AttachFace.FLOOR, Direction.NORTH, Variant.variant()).select(AttachFace.WALL, Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).select(AttachFace.WALL, Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).select(AttachFace.WALL, Direction.SOUTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).select(AttachFace.WALL, Direction.NORTH, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).select(AttachFace.CEILING, Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)).select(AttachFace.CEILING, Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)).select(AttachFace.CEILING, Direction.SOUTH, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)).select(AttachFace.CEILING, Direction.NORTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)));
	}

	static BlockStateGenerator createWall(Block pWallBlock, ResourceLocation pPostModelLocation, ResourceLocation pLowSideModelLocation, ResourceLocation pTallSideModelLocation) {
		return MultiPartGenerator.multiPart(pWallBlock).with(Condition.condition().term(BlockStateProperties.UP, true), Variant.variant().with(VariantProperties.MODEL, pPostModelLocation)).with(Condition.condition().term(BlockStateProperties.NORTH_WALL, WallSide.LOW), Variant.variant().with(VariantProperties.MODEL, pLowSideModelLocation).with(VariantProperties.UV_LOCK, true)).with(Condition.condition().term(BlockStateProperties.EAST_WALL, WallSide.LOW), Variant.variant().with(VariantProperties.MODEL, pLowSideModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).with(Condition.condition().term(BlockStateProperties.SOUTH_WALL, WallSide.LOW), Variant.variant().with(VariantProperties.MODEL, pLowSideModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).with(Condition.condition().term(BlockStateProperties.WEST_WALL, WallSide.LOW), Variant.variant().with(VariantProperties.MODEL, pLowSideModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true)).with(Condition.condition().term(BlockStateProperties.NORTH_WALL, WallSide.TALL), Variant.variant().with(VariantProperties.MODEL, pTallSideModelLocation).with(VariantProperties.UV_LOCK, true)).with(Condition.condition().term(BlockStateProperties.EAST_WALL, WallSide.TALL), Variant.variant().with(VariantProperties.MODEL, pTallSideModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).with(Condition.condition().term(BlockStateProperties.SOUTH_WALL, WallSide.TALL), Variant.variant().with(VariantProperties.MODEL, pTallSideModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).with(Condition.condition().term(BlockStateProperties.WEST_WALL, WallSide.TALL), Variant.variant().with(VariantProperties.MODEL, pTallSideModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true));
	}

	static BlockStateGenerator createFence(Block pFenceBlock, ResourceLocation pFencePostModelLocation, ResourceLocation pFenceSideModelLocation) {
		return MultiPartGenerator.multiPart(pFenceBlock).with(Variant.variant().with(VariantProperties.MODEL, pFencePostModelLocation)).with(Condition.condition().term(BlockStateProperties.NORTH, true), Variant.variant().with(VariantProperties.MODEL, pFenceSideModelLocation).with(VariantProperties.UV_LOCK, true)).with(Condition.condition().term(BlockStateProperties.EAST, true), Variant.variant().with(VariantProperties.MODEL, pFenceSideModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).with(Condition.condition().term(BlockStateProperties.SOUTH, true), Variant.variant().with(VariantProperties.MODEL, pFenceSideModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).with(Condition.condition().term(BlockStateProperties.WEST, true), Variant.variant().with(VariantProperties.MODEL, pFenceSideModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true));
	}

	static BlockStateGenerator createFenceGate(Block pFenceGateBlock, ResourceLocation pOpenModelLocation, ResourceLocation pClosedModelLocation, ResourceLocation pWallOpenModelLocation, ResourceLocation pWallClosedModelLocation) {
		return MultiVariantGenerator.multiVariant(pFenceGateBlock, Variant.variant().with(VariantProperties.UV_LOCK, true)).with(createHorizontalFacingDispatchAlt()).with(PropertyDispatch.properties(BlockStateProperties.IN_WALL, BlockStateProperties.OPEN).select(false, false, Variant.variant().with(VariantProperties.MODEL, pClosedModelLocation)).select(true, false, Variant.variant().with(VariantProperties.MODEL, pWallClosedModelLocation)).select(false, true, Variant.variant().with(VariantProperties.MODEL, pOpenModelLocation)).select(true, true, Variant.variant().with(VariantProperties.MODEL, pWallOpenModelLocation)));
	}

	private static PropertyDispatch createHorizontalFacingDispatchAlt() {
		return PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING).select(Direction.SOUTH, Variant.variant()).select(Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)).select(Direction.NORTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)).select(Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270));
	}

	static BlockStateGenerator createPressurePlate(Block pPressurePlateBlock, ResourceLocation pUnpoweredModelLocation, ResourceLocation pPoweredModelLocation) {
		return MultiVariantGenerator.multiVariant(pPressurePlateBlock).with(createBooleanModelDispatch(BlockStateProperties.POWERED, pPoweredModelLocation, pUnpoweredModelLocation));
	}

	private static PropertyDispatch createBooleanModelDispatch(BooleanProperty pProperty, ResourceLocation pTrueModelLocation, ResourceLocation pFalseModelLocation) {
		return PropertyDispatch.property(pProperty).select(true, Variant.variant().with(VariantProperties.MODEL, pTrueModelLocation)).select(false, Variant.variant().with(VariantProperties.MODEL, pFalseModelLocation));
	}

	void skipAutoItemBlock(Block pBlock) {
		skippedAutoModelsOutput.accept(pBlock.asItem());
	}

	static BlockStateGenerator createSlab(Block pSlabBlock, ResourceLocation pBottomHalfModelLocation, ResourceLocation pTopHalfModelLocation, ResourceLocation pDoubleModelLocation) {
		return MultiVariantGenerator.multiVariant(pSlabBlock).with(PropertyDispatch.property(BlockStateProperties.SLAB_TYPE).select(SlabType.BOTTOM, Variant.variant().with(VariantProperties.MODEL, pBottomHalfModelLocation)).select(SlabType.TOP, Variant.variant().with(VariantProperties.MODEL, pTopHalfModelLocation)).select(SlabType.DOUBLE, Variant.variant().with(VariantProperties.MODEL, pDoubleModelLocation)));
	}

	static BlockStateGenerator createStairs(Block pStairsBlock, ResourceLocation pInnerModelLocation, ResourceLocation pStraightModelLocation, ResourceLocation pOuterModelLocation) {
		return MultiVariantGenerator.multiVariant(pStairsBlock).with(PropertyDispatch.properties(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.HALF, BlockStateProperties.STAIRS_SHAPE).select(Direction.EAST, Half.BOTTOM, StairsShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, pStraightModelLocation)).select(Direction.WEST, Half.BOTTOM, StairsShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, pStraightModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).select(Direction.SOUTH, Half.BOTTOM, StairsShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, pStraightModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).select(Direction.NORTH, Half.BOTTOM, StairsShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, pStraightModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true)).select(Direction.EAST, Half.BOTTOM, StairsShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, pOuterModelLocation)).select(Direction.WEST, Half.BOTTOM, StairsShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, pOuterModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).select(Direction.SOUTH, Half.BOTTOM, StairsShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, pOuterModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).select(Direction.NORTH, Half.BOTTOM, StairsShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, pOuterModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true)).select(Direction.EAST, Half.BOTTOM, StairsShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, pOuterModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true)).select(Direction.WEST, Half.BOTTOM, StairsShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, pOuterModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).select(Direction.SOUTH, Half.BOTTOM, StairsShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, pOuterModelLocation)).select(Direction.NORTH, Half.BOTTOM, StairsShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, pOuterModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).select(Direction.EAST, Half.BOTTOM, StairsShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, pInnerModelLocation)).select(Direction.WEST, Half.BOTTOM, StairsShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, pInnerModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).select(Direction.SOUTH, Half.BOTTOM, StairsShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, pInnerModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).select(Direction.NORTH, Half.BOTTOM, StairsShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, pInnerModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true)).select(Direction.EAST, Half.BOTTOM, StairsShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, pInnerModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true)).select(Direction.WEST, Half.BOTTOM, StairsShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, pInnerModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).select(Direction.SOUTH, Half.BOTTOM, StairsShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, pInnerModelLocation)).select(Direction.NORTH, Half.BOTTOM, StairsShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, pInnerModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).select(Direction.EAST, Half.TOP, StairsShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, pStraightModelLocation).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).select(Direction.WEST, Half.TOP, StairsShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, pStraightModelLocation).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).select(Direction.SOUTH, Half.TOP, StairsShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, pStraightModelLocation).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).select(Direction.NORTH, Half.TOP, StairsShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, pStraightModelLocation).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true)).select(Direction.EAST, Half.TOP, StairsShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, pOuterModelLocation).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).select(Direction.WEST, Half.TOP, StairsShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, pOuterModelLocation).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true)).select(Direction.SOUTH, Half.TOP, StairsShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, pOuterModelLocation).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).select(Direction.NORTH, Half.TOP, StairsShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, pOuterModelLocation).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).select(Direction.EAST, Half.TOP, StairsShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, pOuterModelLocation).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).select(Direction.WEST, Half.TOP, StairsShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, pOuterModelLocation).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).select(Direction.SOUTH, Half.TOP, StairsShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, pOuterModelLocation).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).select(Direction.NORTH, Half.TOP, StairsShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, pOuterModelLocation).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true)).select(Direction.EAST, Half.TOP, StairsShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, pInnerModelLocation).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).select(Direction.WEST, Half.TOP, StairsShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, pInnerModelLocation).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true)).select(Direction.SOUTH, Half.TOP, StairsShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, pInnerModelLocation).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).select(Direction.NORTH, Half.TOP, StairsShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, pInnerModelLocation).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).select(Direction.EAST, Half.TOP, StairsShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, pInnerModelLocation).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).select(Direction.WEST, Half.TOP, StairsShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, pInnerModelLocation).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).select(Direction.SOUTH, Half.TOP, StairsShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, pInnerModelLocation).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).select(Direction.NORTH, Half.TOP, StairsShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, pInnerModelLocation).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true)));
	}

	void createTrapdoor(Block pTrapdoorBlock) {
		TextureMapping texturemapping = TextureMapping.defaultTexture(pTrapdoorBlock);
		ResourceLocation resourceLocation = ModelTemplates.TRAPDOOR_TOP.create(pTrapdoorBlock, texturemapping, modelOutput);
		ResourceLocation resourceLocation1 = ModelTemplates.TRAPDOOR_BOTTOM.create(pTrapdoorBlock, texturemapping, modelOutput);
		ResourceLocation resourceLocation2 = ModelTemplates.TRAPDOOR_OPEN.create(pTrapdoorBlock, texturemapping, modelOutput);
		blockStateOutput.accept(createTrapdoor(pTrapdoorBlock, resourceLocation, resourceLocation1, resourceLocation2));
		delegateItemModel(pTrapdoorBlock, resourceLocation1);
	}

	private static BlockStateGenerator createTrapdoor(Block pTrapdoorBlock, ResourceLocation pTopModelLocation, ResourceLocation pBottomModelLocation, ResourceLocation pOpenModelLocation) {
		return MultiVariantGenerator.multiVariant(pTrapdoorBlock).with(PropertyDispatch.properties(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.HALF, BlockStateProperties.OPEN).select(Direction.NORTH, Half.BOTTOM, false, Variant.variant().with(VariantProperties.MODEL, pBottomModelLocation)).select(Direction.SOUTH, Half.BOTTOM, false, Variant.variant().with(VariantProperties.MODEL, pBottomModelLocation)).select(Direction.EAST, Half.BOTTOM, false, Variant.variant().with(VariantProperties.MODEL, pBottomModelLocation)).select(Direction.WEST, Half.BOTTOM, false, Variant.variant().with(VariantProperties.MODEL, pBottomModelLocation)).select(Direction.NORTH, Half.TOP, false, Variant.variant().with(VariantProperties.MODEL, pTopModelLocation)).select(Direction.SOUTH, Half.TOP, false, Variant.variant().with(VariantProperties.MODEL, pTopModelLocation)).select(Direction.EAST, Half.TOP, false, Variant.variant().with(VariantProperties.MODEL, pTopModelLocation)).select(Direction.WEST, Half.TOP, false, Variant.variant().with(VariantProperties.MODEL, pTopModelLocation)).select(Direction.NORTH, Half.BOTTOM, true, Variant.variant().with(VariantProperties.MODEL, pOpenModelLocation)).select(Direction.SOUTH, Half.BOTTOM, true, Variant.variant().with(VariantProperties.MODEL, pOpenModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)).select(Direction.EAST, Half.BOTTOM, true, Variant.variant().with(VariantProperties.MODEL, pOpenModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)).select(Direction.WEST, Half.BOTTOM, true, Variant.variant().with(VariantProperties.MODEL, pOpenModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)).select(Direction.NORTH, Half.TOP, true, Variant.variant().with(VariantProperties.MODEL, pOpenModelLocation)).select(Direction.SOUTH, Half.TOP, true, Variant.variant().with(VariantProperties.MODEL, pOpenModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)).select(Direction.EAST, Half.TOP, true, Variant.variant().with(VariantProperties.MODEL, pOpenModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)).select(Direction.WEST, Half.TOP, true, Variant.variant().with(VariantProperties.MODEL, pOpenModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)));
	}

	void createOrientableTrapdoor(Block pOrientableTrapdoorBlock) {
		TextureMapping texturemapping = TextureMapping.defaultTexture(pOrientableTrapdoorBlock);
		ResourceLocation resourceLocation = ModelTemplates.ORIENTABLE_TRAPDOOR_TOP.create(pOrientableTrapdoorBlock, texturemapping, modelOutput);
		ResourceLocation resourceLocation1 = ModelTemplates.ORIENTABLE_TRAPDOOR_BOTTOM.create(pOrientableTrapdoorBlock, texturemapping, modelOutput);
		ResourceLocation resourceLocation2 = ModelTemplates.ORIENTABLE_TRAPDOOR_OPEN.create(pOrientableTrapdoorBlock, texturemapping, modelOutput);
		blockStateOutput.accept(createOrientableTrapdoor(pOrientableTrapdoorBlock, resourceLocation, resourceLocation1, resourceLocation2));
		delegateItemModel(pOrientableTrapdoorBlock, resourceLocation1);
	}

	private static BlockStateGenerator createOrientableTrapdoor(Block pOrientableTrapdoorBlock, ResourceLocation pTopModelLocation, ResourceLocation pBottomModelLocation, ResourceLocation pOpenModelLocation) {
		return MultiVariantGenerator.multiVariant(pOrientableTrapdoorBlock).with(PropertyDispatch.properties(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.HALF, BlockStateProperties.OPEN).select(Direction.NORTH, Half.BOTTOM, false, Variant.variant().with(VariantProperties.MODEL, pBottomModelLocation)).select(Direction.SOUTH, Half.BOTTOM, false, Variant.variant().with(VariantProperties.MODEL, pBottomModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)).select(Direction.EAST, Half.BOTTOM, false, Variant.variant().with(VariantProperties.MODEL, pBottomModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)).select(Direction.WEST, Half.BOTTOM, false, Variant.variant().with(VariantProperties.MODEL, pBottomModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)).select(Direction.NORTH, Half.TOP, false, Variant.variant().with(VariantProperties.MODEL, pTopModelLocation)).select(Direction.SOUTH, Half.TOP, false, Variant.variant().with(VariantProperties.MODEL, pTopModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)).select(Direction.EAST, Half.TOP, false, Variant.variant().with(VariantProperties.MODEL, pTopModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)).select(Direction.WEST, Half.TOP, false, Variant.variant().with(VariantProperties.MODEL, pTopModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)).select(Direction.NORTH, Half.BOTTOM, true, Variant.variant().with(VariantProperties.MODEL, pOpenModelLocation)).select(Direction.SOUTH, Half.BOTTOM, true, Variant.variant().with(VariantProperties.MODEL, pOpenModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)).select(Direction.EAST, Half.BOTTOM, true, Variant.variant().with(VariantProperties.MODEL, pOpenModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)).select(Direction.WEST, Half.BOTTOM, true, Variant.variant().with(VariantProperties.MODEL, pOpenModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)).select(Direction.NORTH, Half.TOP, true, Variant.variant().with(VariantProperties.MODEL, pOpenModelLocation).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)).select(Direction.SOUTH, Half.TOP, true, Variant.variant().with(VariantProperties.MODEL, pOpenModelLocation).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0)).select(Direction.EAST, Half.TOP, true, Variant.variant().with(VariantProperties.MODEL, pOpenModelLocation).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)).select(Direction.WEST, Half.TOP, true, Variant.variant().with(VariantProperties.MODEL, pOpenModelLocation).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)));
	}


	@FunctionalInterface
	interface BlockStateGeneratorSupplier {
		BlockStateGenerator create(Block pBlock, ResourceLocation pModelLocation, TextureMapping pTextureMapping, BiConsumer<ResourceLocation, Supplier<JsonElement>> pModelOutput);
	}

	class BlockFamilyProvider {
		private final TextureMapping mapping;
		private final Map<ModelTemplate, ResourceLocation> models = Maps.newHashMap();
		@Nullable
		private BlockFamily family;
		@Nullable
		private ResourceLocation fullBlock;

		public BlockFamilyProvider(TextureMapping textureMapping) {
			mapping = textureMapping;
		}

		public BlockFamilyProvider(TextureMapping textureMapping, Block block) {
			mapping = textureMapping;
			family = new BlockFamily.Builder(block).getFamily();
		}

		public BlockFamilyProvider fullBlock(Block pBlock, ModelTemplate pModelTemplate) {
			fullBlock = pModelTemplate.create(pBlock, mapping, modelOutput);
			if (fullBlockModelCustomGenerators.containsKey(pBlock)) {
				blockStateOutput.accept(fullBlockModelCustomGenerators.get(pBlock).create(pBlock, fullBlock, mapping, modelOutput));
			} else {
				blockStateOutput.accept(createSimpleBlock(pBlock, fullBlock));
			}

			return this;
		}

		public BlockFamilyProvider fullBlockCopies(Block... pBlocks) {
			if (fullBlock == null) {
				throw new IllegalStateException("Full block not generated yet");
			} else {
				for (Block block : pBlocks) {
					blockStateOutput.accept(createSimpleBlock(block, fullBlock));
					delegateItemModel(block, fullBlock);
				}

				return this;
			}
		}

		public BlockFamilyProvider button(Block pButtonBlock) {
			ResourceLocation resourceLocation = ModelTemplates.BUTTON.create(pButtonBlock, mapping, modelOutput);
			ResourceLocation resourceLocation1 = ModelTemplates.BUTTON_PRESSED.create(pButtonBlock, mapping, modelOutput);
			blockStateOutput.accept(createButton(pButtonBlock, resourceLocation, resourceLocation1));
			ResourceLocation resourceLocation2 = ModelTemplates.BUTTON_INVENTORY.create(pButtonBlock, mapping, modelOutput);
			delegateItemModel(pButtonBlock, resourceLocation2);
			return this;
		}

		public BlockFamilyProvider wall(Block pWallBlock) {
			ResourceLocation resourceLocation = ModelTemplates.WALL_POST.create(pWallBlock, mapping, modelOutput);
			ResourceLocation resourceLocation1 = ModelTemplates.WALL_LOW_SIDE.create(pWallBlock, mapping, modelOutput);
			ResourceLocation resourceLocation2 = ModelTemplates.WALL_TALL_SIDE.create(pWallBlock, mapping, modelOutput);
			blockStateOutput.accept(createWall(pWallBlock, resourceLocation, resourceLocation1, resourceLocation2));
			ResourceLocation resourceLocation3 = ModelTemplates.WALL_INVENTORY.create(pWallBlock, mapping, modelOutput);
			delegateItemModel(pWallBlock, resourceLocation3);
			return this;
		}

		public BlockFamilyProvider fence(Block pFenceBlock) {
			ResourceLocation resourceLocation = ModelTemplates.FENCE_POST.create(pFenceBlock, mapping, modelOutput);
			ResourceLocation resourceLocation1 = ModelTemplates.FENCE_SIDE.create(pFenceBlock, mapping, modelOutput);
			blockStateOutput.accept(createFence(pFenceBlock, resourceLocation, resourceLocation1));
			ResourceLocation resourceLocation2 = ModelTemplates.FENCE_INVENTORY.create(pFenceBlock, mapping, modelOutput);
			delegateItemModel(pFenceBlock, resourceLocation2);
			return this;
		}

		public BlockFamilyProvider fenceGate(Block pFenceGateBlock) {
			ResourceLocation resourceLocation = ModelTemplates.FENCE_GATE_OPEN.create(pFenceGateBlock, mapping, modelOutput);
			ResourceLocation resourceLocation1 = ModelTemplates.FENCE_GATE_CLOSED.create(pFenceGateBlock, mapping, modelOutput);
			ResourceLocation resourceLocation2 = ModelTemplates.FENCE_GATE_WALL_OPEN.create(pFenceGateBlock, mapping, modelOutput);
			ResourceLocation resourceLocation3 = ModelTemplates.FENCE_GATE_WALL_CLOSED.create(pFenceGateBlock, mapping, modelOutput);
			blockStateOutput.accept(createFenceGate(pFenceGateBlock, resourceLocation, resourceLocation1, resourceLocation2, resourceLocation3));
			return this;
		}

		public BlockFamilyProvider pressurePlate(Block pPressurePlateBlock) {
			ResourceLocation resourceLocation = ModelTemplates.PRESSURE_PLATE_UP.create(pPressurePlateBlock, mapping, modelOutput);
			ResourceLocation resourceLocation1 = ModelTemplates.PRESSURE_PLATE_DOWN.create(pPressurePlateBlock, mapping, modelOutput);
			blockStateOutput.accept(createPressurePlate(pPressurePlateBlock, resourceLocation, resourceLocation1));
			return this;
		}

		public BlockFamilyProvider sign(Block signBlock, Block wallSignBlock) {
			if (family == null) {
				throw new IllegalStateException("Family not defined");
			} else {
				ResourceLocation resourcelocation = ModelTemplates.PARTICLE_ONLY.create(signBlock, mapping, modelOutput);
				blockStateOutput.accept(createSimpleBlock(signBlock, resourcelocation));
				blockStateOutput.accept(createSimpleBlock(wallSignBlock, resourcelocation));
				createSimpleFlatItemModel(signBlock.asItem());
				skipAutoItemBlock(signBlock);
				return this;
			}
		}

		public BlockFamilyProvider slab(Block pSlabBlock) {
			if (fullBlock == null) {
				throw new IllegalStateException("Full block not generated yet");
			} else {
				ResourceLocation resourceLocation = getOrCreateModel(ModelTemplates.SLAB_BOTTOM, pSlabBlock);
				ResourceLocation resourceLocation1 = getOrCreateModel(ModelTemplates.SLAB_TOP, pSlabBlock);
				blockStateOutput.accept(createSlab(pSlabBlock, resourceLocation, resourceLocation1, fullBlock));
				delegateItemModel(pSlabBlock, resourceLocation);
				return this;
			}
		}

		public BlockFamilyProvider stairs(Block pStairsBlock) {
			ResourceLocation resourceLocation = getOrCreateModel(ModelTemplates.STAIRS_INNER, pStairsBlock);
			ResourceLocation resourceLocation1 = getOrCreateModel(ModelTemplates.STAIRS_STRAIGHT, pStairsBlock);
			ResourceLocation resourceLocation2 = getOrCreateModel(ModelTemplates.STAIRS_OUTER, pStairsBlock);
			blockStateOutput.accept(createStairs(pStairsBlock, resourceLocation, resourceLocation1, resourceLocation2));
			delegateItemModel(pStairsBlock, resourceLocation1);
			return this;
		}

		private BlockFamilyProvider door(Block pDoorBlock) {
			createDoor(pDoorBlock);
			return this;
		}

		private void trapdoor(Block pTrapdoorBlock) {
			if (nonOrientableTrapdoor.contains(pTrapdoorBlock)) {
				createTrapdoor(pTrapdoorBlock);
			} else {
				createOrientableTrapdoor(pTrapdoorBlock);
			}
		}

		private ResourceLocation getOrCreateModel(ModelTemplate pModelTemplate, Block pBlock) {
			return models.computeIfAbsent(pModelTemplate, (resourceLocation) -> resourceLocation.create(pBlock, mapping, modelOutput));
		}

	}

	class WoodProvider {
		private final TextureMapping logMapping;

		public WoodProvider(TextureMapping textureMapping) {
			logMapping = textureMapping;
		}

		public WoodProvider wood(Block pWoodBlock) {
			TextureMapping textureMapping = logMapping.copyAndUpdate(TextureSlot.END, logMapping.get(TextureSlot.SIDE));
			ResourceLocation resourceLocation = ModelTemplates.CUBE_COLUMN.create(pWoodBlock, textureMapping, modelOutput);
			blockStateOutput.accept(createAxisAlignedPillarBlock(pWoodBlock, resourceLocation));
			return this;
		}

		public WoodProvider log(Block pLogBlock) {
			ResourceLocation resourceLocation = ModelTemplates.CUBE_COLUMN.create(pLogBlock, logMapping, modelOutput);
			blockStateOutput.accept(createAxisAlignedPillarBlock(pLogBlock, resourceLocation));
			return this;
		}

		public WoodProvider logWithHorizontal(Block pLogBlock) {
			ResourceLocation resourceLocation = ModelTemplates.CUBE_COLUMN.create(pLogBlock, logMapping, modelOutput);
			ResourceLocation resourceLocation1 = ModelTemplates.CUBE_COLUMN_HORIZONTAL.create(pLogBlock, logMapping, modelOutput);
			blockStateOutput.accept(createRotatedPillarWithHorizontalVariant(pLogBlock, resourceLocation, resourceLocation1));
			return this;
		}
	}
}