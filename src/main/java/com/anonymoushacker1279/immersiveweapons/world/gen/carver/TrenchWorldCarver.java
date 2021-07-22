package com.anonymoushacker1279.immersiveweapons.world.gen.carver;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

public class TrenchWorldCarver extends WorldCarver<ProbabilityConfig> {

	/**
	 * Constructor for TrenchWorldCarver.
	 * @param codec the <code>Codec</code> extending ProbabilityConfig
	 * @param maxHeight the max height
	 */
	public TrenchWorldCarver(Codec<ProbabilityConfig> codec, int maxHeight) {
		super(codec, maxHeight);
	}

	/**
	 * Carve an area of land.
	 * @param chunk the <code>IChunk</code>
	 * @param biomePos the <code>Function</code>
	 * @param rand a <code>Random</code> instance
	 * @param seaLevel the sea level
	 * @param chunkXOffset the X chunk offset
	 * @param chunkZOffset the Z chunk offset
	 * @param chunkX the chunk X position
	 * @param chunkZ the chunk Z position
	 * @param carvingMask the <code>BitSet</code> carving mask
	 * @param config the <code>ProbabilityConfig</code>
	 * @return boolean
	 */
	@Override
	public boolean carve(IChunk chunk, Function<BlockPos, Biome> biomePos, Random rand, int seaLevel, int chunkXOffset, int chunkZOffset, int chunkX, int chunkZ, BitSet carvingMask, ProbabilityConfig config) {
		int i = (getRange() * 2 - 1) * 16;
		double d0 = chunkXOffset * 16 + rand.nextInt(16);
		double d2 = chunkZOffset * 16 + rand.nextInt(16);
		double d1 = rand.nextInt(rand.nextInt(40) + 8) + chunk.getHeight(Type.WORLD_SURFACE_WG, (int) d0, (int) d2) - 2;
		float f = rand.nextFloat() * ((float) Math.PI * 2.0f);
		float f1 = (rand.nextFloat() - 0.5F) * 2.0F / 16.0F;
		float f2 = (rand.nextFloat() * 1.5F + rand.nextFloat()) * 1.5F;
		int j = i - rand.nextInt(i / 8);
		doMathToCarveRegion(chunk, biomePos, rand.nextLong(), seaLevel, chunkX, chunkZ, d0, d1, d2, f2, f, f1, 0, j, carvingMask);
		return true;
	}

	/**
	 *
	 * @param chunk the <code>IChunk</code>
	 * @param biomePos the <code>Function</code>
	 * @param rand a <code>Random</code> instance
	 * @param seaLevel the sea level
	 * @param chunkX the chunk X position
	 * @param chunkZ the chunk Z position
	 * @param randOffsetXCoord the random X offset
	 * @param startY the starting Y level
	 * @param randOffsetZCoord the random Z offset
	 * @param int3 an int; not sure what it does because obfuscations
	 * @param int5 another int; not sure what it does because obfuscations
	 * @param int4 another int; not sure what it does because obfuscations
	 * @param int1 another int; not sure what it does because obfuscations
	 * @param int2 another int; not sure what it does because obfuscations
	 * @param carvingMask the <code>BitSet</code> carving mask
	 */
	private void doMathToCarveRegion(IChunk chunk, Function<BlockPos, Biome> biomePos, long rand, int seaLevel, int chunkX, int chunkZ, double randOffsetXCoord, double startY, double randOffsetZCoord, float int3, float int5, float int4, int int1, int int2, BitSet carvingMask) {
		Random random = new Random(rand);

		float f4 = 0.0F;
		float f1 = 0.0F;

		for (int j = int1; j < int2; ++j) {
			double d0 = MathHelper.sin((float) j * (float) Math.PI / (float) int2) * int3;
			double d1 = d0;
			d0 = d0 * ((double) random.nextFloat() * 0.25D + 0.75D);
			d1 = d1 * ((double) random.nextFloat() * 0.25D + 0.75D);
			float f2 = MathHelper.cos(int4);
			float f3 = MathHelper.sin(int4);
			randOffsetXCoord += MathHelper.cos(int5) * f2;
			startY += f3;
			randOffsetZCoord += MathHelper.sin(int5) * f2;
			int4 = int4 * 0.7F; // These do something with the width/length of carving I believe
			int4 = int4 + f1 * 0.05F;
			int5 += f4 * 0.05F;
			f1 = f1 * 0.8F; // No idea what these do
			f4 = f4 * 0.5F;
			f1 = f1 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 1.5F;
			f4 = f4 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
			if (random.nextInt(4) != 0) {
				if (!canReach(chunkX, chunkZ, randOffsetXCoord, randOffsetZCoord, j, int2, int3)) {
					return;
				}
				carveSphere(chunk, biomePos, rand, seaLevel, chunkX, chunkZ, randOffsetXCoord, startY, randOffsetZCoord, d0, d1, carvingMask);
			}
		}

	}

	/**
	 * Determine if a chunk is the starting one.
	 * @param rand a <code>Random</code> instance
	 * @param chunkX the chunk X position
	 * @param chunkZ the chunk Z position
 	 * @param config the <code>ProbabilityConfig</code> instance
	 * @return boolean
	 */
	@Override
	public boolean isStartChunk(Random rand, int chunkX, int chunkZ, ProbabilityConfig config) {
		return rand.nextFloat() <= config.probability;
	}

	/**
	 * I believe this checks if a chunk should be skipped. Hard telling though.
	 * @param double1 a double
	 * @param double2 another double
	 * @param double3 another double
	 * @param double4 another double
	 * @return boolean
	 */
	@Override
	protected boolean skip(double double1, double double2, double double3, int double4) {
		return false;
	}
}