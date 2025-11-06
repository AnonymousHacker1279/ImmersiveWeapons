package tech.anonymoushacker1279.immersiveweapons.client.particle.wisp;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class WispParticleType extends ParticleType<WispParticleOptions> {

	public static final MapCodec<WispParticleOptions> CODEC = RecordCodecBuilder.mapCodec(instance ->
			instance.group(
							Codec.INT.fieldOf("color").forGetter(WispParticleOptions::color))
					.apply(instance, WispParticleOptions::new));

	public static final StreamCodec<FriendlyByteBuf, WispParticleOptions> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.INT,
			WispParticleOptions::color,
			WispParticleOptions::new
	);

	public WispParticleType(boolean overrideLimiter) {
		super(overrideLimiter);
	}

	@Override
	public MapCodec<WispParticleOptions> codec() {
		return CODEC;
	}

	@Override
	public StreamCodec<? super RegistryFriendlyByteBuf, WispParticleOptions> streamCodec() {
		return STREAM_CODEC;
	}
}