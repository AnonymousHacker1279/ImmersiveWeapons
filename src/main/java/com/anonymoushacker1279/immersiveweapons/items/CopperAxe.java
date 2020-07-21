package com.anonymoushacker1279.immersiveweapons.items;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.util.CustomItemMaterials;

import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;

public class CopperAxe extends AxeItem {

	public CopperAxe() {
		super(CustomItemMaterials.COPPER, 3, -3.0f, (new Item.Properties().group(ImmersiveWeapons.TAB)));
	}

}
