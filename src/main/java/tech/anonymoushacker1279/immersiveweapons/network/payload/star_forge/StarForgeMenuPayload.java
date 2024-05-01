package tech.anonymoushacker1279.immersiveweapons.network.payload.star_forge;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public record StarForgeMenuPayload(int containerId, int menuSelectionIndex,
                                   boolean beginCrafting) implements CustomPacketPayload {

	public static final Type<StarForgeMenuPayload> TYPE = new Type<>(new ResourceLocation(ImmersiveWeapons.MOD_ID, "star_forge_menu"));

	public static final StreamCodec<FriendlyByteBuf, StarForgeMenuPayload> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.INT,
			StarForgeMenuPayload::containerId,
			ByteBufCodecs.INT,
			StarForgeMenuPayload::menuSelectionIndex,
			ByteBufCodecs.BOOL,
			StarForgeMenuPayload::beginCrafting,
			StarForgeMenuPayload::new
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}