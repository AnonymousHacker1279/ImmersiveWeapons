package com.anonymoushacker1279.immersiveweapons.items;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;

public class MoltenItem {

	public static class MoltenSword extends SwordItem {
		public MoltenSword(IItemTier tier, int attackDamageIn, float attackSpeedIn) {
			super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().group(ImmersiveWeapons.TAB).isImmuneToFire());
		}
		
		@Override
	    public boolean hitEntity(ItemStack par1ItemStack, LivingEntity par2EntityLiving, LivingEntity par3EntityLiving)
	    {
	    		par2EntityLiving.setFire(6);
	    		return super.hitEntity(par1ItemStack, par2EntityLiving, par3EntityLiving);
	    }
	}
	
	public static class MoltenAxe extends AxeItem {

		public MoltenAxe(IItemTier tier, int attackDamageIn, float attackSpeedIn) {
			super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().group(ImmersiveWeapons.TAB).isImmuneToFire());
		}
		
		@Override
	    public boolean hitEntity(ItemStack par1ItemStack, LivingEntity par2EntityLiving, LivingEntity par3EntityLiving)
	    {
	    		par2EntityLiving.setFire(6);
	    		return super.hitEntity(par1ItemStack, par2EntityLiving, par3EntityLiving);
	    }
	}
	
	public static class MoltenPickaxe extends PickaxeItem {

		public MoltenPickaxe(IItemTier tier, int attackDamageIn, float attackSpeedIn) {
			super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().group(ImmersiveWeapons.TAB).isImmuneToFire());
		}
		
		@Override
	    public boolean hitEntity(ItemStack par1ItemStack, LivingEntity par2EntityLiving, LivingEntity par3EntityLiving)
	    {
	    		par2EntityLiving.setFire(6);
	    		return super.hitEntity(par1ItemStack, par2EntityLiving, par3EntityLiving);
	    }
	}
	
	public static class MoltenShovel extends ShovelItem {

		public MoltenShovel(IItemTier tier, int attackDamageIn, float attackSpeedIn) {
			super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().group(ImmersiveWeapons.TAB).isImmuneToFire());
		}
		
		@Override
	    public boolean hitEntity(ItemStack par1ItemStack, LivingEntity par2EntityLiving, LivingEntity par3EntityLiving)
	    {
	    		par2EntityLiving.setFire(6);
	    		return super.hitEntity(par1ItemStack, par2EntityLiving, par3EntityLiving);
	    }
	}

	public static class MoltenHoe extends HoeItem {

		public MoltenHoe(IItemTier tier, int attackDamageIn, float attackSpeedIn) {
			super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().group(ImmersiveWeapons.TAB).isImmuneToFire());
		}
		
		@Override
	    public boolean hitEntity(ItemStack par1ItemStack, LivingEntity par2EntityLiving, LivingEntity par3EntityLiving)
	    {
	    		par2EntityLiving.setFire(6);
	    		return super.hitEntity(par1ItemStack, par2EntityLiving, par3EntityLiving);
	    }
	}

}
