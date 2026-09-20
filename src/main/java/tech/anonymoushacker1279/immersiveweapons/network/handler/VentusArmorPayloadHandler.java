package tech.anonymoushacker1279.immersiveweapons.network.handler;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import tech.anonymoushacker1279.immersiveweapons.item.armor.ArmorUtils;
import tech.anonymoushacker1279.immersiveweapons.item.armor.VentusArmorItem;
import tech.anonymoushacker1279.immersiveweapons.item.armor.VentusArmorItem.PacketTypes;
import tech.anonymoushacker1279.immersiveweapons.network.payload.VentusArmorPayload;

public class VentusArmorPayloadHandler {

	private static final VentusArmorPayloadHandler INSTANCE = new VentusArmorPayloadHandler();
	private static final String WIND_SHIELD_START_KEY = "VentusArmorWindShieldStart";
	private static final int COOLDOWN_LATENCY_MARGIN = 10;

	public static VentusArmorPayloadHandler getInstance() {
		return INSTANCE;
	}

	/// The client sends a reflection request every tick while its wind shield is active, so the server must verify
	/// them. The player must be wearing the full set with the effect enabled, and requests are only accepted during
	/// the wind shield duration. A new window can only begin once the cooldown has elapsed (with a small margin for
	/// latency).
	private boolean canReflectProjectiles(ServerPlayer player) {
		if (!ArmorUtils.isWearingVentusArmor(player)) {
			return false;
		}

		CompoundTag persistentData = player.getPersistentData();
		if (!persistentData.getBoolean("VentusArmorEffectEnabled").orElse(false)) {
			return false;
		}

		long gameTime = player.level().getGameTime();
		long elapsed = gameTime - persistentData.getLongOr(WIND_SHIELD_START_KEY, -VentusArmorItem.WIND_SHIELD_COOLDOWN);

		if (elapsed < VentusArmorItem.WIND_SHIELD_DURATION) {
			return true;
		}

		if (elapsed >= VentusArmorItem.WIND_SHIELD_COOLDOWN - COOLDOWN_LATENCY_MARGIN) {
			persistentData.putLong(WIND_SHIELD_START_KEY, gameTime);
			return true;
		}

		return false;
	}

	public void handleData(final VentusArmorPayload data, final IPayloadContext context) {
		context.enqueueWork(() -> {
					if (context.player() instanceof ServerPlayer serverPlayer) {
						if (data.packetType() == PacketTypes.CHANGE_STATE) {
							serverPlayer.getPersistentData().putBoolean("VentusArmorEffectEnabled", data.state());
						}

						if (data.packetType() == PacketTypes.HANDLE_PROJECTILE_REFLECTION && canReflectProjectiles(serverPlayer)) {
							VentusArmorItem.handleProjectileReflection(serverPlayer.level(), serverPlayer);
						}
					}
				})
				.exceptionally(e -> {
					context.disconnect(Component.translatable("immersiveweapons.networking.failure.generic", e.getMessage()));
					return null;
				});
	}
}