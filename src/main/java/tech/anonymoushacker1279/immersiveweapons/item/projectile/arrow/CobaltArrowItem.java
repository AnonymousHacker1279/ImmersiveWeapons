package tech.anonymoushacker1279.immersiveweapons.item.projectile.arrow;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.ArrowEntities;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.ArrowEntities.CobaltArrowEntity;

public class CobaltArrowItem extends AbstractArrowItem {

	/**
	 * Constructor for CobaltArrowItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 * @param damageIn   the damage to deal on impact
	 */
	public CobaltArrowItem(Properties properties, double damageIn) {
		super(properties, damageIn);
		damage = damageIn;
	}

	/**
	 * Create an arrow item.
	 *
	 * @param worldIn the <code>World</code> the shooter is in
	 * @param stack   the <code>ItemStack</code> being shot
	 * @param shooter the <code>LivingEntity</code> shooting the arrow
	 * @return CobaltArrowEntity
	 */
	@Override
	public @NotNull ArrowEntities.CobaltArrowEntity createArrow(@NotNull Level worldIn, @NotNull ItemStack stack, @NotNull LivingEntity shooter) {
		CobaltArrowEntity arrowEntity = new CobaltArrowEntity(shooter, worldIn);
		arrowEntity.setBaseDamage(damage);
		return arrowEntity;
	}
}