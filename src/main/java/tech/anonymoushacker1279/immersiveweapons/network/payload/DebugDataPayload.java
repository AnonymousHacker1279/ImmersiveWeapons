package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import java.util.UUID;

public record DebugDataPayload(UUID playerUUID, float lastDamageDealt, float lastDamageTaken,
                               int ticksSinceRest) implements CustomPacketPayload {

	public static final ResourceLocation ID = new ResourceLocation(ImmersiveWeapons.MOD_ID, "debug_data");

	public DebugDataPayload(final FriendlyByteBuf buffer) {
		this(buffer.readUUID(), buffer.readFloat(), buffer.readFloat(), buffer.readInt());
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		buffer.writeUUID(playerUUID);
		buffer.writeFloat(lastDamageDealt);
		buffer.writeFloat(lastDamageTaken);
		buffer.writeInt(ticksSinceRest);
	}

	@Override
	public ResourceLocation id() {
		return ID;
	}
}