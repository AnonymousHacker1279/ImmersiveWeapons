package com.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;

public class MoltenItems {

	public static class MoltenSword extends SwordItem {
		/**
		 * Constructor for MoltenSword.
		 * @param tier the <code>IItemTier</code>
		 * @param attackDamageIn attack damage
		 * @param attackSpeedIn attack speed
		 * @param properties the <code>Properties</code> for the item
		 */
		public MoltenSword(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
			super(tier, attackDamageIn, attackSpeedIn, properties);
		}

		/**
		 * Runs when an entity is hit.
		 * @param itemStack the <code>ItemStack</code> instance
		 * @param livingEntity the <code>LivingEntity</code> attacking
		 * @param livingEntity1 the <code>LivingEntity</code> being hit
		 * @return boolean
		 */
		@Override
		public boolean hurtEnemy(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity1) {
			livingEntity.setSecondsOnFire(10);
			return super.hurtEnemy(itemStack, livingEntity, livingEntity1);
		}
	}

	public static class MoltenAxe extends AxeItem {
		/**
		 * Constructor for MoltenAxe.
		 * @param tier the <code>IItemTier</code>
		 * @param attackDamageIn attack damage
		 * @param attackSpeedIn attack speed
		 * @param properties the <code>Properties</code> for the item
		 */
		public MoltenAxe(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
			super(tier, attackDamageIn, attackSpeedIn, properties);
		}

		/**
		 * Runs when an entity is hit.
		 * @param itemStack the <code>ItemStack</code> instance
		 * @param livingEntity the <code>LivingEntity</code> attacking
		 * @param livingEntity1 the <code>LivingEntity</code> being hit
		 * @return boolean
		 */
		@Override
		public boolean hurtEnemy(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity1) {
			livingEntity.setSecondsOnFire(10);
			return super.hurtEnemy(itemStack, livingEntity, livingEntity1);
		}
	}

	public static class MoltenPickaxe extends PickaxeItem {
		/**
		 * Constructor for MoltenPickaxe.
		 * @param tier the <code>IItemTier</code>
		 * @param attackDamageIn attack damage
		 * @param attackSpeedIn attack speed
		 * @param properties the <code>Properties</code> for the item
		 */
		public MoltenPickaxe(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
			super(tier, attackDamageIn, attackSpeedIn, properties);
		}

		/**
		 * Runs when an entity is hit.
		 * @param itemStack the <code>ItemStack</code> instance
		 * @param livingEntity the <code>LivingEntity</code> attacking
		 * @param livingEntity1 the <code>LivingEntity</code> being hit
		 * @return boolean
		 */
		@Override
		public boolean hurtEnemy(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity1) {
			livingEntity.setSecondsOnFire(10);
			return super.hurtEnemy(itemStack, livingEntity, livingEntity1);
		}
	}

	public static class MoltenShovel extends ShovelItem {
		/**
		 * Constructor for MoltenShovel.
		 * @param tier the <code>IItemTier</code>
		 * @param attackDamageIn attack damage
		 * @param attackSpeedIn attack speed
		 * @param properties the <code>Properties</code> for the item
		 */
		public MoltenShovel(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
			super(tier, attackDamageIn, attackSpeedIn, properties);
		}

		/**
		 * Runs when an entity is hit.
		 * @param itemStack the <code>ItemStack</code> instance
		 * @param livingEntity the <code>LivingEntity</code> attacking
		 * @param livingEntity1 the <code>LivingEntity</code> being hit
		 * @return boolean
		 */
		@Override
		public boolean hurtEnemy(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity1) {
			livingEntity.setSecondsOnFire(10);
			return super.hurtEnemy(itemStack, livingEntity, livingEntity1);
		}
	}

	public static class MoltenHoe extends HoeItem {
		/**
		 * Constructor for MoltenHoe.
		 * @param tier the <code>IItemTier</code>
		 * @param attackDamageIn attack damage
		 * @param attackSpeedIn attack speed
		 * @param properties the <code>Properties</code> for the item
		 */
		public MoltenHoe(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
			super(tier, attackDamageIn, attackSpeedIn, properties);
		}

		/**
		 * Runs when an entity is hit.
		 * @param itemStack the <code>ItemStack</code> instance
		 * @param livingEntity the <code>LivingEntity</code> attacking
		 * @param livingEntity1 the <code>LivingEntity</code> being hit
		 * @return boolean
		 */
		@Override
		public boolean hurtEnemy(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity1) {
			livingEntity.setSecondsOnFire(10);
			return super.hurtEnemy(itemStack, livingEntity, livingEntity1);
		}
	}

}