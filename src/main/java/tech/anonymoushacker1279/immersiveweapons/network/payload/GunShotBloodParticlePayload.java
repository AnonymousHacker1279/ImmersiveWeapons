package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public record GunShotBloodParticlePayload(ParticleOptions particleOptions, double x, double y,
                                          double z) implements CustomPacketPayload {

	public static final Type<GunShotBloodParticlePayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "configurable_client_particle"));

	public static final StreamCodec<RegistryFriendlyByteBuf, GunShotBloodParticlePayload> STREAM_CODEC = StreamCodec.composite(
			ParticleTypes.STREAM_CODEC,
			GunShotBloodParticlePayload::particleOptions,
			ByteBufCodecs.DOUBLE,
			GunShotBloodParticlePayload::x,
			ByteBufCodecs.DOUBLE,
			GunShotBloodParticlePayload::y,
			ByteBufCodecs.DOUBLE,
			GunShotBloodParticlePayload::z,
			GunShotBloodParticlePayload::new
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}