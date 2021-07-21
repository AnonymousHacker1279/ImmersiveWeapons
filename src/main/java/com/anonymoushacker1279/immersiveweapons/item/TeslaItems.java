package com.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class TeslaItems {

	public static class TeslaSword extends SwordItem {
		/**
		 * Constructor for TeslaSword.
		 * @param tier the <code>IItemTier</code>
		 * @param attackDamageIn attack damage
		 * @param attackSpeedIn attack speed
		 * @param properties the <code>Properties</code> for the item
		 */
		public TeslaSword(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
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
			livingEntity.addEffect(new EffectInstance(Effects.WEAKNESS, 140, 1, false, false));
			livingEntity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 140, 1, false, false));
			livingEntity.addEffect(new EffectInstance(Effects.CONFUSION, 140, 1, false, false));
			return super.hurtEnemy(itemStack, livingEntity, livingEntity1);
		}
	}

	public static class TeslaAxe extends AxeItem {
		/**
		 * Constructor for TeslaAxe.
		 * @param tier the <code>IItemTier</code>
		 * @param attackDamageIn attack damage
		 * @param attackSpeedIn attack speed
		 * @param properties the <code>Properties</code> for the item
		 */
		public TeslaAxe(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
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
			livingEntity.addEffect(new EffectInstance(Effects.WEAKNESS, 140, 1, false, false));
			livingEntity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 140, 1, false, false));
			livingEntity.addEffect(new EffectInstance(Effects.CONFUSION, 140, 1, false, false));
			return super.hurtEnemy(itemStack, livingEntity, livingEntity1);
		}
	}

	public static class TeslaPickaxe extends PickaxeItem {
		/**
		 * Constructor for TeslaPickaxe.
		 * @param tier the <code>IItemTier</code>
		 * @param attackDamageIn attack damage
		 * @param attackSpeedIn attack speed
		 * @param properties the <code>Properties</code> for the item
		 */
		public TeslaPickaxe(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
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
			livingEntity.addEffect(new EffectInstance(Effects.WEAKNESS, 140, 1, false, false));
			livingEntity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 140, 1, false, false));
			livingEntity.addEffect(new EffectInstance(Effects.CONFUSION, 140, 1, false, false));
			return super.hurtEnemy(itemStack, livingEntity, livingEntity1);
		}
	}

	public static class TeslaShovel extends ShovelItem {
		/**
		 * Constructor for TeslaShovel.
		 * @param tier the <code>IItemTier</code>
		 * @param attackDamageIn attack damage
		 * @param attackSpeedIn attack speed
		 * @param properties the <code>Properties</code> for the item
		 */
		public TeslaShovel(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
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
			livingEntity.addEffect(new EffectInstance(Effects.WEAKNESS, 140, 1, false, false));
			livingEntity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 140, 1, false, false));
			livingEntity.addEffect(new EffectInstance(Effects.CONFUSION, 140, 1, false, false));
			return super.hurtEnemy(itemStack, livingEntity, livingEntity1);
		}
	}

	public static class TeslaHoe extends HoeItem {
		/**
		 * Constructor for TeslaHoe.
		 * @param tier the <code>IItemTier</code>
		 * @param attackDamageIn attack damage
		 * @param attackSpeedIn attack speed
		 * @param properties the <code>Properties</code> for the item
		 */
		public TeslaHoe(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
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
			livingEntity.addEffect(new EffectInstance(Effects.WEAKNESS, 140, 1, false, false));
			livingEntity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 140, 1, false, false));
			livingEntity.addEffect(new EffectInstance(Effects.CONFUSION, 140, 1, false, false));
			return super.hurtEnemy(itemStack, livingEntity, livingEntity1);
		}
	}
}