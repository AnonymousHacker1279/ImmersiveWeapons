package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public record PlayerSoundPayload(ResourceLocation soundLocation, float volume,
                                 float pitch) implements CustomPacketPayload {

	public static final Type<PlayerSoundPayload> TYPE = new Type<>(new ResourceLocation(ImmersiveWeapons.MOD_ID, "player_sound"));

	public static final StreamCodec<FriendlyByteBuf, PlayerSoundPayload> STREAM_CODEC = StreamCodec.composite(
			ResourceLocation.STREAM_CODEC,
			PlayerSoundPayload::soundLocation,
			ByteBufCodecs.FLOAT,
			PlayerSoundPayload::volume,
			ByteBufCodecs.FLOAT,
			PlayerSoundPayload::pitch,
			PlayerSoundPayload::new
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}