package tech.anonymoushacker1279.immersiveweapons.item.utility;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class BasicContainerItem extends Item {

	/**
	 * Constructor for BasicContainerItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	public BasicContainerItem(Properties properties) {
		super(properties);
		properties.craftRemainder(asItem());
	}

	/**
	 * Check if the item has a container item.
	 *
	 * @param stack the <code>ItemStack</code> to be checked
	 * @return boolean
	 */
	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}

	/**
	 * Get the container item.
	 *
	 * @param stack the <code>ItemStack</code> to get a container item for
	 * @return ItemStack
	 */
	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		return new ItemStack(asItem());
	}
}