package com.anonymoushacker1279.immersiveweapons.world.structures;

import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import com.mojang.serialization.Codec;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class BearTrap extends Structure<NoFeatureConfig> {
	/**
	 * Constructor for BearTrap.
	 * @param codec the <code>Codec</code> extending NoFeatureConfig
	 */
	public BearTrap(Codec<NoFeatureConfig> codec) {
		super(codec);
	}

	/**
	 * Get the factory start.
	 * @return IStartFactory extending NoFeatureConfig
	 */
	@Override
	public IStartFactory<NoFeatureConfig> getStartFactory() {
		return BearTrap.Start::new;
	}

	/**
	 * Get the generation stage.
	 * @return Decoration
	 */
	@Override
	public Decoration step() {
		return Decoration.SURFACE_STRUCTURES;
	}


	public static class Start extends StructureStart<NoFeatureConfig> {
		/**
		 * Constructor for Start.
		 * @param structureIn the <code>Structure</code> extending NoFeatureConfig
		 * @param chunkX the chunk X position
		 * @param chunkZ the chunk Z position
		 * @param mutableBoundingBox a <code>MutableBoundingBox</code> instance
		 * @param referenceIn the reference ID
		 * @param seedIn the world seed
		 */
		public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
			super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
		}

		/**
		 * Generate structure pieces.
		 * @param dynamicRegistryManager the <code>DynamicRegistries</code> instance
		 * @param generator the <code>ChunkGenerator</code> instance
		 * @param templateManagerIn the <code>TemplateManager</code> instance
		 * @param chunkX the chunk X position
		 * @param chunkZ the chunk Z position
		 * @param biomeIn the <code>Biome</code> instance
		 * @param config the <code>NoFeatureConfig</code> instance
		 */
		@Override
		public void generatePieces(DynamicRegistries dynamicRegistryManager, ChunkGenerator generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, NoFeatureConfig config) {

			Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];

			// Turns the chunk coordinates into actual coordinates we can use.
			int x = (chunkX << 4) + GeneralUtilities.getRandomNumber(0, 17);
			int z = (chunkZ << 4) + GeneralUtilities.getRandomNumber(0, 17);

			// Finds the y value of the terrain at location.
			int surfaceY = generator.getBaseHeight(x, z, Heightmap.Type.WORLD_SURFACE_WG);
			BlockPos blockpos = new BlockPos(x, surfaceY, z);

			BearTrapPieces.start(templateManagerIn, blockpos, rotation, pieces, random);

			calculateBoundingBox();
		}
	}
}