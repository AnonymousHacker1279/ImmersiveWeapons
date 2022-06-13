package tech.anonymoushacker1279.immersiveweapons.item.projectile.arrow;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.StoneArrowEntity;

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
	public @NotNull StoneArrowEntity createArrow(@NotNull Level worldIn, @NotNull ItemStack stack, @NotNull LivingEntity shooter) {
		StoneArrowEntity arrowEntity = new StoneArrowEntity(shooter, worldIn, ref.get().asItem());
		arrowEntity.setBaseDamage(damage);
		return arrowEntity;
	}
}