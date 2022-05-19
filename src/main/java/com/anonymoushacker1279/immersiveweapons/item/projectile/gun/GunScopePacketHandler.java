package com.anonymoushacker1279.immersiveweapons.item.projectile.gun;

import com.anonymoushacker1279.immersiveweapons.item.projectile.gun.data.GunData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public record GunScopePacketHandler(double playerFOV,
                                    double changingPlayerFOV,
                                    float scopeScale) {

	/**
	 * Constructor for GunScopePacketHandler.
	 */
	public GunScopePacketHandler {
	}

	/**
	 * Encodes a packet
	 *
	 * @param msg          the <code>GunScopePacketHandler</code> message being sent
	 * @param packetBuffer the <code>FriendlyByteBuf</code> containing packet data
	 */
	public static void encode(GunScopePacketHandler msg, FriendlyByteBuf packetBuffer) {
		packetBuffer.writeDouble(msg.playerFOV).writeDouble(msg.changingPlayerFOV).writeDouble(msg.scopeScale);
	}

	/**
	 * Decodes a packet
	 *
	 * @param packetBuffer the <code>FriendlyByteBuf</code> containing packet data
	 * @return GunScopePacketHandler
	 */
	public static GunScopePacketHandler decode(FriendlyByteBuf packetBuffer) {
		return new GunScopePacketHandler(packetBuffer.readDouble(), packetBuffer.readDouble(), packetBuffer.readFloat());
	}

	/**
	 * Handles an incoming packet, by sending it to the client/server
	 *
	 * @param msg             the <code>GunScopePacketHandler</code> message being sent
	 * @param contextSupplier the <code>Supplier</code> providing context
	 */
	public static void handle(GunScopePacketHandler msg, Supplier<Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> handleOnClient(msg)));
		context.setPacketHandled(true);
	}

	/**
	 * Runs specifically on the client, when a packet is received
	 *
	 * @param msg the <code>GunScopePacketHandler</code> message being sent
	 */
	private static void handleOnClient(GunScopePacketHandler msg) {
		GunData.playerFOV = msg.playerFOV;
		GunData.changingPlayerFOV = msg.changingPlayerFOV;
		// GunData.scopeScale = msg.scopeScale;
	}
}