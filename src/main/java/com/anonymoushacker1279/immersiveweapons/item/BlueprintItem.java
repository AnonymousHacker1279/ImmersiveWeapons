package com.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlueprintItem extends Item {
	
	public BlueprintItem(Properties properties) {
		super(properties);
		properties.containerItem(this.getItem());
	}
	
	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		return new ItemStack(getItem());
	}

}
