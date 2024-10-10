package tech.anonymoushacker1279.immersiveweapons.entity.npc.trading;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

public record MerchantTrades(int tradeRefreshTime, List<TradeGroup> tradeGroups) {

	public static final Codec<MerchantTrades> CODEC = RecordCodecBuilder.create(inst -> inst.group(
			Codec.INT.fieldOf("tradeRefreshTime").forGetter((tradeGroup) -> tradeGroup.tradeRefreshTime),
			TradeGroup.CODEC.listOf().fieldOf("tradeGroups").forGetter((tradeGroup) -> tradeGroup.tradeGroups)
	).apply(inst, MerchantTrades::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, MerchantTrades> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.INT,
			MerchantTrades::tradeRefreshTime,
			TradeGroup.STREAM_CODEC.apply(ByteBufCodecs.list()),
			MerchantTrades::tradeGroups,
			MerchantTrades::new
	);
}