package tech.anonymoushacker1279.immersiveweapons.item.projectile.arrow;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.arrow.StarstormArrowEntity;

public class StarstormArrowItem extends AbstractArrowItem {

	public StarstormArrowItem(Properties properties, double damage) {
		super(properties, damage);
		this.damage = damage;
	}

	@Override
	public StarstormArrowEntity createArrow(Level level, ItemStack stack, LivingEntity shooter) {
		StarstormArrowEntity arrowEntity = new StarstormArrowEntity(shooter, level);
		arrowEntity.setBaseDamage(damage);
		return arrowEntity;
	}

	@Override
	public boolean canBeInfinite() {
		return false;
	}
}