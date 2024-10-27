package tech.anonymoushacker1279.immersiveweapons.entity.npc.trading;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;

import java.util.Optional;

public record SimpleItemListing(ItemStack item1, ItemStack item2, ItemStack result, int maxUses, int xpReward,
                                float priceMultiplier) implements ItemListing {

	public SimpleItemListing(ItemStack item1, ItemStack result, int maxUses, int xpReward, float priceMultiplier) {
		this(item1, ItemStack.EMPTY, result, maxUses, xpReward, priceMultiplier);
	}

	public SimpleItemListing(int emeralds, ItemStack result, int maxUses, int xpReward) {
		this(new ItemStack(Items.EMERALD, emeralds), result, maxUses, xpReward, 1);
	}

	public SimpleItemListing(int emeralds, ItemStack result, int maxUses) {
		this(new ItemStack(Items.EMERALD, emeralds), result, maxUses, 1, 1);
	}

	public static final Codec<SimpleItemListing> CODEC = RecordCodecBuilder.create(inst -> inst.group(
			ItemStack.CODEC.fieldOf("item_1").forGetter((trade) -> trade.item1),
			ItemStack.CODEC.optionalFieldOf("item_2", ItemStack.EMPTY).forGetter((trade) -> trade.item2),
			ItemStack.CODEC.fieldOf("result").forGetter((trade) -> trade.result),
			Codec.INT.fieldOf("max_uses").forGetter((trade) -> trade.maxUses),
			Codec.INT.optionalFieldOf("xp_reward", 1).forGetter((trade) -> trade.xpReward),
			Codec.FLOAT.optionalFieldOf("price_multiplier", 1f).forGetter((trade) -> trade.priceMultiplier)
	).apply(inst, SimpleItemListing::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, SimpleItemListing> STREAM_CODEC = StreamCodec.composite(
			ItemStack.STREAM_CODEC,
			SimpleItemListing::item1,
			ItemStack.STREAM_CODEC,
			SimpleItemListing::item2,
			ItemStack.STREAM_CODEC,
			SimpleItemListing::result,
			ByteBufCodecs.INT,
			SimpleItemListing::maxUses,
			ByteBufCodecs.INT,
			SimpleItemListing::xpReward,
			ByteBufCodecs.FLOAT,
			SimpleItemListing::priceMultiplier,
			SimpleItemListing::new
	);

	@Override
	public MerchantOffer getOffer(Entity p_219693_, RandomSource p_219694_) {
		ItemCost cost = new ItemCost(item1.getItemHolder(), item1.getCount(), DataComponentPredicate.EMPTY, item1);
		Optional<ItemCost> optionalSecondCost = item2.isEmpty() ? Optional.empty() : Optional.of(new ItemCost(item2.getItemHolder(), item2.getCount(), DataComponentPredicate.EMPTY, item2));
		return new MerchantOffer(cost, optionalSecondCost, result, maxUses, xpReward, priceMultiplier);
	}
}