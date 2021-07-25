package com.anonymoushacker1279.immersiveweapons.client.particle;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;

public class SmokeBombParticleType extends ParticleType<SmokeBombParticleData> {

	private static final boolean ALWAYS_SHOW_REGARDLESS_OF_DISTANCE_FROM_PLAYER = false;

	/**
	 * Constructor for SmokeBombParticleType.
	 */
	public SmokeBombParticleType() {
		super(ALWAYS_SHOW_REGARDLESS_OF_DISTANCE_FROM_PLAYER, SmokeBombParticleData.DESERIALIZER);
	}

	/**
	 * Get the particle codec.
	 * @return Codec extending SmokeBombParticleData
	 */
	@Override
	public Codec<SmokeBombParticleData> codec() {
		return SmokeBombParticleData.CODEC;
	}
}