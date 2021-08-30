package com.anonymoushacker1279.immersiveweapons.item.projectile.arrow;

import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.CopperArrowEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class CopperArrowItem extends AbstractArrowItem {

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
	public @NotNull CopperArrowEntity createArrow(@NotNull Level worldIn, @NotNull ItemStack stack, @NotNull LivingEntity shooter) {
		CopperArrowEntity arrowEntity = new CopperArrowEntity(shooter, worldIn, ref.get().asItem());
		arrowEntity.setBaseDamage(damage);
		return arrowEntity;
	}
}