package com.anonymoushacker1279.immersiveweapons.util;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.block.MortarBlock.MortarBlockPacketHandler;
import com.anonymoushacker1279.immersiveweapons.block.SpikeTrapBlock.SpikeTrapBlockPacketHandler;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.SmokeBombArrowEntity.SmokeBombArrowEntityPacketHandler;
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

	/**
	 * Register packet information.
	 */
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
		PacketHandler.INSTANCE.registerMessage(networkId++,
				MortarBlockPacketHandler.class,
				MortarBlockPacketHandler::encode,
				MortarBlockPacketHandler::decode,
				MortarBlockPacketHandler::handle
		);
		PacketHandler.INSTANCE.registerMessage(networkId++,
				SmokeBombArrowEntityPacketHandler.class,
				SmokeBombArrowEntityPacketHandler::encode,
				SmokeBombArrowEntityPacketHandler::decode,
				SmokeBombArrowEntityPacketHandler::handle
		);
		PacketHandler.INSTANCE.registerMessage(networkId++,
				SpikeTrapBlockPacketHandler.class,
				SpikeTrapBlockPacketHandler::encode,
				SpikeTrapBlockPacketHandler::decode,
				SpikeTrapBlockPacketHandler::handle
		);
	}
}