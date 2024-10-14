package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public record ArrowGravityPayload(double gravity, int entityID) implements CustomPacketPayload {

	public static final Type<ArrowGravityPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "arrow_gravity"));

	public static final StreamCodec<FriendlyByteBuf, ArrowGravityPayload> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.DOUBLE,
			ArrowGravityPayload::gravity,
			ByteBufCodecs.INT,
			ArrowGravityPayload::entityID,
			ArrowGravityPayload::new
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}