package com.anonymoushacker1279.immersiveweapons.item;

import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.CopperArrowEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.DiamondArrowEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.GoldArrowEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.IronArrowEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.NetheriteArrowEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.StoneArrowEntity;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.WoodArrowEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CustomArrows {

	public static class CopperArrowItem extends CustomArrowItem {
		
		public CopperArrowItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			this.damage = damageIn;
		}
		
		@Override
		public CopperArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			CopperArrowEntity arrowentity = new CopperArrowEntity(shooter, worldIn, ref.get());
			arrowentity.setDamage(this.damage);
			return arrowentity;
		}
	}
	
	public static class IronArrowItem extends CustomArrowItem {
		
		public IronArrowItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			this.damage = damageIn;
		}
		
		@Override
		public IronArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			IronArrowEntity arrowentity = new IronArrowEntity(shooter, worldIn, ref.get());
			arrowentity.setDamage(this.damage);
			return arrowentity;
		}
	}
	
	public static class DiamondArrowItem extends CustomArrowItem {
		
		public DiamondArrowItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			this.damage = damageIn;
		}
		
		@Override
		public DiamondArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			DiamondArrowEntity arrowentity = new DiamondArrowEntity(shooter, worldIn, ref.get());
			arrowentity.setDamage(this.damage);
			return arrowentity;
		}
	}
	
	public static class GoldArrowItem extends CustomArrowItem {
		
		public GoldArrowItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			this.damage = damageIn;
		}
		
		@Override
		public GoldArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			GoldArrowEntity arrowentity = new GoldArrowEntity(shooter, worldIn, ref.get());
			arrowentity.setDamage(this.damage);
			return arrowentity;
		}
	}
	
	public static class StoneArrowItem extends CustomArrowItem {
		
		public StoneArrowItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			this.damage = damageIn;
		}
		
		@Override
		public StoneArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			StoneArrowEntity arrowentity = new StoneArrowEntity(shooter, worldIn, ref.get());
			arrowentity.setDamage(this.damage);
			return arrowentity;
		}
	}
	
	public static class WoodArrowItem extends CustomArrowItem {
		
		public WoodArrowItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			this.damage = damageIn;
		}
		
		@Override
		public WoodArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			WoodArrowEntity arrowentity = new WoodArrowEntity(shooter, worldIn, ref.get());
			arrowentity.setDamage(this.damage);
			return arrowentity;
		}
	}
	
	public static class NetheriteArrowItem extends CustomArrowItem {
		
		public NetheriteArrowItem(Properties properties, double damageIn) {
			super(properties, damageIn);
			this.damage = damageIn;
		}
		
		@Override
		public NetheriteArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
			NetheriteArrowEntity arrowentity = new NetheriteArrowEntity(shooter, worldIn, ref.get());
			arrowentity.setDamage(this.damage);
			return arrowentity;
		}
	}
}
