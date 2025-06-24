package tech.anonymoushacker1279.immersiveweapons.network.handler;

import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.AccessoryLoader;
import tech.anonymoushacker1279.immersiveweapons.network.payload.SyncAccessoryDataPayload;

public class SyncAccessoryDataPayloadHandler {

	private static final SyncAccessoryDataPayloadHandler INSTANCE = new SyncAccessoryDataPayloadHandler();

	public static SyncAccessoryDataPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleData(final SyncAccessoryDataPayload data, final IPayloadContext context) {
		context.enqueueWork(() -> data.accessories().forEach(accessory -> AccessoryLoader.ACCESSORIES.put(accessory.item().value(), accessory)))
				.exceptionally(e -> {
					context.disconnect(Component.translatable("immersiveweapons.networking.failure.generic", e.getMessage()));
					return null;
				});
	}
}