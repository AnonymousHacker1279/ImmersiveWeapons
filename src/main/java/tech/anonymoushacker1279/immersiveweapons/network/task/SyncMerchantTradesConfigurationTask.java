package tech.anonymoushacker1279.immersiveweapons.network.task;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.configuration.ServerConfigurationPacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.network.ConfigurationTask;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.network.configuration.ICustomConfigurationTask;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.trading.MerchantTrades;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.trading.TradeLoader;
import tech.anonymoushacker1279.immersiveweapons.network.payload.SyncMerchantTradesPayload;

import java.util.Map.Entry;
import java.util.function.Consumer;

public record SyncMerchantTradesConfigurationTask(
		ServerConfigurationPacketListener listener) implements ICustomConfigurationTask {

	public static final ConfigurationTask.Type TYPE = new ConfigurationTask.Type(new ResourceLocation(ImmersiveWeapons.MOD_ID, "sync_merchant_trades"));

	@Override
	public void run(final Consumer<CustomPacketPayload> sender) {
		for (Entry<EntityType<?>, MerchantTrades> entry : TradeLoader.TRADES.entrySet()) {
			final SyncMerchantTradesPayload payload = new SyncMerchantTradesPayload(entry.getKey(), entry.getValue());
			sender.accept(payload);
		}

		listener.finishCurrentTask(type());
	}

	@Override
	public ConfigurationTask.Type type() {
		return TYPE;
	}
}