package tech.anonymoushacker1279.immersiveweapons.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;

public class MoonglowParticle extends SingleQuadParticle {

	protected static SpriteSet sprites;

	protected MoonglowParticle(ClientLevel level, double x, double y, double z,
	                           double xSpeed, double ySpeed,
	                           double zSpeed, SpriteSet spriteSet) {

		super(level, x, y, z, spriteSet.first());
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
		lifetime = (int) (40.0D / (level.getRandom().nextFloat() * 0.8D + 0.2D));
		setSprite(spriteSet.get(level.getRandom()));
		hasPhysics = true;
	}

	@Override
	protected Layer getLayer() {
		return Layer.OPAQUE;
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {

		public Provider(SpriteSet pSprites) {
			sprites = pSprites;
		}

		@Nullable
		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
			return sprites != null ? new MoonglowParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, sprites) : null;
		}
	}
}