package tech.anonymoushacker1279.immersiveweapons.entity.npc.trading;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.trading.trades.ItemsForEmeralds;

import java.util.List;

public record TradeGroup(int entries, List<ItemsForEmeralds> trades) {

	public static final Codec<TradeGroup> CODEC = RecordCodecBuilder.create(inst -> inst.group(
			Codec.INT.fieldOf("entries").forGetter((tradeGroup) -> tradeGroup.entries),
			ItemsForEmeralds.CODEC.listOf().fieldOf("trades").forGetter((tradeGroup) -> tradeGroup.trades)
	).apply(inst, TradeGroup::new));
}