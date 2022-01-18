package com.anonymoushacker1279.immersiveweapons.world.level.levelgen.structures;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.world.level.levelgen.Structures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.structures.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.PostPlacementProcessor;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier.Context;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class CampsiteStructure extends StructureFeature<JigsawConfiguration> {

	public CampsiteStructure() {
		super(JigsawConfiguration.CODEC, context -> {
			if (!isFeatureChunk(context)) {
				return Optional.empty();
			} else {
				return createPiecesGenerator(context);
			}
		}, PostPlacementProcessor.NONE);
	}

	@Override
	public GenerationStep.@NotNull Decoration step() {
		return GenerationStep.Decoration.SURFACE_STRUCTURES;
	}

	// Test if the current chunk (from context) has a valid location for the structure
	private static boolean isFeatureChunk(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
		BlockPos blockPos = context.chunkPos().getWorldPosition();

		// Get height of land (stops at first non-air block)
		int landHeight = context.chunkGenerator().getFirstOccupiedHeight(blockPos.getX(), blockPos.getZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor());

		// Grabs the column of blocks at given position.
		NoiseColumn columnOfBlocks = context.chunkGenerator().getBaseColumn(blockPos.getX(), blockPos.getZ(), context.heightAccessor());

		// Combine the column of blocks with land height, and get the top block itself which can be tested.
		BlockState topBlock = columnOfBlocks.getBlock(landHeight);

		// Now test to make sure the structure is not spawning on water or other fluids.
		return topBlock.getFluidState().isEmpty();
	}

	private static Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
		// Convert the chunk coordinates into actual coordinates can use. (center of that chunk)
		BlockPos blockPos = context.chunkPos().getMiddleBlockPosition(0);

		JigsawConfiguration configuration = new JigsawConfiguration(
				() -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
						.get(new ResourceLocation(ImmersiveWeapons.MOD_ID, "campsite/start_pool")),
				5       //The structure is only one chunk wide but by using five here it can be replaced with something larger in datapacks
		);

		// Create a new context with the new config that has our JSON pool. We will pass this into JigsawPlacement#addPieces
		Context<JigsawConfiguration> contextWithConfig = Structures.createContextWithConfig(context, configuration);

		// Return the piece generator that is now set up, so that the game runs it when it needs to create the layout of structure pieces.
		// Last 'true' parameter means the structure will automatically be placed at ground level
		return JigsawPlacement.addPieces(contextWithConfig,
				PoolElementStructurePiece::new, blockPos, false, true);
	}
}