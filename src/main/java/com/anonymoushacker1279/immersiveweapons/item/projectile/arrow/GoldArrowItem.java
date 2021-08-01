package com.anonymoushacker1279.immersiveweapons.item.projectile.arrow;

import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.GoldArrowEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GoldArrowItem extends AbstractArrowItem {

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
	public GoldArrowEntity createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
		GoldArrowEntity arrowEntity = new GoldArrowEntity(shooter, worldIn, ref.get());
		arrowEntity.setBaseDamage(damage);
		return arrowEntity;
	}
}