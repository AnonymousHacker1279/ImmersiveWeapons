package tech.anonymoushacker1279.immersiveweapons.item.projectile.arrow;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.SmokeGrenadeArrowEntity;

public class SmokeGrenadeArrowItem extends AbstractArrowItem {

	private final int color;

	/**
	 * Constructor for SmokeGrenadeArrowItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 * @param damageIn   the damage to deal on impact
	 */
	public SmokeGrenadeArrowItem(Properties properties, double damageIn, int color) {
		super(properties, damageIn);
		damage = damageIn;
		this.color = color;
	}

	/**
	 * Create an arrow item.
	 *
	 * @param worldIn the <code>World</code> the shooter is in
	 * @param stack   the <code>ItemStack</code> being shot
	 * @param shooter the <code>LivingEntity</code> shooting the arrow
	 * @return SmokeGrenadeArrowEntity
	 */
	@Override
	public @NotNull CustomArrowEntity.SmokeGrenadeArrowEntity createArrow(@NotNull Level worldIn, @NotNull ItemStack stack, @NotNull LivingEntity shooter) {
		SmokeGrenadeArrowEntity arrowEntity = new SmokeGrenadeArrowEntity(shooter, worldIn, ref.get().asItem());
		arrowEntity.setBaseDamage(damage);
		arrowEntity.pickup = Pickup.DISALLOWED;
		SmokeGrenadeArrowEntity.setColor(color);
		return arrowEntity;
	}
}