package tech.anonymoushacker1279.immersiveweapons.entity.npc.trading.trades;

import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;
import tech.anonymoushacker1279.immersiveweapons.config.ServerConfig;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.Optional;

public class EnchantItemForItems implements VillagerTrades.ItemListing {
	private final ItemStack enchantableItem;
	private final Item tradingItem;
	private int itemCost;
	private final int maxUses;
	private int villagerXP;
	private int totalEnchantmentLevels;

	public EnchantItemForItems(ItemStack enchantableItem, Item tradingItem, int maxUses) {
		this.enchantableItem = enchantableItem.copy();
		this.tradingItem = tradingItem;
		this.maxUses = maxUses;
	}

	@Override
	public MerchantOffer getOffer(Entity trader, RandomSource randomSource) {
		// If there are any enchantments on the item, increase the enchantment level by 1
		ItemStack newEnchantableItem = enchantableItem.copy();
		if (newEnchantableItem.isEnchanted()) {
			ItemEnchantments enchantments = newEnchantableItem.getTagEnchantments();

			enchantments.keySet().forEach(enchantmentHolder -> {
				int maxLevel = ServerConfig.getEnchantCap(enchantmentHolder.getRegisteredName());
				int currentLevel = enchantments.getLevel(enchantmentHolder);

				if (maxLevel == -1) {
					EnchantmentHelper.updateEnchantments(newEnchantableItem, mutable -> mutable.upgrade(enchantmentHolder, currentLevel + 1));
				} else {
					EnchantmentHelper.updateEnchantments(newEnchantableItem, mutable -> mutable.upgrade(enchantmentHolder, Math.min(currentLevel + 1, maxLevel)));
				}
			});

			// Add up the total levels of all enchantments
			RegistryLookup<Enchantment> enchantmentLookup = trader.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
			totalEnchantmentLevels = GeneralUtilities.getTotalEnchantmentLevels(enchantmentLookup, newEnchantableItem);

			// The item cost rises exponentially with higher enchantment levels
			// It caps at 32
			itemCost = Math.min(IWConfigs.SERVER.skygazerMaxEnchantUpgradeCost.getAsInt(), (int) Math.pow(1.3, ((float) totalEnchantmentLevels / 2)));

			// Give XP based on the total levels of all enchantments
			villagerXP = randomSource.nextIntBetweenInclusive(totalEnchantmentLevels / 2, totalEnchantmentLevels);
		}

		IdentifiableMerchantOffer offer = new IdentifiableMerchantOffer(new ItemCost(enchantableItem.getItem()), Optional.of(new ItemCost(tradingItem, itemCost)), newEnchantableItem, maxUses, villagerXP, 0);
		offer.setId("enchant_item_for_items");
		return offer;
	}

	public int getTotalEnchantmentLevels() {
		return totalEnchantmentLevels;
	}
}