package com.anonymoushacker1279.immersiveweapons.client.particle;

import com.mojang.serialization.Codec;
import net.minecraft.particles.ParticleType;

public class SmokeBombParticleType extends ParticleType<SmokeBombParticleData> {

	private static final boolean ALWAYS_SHOW_REGARDLESS_OF_DISTANCE_FROM_PLAYER = false;

	public SmokeBombParticleType() {
		super(ALWAYS_SHOW_REGARDLESS_OF_DISTANCE_FROM_PLAYER, SmokeBombParticleData.DESERIALIZER);
	}

	// get the Codec used to
	// a) convert a FlameParticleData to a serialized format
	// b) construct a FlameParticleData object from the serialized format
	@Override
	public Codec<SmokeBombParticleData> codec() {
		return SmokeBombParticleData.CODEC;
	}
}