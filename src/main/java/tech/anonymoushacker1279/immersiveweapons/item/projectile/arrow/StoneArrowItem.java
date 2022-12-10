package tech.anonymoushacker1279.immersiveweapons.item.projectile.arrow;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.ArrowEntities;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.ArrowEntities.StoneArrowEntity;

public class StoneArrowItem extends AbstractArrowItem {

	/**
	 * Constructor for StoneArrowItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 * @param damageIn   the damage to deal on impact
	 */
	public StoneArrowItem(Properties properties, double damageIn) {
		super(properties, damageIn);
		damage = damageIn;
	}

	/**
	 * Create an arrow item.
	 *
	 * @param worldIn the <code>World</code> the shooter is in
	 * @param stack   the <code>ItemStack</code> being shot
	 * @param shooter the <code>LivingEntity</code> shooting the arrow
	 * @return StoneArrowEntity
	 */
	@Override
	public ArrowEntities.StoneArrowEntity createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
		StoneArrowEntity arrowEntity = new StoneArrowEntity(shooter, worldIn);
		arrowEntity.setBaseDamage(damage);
		return arrowEntity;
	}
}