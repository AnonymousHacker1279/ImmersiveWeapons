package com.anonymoushacker1279.immersiveweapons.item;

import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity.*;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity.PickupStatus;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Bullets {

	public static class WoodBulletItem extends CustomArrowItem {

		public WoodBulletItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			this.damage = damageIn;
		}

		@Override
		public WoodBulletEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			WoodBulletEntity bulletentity = new WoodBulletEntity(shooter, worldIn, ref.get());
			bulletentity.setDamage(this.damage);
			bulletentity.pickupStatus = PickupStatus.DISALLOWED;
			bulletentity.setHitSound(DeferredRegistryHandler.BULLET_WHIZZ.get());
			return bulletentity;
		}
	}

	public static class StoneBulletItem extends CustomArrowItem {

		public StoneBulletItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			this.damage = damageIn;
		}

		@Override
		public StoneBulletEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			StoneBulletEntity bulletentity = new StoneBulletEntity(shooter, worldIn, ref.get());
			bulletentity.setDamage(this.damage);
			bulletentity.pickupStatus = PickupStatus.DISALLOWED;
			bulletentity.setHitSound(DeferredRegistryHandler.BULLET_WHIZZ.get());
			return bulletentity;
		}
	}

	public static class CopperBulletItem extends CustomArrowItem {

		public CopperBulletItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			this.damage = damageIn;
		}

		@Override
		public CopperBulletEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			CopperBulletEntity bulletentity = new CopperBulletEntity(shooter, worldIn, ref.get());
			bulletentity.setDamage(this.damage);
			bulletentity.pickupStatus = PickupStatus.DISALLOWED;
			bulletentity.setHitSound(DeferredRegistryHandler.BULLET_WHIZZ.get());
			return bulletentity;
		}
	}

	public static class IronBulletItem extends CustomArrowItem {

		public IronBulletItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			this.damage = damageIn;
		}

		@Override
		public IronBulletEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			IronBulletEntity bulletentity = new IronBulletEntity(shooter, worldIn, ref.get());
			bulletentity.setDamage(this.damage);
			bulletentity.pickupStatus = PickupStatus.DISALLOWED;
			bulletentity.setHitSound(DeferredRegistryHandler.BULLET_WHIZZ.get());
			return bulletentity;
		}
	}

	public static class GoldBulletItem extends CustomArrowItem {

		public GoldBulletItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			this.damage = damageIn;
		}

		@Override
		public GoldBulletEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			GoldBulletEntity bulletentity = new GoldBulletEntity(shooter, worldIn, ref.get());
			bulletentity.setDamage(this.damage);
			bulletentity.pickupStatus = PickupStatus.DISALLOWED;
			bulletentity.setHitSound(DeferredRegistryHandler.BULLET_WHIZZ.get());
			return bulletentity;
		}
	}

	public static class DiamondBulletItem extends CustomArrowItem {

		public DiamondBulletItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			this.damage = damageIn;
		}

		@Override
		public DiamondBulletEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			DiamondBulletEntity bulletentity = new DiamondBulletEntity(shooter, worldIn, ref.get());
			bulletentity.setDamage(this.damage);
			bulletentity.pickupStatus = PickupStatus.DISALLOWED;
			bulletentity.setHitSound(DeferredRegistryHandler.BULLET_WHIZZ.get());
			bulletentity.setPierceLevel((byte) 1);
			return bulletentity;
		}
	}

	public static class NetheriteBulletItem extends CustomArrowItem {

		public NetheriteBulletItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			this.damage = damageIn;
		}

		@Override
		public NetheriteBulletEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			NetheriteBulletEntity bulletentity = new NetheriteBulletEntity(shooter, worldIn, ref.get());
			bulletentity.setDamage(this.damage);
			bulletentity.pickupStatus = PickupStatus.DISALLOWED;
			bulletentity.setHitSound(DeferredRegistryHandler.BULLET_WHIZZ.get());
			bulletentity.setPierceLevel((byte) 2);
			return bulletentity;
		}
	}
}