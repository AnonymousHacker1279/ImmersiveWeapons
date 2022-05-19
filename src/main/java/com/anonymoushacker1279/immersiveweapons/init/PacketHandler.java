package com.anonymoushacker1279.immersiveweapons.init;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.block.base.MortarBlock.MortarBlockPacketHandler;
import com.anonymoushacker1279.immersiveweapons.block.misc.warrior_statue.WarriorStatueTorso.WarriorStatueTorsoPacketHandler;
import com.anonymoushacker1279.immersiveweapons.block.trap.SpikeTrapBlock.SpikeTrapBlockPacketHandler;
import com.anonymoushacker1279.immersiveweapons.entity.monster.lava_revenant.LavaRevenantEntity.LavaRevenantEntityPacketHandler;
import com.anonymoushacker1279.immersiveweapons.entity.neutral.AbstractFieldMedicEntity.AbstractFieldMedicEntityPacketHandler;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.SmokeBombArrowEntityPacketHandler;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.SmokeBombEntity.SmokeBombEntityPacketHandler;
import com.anonymoushacker1279.immersiveweapons.item.armor.CobaltArmorItem.CobaltArmorItemPacketHandler;
import com.anonymoushacker1279.immersiveweapons.item.armor.TeslaArmorItem.TeslaArmorItemPacketHandler;
import com.anonymoushacker1279.immersiveweapons.item.armor.VentusArmorItem.VentusArmorItemPacketHandler;
import com.anonymoushacker1279.immersiveweapons.item.projectile.gun.GunScopePacketHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

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
				SmokeBombEntityPacketHandler.class,
				SmokeBombEntityPacketHandler::encode,
				SmokeBombEntityPacketHandler::decode,
				SmokeBombEntityPacketHandler::handle
		);
		PacketHandler.INSTANCE.registerMessage(networkId++,
				SmokeBombArrowEntityPacketHandler.class,
				SmokeBombArrowEntityPacketHandler::encode,
				SmokeBombArrowEntityPacketHandler::decode,
				SmokeBombArrowEntityPacketHandler::handle
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
				SpikeTrapBlockPacketHandler.class,
				SpikeTrapBlockPacketHandler::encode,
				SpikeTrapBlockPacketHandler::decode,
				SpikeTrapBlockPacketHandler::handle
		);
		PacketHandler.INSTANCE.registerMessage(networkId++,
				WarriorStatueTorsoPacketHandler.class,
				WarriorStatueTorsoPacketHandler::encode,
				WarriorStatueTorsoPacketHandler::decode,
				WarriorStatueTorsoPacketHandler::handle
		);
		PacketHandler.INSTANCE.registerMessage(networkId++,
				LavaRevenantEntityPacketHandler.class,
				LavaRevenantEntityPacketHandler::encode,
				LavaRevenantEntityPacketHandler::decode,
				LavaRevenantEntityPacketHandler::handle
		);
		PacketHandler.INSTANCE.registerMessage(networkId++,
				AbstractFieldMedicEntityPacketHandler.class,
				AbstractFieldMedicEntityPacketHandler::encode,
				AbstractFieldMedicEntityPacketHandler::decode,
				AbstractFieldMedicEntityPacketHandler::handle
		);
		PacketHandler.INSTANCE.registerMessage(networkId,
				GunScopePacketHandler.class,
				GunScopePacketHandler::encode,
				GunScopePacketHandler::decode,
				GunScopePacketHandler::handle
		);
	}
}