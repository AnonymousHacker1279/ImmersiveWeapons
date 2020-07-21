package com.anonymoushacker1279.immersiveweapons.items;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.util.CustomItemMaterials;

import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;

public class CopperSword extends SwordItem {

	public CopperSword() {
		super(CustomItemMaterials.COPPER, 1, -2.4f, new Item.Properties().group(ImmersiveWeapons.TAB));
		
	}
}