package tech.anonymoushacker1279.immersiveweapons.item.gun;

import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.DistExecutor;
import net.neoforged.neoforge.network.NetworkEvent.Context;
import tech.anonymoushacker1279.immersiveweapons.item.gun.data.GunData;

public record GunScopePacketHandler(double playerFOV,
                                    double changingPlayerFOV,
                                    float scopeScale) {

	public static void encode(GunScopePacketHandler msg, FriendlyByteBuf packetBuffer) {
		packetBuffer.writeDouble(msg.playerFOV).writeDouble(msg.changingPlayerFOV).writeDouble(msg.scopeScale);
	}

	public static GunScopePacketHandler decode(FriendlyByteBuf packetBuffer) {
		return new GunScopePacketHandler(packetBuffer.readDouble(), packetBuffer.readDouble(), packetBuffer.readFloat());
	}

	public static void handle(GunScopePacketHandler msg, Context context) {
		context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> handleOnClient(msg)));
		context.setPacketHandled(true);
	}

	private static void handleOnClient(GunScopePacketHandler msg) {
		GunData.playerFOV = msg.playerFOV;
		GunData.changingPlayerFOV = msg.changingPlayerFOV;
	}
}