package com.anonymoushacker1279.immersiveweapons.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import org.jetbrains.annotations.NotNull;

public class SmokeBombParticleFactory implements ParticleProvider<SmokeBombParticleData> {

	private final SpriteSet sprites;

	/**
	 * Constructor for SmokeBombParticleFactory.
	 * @param sprite an <code>IAnimatedSprite</code> instance
	 */
	public SmokeBombParticleFactory(SpriteSet sprite) {
		sprites = sprite;
	}

	/**
	 * Create a particle at the given coordinates with velocity.
	 * @param smokeBombParticleData a <code>SmokeBombParticleData</code> instance
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
	public Particle createParticle(SmokeBombParticleData smokeBombParticleData, @NotNull ClientLevel world, double xPos, double yPos, double zPos, double xVelocity, double yVelocity, double zVelocity) {
		SmokeBombParticle newParticle = new SmokeBombParticle(world, xPos, yPos, zPos, xVelocity, yVelocity, zVelocity,
				smokeBombParticleData.getTint(), smokeBombParticleData.getDiameter()
		);
		newParticle.pickSprite(sprites);  // choose a random sprite from the available list (in this case there is only one)
		return newParticle;
	}
}