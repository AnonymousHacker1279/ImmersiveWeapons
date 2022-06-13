package tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.PostPlacementProcessor;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class HansHut extends StructureFeature<JigsawConfiguration> {

	public HansHut() {
		super(JigsawConfiguration.CODEC, HansHut::createPiecesGenerator, PostPlacementProcessor.NONE);
	}

	@Override
	public GenerationStep.@NotNull Decoration step() {
		return Decoration.UNDERGROUND_STRUCTURES;
	}

	private static @NotNull Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
		// Check if the height is suitable
		if (context.heightAccessor().getMinBuildHeight() + 3 > -10) {
			return Optional.empty();
		}

		// Convert the chunk coordinates into actual coordinates can use. (center of that chunk)
		BlockPos blockPos = context.chunkPos().getMiddleBlockPosition(0);

		// Get a block position underground
		blockPos = findSuitableSpot(context, blockPos);

		// Don't generate if the position is in a fluid
		NoiseColumn columnOfBlocks = context.chunkGenerator().getBaseColumn(blockPos.getX(), blockPos.getZ(), context.heightAccessor());
		if (!columnOfBlocks.getBlock(blockPos.getY()).getFluidState().isEmpty()) {
			return Optional.empty();
		}

		/* Generate pieces of the structure.
		 Second-to-last boolean is only for structure intersections.
		 Last boolean means the structure will automatically be placed at ground level.
		 */
		return JigsawPlacement.addPieces(
				context,
				PoolElementStructurePiece::new,
				blockPos,
				false,
				false
		);
	}

	@NotNull
	private static BlockPos findSuitableSpot(PieceGeneratorSupplier.Context<JigsawConfiguration> context, BlockPos blockPos) {
		LevelHeightAccessor heightAccessor = context.heightAccessor();

		// Create a random generator that depends on the current chunk location. That way if the world is recreated
		// with the same seed the feature will end up at the same spot
		WorldgenRandom worldgenRandom = new WorldgenRandom(new LegacyRandomSource(context.seed()));
		worldgenRandom.setLargeFeatureSeed(context.seed(), context.chunkPos().x, context.chunkPos().z);

		// Pick a random y location between a low and a high point
		int height = worldgenRandom.nextIntBetweenInclusive(heightAccessor.getMinBuildHeight() + 3,
				-10);

		// Go down until I find a spot that has air. Then go down until I find a spot that is solid again
		NoiseColumn baseColumn = context.chunkGenerator().getBaseColumn(blockPos.getX(), blockPos.getZ(), heightAccessor);
		int tempHeight = height; // Remember the original Y height because I'll just use this if I can't find an air bubble
		int lowerLimit = heightAccessor.getMinBuildHeight() + 3; // Lower limit, don't go below this
		while (tempHeight > lowerLimit && !baseColumn.getBlock(tempHeight).isAir()) {
			tempHeight--;
		}
		// If I found air, I'll go down until I find a non-air block
		if (tempHeight > lowerLimit) {
			while (tempHeight > lowerLimit && baseColumn.getBlock(tempHeight).isAir()) {
				tempHeight--;
			}
			if (tempHeight > lowerLimit) {
				// Found a possible spawn spot
				height = tempHeight + 1;
			}
		}

		return blockPos.atY(height);
	}
}