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

	public CreativeTabSorter(String label) {
		super(label);
	}

	@Override
	public void fill(NonNullList<ItemStack> itemStack) {
		List<Item> items = new ArrayList<Item>();
		DeferredRegistryHandler.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(item -> {
			items.add(item);
		});

		for (Item item : items) {
			item.fillItemGroup(this, itemStack);
		}
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(DeferredRegistryHandler.TESLA_SWORD.get());
	}

}