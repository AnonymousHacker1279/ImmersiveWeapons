package com.anonymoushacker1279.immersiveweapons.item;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;

public class MoltenItem {

	public static class MoltenSword extends SwordItem {
		public MoltenSword(IItemTier tier, int attackDamageIn, float attackSpeedIn) {
			super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().tab(DeferredRegistryHandler.ITEM_GROUP).fireResistant());
		}

		@Override
		public boolean hurtEnemy(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity1) {
			livingEntity.setSecondsOnFire(10);
			return super.hurtEnemy(itemStack, livingEntity, livingEntity1);
		}
	}

	public static class MoltenAxe extends AxeItem {

		public MoltenAxe(IItemTier tier, int attackDamageIn, float attackSpeedIn) {
			super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().tab(DeferredRegistryHandler.ITEM_GROUP).fireResistant());
		}

		@Override
		public boolean hurtEnemy(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity1) {
			livingEntity.setSecondsOnFire(10);
			return super.hurtEnemy(itemStack, livingEntity, livingEntity1);
		}
	}

	public static class MoltenPickaxe extends PickaxeItem {

		public MoltenPickaxe(IItemTier tier, int attackDamageIn, float attackSpeedIn) {
			super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().tab(DeferredRegistryHandler.ITEM_GROUP).fireResistant());
		}

		@Override
		public boolean hurtEnemy(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity1) {
			livingEntity.setSecondsOnFire(10);
			return super.hurtEnemy(itemStack, livingEntity, livingEntity1);
		}
	}

	public static class MoltenShovel extends ShovelItem {

		public MoltenShovel(IItemTier tier, int attackDamageIn, float attackSpeedIn) {
			super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().tab(DeferredRegistryHandler.ITEM_GROUP).fireResistant());
		}

		@Override
		public boolean hurtEnemy(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity1) {
			livingEntity.setSecondsOnFire(10);
			return super.hurtEnemy(itemStack, livingEntity, livingEntity1);
		}
	}

	public static class MoltenHoe extends HoeItem {

		public MoltenHoe(IItemTier tier, int attackDamageIn, float attackSpeedIn) {
			super(tier, attackDamageIn, attackSpeedIn, new Item.Properties().tab(DeferredRegistryHandler.ITEM_GROUP).fireResistant());
		}

		@Override
		public boolean hurtEnemy(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity1) {
			livingEntity.setSecondsOnFire(10);
			return super.hurtEnemy(itemStack, livingEntity, livingEntity1);
		}
	}

}