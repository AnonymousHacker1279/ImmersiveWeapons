package tech.anonymoushacker1279.immersiveweapons.client.particle.smoke_grenade;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;

public class SmokeGrenadeParticle extends TextureSheetParticle {

	@Nullable
	protected static SpriteSet sprites;

	public static class Provider implements ParticleProvider<SmokeGrenadeParticleOptions> {

		public Provider(SpriteSet pSprites) {
			sprites = pSprites;
		}

		@Override
		public Particle createParticle(SmokeGrenadeParticleOptions pType, ClientLevel pLevel, double pX, double pY, double pZ,
		                               double pXSpeed, double pYSpeed, double pZSpeed) {

			if (sprites != null) {
				return new SmokeGrenadeParticle(pLevel, pX, pY, pZ,
						pXSpeed, pYSpeed, pZSpeed, sprites, pType.getColor());
			}
			return null;
		}
	}

	protected SmokeGrenadeParticle(ClientLevel level, double x, double y, double z,
	                               double xSpeed, double ySpeed,
	                               double zSpeed, SpriteSet spriteSet, Vector3f color) {

		super(level, x, y, z, 0.0D, 0.0D, 0.0D);
		friction = 0.96F;
		boolean fancyParticles = IWConfigs.CLIENT.fancySmokeGrenadeParticles.getAsBoolean();
		gravity = fancyParticles ? 0.02F : 0.05F;
		speedUpWhenYMotionIsBlocked = true;
		sprites = spriteSet;
		xd *= 1.0F;
		yd *= 1.0F;
		zd *= 1.0F;
		xd += fancyParticles ? xSpeed * 0.4f : xSpeed;
		yd += fancyParticles ? ySpeed * 0.4f : ySpeed;
		zd += fancyParticles ? zSpeed * 0.4f : zSpeed;
		float vibrancyModifier = level.random.nextFloat() * 0.4F + 0.6F;
		rCol = randomizeColor(color.x(), vibrancyModifier);
		gCol = randomizeColor(color.y(), vibrancyModifier);
		bCol = randomizeColor(color.z(), vibrancyModifier);
		alpha = fancyParticles ? 0.97F : 1.0F;
		quadSize *= 0.75F * 35.0F;
		lifetime = (int) ((double) 90 / ((double) level.random.nextFloat() * 0.8D + 0.2D));
		lifetime = (int) ((float) lifetime * (35.0F * 0.1F));
		lifetime = Math.max(lifetime, 1);
		setSpriteFromAge(spriteSet);
		hasPhysics = true;
	}

	protected float randomizeColor(float color, float multiplier) {
		return (random.nextFloat() * 0.05F + 0.95F) * color * multiplier;
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	@Override
	public float getQuadSize(float pScaleFactor) {
		float size = quadSize * Mth.clamp(((float) age + pScaleFactor) / (float) lifetime * 32.0F, 0.0F, 1.0F);

		if (IWConfigs.CLIENT.fancySmokeGrenadeParticles.getAsBoolean()) {
			size *= 0.66F;
		}

		return size;
	}

	@Override
	public void tick() {
		super.tick();
		if (sprites != null) {
			setSpriteFromAge(sprites);
		}
	}
}