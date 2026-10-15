package tech.anonymoushacker1279.immersiveweapons.client.particle;

import net.minecraft.util.Mth;

/// Particle shaders have no per-particle data besides the vertex color, so a 9-bit seed (0-511) is hidden in the low
/// 3 bits of each color channel. The shader's vertex stage reads it back from the unlit color, so a particle that uses
/// this must pack its final channel values with [#pack] after setting its color.
public class ParticleSeedPacking {

	public static final int SEED_RANGE = 512;

	/// @param channel      the channel value, from 0 to 1
	/// @param seed         the seed, from 0 to 511
	/// @param channelIndex 0 for red, 1 for green, 2 for blue
	/// @return the channel with the seed bits embedded
	public static float pack(float channel, int seed, int channelIndex) {
		int bits = (seed >> (channelIndex * 3)) & 7;
		int value = Math.round(Mth.clamp(channel, 0.0F, 1.0F) * 255.0F);
		// The half-step keeps the value in the right byte whether it is later truncated or rounded
		return (((value & ~7) | bits) + 0.5F) / 255.0F;
	}
}
