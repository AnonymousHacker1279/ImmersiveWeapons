package com.anonymoushacker1279.immersiveweapons.util;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.MolotovEntity.MolotovEntityPacketHandler;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.SmokeBombEntity.SmokeBombEntityPacketHandler;
import com.anonymoushacker1279.immersiveweapons.item.CobaltArmorItem.CobaltArmorItemPacketHandler;
import com.anonymoushacker1279.immersiveweapons.item.TeslaArmorItem.TeslaArmorItemPacketHandler;
import com.anonymoushacker1279.immersiveweapons.item.VentusArmorItem.VentusArmorItemPacketHandler;
import com.anonymoushacker1279.immersiveweapons.tileentity.PanicAlarmTileEntity.PanicAlarmPacketHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler {

	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(ImmersiveWeapons.MOD_ID, "main"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
	);

	public static void registerPackets() {
		int networkId = 0;
		PacketHandler.INSTANCE.registerMessage(networkId++,
				PanicAlarmPacketHandler.class,
				PanicAlarmPacketHandler::encode,
				PanicAlarmPacketHandler::decode,
				PanicAlarmPacketHandler::handle
		);
		PacketHandler.INSTANCE.registerMessage(networkId++,
				SmokeBombEntityPacketHandler.class,
				SmokeBombEntityPacketHandler::encode,
				SmokeBombEntityPacketHandler::decode,
				SmokeBombEntityPacketHandler::handle
		);
		PacketHandler.INSTANCE.registerMessage(networkId++,
				MolotovEntityPacketHandler.class,
				MolotovEntityPacketHandler::encode,
				MolotovEntityPacketHandler::decode,
				MolotovEntityPacketHandler::handle
		);
		PacketHandler.INSTANCE.registerMessage(networkId++,
				CobaltArmorItemPacketHandler.class,
				CobaltArmorItemPacketHandler::encode,
				CobaltArmorItemPacketHandler::decode,
				CobaltArmorItemPacketHandler::handle
		);
		PacketHandler.INSTANCE.registerMessage(networkId++,
				TeslaArmorItemPacketHandler.class,
				TeslaArmorItemPacketHandler::encode,
				TeslaArmorItemPacketHandler::decode,
				TeslaArmorItemPacketHandler::handle
		);
		PacketHandler.INSTANCE.registerMessage(networkId++,
				VentusArmorItemPacketHandler.class,
				VentusArmorItemPacketHandler::encode,
				VentusArmorItemPacketHandler::decode,
				VentusArmorItemPacketHandler::handle
		);
	}
}