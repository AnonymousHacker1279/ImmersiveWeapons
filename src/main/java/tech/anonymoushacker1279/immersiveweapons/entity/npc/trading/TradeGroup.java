package tech.anonymoushacker1279.immersiveweapons.entity.npc.trading;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.trading.trades.ItemsForEmeralds;

import java.util.List;

public record TradeGroup(int entries, List<ItemsForEmeralds> trades) {

	public static final Codec<TradeGroup> CODEC = RecordCodecBuilder.create(inst -> inst.group(
			Codec.INT.fieldOf("entries").forGetter((tradeGroup) -> tradeGroup.entries),
			ItemsForEmeralds.CODEC.listOf().fieldOf("trades").forGetter((tradeGroup) -> tradeGroup.trades)
	).apply(inst, TradeGroup::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, TradeGroup> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.INT,
			TradeGroup::entries,
			ItemsForEmeralds.STREAM_CODEC.apply(ByteBufCodecs.list()),
			TradeGroup::trades,
			TradeGroup::new
	);
}