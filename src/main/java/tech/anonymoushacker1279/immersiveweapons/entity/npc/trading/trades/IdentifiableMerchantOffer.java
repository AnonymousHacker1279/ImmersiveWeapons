package tech.anonymoushacker1279.immersiveweapons.entity.npc.trading.trades;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;

public class IdentifiableMerchantOffer extends MerchantOffer {

	private String id = "";

	/**
	 * Constructor for IdentifiableMerchantOffer. It allows an ID to be specified so that offers can be easily identified.
	 */
	public IdentifiableMerchantOffer(ItemStack pBaseCostA, ItemStack pResult, ItemStack newEnchantableItem, int pMaxUses, int pXp, float pPriceMultiplier) {
		super(pBaseCostA, pResult, newEnchantableItem, pMaxUses, pXp, pPriceMultiplier);
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}