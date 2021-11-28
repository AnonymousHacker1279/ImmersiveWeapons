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
import org.jetbrains.annotations.NotNull;

public class LandmineTrap extends StructureFeature<NoneFeatureConfiguration> {
	/**
	 * Constructor for LandmineTrap.
	 *
	 * @param codec the <code>Codec</code> extending NoneFeatureConfiguration
	 */
	public LandmineTrap(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	/**
	 * Get the factory start.
	 *
	 * @return IStartFactory extending NoneFeatureConfiguration
	 */
	@Override
	public @NotNull StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
		return LandmineTrap.Start::new;
	}

	/**
	 * Get the generation stage.
	 *
	 * @return Decoration
	 */
	@Override
	public @NotNull Decoration step() {
		return Decoration.SURFACE_STRUCTURES;
	}


	public static class Start extends StructureStart<NoneFeatureConfiguration> {
		/**
		 * Constructor for Start.
		 *
		 * @param structure the <code>StructureFeature</code> extending NoneFeatureConfiguration
		 * @param chunkPos  the <code>ChunkPos</code> position
		 * @param reference the reference ID
		 * @param seed      the world seed
		 */
		public Start(StructureFeature<NoneFeatureConfiguration> structure, ChunkPos chunkPos, int reference, long seed) {
			super(structure, chunkPos, reference, seed);
		}

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
		 */
		@Override
		public void generatePieces(@NotNull RegistryAccess registryAccess, ChunkGenerator generator, @NotNull StructureManager structureManager, ChunkPos chunkPos, @NotNull Biome biome, @NotNull NoneFeatureConfiguration config, @NotNull LevelHeightAccessor heightAccessor) {
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