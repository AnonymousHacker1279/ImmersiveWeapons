package com.anonymoushacker1279.immersiveweapons.client.particle.blood;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;
import org.jetbrains.annotations.NotNull;

public class BloodParticleType extends ParticleType<BloodParticleData> {

	/**
	 * Constructor for BloodParticleType.
	 */
	public BloodParticleType() {
		super(false, BloodParticleData.DESERIALIZER);
	}

	/**
	 * Get the particle codec.
	 * @return Codec extending BloodParticleData
	 */
	@Override
	public @NotNull Codec<BloodParticleData> codec() {
		return BloodParticleData.CODEC;
	}
}