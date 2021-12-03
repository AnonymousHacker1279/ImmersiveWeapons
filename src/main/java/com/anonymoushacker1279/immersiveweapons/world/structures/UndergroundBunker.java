package com.anonymoushacker1279.immersiveweapons.world.structures;
// TODO: Rework when Forge API updates
/*
public class UndergroundBunker extends StructureFeature<NoneFeatureConfiguration> {

	*/
/**
	 * Constructor for UndergroundBunker.
	 *
	 * @param codec the <code>Codec</code> extending NoneFeatureConfiguration
	 *//*

	public UndergroundBunker(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	*/
/**
	 * Get the factory start.
	 *
	 * @return IStartFactory extending NoneFeatureConfiguration
	 *//*

	@Override
	public @NotNull StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
		return UndergroundBunker.Start::new;
	}

	*/
/**
	 * Get the generation stage.
	 *
	 * @return Decoration
	 *//*

	@Override
	public @NotNull Decoration step() {
		return Decoration.TOP_LAYER_MODIFICATION;
	}


	public static class Start extends StructureStart<NoneFeatureConfiguration> {
		*/
/**
		 * Constructor for Start.
		 *
		 * @param structure the <code>StructureFeature</code> extending NoneFeatureConfiguration
		 * @param chunkPos  the <code>ChunkPos</code> position
		 * @param reference the reference ID
		 * @param seed      the world seed
		 *//*

		public Start(StructureFeature<NoneFeatureConfiguration> structure, ChunkPos chunkPos, int reference, long seed) {
			super(structure, chunkPos, reference, seed);
		}

		*/
/**
		 * Generate structure pieces.
		 *
		 * @param registryAccess   the <code>RegistryAccess</code> instance
		 * @param generator        the <code>ChunkGenerator</code>
		 * @param structureManager the <code>StructureManager</code>
		 * @param chunkPos         the <code>ChunkPos</code> position
		 * @param biome            the <code>Biome</code> the structure is in
		 * @param config           the <code>NoneFeatureConfiguration</code> instance
		 * @param heightAccessor   the <code>LevelHeightAccessor</code> instance
		 *//*

		@Override
		public void generatePieces(@NotNull RegistryAccess registryAccess, ChunkGenerator generator, @NotNull StructureManager structureManager, ChunkPos chunkPos, @NotNull Biome biome, @NotNull NoneFeatureConfiguration config, @NotNull LevelHeightAccessor heightAccessor) {
			Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];

			// Turns the chunk coordinates into actual coordinates we can use. (Gets center of that chunk)
			int x = (chunkPos.x << 4) + GeneralUtilities.getRandomNumber(0, 17);
			int z = (chunkPos.z << 4) + GeneralUtilities.getRandomNumber(0, 17);

			// Finds the y value of the terrain at location.
			int surfaceY = generator.getBaseHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG, heightAccessor);
			BlockPos blockpos = new BlockPos(x, surfaceY - 7, z);

			UndergroundBunkerPieces.start(structureManager, blockpos, rotation, pieces);

			createBoundingBox();
		}
	}
}*/