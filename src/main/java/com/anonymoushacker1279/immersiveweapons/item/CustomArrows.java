package com.anonymoushacker1279.immersiveweapons.item;

import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity.PickupStatus;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CustomArrows {

	public static class CopperArrowItem extends CustomArrowItem {

		public CopperArrowItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			damage = damageIn;
		}

		@Override
		public CopperArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			CopperArrowEntity arrowEntity = new CopperArrowEntity(shooter, worldIn, ref.get());
			arrowEntity.setBaseDamage(damage);
			return arrowEntity;
		}
	}

	public static class IronArrowItem extends CustomArrowItem {

		public IronArrowItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			damage = damageIn;
		}

		@Override
		public IronArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			IronArrowEntity arrowEntity = new IronArrowEntity(shooter, worldIn, ref.get());
			arrowEntity.setBaseDamage(damage);
			return arrowEntity;
		}
	}

	public static class DiamondArrowItem extends CustomArrowItem {

		public DiamondArrowItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			damage = damageIn;
		}

		@Override
		public DiamondArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			DiamondArrowEntity arrowEntity = new DiamondArrowEntity(shooter, worldIn, ref.get());
			arrowEntity.setBaseDamage(damage);
			return arrowEntity;
		}
	}

	public static class GoldArrowItem extends CustomArrowItem {

		public GoldArrowItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			damage = damageIn;
		}

		@Override
		public GoldArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			GoldArrowEntity arrowEntity = new GoldArrowEntity(shooter, worldIn, ref.get());
			arrowEntity.setBaseDamage(damage);
			return arrowEntity;
		}
	}

	public static class StoneArrowItem extends CustomArrowItem {

		public StoneArrowItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			damage = damageIn;
		}

		@Override
		public StoneArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			StoneArrowEntity arrowEntity = new StoneArrowEntity(shooter, worldIn, ref.get());
			arrowEntity.setBaseDamage(damage);
			return arrowEntity;
		}
	}

	public static class WoodArrowItem extends CustomArrowItem {

		public WoodArrowItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			damage = damageIn;
		}

		@Override
		public WoodArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			WoodArrowEntity arrowEntity = new WoodArrowEntity(shooter, worldIn, ref.get());
			arrowEntity.setBaseDamage(damage);
			return arrowEntity;
		}
	}

	public static class NetheriteArrowItem extends CustomArrowItem {

		public NetheriteArrowItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			damage = damageIn;
		}

		@Override
		public NetheriteArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			NetheriteArrowEntity arrowEntity = new NetheriteArrowEntity(shooter, worldIn, ref.get());
			arrowEntity.setBaseDamage(damage);
			return arrowEntity;
		}
	}

	public static class SmokeBombArrowItem extends CustomArrowItem {

		private final int color;

		public SmokeBombArrowItem(Properties properties, double damageIn, int color) {
			super(properties, damageIn);
			damage = damageIn;
			this.color = color;
		}


		@Override
		public SmokeBombArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			SmokeBombArrowEntity arrowEntity = new SmokeBombArrowEntity(shooter, worldIn, ref.get());
			arrowEntity.setBaseDamage(damage);
			arrowEntity.pickup = PickupStatus.DISALLOWED;
			SmokeBombArrowEntity.setColor(color);
			return arrowEntity;
		}
	}
}