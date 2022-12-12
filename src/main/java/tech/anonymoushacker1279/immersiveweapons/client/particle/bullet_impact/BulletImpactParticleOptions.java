package tech.anonymoushacker1279.immersiveweapons.client.particle.bullet_impact;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.math.Vector3f;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.registries.ForgeRegistries;
import tech.anonymoushacker1279.immersiveweapons.init.ParticleTypesRegistry;

import java.util.Locale;

public class BulletImpactParticleOptions implements ParticleOptions {

	public static final Codec<BulletImpactParticleOptions> CODEC = RecordCodecBuilder.create((particleOptionsInstance)
			-> particleOptionsInstance
			.group(Codec.FLOAT.fieldOf("scale").forGetter((particleOptions) -> particleOptions.scale),
					Codec.INT.fieldOf("blockID").forGetter((particleOptions) -> particleOptions.blockID))
			.apply(particleOptionsInstance, BulletImpactParticleOptions::new));

	protected final Vector3f color;
	protected final float scale;
	protected final int blockID;

	public static final ParticleOptions.Deserializer<BulletImpactParticleOptions> DESERIALIZER = new ParticleOptions.Deserializer<>() {
		@Override
		public BulletImpactParticleOptions fromCommand(ParticleType<BulletImpactParticleOptions> particleType,
		                                               StringReader reader) throws CommandSyntaxException {

			float vibrancy = reader.readFloat();
			reader.expect(' ');
			int blockID = reader.readInt();

			return new BulletImpactParticleOptions(vibrancy, blockID);
		}

		@Override
		public BulletImpactParticleOptions fromNetwork(ParticleType<BulletImpactParticleOptions> particleType,
		                                               FriendlyByteBuf byteBuf) {

			return new BulletImpactParticleOptions(byteBuf.readFloat(), byteBuf.readInt());
		}
	};

	public BulletImpactParticleOptions(float vibrancy, int blockID) {
		Minecraft minecraft = Minecraft.getInstance();
		BlockRenderDispatcher blockRendererDispatcher = minecraft.getBlockRenderer();
		BlockModelShaper blockModelShapes = blockRendererDispatcher.getBlockModelShaper();
		BakedModel blockModel = blockModelShapes.getBlockModel(Block.stateById(blockID));
		TextureAtlasSprite textureAtlasSprite = blockModel.getParticleIcon(ModelData.EMPTY);

		int pixelABGR = textureAtlasSprite.getPixelRGBA(0, 0, 0);
		int r = pixelABGR & 0xff;
		int g = pixelABGR >> 8 & 0xff;
		int b = pixelABGR >> 16 & 0xff;
		Vector3f blockColor = new Vector3f(r / 255f, g / 255f, b / 255f);

		scale = Mth.clamp(vibrancy, 0.001F, 100.0F);
		this.blockID = blockID;
		color = blockColor;
	}

	@Override
	public void writeToNetwork(FriendlyByteBuf byteBuf) {
		byteBuf.writeFloat(scale);
		byteBuf.writeInt(blockID);
	}

	@Override
	public String writeToString() {
		return String.format(Locale.ROOT, "%s %s %.2f %s", ForgeRegistries.PARTICLE_TYPES
				.getKey(getType()), color, scale, blockID);
	}

	public Vector3f getColor() {
		return color;
	}

	@Override
	public ParticleType<BulletImpactParticleOptions> getType() {
		return ParticleTypesRegistry.BULLET_IMPACT_PARTICLE.get();
	}
}