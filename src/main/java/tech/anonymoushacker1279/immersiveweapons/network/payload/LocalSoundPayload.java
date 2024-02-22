package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public record LocalSoundPayload(BlockPos pos, ResourceLocation soundLocation, SoundSource source, float volume,
                                float pitch, boolean distanceDelay) implements CustomPacketPayload {

	public static final ResourceLocation ID = new ResourceLocation(ImmersiveWeapons.MOD_ID, "local_sound");

	public LocalSoundPayload(final FriendlyByteBuf buffer) {
		this(buffer.readBlockPos(), buffer.readResourceLocation(), buffer.readEnum(SoundSource.class), buffer.readFloat(), buffer.readFloat(), buffer.readBoolean());
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		buffer.writeBlockPos(pos);
		buffer.writeResourceLocation(soundLocation);
		buffer.writeEnum(source);
		buffer.writeFloat(volume);
		buffer.writeFloat(pitch);
		buffer.writeBoolean(distanceDelay);
	}

	@Override
	public ResourceLocation id() {
		return ID;
	}
}