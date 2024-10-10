package tech.anonymoushacker1279.immersiveweapons.client.particle.bullet_impact;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class BulletImpactParticleType extends ParticleType<BulletImpactParticleOptions> {

	public static final MapCodec<BulletImpactParticleOptions> CODEC = RecordCodecBuilder.mapCodec(instance ->
			instance.group(
							Codec.FLOAT.fieldOf("scale").forGetter((particleOptions) -> particleOptions.scale),
							Codec.INT.fieldOf("blockID").forGetter((particleOptions) -> particleOptions.blockID))
					.apply(instance, BulletImpactParticleOptions::new));

	public static final StreamCodec<FriendlyByteBuf, BulletImpactParticleOptions> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.FLOAT,
			options -> options.scale,
			ByteBufCodecs.INT,
			options -> options.blockID,
			BulletImpactParticleOptions::new
	);

	public BulletImpactParticleType(boolean overrideLimiter) {
		super(overrideLimiter);
	}

	@Override
	public MapCodec<BulletImpactParticleOptions> codec() {
		return CODEC;
	}

	@Override
	public StreamCodec<? super RegistryFriendlyByteBuf, BulletImpactParticleOptions> streamCodec() {
		return STREAM_CODEC;
	}
}