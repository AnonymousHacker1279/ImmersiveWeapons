package tech.anonymoushacker1279.immersiveweapons.network.payload;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.trading.MerchantTrades;

public record SyncMerchantTradesPayload(EntityType<?> entityType,
                                        MerchantTrades trades) implements CustomPacketPayload {

	public static final ResourceLocation ID = new ResourceLocation(ImmersiveWeapons.MOD_ID, "sync_merchant_trades");

	public SyncMerchantTradesPayload(FriendlyByteBuf pBuffer) {
		this(EntityType.byString(pBuffer.readUtf()).orElseThrow(), pBuffer.readJsonWithCodec(MerchantTrades.CODEC));
	}

	@Override
	public void write(FriendlyByteBuf pBuffer) {
		pBuffer.writeUtf(BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString());
		pBuffer.writeJsonWithCodec(MerchantTrades.CODEC, trades);
	}

	@Override
	public ResourceLocation id() {
		return ID;
	}
}