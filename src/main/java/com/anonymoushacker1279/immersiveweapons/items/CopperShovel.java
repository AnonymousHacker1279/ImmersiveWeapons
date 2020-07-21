package com.anonymoushacker1279.immersiveweapons.items;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.util.CustomItemMaterials;

import net.minecraft.item.Item;
import net.minecraft.item.ShovelItem;

public class CopperShovel extends ShovelItem {

	public CopperShovel() {
		super(CustomItemMaterials.COPPER, -2, -2.7f, (new Item.Properties().group(ImmersiveWeapons.TAB)));
	}

}
