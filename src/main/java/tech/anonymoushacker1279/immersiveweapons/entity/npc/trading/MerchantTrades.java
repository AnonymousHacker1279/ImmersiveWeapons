package tech.anonymoushacker1279.immersiveweapons.entity.npc.trading;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record MerchantTrades(int tradeRefreshTime, List<TradeGroup> tradeGroups) {

	public static final Codec<MerchantTrades> CODEC = RecordCodecBuilder.create(inst -> inst.group(
			Codec.INT.fieldOf("tradeRefreshTime").forGetter((tradeGroup) -> tradeGroup.tradeRefreshTime),
			TradeGroup.CODEC.listOf().fieldOf("tradeGroups").forGetter((tradeGroup) -> tradeGroup.tradeGroups)
	).apply(inst, MerchantTrades::new));
}