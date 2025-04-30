package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public record LocalSoundPayload(BlockPos pos, ResourceKey<SoundEvent> soundKey, SoundSource source, float volume,
                                float pitch, boolean distanceDelay) implements CustomPacketPayload {

	public static final Type<LocalSoundPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "local_sound"));

	public static final StreamCodec<FriendlyByteBuf, LocalSoundPayload> STREAM_CODEC = StreamCodec.composite(
			BlockPos.STREAM_CODEC,
			LocalSoundPayload::pos,
			ResourceKey.streamCodec(Registries.SOUND_EVENT),
			LocalSoundPayload::soundKey,
			NeoForgeStreamCodecs.enumCodec(SoundSource.class),
			LocalSoundPayload::source,
			ByteBufCodecs.FLOAT,
			LocalSoundPayload::volume,
			ByteBufCodecs.FLOAT,
			LocalSoundPayload::pitch,
			ByteBufCodecs.BOOL,
			LocalSoundPayload::distanceDelay,
			LocalSoundPayload::new
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}