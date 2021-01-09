package com.anonymoushacker1279.immersiveweapons.items;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MoltenArmorItem extends ArmorItem{

	private boolean isLeggings = false;
	
	public MoltenArmorItem(IArmorMaterial material, EquipmentSlotType slot, int type) {
		super(material, slot, (new Item.Properties().group(ImmersiveWeapons.TAB)));
		if (type == 2) {
			isLeggings = true;
		}
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return (isLeggings == false ? ImmersiveWeapons.MOD_ID+":textures/armor/molten_layer_1.png" : ImmersiveWeapons.MOD_ID+":textures/armor/molten_layer_2.png");
	}
}
