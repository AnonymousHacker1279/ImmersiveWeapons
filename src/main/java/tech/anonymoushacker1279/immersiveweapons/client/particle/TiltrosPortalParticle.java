package tech.anonymoushacker1279.immersiveweapons.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class TiltrosPortalParticle extends PortalParticle {

	protected static SpriteSet sprites;

	public static class Provider implements ParticleProvider<SimpleParticleType> {

		public Provider(SpriteSet pSprites) {
			sprites = pSprites;
		}

		@Override
		public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ,
		                               double pXSpeed, double pYSpeed, double pZSpeed) {

			PortalParticle particle = new TiltrosPortalParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
			particle.pickSprite(sprites);
			return particle;
		}
	}

	public TiltrosPortalParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
		super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
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
}