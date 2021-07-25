package com.anonymoushacker1279.immersiveweapons.world.structures;

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

public class Campsite extends StructureFeature<NoneFeatureConfiguration> {
	/**
	 * Constructor for Campsite.
	 * @param codec the <code>Codec</code> extending NoFeatureConfig
	 */
	public Campsite(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	/**
	 * Get the factory start.
	 * @return IStartFactory extending NoFeatureConfig
	 */
	@Override
	public StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
		return Campsite.Start::new;
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
		 * @param structureIn the <code>Structure</code> extending NoFeatureConfig
		 * @param referenceIn the reference ID
		 * @param seedIn the world seed
		 */
		public Start(StructureFeature<NoneFeatureConfiguration> structureIn, ChunkPos chunkPos, int referenceIn, long seedIn) {
			super(structureIn, chunkPos, referenceIn, seedIn);
		}

		// TODO: Redo javadocs
		@Override
		public void generatePieces(RegistryAccess registryAccess, ChunkGenerator generator, StructureManager templateManagerIn, ChunkPos chunkPos, Biome biome, NoneFeatureConfiguration config, LevelHeightAccessor heightAccessor) {
			Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];

			// Turns the chunk coordinates into actual coordinates we can use. (Gets center of that chunk)
			int x = (chunkPos.x << 4) + 7;
			int z = (chunkPos.z << 4) + 7;

			// Finds the y value of the terrain at location.
			int surfaceY = generator.getBaseHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG, heightAccessor);
			BlockPos blockpos = new BlockPos(x, surfaceY, z);

			CampsitePieces.start(templateManagerIn, blockpos, rotation, pieces, random);

			// TODO: Does this work properly?
			createBoundingBox();
		}
	}
}