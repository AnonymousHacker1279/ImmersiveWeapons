package com.anonymoushacker1279.immersiveweapons.client.particle.smokebomb;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;
import org.jetbrains.annotations.NotNull;

public class SmokeBombParticleType extends ParticleType<SmokeBombParticleData> {

	/**
	 * Constructor for SmokeBombParticleType.
	 */
	public SmokeBombParticleType() {
		super(false, SmokeBombParticleData.DESERIALIZER);
	}

	/**
	 * Get the particle codec.
	 * @return Codec extending SmokeBombParticleData
	 */
	@Override
	public @NotNull Codec<SmokeBombParticleData> codec() {
		return SmokeBombParticleData.CODEC;
	}
}