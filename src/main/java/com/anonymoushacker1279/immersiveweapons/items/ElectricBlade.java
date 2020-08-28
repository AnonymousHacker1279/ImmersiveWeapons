package com.anonymoushacker1279.immersiveweapons.items;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class ElectricBlade extends SwordItem {

	public ElectricBlade() {
		super(ItemTier.DIAMOND, 2, -2.4f, new Item.Properties().group(ImmersiveWeapons.TAB));
		
	}
	
	@Override
    public boolean hitEntity(ItemStack par1ItemStack, LivingEntity par2EntityLiving, LivingEntity par3EntityLiving)
    {
    		par2EntityLiving.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 60, 1, false, false));
    		par2EntityLiving.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 60, 1, false, false));
    		return super.hitEntity(par1ItemStack, par2EntityLiving, par3EntityLiving);
    }
}