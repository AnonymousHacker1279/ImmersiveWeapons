package tech.anonymoushacker1279.immersiveweapons.client.particle.damage_indicator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class DamageIndicatorParticleType extends ParticleType<DamageIndicatorParticleOptions> {

	public static final MapCodec<DamageIndicatorParticleOptions> CODEC = RecordCodecBuilder.mapCodec(instance ->
			instance.group(Codec.FLOAT.fieldOf("damage").forGetter(DamageIndicatorParticleOptions::damage))
					.apply(instance, DamageIndicatorParticleOptions::new));

	public static final StreamCodec<FriendlyByteBuf, DamageIndicatorParticleOptions> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.FLOAT,
			DamageIndicatorParticleOptions::damage,
			DamageIndicatorParticleOptions::new
	);

	public DamageIndicatorParticleType(boolean overrideLimiter) {
		super(overrideLimiter);
	}

	@Override
	public MapCodec<DamageIndicatorParticleOptions> codec() {
		return CODEC;
	}

	@Override
	public StreamCodec<? super RegistryFriendlyByteBuf, DamageIndicatorParticleOptions> streamCodec() {
		return STREAM_CODEC;
	}
}