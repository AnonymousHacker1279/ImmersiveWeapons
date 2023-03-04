package tech.anonymoushacker1279.immersiveweapons.item.projectile.arrow;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.arrow.CobaltArrowEntity;

public class CobaltArrowItem extends AbstractArrowItem {

	public CobaltArrowItem(Properties properties, double damageIn) {
		super(properties, damageIn);
		damage = damageIn;
	}

	@Override
	public CobaltArrowEntity createArrow(Level level, ItemStack stack, LivingEntity shooter) {
		CobaltArrowEntity arrowEntity = new CobaltArrowEntity(shooter, level);
		arrowEntity.setBaseDamage(damage);
		return arrowEntity;
	}
}