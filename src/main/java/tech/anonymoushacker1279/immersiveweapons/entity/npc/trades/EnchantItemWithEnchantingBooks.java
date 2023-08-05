package tech.anonymoushacker1279.immersiveweapons.entity.npc.trades;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.MerchantOffer;

import java.util.HashMap;
import java.util.Map;

public class EnchantItemWithEnchantingBooks implements VillagerTrades.ItemListing {
	private final ItemStack enchantableItem;
	private final ItemStack tradingItem;
	private final int maxUses;
	private final int villagerXP;
	private final float priceMultiplier;

	public EnchantItemWithEnchantingBooks(ItemStack enchantableItem, ItemStack tradingItem, int maxUses) {
		this(enchantableItem, tradingItem, maxUses, 1, 0.05F);
	}

	public EnchantItemWithEnchantingBooks(ItemStack enchantableItem, ItemStack tradingItem, int maxUses, int villagerXP, float priceMultiplier) {
		this.enchantableItem = enchantableItem.copy();
		this.tradingItem = tradingItem.copy();
		this.maxUses = maxUses;
		this.villagerXP = villagerXP;
		this.priceMultiplier = priceMultiplier;
	}

	@Override
	public MerchantOffer getOffer(Entity trader, RandomSource randomSource) {
		// If there are any enchantments on the item, add the enchantments from the book
		ItemStack newEnchantableItem = enchantableItem.copy();
		if (newEnchantableItem.isEnchanted()) {
			Map<Enchantment, Integer> existingEnchantments = newEnchantableItem.getAllEnchantments();
			Map<Enchantment, Integer> enchantmentsFromBook = new HashMap<>(EnchantmentHelper.getEnchantments(tradingItem));

			// Remove the old enchantments
			newEnchantableItem.removeTagKey("Enchantments");

			// Add all the existing enchantments with the new ones. If there is a shared enchantment, use the higher level from the book
			existingEnchantments.forEach((enchantment, level) -> newEnchantableItem.enchant(enchantment, Math.max(level, enchantmentsFromBook.getOrDefault(enchantment, 0))));
		}

		return new MerchantOffer(enchantableItem, tradingItem, newEnchantableItem, maxUses, villagerXP, priceMultiplier);
	}
}