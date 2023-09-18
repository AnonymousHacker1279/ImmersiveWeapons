package tech.anonymoushacker1279.immersiveweapons.client.particle.bullet_impact;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class BulletImpactParticle extends TextureSheetParticle {

	@Nullable
	static SpriteSet sprites;

	public static class Provider implements ParticleProvider<BulletImpactParticleOptions> {

		public Provider(SpriteSet pSprites) {
			sprites = pSprites;
		}

		@Override
		public Particle createParticle(BulletImpactParticleOptions pType, ClientLevel pLevel, double pX, double pY, double pZ,
		                               double pXSpeed, double pYSpeed, double pZSpeed) {

			if (sprites != null) {
				return new BulletImpactParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, sprites, pType.getBlockID());
			}
			return null;
		}
	}

	protected BulletImpactParticle(ClientLevel level, double x, double y, double z,
	                               double xSpeed, double ySpeed,
	                               double zSpeed, SpriteSet spriteSet, int blockID) {

		super(level, x, y, z, 0.0D, 0.0D, 0.0D);

		Vector3f color = getColorFromBlock(blockID);

		friction = 0.96F;
		gravity = (float) 0.175;
		speedUpWhenYMotionIsBlocked = true;
		sprites = spriteSet;
		xd *= (float) 0.0;
		yd *= (float) 0.0;
		zd *= (float) 0.0;
		xd += xSpeed;
		yd += ySpeed;
		zd += zSpeed;
		float vibrancyModifier = GeneralUtilities.getRandomNumber(0.6f, 1.0f);
		rCol = randomizeColor(color.x(), vibrancyModifier);
		gCol = randomizeColor(color.y(), vibrancyModifier);
		bCol = randomizeColor(color.z(), vibrancyModifier);
		quadSize *= 2.5f;
		lifetime = (int) ((double) 20 / ((double) level.random.nextFloat() * 0.8D + 0.2D));
		lifetime = (int) ((float) lifetime * (float) 1.5);
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
		BakedModel blockModel = blockModelShapes.getBlockModel(Block.stateById(blockID));
		TextureAtlasSprite textureAtlasSprite = blockModel.getParticleIcon(ModelData.EMPTY);

		int pixelABGR = textureAtlasSprite.getPixelRGBA(0, 0, 0);
		int r = pixelABGR & 0xff;
		int g = pixelABGR >> 8 & 0xff;
		int b = pixelABGR >> 16 & 0xff;
		return new Vector3f(r / 255f, g / 255f, b / 255f);
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	@Override
	public float getQuadSize(float pScaleFactor) {
		return quadSize * Mth.clamp(((float) age + pScaleFactor) / (float) lifetime * 32.0F, 0.0F, 1.0F);
	}

	@Override
	public void tick() {
		super.tick();
		if (sprites != null) {
			setSpriteFromAge(sprites);
		}
	}
}