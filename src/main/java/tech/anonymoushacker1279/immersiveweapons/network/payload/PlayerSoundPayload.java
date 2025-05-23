package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public record PlayerSoundPayload(ResourceKey<SoundEvent> soundKey, float volume,
                                 float pitch) implements CustomPacketPayload {

	public static final Type<PlayerSoundPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "player_sound"));

	public static final StreamCodec<FriendlyByteBuf, PlayerSoundPayload> STREAM_CODEC = StreamCodec.composite(
			ResourceKey.streamCodec(Registries.SOUND_EVENT),
			PlayerSoundPayload::soundKey,
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