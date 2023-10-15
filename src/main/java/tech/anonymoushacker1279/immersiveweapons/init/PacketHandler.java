package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.block.MortarBlock.MortarBlockPacketHandler;
import tech.anonymoushacker1279.immersiveweapons.block.SpikeTrapBlock.SpikeTrapBlockPacketHandler;
import tech.anonymoushacker1279.immersiveweapons.block.decoration.AstralCrystalBlock.AstralCrystalBlockPacketHandler;
import tech.anonymoushacker1279.immersiveweapons.block.misc.warrior_statue.WarriorStatueTorso.WarriorStatueTorsoPacketHandler;
import tech.anonymoushacker1279.immersiveweapons.client.gui.overlays.DebugTracingData.DebugDataPacketHandler;
import tech.anonymoushacker1279.immersiveweapons.entity.monster.lava_revenant.LavaRevenantEntity.LavaRevenantEntityPacketHandler;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.AbstractFieldMedicEntity.AbstractFieldMedicEntityPacketHandler;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity.BulletEntityPacketHandler;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.SmokeGrenadeEntity.SmokeGrenadeEntityPacketHandler;
import tech.anonymoushacker1279.immersiveweapons.event.SyncPlayerDataPacketHandler;
import tech.anonymoushacker1279.immersiveweapons.item.armor.AstralArmorItem.AstralArmorItemPacketHandler;
import tech.anonymoushacker1279.immersiveweapons.item.armor.CobaltArmorItem.CobaltArmorItemPacketHandler;
import tech.anonymoushacker1279.immersiveweapons.item.armor.TeslaArmorItem.TeslaArmorItemPacketHandler;
import tech.anonymoushacker1279.immersiveweapons.item.armor.VentusArmorItem.VentusArmorItemPacketHandler;
import tech.anonymoushacker1279.immersiveweapons.item.gun.GunScopePacketHandler;
import tech.anonymoushacker1279.immersiveweapons.menu.AmmunitionTableMenu.AmmunitionTableMenuPacketHandler;

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
		ImmersiveWeapons.LOGGER.info("Registering packet handlers");

		int networkId = 0;
		PacketHandler.INSTANCE.registerMessage(networkId++,
				SmokeGrenadeEntityPacketHandler.class,
				SmokeGrenadeEntityPacketHandler::encode,
				SmokeGrenadeEntityPacketHandler::decode,
				SmokeGrenadeEntityPacketHandler::handle
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
		PacketHandler.INSTANCE.registerMessage(networkId++,
				GunScopePacketHandler.class,
				GunScopePacketHandler::encode,
				GunScopePacketHandler::decode,
				GunScopePacketHandler::handle
		);
		PacketHandler.INSTANCE.registerMessage(networkId++,
				AstralArmorItemPacketHandler.class,
				AstralArmorItemPacketHandler::encode,
				AstralArmorItemPacketHandler::decode,
				AstralArmorItemPacketHandler::handle
		);
		PacketHandler.INSTANCE.registerMessage(networkId++,
				AstralCrystalBlockPacketHandler.class,
				AstralCrystalBlockPacketHandler::encode,
				AstralCrystalBlockPacketHandler::decode,
				AstralCrystalBlockPacketHandler::handle
		);
		PacketHandler.INSTANCE.registerMessage(networkId++,
				BulletEntityPacketHandler.class,
				BulletEntityPacketHandler::encode,
				BulletEntityPacketHandler::decode,
				BulletEntityPacketHandler::handle
		);
		PacketHandler.INSTANCE.registerMessage(networkId++,
				SyncPlayerDataPacketHandler.class,
				SyncPlayerDataPacketHandler::encode,
				SyncPlayerDataPacketHandler::decode,
				SyncPlayerDataPacketHandler::handle
		);
		PacketHandler.INSTANCE.registerMessage(networkId++,
				DebugDataPacketHandler.class,
				DebugDataPacketHandler::encode,
				DebugDataPacketHandler::decode,
				DebugDataPacketHandler::handle
		);
		PacketHandler.INSTANCE.registerMessage(networkId,
				AmmunitionTableMenuPacketHandler.class,
				AmmunitionTableMenuPacketHandler::encode,
				AmmunitionTableMenuPacketHandler::decode,
				AmmunitionTableMenuPacketHandler::handle
		);
	}
}