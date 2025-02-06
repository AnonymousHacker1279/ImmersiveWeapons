package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public record VoidArmorPayload(boolean state, boolean summonDragonBreath) implements CustomPacketPayload {

	public static final Type<VoidArmorPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "void_armor"));

	public static final StreamCodec<FriendlyByteBuf, VoidArmorPayload> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.BOOL,
			VoidArmorPayload::state,
			ByteBufCodecs.BOOL,
			VoidArmorPayload::summonDragonBreath,
			VoidArmorPayload::new
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}