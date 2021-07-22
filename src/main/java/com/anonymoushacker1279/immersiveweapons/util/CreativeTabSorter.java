package com.anonymoushacker1279.immersiveweapons.util;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class CreativeTabSorter extends ItemGroup {

	/**
	 * Constructor for CreativeTabSorter.
	 * @param label the tab label
	 */
	public CreativeTabSorter(String label) {
		super(label);
	}

	/**
	 * Fill the item list with the proper ordering.
	 * @param itemStack the <code>NonNullList</code> extending ItemStack
	 */
	@Override
	public void fillItemList(NonNullList<ItemStack> itemStack) {
		List<Item> items = new ArrayList<>();
		DeferredRegistryHandler.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(items::add);

		for (Item item : items) {
			item.fillItemCategory(this, itemStack);
		}
	}

	/**
	 * Set the tab icon.
	 * @return ItemStack
	 */
	@Override
	public ItemStack makeIcon() {
		return new ItemStack(DeferredRegistryHandler.TESLA_SWORD.get());
	}

}