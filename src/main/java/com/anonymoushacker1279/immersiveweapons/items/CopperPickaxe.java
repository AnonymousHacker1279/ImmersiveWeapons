package com.anonymoushacker1279.immersiveweapons.items;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.util.CustomItemMaterials;

import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;

public class CopperPickaxe extends PickaxeItem {

	public CopperPickaxe() {
		super(CustomItemMaterials.COPPER, 1, -2.3F, (new Item.Properties().group(ImmersiveWeapons.TAB)));
	}
}