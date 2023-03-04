package tech.anonymoushacker1279.immersiveweapons.item.projectile.arrow;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.arrow.WoodenArrowEntity;

public class WoodenArrowItem extends AbstractArrowItem {

	public WoodenArrowItem(Properties properties, double damageIn) {
		super(properties, damageIn);
		damage = damageIn;
	}

	@Override
	public WoodenArrowEntity createArrow(Level level, ItemStack stack, LivingEntity shooter) {
		WoodenArrowEntity arrowEntity = new WoodenArrowEntity(shooter, level);
		arrowEntity.setBaseDamage(damage);
		return arrowEntity;
	}
}