package com.anonymoushacker1279.immersiveweapons.item;

import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity.*;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity.PickupStatus;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BulletItem {

	public static class WoodBulletItem extends CustomArrowItem {

		public WoodBulletItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			damage = damageIn;
		}

		@Override
		public WoodBulletEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			WoodBulletEntity bulletEntity = new WoodBulletEntity(shooter, worldIn, ref.get());
			bulletEntity.setBaseDamage(damage);
			bulletEntity.pickup = PickupStatus.DISALLOWED;
			bulletEntity.setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
			return bulletEntity;
		}
	}

	public static class StoneBulletItem extends CustomArrowItem {

		public StoneBulletItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			damage = damageIn;
		}

		@Override
		public StoneBulletEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			StoneBulletEntity bulletEntity = new StoneBulletEntity(shooter, worldIn, ref.get());
			bulletEntity.setBaseDamage(damage);
			bulletEntity.pickup = PickupStatus.DISALLOWED;
			bulletEntity.setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
			return bulletEntity;
		}
	}

	public static class CopperBulletItem extends CustomArrowItem {

		public CopperBulletItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			damage = damageIn;
		}

		@Override
		public CopperBulletEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			CopperBulletEntity bulletEntity = new CopperBulletEntity(shooter, worldIn, ref.get());
			bulletEntity.setBaseDamage(damage);
			bulletEntity.pickup = PickupStatus.DISALLOWED;
			bulletEntity.setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
			return bulletEntity;
		}
	}

	public static class IronBulletItem extends CustomArrowItem {

		public IronBulletItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			damage = damageIn;
		}

		@Override
		public IronBulletEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			IronBulletEntity bulletEntity = new IronBulletEntity(shooter, worldIn, ref.get());
			bulletEntity.setBaseDamage(damage);
			bulletEntity.pickup = PickupStatus.DISALLOWED;
			bulletEntity.setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
			return bulletEntity;
		}
	}

	public static class GoldBulletItem extends CustomArrowItem {

		public GoldBulletItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			damage = damageIn;
		}

		@Override
		public GoldBulletEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			GoldBulletEntity bulletEntity = new GoldBulletEntity(shooter, worldIn, ref.get());
			bulletEntity.setBaseDamage(damage);
			bulletEntity.pickup = PickupStatus.DISALLOWED;
			bulletEntity.setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
			return bulletEntity;
		}
	}

	public static class DiamondBulletItem extends CustomArrowItem {

		public DiamondBulletItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			damage = damageIn;
		}

		@Override
		public DiamondBulletEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			DiamondBulletEntity bulletEntity = new DiamondBulletEntity(shooter, worldIn, ref.get());
			bulletEntity.setBaseDamage(damage);
			bulletEntity.pickup = PickupStatus.DISALLOWED;
			bulletEntity.setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
			bulletEntity.setPierceLevel((byte) 1);
			return bulletEntity;
		}
	}

	public static class NetheriteBulletItem extends CustomArrowItem {

		public NetheriteBulletItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			damage = damageIn;
		}

		@Override
		public NetheriteBulletEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			NetheriteBulletEntity bulletEntity = new NetheriteBulletEntity(shooter, worldIn, ref.get());
			bulletEntity.setBaseDamage(damage);
			bulletEntity.pickup = PickupStatus.DISALLOWED;
			bulletEntity.setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
			bulletEntity.setPierceLevel((byte) 2);
			return bulletEntity;
		}
	}

	public static class FlareItem extends CustomArrowItem {

		public FlareItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			damage = damageIn;
		}

		@Override
		public FlareEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			FlareEntity flareEntity = new FlareEntity(shooter, worldIn, ref.get());
			flareEntity.setBaseDamage(damage);
			flareEntity.pickup = PickupStatus.DISALLOWED;
			flareEntity.setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
			return flareEntity;
		}
	}
}