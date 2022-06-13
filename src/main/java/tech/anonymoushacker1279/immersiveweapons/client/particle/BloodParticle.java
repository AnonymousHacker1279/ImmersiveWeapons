package tech.anonymoushacker1279.immersiveweapons.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class BloodParticle extends TextureSheetParticle {

	protected static SpriteSet sprites;

	public static class Provider implements ParticleProvider<SimpleParticleType> {

		public Provider(SpriteSet pSprites) {
			sprites = pSprites;
		}

		@Override
		public Particle createParticle(@NotNull SimpleParticleType pType, @NotNull ClientLevel pLevel, double pX, double pY, double pZ,
		                               double pXSpeed, double pYSpeed, double pZSpeed) {

			return new BloodParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, sprites);
		}
	}

	protected BloodParticle(ClientLevel level, double x, double y, double z,
	                        double xSpeed, double ySpeed,
	                        double zSpeed, SpriteSet spriteSet) {

		super(level, x, y, z, 0.0D, 0.0D, 0.0D);
		friction = 0.96F;
		gravity = (float) 0.2;
		speedUpWhenYMotionIsBlocked = true;
		sprites = spriteSet;
		xd *= (float) 0.0;
		yd *= (float) 0.0;
		zd *= (float) 0.0;
		xd += xSpeed;
		yd += ySpeed;
		zd += zSpeed;
		float vibrancyModifier = level.random.nextFloat();
		rCol = vibrancyModifier;
		gCol = vibrancyModifier;
		bCol = vibrancyModifier;
		quadSize *= 0.75F * (float) 0.5;
		lifetime = (int) ((double) 40 / ((double) level.random.nextFloat() * 0.8D + 0.2D));
		lifetime = (int) ((float) lifetime * (float) 0.5);
		lifetime = Math.max(lifetime, 1);
		setSpriteFromAge(spriteSet);
		hasPhysics = true;
	}

	@Override
	public @NotNull ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	@Override
	public float getQuadSize(float pScaleFactor) {
		return quadSize * Mth.clamp(((float) age + pScaleFactor) / (float) lifetime * 32.0F, 0.0F, 1.0F);
	}

	@Override
	public void tick() {
		super.tick();
		setSpriteFromAge(sprites);
	}
}