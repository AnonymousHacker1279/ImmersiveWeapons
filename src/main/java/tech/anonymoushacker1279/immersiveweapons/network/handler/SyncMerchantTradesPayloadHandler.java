package tech.anonymoushacker1279.immersiveweapons.network.handler;

import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.trading.TradeLoader;
import tech.anonymoushacker1279.immersiveweapons.network.payload.SyncMerchantTradesPayload;

public class SyncMerchantTradesPayloadHandler {

	private static final SyncMerchantTradesPayloadHandler INSTANCE = new SyncMerchantTradesPayloadHandler();

	public static SyncMerchantTradesPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleData(final SyncMerchantTradesPayload data, final IPayloadContext context) {
		context.enqueueWork(() -> TradeLoader.TRADES.put(data.entityType(), data.trades()))
				.exceptionally(e -> {
					context.disconnect(Component.translatable("immersiveweapons.networking.failure.generic", e.getMessage()));
					return null;
				});
	}
}