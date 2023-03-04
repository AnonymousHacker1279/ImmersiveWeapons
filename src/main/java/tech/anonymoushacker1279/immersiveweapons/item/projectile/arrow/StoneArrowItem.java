package tech.anonymoushacker1279.immersiveweapons.item.projectile.arrow;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.arrow.StoneArrowEntity;

public class StoneArrowItem extends AbstractArrowItem {

	public StoneArrowItem(Properties properties, double damageIn) {
		super(properties, damageIn);
		damage = damageIn;
	}

	@Override
	public StoneArrowEntity createArrow(Level level, ItemStack stack, LivingEntity shooter) {
		StoneArrowEntity arrowEntity = new StoneArrowEntity(shooter, level);
		arrowEntity.setBaseDamage(damage);
		return arrowEntity;
	}
}