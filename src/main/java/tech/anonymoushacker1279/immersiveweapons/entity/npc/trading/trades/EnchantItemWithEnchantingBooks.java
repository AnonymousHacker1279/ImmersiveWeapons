package tech.anonymoushacker1279.immersiveweapons.entity.npc.trading.trades;

import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.Optional;

public class EnchantItemWithEnchantingBooks implements VillagerTrades.ItemListing {
	private final ItemStack enchantableItem;
	private final ItemStack tradingItem;
	private final int maxUses;
	private int villagerXP;
	private int totalEnchantmentLevels;

	public EnchantItemWithEnchantingBooks(ItemStack enchantableItem, ItemStack tradingItem, int maxUses) {
		this(enchantableItem, tradingItem, maxUses, 1);
	}

	public EnchantItemWithEnchantingBooks(ItemStack enchantableItem, ItemStack tradingItem, int maxUses, int villagerXP) {
		this.enchantableItem = enchantableItem.copy();
		this.tradingItem = tradingItem.copy();
		this.maxUses = maxUses;
		this.villagerXP = villagerXP;
	}

	@Override
	public MerchantOffer getOffer(Entity trader, RandomSource randomSource) {
		// If there are any enchantments on the item, add the enchantments from the book
		ItemStack newEnchantableItem = enchantableItem.copy();
		if (newEnchantableItem.isEnchanted()) {
			ItemEnchantments enchantments = newEnchantableItem.getEnchantments();
			ItemEnchantments bookEnchantments = tradingItem.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY);

			// Add the existing enchantments, increasing the level if the book has a higher level
			bookEnchantments.keySet().forEach(enchantmentHolder -> {
				int level = bookEnchantments.getLevel(enchantmentHolder);
				int currentLevel = enchantments.getLevel(enchantmentHolder);

				if ((currentLevel < level) || (currentLevel == 0 && newEnchantableItem.supportsEnchantment(enchantmentHolder))) {
					EnchantmentHelper.updateEnchantments(newEnchantableItem, mutable -> mutable.upgrade(enchantmentHolder, level));
				}
			});
		}

		// Add up the total levels of all enchantments
		RegistryLookup<Enchantment> enchantmentLookup = trader.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
		totalEnchantmentLevels = GeneralUtilities.getTotalEnchantmentLevels(enchantmentLookup, newEnchantableItem);

		// Give XP based on the total levels of all enchantments
		villagerXP = randomSource.nextIntBetweenInclusive(totalEnchantmentLevels / 4, totalEnchantmentLevels / 2);

		IdentifiableMerchantOffer offer = new IdentifiableMerchantOffer(new ItemCost(enchantableItem.getItem()), Optional.of(new ItemCost(tradingItem.getItem())), newEnchantableItem, maxUses, villagerXP, 0);
		offer.setId("enchant_item_with_enchanting_books");
		return offer;
	}

	public int getTotalEnchantmentLevels() {
		return totalEnchantmentLevels;
	}
}