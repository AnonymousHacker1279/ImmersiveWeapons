package tech.anonymoushacker1279.immersiveweapons.entity.npc.trading;

import com.google.gson.*;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import tech.anonymoushacker1279.immersiveweapons.network.payload.SyncMerchantTradesPayload;

import java.util.*;
import java.util.Map.Entry;

public class TradeLoader extends SimpleJsonResourceReloadListener {

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	public static final Map<EntityType<?>, MerchantTrades> TRADES = new HashMap<>(5);

	public TradeLoader() {
		super(GSON, "merchant_trades");
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
		TRADES.clear();
		RegistryOps<JsonElement> registryops = makeConditionalOps();

		pObject.forEach((id, element) -> {
			try {
				MerchantTrades trades = MerchantTrades.CODEC.parse(registryops, element).getOrThrow();
				TRADES.put(BuiltInRegistries.ENTITY_TYPE.get(id), trades);
			} catch (Exception e) {
				throw new IllegalStateException("Failed to load merchant trades from " + id, e);
			}
		});

		// Sync trades to clients
		if (ServerLifecycleHooks.getCurrentServer() != null) {
			for (Entry<EntityType<?>, MerchantTrades> entry : TRADES.entrySet()) {
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