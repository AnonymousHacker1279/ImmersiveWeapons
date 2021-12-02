package com.anonymoushacker1279.immersiveweapons.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class GenericParticle extends TextureSheetParticle {

	private final double xPos;
	private final double yPos;
	private final double zPos;

	/**
	 * Constructor for GenericParticle.
	 *
	 * @param world     the <code>ClientWorld</code> the particle is in
	 * @param x         the X position to spawn at
	 * @param y         the Y position to spawn at
	 * @param z         the Z position to spawn at
	 * @param velocityX the X velocity to spawn with
	 * @param velocityY the Y velocity to spawn with
	 * @param velocityZ the Z velocity to spawn with
	 * @param tint      the <code>Color</code> to spawn with
	 * @param diameter  the diameter to spawn at
	 */
	public GenericParticle(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Color tint, double diameter) {
		super(world, x, y, z, velocityX, velocityY, velocityZ);

		xPos = x;
		yPos = y;
		zPos = z;

		setColor(tint.getRed() / 255.0F, tint.getGreen() / 255.0F, tint.getBlue() / 255.0F);
		setSize((float) diameter, (float) diameter);    // the size (width, height) of the collision box.

		final float PARTICLE_SCALE_FOR_ONE_METER = 0.5F; //  if the particleScale is 0.5, the texture will be rendered as 1 meter high
		quadSize = PARTICLE_SCALE_FOR_ONE_METER * (float) diameter; // sets the rendering size of the particle for a TexturedParticle.

		lifetime = 500;  // lifetime in ticks: 100 ticks = 5 seconds

		alpha = 0.95F; // transparency

		//the vanilla Particle constructor added random variation to our starting velocity. Undo it!
		xd = velocityX;
		yd = velocityY;
		zd = velocityZ;

		hasPhysics = true;  // the move() method will check for collisions with scenery
	}

	/**
	 * Get the lighting color.
	 *
	 * @param partialTick the current partial tick
	 * @return int
	 */
	@Override
	protected int getLightColor(float partialTick) {
		BlockPos blockPos = new BlockPos(xPos, yPos, zPos).above();
		int lightAtParticleLocation = 0;    // Get the light level at the current position
		Minecraft mc = Minecraft.getInstance();
		if (mc.level != null) {
			lightAtParticleLocation = mc.level.getMaxLocalRawBrightness(blockPos);
		}
		int BLOCK_LIGHT = lightAtParticleLocation;
		int SKY_LIGHT = lightAtParticleLocation;
		return LightTexture.pack(BLOCK_LIGHT, SKY_LIGHT);
	}

	/**
	 * Get the render type.
	 *
	 * @return IParticleRenderType
	 */
	@Override
	public @NotNull ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	/**
	 * Runs once per tick to update particle data.
	 */
	@Override
	public void tick() {
		xo = x;
		yo = y;
		zo = z;

		move(xd, yd, zd);
		if (onGround) {
			remove();
		}
		if (yo == y && yd > 0) {
			remove();
		}
		if (age++ >= lifetime) {
			remove();
		}
	}
}