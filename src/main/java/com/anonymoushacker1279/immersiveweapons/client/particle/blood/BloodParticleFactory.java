package com.anonymoushacker1279.immersiveweapons.client.particle.blood;

import com.anonymoushacker1279.immersiveweapons.client.particle.GenericParticle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import org.jetbrains.annotations.NotNull;

public class BloodParticleFactory  implements ParticleProvider<BloodParticleData> {

	private final SpriteSet sprites;

	/**
	 * Constructor for BloodParticleFactory.
	 * @param sprite an <code>IAnimatedSprite</code> instance
	 */
	public BloodParticleFactory(SpriteSet sprite) {
		sprites = sprite;
	}

	/**
	 * Create a particle at the given coordinates with velocity.
	 * @param bloodParticleData a <code>BloodParticleData</code> instance
	 * @param world the <code>ClientWorld</code> the particle is in
	 * @param xPos the X position to spawn at
	 * @param yPos the Y position to spawn at
	 * @param zPos the Z position to spawn at
	 * @param xVelocity the X velocity to spawn with
	 * @param yVelocity the Y velocity to spawn with
	 * @param zVelocity the Z velocity to spawn with
	 * @return Particle
	 */
	@Override
	public Particle createParticle(BloodParticleData bloodParticleData, @NotNull ClientLevel world, double xPos, double yPos, double zPos, double xVelocity, double yVelocity, double zVelocity) {
		GenericParticle newParticle = new GenericParticle(world, xPos, yPos, zPos, xVelocity, yVelocity, zVelocity,
				bloodParticleData.getTint(), bloodParticleData.getDiameter()
		);
		newParticle.pickSprite(sprites);  // choose a random sprite from the available list (in this case there is only one)
		return newParticle;
	}
}