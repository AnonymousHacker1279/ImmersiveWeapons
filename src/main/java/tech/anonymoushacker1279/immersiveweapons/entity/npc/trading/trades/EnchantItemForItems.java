package tech.anonymoushacker1279.immersiveweapons.entity.npc.trading.trades;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.trading.MerchantOffer;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.config.CommonConfig;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.Map;

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
			Map<Enchantment, Integer> enchantments = newEnchantableItem.getAllEnchantments();

			// Remove the old enchantments
			newEnchantableItem.removeTagKey("Enchantments");

			enchantments.forEach((enchantment, level) -> {
				// Levels above 255 cannot exist because the game clamps levels upon reading
				if (level >= 255) {
					GeneralUtilities.unrestrictedEnchant(newEnchantableItem, enchantment, 255);
					return;
				}

				// Get the max level for this enchantment
				ResourceLocation enchantmentLocation = BuiltInRegistries.ENCHANTMENT.getKey(enchantment);

				if (enchantmentLocation == null) {
					ImmersiveWeapons.LOGGER.error("Failed to locate enchantment {} in registry", enchantment);
					return;
				}

				int maxLevel = CommonConfig.skygazerEnchantCaps.getOrDefault(enchantmentLocation.toString(), -1);

				// If the level is -1, it's uncapped
				if (maxLevel == -1) {
					GeneralUtilities.unrestrictedEnchant(newEnchantableItem, enchantment, level + 1);
				}
				// Otherwise, cap it at the max level
				else {
					GeneralUtilities.unrestrictedEnchant(newEnchantableItem, enchantment, Math.min(level + 1, maxLevel));
				}
			});

			// Add up the total levels of all enchantments
			totalEnchantmentLevels = GeneralUtilities.getTotalEnchantmentLevels(newEnchantableItem);

			// The item cost rises exponentially with higher enchantment levels
			// It caps at 32
			itemCost = Math.min(CommonConfig.skygazerMaxEnchantUpgradeCost, (int) Math.pow(1.3, ((float) totalEnchantmentLevels / 2)));

			// Give XP based on the total levels of all enchantments
			villagerXP = randomSource.nextIntBetweenInclusive(totalEnchantmentLevels / 2, totalEnchantmentLevels);
		}

		IdentifiableMerchantOffer offer = new IdentifiableMerchantOffer(enchantableItem, new ItemStack(tradingItem, itemCost), newEnchantableItem, maxUses, villagerXP, 0);
		offer.setId("enchant_item_for_items");
		return offer;
	}

	public int getTotalEnchantmentLevels() {
		return totalEnchantmentLevels;
	}
}