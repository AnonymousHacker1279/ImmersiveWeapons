package com.anonymoushacker1279.immersiveweapons.items;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.util.CustomItemMaterials;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;

public class MoltenPickaxe extends PickaxeItem {

	public MoltenPickaxe() {
		super(CustomItemMaterials.MOLTEN, 2, -2.3F, (new Item.Properties().group(ImmersiveWeapons.TAB)));
	}
	
	@Override
    public boolean hitEntity(ItemStack par1ItemStack, LivingEntity par2EntityLiving, LivingEntity par3EntityLiving)
    {
    		par2EntityLiving.setFire(6);
    		return super.hitEntity(par1ItemStack, par2EntityLiving, par3EntityLiving);
    }
}