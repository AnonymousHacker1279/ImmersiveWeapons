package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public record GunScopePayload(double playerFOV, double changingPlayerFOV,
                              float scopeScale) implements CustomPacketPayload {

	public static final Type<GunScopePayload> TYPE = new Type<>(new ResourceLocation(ImmersiveWeapons.MOD_ID, "gun_scope"));

	public static final StreamCodec<FriendlyByteBuf, GunScopePayload> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.DOUBLE,
			GunScopePayload::playerFOV,
			ByteBufCodecs.DOUBLE,
			GunScopePayload::changingPlayerFOV,
			ByteBufCodecs.FLOAT,
			GunScopePayload::scopeScale,
			GunScopePayload::new
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}