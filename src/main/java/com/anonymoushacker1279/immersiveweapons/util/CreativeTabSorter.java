package com.anonymoushacker1279.immersiveweapons.util;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fmllegacy.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CreativeTabSorter extends CreativeModeTab {

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
	public void fillItemList(@NotNull NonNullList<ItemStack> itemStack) {
		List<Item> items = new ArrayList<>(1);
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
	public @NotNull ItemStack makeIcon() {
		return new ItemStack(DeferredRegistryHandler.TESLA_SWORD.get());
	}

}