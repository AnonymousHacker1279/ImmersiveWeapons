package tech.anonymoushacker1279.immersiveweapons.item.utility;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CraftingToolItem extends Item {

	/**
	 * A basic item type that simply is not used up during crafting.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	public CraftingToolItem(Properties properties) {
		super(properties);
	}

	@Override
	public ItemStack getCraftingRemainder(ItemStack itemStack) {
		return itemStack.copy();
	}
}