package com.anonymoushacker1279.immersiveweapons.client.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;

import javax.annotation.Nullable;

public class SmokeBombParticleFactory implements IParticleFactory<SmokeBombParticleData> {

	private final IAnimatedSprite sprites;

	public SmokeBombParticleFactory(IAnimatedSprite sprite) {
		this.sprites = sprite;
	}

	@Nullable
	@Override
	public Particle makeParticle(SmokeBombParticleData smokeBombParticleData, ClientWorld world, double xPos, double yPos, double zPos, double xVelocity, double yVelocity, double zVelocity) {
		SmokeBombParticle newParticle = new SmokeBombParticle(world, xPos, yPos, zPos, xVelocity, yVelocity, zVelocity,
				smokeBombParticleData.getTint(), smokeBombParticleData.getDiameter(),
				sprites);
		newParticle.selectSpriteRandomly(sprites);  // choose a random sprite from the available list (in this case there is only one)
		return newParticle;
	}
}