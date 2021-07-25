package com.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;

public class TeslaItems {

	public static class TeslaSword extends SwordItem {
		/**
		 * Constructor for TeslaSword.
		 * @param tier the <code>IItemTier</code>
		 * @param attackDamageIn attack damage
		 * @param attackSpeedIn attack speed
		 * @param properties the <code>Properties</code> for the item
		 */
		public TeslaSword(Tier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
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
			livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 140, 1, false, false));
			livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 140, 1, false, false));
			livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 140, 1, false, false));
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
		public TeslaAxe(Tier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
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
			livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 140, 1, false, false));
			livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 140, 1, false, false));
			livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 140, 1, false, false));
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
		public TeslaPickaxe(Tier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
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
			livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 140, 1, false, false));
			livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 140, 1, false, false));
			livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 140, 1, false, false));
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
		public TeslaShovel(Tier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
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
			livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 140, 1, false, false));
			livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 140, 1, false, false));
			livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 140, 1, false, false));
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
		public TeslaHoe(Tier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
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
			livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 140, 1, false, false));
			livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 140, 1, false, false));
			livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 140, 1, false, false));
			return super.hurtEnemy(itemStack, livingEntity, livingEntity1);
		}
	}
}