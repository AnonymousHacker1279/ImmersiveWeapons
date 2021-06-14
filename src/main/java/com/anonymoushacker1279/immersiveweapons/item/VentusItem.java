package com.anonymoushacker1279.immersiveweapons.item;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class VentusItem {

	public static class VentusSword extends SwordItem {
		public VentusSword(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties tab) {
			super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().tab(DeferredRegistryHandler.ITEM_GROUP));
		}

		@Override
		public boolean hurtEnemy(ItemStack par1ItemStack, LivingEntity par2EntityLiving, LivingEntity par3EntityLiving) {
			par2EntityLiving.addEffect(new EffectInstance(Effects.LEVITATION, 60, 2, false, false));
			return super.hurtEnemy(par1ItemStack, par2EntityLiving, par3EntityLiving);
		}
	}

	public static class VentusAxe extends AxeItem {

		public VentusAxe(IItemTier tier, int attackDamageIn, float attackSpeedIn) {
			super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().tab(DeferredRegistryHandler.ITEM_GROUP).fireResistant());
		}

		@Override
		public boolean hurtEnemy(ItemStack par1ItemStack, LivingEntity par2EntityLiving, LivingEntity par3EntityLiving) {
			par2EntityLiving.addEffect(new EffectInstance(Effects.LEVITATION, 60, 2, false, false));
			return super.hurtEnemy(par1ItemStack, par2EntityLiving, par3EntityLiving);
		}
	}

	public static class VentusPickaxe extends PickaxeItem {

		public VentusPickaxe(IItemTier tier, int attackDamageIn, float attackSpeedIn) {
			super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().tab(DeferredRegistryHandler.ITEM_GROUP).fireResistant());
		}

		@Override
		public boolean hurtEnemy(ItemStack par1ItemStack, LivingEntity par2EntityLiving, LivingEntity par3EntityLiving) {
			par2EntityLiving.addEffect(new EffectInstance(Effects.LEVITATION, 60, 2, false, false));
			return super.hurtEnemy(par1ItemStack, par2EntityLiving, par3EntityLiving);
		}
	}

	public static class VentusShovel extends ShovelItem {

		public VentusShovel(IItemTier tier, int attackDamageIn, float attackSpeedIn) {
			super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().tab(DeferredRegistryHandler.ITEM_GROUP).fireResistant());
		}

		@Override
		public boolean hurtEnemy(ItemStack par1ItemStack, LivingEntity par2EntityLiving, LivingEntity par3EntityLiving) {
			par2EntityLiving.addEffect(new EffectInstance(Effects.LEVITATION, 60, 2, false, false));
			return super.hurtEnemy(par1ItemStack, par2EntityLiving, par3EntityLiving);
		}
	}

	public static class VentusHoe extends HoeItem {

		public VentusHoe(IItemTier tier, int attackDamageIn, float attackSpeedIn) {
			super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().tab(DeferredRegistryHandler.ITEM_GROUP).fireResistant());
		}

		@Override
		public boolean hurtEnemy(ItemStack par1ItemStack, LivingEntity par2EntityLiving, LivingEntity par3EntityLiving) {
			par2EntityLiving.addEffect(new EffectInstance(Effects.LEVITATION, 60, 2, false, false));
			return super.hurtEnemy(par1ItemStack, par2EntityLiving, par3EntityLiving);
		}
	}
}