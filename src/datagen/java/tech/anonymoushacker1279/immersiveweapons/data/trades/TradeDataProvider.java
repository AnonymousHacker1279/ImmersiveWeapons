package tech.anonymoushacker1279.immersiveweapons.data.trades;

import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.trading.MerchantTrades;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.trading.TradeGroup;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public abstract class TradeDataProvider implements DataProvider {

	private final PackOutput packOutput;
	private final Map<EntityType<?>, Integer> tradeRefreshTimeMap = new HashMap<>(5);

	public TradeDataProvider(PackOutput output) {
		this.packOutput = output;
	}

	@Override
	public CompletableFuture<?> run(CachedOutput cache) {
		Path path = this.packOutput.getOutputFolder();
		Map<EntityType<?>, List<TradeGroup>> trades = getTrades();
		List<CompletableFuture<?>> futures = new ArrayList<>(5);

		trades.forEach((type, tradeGroups) -> {
			ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(type);
			Path filePath = path.resolve("data/" + id.getNamespace() + "/merchant_trades/" + id.getPath() + ".json");

			DataResult<JsonElement> tradesResult = MerchantTrades.CODEC.encodeStart(JsonOps.INSTANCE, new MerchantTrades(tradeRefreshTimeMap.getOrDefault(type, 24000), tradeGroups));
			JsonElement tradesElement = tradesResult.result().orElseThrow(() -> new IllegalStateException("Failed to encode merchant trades for " + id));
			futures.add(DataProvider.saveStable(cache, tradesElement, filePath));
		});

		return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
	}

	protected abstract Map<EntityType<?>, List<TradeGroup>> getTrades();

	protected void addTradeRefreshTime(EntityType<?> type, int tradeRefreshTime) {
		tradeRefreshTimeMap.put(type, tradeRefreshTime);
	}

	@Override
	public String getName() {
		return "Merchant Trades";
	}
}