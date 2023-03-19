package tech.anonymoushacker1279.immersiveweapons.item.projectile.arrow;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.arrow.TeslaArrowEntity;

public class TeslaArrowItem extends AbstractArrowItem {

	public TeslaArrowItem(Properties properties, double damage) {
		super(properties, damage);
		this.damage = damage;
	}

	@Override
	public TeslaArrowEntity createArrow(Level level, ItemStack stack, LivingEntity shooter) {
		TeslaArrowEntity arrowEntity = new TeslaArrowEntity(shooter, level);
		arrowEntity.setBaseDamage(damage);
		return arrowEntity;
	}

	@Override
	public boolean canBeInfinite() {
		return false;
	}
}