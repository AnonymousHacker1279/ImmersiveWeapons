package com.anonymoushacker1279.immersiveweapons.item;

import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity.PickupStatus;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ArrowItems {

	public static class CopperArrowItem extends CustomArrowItem {

		/**
		 * Constructor for CopperArrowItem.
		 * @param properties the <code>Properties</code> for the item
		 * @param damageIn the damage to deal on impact
		 */
		public CopperArrowItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			damage = damageIn;
		}

		/**
		 * Create an arrow item.
		 * @param worldIn the <code>World</code> the shooter is in
		 * @param stack the <code>ItemStack</code> being shot
		 * @param shooter the <code>LivingEntity</code> shooting the arrow
		 * @return CopperArrowEntity
		 */
		@Override
		public CopperArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			CopperArrowEntity arrowEntity = new CopperArrowEntity(shooter, worldIn, ref.get());
			arrowEntity.setBaseDamage(damage);
			return arrowEntity;
		}
	}

	public static class IronArrowItem extends CustomArrowItem {

		/**
		 * Constructor for IronArrowItem.
		 * @param properties the <code>Properties</code> for the item
		 * @param damageIn the damage to deal on impact
		 */
		public IronArrowItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			damage = damageIn;
		}

		/**
		 * Create an arrow item.
		 * @param worldIn the <code>World</code> the shooter is in
		 * @param stack the <code>ItemStack</code> being shot
		 * @param shooter the <code>LivingEntity</code> shooting the arrow
		 * @return IronArrowEntity
		 */
		@Override
		public IronArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			IronArrowEntity arrowEntity = new IronArrowEntity(shooter, worldIn, ref.get());
			arrowEntity.setBaseDamage(damage);
			return arrowEntity;
		}
	}

	public static class DiamondArrowItem extends CustomArrowItem {

		/**
		 * Constructor for DiamondArrowItem.
		 * @param properties the <code>Properties</code> for the item
		 * @param damageIn the damage to deal on impact
		 */
		public DiamondArrowItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			damage = damageIn;
		}

		/**
		 * Create an arrow item.
		 * @param worldIn the <code>World</code> the shooter is in
		 * @param stack the <code>ItemStack</code> being shot
		 * @param shooter the <code>LivingEntity</code> shooting the arrow
		 * @return DiamondArrowEntity
		 */
		@Override
		public DiamondArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			DiamondArrowEntity arrowEntity = new DiamondArrowEntity(shooter, worldIn, ref.get());
			arrowEntity.setBaseDamage(damage);
			return arrowEntity;
		}
	}

	public static class GoldArrowItem extends CustomArrowItem {

		/**
		 * Constructor for GoldArrowItem.
		 * @param properties the <code>Properties</code> for the item
		 * @param damageIn the damage to deal on impact
		 */
		public GoldArrowItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			damage = damageIn;
		}

		/**
		 * Create an arrow item.
		 * @param worldIn the <code>World</code> the shooter is in
		 * @param stack the <code>ItemStack</code> being shot
		 * @param shooter the <code>LivingEntity</code> shooting the arrow
		 * @return GoldArrowEntity
		 */
		@Override
		public GoldArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			GoldArrowEntity arrowEntity = new GoldArrowEntity(shooter, worldIn, ref.get());
			arrowEntity.setBaseDamage(damage);
			return arrowEntity;
		}
	}

	public static class StoneArrowItem extends CustomArrowItem {

		/**
		 * Constructor for StoneArrowItem.
		 * @param properties the <code>Properties</code> for the item
		 * @param damageIn the damage to deal on impact
		 */
		public StoneArrowItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			damage = damageIn;
		}

		/**
		 * Create an arrow item.
		 * @param worldIn the <code>World</code> the shooter is in
		 * @param stack the <code>ItemStack</code> being shot
		 * @param shooter the <code>LivingEntity</code> shooting the arrow
		 * @return StoneArrowEntity
		 */
		@Override
		public StoneArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			StoneArrowEntity arrowEntity = new StoneArrowEntity(shooter, worldIn, ref.get());
			arrowEntity.setBaseDamage(damage);
			return arrowEntity;
		}
	}

	public static class WoodArrowItem extends CustomArrowItem {

		/**
		 * Constructor for WoodArrowItem.
		 * @param properties the <code>Properties</code> for the item
		 * @param damageIn the damage to deal on impact
		 */
		public WoodArrowItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			damage = damageIn;
		}

		/**
		 * Create an arrow item.
		 * @param worldIn the <code>World</code> the shooter is in
		 * @param stack the <code>ItemStack</code> being shot
		 * @param shooter the <code>LivingEntity</code> shooting the arrow
		 * @return WoodArrowEntity
		 */
		@Override
		public WoodArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			WoodArrowEntity arrowEntity = new WoodArrowEntity(shooter, worldIn, ref.get());
			arrowEntity.setBaseDamage(damage);
			return arrowEntity;
		}
	}

	public static class NetheriteArrowItem extends CustomArrowItem {

		/**
		 * Constructor for NetheriteArrowItem.
		 * @param properties the <code>Properties</code> for the item
		 * @param damageIn the damage to deal on impact
		 */
		public NetheriteArrowItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			damage = damageIn;
		}

		/**
		 * Create an arrow item.
		 * @param worldIn the <code>World</code> the shooter is in
		 * @param stack the <code>ItemStack</code> being shot
		 * @param shooter the <code>LivingEntity</code> shooting the arrow
		 * @return NetheriteArrowEntity
		 */
		@Override
		public NetheriteArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			NetheriteArrowEntity arrowEntity = new NetheriteArrowEntity(shooter, worldIn, ref.get());
			arrowEntity.setBaseDamage(damage);
			return arrowEntity;
		}
	}

	public static class SmokeBombArrowItem extends CustomArrowItem {

		private final int color;

		/**
		 * Constructor for SmokeBombArrowItem.
		 * @param properties the <code>Properties</code> for the item
		 * @param damageIn the damage to deal on impact
		 */
		public SmokeBombArrowItem(Properties properties, double damageIn, int color) {
			super(properties, damageIn);
			damage = damageIn;
			this.color = color;
		}

		/**
		 * Create an arrow item.
		 * @param worldIn the <code>World</code> the shooter is in
		 * @param stack the <code>ItemStack</code> being shot
		 * @param shooter the <code>LivingEntity</code> shooting the arrow
		 * @return SmokeBombArrowEntity
		 */
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