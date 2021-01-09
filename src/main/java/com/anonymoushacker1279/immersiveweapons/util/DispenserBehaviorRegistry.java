package com.anonymoushacker1279.immersiveweapons.util;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DispenserBehaviorRegistry implements IDispenseItemBehavior {

	public static void init() {
		DispenserBlock.registerDispenseBehavior(DeferredRegistryHandler.COPPER_ARROW.get(), new ProjectileDispenseBehavior() {
			@Override
			protected ProjectileEntity getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
				CustomArrowEntity arrowentity = new CustomArrowEntity(worldIn, position.getX(), position.getY(), position.getZ());
				arrowentity.pickupStatus = CustomArrowEntity.PickupStatus.ALLOWED;
				arrowentity.setDamage(2.15d);
				return arrowentity;
			}
		});
	}

	@Override
	public ItemStack dispense(IBlockSource p_dispense_1_, ItemStack p_dispense_2_) {
		return null;
	}
}