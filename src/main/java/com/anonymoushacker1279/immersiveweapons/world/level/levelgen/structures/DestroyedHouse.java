package com.anonymoushacker1279.immersiveweapons.world.level.levelgen.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.PostPlacementProcessor;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class DestroyedHouse extends StructureFeature<JigsawConfiguration> {

	static int landHeight;

	public DestroyedHouse() {
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
		landHeight = context.chunkGenerator().getFirstOccupiedHeight(blockPos.getX(), blockPos.getZ(),
				Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor());

		// Grabs the column of blocks at given position.
		NoiseColumn columnOfBlocks = context.chunkGenerator().getBaseColumn(blockPos.getX(), blockPos.getZ(),
				context.heightAccessor());

		// Combine the column of blocks with land height, and get the top block itself which can be tested.
		BlockState topBlock = columnOfBlocks.getBlock(landHeight);
		BlockState structureTop = columnOfBlocks.getBlock(landHeight + 5);

		// Now test to make sure the structure is not spawning on water or other fluids.
		return topBlock.getFluidState().isEmpty() && structureTop.isAir();
	}

	private static Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(
			PieceGeneratorSupplier.Context<JigsawConfiguration> context) {

		// Convert the chunk coordinates into actual coordinates can use. (center of that chunk)
		BlockPos blockPos = context.chunkPos().getMiddleBlockPosition(0);

		/* Generate pieces of the structure.
		 Second-to-last boolean is only for structure intersections.
		 Last boolean means the structure will automatically be placed at ground level.
		 */
		return JigsawPlacement.addPieces(
				context,
				PoolElementStructurePiece::new,
				blockPos.atY(landHeight),
				false,
				false
		);
	}
}