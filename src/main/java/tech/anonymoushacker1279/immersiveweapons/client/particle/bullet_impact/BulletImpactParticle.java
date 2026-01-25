package tech.anonymoushacker1279.immersiveweapons.client.particle.bullet_impact;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class BulletImpactParticle extends SingleQuadParticle {

	@Nullable
	static SpriteSet sprites;

	protected BulletImpactParticle(ClientLevel level, double x, double y, double z,
	                               double xSpeed, double ySpeed,
	                               double zSpeed, SpriteSet spriteSet, int blockID) {

		super(level, x, y, z, spriteSet.first());

		Vector3f color = getColorFromBlock(blockID);

		friction = 0.96F;
		gravity = (float) 0.175;
		speedUpWhenYMotionIsBlocked = true;
		sprites = spriteSet;
		xd *= 0.0F;
		yd *= 0.0F;
		zd *= 0.0F;
		xd += xSpeed;
		yd += ySpeed;
		zd += zSpeed;
		float vibrancyModifier = 0.6F + level.random.nextFloat() * 0.4F;
		rCol = randomizeColor(color.x(), vibrancyModifier);
		gCol = randomizeColor(color.y(), vibrancyModifier);
		bCol = randomizeColor(color.z(), vibrancyModifier);
		quadSize *= 2.5f;
		lifetime = (int) ((double) 20 / ((double) level.random.nextFloat() * 0.8D + 0.2D));
		lifetime = (int) ((float) lifetime * 1.5F);
		lifetime = Math.max(lifetime, 1);
		setSpriteFromAge(spriteSet);
		hasPhysics = true;
	}

	protected float randomizeColor(float color, float multiplier) {
		return (random.nextFloat() * 0.05F + 0.95F) * color * multiplier;
	}

	protected Vector3f getColorFromBlock(int blockID) {
		Minecraft minecraft = Minecraft.getInstance();
		BlockRenderDispatcher blockRendererDispatcher = minecraft.getBlockRenderer();
		BlockModelShaper blockModelShapes = blockRendererDispatcher.getBlockModelShaper();
		BlockStateModel model = blockModelShapes.getBlockModel(Block.stateById(blockID));
		TextureAtlasSprite textureAtlasSprite = model.particleIcon();

		int pixelRGBA = textureAtlasSprite.getPixelRGBA(0, 0, 0);
		float red = (float) (pixelRGBA >> 16 & 255) / 255.0F;
		float green = (float) (pixelRGBA >> 8 & 255) / 255.0F;
		float blue = (float) (pixelRGBA & 255) / 255.0F;
		return new Vector3f(red, green, blue);
	}

	@Override
	public float getQuadSize(float pScaleFactor) {
		return quadSize * Mth.clamp(((float) age + pScaleFactor) / (float) lifetime * 32.0F, 0.0F, 1.0F);
	}

	@Override
	protected Layer getLayer() {
		return Layer.OPAQUE;
	}

	@Override
	public void tick() {
		super.tick();
		if (sprites != null) {
			setSpriteFromAge(sprites);
		}
	}

	public static class Provider implements ParticleProvider<BulletImpactParticleOptions> {

		public Provider(SpriteSet pSprites) {
			sprites = pSprites;
		}

		@Nullable
		@Override
		public Particle createParticle(BulletImpactParticleOptions type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
			return sprites != null ? new BulletImpactParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, sprites, type.blockID()) : null;
		}
	}
}