package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public record PlayerSoundPayload(ResourceLocation soundLocation, float volume,
                                 float pitch) implements CustomPacketPayload {

	public static final ResourceLocation ID = new ResourceLocation(ImmersiveWeapons.MOD_ID, "player_sound");

	public PlayerSoundPayload(final FriendlyByteBuf buffer) {
		this(buffer.readResourceLocation(), buffer.readFloat(), buffer.readFloat());
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		buffer.writeResourceLocation(soundLocation);
		buffer.writeFloat(volume);
		buffer.writeFloat(pitch);
	}

	@Override
	public ResourceLocation id() {
		return ID;
	}
}