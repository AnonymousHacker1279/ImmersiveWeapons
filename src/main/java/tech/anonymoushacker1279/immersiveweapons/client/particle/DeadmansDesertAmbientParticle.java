package tech.anonymoushacker1279.immersiveweapons.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;

public class DeadmansDesertAmbientParticle extends TextureSheetParticle {

	protected static SpriteSet sprites;

	public static class Provider implements ParticleProvider<SimpleParticleType> {

		public Provider(SpriteSet pSprites) {
			sprites = pSprites;
		}

		@Override
		public Particle createParticle(@NotNull SimpleParticleType pType, @NotNull ClientLevel pLevel, double pX, double pY, double pZ,
		                               double pXSpeed, double pYSpeed, double pZSpeed) {

			return new DeadmansDesertAmbientParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, sprites);
		}
	}

	protected DeadmansDesertAmbientParticle(ClientLevel level, double x, double y, double z,
	                                        double xSpeed, double ySpeed,
	                                        double zSpeed, SpriteSet spriteSet) {

		super(level, x, y, z, xSpeed, ySpeed, zSpeed);
		friction = 0.66F;
		gravity = 0.16F;
		speedUpWhenYMotionIsBlocked = true;
		sprites = spriteSet;
		xd *= 0.0F;
		yd *= 0.0F;
		zd *= 0.0F;
		xd += xSpeed + (level.random.nextFloat() * 0.63F);
		yd += ySpeed;
		zd += zSpeed + (level.random.nextFloat() * 0.63F);
		rCol = 1.0F;
		gCol = 1.0F;
		bCol = 1.0F;
		quadSize *= 0.8F;
		lifetime = (int) (200.0D / (level.random.nextFloat() * 0.4D + 0.6D));
		pickSprite(spriteSet);
		hasPhysics = true;
	}

	@Override
	public @NotNull ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}
}