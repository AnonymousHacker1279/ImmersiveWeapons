package tech.anonymoushacker1279.immersiveweapons.item.projectile.arrow;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.arrow.CopperArrowEntity;


public class CopperArrowItem extends AbstractArrowItem {

	public CopperArrowItem(Properties properties, double damageIn) {
		super(properties, damageIn);
		damage = damageIn;
	}

	@Override
	public CopperArrowEntity createArrow(Level level, ItemStack stack, LivingEntity shooter) {
		CopperArrowEntity arrowEntity = new CopperArrowEntity(shooter, level);
		arrowEntity.setBaseDamage(damage);
		return arrowEntity;
	}
}