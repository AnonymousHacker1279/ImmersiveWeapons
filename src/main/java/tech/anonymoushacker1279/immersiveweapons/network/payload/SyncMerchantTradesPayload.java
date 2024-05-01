package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.trading.MerchantTrades;

public record SyncMerchantTradesPayload(EntityType<?> entityType,
                                        MerchantTrades trades) implements CustomPacketPayload {

	public static final Type<SyncMerchantTradesPayload> TYPE = new Type<>(new ResourceLocation(ImmersiveWeapons.MOD_ID, "sync_merchant_trades"));

	public static final StreamCodec<RegistryFriendlyByteBuf, SyncMerchantTradesPayload> STREAM_CODEC = StreamCodec.composite(
			EntityTypeCodec(),
			SyncMerchantTradesPayload::entityType,
			MerchantTrades.STREAM_CODEC,
			SyncMerchantTradesPayload::trades,
			SyncMerchantTradesPayload::new
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

	static StreamCodec<RegistryFriendlyByteBuf, EntityType<?>> EntityTypeCodec() {
		return new StreamCodec<>() {
			public EntityType<?> decode(RegistryFriendlyByteBuf byteBuf) {
				return EntityType.byString(byteBuf.readUtf()).orElseThrow();
			}

			public void encode(RegistryFriendlyByteBuf byteBuf, EntityType<?> entityType) {
				byteBuf.writeUtf(BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString());
			}
		};
	}
}