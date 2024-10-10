package tech.anonymoushacker1279.immersiveweapons.entity.npc.trading.trades;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;

import java.util.Optional;

public class IdentifiableMerchantOffer extends MerchantOffer {

	private String id = "";

	/**
	 * Constructor for IdentifiableMerchantOffer. It allows an ID to be specified so that offers can be easily identified.
	 */
	public IdentifiableMerchantOffer(ItemCost baseCostA, Optional<ItemCost> costB, ItemStack result, int maxUses, int xp, float priceMultiplier) {
		super(baseCostA, costB, result, maxUses, xp, priceMultiplier);
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}