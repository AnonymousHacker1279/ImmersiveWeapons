package com.anonymoushacker1279.immersiveweapons.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;

import java.awt.*;

public class SmokeBombParticle extends SpriteTexturedParticle {

	@SuppressWarnings("unused")
	private final IAnimatedSprite sprites;
	Minecraft mc = Minecraft.getInstance();
	private final double xPos;
	private final double yPos;
	private final double zPos;

	public SmokeBombParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Color tint, double diameter, IAnimatedSprite sprites) {
		super(world, x, y, z, velocityX, velocityY, velocityZ);
		this.sprites = sprites;

		xPos = x;
		yPos = y;
		zPos = z;

		setColor(tint.getRed() / 255.0F, tint.getGreen() / 255.0F, tint.getBlue() / 255.0F);
		setSize((float) diameter, (float) diameter);    // the size (width, height) of the collision box.

		final float PARTICLE_SCALE_FOR_ONE_METER = 0.5F; //  if the particleScale is 0.5, the texture will be rendered as 1 meter high
		quadSize = PARTICLE_SCALE_FOR_ONE_METER * (float) diameter; // sets the rendering size of the particle for a TexturedParticle.

		lifetime = 500;  // lifetime in ticks: 100 ticks = 5 seconds

		final float ALPHA_VALUE = 0.95F;
		this.alpha = ALPHA_VALUE;

		//the vanilla Particle constructor added random variation to our starting velocity.  Undo it!
		xd = velocityX;
		yd = velocityY;
		zd = velocityZ;

		this.hasPhysics = true;  // the move() method will check for collisions with scenery
	}

	@Override
	protected int getLightColor(float partialTick) {
		BlockPos blockPos = new BlockPos(xPos, yPos, zPos).above();
		int lightAtParticleLocation = mc.level.getMaxLocalRawBrightness(blockPos);    // Get the light level at the current position
		final int BLOCK_LIGHT = lightAtParticleLocation;
		final int SKY_LIGHT = lightAtParticleLocation;
		final int FULL_BRIGHTNESS_VALUE = LightTexture.pack(BLOCK_LIGHT, SKY_LIGHT);
		return FULL_BRIGHTNESS_VALUE;
	}

	@Override
	public IParticleRenderType getRenderType() {
		return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	/**
	 * call once per tick to update the Particle position, calculate collisions, remove when max lifetime is reached, etc
	 */
	@Override
	public void tick() {

		xo = x;
		yo = y;
		zo = z;

		move(xd, yd, zd);
		if (onGround) {
			this.remove();
		}

		if (yo == y && yd > 0) {
			this.remove();
		}

		if (this.age++ >= this.lifetime) {
			this.remove();
		}
	}
}