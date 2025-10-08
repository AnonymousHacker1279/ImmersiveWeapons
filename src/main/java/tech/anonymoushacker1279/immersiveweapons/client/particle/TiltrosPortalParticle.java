package tech.anonymoushacker1279.immersiveweapons.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.PortalParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;

public class TiltrosPortalParticle extends PortalParticle {

	protected static SpriteSet sprites;

	public TiltrosPortalParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
		super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, sprites.first());
	}

	@Override
	public void tick() {
		super.tick();

		float ageRatio = (float) this.age / (float) this.lifetime;
		ageRatio *= ageRatio;
		ageRatio *= ageRatio;

		int redComponent = 11;
		int greenComponent = (int) (ageRatio * 71.0F);
		if (greenComponent > 57) {
			greenComponent = 57;
		}
		int blueComponent = 79;

		setColor((float) redComponent / 255.0F, (float) greenComponent / 255.0F, (float) blueComponent / 255.0F);
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {

		public Provider(SpriteSet pSprites) {
			sprites = pSprites;
		}

		@Nullable
		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
			return sprites != null ? new TiltrosPortalParticle(level, x, y, z, xSpeed, ySpeed, zSpeed) : null;
		}
	}
}