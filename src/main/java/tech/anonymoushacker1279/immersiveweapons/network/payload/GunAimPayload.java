package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

/// Sent by the client when it starts or stops aiming a firearm.
public record GunAimPayload(boolean aiming) implements CustomPacketPayload {

	public static final Type<GunAimPayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "gun_aim"));

	public static final StreamCodec<FriendlyByteBuf, GunAimPayload> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.BOOL,
			GunAimPayload::aiming,
			GunAimPayload::new
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
