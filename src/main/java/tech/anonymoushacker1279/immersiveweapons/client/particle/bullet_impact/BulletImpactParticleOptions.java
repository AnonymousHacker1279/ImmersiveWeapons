package tech.anonymoushacker1279.immersiveweapons.client.particle.bullet_impact;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraftforge.registries.ForgeRegistries;
import tech.anonymoushacker1279.immersiveweapons.init.ParticleTypesRegistry;

import java.util.Locale;

public class BulletImpactParticleOptions implements ParticleOptions {

	public static final Codec<BulletImpactParticleOptions> CODEC = RecordCodecBuilder.create((particleOptionsInstance)
			-> particleOptionsInstance
			.group(Codec.FLOAT.fieldOf("scale").forGetter((particleOptions) -> particleOptions.scale),
					Codec.INT.fieldOf("blockID").forGetter((particleOptions) -> particleOptions.blockID))
			.apply(particleOptionsInstance, BulletImpactParticleOptions::new));

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
		scale = Mth.clamp(vibrancy, 0.001F, 100.0F);
		this.blockID = blockID;
	}

	@Override
	public void writeToNetwork(FriendlyByteBuf byteBuf) {
		byteBuf.writeFloat(scale);
		byteBuf.writeInt(blockID);
	}

	@Override
	public String writeToString() {
		return String.format(Locale.ROOT, "%s %.2f %s", ForgeRegistries.PARTICLE_TYPES
				.getKey(getType()), scale, blockID);
	}

	public int getBlockID() {
		return blockID;
	}

	@Override
	public ParticleType<BulletImpactParticleOptions> getType() {
		return ParticleTypesRegistry.BULLET_IMPACT_PARTICLE.get();
	}
}