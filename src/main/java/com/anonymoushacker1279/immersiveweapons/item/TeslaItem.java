package com.anonymoushacker1279.immersiveweapons.item;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class TeslaItem {

	public static class TeslaSword extends SwordItem {
		public TeslaSword(IItemTier tier, int attackDamageIn, float attackSpeedIn) {
			super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().group(DeferredRegistryHandler.ITEM_GROUP));
		}
		
		@Override
		public boolean hitEntity(ItemStack par1ItemStack, LivingEntity par2EntityLiving, LivingEntity par3EntityLiving) {
			par2EntityLiving.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 140, 1, false, false));
			par2EntityLiving.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 140, 1, false, false));
			par2EntityLiving.addPotionEffect(new EffectInstance(Effects.NAUSEA, 140, 1, false, false));
			return super.hitEntity(par1ItemStack, par2EntityLiving, par3EntityLiving);
		}
	}
	
	public static class TeslaAxe extends AxeItem {

		public TeslaAxe(IItemTier tier, int attackDamageIn, float attackSpeedIn) {
			super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().group(DeferredRegistryHandler.ITEM_GROUP).isImmuneToFire());
		}
		
		@Override
		public boolean hitEntity(ItemStack par1ItemStack, LivingEntity par2EntityLiving, LivingEntity par3EntityLiving) {
			par2EntityLiving.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 140, 1, false, false));
			par2EntityLiving.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 140, 1, false, false));
			par2EntityLiving.addPotionEffect(new EffectInstance(Effects.NAUSEA, 140, 1, false, false));
			return super.hitEntity(par1ItemStack, par2EntityLiving, par3EntityLiving);
		}
	}
	
	public static class TeslaPickaxe extends PickaxeItem {

		public TeslaPickaxe(IItemTier tier, int attackDamageIn, float attackSpeedIn) {
			super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().group(DeferredRegistryHandler.ITEM_GROUP).isImmuneToFire());
		}
		
		@Override
		public boolean hitEntity(ItemStack par1ItemStack, LivingEntity par2EntityLiving, LivingEntity par3EntityLiving) {
			par2EntityLiving.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 140, 1, false, false));
			par2EntityLiving.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 140, 1, false, false));
			par2EntityLiving.addPotionEffect(new EffectInstance(Effects.NAUSEA, 140, 1, false, false));
			return super.hitEntity(par1ItemStack, par2EntityLiving, par3EntityLiving);
		}
	}
	
	public static class TeslaShovel extends ShovelItem {

		public TeslaShovel(IItemTier tier, int attackDamageIn, float attackSpeedIn) {
			super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().group(DeferredRegistryHandler.ITEM_GROUP).isImmuneToFire());
		}
		
		@Override
		public boolean hitEntity(ItemStack par1ItemStack, LivingEntity par2EntityLiving, LivingEntity par3EntityLiving) {
			par2EntityLiving.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 140, 1, false, false));
			par2EntityLiving.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 140, 1, false, false));
			par2EntityLiving.addPotionEffect(new EffectInstance(Effects.NAUSEA, 140, 1, false, false));
			return super.hitEntity(par1ItemStack, par2EntityLiving, par3EntityLiving);
		}
	}
	
	public static class TeslaHoe extends HoeItem {

		public TeslaHoe(IItemTier tier, int attackDamageIn, float attackSpeedIn) {
			super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().group(DeferredRegistryHandler.ITEM_GROUP).isImmuneToFire());
		}
		
		@Override
		public boolean hitEntity(ItemStack par1ItemStack, LivingEntity par2EntityLiving, LivingEntity par3EntityLiving) {
			par2EntityLiving.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 140, 1, false, false));
			par2EntityLiving.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 140, 1, false, false));
			par2EntityLiving.addPotionEffect(new EffectInstance(Effects.NAUSEA, 140, 1, false, false));
			return super.hitEntity(par1ItemStack, par2EntityLiving, par3EntityLiving);
		}
	}
}