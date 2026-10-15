package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public record SmokeGrenadePayload(double x, double y, double z,
                                  int color, int forcedParticleCount, float radius,
                                  int duration, float particleFraction,
                                  boolean playSound) implements CustomPacketPayload {

	public static final Type<SmokeGrenadePayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "smoke_grenade"));

	public static final StreamCodec<FriendlyByteBuf, SmokeGrenadePayload> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.DOUBLE,
			SmokeGrenadePayload::x,
			ByteBufCodecs.DOUBLE,
			SmokeGrenadePayload::y,
			ByteBufCodecs.DOUBLE,
			SmokeGrenadePayload::z,
			ByteBufCodecs.INT,
			SmokeGrenadePayload::color,
			ByteBufCodecs.INT,
			SmokeGrenadePayload::forcedParticleCount,
			ByteBufCodecs.FLOAT,
			SmokeGrenadePayload::radius,
			ByteBufCodecs.VAR_INT,
			SmokeGrenadePayload::duration,
			ByteBufCodecs.FLOAT,
			SmokeGrenadePayload::particleFraction,
			ByteBufCodecs.BOOL,
			SmokeGrenadePayload::playSound,
			SmokeGrenadePayload::new
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}