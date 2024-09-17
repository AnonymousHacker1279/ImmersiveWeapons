package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import java.util.UUID;

/**
 * A packet payload for syncing player persistent data between the server and client.
 *
 * @param tag        the <code>CompoundTag</code> to sync, should be from {@link Player#getPersistentData()}
 * @param playerUUID the <code>UUID</code> of the player to sync to
 */
public record SyncPlayerDataPayload(CompoundTag tag, UUID playerUUID) implements CustomPacketPayload {

	public static final Type<SyncPlayerDataPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "sync_player_data"));

	public static final StreamCodec<FriendlyByteBuf, SyncPlayerDataPayload> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.COMPOUND_TAG,
			SyncPlayerDataPayload::tag,
			UUIDUtil.STREAM_CODEC,
			SyncPlayerDataPayload::playerUUID,
			SyncPlayerDataPayload::new
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}