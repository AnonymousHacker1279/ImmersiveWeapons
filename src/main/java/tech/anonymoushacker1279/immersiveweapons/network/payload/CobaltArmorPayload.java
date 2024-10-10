package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public record CobaltArmorPayload(boolean state) implements CustomPacketPayload {

	public static final Type<CobaltArmorPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "cobalt_armor"));

	public static final StreamCodec<FriendlyByteBuf, CobaltArmorPayload> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.BOOL,
			CobaltArmorPayload::state,
			CobaltArmorPayload::new
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}