package tech.anonymoushacker1279.immersiveweapons.item.projectile.arrow;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.arrow.GoldenArrowEntity;

public class GoldenArrowItem extends AbstractArrowItem {

	public GoldenArrowItem(Properties properties, double damageIn) {
		super(properties, damageIn);
		damage = damageIn;
	}

	@Override
	public GoldenArrowEntity createArrow(Level level, ItemStack stack, LivingEntity shooter) {
		GoldenArrowEntity arrowEntity = new GoldenArrowEntity(shooter, level);
		arrowEntity.setBaseDamage(damage);
		return arrowEntity;
	}
}