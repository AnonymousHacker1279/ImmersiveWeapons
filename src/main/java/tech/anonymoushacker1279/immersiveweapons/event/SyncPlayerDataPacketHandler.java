package tech.anonymoushacker1279.immersiveweapons.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * A packet handler for syncing player persistent data from server to client.
 *
 * @param tag        the <code>CompoundTag</code> to sync, should be from {@link Player#getPersistentData()}
 * @param playerUUID the <code>UUID</code> of the player to sync to
 */
public record SyncPlayerDataPacketHandler(CompoundTag tag, UUID playerUUID) {

	public static void encode(SyncPlayerDataPacketHandler msg, FriendlyByteBuf packetBuffer) {
		packetBuffer.writeNbt(msg.tag).writeUUID(msg.playerUUID);
	}

	public static SyncPlayerDataPacketHandler decode(FriendlyByteBuf packetBuffer) {
		return new SyncPlayerDataPacketHandler(Objects.requireNonNull(packetBuffer.readNbt()), packetBuffer.readUUID());
	}

	public static void handle(SyncPlayerDataPacketHandler msg, Supplier<Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> runOnClient(msg)));
		context.setPacketHandled(true);
	}

	private static void runOnClient(SyncPlayerDataPacketHandler msg) {
		SyncHandler.syncPersistentData(msg.tag, msg.playerUUID);
	}
}