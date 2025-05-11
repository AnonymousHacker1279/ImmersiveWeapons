package tech.anonymoushacker1279.immersiveweapons.entity.npc.trading;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.network.payload.SyncMerchantTradesPayload;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TradeLoader extends SimpleJsonResourceReloadListener<MerchantTrades> {

	public static final Map<EntityType<?>, MerchantTrades> TRADES = new HashMap<>(5);
	public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "merchant_trades");

	public TradeLoader() {
		super(MerchantTrades.CODEC, FileToIdConverter.json("merchant_trades"));
	}

	@Override
	protected void apply(Map<ResourceLocation, MerchantTrades> map, ResourceManager resourceManager, ProfilerFiller profiler) {
		TRADES.clear();

		map.forEach((id, element) -> {
			try {
				ResourceKey<EntityType<?>> key = ResourceKey.create(BuiltInRegistries.ENTITY_TYPE.key(), id);
				TRADES.put(BuiltInRegistries.ENTITY_TYPE.getOrThrow(key).value(), element);
			} catch (Exception e) {
				throw new IllegalStateException("Failed to load merchant trades from " + id, e);
			}
		});

		// Sync trades to clients
		if (ServerLifecycleHooks.getCurrentServer() != null) {
			for (Map.Entry<EntityType<?>, MerchantTrades> entry : TRADES.entrySet()) {
				final SyncMerchantTradesPayload payload = new SyncMerchantTradesPayload(entry.getKey(), entry.getValue());
				PacketDistributor.sendToAllPlayers(payload);
			}
		}
	}


	public static Int2ObjectMap<ItemListing[]> getTrades(EntityType<?> type) {
		Int2ObjectMap<ItemListing[]> tradesMap = new Int2ObjectOpenHashMap<>();
		List<TradeGroup> tradeGroups = TRADES.get(type).tradeGroups();

		for (TradeGroup tradeGroup : tradeGroups) {
			List<SimpleItemListing> trades = tradeGroup.trades();
			ItemListing[] itemListingArray = trades.stream()
					.map(trade -> (ItemListing) new SimpleItemListing(
							trade.item1(),
							trade.item2(),
							trade.result(),
							trade.maxUses(),
							trade.xpReward(),
							trade.priceMultiplier()
					))
					.toArray(ItemListing[]::new);
			tradesMap.put(tradeGroup.entries(), itemListingArray);
		}

		return tradesMap;
	}

	public int getTradeRefreshTime(EntityType<?> type) {
		return TRADES.get(type).tradeRefreshTime();
	}
}