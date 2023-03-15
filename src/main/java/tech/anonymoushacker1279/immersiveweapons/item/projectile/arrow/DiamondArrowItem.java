package tech.anonymoushacker1279.immersiveweapons.item.projectile.arrow;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.arrow.DiamondArrowEntity;

public class DiamondArrowItem extends AbstractArrowItem {

	public DiamondArrowItem(Properties properties, double damageIn) {
		super(properties, damageIn);
		damage = damageIn;
	}

	@Override
	public DiamondArrowEntity createArrow(Level level, ItemStack stack, LivingEntity shooter) {
		DiamondArrowEntity arrowEntity = new DiamondArrowEntity(shooter, level);
		arrowEntity.setBaseDamage(damage);
		return arrowEntity;
	}

	@Override
	public boolean canBeInfinite() {
		return false;
	}
}