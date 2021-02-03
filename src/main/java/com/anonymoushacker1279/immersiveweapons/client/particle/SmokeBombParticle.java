package com.anonymoushacker1279.immersiveweapons.client.particle;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;

public class SmokeBombParticle extends SpriteTexturedParticle {

	private double xPos;
	private double yPos;
	private double zPos;
	
	Minecraft mc = Minecraft.getInstance();
	
	public SmokeBombParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Color tint, double diameter, IAnimatedSprite sprites) {
		super(world, x, y, z, velocityX, velocityY, velocityZ);
		this.sprites = sprites;
		
		xPos = x;
		yPos = y;
		zPos = z;
		
		setColor(tint.getRed()/255.0F, tint.getGreen()/255.0F, tint.getBlue()/255.0F);
		setSize((float)diameter, (float)diameter);    // the size (width, height) of the collision box.
		
		final float PARTICLE_SCALE_FOR_ONE_METER = 0.5F; //  if the particleScale is 0.5, the texture will be rendered as 1 meter high
		particleScale = PARTICLE_SCALE_FOR_ONE_METER * (float)diameter; // sets the rendering size of the particle for a TexturedParticle.
		
		maxAge = 500;  // lifetime in ticks: 100 ticks = 5 seconds
		
		final float ALPHA_VALUE = 0.95F;
		this.particleAlpha = ALPHA_VALUE;
		
		//the vanilla Particle constructor added random variation to our starting velocity.  Undo it!
		motionX = velocityX;
		motionY = velocityY;
		motionZ = velocityZ;
		
		this.canCollide = true;  // the move() method will check for collisions with scenery
	}

		@Override
		protected int getBrightnessForRender(float partialTick) {
			BlockPos blockPos = new BlockPos(xPos, yPos, zPos).up();
			int lightAtParticleLocation = mc.world.getLight(blockPos);	// Get the light level at the current position
			final int BLOCK_LIGHT = lightAtParticleLocation;
			final int SKY_LIGHT = lightAtParticleLocation;
			final int FULL_BRIGHTNESS_VALUE = LightTexture.packLight(BLOCK_LIGHT, SKY_LIGHT);
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
			
			prevPosX = posX;
			prevPosY = posY;
			prevPosZ = posZ;
			
			move(motionX, motionY, motionZ);
			if (onGround) {
				this.setExpired();
			}
			
			if (prevPosY == posY && motionY > 0) {
				this.setExpired();
			}
			
			if (this.age++ >= this.maxAge) {
				this.setExpired();
			}
		}
		
		@SuppressWarnings("unused")
		private final IAnimatedSprite sprites;
}
