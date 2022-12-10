package tech.anonymoushacker1279.immersiveweapons.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class MoonglowParticle extends TextureSheetParticle {

	protected static SpriteSet sprites;

	public static class Provider implements ParticleProvider<SimpleParticleType> {

		public Provider(SpriteSet pSprites) {
			sprites = pSprites;
		}

		@Override
		public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ,
		                               double pXSpeed, double pYSpeed, double pZSpeed) {

			return new MoonglowParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, sprites);
		}
	}

	protected MoonglowParticle(ClientLevel level, double x, double y, double z,
	                           double xSpeed, double ySpeed,
	                           double zSpeed, SpriteSet spriteSet) {

		super(level, x, y, z, 0.0D, 0.0D, 0.0D);
		friction = 0.96F;
		gravity = 0.01F;
		speedUpWhenYMotionIsBlocked = true;
		sprites = spriteSet;
		xd *= 0.0F;
		yd *= 0.0F;
		zd *= 0.0F;
		xd += xSpeed;
		yd += ySpeed;
		zd += zSpeed;
		rCol = 1.0F;
		gCol = 1.0F;
		bCol = 1.0F;
		quadSize *= 0.95F;
		lifetime = (int) (40.0D / (level.random.nextFloat() * 0.8D + 0.2D));
		pickSprite(spriteSet);
		hasPhysics = true;
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}
}