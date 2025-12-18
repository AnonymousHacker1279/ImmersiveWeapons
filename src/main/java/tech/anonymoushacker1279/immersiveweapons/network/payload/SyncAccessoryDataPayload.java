package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.Accessory;

import java.util.HashSet;

public record SyncAccessoryDataPayload(HashSet<Accessory> accessories) implements CustomPacketPayload {

	public static final Type<SyncAccessoryDataPayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "sync_accessory_data"));

	public static final StreamCodec<RegistryFriendlyByteBuf, SyncAccessoryDataPayload> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.collection(HashSet::new, Accessory.STREAM_CODEC),
			SyncAccessoryDataPayload::accessories,
			SyncAccessoryDataPayload::new
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}