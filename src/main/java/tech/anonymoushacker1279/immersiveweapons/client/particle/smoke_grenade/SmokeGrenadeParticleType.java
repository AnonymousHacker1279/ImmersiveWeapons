package tech.anonymoushacker1279.immersiveweapons.client.particle.smoke_grenade;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;

public class SmokeGrenadeParticleType extends ParticleType<SmokeGrenadeParticleOptions> {

	public static final MapCodec<SmokeGrenadeParticleOptions> CODEC = RecordCodecBuilder.mapCodec(instance ->
			instance.group(
							ExtraCodecs.VECTOR3F.fieldOf("color").forGetter(options -> options.color),
							Codec.FLOAT.fieldOf("scale").forGetter(options -> options.scale))
					.apply(instance, SmokeGrenadeParticleOptions::new));

	public static final StreamCodec<FriendlyByteBuf, SmokeGrenadeParticleOptions> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.VECTOR3F,
			options -> options.color,
			ByteBufCodecs.FLOAT,
			options -> options.scale,
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