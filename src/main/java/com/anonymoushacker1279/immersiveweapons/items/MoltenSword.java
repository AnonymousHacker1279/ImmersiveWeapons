package com.anonymoushacker1279.immersiveweapons.items;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.util.CustomItemMaterials;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

public class MoltenSword extends SwordItem {
	public static final Logger LOGGER = LogManager.getLogger();

	public MoltenSword() {
		super(CustomItemMaterials.MOLTEN, 3, -2.1f, new Item.Properties().group(ImmersiveWeapons.TAB));
	}
	
	@Override
    public boolean hitEntity(ItemStack par1ItemStack, LivingEntity par2EntityLiving, LivingEntity par3EntityLiving)
    {
    		par2EntityLiving.setFire(6);
    		return super.hitEntity(par1ItemStack, par2EntityLiving, par3EntityLiving);
    }
	
}