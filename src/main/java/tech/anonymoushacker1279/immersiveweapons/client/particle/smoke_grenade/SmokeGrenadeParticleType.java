package tech.anonymoushacker1279.immersiveweapons.client.particle.smoke_grenade;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class SmokeGrenadeParticleType extends ParticleType<SmokeGrenadeParticleOptions> {

	public static final MapCodec<SmokeGrenadeParticleOptions> CODEC = RecordCodecBuilder.mapCodec(instance ->
			instance.group(
							Codec.INT.fieldOf("color").forGetter(SmokeGrenadeParticleOptions::color),
							Codec.FLOAT.fieldOf("scale").forGetter(SmokeGrenadeParticleOptions::scale))
					.apply(instance, SmokeGrenadeParticleOptions::new));

	public static final StreamCodec<FriendlyByteBuf, SmokeGrenadeParticleOptions> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.INT,
			SmokeGrenadeParticleOptions::color,
			ByteBufCodecs.FLOAT,
			SmokeGrenadeParticleOptions::scale,
			SmokeGrenadeParticleOptions::new
	);

	public SmokeGrenadeParticleType(boolean overrideLimiter) {
		super(overrideLimiter);
	}

	@Override
	public MapCodec<SmokeGrenadeParticleOptions> codec() {
		return CODEC;
	}

	@Override
	public StreamCodec<? super RegistryFriendlyByteBuf, SmokeGrenadeParticleOptions> streamCodec() {
		return STREAM_CODEC;
	}
}