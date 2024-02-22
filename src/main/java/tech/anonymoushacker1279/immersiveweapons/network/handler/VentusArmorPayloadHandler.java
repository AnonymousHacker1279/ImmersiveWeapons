package tech.anonymoushacker1279.immersiveweapons.network.handler;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import tech.anonymoushacker1279.immersiveweapons.item.armor.VentusArmorItem;
import tech.anonymoushacker1279.immersiveweapons.item.armor.VentusArmorItem.PacketTypes;
import tech.anonymoushacker1279.immersiveweapons.network.payload.VentusArmorPayload;

public class VentusArmorPayloadHandler {

	private static final VentusArmorPayloadHandler INSTANCE = new VentusArmorPayloadHandler();

	public static VentusArmorPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleData(final VentusArmorPayload data, final PlayPayloadContext context) {
		context.workHandler().submitAsync(() -> {
					if (context.player().isPresent() && context.player().get() instanceof ServerPlayer serverPlayer) {
						if (data.packetType() == PacketTypes.CHANGE_STATE) {
							serverPlayer.getPersistentData().putBoolean("VentusArmorEffectEnabled", data.state());
						}

						if (data.packetType() == PacketTypes.HANDLE_PROJECTILE_REFLECTION) {
							VentusArmorItem.handleProjectileReflection(serverPlayer.level(), serverPlayer);
						}
					}
				})
				.exceptionally(e -> {
					context.packetHandler().disconnect(Component.translatable("immersiveweapons.networking.failure.generic", e.getMessage()));
					return null;
				});
	}
}