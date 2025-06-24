package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.Accessory;

public record SyncAccessoryDataPayload(Holder<Item> item, Accessory accessory) implements CustomPacketPayload {

	public static final Type<SyncAccessoryDataPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "sync_accessory_data"));

	public static final StreamCodec<RegistryFriendlyByteBuf, SyncAccessoryDataPayload> STREAM_CODEC = StreamCodec.composite(
			Item.STREAM_CODEC,
			SyncAccessoryDataPayload::item,
			Accessory.STREAM_CODEC,
			SyncAccessoryDataPayload::accessory,
			SyncAccessoryDataPayload::new
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}