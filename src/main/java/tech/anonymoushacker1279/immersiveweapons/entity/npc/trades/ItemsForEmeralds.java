package tech.anonymoushacker1279.immersiveweapons.entity.npc.trades;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.*;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Block;

public class ItemsForEmeralds implements VillagerTrades.ItemListing {
	private final ItemStack itemStack;
	private final int emeraldCost;
	private final int itemCount;
	private final int maxUses;
	private final int villagerXP;
	private final float priceMultiplier;

	public ItemsForEmeralds(Block block, int emeraldCost, int itemCount, int maxUses) {
		this(new ItemStack(block), emeraldCost, itemCount, maxUses);
	}

	public ItemsForEmeralds(Item item, int emeraldCost, int itemCount, int maxUses) {
		this(new ItemStack(item), emeraldCost, itemCount, maxUses);
	}

	public ItemsForEmeralds(ItemStack itemStack, int emeraldCost, int itemCount, int maxUses) {
		this(itemStack, emeraldCost, itemCount, maxUses, 1, 0.05F);
	}

	public ItemsForEmeralds(ItemStack itemStack, int emeraldCost, int itemCount, int maxUses, int villagerXP, float priceMultiplier) {
		this.itemStack = itemStack;
		this.emeraldCost = emeraldCost;
		this.itemCount = itemCount;
		this.maxUses = maxUses;
		this.villagerXP = villagerXP;
		this.priceMultiplier = priceMultiplier;
	}

	@Override
	public MerchantOffer getOffer(Entity trader, RandomSource randomSource) {
		return new MerchantOffer(new ItemStack(Items.EMERALD, emeraldCost),
				new ItemStack(itemStack.getItem(), itemCount),
				maxUses, villagerXP, priceMultiplier
		);
	}
}