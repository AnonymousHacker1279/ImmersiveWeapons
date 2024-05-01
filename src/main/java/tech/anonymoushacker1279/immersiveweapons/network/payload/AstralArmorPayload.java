package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public record AstralArmorPayload(boolean state) implements CustomPacketPayload {

	public static final Type<AstralArmorPayload> TYPE = new Type<>(new ResourceLocation(ImmersiveWeapons.MOD_ID, "astral_armor"));

	public static final StreamCodec<FriendlyByteBuf, AstralArmorPayload> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.BOOL,
			AstralArmorPayload::state,
			AstralArmorPayload::new
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}