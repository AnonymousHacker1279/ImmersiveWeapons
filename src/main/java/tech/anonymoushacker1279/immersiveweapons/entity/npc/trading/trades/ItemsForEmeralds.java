package tech.anonymoushacker1279.immersiveweapons.entity.npc.trading.trades;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;

public record ItemsForEmeralds(ItemStack itemStack, int emeraldCost, int maxUses, int xpReward,
                               float priceMultiplier) implements VillagerTrades.ItemListing {

	public static final Codec<ItemsForEmeralds> CODEC = RecordCodecBuilder.create(inst -> inst.group(
			ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("item").forGetter((trade) -> trade.itemStack),
			Codec.INT.fieldOf("emerald_cost").forGetter((trade) -> trade.emeraldCost),
			Codec.INT.fieldOf("max_uses").forGetter((trade) -> trade.maxUses),
			Codec.INT.optionalFieldOf("xp_reward", 1).forGetter((trade) -> trade.xpReward),
			Codec.FLOAT.optionalFieldOf("price_multiplier", 0.05F).forGetter((trade) -> trade.priceMultiplier)
	).apply(inst, ItemsForEmeralds::new));

	public ItemsForEmeralds(ItemStack itemStack, int emeraldCost, int maxUses) {
		this(itemStack, emeraldCost, maxUses, 1, 0.05F);
	}

	@Override
	public MerchantOffer getOffer(Entity trader, RandomSource randomSource) {
		return new MerchantOffer(new ItemStack(Items.EMERALD, emeraldCost), itemStack, maxUses, xpReward, priceMultiplier);
	}
}