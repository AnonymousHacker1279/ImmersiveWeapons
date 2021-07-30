package com.anonymoushacker1279.immersiveweapons.item;

import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity.*;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BulletItems {

	public static class WoodBulletItem extends CustomArrowItem {

		/**
		 * Constructor for WoodBulletItem.
		 * @param properties the <code>Properties</code> for the item
		 * @param damageIn the damage to deal on impact
		 */
		public WoodBulletItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			damage = damageIn;
		}

		/**
		 * Create an arrow item.
		 * @param worldIn the <code>World</code> the shooter is in
		 * @param stack the <code>ItemStack</code> being shot
		 * @param shooter the <code>LivingEntity</code> shooting the arrow
		 * @return WoodBulletEntity
		 */
		@Override
		public WoodBulletEntity createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
			WoodBulletEntity bulletEntity = new WoodBulletEntity(shooter, worldIn, ref.get());
			bulletEntity.setBaseDamage(damage);
			bulletEntity.pickup = Pickup.DISALLOWED;
			bulletEntity.setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
			return bulletEntity;
		}
	}

	public static class StoneBulletItem extends CustomArrowItem {

		/**
		 * Constructor for StoneBulletItem.
		 * @param properties the <code>Properties</code> for the item
		 * @param damageIn the damage to deal on impact
		 */
		public StoneBulletItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			damage = damageIn;
		}

		/**
		 * Create an arrow item.
		 * @param worldIn the <code>World</code> the shooter is in
		 * @param stack the <code>ItemStack</code> being shot
		 * @param shooter the <code>LivingEntity</code> shooting the arrow
		 * @return StoneBulletItem
		 */
		@Override
		public StoneBulletEntity createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
			StoneBulletEntity bulletEntity = new StoneBulletEntity(shooter, worldIn, ref.get());
			bulletEntity.setBaseDamage(damage);
			bulletEntity.pickup = Pickup.DISALLOWED;
			bulletEntity.setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
			return bulletEntity;
		}
	}

	public static class CopperBulletItem extends CustomArrowItem {

		/**
		 * Constructor for CopperBulletItem.
		 * @param properties the <code>Properties</code> for the item
		 * @param damageIn the damage to deal on impact
		 */
		public CopperBulletItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			damage = damageIn;
		}

		/**
		 * Create an arrow item.
		 * @param worldIn the <code>World</code> the shooter is in
		 * @param stack the <code>ItemStack</code> being shot
		 * @param shooter the <code>LivingEntity</code> shooting the arrow
		 * @return CopperBulletItem
		 */
		@Override
		public CopperBulletEntity createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
			CopperBulletEntity bulletEntity = new CopperBulletEntity(shooter, worldIn, ref.get());
			bulletEntity.setBaseDamage(damage);
			bulletEntity.pickup = Pickup.DISALLOWED;
			bulletEntity.setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
			return bulletEntity;
		}
	}

	public static class IronBulletItem extends CustomArrowItem {

		/**
		 * Constructor for IronBulletItem.
		 * @param properties the <code>Properties</code> for the item
		 * @param damageIn the damage to deal on impact
		 */
		public IronBulletItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			damage = damageIn;
		}

		/**
		 * Create an arrow item.
		 * @param worldIn the <code>World</code> the shooter is in
		 * @param stack the <code>ItemStack</code> being shot
		 * @param shooter the <code>LivingEntity</code> shooting the arrow
		 * @return IronBulletEntity
		 */
		@Override
		public IronBulletEntity createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
			IronBulletEntity bulletEntity = new IronBulletEntity(shooter, worldIn, ref.get());
			bulletEntity.setBaseDamage(damage);
			bulletEntity.pickup = Pickup.DISALLOWED;
			bulletEntity.setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
			return bulletEntity;
		}
	}

	public static class GoldBulletItem extends CustomArrowItem {

		/**
		 * Constructor for GoldBulletItem.
		 * @param properties the <code>Properties</code> for the item
		 * @param damageIn the damage to deal on impact
		 */
		public GoldBulletItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			damage = damageIn;
		}

		/**
		 * Create an arrow item.
		 * @param worldIn the <code>World</code> the shooter is in
		 * @param stack the <code>ItemStack</code> being shot
		 * @param shooter the <code>LivingEntity</code> shooting the arrow
		 * @return GoldBulletEntity
		 */
		@Override
		public GoldBulletEntity createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
			GoldBulletEntity bulletEntity = new GoldBulletEntity(shooter, worldIn, ref.get());
			bulletEntity.setBaseDamage(damage);
			bulletEntity.pickup = Pickup.DISALLOWED;
			bulletEntity.setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
			return bulletEntity;
		}
	}

	public static class DiamondBulletItem extends CustomArrowItem {

		/**
		 * Constructor for DiamondBulletItem.
		 * @param properties the <code>Properties</code> for the item
		 * @param damageIn the damage to deal on impact
		 */
		public DiamondBulletItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			damage = damageIn;
		}

		/**
		 * Create an arrow item.
		 * @param worldIn the <code>World</code> the shooter is in
		 * @param stack the <code>ItemStack</code> being shot
		 * @param shooter the <code>LivingEntity</code> shooting the arrow
		 * @return DiamondBulletEntity
		 */
		@Override
		public DiamondBulletEntity createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
			DiamondBulletEntity bulletEntity = new DiamondBulletEntity(shooter, worldIn, ref.get());
			bulletEntity.setBaseDamage(damage);
			bulletEntity.pickup = Pickup.DISALLOWED;
			bulletEntity.setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
			bulletEntity.setPierceLevel((byte) 1);
			return bulletEntity;
		}
	}

	public static class NetheriteBulletItem extends CustomArrowItem {

		/**
		 * Constructor for NetheriteBulletItem.
		 * @param properties the <code>Properties</code> for the item
		 * @param damageIn the damage to deal on impact
		 */
		public NetheriteBulletItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			damage = damageIn;
		}

		/**
		 * Create an arrow item.
		 * @param worldIn the <code>World</code> the shooter is in
		 * @param stack the <code>ItemStack</code> being shot
		 * @param shooter the <code>LivingEntity</code> shooting the arrow
		 * @return NetheriteBulletEntity
		 */
		@Override
		public NetheriteBulletEntity createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
			NetheriteBulletEntity bulletEntity = new NetheriteBulletEntity(shooter, worldIn, ref.get());
			bulletEntity.setBaseDamage(damage);
			bulletEntity.pickup = Pickup.DISALLOWED;
			bulletEntity.setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
			bulletEntity.setPierceLevel((byte) 2);
			return bulletEntity;
		}
	}

	public static class FlareItem extends CustomArrowItem {

		/**
		 * Constructor for FlareItem.
		 * @param properties the <code>Properties</code> for the item
		 * @param damageIn the damage to deal on impact
		 */
		public FlareItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			damage = damageIn;
		}

		/**
		 * Create an arrow item.
		 * @param worldIn the <code>World</code> the shooter is in
		 * @param stack the <code>ItemStack</code> being shot
		 * @param shooter the <code>LivingEntity</code> shooting the arrow
		 * @return FlareEntity
		 */
		@Override
		public FlareEntity createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
			FlareEntity flareEntity = new FlareEntity(shooter, worldIn, ref.get());
			flareEntity.setBaseDamage(damage);
			flareEntity.pickup = Pickup.DISALLOWED;
			flareEntity.setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
			return flareEntity;
		}
	}
}