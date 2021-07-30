package com.anonymoushacker1279.immersiveweapons.world.structures;

import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class LandmineTrap extends StructureFeature<NoneFeatureConfiguration> {
	/**
	 * Constructor for LandmineTrap.
	 * @param codec the <code>Codec</code> extending NoFeatureConfig
	 */
	public LandmineTrap(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	/**
	 * Get the factory start.
	 * @return IStartFactory extending NoFeatureConfig
	 */
	@Override
	public StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
		return LandmineTrap.Start::new;
	}

	/**
	 * Get the generation stage.
	 * @return Decoration
	 */
	@Override
	public Decoration step() {
		return Decoration.SURFACE_STRUCTURES;
	}


	public static class Start extends StructureStart<NoneFeatureConfiguration> {
		/**
		 * Constructor for Start.
		 * @param structureIn the <code>StructureFeature</code> extending NoFeatureConfig
		 * @param chunkPos the <code>ChunkPos</code> position
		 * @param referenceIn the reference ID
		 * @param seedIn the world seed
		 */
		public Start(StructureFeature<NoneFeatureConfiguration> structureIn, ChunkPos chunkPos, int referenceIn, long seedIn) {
			super(structureIn, chunkPos, referenceIn, seedIn);
		}

		/**
		 * Generate structure pieces.
		 * @param registryAccess the <code>RegistryAccess</code> instance
		 * @param generator the <code>ChunkGenerator</code>
		 * @param structureManager the <code>StructureManager</code>
		 * @param chunkPos the <code>ChunkPos</code> position
		 * @param biome the <code>Biome</code> the structure is in
		 * @param config the <code>NoneFeatureConfiguration</code> instance
		 * @param heightAccessor the <code>LevelHeightAccessor</code> instance
		 */
		@Override
		public void generatePieces(RegistryAccess registryAccess, ChunkGenerator generator, StructureManager structureManager, ChunkPos chunkPos, Biome biome, NoneFeatureConfiguration config, LevelHeightAccessor heightAccessor) {
			Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];

			// Turns the chunk coordinates into actual coordinates we can use.
			int x = (chunkPos.x << 4) + GeneralUtilities.getRandomNumber(0, 17);
			int z = (chunkPos.z << 4) + GeneralUtilities.getRandomNumber(0, 17);

			// Finds the y value of the terrain at location.
			int surfaceY = generator.getBaseHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG, heightAccessor);
			BlockPos blockpos = new BlockPos(x, surfaceY, z);

			LandmineTrapPieces.start(structureManager, blockpos, rotation, pieces);

			createBoundingBox();
		}
	}
}