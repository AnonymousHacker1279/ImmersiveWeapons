package com.anonymoushacker1279.immersiveweapons.items;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.util.CustomItemMaterials;

import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;

public class CopperHoe extends HoeItem {

	public CopperHoe() {
		super(CustomItemMaterials.COPPER, 1.0f, (new Item.Properties().group(ImmersiveWeapons.TAB)));
	}

}
