package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import java.util.UUID;

public record DebugDataPayload(UUID playerUUID, float lastDamageDealt, float lastDamageTaken,
                               int ticksSinceRest) implements CustomPacketPayload {

	public static final Type<DebugDataPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "debug_data"));

	public static final StreamCodec<FriendlyByteBuf, DebugDataPayload> STREAM_CODEC = StreamCodec.composite(
			UUIDUtil.STREAM_CODEC,
			DebugDataPayload::playerUUID,
			ByteBufCodecs.FLOAT,
			DebugDataPayload::lastDamageDealt,
			ByteBufCodecs.FLOAT,
			DebugDataPayload::lastDamageTaken,
			ByteBufCodecs.INT,
			DebugDataPayload::ticksSinceRest,
			DebugDataPayload::new
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}