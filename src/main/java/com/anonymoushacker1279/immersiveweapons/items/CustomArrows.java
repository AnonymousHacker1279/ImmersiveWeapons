package com.anonymoushacker1279.immersiveweapons.items;

import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.CopperArrowEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CustomArrows {

	public static class CopperArrowItem extends CustomArrowItem {
		
		// public final double damage;
		// private RegistryObject<Item> ref;
		
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
}
