package tech.anonymoushacker1279.immersiveweapons.client.particle.wisp;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class WispParticle extends SingleQuadParticle {

	public WispParticle(ClientLevel level, double x, double y, double z, int color, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
		super(level, x, y, z, xSpeed, ySpeed, zSpeed, sprites.get(level.getRandom()));
		gravity = 0.01f;
		xd *= 0.1d;
		yd *= 0.1d;
		zd *= 0.1d;
		xd += xSpeed;
		yd += ySpeed;
		zd += zSpeed;
		rCol = (float) (color >> 16 & 0xFF) / 255.0F;
		gCol = (float) (color >> 8 & 0xFF) / 255.0F;
		bCol = (float) (color & 0xFF) / 255.0F;
		alpha = 0.75F;
		quadSize *= 0.5F;
		lifetime = 40 + level.random.nextInt(20);
	}

	@Override
	public int getLightColor(float partialTick) {
		float lifeProgress = (this.age + partialTick) / this.lifetime;
		float clampedProgress = Mth.clamp(lifeProgress, 0.0F, 1.0F);

		int packedLight = super.getLightColor(partialTick);
		int blockLight = packedLight & 0xFF;
		int skyLight = (packedLight >> 16) & 0xFF;

		int maxBlockLight = 240;
		int increasedBlockLight = (int) (clampedProgress * maxBlockLight);
		blockLight += increasedBlockLight;
		if (blockLight > maxBlockLight) {
			blockLight = maxBlockLight;
		}

		return blockLight | (skyLight << 16);
	}

	@Override
	protected Layer getLayer() {
		return Layer.TRANSLUCENT;
	}

	public record Provider(SpriteSet sprites) implements ParticleProvider<WispParticleOptions> {

		@Override
		public Particle createParticle(WispParticleOptions type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
			return new WispParticle(level, x, y, z, type.color(), xSpeed, ySpeed, zSpeed, sprites);
		}
	}
}