package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

/// Sent by the client when the fire key is pressed while holding a firearm: fires if loaded, otherwise starts a reload.
public record GunTriggerPayload() implements CustomPacketPayload {

	public static final Type<GunTriggerPayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "gun_trigger"));

	public static final StreamCodec<FriendlyByteBuf, GunTriggerPayload> STREAM_CODEC = StreamCodec.unit(new GunTriggerPayload());

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
