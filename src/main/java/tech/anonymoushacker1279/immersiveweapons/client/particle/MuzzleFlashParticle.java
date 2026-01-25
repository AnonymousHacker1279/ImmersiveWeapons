package tech.anonymoushacker1279.immersiveweapons.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;

public class MuzzleFlashParticle extends SingleQuadParticle {

	protected static SpriteSet sprites;

	protected MuzzleFlashParticle(ClientLevel level, double x, double y, double z,
	                              double xSpeed, double ySpeed,
	                              double zSpeed, SpriteSet spriteSet) {

		super(level, x, y, z, spriteSet.first());
		friction = 0.46F;
		gravity = (float) 0.075;
		speedUpWhenYMotionIsBlocked = true;
		sprites = spriteSet;
		xd *= (float) 0.0;
		yd *= (float) 0.0;
		zd *= (float) 0.0;
		xd += xSpeed;
		yd += ySpeed;
		zd += zSpeed;
		float vibrancyModifier = 0.6F + level.random.nextFloat() * 0.4F;
		rCol = vibrancyModifier;
		gCol = vibrancyModifier;
		bCol = vibrancyModifier;
		quadSize *= 1.125F;
		lifetime = (int) ((double) 20 / ((double) level.random.nextFloat() * 0.1D + 0.9D));
		lifetime = (int) ((float) lifetime * 0.075F);
		lifetime = Math.max(lifetime, 1);
		setSpriteFromAge(spriteSet);
		hasPhysics = true;
		alpha = 0.85f;
	}

	@Override
	protected Layer getLayer() {
		return Layer.TRANSLUCENT;
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

	@Override
	protected int getLightColor(float pPartialTick) {
		return 255;
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {

		public Provider(SpriteSet pSprites) {
			sprites = pSprites;
		}

		@Nullable
		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
			return sprites != null ? new MuzzleFlashParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, sprites) : null;
		}
	}
}