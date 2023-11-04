package tech.anonymoushacker1279.immersiveweapons.entity.npc.trades;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.neoforge.registries.ForgeRegistries;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.entity.npc.SkygazerEntity;

import java.util.Map;

public class EnchantItemForItems implements VillagerTrades.ItemListing {
	private final ItemStack enchantableItem;
	private final Item tradingItem;
	private int itemCost;
	private final int maxUses;
	private final int villagerXP;
	private final float priceMultiplier;

	public EnchantItemForItems(ItemStack enchantableItem, Item tradingItem, int maxUses) {
		this(enchantableItem, tradingItem, maxUses, 1, 0.05F);
	}

	public EnchantItemForItems(ItemStack enchantableItem, Item tradingItem, int maxUses, int villagerXP, float priceMultiplier) {
		this.enchantableItem = enchantableItem.copy();
		this.tradingItem = tradingItem;
		this.maxUses = maxUses;
		this.villagerXP = villagerXP;
		this.priceMultiplier = priceMultiplier;
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
				// Get the max level for this enchantment
				ResourceLocation enchantmentLocation = ForgeRegistries.ENCHANTMENTS.getKey(enchantment);

				if (enchantmentLocation == null) {
					ImmersiveWeapons.LOGGER.error("Failed to locate enchantment {} in registry", enchantment);
					return;
				}

				int maxLevel = SkygazerEntity.ENCHANT_CAPS.getOrDefault(enchantmentLocation.toString(), -1);

				// If the level is -1, it's uncapped
				if (maxLevel == -1) {
					newEnchantableItem.enchant(enchantment, level + 1);
				}
				// Otherwise, cap it at the max level
				else {
					newEnchantableItem.enchant(enchantment, Math.min(level + 1, maxLevel));
				}
			});

			// Add up the total levels of all enchantments
			int totalEnchantmentLevels = enchantments.values().stream().mapToInt(Integer::intValue).sum();

			// The item cost rises exponentially with higher enchantment levels
			// It caps at 32
			itemCost = Math.min(32, (int) Math.pow(1.3, ((float) totalEnchantmentLevels / 2)));
		}

		return new MerchantOffer(enchantableItem, new ItemStack(tradingItem, itemCost), newEnchantableItem, maxUses, villagerXP, priceMultiplier);
	}
}