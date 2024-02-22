package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import java.util.Objects;
import java.util.UUID;

/**
 * A packet payload for syncing player persistent data between the server and client.
 *
 * @param tag        the <code>CompoundTag</code> to sync, should be from {@link Player#getPersistentData()}
 * @param playerUUID the <code>UUID</code> of the player to sync to
 */
public record SyncPlayerDataPayload(CompoundTag tag, UUID playerUUID) implements CustomPacketPayload {

	public static final ResourceLocation ID = new ResourceLocation(ImmersiveWeapons.MOD_ID, "sync_player_data");

	public SyncPlayerDataPayload(final FriendlyByteBuf buffer) {
		this(Objects.requireNonNull(buffer.readNbt()), buffer.readUUID());
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		buffer.writeNbt(tag);
		buffer.writeUUID(playerUUID);
	}

	@Override
	public ResourceLocation id() {
		return ID;
	}
}