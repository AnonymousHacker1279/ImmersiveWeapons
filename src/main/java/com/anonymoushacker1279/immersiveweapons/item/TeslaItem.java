package com.anonymoushacker1279.immersiveweapons.item;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class TeslaItem {

	public static class TeslaSword extends SwordItem {
		public TeslaSword(IItemTier tier, int attackDamageIn, float attackSpeedIn) {
			super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().tab(DeferredRegistryHandler.ITEM_GROUP));
		}

		@Override
		public boolean hurtEnemy(ItemStack par1ItemStack, LivingEntity par2EntityLiving, LivingEntity par3EntityLiving) {
			par2EntityLiving.addEffect(new EffectInstance(Effects.WEAKNESS, 140, 1, false, false));
			par2EntityLiving.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 140, 1, false, false));
			par2EntityLiving.addEffect(new EffectInstance(Effects.CONFUSION, 140, 1, false, false));
			return super.hurtEnemy(par1ItemStack, par2EntityLiving, par3EntityLiving);
		}
	}

	public static class TeslaAxe extends AxeItem {

		public TeslaAxe(IItemTier tier, int attackDamageIn, float attackSpeedIn) {
			super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().tab(DeferredRegistryHandler.ITEM_GROUP).fireResistant());
		}

		@Override
		public boolean hurtEnemy(ItemStack par1ItemStack, LivingEntity par2EntityLiving, LivingEntity par3EntityLiving) {
			par2EntityLiving.addEffect(new EffectInstance(Effects.WEAKNESS, 140, 1, false, false));
			par2EntityLiving.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 140, 1, false, false));
			par2EntityLiving.addEffect(new EffectInstance(Effects.CONFUSION, 140, 1, false, false));
			return super.hurtEnemy(par1ItemStack, par2EntityLiving, par3EntityLiving);
		}
	}

	public static class TeslaPickaxe extends PickaxeItem {

		public TeslaPickaxe(IItemTier tier, int attackDamageIn, float attackSpeedIn) {
			super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().tab(DeferredRegistryHandler.ITEM_GROUP).fireResistant());
		}

		@Override
		public boolean hurtEnemy(ItemStack par1ItemStack, LivingEntity par2EntityLiving, LivingEntity par3EntityLiving) {
			par2EntityLiving.addEffect(new EffectInstance(Effects.WEAKNESS, 140, 1, false, false));
			par2EntityLiving.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 140, 1, false, false));
			par2EntityLiving.addEffect(new EffectInstance(Effects.CONFUSION, 140, 1, false, false));
			return super.hurtEnemy(par1ItemStack, par2EntityLiving, par3EntityLiving);
		}
	}

	public static class TeslaShovel extends ShovelItem {

		public TeslaShovel(IItemTier tier, int attackDamageIn, float attackSpeedIn) {
			super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().tab(DeferredRegistryHandler.ITEM_GROUP).fireResistant());
		}

		@Override
		public boolean hurtEnemy(ItemStack par1ItemStack, LivingEntity par2EntityLiving, LivingEntity par3EntityLiving) {
			par2EntityLiving.addEffect(new EffectInstance(Effects.WEAKNESS, 140, 1, false, false));
			par2EntityLiving.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 140, 1, false, false));
			par2EntityLiving.addEffect(new EffectInstance(Effects.CONFUSION, 140, 1, false, false));
			return super.hurtEnemy(par1ItemStack, par2EntityLiving, par3EntityLiving);
		}
	}

	public static class TeslaHoe extends HoeItem {

		public TeslaHoe(IItemTier tier, int attackDamageIn, float attackSpeedIn) {
			super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().tab(DeferredRegistryHandler.ITEM_GROUP).fireResistant());
		}

		@Override
		public boolean hurtEnemy(ItemStack par1ItemStack, LivingEntity par2EntityLiving, LivingEntity par3EntityLiving) {
			par2EntityLiving.addEffect(new EffectInstance(Effects.WEAKNESS, 140, 1, false, false));
			par2EntityLiving.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 140, 1, false, false));
			par2EntityLiving.addEffect(new EffectInstance(Effects.CONFUSION, 140, 1, false, false));
			return super.hurtEnemy(par1ItemStack, par2EntityLiving, par3EntityLiving);
		}
	}
}